/*
 * PhysicsValueFactory.java
 *
 * Created on 20 de Agosto de 2002, 13:46
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.metadata.Scale;

/**
 * 
 * @author jp
 */
public class PhysicsValueFactory {

	/** Creates a new instance of PhysicsValueFactory */
	private PhysicsValueFactory() {
	}

	public static PhysicsValue fromBoolean(boolean booleanvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromBoolean(booleanvalue),
				null, appliedmultiplier);
	}

	public static PhysicsValue fromByte(byte bytevalue, byte errorvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromByte(bytevalue),
				PhysicsValFactory.fromByte(errorvalue), appliedmultiplier);
	}

	public static PhysicsValue fromChar(char charvalue, char errorvalue,
			Multiplier appliedmultiplier) {
		return PhysicsValueFactory.fromShort((short) charvalue,
				(short) errorvalue, appliedmultiplier);
	}

	public static PhysicsValue fromShort(short shortvalue, short errorvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromShort(shortvalue),
				PhysicsValFactory.fromShort(errorvalue), appliedmultiplier);
	}

	public static PhysicsValue fromInt(int intvalue, int errorvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromInt(intvalue),
				PhysicsValFactory.fromInt(errorvalue), appliedmultiplier);
	}

	public static PhysicsValue fromFloat(float floatvalue, float errorvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromFloat(floatvalue),
				PhysicsValFactory.fromFloat(errorvalue), appliedmultiplier);
	}

	public static PhysicsValue fromDouble(double doublevalue,
			double errorvalue, Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromDouble(doublevalue),
				PhysicsValFactory.fromDouble(errorvalue), appliedmultiplier);
	}

	public static PhysicsValue fromByteArray(byte[] bytearrayvalue,
			Multiplier appliedmultiplier, String mime_type) {
		return new PhysicsValue(PhysicsValFactory.fromByteArray(bytearrayvalue,
				mime_type), null, appliedmultiplier);
	}

	public static PhysicsValue fromByte(byte bytevalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromByte(bytevalue), null,
				appliedmultiplier);
	}

	public static PhysicsValue fromChar(char charvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromShort((short) charvalue),
				null, appliedmultiplier);
	}

	public static PhysicsValue fromShort(short shortvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromShort(shortvalue), null,
				appliedmultiplier);
	}

	public static PhysicsValue fromInt(int intvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromInt(intvalue), null,
				appliedmultiplier);
	}

	public static PhysicsValue fromFloat(float floatvalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromFloat(floatvalue), null,
				appliedmultiplier);
	}

	public static PhysicsValue fromDouble(double doublevalue,
			Multiplier appliedmultiplier) {
		return new PhysicsValue(PhysicsValFactory.fromDouble(doublevalue),
				null, appliedmultiplier);
	}

	public static PhysicsValue fromDouble(double value, Scale s) {
		int antiInfiniteLoop = 0;
		double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null)
			error = s.getDefaultErrorValue().toDouble();
		if (error < step)
			error = step;
		double aux = step;
		while (aux >= 10. && antiInfiniteLoop<20) {
			antiInfiniteLoop++;
			multiplier_exp--;
			aux /= 10.;
		}
		aux = step;
		antiInfiniteLoop = 0;
		while (aux < 1. && antiInfiniteLoop<20) {
			antiInfiniteLoop++;
			multiplier_exp++;
			aux *= 10.;
		}

		double multiplier = Math.pow(10., (double) multiplier_exp);
		double value_corrected = Math.floor(value * multiplier) / multiplier;
		double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory.fromDouble(value_corrected),
				PhysicsValFactory.fromDouble(error_corrected), s
						.getMultiplier());
	}

	public static PhysicsValue fromInt(int value, Scale s) {
		double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null)
			error = s.getDefaultErrorValue().toDouble();
		if (error < step)
			error = step;
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

		double multiplier = Math.pow(10., (double) multiplier_exp);
		double value_corrected = Math.floor((double) value * multiplier)
				/ multiplier;
		double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory
				.fromInt((int) value_corrected), PhysicsValFactory
				.fromInt((int) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromLong(long value, Scale s) {
		double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null)
			error = s.getDefaultErrorValue().toDouble();
		if (error < step)
			error = step;
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

		double multiplier = Math.pow(10., (double) multiplier_exp);
		double value_corrected = Math.floor((double) value * multiplier)
				/ multiplier;
		double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory
				.fromLong((long) value_corrected), PhysicsValFactory
				.fromLong((long) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromShort(short value, Scale s) {
		double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null)
			error = s.getDefaultErrorValue().toDouble();
		if (error < step)
			error = step;
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

		double multiplier = Math.pow(10., (double) multiplier_exp);
		double value_corrected = Math.floor((double) value * multiplier)
				/ multiplier;
		double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory
				.fromShort((short) value_corrected), PhysicsValFactory
				.fromShort((short) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromByte(byte value, Scale s) {
		double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null)
			error = s.getDefaultErrorValue().toDouble();
		if (error < step)
			error = step;
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

		double multiplier = Math.pow(10., (double) multiplier_exp);
		double value_corrected = Math.floor((double) value * multiplier)
				/ multiplier;
		double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory
				.fromByte((byte) value_corrected), PhysicsValFactory
				.fromByte((byte) error_corrected), s.getMultiplier());
	}

	public static PhysicsValue fromFloat(float value, Scale s) {
		double step = s.getStepValue().toDouble();
		int multiplier_exp = 0;
		double error = 0;
		if (s.getDefaultErrorValue() != null)
			error = s.getDefaultErrorValue().toDouble();
		if (error < step)
			error = step;
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

		double multiplier = Math.pow(10., (double) multiplier_exp);
		double value_corrected = Math.rint((double) value * multiplier)
				/ multiplier;
		double error_corrected = Math.rint(error * multiplier) / multiplier;
		return new PhysicsValue(PhysicsValFactory
				.fromFloat((float) value_corrected), PhysicsValFactory
				.fromFloat((float) error_corrected), s.getMultiplier());
	}

}
