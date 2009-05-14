package com.linkare.rec.data.acquisition;

public final class SamplesPacket implements org.omg.CORBA.portable.IDLEntity
{
	//
	// Struct member packet_number
	//
	private int packet_number;

	//
	// Struct member total_packets
	//
	private int total_packets;

	//
	// Struct member the_data
	//
	private com.linkare.rec.data.acquisition.PhysicsValue[][] the_data;


	//when this packet started!
	private com.linkare.rec.data.synch.DateTime time_start = null;

	//
	// Default constructor
	//
	public SamplesPacket()
	{
		this(0,0,null,null);
	}

	//
	// Constructor with fields initialization
	// @param	packet_number	packet_number struct member
	// @param	total_packets	total_packets struct member
	//
	public SamplesPacket( int packet_number, int total_packets)
	{
		this(packet_number,total_packets,null,null);
	}

	
	//
	// Constructor with fields initialization
	// @param	packet_number	packet_number struct member
	// @param	total_packets	total_packets struct member
	// @param	the_data	the_data struct member
	//
	public SamplesPacket( int packet_number, int total_packets, com.linkare.rec.data.acquisition.PhysicsValue[][] the_data )
	{
		this(packet_number,total_packets,the_data,null);
	}

	//
	// Constructor with fields initialization
	// @param	packet_number	packet_number struct member
	// @param	total_packets	total_packets struct member
	// @param	the_data	the_data struct member
	// @param	time_start	time_start struct member
	//
	public SamplesPacket( int packet_number, int total_packets, com.linkare.rec.data.acquisition.PhysicsValue[][] the_data , com.linkare.rec.data.synch.DateTime time_start )
	{
		setPacketNumber(packet_number);
		setTotalPackets(total_packets);
		setData(the_data);
		setTimeStart(time_start);
	}


	//
	// Copy Constructor
	//
	public SamplesPacket( SamplesPacket other)
	{
		this(other.getPacketNumber(),other.getTotalPackets(),null,null);
		copyData(other.the_data);
		copyTimeStart(other.time_start);
	}


	/** Getter for property packet_number.
	 * @return Value of property packet_number.
	 */
	public int getPacketNumber()
	{
		return this.packet_number;
	}

	/** Setter for property packet_number.
	 * @param packet_number New value of property packet_number.
	 */
	public void setPacketNumber(int packet_number)
	{
		this.packet_number = packet_number;
	}

	/** Getter for property packet_number.
	 * @return Value of property packet_number.
	 */
	public int getTotalPackets()
	{
		return this.total_packets;
	}

	/** Setter for property packet_number.
	 * @param packet_number New value of property packet_number.
	 */
	public void setTotalPackets(int total_packets)
	{
		this.total_packets = total_packets;
	}



	/** Getter for property time_start.
	 * @return Value of property time_start.
	 */
	public com.linkare.rec.data.synch.DateTime getTimeStart()
	{
		return this.time_start ;
	}

	/** Setter for property time_start.
	 * @param time_start New value of property time_start.
	 */
	public void setTimeStart(com.linkare.rec.data.synch.DateTime time_start)
	{
		this.time_start= time_start;
	}

	/** Indexed getter for property the_data.
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.acquisition.PhysicsValue[] getData(int index)
	{
	   
		if(the_data== null || index>the_data.length)
			throw new RuntimeException("index doesn't exist in the_data array");

		return this.the_data[index];
	}

	/** Double Indexed getter for property the_data.
	 * @param index Index of the property.
	 * @param index2 Second Index of the property.
	 * @return Value of the property at <CODE>index</CODE>,<CODE>index2</CODE>.
	 */
	public com.linkare.rec.data.acquisition.PhysicsValue getData(int index,int index2)
	{
		if(the_data== null || index>the_data.length)
			throw new RuntimeException("index doesn't exist in the_data array");

		if(the_data[index]== null || index2>=the_data[index].length)
			throw new RuntimeException("index2 doesn't exist in the_data array");

		return this.the_data[index][index2];
	}

	/** Getter for property the_data.
	 * @return Value of property the_data.
	 */
	public com.linkare.rec.data.acquisition.PhysicsValue[][] getData()
	{
		return this.the_data;
	}

	/** Indexed setter for property the_data.
	 * @param index Index of the property.
	 * @param the_data New value of the property at <CODE>index</CODE>.
	 */
	public void setData(int index, com.linkare.rec.data.acquisition.PhysicsValue[] the_data)
	{
		if(this.the_data!=null && index<this.the_data.length)
		{
		    this.the_data[index] = the_data;
		}
		
		else
		{
			com.linkare.rec.data.acquisition.PhysicsValue[][] temp=new com.linkare.rec.data.acquisition.PhysicsValue[index+1][];
			if(this.the_data!=null)
				System.arraycopy(this.the_data,0,temp,0,this.the_data.length);

			temp[index]=the_data;
		}

	}

	
	/** Setter for property the_data.
	 * @param the_data New value of property the_data.
	 */
	public void setData(com.linkare.rec.data.acquisition.PhysicsValue[][] the_data)
	{
		this.the_data = the_data;
	}

	private void copyTimeStart(com.linkare.rec.data.synch.DateTime time_start)
	{
		this.time_start=new com.linkare.rec.data.synch.DateTime(time_start);
	}


	private void copyData(com.linkare.rec.data.acquisition.PhysicsValue[][] the_data)
	{
		this.the_data=new com.linkare.rec.data.acquisition.PhysicsValue[the_data.length][];

		for(int i=0;i<the_data.length;i++)
		{
			this.the_data[i]=new com.linkare.rec.data.acquisition.PhysicsValue[the_data[i].length];
			for(int j=0;j<the_data[i].length;j++)
				this.the_data[i][j]=new com.linkare.rec.data.acquisition.PhysicsValue(the_data[i][j]);
		}
	}
	
	
} // class SamplesPacket
