package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DataProducerConfigHolder implements Streamable
{
    public DataProducerConfig value = null;
    
    public DataProducerConfigHolder()
    {
    }
    
    public DataProducerConfigHolder(DataProducerConfig initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = DataProducerConfigHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	DataProducerConfigHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return DataProducerConfigHelper.type();
    }
    
}
