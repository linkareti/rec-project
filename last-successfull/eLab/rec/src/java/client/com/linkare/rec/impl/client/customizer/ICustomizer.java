package com.linkare.rec.impl.client.customizer;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;

/**
 * What customizers need to implement to be able to correctly set up the
 * graphical user interface for an apparatus and to inform other bits of code
 * that the experiment is OK to be used and has been customized to the
 * particular hardware it is meant to deal with
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ICustomizer extends CustomizerInfo {

	/**
	 * The framework will call this method on your customizer, so you can fire
	 * some events when interesting things happen in your customizer interface.
	 * 
	 * @param l The Listener the framework is registering
	 */
	public void addICustomizerListener(ICustomizerListener l);

	/**
	 * The framework will call this method on your customizer, when it is no
	 * longer interested in listening to events from your customizer
	 * 
	 * @param l The Listener the framework is registering
	 */
	public void removeICustomizerListener(ICustomizerListener l);

	/**
	 * You should return a visual component that must be installed on the global
	 * UI
	 * 
	 * @return
	 */
	javax.swing.JComponent getCustomizerComponent();

	javax.swing.JMenuBar getMenuBar();

	/**
	 * We are setting the value boundaries accordingly to whats in the XML file
	 * -> HardwareInfo This method precedes the @see
	 * setHardwareAcquisitionConfig(HardwareAcquisitionConfig) and is meant to
	 * configure only the graphical interface to whatever the XML says the
	 * hardware can do
	 * 
	 * @param info the object where we retrieve our information from.
	 *            HardwareInfo is a kind of XML Meta data Wrapper to allow us to
	 *            more contextually read that hardware descriptor ->
	 *            HardwareInfo.xml
	 */
	void setHardwareInfo(HardwareInfo info);

	/**
	 * This method is meant to configure the initial values on the graphical
	 * interface to whatever the hardware is currently set to. Basically here we
	 * use the HardwareAquisitionConfig as a proxy to talk to the hardware
	 * itself and retrieve status information. When an experiment is started we
	 * need to align the interface with both the statical information on the
	 * hardware abilities that is done on the later method @see
	 * setHardwareInfo(HardwareInfo) and only after can we set information such
	 * as what is the current status of the apparatus now... That is done here
	 * 
	 * @param config Our proxy to talk to the hardware itself
	 */
	void setHardwareAcquisitionConfig(HardwareAcquisitionConfig config);

	HardwareAcquisitionConfig getAcquisitionConfig();
}