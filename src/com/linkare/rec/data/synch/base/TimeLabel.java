/*
 * TimeLabel.java
 *
 * Created on 27 de Junho de 2002, 18:55
 */

package com.linkare.rec.data.synch.base;

/**
 *
 * @author  jp
 */
public class TimeLabel extends javax.swing.JLabel
{

	/** Holds value of property time. */
	private com.linkare.rec.data.synch.Time time;

	/** Creates a new instance of TimeLabel */
	public TimeLabel()
	{
	}

	public String getText()
	{
		if(time==null) return "";
		else return time.toString();
	}

	public void setText(String str)
	{
	}

	/** Getter for property time.
	 * @return Value of property time.
	 */
	public com.linkare.rec.data.synch.Time getTime()
	{
		return this.time;
	}

	/** Setter for property time.
	 * @param time New value of property time.
	 */
	public void setTime(com.linkare.rec.data.synch.Time time)
	{
		this.time = time;
		repaint();
	}

}
