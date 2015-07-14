/*
 * MathUtil.java
 *
 * Created on 30 de Julho de 2003, 14:51
 *
 * ->Changed by André on 26/07/04:
 *    For now we'll only check if the value is in scale: value C [a,b], no check on 
 *   the validity of the step. No zero value steps will be accepted.
 */

package com.linkare.rec.impl.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MathUtil {

	private static final Logger LOGGER = Logger.getLogger(MathUtil.class.getName());

	public static boolean isValueInScale(double minValue, double maxValue, double stepValue, final double value) {
		boolean retVal = true;

		stepValue = stepValue < 0. ? -stepValue : stepValue;

		if (maxValue < minValue) {
			final double temp_min = minValue;
			minValue = maxValue;
			maxValue = temp_min;
		}

		final String originalScaleDef = new StringBuilder("scale [").append(minValue).append(" ; ").append(maxValue)
				.append("] step=").append(stepValue).toString();

		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Is value " + value + " in " + originalScaleDef + "?");
		}

		if (value < minValue || value > maxValue) {
			retVal = false;
		} else if (stepValue == 0.) {
			retVal = (minValue <= value && value <= maxValue);
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "step_value is 0., returning (min_value <= value <= max_value value) = "
						+ retVal);
			}
		} else {
//			BigDecimal valueBig = new BigDecimal(value, MathContext.DECIMAL64);
//			BigDecimal stepBig = new BigDecimal(stepValue, MathContext.DECIMAL64);
//			BigDecimal minBig = new BigDecimal(minValue, MathContext.DECIMAL64);
//
//			BigDecimal delta = minBig.subtract(valueBig, MathContext.DECIMAL64);
//			if (BigDecimal.ZERO.compareTo(delta.remainder(stepBig, MathContext.DECIMAL64))!=0) {
//				retVal=false;
//			}
			retVal = (minValue <= value && value <= maxValue);
		}

		if(retVal && LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "value " + value + " is in " + originalScaleDef + " ... returning true!");
		}
		else if(!retVal && LOGGER.isLoggable(Level.WARNING)) {
			LOGGER.log(Level.WARNING, "value " + value + " is not in " + originalScaleDef + " ... returning false!");	
		}
		
		return retVal;

	}
}
