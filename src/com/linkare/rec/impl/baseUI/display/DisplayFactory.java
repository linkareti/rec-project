/*
 * DisplayFactory.java
 *
 * Created on August 2, 2004, 4:56 PM
 */

package com.linkare.rec.impl.baseUI.display;

/**
 *
 * @author  andre
 */

import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;

public interface DisplayFactory
{
    public void init(Display[] displays);
    public void setAcquisitionInfo(HardwareInfo hinfo);
    public void setAcquisitionConfig(HardwareAcquisitionConfig hconfig);
    public ExpDataDisplay[] getDisplays();
    public void destroyDisplays();
}
