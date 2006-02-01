package com.linkare.rec.data.config;

abstract public class ChannelAcquisitionConfigHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/config/ChannelAcquisitionConfig:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.config.ChannelAcquisitionConfig that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.config.ChannelAcquisitionConfig extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			synchronized (org.omg.CORBA.TypeCode.class)
			{
				if (__typeCode == null)
				{
					if (__active)
					{
						return org.omg.CORBA.ORB.init().create_recursive_tc( _id );
					}
					__active = true;
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [6];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.metadata.ChannelNameHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember(
					"channel_name",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.synch.VTDateTimeHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember(
					"time_start",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.synch.VTFrequencyHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember(
					"selected_frequency",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTScaleHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember(
					"selected_scale",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.config.VTChannelParameterConfigListHelper.type();
					_members0[4] = new org.omg.CORBA.StructMember(
					"selected_channel_parameters",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[5] = new org.omg.CORBA.StructMember(
					"total_samples",
					_tcOf_members0,
					null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.id(), "ChannelAcquisitionConfig", _members0);
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

	public static com.linkare.rec.data.config.ChannelAcquisitionConfig read(org.omg.CORBA.portable.InputStream istream)
	{

		com.linkare.rec.data.config.ChannelAcquisitionConfig new_one = new com.linkare.rec.data.config.ChannelAcquisitionConfig();

		new_one.setChannelName(com.linkare.rec.data.metadata.ChannelNameHelper.read(istream));
		new_one.setTimeStart((com.linkare.rec.data.synch.DateTime) com.linkare.rec.data.synch.VTDateTimeHelper.read(istream));
		new_one.setSelectedFrequency((com.linkare.rec.data.synch.Frequency) com.linkare.rec.data.synch.VTFrequencyHelper.read(istream));
		new_one.setSelectedScale((com.linkare.rec.data.metadata.Scale) com.linkare.rec.data.metadata.VTScaleHelper.read(istream));
		new_one.setSelectedChannelParameters((com.linkare.rec.data.config.ParameterConfig[]) com.linkare.rec.data.config.VTChannelParameterConfigListHelper.read(istream));
		new_one.setTotalSamples(istream.read_long());

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.config.ChannelAcquisitionConfig value)
	{
		com.linkare.rec.data.metadata.ChannelNameHelper.write(ostream,value.getChannelName());
		com.linkare.rec.data.synch.VTDateTimeHelper.write(ostream, value.getTimeStart());
		com.linkare.rec.data.synch.VTFrequencyHelper.write(ostream, value.getSelectedFrequency());
		com.linkare.rec.data.metadata.VTScaleHelper.write(ostream, value.getSelectedScale());
		com.linkare.rec.data.config.VTChannelParameterConfigListHelper.write(ostream, value.getSelectedChannelParameters());
		ostream.write_long(value.getTotalSamples());
	}

}
