/*
 * TimeLabel.java
 *
 * Created on 27 de Junho de 2002, 18:55
 */

package com.linkare.rec.data.synch.base;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class TimeLabel extends javax.swing.JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5767322376038801008L;
	/** Holds value of property time. */
	private com.linkare.rec.data.synch.Time time;

	/** Creates a new instance of TimeLabel */
	public TimeLabel() {
	}

	@Override
	public String getText() {
		if (time == null) {
			return "";
		} else {
			return time.toString();
		}
	}

	@Override
	public void setText(final String str) {
	}

	/**
	 * Getter for property time.
	 * 
	 * @return Value of property time.
	 */
	public com.linkare.rec.data.synch.Time getTime() {
		return time;
	}

	/**
	 * Setter for property time.
	 * 
	 * @param time New value of property time.
	 */
	public void setTime(final com.linkare.rec.data.synch.Time time) {
		this.time = time;
		repaint();
	}

}
