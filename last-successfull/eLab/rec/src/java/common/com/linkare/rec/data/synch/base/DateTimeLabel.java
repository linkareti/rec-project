/*
 * DateTimeLabel.java
 *
 * Created on 27 de Junho de 2002, 18:53
 */

package com.linkare.rec.data.synch.base;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DateTimeLabel extends javax.swing.JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2326720049576055866L;
	/** Holds value of property dateTime. */
	private com.linkare.rec.data.synch.DateTime dateTime;

	/** Creates a new instance of DateTimeLabel */
	public DateTimeLabel() {
	}

	@Override
	public String getText() {
		if (dateTime == null) {
			return "";
		}
		return dateTime.toString();
	}

	@Override
	public void setText(final String str) {
	}

	/**
	 * Getter for property dateTime.
	 * 
	 * @return Value of property dateTime.
	 */
	public com.linkare.rec.data.synch.DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Setter for property dateTime.
	 * 
	 * @param dateTime New value of property dateTime.
	 */
	public void setDateTime(final com.linkare.rec.data.synch.DateTime dateTime) {
		this.dateTime = dateTime;
		repaint();
	}

}
