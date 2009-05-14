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
public class MonthMap
{
	
	private static final java.util.HashMap<Integer, String> monthNameMap=new java.util.HashMap<Integer, String>();
	private static final java.util.HashMap<String, Object> monthValueMap=new java.util.HashMap<String, Object>();
	static
	{
		monthNameMap.put(new Integer(java.util.Calendar.JANUARY),"Janeiro");
		monthNameMap.put(new Integer(java.util.Calendar.FEBRUARY),"Fevereiro");
		monthNameMap.put(new Integer(java.util.Calendar.MARCH),"Marco");
		monthNameMap.put(new Integer(java.util.Calendar.APRIL),"Abril");
		monthNameMap.put(new Integer(java.util.Calendar.MAY),"Maio");
		monthNameMap.put(new Integer(java.util.Calendar.JUNE),"Junho");
		monthNameMap.put(new Integer(java.util.Calendar.JULY),"Julho");
		monthNameMap.put(new Integer(java.util.Calendar.AUGUST),"Agosto");
		monthNameMap.put(new Integer(java.util.Calendar.SEPTEMBER),"Setembro");
		monthNameMap.put(new Integer(java.util.Calendar.OCTOBER),"Outubro");
		monthNameMap.put(new Integer(java.util.Calendar.NOVEMBER),"Novembro");
		monthNameMap.put(new Integer(java.util.Calendar.DECEMBER),"Dezembro");
		
		Object[] keysObj=monthNameMap.keySet().toArray();
		for(int i=0;i<keysObj.length;i++)
			monthValueMap.put(monthNameMap.get(keysObj[i]),keysObj[i]);
	}
	
	public static String[] getMonthNames()
	{
		Object[] values=monthNameMap.values().toArray();
		String[] monthNames=new String[values.length];
		for(int i=0;i<values.length;i++)
			monthNames[i]=(String)values[values.length-1-i];
		
		return monthNames;
	}
	
	public static int getMonthNumberfromName(String name)
	{
		return ((Integer)monthValueMap.get(name)).intValue();
	}
	
	public static String getMonthNamefromNumber(int number)
	{
	    return (String)monthNameMap.get(new Integer(number));
	}
}
