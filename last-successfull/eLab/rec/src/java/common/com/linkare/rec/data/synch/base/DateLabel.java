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
public class DateLabel extends javax.swing.JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5917134646068781819L;
	/** Holds value of property date. */
	private com.linkare.rec.data.synch.Date date = new com.linkare.rec.data.synch.Date();

	/** Creates a new instance of DateLabel */
	public DateLabel() {
	}

	@Override
	public String getText() {
		if (date == null) {
			return "";
		}
		return date.toString();
	}

	@Override
	public void setText(final String str) {
	}

	/**
	 * Getter for property date.
	 * 
	 * @return Value of property date.
	 */
	public com.linkare.rec.data.synch.Date getDate() {
		return date;
	}

	/**
	 * Setter for property date.
	 * 
	 * @param date New value of property date.
	 */
	public void setDate(final com.linkare.rec.data.synch.Date date) {
		this.date = date;
		repaint();
	}

}
