package com.linkare.rec.data.metadata;

abstract public class ScaleHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/metadata/Scale:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.metadata.Scale that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.metadata.Scale extract(org.omg.CORBA.Any a)
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [8];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.acquisition.PhysicsValHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember(
					"min_value",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.acquisition.PhysicsValHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember(
					"max_value",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.acquisition.PhysicsValHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember(
					"default_error",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.acquisition.PhysicsValHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember(
					"step",
					_tcOf_members0,
					null);
					_tcOf_members0 = com.linkare.rec.data.MultiplierHelper.type();
					_members0[4] = new org.omg.CORBA.StructMember(
					"applied_multiplier",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[5] = new org.omg.CORBA.StructMember(
					"scale_label",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[6] = new org.omg.CORBA.StructMember(
					"PhysicsUnitName",
					_tcOf_members0,
					null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[7] = new org.omg.CORBA.StructMember(
					"PhysicsUnitSymbol",
					_tcOf_members0,
					null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.metadata.ScaleHelper.id(), "Scale", _members0);
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

	public static com.linkare.rec.data.metadata.Scale read(org.omg.CORBA.portable.InputStream istream)
	{

		com.linkare.rec.data.metadata.Scale new_one = new com.linkare.rec.data.metadata.Scale();

		new_one.setMinimumValue( com.linkare.rec.data.acquisition.PhysicsValHelper.read(istream));
		new_one.setMaximumValue(com.linkare.rec.data.acquisition.PhysicsValHelper.read(istream));
		new_one.setDefaultErrorValue(com.linkare.rec.data.acquisition.PhysicsValHelper.read(istream));
		new_one.setStepValue(com.linkare.rec.data.acquisition.PhysicsValHelper.read(istream));
		new_one.setMultiplier(com.linkare.rec.data.MultiplierHelper.read(istream));
		new_one.setScaleLabel(org.omg.CORBA.WStringValueHelper.read(istream));
		new_one.setPhysicsUnitName(istream.read_wstring());
		new_one.setPhysicsUnitSymbol(istream.read_wstring());

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.metadata.Scale value)
	{
		com.linkare.rec.data.acquisition.PhysicsValHelper.write(ostream,value.getMinimumValue());
		com.linkare.rec.data.acquisition.PhysicsValHelper.write(ostream,value.getMaximumValue());
		com.linkare.rec.data.acquisition.PhysicsValHelper.write(ostream,value.getDefaultErrorValue());
		com.linkare.rec.data.acquisition.PhysicsValHelper.write(ostream,value.getStepValue());
		com.linkare.rec.data.MultiplierHelper.write(ostream,value.getMultiplier());
		org.omg.CORBA.WStringValueHelper.write(ostream,value.getScaleLabel());
		ostream.write_wstring(value.getPhysicsUnitName());
		ostream.write_wstring(value.getPhysicsUnitSymbol());

	}

}
