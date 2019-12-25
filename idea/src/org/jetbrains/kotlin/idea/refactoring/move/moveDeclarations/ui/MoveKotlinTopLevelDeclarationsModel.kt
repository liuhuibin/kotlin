/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.refactoring.move.moveDeclarations.ui

import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.options.ConfigurationException
import com.intellij.psi.*
import com.intellij.refactoring.BaseRefactoringProcessor
import com.intellij.refactoring.MoveDestination
import com.intellij.refactoring.PackageWrapper
import com.intellij.refactoring.RefactoringBundle
import com.intellij.refactoring.move.MoveCallback
import com.intellij.refactoring.move.moveClassesOrPackages.AutocreatingSingleSourceRootMoveDestination
import com.intellij.refactoring.move.moveClassesOrPackages.MultipleRootsMoveDestination
import com.intellij.util.IncorrectOperationException
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.core.util.toPsiFile
import org.jetbrains.kotlin.idea.refactoring.KotlinRefactoringBundle
import org.jetbrains.kotlin.idea.refactoring.getOrCreateKotlinFile
import org.jetbrains.kotlin.idea.refactoring.move.getOrCreateDirectory
import org.jetbrains.kotlin.idea.refactoring.move.moveDeclarations.*
import org.jetbrains.kotlin.idea.refactoring.move.updatePackageDirective
import org.jetbrains.kotlin.idea.util.application.executeCommand
import org.jetbrains.kotlin.idea.util.application.runWriteAction
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Paths

internal class MoveKotlinTopLevelDeclarationsModel(
    val project: Project,
    val elementsToMove: List<KtNamedDeclaration>,
    val targetPackage: String,
    val selectedPsiDirectory: PsiDirectory?,
    val fileNameInPackage: String,
    val targetFilePath: String,
    val isMoveToPackage: Boolean,
    val isSearchReferences: Boolean,
    val isSearchInComments: Boolean,
    val isSearchInNonJavaFiles: Boolean,
    val isDeleteEmptyFiles: Boolean,
    val isUpdatePackageDirective: Boolean,
    val isFullFileMove: Boolean,
    val moveCallback: MoveCallback?
) : Model<BaseRefactoringProcessor> {

    private inline fun <T, K> List<T>.mapToSingleOrNull(transform: (T) -> K?): K? =
        mapTo(mutableSetOf(), transform).singleOrNull()

    private val sourceDirectory by lazy {
        sourceFiles.mapToSingleOrNull { it.parent } ?: throw ConfigurationException("Can't determine sources directory")
    }

    private val sourceFiles = elementsToMove.map { it.containingKtFile }.distinct()

    private data class TargetDirAndDestination(val targetDir: VirtualFile?, val destination: MoveDestination)

    private fun selectPackageBasedTargetDirAndDestination(): TargetDirAndDestination {

        val targetPackageWrapper = PackageWrapper(PsiManager.getInstance(project), targetPackage)

        return if (selectedPsiDirectory == null)
            TargetDirAndDestination(null, MultipleRootsMoveDestination(targetPackageWrapper))
        else {
            TargetDirAndDestination(
                selectedPsiDirectory.virtualFile,
                AutocreatingSingleSourceRootMoveDestination(targetPackageWrapper, selectedPsiDirectory.virtualFile)
            )
        }
    }

    private fun checkTargetFileName(fileName: String) {
        if (FileTypeManager.getInstance().getFileTypeByFileName(fileName) != KotlinFileType.INSTANCE) {
            throw ConfigurationException(KotlinRefactoringBundle.message("refactoring.move.non.kotlin.file"))
        }
    }

    private fun getFilesExistingInTargetDir(
        targetFileName: String?,
        targetDirectory: PsiDirectory
    ): Set<PsiFile> {
        return if (targetFileName != null) {
            targetDirectory.findFile(targetFileName)?.let { setOf(it) }.orEmpty()
        } else {
            sourceFiles.mapNotNullTo(mutableSetOf()) { targetDirectory.findFile(it.name) }
        }
    }

    private fun selectMoveTargetToPackage(): KotlinMoveTarget {
        require(sourceFiles.isNotEmpty())

        val (targetDir, moveDestination) = selectPackageBasedTargetDirAndDestination()
        val targetDirectory = moveDestination.getTargetIfExists(sourceDirectory)
            ?: throw ConfigurationException("Can't get target directory for selected package")

        val destination = sourceFiles
            .mapToSingleOrNull { moveDestination.getTargetIfExists(it) }
            ?: throw ConfigurationException("Can't get target for all source elements")

        val singleSourceFileMode = sourceFiles.size == 1

        val targetFileName = if (singleSourceFileMode) fileNameInPackage.also(::checkTargetFileName) else null
        val filesExistingInTargetDir = getFilesExistingInTargetDir(targetFileName, targetDirectory)

        if (filesExistingInTargetDir.isNotEmpty()) {
            if (singleSourceFileMode) {
                val singeTargetFile = filesExistingInTargetDir.single() as? KtFile
                if (singeTargetFile != null) {
                    return KotlinMoveTargetForExistingElement(singeTargetFile)
                }
            } else {
                val filePathsToReport = filesExistingInTargetDir.joinToString(
                    separator = "\n",
                    prefix = "Cannot perform refactoring since the following files already exist:\n\n"
                ) { it.virtualFile.path }
                throw ConfigurationException(filePathsToReport)
            }
        }

        return KotlinMoveTargetForDeferredFile(
            FqName(targetPackage),
            destination,
            targetDir
        ) {
            val deferredFileName = if (singleSourceFileMode) fileNameInPackage else it.name
            val deferredFileDirectory = moveDestination.getTargetDirectory(it)
            getOrCreateKotlinFile(deferredFileName, deferredFileDirectory)
        }
    }

    private fun selectMoveTargetToFile(): KotlinMoveTarget {

        try {
            Paths.get(targetFilePath)
        } catch (e: InvalidPathException) {
            throw ConfigurationException("Invalid target path $targetFilePath")
        }

        val targetFile = File(targetFilePath)
        checkTargetFileName(targetFile.name)

        val jetFile = targetFile.toPsiFile(project) as? KtFile
        if (jetFile != null) {
            if (sourceFiles.size == 1 && sourceFiles.contains(jetFile)) {
                throw ConfigurationException("Can't move to the original file")
            }
            return KotlinMoveTargetForExistingElement(jetFile)
        }

        val targetDirPath = targetFile.toPath().parent
            ?: throw ConfigurationException("Incorrect target path. Directory is not specified.")

        val projectBasePath = project.basePath
            ?: throw ConfigurationException("Can't move for current project")

        if (!targetDirPath.startsWith(projectBasePath)) {
            throw ConfigurationException("Incorrect target path. Directory $targetDirPath does not belong to current project.")
        }

        val absoluteTargetDirPath = targetDirPath.toString()
        val psiDirectory: PsiDirectory
        try {
            psiDirectory = getOrCreateDirectory(absoluteTargetDirPath, project)
        } catch (e: IncorrectOperationException) {
            throw ConfigurationException("Failed to create parent directory: $absoluteTargetDirPath")
        }

        val targetPackageFqName = sourceFiles.singleOrNull()?.packageFqName
            ?: JavaDirectoryService.getInstance().getPackage(psiDirectory)?.let { FqName(it.qualifiedName) }
            ?: throw ConfigurationException("Could not find package corresponding to $absoluteTargetDirPath")

        val finalTargetPackageFqName = targetPackageFqName.asString()
        return KotlinMoveTargetForDeferredFile(
            targetPackageFqName,
            psiDirectory,
            targetFile = null
        ) { getOrCreateKotlinFile(targetFile.name, psiDirectory, finalTargetPackageFqName) }
    }

    private fun selectMoveTarget() =
        if (isMoveToPackage) selectMoveTargetToPackage() else selectMoveTargetToFile()

    private fun verifyBeforeRun() {

        if (elementsToMove.isEmpty()) throw ConfigurationException("At least one member must be selected")
        if (sourceFiles.isEmpty()) throw ConfigurationException("None elements were selected")
        if (sourceFiles.size == 1 && fileNameInPackage.isBlank()) throw ConfigurationException("File name may not be empty")

        if (isMoveToPackage) {
            if (targetPackage.isNotEmpty() && !PsiNameHelper.getInstance(project).isQualifiedName(targetPackage)) {
                throw ConfigurationException("\'$targetPackage\' is invalid destination package name")
            }
        } else {
            val targetFile = File(targetFilePath).toPsiFile(project)
            if (targetFile != null && targetFile !is KtFile) {
                throw ConfigurationException(KotlinRefactoringBundle.message("refactoring.move.non.kotlin.file"))
            }
        }
    }

    @Throws(ConfigurationException::class)
    override fun computeModelResult() = computeModelResult(throwOnConflicts = false)

    @Throws(ConfigurationException::class)
    override fun computeModelResult(throwOnConflicts: Boolean): BaseRefactoringProcessor {

        verifyBeforeRun()

        if (isFullFileMove && isMoveToPackage) {
            tryMoveFile(throwOnConflicts)?.let { return it }
        }

        return moveDeclaration(throwOnConflicts)
    }

    private fun tryMoveFile(throwOnConflicts: Boolean): BaseRefactoringProcessor? {

        val targetFileName = if (sourceFiles.size > 1) null else fileNameInPackage
        if (targetFileName != null) checkTargetFileName(targetFileName)

        val moveDestination = selectPackageBasedTargetDirAndDestination().destination
        val targetDir = moveDestination.getTargetIfExists(sourceDirectory) ?: return null

        val filesExistingInTargetDir = getFilesExistingInTargetDir(targetFileName, targetDir)

        val moveAsFile = filesExistingInTargetDir.isEmpty() ||
                filesExistingInTargetDir.singleOrNull()?.let { sourceFiles.contains(it) } == true

        if (!moveAsFile) return null

        val targetDirectory = project.executeCommand(RefactoringBundle.message("move.title"), null) {
            runWriteAction<PsiDirectory> { moveDestination.getTargetDirectory(sourceDirectory) }
        }

        sourceFiles.forEach { it.updatePackageDirective = isUpdatePackageDirective }

        return if (targetFileName != null)
            MoveToKotlinFileProcessor(
                project,
                sourceFiles.first(),
                targetDirectory,
                targetFileName,
                searchInComments = isSearchInComments,
                searchInNonJavaFiles = isSearchInNonJavaFiles,
                moveCallback = moveCallback,
                throwOnConflicts = throwOnConflicts
            )
        else
            KotlinAwareMoveFilesOrDirectoriesProcessor(
                project,
                sourceFiles,
                targetDirectory,
                isSearchReferences,
                searchInComments = isSearchInComments,
                searchInNonJavaFiles = isSearchInNonJavaFiles,
                moveCallback = moveCallback,
                throwOnConflicts = throwOnConflicts
            )
    }

    private fun moveDeclaration(throwOnConflicts: Boolean): BaseRefactoringProcessor {
        val target = selectMoveTarget()
        for (element in elementsToMove) {
            target.verify(element.containingFile)?.let { throw ConfigurationException(it) }
        }

        val options = MoveDeclarationsDescriptor(
            project,
            MoveSource(elementsToMove),
            target,
            MoveDeclarationsDelegate.TopLevel,
            isSearchInComments,
            isSearchInNonJavaFiles,
            deleteSourceFiles = isFullFileMove && isDeleteEmptyFiles,
            moveCallback = moveCallback,
            openInEditor = false,
            allElementsToMove = null,
            analyzeConflicts = true,
            searchReferences = isSearchReferences
        )
        return MoveKotlinDeclarationsProcessor(options, Mover.Default, throwOnConflicts)
    }
}