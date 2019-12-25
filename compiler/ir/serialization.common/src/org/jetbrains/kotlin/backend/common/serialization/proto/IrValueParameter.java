// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: compiler/ir/serialization.common/src/KotlinIr.proto

package org.jetbrains.kotlin.backend.common.serialization.proto;

/**
 * Protobuf type {@code org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter}
 */
public final class IrValueParameter extends
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite implements
    // @@protoc_insertion_point(message_implements:org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter)
    IrValueParameterOrBuilder {
  // Use IrValueParameter.newBuilder() to construct.
  private IrValueParameter(org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder builder) {
    super(builder);
    this.unknownFields = builder.getUnknownFields();
  }
  private IrValueParameter(boolean noInit) { this.unknownFields = org.jetbrains.kotlin.protobuf.ByteString.EMPTY;}

  private static final IrValueParameter defaultInstance;
  public static IrValueParameter getDefaultInstance() {
    return defaultInstance;
  }

  public IrValueParameter getDefaultInstanceForType() {
    return defaultInstance;
  }

  private final org.jetbrains.kotlin.protobuf.ByteString unknownFields;
  private IrValueParameter(
      org.jetbrains.kotlin.protobuf.CodedInputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    initFields();
    int mutable_bitField0_ = 0;
    org.jetbrains.kotlin.protobuf.ByteString.Output unknownFieldsOutput =
        org.jetbrains.kotlin.protobuf.ByteString.newOutput();
    org.jetbrains.kotlin.protobuf.CodedOutputStream unknownFieldsCodedOutput =
        org.jetbrains.kotlin.protobuf.CodedOutputStream.newInstance(
            unknownFieldsOutput, 1);
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownField(input, unknownFieldsCodedOutput,
                                   extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.Builder subBuilder = null;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
              subBuilder = base_.toBuilder();
            }
            base_ = input.readMessage(org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.PARSER, extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(base_);
              base_ = subBuilder.buildPartial();
            }
            bitField0_ |= 0x00000001;
            break;
          }
          case 16: {
            bitField0_ |= 0x00000002;
            name_ = input.readInt32();
            break;
          }
          case 24: {
            bitField0_ |= 0x00000004;
            index_ = input.readInt32();
            break;
          }
          case 32: {
            bitField0_ |= 0x00000008;
            type_ = input.readInt32();
            break;
          }
          case 40: {
            bitField0_ |= 0x00000010;
            varargElementType_ = input.readInt32();
            break;
          }
          case 48: {
            bitField0_ |= 0x00000020;
            isCrossinline_ = input.readBool();
            break;
          }
          case 56: {
            bitField0_ |= 0x00000040;
            isNoinline_ = input.readBool();
            break;
          }
          case 64: {
            bitField0_ |= 0x00000080;
            defaultValue_ = input.readInt32();
            break;
          }
        }
      }
    } catch (org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException(
          e.getMessage()).setUnfinishedMessage(this);
    } finally {
      try {
        unknownFieldsCodedOutput.flush();
      } catch (java.io.IOException e) {
      // Should not happen
      } finally {
        unknownFields = unknownFieldsOutput.toByteString();
      }
      makeExtensionsImmutable();
    }
  }
  public static org.jetbrains.kotlin.protobuf.Parser<IrValueParameter> PARSER =
      new org.jetbrains.kotlin.protobuf.AbstractParser<IrValueParameter>() {
    public IrValueParameter parsePartialFrom(
        org.jetbrains.kotlin.protobuf.CodedInputStream input,
        org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
        throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
      return new IrValueParameter(input, extensionRegistry);
    }
  };

  @java.lang.Override
  public org.jetbrains.kotlin.protobuf.Parser<IrValueParameter> getParserForType() {
    return PARSER;
  }

  private int bitField0_;
  public static final int BASE_FIELD_NUMBER = 1;
  private org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base_;
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
   */
  public boolean hasBase() {
    return ((bitField0_ & 0x00000001) == 0x00000001);
  }
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
   */
  public org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase getBase() {
    return base_;
  }

  public static final int NAME_FIELD_NUMBER = 2;
  private int name_;
  /**
   * <code>required int32 name = 2;</code>
   */
  public boolean hasName() {
    return ((bitField0_ & 0x00000002) == 0x00000002);
  }
  /**
   * <code>required int32 name = 2;</code>
   */
  public int getName() {
    return name_;
  }

  public static final int INDEX_FIELD_NUMBER = 3;
  private int index_;
  /**
   * <code>required int32 index = 3;</code>
   */
  public boolean hasIndex() {
    return ((bitField0_ & 0x00000004) == 0x00000004);
  }
  /**
   * <code>required int32 index = 3;</code>
   */
  public int getIndex() {
    return index_;
  }

  public static final int TYPE_FIELD_NUMBER = 4;
  private int type_;
  /**
   * <code>required int32 type = 4;</code>
   */
  public boolean hasType() {
    return ((bitField0_ & 0x00000008) == 0x00000008);
  }
  /**
   * <code>required int32 type = 4;</code>
   */
  public int getType() {
    return type_;
  }

  public static final int VARARG_ELEMENT_TYPE_FIELD_NUMBER = 5;
  private int varargElementType_;
  /**
   * <code>optional int32 vararg_element_type = 5;</code>
   */
  public boolean hasVarargElementType() {
    return ((bitField0_ & 0x00000010) == 0x00000010);
  }
  /**
   * <code>optional int32 vararg_element_type = 5;</code>
   */
  public int getVarargElementType() {
    return varargElementType_;
  }

  public static final int IS_CROSSINLINE_FIELD_NUMBER = 6;
  private boolean isCrossinline_;
  /**
   * <code>required bool is_crossinline = 6;</code>
   */
  public boolean hasIsCrossinline() {
    return ((bitField0_ & 0x00000020) == 0x00000020);
  }
  /**
   * <code>required bool is_crossinline = 6;</code>
   */
  public boolean getIsCrossinline() {
    return isCrossinline_;
  }

  public static final int IS_NOINLINE_FIELD_NUMBER = 7;
  private boolean isNoinline_;
  /**
   * <code>required bool is_noinline = 7;</code>
   */
  public boolean hasIsNoinline() {
    return ((bitField0_ & 0x00000040) == 0x00000040);
  }
  /**
   * <code>required bool is_noinline = 7;</code>
   */
  public boolean getIsNoinline() {
    return isNoinline_;
  }

  public static final int DEFAULT_VALUE_FIELD_NUMBER = 8;
  private int defaultValue_;
  /**
   * <code>optional int32 default_value = 8;</code>
   */
  public boolean hasDefaultValue() {
    return ((bitField0_ & 0x00000080) == 0x00000080);
  }
  /**
   * <code>optional int32 default_value = 8;</code>
   */
  public int getDefaultValue() {
    return defaultValue_;
  }

  private void initFields() {
    base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();
    name_ = 0;
    index_ = 0;
    type_ = 0;
    varargElementType_ = 0;
    isCrossinline_ = false;
    isNoinline_ = false;
    defaultValue_ = 0;
  }
  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    if (!hasBase()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasName()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasIndex()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasType()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasIsCrossinline()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasIsNoinline()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!getBase().isInitialized()) {
      memoizedIsInitialized = 0;
      return false;
    }
    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(org.jetbrains.kotlin.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      output.writeMessage(1, base_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      output.writeInt32(2, name_);
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      output.writeInt32(3, index_);
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      output.writeInt32(4, type_);
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      output.writeInt32(5, varargElementType_);
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      output.writeBool(6, isCrossinline_);
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      output.writeBool(7, isNoinline_);
    }
    if (((bitField0_ & 0x00000080) == 0x00000080)) {
      output.writeInt32(8, defaultValue_);
    }
    output.writeRawBytes(unknownFields);
  }

  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeMessageSize(1, base_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(2, name_);
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(3, index_);
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(4, type_);
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(5, varargElementType_);
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeBoolSize(6, isCrossinline_);
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeBoolSize(7, isNoinline_);
    }
    if (((bitField0_ & 0x00000080) == 0x00000080)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(8, defaultValue_);
    }
    size += unknownFields.size();
    memoizedSerializedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  protected java.lang.Object writeReplace()
      throws java.io.ObjectStreamException {
    return super.writeReplace();
  }

  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(
      org.jetbrains.kotlin.protobuf.ByteString data)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(
      org.jetbrains.kotlin.protobuf.ByteString data,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(byte[] data)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(
      byte[] data,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(
      java.io.InputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseDelimitedFrom(
      java.io.InputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(
      org.jetbrains.kotlin.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parseFrom(
      org.jetbrains.kotlin.protobuf.CodedInputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }

  public static Builder newBuilder() { return Builder.create(); }
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder(org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter prototype) {
    return newBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() { return newBuilder(this); }

  /**
   * Protobuf type {@code org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter}
   */
  public static final class Builder extends
      org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder<
        org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter)
      org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder {
    // Construct using org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
    }
    private static Builder create() {
      return new Builder();
    }

    public Builder clear() {
      super.clear();
      base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();
      bitField0_ = (bitField0_ & ~0x00000001);
      name_ = 0;
      bitField0_ = (bitField0_ & ~0x00000002);
      index_ = 0;
      bitField0_ = (bitField0_ & ~0x00000004);
      type_ = 0;
      bitField0_ = (bitField0_ & ~0x00000008);
      varargElementType_ = 0;
      bitField0_ = (bitField0_ & ~0x00000010);
      isCrossinline_ = false;
      bitField0_ = (bitField0_ & ~0x00000020);
      isNoinline_ = false;
      bitField0_ = (bitField0_ & ~0x00000040);
      defaultValue_ = 0;
      bitField0_ = (bitField0_ & ~0x00000080);
      return this;
    }

    public Builder clone() {
      return create().mergeFrom(buildPartial());
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter getDefaultInstanceForType() {
      return org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter.getDefaultInstance();
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter build() {
      org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter buildPartial() {
      org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter result = new org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
        to_bitField0_ |= 0x00000001;
      }
      result.base_ = base_;
      if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
        to_bitField0_ |= 0x00000002;
      }
      result.name_ = name_;
      if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
        to_bitField0_ |= 0x00000004;
      }
      result.index_ = index_;
      if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
        to_bitField0_ |= 0x00000008;
      }
      result.type_ = type_;
      if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
        to_bitField0_ |= 0x00000010;
      }
      result.varargElementType_ = varargElementType_;
      if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
        to_bitField0_ |= 0x00000020;
      }
      result.isCrossinline_ = isCrossinline_;
      if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
        to_bitField0_ |= 0x00000040;
      }
      result.isNoinline_ = isNoinline_;
      if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
        to_bitField0_ |= 0x00000080;
      }
      result.defaultValue_ = defaultValue_;
      result.bitField0_ = to_bitField0_;
      return result;
    }

    public Builder mergeFrom(org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter other) {
      if (other == org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter.getDefaultInstance()) return this;
      if (other.hasBase()) {
        mergeBase(other.getBase());
      }
      if (other.hasName()) {
        setName(other.getName());
      }
      if (other.hasIndex()) {
        setIndex(other.getIndex());
      }
      if (other.hasType()) {
        setType(other.getType());
      }
      if (other.hasVarargElementType()) {
        setVarargElementType(other.getVarargElementType());
      }
      if (other.hasIsCrossinline()) {
        setIsCrossinline(other.getIsCrossinline());
      }
      if (other.hasIsNoinline()) {
        setIsNoinline(other.getIsNoinline());
      }
      if (other.hasDefaultValue()) {
        setDefaultValue(other.getDefaultValue());
      }
      setUnknownFields(
          getUnknownFields().concat(other.unknownFields));
      return this;
    }

    public final boolean isInitialized() {
      if (!hasBase()) {
        
        return false;
      }
      if (!hasName()) {
        
        return false;
      }
      if (!hasIndex()) {
        
        return false;
      }
      if (!hasType()) {
        
        return false;
      }
      if (!hasIsCrossinline()) {
        
        return false;
      }
      if (!hasIsNoinline()) {
        
        return false;
      }
      if (!getBase().isInitialized()) {
        
        return false;
      }
      return true;
    }

    public Builder mergeFrom(
        org.jetbrains.kotlin.protobuf.CodedInputStream input,
        org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter) e.getUnfinishedMessage();
        throw e;
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public boolean hasBase() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase getBase() {
      return base_;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder setBase(org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase value) {
      if (value == null) {
        throw new NullPointerException();
      }
      base_ = value;

      bitField0_ |= 0x00000001;
      return this;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder setBase(
        org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.Builder builderForValue) {
      base_ = builderForValue.build();

      bitField0_ |= 0x00000001;
      return this;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder mergeBase(org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase value) {
      if (((bitField0_ & 0x00000001) == 0x00000001) &&
          base_ != org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance()) {
        base_ =
          org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.newBuilder(base_).mergeFrom(value).buildPartial();
      } else {
        base_ = value;
      }

      bitField0_ |= 0x00000001;
      return this;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder clearBase() {
      base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();

      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    private int name_ ;
    /**
     * <code>required int32 name = 2;</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int32 name = 2;</code>
     */
    public int getName() {
      return name_;
    }
    /**
     * <code>required int32 name = 2;</code>
     */
    public Builder setName(int value) {
      bitField0_ |= 0x00000002;
      name_ = value;
      
      return this;
    }
    /**
     * <code>required int32 name = 2;</code>
     */
    public Builder clearName() {
      bitField0_ = (bitField0_ & ~0x00000002);
      name_ = 0;
      
      return this;
    }

    private int index_ ;
    /**
     * <code>required int32 index = 3;</code>
     */
    public boolean hasIndex() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int32 index = 3;</code>
     */
    public int getIndex() {
      return index_;
    }
    /**
     * <code>required int32 index = 3;</code>
     */
    public Builder setIndex(int value) {
      bitField0_ |= 0x00000004;
      index_ = value;
      
      return this;
    }
    /**
     * <code>required int32 index = 3;</code>
     */
    public Builder clearIndex() {
      bitField0_ = (bitField0_ & ~0x00000004);
      index_ = 0;
      
      return this;
    }

    private int type_ ;
    /**
     * <code>required int32 type = 4;</code>
     */
    public boolean hasType() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required int32 type = 4;</code>
     */
    public int getType() {
      return type_;
    }
    /**
     * <code>required int32 type = 4;</code>
     */
    public Builder setType(int value) {
      bitField0_ |= 0x00000008;
      type_ = value;
      
      return this;
    }
    /**
     * <code>required int32 type = 4;</code>
     */
    public Builder clearType() {
      bitField0_ = (bitField0_ & ~0x00000008);
      type_ = 0;
      
      return this;
    }

    private int varargElementType_ ;
    /**
     * <code>optional int32 vararg_element_type = 5;</code>
     */
    public boolean hasVarargElementType() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>optional int32 vararg_element_type = 5;</code>
     */
    public int getVarargElementType() {
      return varargElementType_;
    }
    /**
     * <code>optional int32 vararg_element_type = 5;</code>
     */
    public Builder setVarargElementType(int value) {
      bitField0_ |= 0x00000010;
      varargElementType_ = value;
      
      return this;
    }
    /**
     * <code>optional int32 vararg_element_type = 5;</code>
     */
    public Builder clearVarargElementType() {
      bitField0_ = (bitField0_ & ~0x00000010);
      varargElementType_ = 0;
      
      return this;
    }

    private boolean isCrossinline_ ;
    /**
     * <code>required bool is_crossinline = 6;</code>
     */
    public boolean hasIsCrossinline() {
      return ((bitField0_ & 0x00000020) == 0x00000020);
    }
    /**
     * <code>required bool is_crossinline = 6;</code>
     */
    public boolean getIsCrossinline() {
      return isCrossinline_;
    }
    /**
     * <code>required bool is_crossinline = 6;</code>
     */
    public Builder setIsCrossinline(boolean value) {
      bitField0_ |= 0x00000020;
      isCrossinline_ = value;
      
      return this;
    }
    /**
     * <code>required bool is_crossinline = 6;</code>
     */
    public Builder clearIsCrossinline() {
      bitField0_ = (bitField0_ & ~0x00000020);
      isCrossinline_ = false;
      
      return this;
    }

    private boolean isNoinline_ ;
    /**
     * <code>required bool is_noinline = 7;</code>
     */
    public boolean hasIsNoinline() {
      return ((bitField0_ & 0x00000040) == 0x00000040);
    }
    /**
     * <code>required bool is_noinline = 7;</code>
     */
    public boolean getIsNoinline() {
      return isNoinline_;
    }
    /**
     * <code>required bool is_noinline = 7;</code>
     */
    public Builder setIsNoinline(boolean value) {
      bitField0_ |= 0x00000040;
      isNoinline_ = value;
      
      return this;
    }
    /**
     * <code>required bool is_noinline = 7;</code>
     */
    public Builder clearIsNoinline() {
      bitField0_ = (bitField0_ & ~0x00000040);
      isNoinline_ = false;
      
      return this;
    }

    private int defaultValue_ ;
    /**
     * <code>optional int32 default_value = 8;</code>
     */
    public boolean hasDefaultValue() {
      return ((bitField0_ & 0x00000080) == 0x00000080);
    }
    /**
     * <code>optional int32 default_value = 8;</code>
     */
    public int getDefaultValue() {
      return defaultValue_;
    }
    /**
     * <code>optional int32 default_value = 8;</code>
     */
    public Builder setDefaultValue(int value) {
      bitField0_ |= 0x00000080;
      defaultValue_ = value;
      
      return this;
    }
    /**
     * <code>optional int32 default_value = 8;</code>
     */
    public Builder clearDefaultValue() {
      bitField0_ = (bitField0_ & ~0x00000080);
      defaultValue_ = 0;
      
      return this;
    }

    // @@protoc_insertion_point(builder_scope:org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter)
  }

  static {
    defaultInstance = new IrValueParameter(true);
    defaultInstance.initFields();
  }

  // @@protoc_insertion_point(class_scope:org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter)
}
