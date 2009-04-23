package com.linkare.rec.impl.newface.component.media;

/**
 * Estados do player.
 * @author bcatarino
 */
public enum MediaPlayerState {

    // O Player não tem nenhum media associado.
    EMPTY,

    // O Player está parado.
    STOPPED,
    
    // O Player está a reproduzir.
    PLAYING,

    // O Player está em pausa.
    PAUSED;
}
