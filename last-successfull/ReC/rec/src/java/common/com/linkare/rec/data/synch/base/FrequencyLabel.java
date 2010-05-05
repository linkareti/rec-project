/*
 * FrequencyLabel.java
 *
 * Created on 27 de Junho de 2002, 18:50
 */

package com.linkare.rec.data.synch.base;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class FrequencyLabel extends javax.swing.JLabel {

	/** Holds value of property frequency. */
	private com.linkare.rec.data.synch.Frequency frequency;

	/** Creates a new instance of FrequencyLabel */
	public FrequencyLabel() {
	}

	public String getText() {
		if (this.frequency == null)
			return "";

		return this.frequency.toString();
	}

	public void setText(String str) {
	}

	/**
	 * Getter for property frequency.
	 * 
	 * @return Value of property frequency.
	 */
	public com.linkare.rec.data.synch.Frequency getFrequency() {
		return this.frequency;
	}

	/**
	 * Setter for property frequency.
	 * 
	 * @param frequency New value of property frequency.
	 */
	public void setFrequency(com.linkare.rec.data.synch.Frequency frequency) {
		this.frequency = frequency;
		repaint();
	}

}
