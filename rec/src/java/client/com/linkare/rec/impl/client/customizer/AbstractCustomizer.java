/*
 * BaseCustomizer.java
 *
 * Created on 8 de Maio de 2003, 14:58
 */

package com.linkare.rec.impl.client.customizer;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;


/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer
{

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList =  null;


	/** Registers ICustomizerListener to receive events.
	 * @param listener The listener to register.
	 */
	public synchronized void addICustomizerListener(ICustomizerListener listener)
	{
		if (listenerList == null )
		{
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(ICustomizerListener.class, listener);
	}

	/** Removes ICustomizerListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public synchronized void removeICustomizerListener(ICustomizerListener listener)
	{
		listenerList.remove(ICustomizerListener.class, listener);
	}

	/** Notifies all registered listeners about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireICustomizerListenerCanceled()
	{
		if (listenerList == null) return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==ICustomizerListener.class)
			{
				((ICustomizerListener)listeners[i+1]).canceled();
			}
		}
	}

	/** Notifies all registered listeners about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireICustomizerListenerDone()
	{
		if (listenerList == null) return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==ICustomizerListener.class)
			{

				((ICustomizerListener)listeners[i+1]).done();
			}
		}
	}


	private HardwareInfo hardwareInfo=null;
	private HardwareAcquisitionConfig acqConfig=null;

	public HardwareAcquisitionConfig getAcquisitionConfig()
	{
		return acqConfig;
	}

	public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig)
	{
		this.acqConfig=acqConfig;
	}

	public void setHardwareInfo(HardwareInfo hardwareInfo)
	{
		this.hardwareInfo=hardwareInfo;
	}

	protected HardwareInfo getHardwareInfo()
	{
		return this.hardwareInfo;
	}
	
	public javax.swing.JMenuBar getMenuBar()
	{
	    return null;
	}
}
