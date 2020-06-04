package com.linkare.irn.nascimento.util;

import java.math.BigDecimal;

import javax.faces.event.ValueChangeEvent;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public final class NumberUtils {

    public static BigDecimal getValueOrZeroOnNull(final BigDecimal value) {
	return value == null ? BigDecimal.ZERO : value;
    }

    public static int getValueOrZeroOnNull(final Integer value) {
	return value == null ? 0 : value;
    }

    public static BigDecimal sum(final BigDecimal... values) {
	BigDecimal result = BigDecimal.ZERO;
	for (BigDecimal value : values) {
	    result = result.add(getValueOrZeroOnNull(value));
	}
	return result;
    }

    public static BigDecimal getDiff(final ValueChangeEvent event) {
	return getDiff((BigDecimal) event.getOldValue(), (BigDecimal) event.getNewValue());
    }

    public static BigDecimal getDiff(final BigDecimal oldV, final BigDecimal newV) {
	final BigDecimal oldValue = getValueOrZeroOnNull(oldV);
	final BigDecimal newValue = getValueOrZeroOnNull(newV);

	return newValue.subtract(oldValue);
    }
}