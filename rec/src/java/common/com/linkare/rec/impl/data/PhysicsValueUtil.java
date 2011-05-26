/*
 * ScaleUtil.java
 *
 * Created on 20 de Agosto de 2002, 16:51
 */

package com.linkare.rec.impl.data;

import java.text.DecimalFormat;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.PhysicsValueType;
import com.linkare.rec.data.metadata.Scale;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PhysicsValueUtil {

	/** Creates a new instance of ScaleUtil */
	private PhysicsValueUtil() {
	}

	public static boolean isLessThan(final PhysicsValue pv1, final PhysicsValue pv2) {
		final double value1 = PhysicsValueUtil.toDoubleValue(pv1.getValue());
		final double value2 = PhysicsValueUtil.toDoubleValue(pv2.getValue());
		final double relatedmultiplier = pv1.getAppliedMultiplier().getExpValue()
				/ pv2.getAppliedMultiplier().getExpValue();

		return value1 * relatedmultiplier < value2;
	}

	public static boolean isEqual(final PhysicsValue pv1, final PhysicsValue pv2) {
		final double value1 = PhysicsValueUtil.toDoubleValue(pv1.getValue());
		final double value2 = PhysicsValueUtil.toDoubleValue(pv2.getValue());
		final double relatedmultiplier = pv1.getAppliedMultiplier().getExpValue()
				/ pv2.getAppliedMultiplier().getExpValue();

		return value1 * relatedmultiplier == value2;
	}

	public static boolean isMoreThan(final PhysicsValue pv1, final PhysicsValue pv2) {
		final double value1 = PhysicsValueUtil.toDoubleValue(pv1.getValue());
		final double value2 = PhysicsValueUtil.toDoubleValue(pv2.getValue());
		final double relatedmultiplier = pv1.getAppliedMultiplier().getExpValue()
				/ pv2.getAppliedMultiplier().getExpValue();

		return value1 * relatedmultiplier > value2;
	}

	public static boolean isLessThanOrEqual(final PhysicsValue pv1, final PhysicsValue pv2) {
		return (PhysicsValueUtil.isLessThan(pv1, pv2) || PhysicsValueUtil.isEqual(pv1, pv2));
	}

	public static boolean isMoreThanOrEqual(final PhysicsValue pv1, final PhysicsValue pv2) {
		return !PhysicsValueUtil.isLessThan(pv1, pv2);
	}

	public static boolean isBetween(final PhysicsValue pvalue, final PhysicsValue min, final PhysicsValue max) {
		return (PhysicsValueUtil.isMoreThanOrEqual(pvalue, min) && PhysicsValueUtil.isLessThanOrEqual(pvalue, max));
	}

	public static boolean isInScale(final PhysicsValue value, final Scale scale) {

		if (!PhysicsValueUtil.isBetween(value, new PhysicsValue(scale.getMinimumValue(), null, scale.getMultiplier()),
				new PhysicsValue(scale.getMaximumValue(), null, scale.getMultiplier()))) {
			return false;
		}

		final double valued = PhysicsValueUtil.toDoubleValue(value.getValue())
				* value.getAppliedMultiplier().getExpValue();
		final double vmin = PhysicsValueUtil.toDoubleValue(scale.getMinimumValue())
				* scale.getMultiplier().getExpValue();
		final double step = PhysicsValueUtil.toDoubleValue(scale.getStepValue()) * scale.getMultiplier().getExpValue();

		return (valued - vmin) % step == 0;
	}

	// Delegate functions for PhysicsValueUtil
	public static double toDoubleValue(final PhysicsVal pv) {
		if (pv == null) {
			return 0.;
		}

		switch (pv.getDiscriminator().value()) {
		case PhysicsValueType._BooleanVal:
			return pv.isBooleanValue() ? 1.0 : 0.0;
		case PhysicsValueType._ByteVal:
			return pv.getByteValue();
		case PhysicsValueType._ShortVal:
			return pv.getShortValue();
		case PhysicsValueType._IntVal:
			return pv.getIntValue();
		case PhysicsValueType._LongVal:
			return pv.getLongValue();
		case PhysicsValueType._FloatVal:
			return pv.getFloatValue();
		case PhysicsValueType._DoubleVal:
			return pv.getDoubleValue();
		case PhysicsValueType._ByteArrayVal:
			return pv.getByteArrayValue().getData().length;
		default:
			return 0.;
		}
	}

	public static String toScientificNotation(final PhysicsValue pv) {
		String retorna = PhysicsValueUtil.toScientificNotation(pv.getValue());

		if (retorna.endsWith("E0")) {
			retorna = retorna.substring(0, retorna.length() - 2);
		}

		if (pv.getError() != null) {
			retorna += " \u00B1 " + PhysicsValueUtil.toScientificNotation(pv.getError());
		}

		if (retorna.endsWith("E0")) {
			retorna = retorna.substring(0, retorna.length() - 2);
		}

		return retorna;
	}

	public static String toEngineeringNotation(final PhysicsValue pv) {

		String retorna = PhysicsValueUtil.toEngineeringNotation(pv.getValue());

		if (retorna.endsWith("E0")) {
			retorna = retorna.substring(0, retorna.length() - 2);
		}

		if (pv.getError() != null) {
			retorna += " \u00B1 " + PhysicsValueUtil.toEngineeringNotation(pv.getError());
		}

		if (retorna.endsWith("E0")) {
			retorna = retorna.substring(0, retorna.length() - 2);
		}

		return retorna;
	}

	public static String toScientificNotation(final PhysicsVal pv) {
		DecimalFormat formatter = new DecimalFormat("0.#E0");
		if (pv.getDiscriminator() == PhysicsValueType.ByteVal || pv.getDiscriminator() == PhysicsValueType.ShortVal
				|| pv.getDiscriminator() == PhysicsValueType.IntVal
				|| pv.getDiscriminator() == PhysicsValueType.LongVal
				|| pv.getDiscriminator() == PhysicsValueType.BooleanVal) {
			formatter = new DecimalFormat("0E0");
			formatter.setGroupingUsed(false);
			formatter.setDecimalSeparatorAlwaysShown(false);
		}

		String retorna = formatter.format(pv.toDouble());

		if (retorna.endsWith("E0")) {
			retorna = retorna.substring(0, retorna.length() - 2);
		}

		return retorna;
	}

	public static String toEngineeringNotation(final PhysicsVal pv) {
		DecimalFormat formatter = new DecimalFormat("##0.#E0");
		if (pv.getDiscriminator() == PhysicsValueType.ByteVal || pv.getDiscriminator() == PhysicsValueType.ShortVal
				|| pv.getDiscriminator() == PhysicsValueType.IntVal
				|| pv.getDiscriminator() == PhysicsValueType.LongVal
				|| pv.getDiscriminator() == PhysicsValueType.BooleanVal) {
			formatter = new DecimalFormat("##0E0");
			formatter.setGroupingUsed(false);
			formatter.setDecimalSeparatorAlwaysShown(false);
		}

		String retorna = formatter.format(pv.toDouble());

		if (retorna.endsWith("E0")) {
			retorna = retorna.substring(0, retorna.length() - 2);
		}

		return retorna;
	}

}
