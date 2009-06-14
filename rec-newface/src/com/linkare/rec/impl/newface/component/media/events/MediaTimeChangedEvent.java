package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventObject;

/**
 * Represents the player's event of media time changing.
 */
public class MediaTimeChangedEvent extends EventObject {
    public MediaTimeChangedEvent(Object source) {
        super(source);
    }
}