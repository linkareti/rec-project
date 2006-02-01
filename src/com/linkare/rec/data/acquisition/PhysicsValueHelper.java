package com.linkare.rec.data.acquisition;

abstract public class PhysicsValueHelper
{
    private static String  _id = "IDL:com/linkare/rec/data/acquisitionData/PhysicsValue:1.0";
    
    public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.acquisition.PhysicsValue that)
    {
	org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
	a.type(type());
	write(out, that);
	a.read_value(out.create_input_stream(), type());
    }
    
    public static com.linkare.rec.data.acquisition.PhysicsValue extract(org.omg.CORBA.Any a)
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
		    org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
		    org.omg.CORBA.TypeCode _tcOf_members0 = null;
		    _tcOf_members0 = com.linkare.rec.data.acquisition.PhysicsValHelper.type();
		    _members0[0] = new org.omg.CORBA.StructMember(
		    "the_value",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = com.linkare.rec.data.acquisition.VTPhysicsValHelper.type();
		    _members0[1] = new org.omg.CORBA.StructMember(
		    "the_error",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = com.linkare.rec.data.MultiplierHelper.type();
		    _members0[2] = new org.omg.CORBA.StructMember(
		    "applied_multiplier",
		    _tcOf_members0,
		    null);
		    __typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.acquisition.PhysicsValueHelper.id(), "PhysicsValue", _members0);
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
    
    public static com.linkare.rec.data.acquisition.PhysicsValue read(org.omg.CORBA.portable.InputStream istream)
    {
	com.linkare.rec.data.acquisition.PhysicsValue new_one = new com.linkare.rec.data.acquisition.PhysicsValue();
	
	new_one.setValue( com.linkare.rec.data.acquisition.PhysicsValHelper.read(istream));
	new_one.setError(com.linkare.rec.data.acquisition.VTPhysicsValHelper.read(istream));
	new_one.setAppliedMultiplier(com.linkare.rec.data.MultiplierHelper.read(istream));
	
	return new_one;
    }
    
    public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.acquisition.PhysicsValue value)
    {
	com.linkare.rec.data.acquisition.PhysicsValHelper.write(ostream,value.getValue());
	com.linkare.rec.data.acquisition.VTPhysicsValHelper.write(ostream,value.getError());
	com.linkare.rec.data.MultiplierHelper.write(ostream,value.getAppliedMultiplier());
    }
    
}
