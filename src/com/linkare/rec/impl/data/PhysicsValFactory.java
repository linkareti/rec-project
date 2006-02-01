/*
 * PhysicsValFactory.java
 *
 * Created on 20 de Agosto de 2002, 13:39
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.ByteArrayValue;
/**
 *
 * @author  jp
 */
public class PhysicsValFactory
{

    /** Creates a new instance of PhysicsValFactory */
    private PhysicsValFactory()
    {
    }

    public static PhysicsVal fromBoolean(boolean booleanvalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setBooleanValue(booleanvalue);

	return value;
    }
    public static PhysicsVal fromByte(byte bytevalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setByteValue(bytevalue);

	return value;
    }

    public static PhysicsVal fromChar(char charvalue)
    {
	return PhysicsValFactory.fromShort((short)charvalue);
    }

    public static PhysicsVal fromShort(short shortvalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setShortValue(shortvalue);

	return value;
    }

    public static PhysicsVal fromInt(int intvalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setIntValue(intvalue);

	return value;
    }

    
     public static PhysicsVal fromLong(long longvalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setLongValue(longvalue);
	return value;
    }
     
    public static PhysicsVal fromFloat(float floatvalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setFloatValue(floatvalue);

	return value;
    }

    public static PhysicsVal fromDouble(double doublevalue)
    {
	PhysicsVal value=new PhysicsVal();
	value.setDoubleValue(doublevalue);
	return value;
    }

    public static PhysicsVal fromByteArray(byte[] bytearrayvalue,String mime_type)
    {
	PhysicsVal value=new PhysicsVal();
	value.setByteArrayValue(new ByteArrayValue(bytearrayvalue,mime_type));
	return value;
    }

}
