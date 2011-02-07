package com.linkare.rec.data.acquisition;

import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.impl.data.PhysicsValueUtil;

public final class PhysicsVal implements IDLEntity {
	// Discriminator
	private com.linkare.rec.data.acquisition.PhysicsValueType discriminator;

	// Union member boolean_value
	private boolean _boolean_value;

	// Union member byte_value
	private byte _byte_value;

	// Union member short_value
	private short _short_value;

	// Union member int_value
	private int _int_value;

	// Union member long_value
	private long _long_value;

	// Union member float_value
	private float _float_value;

	// Union member double_value
	private double _double_value;

	// Union member byte_array_value
	private ByteArrayValue _byte_array_value;

	/** Default Constructor */
	public PhysicsVal() {
	}

	/* Copy constructor */
	public PhysicsVal(PhysicsVal other) {
		switch (other.getDiscriminator().value()) {
		case com.linkare.rec.data.acquisition.PhysicsValueType._BooleanVal:
			setBooleanValue(other.isBooleanValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._ByteVal:
			setByteValue(other.getByteValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._ShortVal:
			setShortValue(other.getShortValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._IntVal:
			setIntValue(other.getIntValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._LongVal:
			setLongValue(other.getLongValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._FloatVal:
			setFloatValue(other.getFloatValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._DoubleVal:
			setDoubleValue(other.getDoubleValue());
			break;
		case com.linkare.rec.data.acquisition.PhysicsValueType._ByteArrayVal:
			copyByteArrayValue(other.getByteArrayValue());
			break;
		default:
			setDefault();
			break;
		}
	}

	//
	// Get discriminator value
	//
	public com.linkare.rec.data.acquisition.PhysicsValueType getDiscriminator() {
		return discriminator;
	}

	//
	// Set discriminator value
	//
	public void setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType discriminator) {
		this.discriminator = discriminator;
	}

	//
	// default access
	//
	public void getDefault() {
	}

	//
	// default access
	//
	public void setDefault() {
	}

	/**
	 * Getter for property booleanValue.
	 * 
	 * @return Value of property booleanValue.
	 */
	public boolean isBooleanValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .BooleanVal) throw new RuntimeException("Invalid type request");
		 */

		return this._boolean_value;
	}

	/**
	 * Setter for property booleanValue.
	 * 
	 * @param booleanValue New value of property booleanValue.
	 */
	public void setBooleanValue(boolean _boolean_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.BooleanVal);
		this._boolean_value = _boolean_value;
	}

	/**
	 * Getter for property byteValue.
	 * 
	 * @return Value of property byteValue.
	 */
	public byte getByteValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .ByteVal) throw new RuntimeException("Invalid type request");
		 */

		return this._byte_value;
	}

	/**
	 * Setter for property byteValue.
	 * 
	 * @param byteValue New value of property byteValue.
	 */
	public void setByteValue(byte _byte_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.ByteVal);
		this._byte_value = _byte_value;
	}

	/**
	 * Getter for property shortValue.
	 * 
	 * @return Value of property shortValue.
	 */
	public short getShortValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .ShortVal) throw new RuntimeException("Invalid type request");
		 */

		return this._short_value;
	}

	/**
	 * Setter for property shortValue.
	 * 
	 * @param shortValue New value of property shortValue.
	 */
	public void setShortValue(short _short_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.ShortVal);
		this._short_value = _short_value;
	}

	/**
	 * Getter for property intValue.
	 * 
	 * @return Value of property intValue.
	 */
	public int getIntValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .IntVal) throw new RuntimeException("Invalid type request");
		 */

		return this._int_value;
	}

	/**
	 * Setter for property intValue.
	 * 
	 * @param intValue New value of property intValue.
	 */
	public void setIntValue(int _int_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.IntVal);
		this._int_value = _int_value;
	}

	/**
	 * Getter for property longValue.
	 * 
	 * @return Value of property longValue.
	 */
	public long getLongValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .LongVal) throw new RuntimeException("Invalid type request");
		 */

		return this._long_value;
	}

	/**
	 * Setter for property longValue.
	 * 
	 * @param longValue New value of property longValue.
	 */
	public void setLongValue(long _long_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.LongVal);
		this._long_value = _long_value;
	}

	/**
	 * Getter for property floatValue.
	 * 
	 * @return Value of property floatValue.
	 */
	public float getFloatValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .FloatVal) throw new RuntimeException("Invalid type request");
		 */

		return this._float_value;
	}

	/**
	 * Setter for property floatValue.
	 * 
	 * @param floatValue New value of property floatValue.
	 */
	public void setFloatValue(float _float_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.FloatVal);
		this._float_value = _float_value;
	}

	/**
	 * Getter for property doubleValue.
	 * 
	 * @return Value of property doubleValue.
	 */
	public double getDoubleValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .DoubleVal) throw new RuntimeException("Invalid type request");
		 */

		return this._double_value;
	}

	/**
	 * Setter for property doubleValue.
	 * 
	 * @param doubleValue New value of property doubleValue.
	 */
	public void setDoubleValue(double _double_value) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.DoubleVal);
		this._double_value = _double_value;
	}

	/**
	 * Getter for property byteArrayValue.
	 * 
	 * @return Value of property byteArrayValue.
	 */
	public ByteArrayValue getByteArrayValue() {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .ByteArrayVal) throw new RuntimeException("Invalid type request");
		 */
		return this._byte_array_value;
	}

	/**
	 * Setter for property byteArrayValue.
	 * 
	 * @param byteArray New value of property byteArrayValue.
	 */
	public void setByteArrayValue(ByteArrayValue newValue) {
		/*
		 * if(getDiscriminator()!=com.linkare.rec.data.acquisition.PhysicsValueType
		 * .ByteArrayVal) throw new RuntimeException("Invalid type request");
		 */
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.ByteArrayVal);
		this._byte_array_value = newValue;
	}

	/**
	 * private copy method for a ByteArrayValue
	 * 
	 * @param byteArray New value of property byteArrayValue.
	 */
	private void copyByteArrayValue(ByteArrayValue byteArray) {
		setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueType.ByteArrayVal);
		if (byteArray == null) {
			this._byte_array_value = null;
			return;
		}

		this._byte_array_value = new ByteArrayValue(byteArray);

	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PhysicsVal))
			return false;

		PhysicsVal other = (PhysicsVal) obj;

		if (other.getDiscriminator().getValue() != getDiscriminator().getValue())
			return false;

		switch (other.getDiscriminator().value()) {
		case com.linkare.rec.data.acquisition.PhysicsValueType._BooleanVal:
			return other.isBooleanValue() == isBooleanValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._ByteVal:
			return other.getByteValue() == getByteValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._ShortVal:
			return other.getShortValue() == getShortValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._IntVal:
			return other.getIntValue() == getIntValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._LongVal:
			return other.getLongValue() == getLongValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._FloatVal:
			return other.getFloatValue() == getFloatValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._DoubleVal:
			return other.getDoubleValue() == getDoubleValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._ByteArrayVal:
			return other.getByteArrayValue().equals(getByteArrayValue());
		default:
			return false;
		}

	}

	public String toString() {
		switch (getDiscriminator().value()) {
		case com.linkare.rec.data.acquisition.PhysicsValueType._BooleanVal:
			return isBooleanValue() ? "true" : "false";
		case com.linkare.rec.data.acquisition.PhysicsValueType._ByteVal:
			return "" + getByteValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._ShortVal:
			return "" + getShortValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._IntVal:
			return "" + getIntValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._LongVal:
			return "" + getLongValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._FloatVal:
			return "" + getFloatValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._DoubleVal:
			return "" + getDoubleValue();
		case com.linkare.rec.data.acquisition.PhysicsValueType._ByteArrayVal:
			return "Object: mimetype=" + getByteArrayValue().getMimeType();
		default:
			return "";
		}
	}

	public double toDouble() {
		return PhysicsValueUtil.toDoubleValue(this);
	}

	public String toScientificNotation() {
		return PhysicsValueUtil.toScientificNotation(this);
	}

	public String toEngineeringNotation() {
		return PhysicsValueUtil.toEngineeringNotation(this);
	}

} // class PhysicsVal
