package com.linkare.rec.impl.newface.component.media;

/**
 * Codecs de vídeo suportados pelo módulo de transcoding do VLC.
 * @author bcatarino
 */
public enum VideoCodecs {
    MP4V;
    //TODO acrescentar restantes codecs suportados pelo VLC em trancoding.

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
