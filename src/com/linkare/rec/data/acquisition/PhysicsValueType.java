package com.linkare.rec.data.acquisition;

public class PhysicsValueType implements org.omg.CORBA.portable.IDLEntity
{
	//
	// Internal member value
	//
	private int _PhysicsValueType_value;
	
	//
	// Enum member BooleanVal value
	//
	public static final int _BooleanVal = 0;
	
	//
	// Enum member BooleanVal
	//
	public static final PhysicsValueType BooleanVal = new PhysicsValueType(_BooleanVal);
	
	//
	// Enum member ByteVal value
	//
	public static final int _ByteVal = 1;
	
	//
	// Enum member ByteVal
	//
	public static final PhysicsValueType ByteVal = new PhysicsValueType(_ByteVal);
	
	//
	// Enum member ShortVal value
	//
	public static final int _ShortVal = 2;
	
	//
	// Enum member ShortVal
	//
	public static final PhysicsValueType ShortVal = new PhysicsValueType(_ShortVal);
	
	//
	// Enum member IntVal value
	//
	public static final int _IntVal = 3;
	
	//
	// Enum member IntVal
	//
	public static final PhysicsValueType IntVal = new PhysicsValueType(_IntVal);
	
	//
	// Enum member LongVal value
	//
	public static final int _LongVal = 4;
	
	//
	// Enum member LongVal
	//
	public static final PhysicsValueType LongVal = new PhysicsValueType(_LongVal);
	
	//
	// Enum member FloatVal value
	//
	public static final int _FloatVal = 5;
	
	//
	// Enum member FloatVal
	//
	public static final PhysicsValueType FloatVal = new PhysicsValueType(_FloatVal);
	
	//
	// Enum member DoubleVal value
	//
	public static final int _DoubleVal = 6;
	
	//
	// Enum member DoubleVal
	//
	public static final PhysicsValueType DoubleVal = new PhysicsValueType(_DoubleVal);
	
	//
	// Enum member ByteArrayVal value
	//
	public static final int _ByteArrayVal = 7;
	
	//
	// Enum member ByteArrayVal
	//
	public static final PhysicsValueType ByteArrayVal = new PhysicsValueType(_ByteArrayVal);
	
	//
	// Return the internal member value
	// @return	the member value
	//
	public int value()
	{
		return _PhysicsValueType_value;
	}
	
	//
	// Return a enum member from its value
	// @param		an enum value
	// @return	an enum member
	//
	public static PhysicsValueType from_int( int value )
	{
		switch ( value )
		{
			case 0 :
				return BooleanVal;
			case 1 :
				return ByteVal;
			case 2 :
				return ShortVal;
			case 3 :
				return IntVal;
			case 4 :
				return LongVal;
			case 5 :
				return FloatVal;
			case 6 :
				return DoubleVal;
			case 7 :
				return ByteArrayVal;
		}
		throw new org.omg.CORBA.BAD_OPERATION();
	}
	
	//
	// Private constructor
	// @param		the enum value for this new member
	//
	private PhysicsValueType( int value )
	{
		_PhysicsValueType_value = value;
	}
	
	
	//just for the sake of serialization
	public PhysicsValueType()
	{
	}
	
	public int getValue()
	{
		return _PhysicsValueType_value;
	}
	
	public void setValue(int value)
	{
		_PhysicsValueType_value=value;
	}
	
} // class PhysicsValueType
