package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTChannelAcquisitionConfigSearchListHolder implements Streamable
{
    public ChannelAcquisitionConfigSearch[] value;
    
    public VTChannelAcquisitionConfigSearchListHolder()
    {
    }
    
    public VTChannelAcquisitionConfigSearchListHolder(ChannelAcquisitionConfigSearch[] initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = VTChannelAcquisitionConfigSearchListHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	VTChannelAcquisitionConfigSearchListHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return VTChannelAcquisitionConfigSearchListHelper.type();
    }
    
}
