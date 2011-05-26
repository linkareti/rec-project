package com.linkare.rec.data.config;

abstract public class HardwareAcquisitionConfigHelper {
	private static String _id = "IDL:com/linkare/rec/data/config/HardwareAcquisitionConfig:1.0";

	public static void insert(final org.omg.CORBA.Any a,
			final com.linkare.rec.data.config.HardwareAcquisitionConfig that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareAcquisitionConfigHelper.type());
		HardwareAcquisitionConfigHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareAcquisitionConfigHelper.type());
	}

	public static com.linkare.rec.data.config.HardwareAcquisitionConfig extract(final org.omg.CORBA.Any a) {
		return HardwareAcquisitionConfigHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareAcquisitionConfigHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (HardwareAcquisitionConfigHelper.__typeCode == null) {
					if (HardwareAcquisitionConfigHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(HardwareAcquisitionConfigHelper._id);
					}
					HardwareAcquisitionConfigHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[7];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;

					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[0] = new org.omg.CORBA.StructMember("familiarName", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[1] = new org.omg.CORBA.StructMember("hardware_unique_id", _tcOf_members0, null);

					_tcOf_members0 = com.linkare.rec.data.synch.DateTimeHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember("time_start", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.synch.FrequencyHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember("selected_frequency", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.type();
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_sequence_tc(0, _tcOf_members0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.id(),
							"ChannelAcquisitionConfigList", _tcOf_members0);
					_members0[4] = new org.omg.CORBA.StructMember("channels_config", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.type();
					_members0[5] = new org.omg.CORBA.StructMember("selected_hardware_parameters", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[6] = new org.omg.CORBA.StructMember("total_samples", _tcOf_members0, null);
					HardwareAcquisitionConfigHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.id(),
							"HardwareAcquisitionConfig", _members0);
					HardwareAcquisitionConfigHelper.__active = false;
				}
			}
		}
		return HardwareAcquisitionConfigHelper.__typeCode;
	}

	public static String id() {
		return HardwareAcquisitionConfigHelper._id;
	}

	public static com.linkare.rec.data.config.HardwareAcquisitionConfig read(
			final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.config.HardwareAcquisitionConfig new_one = new com.linkare.rec.data.config.HardwareAcquisitionConfig();
		new_one.setFamiliarName(istream.read_wstring());
		new_one.setHardwareUniqueID(istream.read_wstring());
		new_one.setTimeStart(com.linkare.rec.data.synch.DateTimeHelper.read(istream));
		new_one.setSelectedFrequency(com.linkare.rec.data.synch.FrequencyHelper.read(istream));
		new_one.setChannelsConfig(com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.read(istream));
		new_one.setSelectedHardwareParameters(com.linkare.rec.data.config.VTHardwareParameterConfigListHelper
				.read(istream));
		new_one.setTotalSamples(istream.read_long());

		return new_one;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.config.HardwareAcquisitionConfig value) {
		ostream.write_wstring(value.getFamiliarName());
		ostream.write_wstring(value.getHardwareUniqueID());
		com.linkare.rec.data.synch.DateTimeHelper.write(ostream, value.getTimeStart());
		com.linkare.rec.data.synch.FrequencyHelper.write(ostream, value.getSelectedFrequency());
		com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.write(ostream, value.getChannelsConfig());
		com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.write(ostream,
				value.getSelectedHardwareParameters());
		ostream.write_long(value.getTotalSamples());
	}

}
