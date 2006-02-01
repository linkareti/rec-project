/*
 * SpinnerMonthModel.java
 *
 * Created on 5 de Julho de 2002, 15:24
 */

package com.linkare.rec.data.synch.base;

/**
 *
 * @author  jp
 */
public class SpinnerMonthModel extends javax.swing.AbstractSpinnerModel
{
	
	private String value=null;
	
	/** Creates a new instance of SpinnerMonthModel */
	public SpinnerMonthModel()
	{
		setValue(null);
	}
	
	/** Return the object in the sequence that comes after the object returned
	 * by <code>getValue()</code>. If the end of the sequence has been reached
	 * then return null.  Calling this method does not effect <code>value</code>.
	 *
	 * @return the next legal value or null if one doesn't exist
	 * @see #getValue
	 * @see #getPreviousValue
	 */
	public Object getNextValue()
	{
	    int monthNumber=MonthMap.getMonthNumberfromName(value)-1;
	    monthNumber=monthNumber%12;
	    if(monthNumber<0) monthNumber+=12;
	    return MonthMap.getMonthNamefromNumber(MonthMap.getMonthNumberfromName(value)+1);
	}
	
	/** Return the object in the sequence that comes before the object returned
	 * by <code>getValue()</code>.  If the end of the sequence has been reached then
	 * return null. Calling this method does not effect <code>value</code>.
	 *
	 * @return the previous legal value or null if one doesn't exist
	 * @see #getValue
	 * @see #getNextValue
	 */
	public Object getPreviousValue()
	{
	    int monthNumber=MonthMap.getMonthNumberfromName(value)-1;
	    monthNumber=monthNumber%12;
	    if(monthNumber<0) monthNumber+=12;
	    return MonthMap.getMonthNamefromNumber(monthNumber);
	}
	
	/** The <i>current element</i> of the sequence.  This element is usually
	 * displayed by the <code>editor</code> part of a <code>JSpinner</code>.
	 *
	 * @return the current spinner value.
	 * @see #setValue
	 */
	public Object getValue()
	{
		return this.value;
	}
	
	/** Changes current value of the model, typically this value is displayed
	 * by the <code>editor</code> part of a  <code>JSpinner</code>.
	 * If the <code>SpinnerModel</code> implementation doesn't support
	 * the specified value then an <code>IllegalArgumentException</code>
	 * is thrown.  For example a <code>SpinnerModel</code> for numbers might
	 * only support values that are integer multiples of ten. In
	 * that case, <code>model.setValue(new Number(11))</code>
	 * would throw an exception.
	 *
	 * @throws IllegalArgumentException if <code>value</code> isn't allowed
	 * @see #getValue
	 */
	public void setValue(Object value)
	{
		if((value instanceof String) && value!=null)
		{
			this.value=(String)value;
		}
		else
			this.value=MonthMap.getMonthNames()[0];
		
		this.fireStateChanged();
	}
	
}
