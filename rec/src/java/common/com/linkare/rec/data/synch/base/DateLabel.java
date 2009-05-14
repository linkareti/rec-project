/*
 * DateLabel.java
 *
 * Created on 27 de Junho de 2002, 18:57
 */

package com.linkare.rec.data.synch.base;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class DateLabel extends javax.swing.JLabel
{

	/** Holds value of property date. */
	private com.linkare.rec.data.synch.Date date=new com.linkare.rec.data.synch.Date();

	/** Creates a new instance of DateLabel */
	public DateLabel()
	{
	}

	public String getText()
	{
		if(date==null) return "";
		return date.toString();
	}

	public void setText(String str)
	{
	}

	/** Getter for property date.
	 * @return Value of property date.
	 */
	public com.linkare.rec.data.synch.Date getDate()
	{
		return this.date;
	}

	/** Setter for property date.
	 * @param date New value of property date.
	 */
	public void setDate(com.linkare.rec.data.synch.Date date)
	{
		this.date = date;
		repaint();
	}

}
