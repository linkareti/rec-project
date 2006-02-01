/*
 * ICustomizer.java
 *
 * Created on 8 de Maio de 2003, 14:11
 */

package com.linkare.rec.impl.client.customizer;

import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
/**
 *
 * @author  jp
 */
public interface ICustomizer extends CustomizerInfo
{
	public void addICustomizerListener(ICustomizerListener l);
	public void removeICustomizerListener(ICustomizerListener l);

	javax.swing.JComponent getCustomizerComponent();
	javax.swing.JMenuBar getMenuBar();

	void setHardwareInfo(HardwareInfo info);
	void setHardwareAcquisitionConfig(HardwareAcquisitionConfig config);
	HardwareAcquisitionConfig getAcquisitionConfig();
}
