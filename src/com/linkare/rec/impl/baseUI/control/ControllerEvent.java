/*
 * ControlEvent.java
 *
 * Created on July 27, 2004, 10:30 AM
 */

package com.linkare.rec.impl.baseUI.control;

/**
 *
 * @author  andre
 */
public class ControllerEvent extends java.util.EventObject
{    
    public static final byte PLAY = 0;
    public static final byte STOP = 1;
    public static final byte CUSTOMIZE = 2;
    public static final byte CONNECT = 3;
    public static final byte AUTOPLAY = 4;
    public static final byte VIDEO = 5;
    public static final byte CHAT = 6;
    public static final byte TREE = 7;
    public static final byte USERS = 8;
    public static final byte PROGRESS_END = 9;
    public static final byte EXIT = 10;
    public static final byte CASCADE = 11;
    public static final byte TILE = 12;
    public static final byte ABOUT = 13;
    public static final byte CONTENTS = 14;
    public static final byte HISTORY = 15;
    public static final byte LOGIN_CLOSED = 16;
    
    private byte trigger = -1;
    private String customTrigger = "";
    
    public ControllerEvent(Object source)
    {
	super(source);
    }
    
    /** Creates a new instance of ControlEvent */
    public ControllerEvent(Object source, byte trigger)
    {
	super(source);
	this.trigger = trigger;
    }
    
    /** Creates a new instance of ControlEvent */
    public ControllerEvent(Object source, String customTrigger)
    {
	super(source);
	this.customTrigger = customTrigger;
    }
    
    public byte getTrigger()
    {
	return trigger;
    }
    
    public String getCustomTrigger()
    {
	return customTrigger;
    }
}
