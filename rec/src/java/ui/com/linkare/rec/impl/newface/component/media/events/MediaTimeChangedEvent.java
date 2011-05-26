package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventObject;

/**
 * Represents the player's event of media time changing.
 */
public class MediaTimeChangedEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5993125250003158314L;

	public MediaTimeChangedEvent(final Object source) {
		super(source);
	}
}