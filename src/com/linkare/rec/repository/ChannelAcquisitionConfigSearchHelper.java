package com.linkare.rec.repository;

import com.linkare.rec.data.config.VTChannelParameterConfigListHelper;
import com.linkare.rec.data.metadata.VTScaleHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;


abstract public class ChannelAcquisitionConfigSearchHelper
{
    private static String  _id = "IDL:com/linkare/rec/repository/ChannelAcquisitionConfigSearch:1.0";
    
    public static void insert(Any a, ChannelAcquisitionConfigSearch that)
    {
	OutputStream out = a.create_output_stream();
	a.type(type());
	write(out, that);
	a.read_value(out.create_input_stream(), type());
    }
    
    public static ChannelAcquisitionConfigSearch extract(Any a)
    {
	return read(a.create_input_stream());
    }
    
    private static TypeCode __typeCode = null;
    private static boolean __active = false;
    public static synchronized TypeCode type()
    {
	if (__typeCode == null)
	{
	    synchronized (TypeCode.class)
	    {
		if (__typeCode == null)
		{
		    if (__active)
		    {
			return ORB.init().create_recursive_tc( _id );
		    }
		    __active = true;
		    StructMember[] _members0 = new StructMember [6];
		    TypeCode _tcOf_members0 = null;
		    _tcOf_members0 = VTChannelNameHelper.type();
		    _members0[0] = new StructMember(
		    "channel_name",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = VTDateTimeSearchHelper.type();
		    _members0[1] = new StructMember(
		    "startTimeSearch",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = VTFrequencySearchHelper.type();
		    _members0[2] = new StructMember(
		    "frequencySearch",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = VTScaleHelper.type();
		    _members0[3] = new StructMember(
		    "selectedScale",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = VTChannelParameterConfigListHelper.type();
		    _members0[4] = new StructMember(
		    "selected_channel_parameters",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = VTSamplesNumSearchHelper.type();
		    _members0[5] = new StructMember(
		    "samplesNumSearch",
		    _tcOf_members0,
		    null);
		    __typeCode = ORB.init().create_struct_tc(ChannelAcquisitionConfigSearchHelper.id(), "ChannelAcquisitionConfigSearch", _members0);
		    __active = false;
		}
	    }
	}
	return __typeCode;
    }
    
    public static String id()
    {
	return _id;
    }
    
    public static ChannelAcquisitionConfigSearch read(InputStream istream)
    {
	ChannelAcquisitionConfigSearch value = new ChannelAcquisitionConfigSearch();
	value.setChannelName(VTChannelNameHelper.read(istream));
	value.setStartTimeSearch(VTDateTimeSearchHelper.read(istream));
	value.setFrequencySearch(VTFrequencySearchHelper.read(istream));
	value.setSelectedScale(VTScaleHelper.read(istream));
	value.setSelectedChannelParameters(VTChannelParameterConfigListHelper.read(istream));
	value.setSamplesNumSearch(VTSamplesNumSearchHelper.read(istream));
	return value;
    }
    
    public static void write(OutputStream ostream, ChannelAcquisitionConfigSearch value)
    {
	VTChannelNameHelper.write(ostream, value.getChannelName());
	VTDateTimeSearchHelper.write(ostream, value.getStartTimeSearch());
	VTFrequencySearchHelper.write(ostream, value.getFrequencySearch());
	VTScaleHelper.write(ostream, value.getSelectedScale());
	VTChannelParameterConfigListHelper.write(ostream, value.getSelectedChannelParameters());
	VTSamplesNumSearchHelper.write(ostream, value.getSamplesNumSearch());
    }
    
}
