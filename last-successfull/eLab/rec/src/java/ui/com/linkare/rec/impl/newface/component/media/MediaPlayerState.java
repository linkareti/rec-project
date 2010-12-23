package com.linkare.rec.impl.newface.component.media;

/**
 * Estados do player.
 * 
 * @author bcatarino
 */
public enum MediaPlayerState {

	// O Player não tem nenhum media associado.
	EMPTY,

	// O Player está parado.
	STOPPED,

    // O player parou de reproduzir porque não recebeu mais streams.
    STOPPED_BY_NO_MORE_ES,
    
	// O Player está a reproduzir.
	PLAYING,

	// O Player está em pausa.
	PAUSED;
}
