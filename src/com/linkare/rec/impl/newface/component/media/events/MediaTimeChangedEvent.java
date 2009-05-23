package com.linkare.rec.impl.newface.component.media.events;

import java.util.EventObject;

/**
 * Classe que representa o evento de timeChanged sempre que o tempo do
 * player muda.
 */
public class MediaTimeChangedEvent extends EventObject {
    public MediaTimeChangedEvent(Object source) {
        super(source);
    }
}