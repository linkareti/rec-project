package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ChannelAcquisitionConfigSearchListHolder implements Streamable
{
    public ChannelAcquisitionConfigSearch value[] = null;
    
    public ChannelAcquisitionConfigSearchListHolder()
    {
    }
    
    public ChannelAcquisitionConfigSearchListHolder(ChannelAcquisitionConfigSearch[] initialValue)
    {
	value = initialValue;
    }
    
    public void _read(InputStream i)
    {
	value = ChannelAcquisitionConfigSearchListHelper.read(i);
    }
    
    public void _write(OutputStream o)
    {
	ChannelAcquisitionConfigSearchListHelper.write(o, value);
    }
    
    public TypeCode _type()
    {
	return ChannelAcquisitionConfigSearchListHelper.type();
    }
    
}
