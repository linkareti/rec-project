package com.linkare.rec.data.acquisition;

public final class ByteArrayValue implements org.omg.CORBA.portable.IDLEntity
{
	private byte data[] = null;
	private String mimeType = null;
	
	/** Default Constructor */
	public ByteArrayValue()
	{
	}
	
	public ByteArrayValue(byte[] _data, String _mime_type)
	{
		data = _data;
		mimeType = _mime_type;
	}
	
	/** Copy Constructor */
	public ByteArrayValue(ByteArrayValue value)
	{
		this.data = new byte[value.getData().length];
		System.arraycopy(value.getData(),0,this.data,0,this.data.length);
		this.mimeType = new String(value.getMimeType());
	}
	
	public void setData(byte[] _data)
	{
		this.data = _data;
	}
	
	public void setData(int index, byte byteValue)
	{
		if(this.data!=null && index<this.data.length)
			this.data[index] = byteValue;
		else
		{
			byte[] temp=new byte[index+1];
			if(this.data!=null)
				System.arraycopy(this.data,0,temp,0,this.data.length);
			
			temp[index]=byteValue;
			this.data = temp;
			temp=null;
			System.gc();
		}
	}
	
	public byte[] getData()
	{
		return this.data;
	}
	
	public byte getData(int index)
	{
		return this.data[index];
	}
	
	public void setMimeType(String _mime_type)
	{
		this.mimeType = _mime_type;
	}
	
	public String getMimeType()
	{
		return this.mimeType;
	}
	
	public int getSize()
	{
		return this.data.length;
	}
	
	public boolean equals(Object obj)
	{
	    if(obj==null || ! (obj instanceof ByteArrayValue))
	    return false;
	
	    ByteArrayValue other=(ByteArrayValue)obj;
	
	    if(!other.getMimeType().equals(getMimeType())) return false;
	    
	    if(other.getData()==null && getData()==null) return true;
	    if(other.getData()==null) return false;
	    if(getData()==null) return false;
	    
	    if(other.getData().length!=getData().length) return false;
	    
	    boolean retVal=true;
	    for(int i=0;i<getData().length && retVal;i++)
		if(getData()[i]!=other.getData()[i])
		    retVal=false;
	    
	    return retVal;
	    
	}
	
} // class ByteArrayValue
