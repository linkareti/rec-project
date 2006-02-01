package com.linkare.rec.data.acquisition;

public final class PhysicsValueMatrixHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.acquisition.PhysicsValue[] value[] = null;

	public PhysicsValueMatrixHolder()
	{
	}

	public PhysicsValueMatrixHolder(com.linkare.rec.data.acquisition.PhysicsValue[][] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.type();
	}

}
