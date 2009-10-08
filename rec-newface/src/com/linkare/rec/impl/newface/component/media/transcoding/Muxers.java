package com.linkare.rec.impl.newface.component.media.transcoding;

/**
 * Muxers suportados pelo módulo de transcoding do VLC.
 * 
 * @see <a href="http://www.videolan.org/streaming-features.html"> Formatos e Muxers suportados em trancoding</a>
 * @author bcatarino
 */
public enum Muxers {
    MP4;
    //TODO acrescentar restantes muxers suportados pelo VLC em trancoding.

    @Override
    public String toString() {
	return this.name().toLowerCase();
    }
}
