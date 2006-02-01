/*
 * AcquisitionHeaderDisplay.java
 *
 * Created on 9 de Maio de 2003, 12:21
 */

package com.linkare.rec.impl.baseUI;

import javax.swing.JComponent;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
/**
 *
 * @author  jp
 */
public interface AcquisitionHeaderDisplay
{
	public JComponent getDisplayComponent();

	public void setAcquisitionHeader(HardwareAcquisitionConfig header);
	public HardwareAcquisitionConfig getAcquisitionHeader();
}
