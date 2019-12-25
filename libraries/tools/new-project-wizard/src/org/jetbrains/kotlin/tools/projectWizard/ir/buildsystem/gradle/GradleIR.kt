package org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.gradle

import org.jetbrains.kotlin.tools.projectWizard.ir.buildsystem.*
import org.jetbrains.kotlin.tools.projectWizard.plugins.printer.BuildFilePrinter
import org.jetbrains.kotlin.tools.projectWizard.plugins.printer.GradlePrinter

interface GradleIR : BuildSystemIR {
    fun GradlePrinter.renderGradle()

    override fun BuildFilePrinter.render() {
        if (this is GradlePrinter) renderGradle()
    }
}


data class RawGradleIR(
    val renderer: GradlePrinter.() -> Unit
) : GradleIR {
    override fun GradlePrinter.renderGradle() = renderer()
}

data class CreateGradleValueIR(val name: String, val body: GradleIR) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        when (dsl) {
            GradlePrinter.GradleDsl.KOTLIN -> {
                +"val "
                +name
                +" = "
                body.render(this)
            }
            GradlePrinter.GradleDsl.GROOVY -> {
                +"def "
                +name
                +" = "
                body.render(this)
            }
        }
    }

}

data class GradleCallIr(
    val name: String,
    val parameters: List<BuildSystemIR> = emptyList()
) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        call(name, forceBrackets = true) {
            parameters.list { it.render(this) }
        }
    }
}

data class GradleNewInstanceCall(
    val name: String,
    val parameters: List<BuildSystemIR> = emptyList()
) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        if (dsl == GradlePrinter.GradleDsl.GROOVY) {
            +"new "
        }
        call(name, forceBrackets = true) {
            parameters.list { it.render(this) }
        }
    }
}




data class GradleSectionIR(
    val name: String,
    val body: BodyIR
) : GradleIR, FreeIR {
    override fun GradlePrinter.renderGradle() {
        +name
        +" "
        body.render(this)
    }
}

data class GradleAssignmentIR(
    val target: String,
    val assignee: BuildSystemIR
) : GradleIR, FreeIR {
    override fun GradlePrinter.renderGradle() {
        +"$target = "
        assignee.render(this)
    }
}

data class GradleStringConstIR(
    val text: String
) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        +text.quotified
    }
}

data class CompilationAccessIr(
    val targetName: String,
    val compilationName: String,
    val property: String?
) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        when (dsl) {
            GradlePrinter.GradleDsl.KOTLIN -> +"kotlin.$targetName().compilations[\"$compilationName\"]"
            GradlePrinter.GradleDsl.GROOVY -> +"kotlin.$targetName().compilations.$compilationName"
        }
        property?.let { +".$it" }
    }
}

data class GradleDynamicPropertyAccessIR(val qualifier: BuildSystemIR, val propertyName: String) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        qualifier.render(this)
        when (dsl) {
            GradlePrinter.GradleDsl.KOTLIN -> +"[${propertyName.quotified}]"
            GradlePrinter.GradleDsl.GROOVY -> +".$propertyName"
        }
    }
}

data class GradlePropertyAccessIR(val propertyName: String) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        +propertyName
    }
}

data class GradleImportIR(val import: String) : GradleIR {
    override fun GradlePrinter.renderGradle() {
        +"import "
        +import
    }
}

interface BuildScriptIR : BuildSystemIR

data class BuildScriptDependencyIR(val dependencyIR: GradleIR) : BuildScriptIR, BuildSystemIR by dependencyIR
data class BuildScriptRepositoryIR(val repositoryIR: RepositoryIR) : BuildScriptIR, BuildSystemIR by repositoryIR