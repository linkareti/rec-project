package com.linkare.rec.impl.newface.component.media.transcoding;

/**
 * Codecs de vídeo suportados pelo módulo de transcoding do VLC.
 * @see <a href="http://www.videolan.org/streaming-features.html">
 * Formatos e Muxers suportados em trancoding</a>
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
