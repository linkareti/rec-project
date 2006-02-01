package com.linkare.rec.acquisition;

public class HardwareState implements org.omg.CORBA.portable.IDLEntity
{
    /**
     * Internal member value
     */
    private byte value;
    
    /**
     * Enum member UNKNOWN value
     */
    public static final byte _UNKNOWN = 0;
    
    /**
     * Enum member UNKNOWN
     */
    public static final HardwareState UNKNOWN = new HardwareState(_UNKNOWN);
    
    /**
     * Enum member CONFIGURING value
     */
    public static final byte _CONFIGURING = 1;
    
    /**
     * Enum member CONFIGURING
     */
    public static final HardwareState CONFIGURING = new HardwareState(_CONFIGURING);
    
    /**
     * Enum member CONFIGURED value
     */
    public static final byte _CONFIGURED = 2;
    
    /**
     * Enum member CONFIGURED
     */
    public static final HardwareState CONFIGURED = new HardwareState(_CONFIGURED);
    
    /**
     * Enum member STARTING value
     */
    public static final byte _STARTING = 3;
    
    /**
     * Enum member STARTING
     */
    public static final HardwareState STARTING = new HardwareState(_STARTING);
    
    /**
     * Enum member STARTED value
     */
    public static final byte _STARTED = 4;
    
    /**
     * Enum member STARTED
     */
    public static final HardwareState STARTED = new HardwareState(_STARTED);
    
    /**
     * Enum member STOPING value
     */
    public static final byte _STOPING = 5;
    
    /**
     * Enum member STOPING
     */
    public static final HardwareState STOPING = new HardwareState(_STOPING);
    
    /**
     * Enum member STOPED value
     */
    public static final byte _STOPED = 6;
    
    /**
     * Enum member STOPED
     */
    public static final HardwareState STOPED = new HardwareState(_STOPED);
    
    /**
     * Enum member RESETING value
     */
    public static final byte _RESETING = 7;
    
    /**
     * Enum member RESETING
     */
    public static final HardwareState RESETING = new HardwareState(_RESETING);
    
    /**
     * Enum member RESETED value
     */
    public static final byte _RESETED = 8;
    
    /**
     * Enum member RESETED
     */
    public static final HardwareState RESETED = new HardwareState(_RESETED);
    
    
    
    
    /**
     * Return a string representation
     * @return	a string representation of the enumeration
     */
    public java.lang.String toString()
    {
	switch ( getValue() )
	{
	    case 0 :
		return "UNKNOWN";
	    case 1 :
		return "CONFIGURING";
	    case 2 :
		return "CONFIGURED";
	    case 3 :
		return "STARTING";
	    case 4 :
		return "STARTED";
	    case 5 :
		return "STOPING";
	    case 6 :
		return "STOPED";
	    case 7 :
		return "RESETING";
	    case 8 :
		return "RESETED";
	}
	throw new org.omg.CORBA.BAD_OPERATION();
    }
    
    /** Getter for property value.
     * @return Value of property value.
     *
     */
    public byte getValue()
    {
	return value;
    }
    
    /** Setter for property value.
     * @param value New value of property value.
     *
     */
    public void setValue(byte value)
    {
	if(value==_UNKNOWN || value==_CONFIGURING || value==_CONFIGURED
	||  value==_STARTING || value==_STARTED || value==_STOPING
	||  value==_STOPED || value==_RESETING || value==_RESETED)
	    this.value=value;
	else
	    throw new org.omg.CORBA.BAD_OPERATION();
    }
    
    public HardwareState()
    {
	setValue(_UNKNOWN);
    }
    public HardwareState(byte value)
    {
	setValue(value);
    }
    public HardwareState(HardwareState other)
    {
	if(other!=null)
	    setValue(other.getValue());
	else
	    setValue(_UNKNOWN);
    }
    
    
    
    public boolean equals(Object other)
    {
	if(other==null || !(other instanceof HardwareState)) return false;
	
	return ((HardwareState)other).getValue()==getValue();
    }
    
    
} // class HardwareState
