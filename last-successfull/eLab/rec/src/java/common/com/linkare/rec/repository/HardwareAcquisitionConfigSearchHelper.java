package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import com.linkare.rec.data.config.VTHardwareParameterConfigListHelper;

abstract public class HardwareAcquisitionConfigSearchHelper {
	private static String _id = "IDL:com/linkare/rec/repository/HardwareAcquisitionConfigSearch:1.0";

	public static void insert(final Any a, final HardwareAcquisitionConfigSearch that) {
		final OutputStream out = a.create_output_stream();
		a.type(HardwareAcquisitionConfigSearchHelper.type());
		HardwareAcquisitionConfigSearchHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareAcquisitionConfigSearchHelper.type());
	}

	public static HardwareAcquisitionConfigSearch extract(final Any a) {
		return HardwareAcquisitionConfigSearchHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (HardwareAcquisitionConfigSearchHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (HardwareAcquisitionConfigSearchHelper.__typeCode == null) {
					if (HardwareAcquisitionConfigSearchHelper.__active) {
						return ORB.init().create_recursive_tc(HardwareAcquisitionConfigSearchHelper._id);
					}
					HardwareAcquisitionConfigSearchHelper.__active = true;
					final StructMember[] _members0 = new StructMember[5];
					TypeCode _tcOf_members0 = null;
					_tcOf_members0 = VTDateTimeSearchHelper.type();
					_members0[0] = new StructMember("startTimeSearch", _tcOf_members0, null);
					_tcOf_members0 = VTFrequencySearchHelper.type();
					_members0[1] = new StructMember("frequencySearch", _tcOf_members0, null);
					_tcOf_members0 = VTChannelAcquisitionConfigSearchListHelper.type();
					_members0[2] = new StructMember("channelsConfigSearch", _tcOf_members0, null);
					_tcOf_members0 = VTHardwareParameterConfigListHelper.type();
					_members0[3] = new StructMember("selected_hardware_parameters", _tcOf_members0, null);
					_tcOf_members0 = VTSamplesNumSearchHelper.type();
					_members0[4] = new StructMember("samplesNumSearch", _tcOf_members0, null);
					HardwareAcquisitionConfigSearchHelper.__typeCode = ORB.init().create_struct_tc(
							HardwareAcquisitionConfigSearchHelper.id(), "HardwareAcquisitionConfigSearch", _members0);
					HardwareAcquisitionConfigSearchHelper.__active = false;
				}
			}
		}
		return HardwareAcquisitionConfigSearchHelper.__typeCode;
	}

	public static String id() {
		return HardwareAcquisitionConfigSearchHelper._id;
	}

	public static HardwareAcquisitionConfigSearch read(final InputStream istream) {
		final HardwareAcquisitionConfigSearch value = new HardwareAcquisitionConfigSearch();
		value.setStartTimeSearch(VTDateTimeSearchHelper.read(istream));
		value.setFrequencySearch(VTFrequencySearchHelper.read(istream));
		value.setChannelsConfigSearch(VTChannelAcquisitionConfigSearchListHelper.read(istream));
		value.setSelectedHardwareParameters(VTHardwareParameterConfigListHelper.read(istream));
		value.setSamplesNumSearch(VTSamplesNumSearchHelper.read(istream));
		return value;
	}

	public static void write(final OutputStream ostream, final HardwareAcquisitionConfigSearch value) {
		VTDateTimeSearchHelper.write(ostream, value.getStartTimeSearch());
		VTFrequencySearchHelper.write(ostream, value.getFrequencySearch());
		VTChannelAcquisitionConfigSearchListHelper.write(ostream, value.getChannelsConfigSearch());
		VTHardwareParameterConfigListHelper.write(ostream, value.getSelectedHardwareParameters());
		VTSamplesNumSearchHelper.write(ostream, value.getSamplesNumSearch());
	}

}
