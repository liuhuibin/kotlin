/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.fir;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/fir/lazyResolve")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class FirLazyResolveTestGenerated extends AbstractFirLazyResolveTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    public void testAllFilesPresentInLazyResolve() throws Exception {
        KotlinTestUtils.assertAllTestsPresentInSingleGeneratedClassWithExcluded(this.getClass(), new File("idea/testData/fir/lazyResolve"), Pattern.compile("^(.+)\\.test$"), null);
    }

    @TestMetadata("elvis/elvis.test")
    public void testElvis_Elvis() throws Exception {
        runTest("idea/testData/fir/lazyResolve/elvis/elvis.test");
    }

    @TestMetadata("import/import.test")
    public void testImport_Import() throws Exception {
        runTest("idea/testData/fir/lazyResolve/import/import.test");
    }

    @TestMetadata("inInit/inInit.test")
    public void testInInit_InInit() throws Exception {
        runTest("idea/testData/fir/lazyResolve/inInit/inInit.test");
    }

    @TestMetadata("inLocal/inLocal.test")
    public void testInLocal_InLocal() throws Exception {
        runTest("idea/testData/fir/lazyResolve/inLocal/inLocal.test");
    }

    @TestMetadata("inSecondary/inSecondary.test")
    public void testInSecondary_InSecondary() throws Exception {
        runTest("idea/testData/fir/lazyResolve/inSecondary/inSecondary.test");
    }

    @TestMetadata("secondary/secondary.test")
    public void testSecondary_Secondary() throws Exception {
        runTest("idea/testData/fir/lazyResolve/secondary/secondary.test");
    }

    @TestMetadata("simple/simple.test")
    public void testSimple_Simple() throws Exception {
        runTest("idea/testData/fir/lazyResolve/simple/simple.test");
    }

    @TestMetadata("simpleProperty/simpleProperty.test")
    public void testSimpleProperty_SimpleProperty() throws Exception {
        runTest("idea/testData/fir/lazyResolve/simpleProperty/simpleProperty.test");
    }
}
