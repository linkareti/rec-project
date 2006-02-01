package com.linkare.rec.repository;

import java.io.Serializable;
import org.omg.CORBA.*;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class VTSamplesNumSearchHelper implements BoxedValueHelper
{
    private static String  _id = "IDL:com/linkare/rec/repository/VTSamplesNumSearch:1.0";
    
    private static VTSamplesNumSearchHelper _instance = new VTSamplesNumSearchHelper();
    
    
    public VTSamplesNumSearchHelper()
    {
    }
    
    public static void insert(Any a, SamplesNumSearch that)
    {
	org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
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
		    __typeCode = SamplesNumSearchHelper.type();
		    __typeCode = ORB.init().create_value_box_tc(_id, "VTSamplesNumSearch", __typeCode);
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
    
    public static SamplesNumSearch read(org.omg.CORBA.portable.InputStream istream)
    {
	if (!(istream instanceof InputStream))
	{
	    throw new BAD_PARAM(); }
	return (SamplesNumSearch) ((InputStream) istream).read_value(_instance);
    }
    
    public Serializable read_value(org.omg.CORBA.portable.InputStream istream)
    {
	SamplesNumSearch tmp = SamplesNumSearchHelper.read(istream);
	return (Serializable) tmp;
    }
    
    public static void write(org.omg.CORBA.portable.OutputStream ostream, SamplesNumSearch value)
    {
	if (!(ostream instanceof OutputStream))
	{
	    throw new BAD_PARAM(); }
	((OutputStream) ostream).write_value(value, _instance);
    }
    
    public void write_value(org.omg.CORBA.portable.OutputStream ostream, Serializable value)
    {
	if (!(value instanceof SamplesNumSearch))
	{
	    throw new MARSHAL(); }
	SamplesNumSearch valueType = (SamplesNumSearch) value;
	SamplesNumSearchHelper.write(ostream, valueType);
    }
    
    public String get_id()
    {
	return _id;
    }
    
}
