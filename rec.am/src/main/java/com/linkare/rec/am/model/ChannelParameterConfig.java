package com.linkare.rec.am.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author artur
 */
@Entity
@DiscriminatorValue(value = "CHANNEL")
public class ChannelParameterConfig extends ParameterConfig {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "KEY_CHANNEL_ACQUISITION_CONFIG")
    private ChannelAcquisitionConfig channel;

    public ChannelParameterConfig() {
    }

    public ChannelAcquisitionConfig getChannel() {
	return channel;
    }

    public void setChannel(ChannelAcquisitionConfig channel) {
	this.channel = channel;
    }

}
