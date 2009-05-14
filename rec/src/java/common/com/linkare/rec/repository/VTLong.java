package com.linkare.rec.repository;

import org.omg.CORBA.portable.ValueBase;

public class VTLong implements ValueBase
{
    private int value;
    public VTLong()
    {
	setValue(0);
    }
    public VTLong(int initial)
    {
	setValue(initial);
    }
    
    private static String[] _truncatable_ids =
    {VTLongHelper.id()};
    
    public String[] _truncatable_ids()
    {
	return _truncatable_ids;
    }
    
    /** Getter for property value.
     * @return Value of property value.
     *
     */
    public int getValue()
    {
	return value;
    }
    
    /** Setter for property value.
     * @param value New value of property value.
     *
     */
    public void setValue(int value)
    {
	this.value = value;
    }
    
} // class VTLong
