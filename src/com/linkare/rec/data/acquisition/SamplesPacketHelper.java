package com.linkare.rec.data.acquisition;

abstract public class SamplesPacketHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/acquisitionData/SamplesPacket:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.acquisition.SamplesPacket that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.acquisition.SamplesPacket extract(org.omg.CORBA.Any a)
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.synch.VTDateTimeHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember(
					"time_start",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[1] = new org.omg.CORBA.StructMember(
					"packet_number",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[2] = new org.omg.CORBA.StructMember(
					"total_packets",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.acquisition.VTPhysicsValueMatrixHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember(
					"the_data",
					_tcOf_members0,
					null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.acquisition.SamplesPacketHelper.id(), "SamplesPacket", _members0);
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

	public static com.linkare.rec.data.acquisition.SamplesPacket read(org.omg.CORBA.portable.InputStream istream)
	{

		com.linkare.rec.data.acquisition.SamplesPacket new_one = new com.linkare.rec.data.acquisition.SamplesPacket();
		new_one.setTimeStart(com.linkare.rec.data.synch.VTDateTimeHelper.read(istream));
		new_one.setPacketNumber(istream.read_long());
		new_one.setTotalPackets(istream.read_long());
		new_one.setData((com.linkare.rec.data.acquisition.PhysicsValue[][]) com.linkare.rec.data.acquisition.VTPhysicsValueMatrixHelper.read(istream));

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.acquisition.SamplesPacket value)
	{
		com.linkare.rec.data.synch.VTDateTimeHelper.write(ostream, value.getTimeStart());
		ostream.write_long(value.getPacketNumber());
		ostream.write_long(value.getTotalPackets());
		com.linkare.rec.data.acquisition.VTPhysicsValueMatrixHelper.write(ostream, value.getData());
	}

}
