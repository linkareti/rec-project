package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventListener;

/**
 * Defines the events fired by VLC that the client should handle.
 * 
 * @author bcatarino
 */
public interface MediaApplicationEventListener extends EventListener {

    /**
     * Fired when playing a streaming media and no ES where received.
     * 
     * @param evt
     */
    void notConnected(MediaStoppedEvent evt);

    void stopped(MediaStoppedEvent evt);

    void timeChanged(MediaTimeChangedEvent evt);
}
