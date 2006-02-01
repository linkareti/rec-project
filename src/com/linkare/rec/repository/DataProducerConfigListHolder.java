package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DataProducerConfigListHolder implements Streamable
{
    public DataProducerConfig value[] = null;
    
    public DataProducerConfigListHolder()
    {
    }
    
    public DataProducerConfigListHolder(DataProducerConfig[] initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = DataProducerConfigListHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	DataProducerConfigListHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return DataProducerConfigListHelper.type();
    }
    
}
