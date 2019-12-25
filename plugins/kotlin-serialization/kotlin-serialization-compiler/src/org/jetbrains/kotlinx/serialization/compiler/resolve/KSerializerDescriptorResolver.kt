/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlinx.serialization.compiler.resolve

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorImpl
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.annotations.createDeprecatedAnnotation
import org.jetbrains.kotlin.descriptors.impl.*
import org.jetbrains.kotlin.incremental.components.NoLookupLocation
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.builtIns
import org.jetbrains.kotlin.resolve.descriptorUtil.module
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor
import org.jetbrains.kotlin.storage.LockBasedStorageManager
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.KotlinTypeFactory
import org.jetbrains.kotlin.types.TypeProjectionImpl
import org.jetbrains.kotlin.types.Variance
import org.jetbrains.kotlin.types.typeUtil.createProjection
import org.jetbrains.kotlin.types.typeUtil.makeNullable
import org.jetbrains.kotlinx.serialization.compiler.backend.ir.SimpleSyntheticPropertyDescriptor
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerialEntityNames.IMPL_NAME
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerialEntityNames.SERIALIZER_CLASS_NAME
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerialEntityNames.typeArgPrefix
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerializationAnnotations.serialInfoFqName
import java.util.*

object KSerializerDescriptorResolver {

    private fun createDeprecatedHiddenAnnotation(module: ModuleDescriptor): AnnotationDescriptor {
        return module.builtIns.createDeprecatedAnnotation("This synthesized declaration should not be used directly", level = "HIDDEN")
    }

    fun isSerialInfoImpl(thisDescriptor: ClassDescriptor): Boolean {
        return thisDescriptor.name == IMPL_NAME
               && thisDescriptor.containingDeclaration is LazyClassDescriptor
               && thisDescriptor.containingDeclaration.annotations.hasAnnotation(serialInfoFqName)
    }

    fun addSerialInfoSuperType(thisDescriptor: ClassDescriptor, supertypes: MutableList<KotlinType>) {
        if (isSerialInfoImpl(thisDescriptor)) {
            supertypes.add((thisDescriptor.containingDeclaration as LazyClassDescriptor).toSimpleType(false))
        }
    }

    fun addSerializerSupertypes(classDescriptor: ClassDescriptor, supertypes: MutableList<KotlinType>) {
        val serializableClassDescriptor = getSerializableClassDescriptorBySerializer(classDescriptor) ?: return
        if (supertypes.any(::isKSerializer)) return

        // Add GeneratedSerializer as superinterface for generated $serializer class, and KSerializer to all others
        val fqName = if (classDescriptor.name == SerialEntityNames.SERIALIZER_CLASS_NAME)
            SerialEntityNames.GENERATED_SERIALIZER_FQ
        else
            SerialEntityNames.KSERIALIZER_NAME_FQ
        supertypes.add(classDescriptor.createSerializerTypeFor(serializableClassDescriptor.defaultType, fqName))
    }

    fun addSerialInfoImplClass(
        interfaceDesc: ClassDescriptor,
        declarationProvider: ClassMemberDeclarationProvider,
        ctx: LazyClassContext
    ): ClassDescriptor {
        val interfaceDecl = declarationProvider.correspondingClassOrObject!!
        val scope = ctx.declarationScopeProvider.getResolutionScopeForDeclaration(declarationProvider.ownerInfo!!.scopeAnchor)

        val props = interfaceDecl.primaryConstructorParameters
        // if there is some properties, there will be a public synthetic constructor at the codegen phase
        val primaryCtorVisibility = if (props.isEmpty()) Visibilities.PUBLIC else Visibilities.PRIVATE

        val descriptor = SyntheticClassOrObjectDescriptor(
            ctx,
            interfaceDecl,
            interfaceDesc,
            IMPL_NAME,
            interfaceDesc.source,
            scope,
            Modality.FINAL,
            Visibilities.PUBLIC,
            Annotations.create(listOf(createDeprecatedHiddenAnnotation(interfaceDesc.module))),
            primaryCtorVisibility,
            ClassKind.CLASS,
            false
        )
        descriptor.initialize()
        return descriptor
    }

    fun addSerializerImplClass(
        thisDescriptor: ClassDescriptor,
        declarationProvider: ClassMemberDeclarationProvider,
        ctx: LazyClassContext
    ): ClassDescriptor {
        val thisDeclaration = declarationProvider.correspondingClassOrObject!!
        val scope = ctx.declarationScopeProvider.getResolutionScopeForDeclaration(declarationProvider.ownerInfo!!.scopeAnchor)
        val hasTypeParams = thisDescriptor.declaredTypeParameters.isNotEmpty()
        val serializerKind = if (hasTypeParams) ClassKind.CLASS else ClassKind.OBJECT
        val serializerDescriptor = SyntheticClassOrObjectDescriptor(
            ctx,
            thisDeclaration,
            thisDescriptor, SERIALIZER_CLASS_NAME, thisDescriptor.source,
            scope,
            Modality.FINAL, Visibilities.PUBLIC,
            Annotations.create(listOf(createDeprecatedHiddenAnnotation(thisDescriptor.module))),
            Visibilities.PRIVATE,
            serializerKind, false
        )
        val typeParameters: List<TypeParameterDescriptor> =
            thisDescriptor.declaredTypeParameters.mapIndexed { index, param ->
                TypeParameterDescriptorImpl.createWithDefaultBound(
                    serializerDescriptor, Annotations.EMPTY, false, Variance.INVARIANT,
                    param.name, index, LockBasedStorageManager.NO_LOCKS
                )
            }
        serializerDescriptor.initialize(typeParameters)
        val secondaryCtors =
            if (!hasTypeParams)
                emptyList()
            else
                listOf(createTypedSerializerConstructorDescriptor(serializerDescriptor, thisDescriptor, typeParameters))
        serializerDescriptor.secondaryConstructors = secondaryCtors
        return serializerDescriptor
    }

    fun generateSerializerProperties(
        thisDescriptor: ClassDescriptor,
        fromSupertypes: ArrayList<PropertyDescriptor>,
        name: Name,
        result: MutableSet<PropertyDescriptor>
    ) {
        val classDescriptor = getSerializableClassDescriptorBySerializer(thisDescriptor) ?: return

        // Do not auto-generate anything for user serializers
        if (!isAllowedToHaveAutoGeneratedSerializerMethods(thisDescriptor, classDescriptor)) return

        if (name == SerialEntityNames.SERIAL_DESC_FIELD_NAME && result.none(thisDescriptor::checkSerializableClassPropertyResult) &&
            fromSupertypes.none { thisDescriptor.checkSerializableClassPropertyResult(it) && it.modality == Modality.FINAL }
        ) {
            result.add(createSerializableClassPropertyDescriptor(thisDescriptor, classDescriptor))
        }
        // don't add local serializer fields if typed constructor is not synthetic
        if (classDescriptor.declaredTypeParameters.isNotEmpty() &&
            findSerializerConstructorForTypeArgumentsSerializers(thisDescriptor, onlyIfSynthetic = true) != null
        ) {
            result.addAll(createLocalSerializersFieldsDescriptor(name, classDescriptor, thisDescriptor))
        }
    }

    fun generateCompanionObjectMethods(
        thisDescriptor: ClassDescriptor,
        name: Name,
        result: MutableCollection<SimpleFunctionDescriptor>
    ) {
        val classDescriptor = getSerializableClassDescriptorByCompanion(thisDescriptor) ?: return

        if (name == SerialEntityNames.SERIALIZER_PROVIDER_NAME && result.none { it.valueParameters.size == classDescriptor.declaredTypeParameters.size }) {
            result.add(createSerializerGetterDescriptor(thisDescriptor, classDescriptor))
        }
    }

    fun generateSerializerMethods(
        thisDescriptor: ClassDescriptor,
        fromSupertypes: List<SimpleFunctionDescriptor>,
        name: Name,
        result: MutableCollection<SimpleFunctionDescriptor>
    ) {
        val classDescriptor = getSerializableClassDescriptorBySerializer(thisDescriptor) ?: return

        // Do not auto-generate anything for user serializers
        if (!isAllowedToHaveAutoGeneratedSerializerMethods(thisDescriptor, classDescriptor)) return

        fun shouldAddSerializerFunction(checkParameters: (FunctionDescriptor) -> Boolean): Boolean {
            // Add 'save' / 'load' iff there is no such declared member AND there is no such final member in supertypes
            return result.none(checkParameters) &&
                    fromSupertypes.none { checkParameters(it) && it.modality == Modality.FINAL }
        }

        val isSave = name == SerialEntityNames.SAVE_NAME &&
                shouldAddSerializerFunction { classDescriptor.checkSaveMethodParameters(it.valueParameters) }
        val isLoad = name == SerialEntityNames.LOAD_NAME &&
                shouldAddSerializerFunction { classDescriptor.checkLoadMethodParameters(it.valueParameters) }
        val isDescriptorGetter = name == SerialEntityNames.GENERATED_DESCRIPTOR_GETTER &&
                thisDescriptor.typeConstructor.supertypes.any(::isGeneratedKSerializer) &&
                shouldAddSerializerFunction { true /* TODO? */ }

        if (isSave || isLoad || isDescriptorGetter) {
            result.add(doCreateSerializerFunction(thisDescriptor, name))
        }
    }

    private fun createSerializableClassPropertyDescriptor(
        companionDescriptor: ClassDescriptor,
        classDescriptor: ClassDescriptor
    ): PropertyDescriptor =
        doCreateSerializerProperty(companionDescriptor, classDescriptor, SerialEntityNames.SERIAL_DESC_FIELD_NAME)

    private fun doCreateSerializerProperty(
        companionDescriptor: ClassDescriptor,
        classDescriptor: ClassDescriptor,
        name: Name
    ): PropertyDescriptor {
        val typeParam = listOf(createProjection(classDescriptor.defaultType, Variance.INVARIANT, null))
        val propertyFromSerializer = companionDescriptor.getGeneratedSerializerDescriptor().getMemberScope(typeParam)
            .getContributedVariables(name, NoLookupLocation.FROM_BUILTINS).single()

        val propertyDescriptor = PropertyDescriptorImpl.create(
            companionDescriptor, Annotations.EMPTY, Modality.OPEN, Visibilities.PUBLIC, false, name,
            CallableMemberDescriptor.Kind.SYNTHESIZED, companionDescriptor.source, false, false, false, false, false, false
        )

        val extensionReceiverParameter: ReceiverParameterDescriptor? = null // kludge to disambiguate call
        propertyDescriptor.setType(
            propertyFromSerializer.type,
            propertyFromSerializer.typeParameters,
            companionDescriptor.thisAsReceiverParameter,
            extensionReceiverParameter
        )

        val propertyGetter = PropertyGetterDescriptorImpl(
            propertyDescriptor, Annotations.EMPTY, Modality.OPEN, Visibilities.PUBLIC, false, false, false,
            CallableMemberDescriptor.Kind.SYNTHESIZED, null, companionDescriptor.source
        )

        propertyGetter.initialize(propertyFromSerializer.type)
        propertyDescriptor.initialize(propertyGetter, null)
        propertyDescriptor.overriddenDescriptors = listOf(propertyFromSerializer)

        return propertyDescriptor
    }

    private fun doCreateSerializerFunction(
        companionDescriptor: ClassDescriptor,
        name: Name
    ): SimpleFunctionDescriptor {
        val functionDescriptor = SimpleFunctionDescriptorImpl.create(
            companionDescriptor, Annotations.EMPTY, name, CallableMemberDescriptor.Kind.SYNTHESIZED, companionDescriptor.source
        )

        val serializableClassOnImplSite = extractKSerializerArgumentFromImplementation(companionDescriptor)
            ?: throw AssertionError("Serializer does not implement ${SerialEntityNames.KSERIALIZER_CLASS}??")

        val typeParam = listOf(createProjection(serializableClassOnImplSite, Variance.INVARIANT, null))
        val functionFromSerializer = companionDescriptor.getGeneratedSerializerDescriptor().getMemberScope(typeParam)
            .getContributedFunctions(name, NoLookupLocation.FROM_BUILTINS).single()

        functionDescriptor.initialize(
            null,
            companionDescriptor.thisAsReceiverParameter,
            functionFromSerializer.typeParameters,
            functionFromSerializer.valueParameters.map { it.copy(functionDescriptor, it.name, it.index) },
            functionFromSerializer.returnType,
            Modality.OPEN,
            Visibilities.PUBLIC
        )

        return functionDescriptor
    }

    fun createLoadConstructorDescriptor(
        classDescriptor: ClassDescriptor,
        bindingContext: BindingContext
    ): ClassConstructorDescriptor {
        if (!classDescriptor.isInternalSerializable) throw IllegalArgumentException()

        val functionDescriptor = ClassConstructorDescriptorImpl.createSynthesized(
            classDescriptor,
            Annotations.create(listOf(createDeprecatedHiddenAnnotation(classDescriptor.module))),
            false,
            SourceElement.NO_SOURCE
        )

        val markerDesc = classDescriptor.getKSerializerConstructorMarker()
        val markerType = markerDesc.toSimpleType(nullable = true)

        val serializableProperties = bindingContext.serializablePropertiesFor(classDescriptor).serializableProperties
        val parameterDescsAsProps = serializableProperties.map { it.descriptor }
        val bitMaskSlotsCount = serializableProperties.bitMaskSlotCount()
        var i = 0
        val consParams = mutableListOf<ValueParameterDescriptor>()
        repeat(bitMaskSlotsCount) {
            consParams.add(
                ValueParameterDescriptorImpl(
                    functionDescriptor, null, i++, Annotations.EMPTY, Name.identifier("seen$i"), functionDescriptor.builtIns.intType, false,
                    false, false, null, functionDescriptor.source
                )
            )
        }
        for (prop in parameterDescsAsProps) {
            consParams.add(
                ValueParameterDescriptorImpl(
                    functionDescriptor, null, i++, prop.annotations, prop.name, prop.type.makeNullableIfNotPrimitive(), false, false,
                    false, null, functionDescriptor.source
                )
            )
        }
        consParams.add(
            ValueParameterDescriptorImpl(
                functionDescriptor, null, i, Annotations.EMPTY, SerialEntityNames.dummyParamName, markerType, false,
                false, false, null, functionDescriptor.source
            )
        )

        functionDescriptor.initialize(
            consParams,
            Visibilities.PUBLIC
        )

        functionDescriptor.returnType = classDescriptor.defaultType
        return functionDescriptor
    }

    private fun createTypedSerializerConstructorDescriptor(
        classDescriptor: ClassDescriptor,
        serializableDescriptor: ClassDescriptor,
        typeParameters: List<TypeParameterDescriptor>
    ): ClassConstructorDescriptor {
        val constrDesc = ClassConstructorDescriptorImpl.createSynthesized(
            classDescriptor,
            Annotations.create(listOf(createDeprecatedHiddenAnnotation(classDescriptor.module))),
            false,
            classDescriptor.source
        )
        val serializerClass = classDescriptor.getClassFromSerializationPackage(SerialEntityNames.KSERIALIZER_CLASS)
        assert(serializableDescriptor.declaredTypeParameters.size == typeParameters.size)
        val args = List(serializableDescriptor.declaredTypeParameters.size) { index ->
            val pType = KotlinTypeFactory.simpleNotNullType(
                Annotations.EMPTY,
                serializerClass,
                listOf(TypeProjectionImpl(typeParameters[index].defaultType))
            )

            ValueParameterDescriptorImpl(
                constrDesc, null, index, Annotations.EMPTY, Name.identifier("$typeArgPrefix$index"), pType,
                false, false, false, null, constrDesc.source
            )
        }

        constrDesc.initialize(args, Visibilities.PUBLIC, typeParameters)
        constrDesc.returnType = classDescriptor.defaultType
        return constrDesc
    }

    /**
     * Creates free type parameters T0, T1, ... for given serializable class
     * Returns [T0, T1, ...] and [KSerializer<T0>, KSerializer<T1>,...]
     */
    private fun createKSerializerParamsForEachGenericArgument(
        parentFunction: FunctionDescriptor,
        serializableClass: ClassDescriptor,
        actualArgsOffset: Int = 0
    ): Pair<List<TypeParameterDescriptor>, List<ValueParameterDescriptor>> {
        val serializerClass = serializableClass.getClassFromSerializationPackage(SerialEntityNames.KSERIALIZER_CLASS)
        val args = mutableListOf<ValueParameterDescriptor>()
        val typeArgs = mutableListOf<TypeParameterDescriptor>()
        var i = 0

        serializableClass.declaredTypeParameters.forEach { _ ->
            val targ = TypeParameterDescriptorImpl.createWithDefaultBound(
                parentFunction, Annotations.EMPTY, false, Variance.INVARIANT,
                Name.identifier("T$i"), i, LockBasedStorageManager.NO_LOCKS
            )

            val pType =
                KotlinTypeFactory.simpleNotNullType(Annotations.EMPTY, serializerClass, listOf(TypeProjectionImpl(targ.defaultType)))

            args.add(
                ValueParameterDescriptorImpl(
                    containingDeclaration = parentFunction,
                    original = null,
                    index = actualArgsOffset + i,
                    annotations = Annotations.EMPTY,
                    name = Name.identifier("$typeArgPrefix$i"),
                    outType = pType,
                    declaresDefaultValue = false,
                    isCrossinline = false,
                    isNoinline = false,
                    varargElementType = null,
                    source = parentFunction.source
                )
            )

            typeArgs.add(targ)
            i++
        }

        return typeArgs to args
    }

    private fun createSerializerGetterDescriptor(thisClass: ClassDescriptor, serializableClass: ClassDescriptor): SimpleFunctionDescriptor {
        val f = SimpleFunctionDescriptorImpl.create(
            thisClass,
            Annotations.EMPTY,
            SerialEntityNames.SERIALIZER_PROVIDER_NAME,
            CallableMemberDescriptor.Kind.SYNTHESIZED,
            thisClass.source
        )
        val serializerClass = thisClass.getClassFromSerializationPackage(SerialEntityNames.KSERIALIZER_CLASS)

        val (typeArgs, args) = createKSerializerParamsForEachGenericArgument(f, serializableClass)

        val newSerializableType =
            KotlinTypeFactory.simpleNotNullType(Annotations.EMPTY, serializableClass, typeArgs.map { TypeProjectionImpl(it.defaultType) })
        val serialReturnType =
            KotlinTypeFactory.simpleNotNullType(Annotations.EMPTY, serializerClass, listOf(TypeProjectionImpl(newSerializableType)))

        f.initialize(null, thisClass.thisAsReceiverParameter, typeArgs, args, serialReturnType, Modality.FINAL, Visibilities.PUBLIC)
        return f
    }


    private fun KotlinType.makeNullableIfNotPrimitive() =
        if (KotlinBuiltIns.isPrimitiveType(this)) this
        else this.makeNullable()

    fun createWriteSelfFunctionDescriptor(thisClass: ClassDescriptor): FunctionDescriptor {
        val jvmStaticClass = thisClass.module.findClassAcrossModuleDependencies(
            ClassId(
                FqName("kotlin.jvm"),
                Name.identifier("JvmStatic")
            )
        )!!
        val jvmStaticAnnotation = AnnotationDescriptorImpl(jvmStaticClass.defaultType, mapOf(), jvmStaticClass.source)
        val annotations = Annotations.create(listOf(jvmStaticAnnotation))

        val f = SimpleFunctionDescriptorImpl.create(
            thisClass,
            annotations,
            SerialEntityNames.WRITE_SELF_NAME,
            CallableMemberDescriptor.Kind.SYNTHESIZED,
            thisClass.source
        )
        val returnType = f.builtIns.unitType

        val (typeArgs, argsKSer) = createKSerializerParamsForEachGenericArgument(f, thisClass, actualArgsOffset = 3)

        val args = mutableListOf<ValueParameterDescriptor>()

        // object
        val objectType =
            KotlinTypeFactory.simpleNotNullType(Annotations.EMPTY, thisClass, typeArgs.map { TypeProjectionImpl(it.defaultType) })
        args.add(
            ValueParameterDescriptorImpl(
                containingDeclaration = f,
                original = null,
                index = 0,
                annotations = Annotations.EMPTY,
                name = Name.identifier("self"),
                outType = objectType,
                declaresDefaultValue = false,
                isCrossinline = false,
                isNoinline = false,
                varargElementType = null,
                source = f.source
            )
        )

        // encoder
        args.add(
            ValueParameterDescriptorImpl(
                containingDeclaration = f,
                original = null,
                index = 1,
                annotations = Annotations.EMPTY,
                name = Name.identifier("output"),
                outType = thisClass.getClassFromSerializationPackage(SerialEntityNames.STRUCTURE_ENCODER_CLASS).toSimpleType(false),
                declaresDefaultValue = false,
                isCrossinline = false,
                isNoinline = false,
                varargElementType = null,
                source = f.source
            )
        )

        //descriptor
        args.add(
            ValueParameterDescriptorImpl(
                containingDeclaration = f,
                original = null,
                index = 2,
                annotations = Annotations.EMPTY,
                name = Name.identifier("serialDesc"),
                outType = thisClass.getClassFromSerializationPackage(SerialEntityNames.SERIAL_DESCRIPTOR_CLASS).toSimpleType(false),
                declaresDefaultValue = false,
                isCrossinline = false,
                isNoinline = false,
                varargElementType = null,
                source = f.source
            )
        )

        args.addAll(argsKSer)

        f.initialize(
            null,
            thisClass.thisAsReceiverParameter,
            typeArgs,
            args,
            returnType,
            Modality.FINAL,
            Visibilities.PUBLIC
        )

        return f
    }

    fun generateDescriptorsForAnnotationImpl(
        thisDescriptor: ClassDescriptor,
        fromSupertypes: List<PropertyDescriptor>,
        result: MutableCollection<PropertyDescriptor>
    ) {
        if (isSerialInfoImpl(thisDescriptor)) {
            result.add(
                fromSupertypes[0].copy(
                    thisDescriptor,
                    Modality.FINAL,
                    Visibilities.PUBLIC,
                    CallableMemberDescriptor.Kind.SYNTHESIZED,
                    true
                ) as PropertyDescriptor
            )
        }
    }

    // create properties typeSerial0, typeSerial1, etc... for storing generic arguments' serializers
    private fun createLocalSerializersFieldsDescriptor(
        name: Name,
        serializableDescriptor: ClassDescriptor,
        serializerDescriptor: ClassDescriptor
    ): List<PropertyDescriptor> {
        if (serializableDescriptor.declaredTypeParameters.isEmpty()) return emptyList()
        val serializerClass = serializableDescriptor.getClassFromSerializationPackage(SerialEntityNames.KSERIALIZER_CLASS)
        val index = name.identifier.removePrefix(typeArgPrefix).toIntOrNull() ?: return emptyList()
        val param = serializerDescriptor.declaredTypeParameters[index]
        val pType =
            KotlinTypeFactory.simpleNotNullType(
                Annotations.EMPTY,
                serializerClass,
                listOf(TypeProjectionImpl(param.defaultType))
            )
        val desc = SimpleSyntheticPropertyDescriptor(serializerDescriptor, "$typeArgPrefix$index", pType)
        return listOf(desc)
    }
}
