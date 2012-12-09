/* 
 * PrioritazibleBuffer.java created on 26 May 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.aleatorio.Hardware;

import javax.media.Buffer;

import com.linkare.rec.impl.events.Prioritazible;
import com.linkare.rec.impl.threading.util.EnumPriority;

public class PrioritazibleBuffer implements Prioritazible {
	private Buffer buffer = null;

	/**
	 * Creates the
	 * <code>DataSourceReader.DataSourceHandler.PrioritazibleBuffer</code> .
	 * 
	 * @param buffer
	 */
	public PrioritazibleBuffer(final Buffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MAXIMUM;
	}

	public Buffer getBuffer() {
		return buffer;
	}
}