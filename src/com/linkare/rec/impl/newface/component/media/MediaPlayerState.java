package com.linkare.rec.impl.newface.component.media;

/**
 * Estados do player.
 * @author bcatarino
 */
public enum MediaPlayerState {

    // O Player n�o tem nenhum media associado.
    EMPTY,

    // O Player est� parado.
    STOPPED,
    
    // O Player est� a reproduzir.
    PLAYING,

    // O Player est� em pausa.
    PAUSED;

}
