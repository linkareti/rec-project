package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTSamplesNumSearchHolder implements Streamable
{
    public SamplesNumSearch value;
    
    public VTSamplesNumSearchHolder()
    {
    }
    
    public VTSamplesNumSearchHolder(SamplesNumSearch initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = VTSamplesNumSearchHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	VTSamplesNumSearchHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return VTSamplesNumSearchHelper.type();
    }
    
}
