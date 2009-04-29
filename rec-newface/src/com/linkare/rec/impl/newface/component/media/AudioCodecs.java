package com.linkare.rec.impl.newface.component.media;

/**
 * Codecs de audio suportados pelo módulo de transcoding do VLC.
 * @author bcatarino
 */
public enum AudioCodecs {
    MP4A,
    MPGA;

    //TODO acrescentar restantes codecs suportados pelo VLC em trancoding.

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
