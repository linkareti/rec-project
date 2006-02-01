package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTFrequencySearchHolder implements Streamable
{
    public FrequencySearch value;
    
    public VTFrequencySearchHolder()
    {
    }
    
    public VTFrequencySearchHolder(FrequencySearch initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = VTFrequencySearchHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	VTFrequencySearchHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return VTFrequencySearchHelper.type();
    }
    
}
