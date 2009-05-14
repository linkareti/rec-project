package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

abstract public class SamplesNumSearchHelper
{
    private static String  _id = "IDL:com/linkare/rec/repository/SamplesNumSearch:1.0";
    
    public static void insert(Any a, SamplesNumSearch that)
    {
	OutputStream out = a.create_output_stream();
	a.type(type());
	write(out, that);
	a.read_value(out.create_input_stream(), type());
    }
    
    public static SamplesNumSearch extract(Any a)
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
		    StructMember[] _members0 = new StructMember [2];
		    TypeCode _tcOf_members0 = null;
		    _tcOf_members0 = VTLongHelper.type();
		    _members0[0] = new StructMember(
		    "minSamplesNum",
		    _tcOf_members0,
		    null);
		    _tcOf_members0 = VTLongHelper.type();
		    _members0[1] = new StructMember(
		    "maxSamplesNum",
		    _tcOf_members0,
		    null);
		    __typeCode = ORB.init().create_struct_tc(SamplesNumSearchHelper.id(), "SamplesNumSearch", _members0);
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
    
    public static SamplesNumSearch read(InputStream istream)
    {
	SamplesNumSearch value = new SamplesNumSearch();
	value.setMinSamplesNum(VTLongHelper.read(istream));
	value.setMaxSamplesNum(VTLongHelper.read(istream));
	return value;
    }
    
    public static void write(OutputStream ostream, SamplesNumSearch value)
    {
	VTLongHelper.write(ostream, value.getMinSamplesNum());
	VTLongHelper.write(ostream, value.getMaxSamplesNum());
    }
    
}
