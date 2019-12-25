/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.codeInsight.generate;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/codeInsight/generate/toString")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class GenerateToStringActionTestGenerated extends AbstractGenerateToStringActionTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    public void testAllFilesPresentInToString() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/testData/codeInsight/generate/toString"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @TestMetadata("idea/testData/codeInsight/generate/toString/common")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class Common extends AbstractGenerateToStringActionTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
        }

        public void testAllFilesPresentInCommon() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/testData/codeInsight/generate/toString/common"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @TestMetadata("annotation.kt")
        public void testAnnotation() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/common/annotation.kt");
        }

        @TestMetadata("interface.kt")
        public void testInterface() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/common/interface.kt");
        }

        @TestMetadata("object.kt")
        public void testObject() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/common/object.kt");
        }
    }

    @TestMetadata("idea/testData/codeInsight/generate/toString/multipeTemplates")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class MultipeTemplates extends AbstractGenerateToStringActionTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
        }

        public void testAllFilesPresentInMultipeTemplates() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/testData/codeInsight/generate/toString/multipeTemplates"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @TestMetadata("arrays.kt")
        public void testArrays() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/arrays.kt");
        }

        @TestMetadata("customAccessors.kt")
        public void testCustomAccessors() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/customAccessors.kt");
        }

        @TestMetadata("dataClass.kt")
        public void testDataClass() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/dataClass.kt");
        }

        @TestMetadata("keepQuotes.kt")
        public void testKeepQuotes() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/keepQuotes.kt");
        }

        @TestMetadata("multipleVars.kt")
        public void testMultipleVars() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/multipleVars.kt");
        }

        @TestMetadata("multipleVarsWithSuperClass.kt")
        public void testMultipleVarsWithSuperClass() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/multipleVarsWithSuperClass.kt");
        }

        @TestMetadata("noVars.kt")
        public void testNoVars() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/noVars.kt");
        }

        @TestMetadata("singleVar.kt")
        public void testSingleVar() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/singleVar.kt");
        }

        @TestMetadata("superClassNoVars.kt")
        public void testSuperClassNoVars() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/multipeTemplates/superClassNoVars.kt");
        }
    }

    @TestMetadata("idea/testData/codeInsight/generate/toString/singleTemplate")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class SingleTemplate extends AbstractGenerateToStringActionTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
        }

        public void testAllFilesPresentInSingleTemplate() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/testData/codeInsight/generate/toString/singleTemplate"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @TestMetadata("arrays.kt")
        public void testArrays() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/arrays.kt");
        }

        @TestMetadata("customAccessors.kt")
        public void testCustomAccessors() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/customAccessors.kt");
        }

        @TestMetadata("dataClass.kt")
        public void testDataClass() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/dataClass.kt");
        }

        @TestMetadata("explicitDefaultAccessors.kt")
        public void testExplicitDefaultAccessors() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/explicitDefaultAccessors.kt");
        }

        @TestMetadata("keepQuotes.kt")
        public void testKeepQuotes() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/keepQuotes.kt");
        }

        @TestMetadata("multipleVars.kt")
        public void testMultipleVars() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/multipleVars.kt");
        }

        @TestMetadata("multipleVarsWithSuperClass.kt")
        public void testMultipleVarsWithSuperClass() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/multipleVarsWithSuperClass.kt");
        }

        @TestMetadata("noVars.kt")
        public void testNoVars() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/noVars.kt");
        }

        @TestMetadata("nullableArrays.kt")
        public void testNullableArrays() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/nullableArrays.kt");
        }

        @TestMetadata("singleVar.kt")
        public void testSingleVar() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/singleVar.kt");
        }

        @TestMetadata("superClassNoVars.kt")
        public void testSuperClassNoVars() throws Exception {
            runTest("idea/testData/codeInsight/generate/toString/singleTemplate/superClassNoVars.kt");
        }
    }
}
