/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.debugger.evaluate;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/idea-completion/testData/handlers/runtimeCast")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class CodeFragmentCompletionHandlerTestGenerated extends AbstractCodeFragmentCompletionHandlerTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    public void testAllFilesPresentInRuntimeCast() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/idea-completion/testData/handlers/runtimeCast"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @TestMetadata("CastPrivateFun.kt")
    public void testCastPrivateFun() throws Exception {
        runTest("idea/idea-completion/testData/handlers/runtimeCast/CastPrivateFun.kt");
    }

    @TestMetadata("CastPropertyWithSmartCast.kt")
    public void testCastPropertyWithSmartCast() throws Exception {
        runTest("idea/idea-completion/testData/handlers/runtimeCast/CastPropertyWithSmartCast.kt");
    }

    @TestMetadata("InsertExtFunction.kt")
    public void testInsertExtFunction() throws Exception {
        runTest("idea/idea-completion/testData/handlers/runtimeCast/InsertExtFunction.kt");
    }

    @TestMetadata("InsertFunction.kt")
    public void testInsertFunction() throws Exception {
        runTest("idea/idea-completion/testData/handlers/runtimeCast/InsertFunction.kt");
    }

    @TestMetadata("InsertImport.kt")
    public void testInsertImport() throws Exception {
        runTest("idea/idea-completion/testData/handlers/runtimeCast/InsertImport.kt");
    }

    @TestMetadata("NotImportedExtension.kt")
    public void testNotImportedExtension() throws Exception {
        runTest("idea/idea-completion/testData/handlers/runtimeCast/NotImportedExtension.kt");
    }
}
