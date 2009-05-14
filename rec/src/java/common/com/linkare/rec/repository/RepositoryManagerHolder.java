package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class RepositoryManagerHolder implements Streamable
{
    public RepositoryManager value = null;
    
    public RepositoryManagerHolder()
    {
    }
    
    public RepositoryManagerHolder(RepositoryManager initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = RepositoryManagerHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	RepositoryManagerHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return RepositoryManagerHelper.type();
    }
    
}
