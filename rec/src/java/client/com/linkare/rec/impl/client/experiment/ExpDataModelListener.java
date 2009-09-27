/*
 * ExpDataModelListener.java
 *
 * Created on 7 de Maio de 2003, 12:01
 */

package com.linkare.rec.impl.client.experiment;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ExpDataModelListener extends java.util.EventListener {
	public void newSamples(NewExpDataEvent evt);

	public void dataModelWaiting();

	public void dataModelStartedNoData();

	public void dataModelStarted();

	public void dataModelEnded();

	public void dataModelStoped();

	public void dataModelError();
	// public void headerAvailable(HardwareAcquisitionConfig header);
	// headerAvailable is no longer used. Now get the header at startedNoData
	// from the ExpDataModel class
}
