package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventObject;

/**
 * Represents the event of the media being stopped.
 * 
 * @author bcatarino
 */
public class MediaStoppedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3677208682116460474L;

	public MediaStoppedEvent(final Object source) {
		super(source);
	}
}
