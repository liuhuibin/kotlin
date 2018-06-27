/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.checkers

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor
import org.jetbrains.kotlin.config.LanguageFeature
import org.jetbrains.kotlin.config.LanguageVersionSettings
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.typeUtil.contains

object BigFunctionTypeAvailabilityChecker : CallChecker {
    override fun check(resolvedCall: ResolvedCall<*>, reportOn: PsiElement, context: CallCheckerContext) {
        val descriptor = resolvedCall.resultingDescriptor
        val returnType = descriptor.returnType
        if (returnType != null) {
            check(returnType, reportOn, context.languageVersionSettings, context.trace)
        }
    }

    private fun check(type: KotlinType, reportOn: PsiElement, languageVersionSettings: LanguageVersionSettings, trace: BindingTrace) {
        if (!languageVersionSettings.supportsFeature(LanguageFeature.FunctionTypesWithBigArity) && type.contains { argumentType ->
            val descriptor = argumentType.constructor.declarationDescriptor
            descriptor is FunctionClassDescriptor && descriptor.hasBigArity
        }) {
            trace.report(
                Errors.UNSUPPORTED_FEATURE.on(
                    reportOn, LanguageFeature.FunctionTypesWithBigArity to languageVersionSettings
                )
            )
        }
    }

    object ClassifierUsage : ClassifierUsageChecker {
        override fun check(targetDescriptor: ClassifierDescriptor, element: PsiElement, context: ClassifierUsageCheckerContext) {
            check(targetDescriptor.defaultType, element, context.languageVersionSettings, context.trace)
        }
    }
}
