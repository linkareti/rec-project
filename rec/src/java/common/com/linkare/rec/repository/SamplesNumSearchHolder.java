package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SamplesNumSearchHolder implements Streamable
{
    public SamplesNumSearch value = null;
    
    public SamplesNumSearchHolder()
    {
    }
    
    public SamplesNumSearchHolder(SamplesNumSearch initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = SamplesNumSearchHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	SamplesNumSearchHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return SamplesNumSearchHelper.type();
    }
    
}
