/*
 * PhysicsValueFactory.java
 *
 * Created on 20 de Agosto de 2002, 13:46
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.metadata.Scale;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PhysicsValueFactory {

	/** Creates a new instance of PhysicsValueFactory */
	private PhysicsValueFactory() {
	}

	public static PhysicsValue fromBoolean(final boolean booleanvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromBoolean(booleanvalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromByte(final byte bytevalue, final byte errorvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromByte(bytevalue), PhysicsValFactory.fromByte(errorvalue),
				appliedmultiplier);
	}

	public static PhysicsValue fromChar(final char charvalue, final char errorvalue, final Multiplier appliedmultiplier) {
		return PhysicsValueFactory.fromShort((short) charvalue, (short) errorvalue, appliedmultiplier);
	}

	public static PhysicsValue fromShort(final short shortvalue, final short errorvalue,
			final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromShort(shortvalue), PhysicsValFactory.fromShort(errorvalue),
				appliedmultiplier);
	}

	public static PhysicsValue fromInt(final int intvalue, final int errorvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromInt(intvalue), PhysicsValFactory.fromInt(errorvalue),
				appliedmultiplier);
	}

	public static PhysicsValue fromFloat(final float floatvalue, final float errorvalue,
			final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromFloat(floatvalue), PhysicsValFactory.fromFloat(errorvalue),
				appliedmultiplier);
	}

	public static PhysicsValue fromDouble(final double doublevalue, final double errorvalue,
			final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromDouble(doublevalue), PhysicsValFactory.fromDouble(errorvalue),
				appliedmultiplier);
	}

	public static PhysicsValue fromByteArray(final byte[] bytearrayvalue, final Multiplier appliedmultiplier,
			final String mime_type) {
		return new PhysicsValue(PhysicsValFactory.fromByteArray(bytearrayvalue, mime_type), null, appliedmultiplier);
	}

	public static PhysicsValue fromByte(final byte bytevalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromByte(bytevalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromChar(final char charvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromShort((short) charvalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromShort(final short shortvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromShort(shortvalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromInt(final int intvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromInt(intvalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromFloat(final float floatvalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromFloat(floatvalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromDouble(final double doublevalue, final Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromDouble(doublevalue), null, appliedmultiplier);
	}

	public static PhysicsValue fromDouble(final double value, final Scale s) {
		final double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null) {
			error = s.getDefaultErrorValue().toDouble();
		}
		if (error < step) {
			error = step;
		}
		double aux = step;
		while (aux >= 10.) {
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		if (aux > 0) { // aux can be zero...
			while (aux < 1.) {
				multiplier_exp++;
				aux *= 10.;
			}
		}

		final double multiplier = Math.pow(10., multiplier_exp);
		final double value_corrected = Math.floor(value * multiplier) / multiplier;
		final double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromDouble(value_corrected),
				PhysicsValFactory.fromDouble(error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromInt(final int value, final Scale s) {
		final double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null) {
			error = s.getDefaultErrorValue().toDouble();
		}
		if (error < step) {
			error = step;
		}
		double aux = step;
		while (aux >= 10.) {
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		while (aux < 1.) {
			multiplier_exp++;
			aux *= 10.;
		}

		final double multiplier = Math.pow(10., multiplier_exp);
		final double value_corrected = Math.floor(value * multiplier) / multiplier;
		final double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromInt((int) value_corrected),
				PhysicsValFactory.fromInt((int) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromLong(final long value, final Scale s) {
		final double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null) {
			error = s.getDefaultErrorValue().toDouble();
		}
		if (error < step) {
			error = step;
		}
		double aux = step;
		while (aux >= 10.) {
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		while (aux < 1.) {
			multiplier_exp++;
			aux *= 10.;
		}

		final double multiplier = Math.pow(10., multiplier_exp);
		final double value_corrected = Math.floor(value * multiplier) / multiplier;
		final double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromLong((long) value_corrected),
				PhysicsValFactory.fromLong((long) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromShort(final short value, final Scale s) {
		final double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null) {
			error = s.getDefaultErrorValue().toDouble();
		}
		if (error < step) {
			error = step;
		}
		double aux = step;
		while (aux >= 10.) {
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		while (aux < 1.) {
			multiplier_exp++;
			aux *= 10.;
		}

		final double multiplier = Math.pow(10., multiplier_exp);
		final double value_corrected = Math.floor(value * multiplier) / multiplier;
		final double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromShort((short) value_corrected),
				PhysicsValFactory.fromShort((short) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromByte(final byte value, final Scale s) {
		final double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null) {
			error = s.getDefaultErrorValue().toDouble();
		}
		if (error < step) {
			error = step;
		}
		double aux = step;
		while (aux >= 10.) {
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		while (aux < 1.) {
			multiplier_exp++;
			aux *= 10.;
		}

		final double multiplier = Math.pow(10., multiplier_exp);
		final double value_corrected = Math.floor(value * multiplier) / multiplier;
		final double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromByte((byte) value_corrected),
				PhysicsValFactory.fromByte((byte) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromFloat(final float value, final Scale s) {
		final double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null) {
			error = s.getDefaultErrorValue().toDouble();
		}
		if (error < step) {
			error = step;
		}
		double aux = step;
		while (aux >= 10.) {
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		while (aux < 1.) {
			multiplier_exp++;
			aux *= 10.;
		}

		final double multiplier = Math.pow(10., multiplier_exp);
		final double value_corrected = Math.rint(value * multiplier) / multiplier;
		final double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromFloat((float) value_corrected),
				PhysicsValFactory.fromFloat((float) error_corrected), s.getMultiplier());
	}

}
