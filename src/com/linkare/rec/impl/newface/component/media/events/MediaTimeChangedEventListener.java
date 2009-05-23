package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventListener;

public interface MediaTimeChangedEventListener extends EventListener {
    void timeChanged(MediaTimeChangedEvent evt);
}
