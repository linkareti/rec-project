package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HardwareAcquisitionConfigSearchListHolder implements Streamable
{
    public HardwareAcquisitionConfigSearch value[] = null;
    
    public HardwareAcquisitionConfigSearchListHolder()
    {
    }
    
    public HardwareAcquisitionConfigSearchListHolder(HardwareAcquisitionConfigSearch[] initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = HardwareAcquisitionConfigSearchListHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	HardwareAcquisitionConfigSearchListHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return HardwareAcquisitionConfigSearchListHelper.type();
    }
    
}
