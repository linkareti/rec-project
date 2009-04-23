package com.linkare.rec.impl.newface.component.media;

/**
 * Muxers suportados pelo módulo de transcoding do VLC.
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
