package com.linkare.rec.impl.newface.component.media.transcoding;

/**
 * Define as opções de transcoding a serem usadas pelo VLM.
 * @author bcatarino
 */
public class TranscodingConfig {

    private Muxers muxer;
    private VideoCodecs videoCodec;
    private AudioCodecs audioCodec;
    private int videoBitrate;
    private int videoScale;
    private int audioBitrate;
    private int soundChannels;

    //TODO ver que outras opções se pode passar ao VLM e se vale a pena implementar

    public int getAudioBitrate() {
        return audioBitrate;
    }

    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public AudioCodecs getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(AudioCodecs audioCodec) {
        this.audioCodec = audioCodec;
    }

    public Muxers getMuxer() {
        return muxer;
    }

    public void setMuxer(Muxers muxer) {
        this.muxer = muxer;
    }

    public int getSoundChannels() {
        return soundChannels;
    }

    public void setSoundChannels(int soundChannels) {
        this.soundChannels = soundChannels;
    }

    public int getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public VideoCodecs getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(VideoCodecs videoCodec) {
        this.videoCodec = videoCodec;
    }

    public int getVideoScale() {
        return videoScale;
    }

    public void setVideoScale(int videoScale) {
        this.videoScale = videoScale;
    }
}
