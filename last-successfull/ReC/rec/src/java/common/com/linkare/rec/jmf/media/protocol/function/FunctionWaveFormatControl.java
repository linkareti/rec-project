package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

import javax.media.Format;
import javax.media.control.FormatControl;

public class FunctionWaveFormatControl implements FormatControl {

    /**
     * 
     */
    private final DataSource dataSource;

    /**
     * @param dataSource
     */
    FunctionWaveFormatControl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private boolean enabled = true;

    @Override
    public Component getControlComponent() {
        return null;
    }

    @Override
    public Format getFormat() {
        return dataSource.getAudioFormat();
    }

    @Override
    public Format setFormat(Format format) {
        return null;
    }

    @Override
    public Format[] getSupportedFormats() {
        return new Format[] { dataSource.getAudioFormat() };
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}