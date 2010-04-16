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

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MathUtil {

	public static boolean isValueInScale(double min_value, double max_value, double step_value, double value) {
		step_value = step_value < 0. ? -step_value : step_value;

		if (max_value < min_value) {
			double temp_min = min_value;
			min_value = max_value;
			max_value = temp_min;
		}

		System.err.println("value " + value + " should be in scale [" + min_value + " - " + max_value + "] step="
				+ step_value);
		if (step_value == 0.) {
			System.err.println("step_value is 0., returning (value<=max_value && value>=min_value)="
					+ (value <= max_value && value >= min_value));
			return value <= max_value && value >= min_value;
		}

		if (value < min_value || value > max_value) {
			System.err.println("value is out of scale... returning false!");
			return false;
		}

		return true;

		/*
		 * int dec_places_step=0; double step_value_temp=step_value;
		 * while(Math.floor(step_value_temp)<step_value_temp) {
		 * dec_places_step++; step_value_temp=step_value_temp*10.; }
		 * 
		 * double div=(value-min_value)/step_value; double
		 * power_mult=Math.pow(10.,dec_places_step);
		 * div=Math.floor(div*power_mult)/power_mult;
		 * System.err.println("div="+div);
		 * System.err.println("Math.floor(div)="+Math.floor(div));
		 * System.err.println("Diference="+(div-Math.floor(div)));
		 * System.err.println("Return value="+((div-Math.floor(div))==0.));
		 * return ((div-Math.floor(div))==0.);
		 */
	}

}
