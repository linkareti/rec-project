package com.linkare.rec.data.acquisition;

public final class PhysicsValueListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.acquisition.PhysicsValue value[] = null;

	public PhysicsValueListHolder()
	{
	}

	public PhysicsValueListHolder(com.linkare.rec.data.acquisition.PhysicsValue[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.acquisition.PhysicsValueListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.acquisition.PhysicsValueListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.acquisition.PhysicsValueListHelper.type();
	}

}
