package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventObject;

/**
 * Event that represents the impossibility to connect to media, because no more
 * elementary streams were received.
 * @author bcatarino
 */
public class MediaNotConnectedEvent extends EventObject {

    public MediaNotConnectedEvent(Object source) {
        super(source);
    }

}
