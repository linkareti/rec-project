package com.linkare.rec.impl.newface.display;

import java.util.List;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.newface.config.Display;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;

public interface DisplayFactory {

    public void init(List<Display> displays);

    public void setAcquisitionInfo(HardwareInfo hinfo);

    public void setAcquisitionConfig(HardwareAcquisitionConfig hconfig);

    public List<ExpDataDisplay> getDisplays();

    public void destroyDisplays();

}
