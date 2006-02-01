package com.linkare.rec.data.metadata;

abstract public class HardwareInfoHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/metadata/HardwareInfo:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.metadata.HardwareInfo that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.metadata.HardwareInfo extract(org.omg.CORBA.Any a)
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [12];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember(
					"DriverVersion",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember(
					"HardwareName",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember(
					"HardwareVersion",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember(
					"HardwareManufacturer",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[4] = new org.omg.CORBA.StructMember(
					"DescriptionText",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[5] = new org.omg.CORBA.StructMember(
					"familiarName",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[6] = new org.omg.CORBA.StructMember(
					"hardware_unique_id",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.metadata.ChannelInfoHelper.type();
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_sequence_tc(0, _tcOf_members0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.data.metadata.ChannelInfoListHelper.id(), "ChannelInfoList", _tcOf_members0);
					_members0[7] = new org.omg.CORBA.StructMember(
					"channels_info",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTHardwareParameterListHelper.type();
					_members0[8] = new org.omg.CORBA.StructMember(
					"hardware_parameters",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.metadata.FrequencyScaleHelper.type();
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_sequence_tc(0, _tcOf_members0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.data.metadata.FrequencyScaleListHelper.id(), "FrequencyScaleList", _tcOf_members0);
					_members0[9] = new org.omg.CORBA.StructMember(
					"hardware_frequencies",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.synch.FrequencyHelper.type();
					_members0[10] = new org.omg.CORBA.StructMember(
					"selected_frequency",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.metadata.SamplesNumScaleHelper.type();
					_members0[11] = new org.omg.CORBA.StructMember(
					"sampling_scale",
					_tcOf_members0,
					null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.metadata.HardwareInfoHelper.id(), "HardwareInfo", _members0);
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


	public static com.linkare.rec.data.metadata.HardwareInfo read(org.omg.CORBA.portable.InputStream istream)
	{

		com.linkare.rec.data.metadata.HardwareInfo new_one = new com.linkare.rec.data.metadata.HardwareInfo();

		new_one.setDriverVersion(  org.omg.CORBA.WStringValueHelper.read(istream));
		new_one.setHardwareName(org.omg.CORBA.WStringValueHelper.read(istream));
		new_one.setHardwareVersion(org.omg.CORBA.WStringValueHelper.read(istream));
		new_one.setHardwareManufacturer(org.omg.CORBA.WStringValueHelper.read(istream));
		new_one.setDescriptionText(org.omg.CORBA.WStringValueHelper.read(istream));
		new_one.setFamiliarName(istream.read_wstring());
		new_one.setHardwareUniqueID(istream.read_wstring());
		new_one.setChannelsInfo(com.linkare.rec.data.metadata.ChannelInfoListHelper.read(istream));
		new_one.setHardwareParameters(com.linkare.rec.data.metadata.VTHardwareParameterListHelper.read(istream));
		new_one.setHardwareFrequencies(com.linkare.rec.data.metadata.FrequencyScaleListHelper.read(istream));
		new_one.setSelectedFrequency(com.linkare.rec.data.synch.FrequencyHelper.read(istream));
		new_one.setSamplingScale(com.linkare.rec.data.metadata.SamplesNumScaleHelper.read(istream));

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.metadata.HardwareInfo value)
	{
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getDriverVersion());
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getHardwareName());
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getHardwareVersion());
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getHardwareManufacturer());
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getDescriptionText());
		ostream.write_wstring(value.getFamiliarName());
		ostream.write_wstring(value.getHardwareUniqueID());
		com.linkare.rec.data.metadata.ChannelInfoListHelper.write(ostream, value.getChannelsInfo());
		com.linkare.rec.data.metadata.VTHardwareParameterListHelper.write(ostream, value.getHardwareParameters());
		com.linkare.rec.data.metadata.FrequencyScaleListHelper.write(ostream, value.getHardwareFrequencies());
		com.linkare.rec.data.synch.FrequencyHelper.write(ostream, value.getSelectedFrequency());
		com.linkare.rec.data.metadata.SamplesNumScaleHelper.write(ostream,value.getSamplingScale());
	}

}
