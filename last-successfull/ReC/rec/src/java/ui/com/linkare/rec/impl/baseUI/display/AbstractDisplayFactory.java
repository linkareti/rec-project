/*
 * AbstractDisplayFactory.java
 *
 * Created on August 2, 2004, 5:04 PM
 */

package com.linkare.rec.impl.baseUI.display;

/**
 * 
 * @author André Neto - LEFT - IST
 */

public abstract class AbstractDisplayFactory implements DisplayFactory {
	private com.linkare.rec.data.config.HardwareAcquisitionConfig hconfig = null;
	private com.linkare.rec.data.metadata.HardwareInfo hinfo = null;
	private com.linkare.rec.impl.baseUI.config.Display[] displays = null;

	@Override
	public abstract void destroyDisplays();

	@Override
	public abstract com.linkare.rec.impl.client.experiment.ExpDataDisplay[] getDisplays();

	@Override
	public void init(final com.linkare.rec.impl.baseUI.config.Display[] displays) {
		this.displays = displays;
	}

	public com.linkare.rec.impl.baseUI.config.Display[] getInitDisplays() {
		return displays;
	}

	@Override
	public void setAcquisitionConfig(final com.linkare.rec.data.config.HardwareAcquisitionConfig hconfig) {
		this.hconfig = hconfig;
	}

	@Override
	public void setAcquisitionInfo(final com.linkare.rec.data.metadata.HardwareInfo hinfo) {
		this.hinfo = hinfo;
	}

	public com.linkare.rec.data.config.HardwareAcquisitionConfig getAcquisitionConfig() {
		return hconfig;
	}

	public com.linkare.rec.data.metadata.HardwareInfo getAcquisitionInfo() {
		return hinfo;
	}
}
