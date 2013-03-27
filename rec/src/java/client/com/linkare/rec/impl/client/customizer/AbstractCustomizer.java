package com.linkare.rec.impl.client.customizer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractCustomizer extends JPanel implements ICustomizer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2585288988776353777L;
	/** Utility field used by event firing mechanism. */
	private final javax.swing.event.EventListenerList listenerList = new EventListenerList();

	/**
	 * Registers ICustomizerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public final synchronized void addICustomizerListener(final ICustomizerListener listener) {
		listenerList.add(ICustomizerListener.class, listener);
	}

	/**
	 * Removes ICustomizerListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public final synchronized void removeICustomizerListener(final ICustomizerListener listener) {
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireICustomizerListenerCanceled() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ICustomizerListener.class) {

				((ICustomizerListener) listeners[i + 1]).done();
			}
		}
	}

	private HardwareInfo hardwareInfo = null;
	private HardwareAcquisitionConfig acqConfig = null;

	@Override
	public HardwareAcquisitionConfig getAcquisitionConfig() {
		return acqConfig;
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
		return hardwareInfo;
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public JComponent getCustomizerComponent() {
		return this;
	}
}
