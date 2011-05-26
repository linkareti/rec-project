/*
 * PhysicsValFactory.java
 *
 * Created on 20 de Agosto de 2002, 13:39
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.ByteArrayValue;
import com.linkare.rec.data.acquisition.PhysicsVal;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PhysicsValFactory {

	/** Creates a new instance of PhysicsValFactory */
	private PhysicsValFactory() {
	}

	public static PhysicsVal fromBoolean(final boolean booleanvalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setBooleanValue(booleanvalue);

		return value;
	}

	public static PhysicsVal fromByte(final byte bytevalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setByteValue(bytevalue);

		return value;
	}

	public static PhysicsVal fromChar(final char charvalue) {
		return PhysicsValFactory.fromShort((short) charvalue);
	}

	public static PhysicsVal fromShort(final short shortvalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setShortValue(shortvalue);

		return value;
	}

	public static PhysicsVal fromInt(final int intvalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setIntValue(intvalue);

		return value;
	}

	public static PhysicsVal fromLong(final long longvalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setLongValue(longvalue);
		return value;
	}

	public static PhysicsVal fromFloat(final float floatvalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setFloatValue(floatvalue);

		return value;
	}

	public static PhysicsVal fromDouble(final double doublevalue) {
		final PhysicsVal value = new PhysicsVal();
		value.setDoubleValue(doublevalue);
		return value;
	}

	public static PhysicsVal fromByteArray(final byte[] bytearrayvalue, final String mime_type) {
		final PhysicsVal value = new PhysicsVal();
		value.setByteArrayValue(new ByteArrayValue(bytearrayvalue, mime_type));
		return value;
	}

}
