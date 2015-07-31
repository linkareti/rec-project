/*
 * WebRobotCustomizer.java
 *
 * Created on July 16, 2003, 3:24 PM
 */

package pt.utl.ist.elab.client.webrobot.customizer;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class WebRobotCustomizer implements com.linkare.rec.impl.client.customizer.ICustomizer {

	private final javax.swing.ImageIcon iconPrograf = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/webrobot/customizer/Icons/webrobot.gif"));
	private HardwareAcquisitionConfig acqConfig;
	private HardwareInfo hardwareInfo;
	private static String TITLE_VERSION = "JPrograf 1.1.3";
	private JPrograf jPrograf = null;

	/** Creates a new instance of WebRobotCustomizer */
	public WebRobotCustomizer() {
	}

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addICustomizerListener(final ICustomizerListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(ICustomizerListener.class, listener);
	}

	/**
	 * Removes ICustomizerListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeICustomizerListener(final ICustomizerListener listener) {
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */

	protected void fireICustomizerListenerCanceled() {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ICustomizerListener.class) {
				((ICustomizerListener) listeners[i + 1]).canceled();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */

	protected void fireICustomizerListenerDone() {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ICustomizerListener.class) {
				((ICustomizerListener) listeners[i + 1]).done();
			}
		}
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return jPrograf.getAcquisitionConfig();
	}

	@Override
	public void setHardwareAcquisitionConfig(final HardwareAcquisitionConfig acqConfig) {
		this.acqConfig = acqConfig;
	}

	@Override
	public void setHardwareInfo(final HardwareInfo hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	protected HardwareInfo getHardwareInfo() {
		return jPrograf.getHardwareInfo();
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return jPrograf.getMenuBar();
	}

	@Override
	public javax.swing.JComponent getCustomizerComponent() {
		jPrograf = new JPrograf(this);
		jPrograf.setHardwareAcquisitionConfig(acqConfig);
		jPrograf.setHardwareInfo(hardwareInfo);
		return jPrograf.getCustomizerComponent();
	}

	@Override
	public javax.swing.ImageIcon getCustomizerIcon() {
		return iconPrograf;
	}

	@Override
	public String getCustomizerTitle() {
		return WebRobotCustomizer.TITLE_VERSION;
	}
}