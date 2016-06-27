package com.linkare.rec.impl.client.experiment;

/*
 * NewSamplesEvent.java
 *
 * Created on 7 de Maio de 2003, 12:02
 */

import com.linkare.rec.impl.utils.IntersectableEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 *         ->Changed by Andre on 16/07/04; intersectTo
 * 
 */
public class NewExpDataEvent extends java.util.EventObject implements IntersectableEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 574403812294394017L;

	/** Holds value of property samplesStartRow. */
	private int samplesStartIndex;

	/** Holds value of property samplesEndIndex. */
	private int samplesEndIndex;

	/** Creates a new instance of NewSamplesEvent */
	public NewExpDataEvent(final Object source, final int samplesStartIndex, final int samplesEndIndex) {
		super(source);
		this.samplesStartIndex = samplesStartIndex;
		this.samplesEndIndex = samplesEndIndex;
	}

	/**
	 * Getter for property samplesStartRow.
	 * 
	 * @return Value of property samplesStartRow.
	 */
	public int getSamplesStartIndex() {
		return samplesStartIndex;
	}

	/**
	 * Getter for property samplesEndIndex.
	 * 
	 * @return Value of property samplesEndIndex.
	 */
	public int getSamplesEndIndex() {
		return samplesEndIndex;
	}

	@Override
	public boolean intersectTo(final IntersectableEvent other) {
		if (!(other instanceof NewExpDataEvent)) {
			return false;
		}

		final NewExpDataEvent evt = (NewExpDataEvent) other;
		samplesStartIndex = Math.min(samplesStartIndex, evt.getSamplesStartIndex());
		samplesEndIndex = Math.max(samplesEndIndex, evt.getSamplesEndIndex());

		return true;
	}
}
