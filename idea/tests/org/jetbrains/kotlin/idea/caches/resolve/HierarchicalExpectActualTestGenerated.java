/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.caches.resolve;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class HierarchicalExpectActualTestGenerated extends AbstractHierarchicalExpectActualTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    public void testAllFilesPresentInHierarchicalExpectActualMatching() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching"), Pattern.compile("^([^\\.]+)$"), null, false);
    }

    @TestMetadata("diamondActualInBottom")
    public void testDiamondActualInBottom() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/diamondActualInBottom/");
    }

    @TestMetadata("diamondActualOnOnePath")
    public void testDiamondActualOnOnePath() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/diamondActualOnOnePath/");
    }

    @TestMetadata("diamondDuplicateActuals")
    public void testDiamondDuplicateActuals() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/diamondDuplicateActuals/");
    }

    @TestMetadata("diamondSeesTwoActuals")
    public void testDiamondSeesTwoActuals() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/diamondSeesTwoActuals/");
    }

    @TestMetadata("differentKindsOfDependencies")
    public void testDifferentKindsOfDependencies() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/differentKindsOfDependencies/");
    }

    @TestMetadata("duplicateActualsExplicit")
    public void testDuplicateActualsExplicit() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateActualsExplicit/");
    }

    @TestMetadata("duplicateActualsImplicit")
    public void testDuplicateActualsImplicit() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateActualsImplicit/");
    }

    @TestMetadata("duplicateActualsOneWeaklyIncompatible")
    public void testDuplicateActualsOneWeaklyIncompatible() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateActualsOneWeaklyIncompatible/");
    }

    @TestMetadata("duplicateActualsOneWithStrongIncompatibility")
    public void testDuplicateActualsOneWithStrongIncompatibility() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateActualsOneWithStrongIncompatibility/");
    }

    @TestMetadata("duplicateExpectsExplicit")
    public void testDuplicateExpectsExplicit() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateExpectsExplicit/");
    }

    @TestMetadata("duplicateExpectsImplicit")
    public void testDuplicateExpectsImplicit() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateExpectsImplicit/");
    }

    @TestMetadata("duplicateExpectsWithStrongIncompatibility")
    public void testDuplicateExpectsWithStrongIncompatibility() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/duplicateExpectsWithStrongIncompatibility/");
    }

    @TestMetadata("hierarcicalActualization")
    public void testHierarcicalActualization() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/hierarcicalActualization/");
    }

    @TestMetadata("incompleteActualization")
    public void testIncompleteActualization() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/incompleteActualization/");
    }

    @TestMetadata("weaklyIncompatibleActualInIntermediateModule")
    public void testWeaklyIncompatibleActualInIntermediateModule() throws Exception {
        runTest("idea/testData/multiModuleHighlighting/hierarchicalExpectActualMatching/weaklyIncompatibleActualInIntermediateModule/");
    }
}
