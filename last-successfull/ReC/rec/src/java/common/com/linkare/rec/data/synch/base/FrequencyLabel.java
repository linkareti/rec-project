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

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856699886301004982L;
	/** Holds value of property frequency. */
	private com.linkare.rec.data.synch.Frequency frequency;

	/** Creates a new instance of FrequencyLabel */
	public FrequencyLabel() {
	}

	@Override
	public String getText() {
		if (frequency == null) {
			return "";
		}

		return frequency.toString();
	}

	@Override
	public void setText(final String str) {
	}

	/**
	 * Getter for property frequency.
	 * 
	 * @return Value of property frequency.
	 */
	public com.linkare.rec.data.synch.Frequency getFrequency() {
		return frequency;
	}

	/**
	 * Setter for property frequency.
	 * 
	 * @param frequency New value of property frequency.
	 */
	public void setFrequency(final com.linkare.rec.data.synch.Frequency frequency) {
		this.frequency = frequency;
		repaint();
	}

}
