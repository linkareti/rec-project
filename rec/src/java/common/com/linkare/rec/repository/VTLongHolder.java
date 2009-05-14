package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTLongHolder implements Streamable
{
    public int value;
    
    public VTLongHolder()
    {
    }
    
    public VTLongHolder(int initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = VTLongHelper.read(i).getValue();
    }
    
    public void _write(OutputStream o)
    {
	VTLong vb = new VTLong(value);
	VTLongHelper.write(o, vb);
    }
    
    public TypeCode _type()
    {
	return VTLongHelper.type();
    }
    
}
