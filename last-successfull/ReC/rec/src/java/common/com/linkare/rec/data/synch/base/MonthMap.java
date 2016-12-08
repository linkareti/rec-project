/*
 * MonthMap.java
 *
 * Created on 3 de Julho de 2002, 19:15
 */

package com.linkare.rec.data.synch.base;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MonthMap {

	private static final java.util.HashMap<Integer, String> monthNameMap = new java.util.HashMap<Integer, String>();
	private static final java.util.HashMap<String, Object> monthValueMap = new java.util.HashMap<String, Object>();
	static {
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.JANUARY), "Janeiro");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.FEBRUARY), "Fevereiro");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.MARCH), "Marco");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.APRIL), "Abril");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.MAY), "Maio");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.JUNE), "Junho");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.JULY), "Julho");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.AUGUST), "Agosto");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.SEPTEMBER), "Setembro");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.OCTOBER), "Outubro");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.NOVEMBER), "Novembro");
		MonthMap.monthNameMap.put(new Integer(java.util.Calendar.DECEMBER), "Dezembro");

		final Object[] keysObj = MonthMap.monthNameMap.keySet().toArray();
		for (final Object element : keysObj) {
			MonthMap.monthValueMap.put(MonthMap.monthNameMap.get(element), element);
		}
	}

	public static String[] getMonthNames() {
		final Object[] values = MonthMap.monthNameMap.values().toArray();
		final String[] monthNames = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			monthNames[i] = (String) values[values.length - 1 - i];
		}

		return monthNames;
	}

	public static int getMonthNumberfromName(final String name) {
		return ((Integer) MonthMap.monthValueMap.get(name)).intValue();
	}

	public static String getMonthNamefromNumber(final int number) {
		return MonthMap.monthNameMap.get(new Integer(number));
	}
}
