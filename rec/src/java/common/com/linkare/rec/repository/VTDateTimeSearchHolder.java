package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTDateTimeSearchHolder implements Streamable
{
    public DateTimeSearch value;
    
    public VTDateTimeSearchHolder()
    {
    }
    
    public VTDateTimeSearchHolder(DateTimeSearch initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = VTDateTimeSearchHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	VTDateTimeSearchHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return VTDateTimeSearchHelper.type();
    }
    
}
