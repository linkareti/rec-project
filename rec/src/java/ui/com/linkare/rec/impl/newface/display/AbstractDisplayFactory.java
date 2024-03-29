package com.linkare.rec.impl.newface.display;

import java.util.List;

import com.linkare.rec.web.config.Display;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;

public abstract class AbstractDisplayFactory implements DisplayFactory {

	protected HardwareAcquisitionConfig hardwareAcquisitionConfig;
	protected HardwareInfo hardwareInfo;
	protected List<Display> displays;

	@Override
	public abstract void destroyDisplays();

	@Override
	public abstract List<ExpDataDisplay> getDisplays();

	public List<Display> getInitDisplays() {
		return displays;
	}

	public HardwareAcquisitionConfig getHardwareAcquisitionConfig() {
		return hardwareAcquisitionConfig;
	}

	public HardwareInfo getHardwareInfo() {
		return hardwareInfo;
	}

	@Override
	public void init(final List<Display> displays) {
		this.displays = displays;
	}

	@Override
	public void setAcquisitionConfig(final HardwareAcquisitionConfig hconfig) {
		hardwareAcquisitionConfig = hconfig;
	}

	@Override
	public void setAcquisitionInfo(final HardwareInfo hinfo) {
		hardwareInfo = hinfo;
	}

}
