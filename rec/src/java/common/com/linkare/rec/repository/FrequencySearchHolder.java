package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class FrequencySearchHolder implements Streamable
{
    public FrequencySearch value = null;
    
    public FrequencySearchHolder()
    {
    }
    
    public FrequencySearchHolder(FrequencySearch initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = FrequencySearchHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	FrequencySearchHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return FrequencySearchHelper.type();
    }
    
}
