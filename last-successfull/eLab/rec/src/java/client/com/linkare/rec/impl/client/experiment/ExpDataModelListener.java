/*
 * ExpDataModelListener.java
 *
 * Created on 7 de Maio de 2003, 12:01
 */

package com.linkare.rec.impl.client.experiment;

import javax.swing.SwingUtilities;

import com.linkare.rec.acquisition.Hardware;

/**
 * Callback interface for each project's {@link ExpDataDisplay} to implement if
 * they want to be informed of state transition events in the remote experiments
 * execution.
 * 
 * These events are propagated in ORB threads, so if you are updating the Java
 * interface, please do it in the correct AWT Thread by using
 * {@link SwingUtilities#invokeLater(Runnable)} or
 * {@link SwingUtilities#invokeAndWait(Runnable)}
 * 
 * @see ExpDataModel
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ExpDataModelListener extends java.util.EventListener {

	/**
	 * The {@link ExpDataModel} instance will call this method everytime new
	 * samples are available.
	 * 
	 * @param evt An event carrying the first to last sample index range
	 *            currently available. From this point on, it is possible to
	 *            invoke the {@link ExpDataModel#getValueAt(int, int)} with the
	 *            sampleIndex ranging from the
	 *            {@link NewExpDataEvent#getSamplesStartIndex()} to
	 *            {@link NewExpDataEvent#getSamplesEndIndex()}
	 */
	public void newSamples(NewExpDataEvent evt);

	/**
	 * The {@link ExpDataModel} instance will call this method when it is
	 * running but there are still no samples available. The display components
	 * should somehow prepare for receiving data but display a message to the
	 * user that data is still not availabe at the remote endpoint. By this
	 * time, the {@link ExpDataModel#getAcquisitionConfig()} is still not
	 * available
	 */
	public void dataModelWaiting();

	/**
	 * The {@link ExpDataModel} instance will call this method when it detects
	 * that the remote {@link Hardware} is already acquiring data, but still no
	 * data is available. By this time, the
	 * {@link ExpDataModel#getAcquisitionConfig()} is already available
	 */
	public void dataModelStartedNoData();

	/**
	 * The {@link ExpDataModel} instance will call this method when the remote
	 * {@link Hardware} is already acquiring data and the first sample is
	 * available
	 */
	public void dataModelStarted();

	/**
	 * The {@link ExpDataModel} instance will call this method when the remote
	 * {@link Hardware} has already ended acquiring data
	 */
	public void dataModelEnded();

	/**
	 * The {@link ExpDataModel} instance will call this method when the remote
	 * {@link Hardware} has been stopped
	 */
	public void dataModelStoped();

	/**
	 * The {@link ExpDataModel} instance will call this method when the remote
	 * acquisition has transitioned to an error state. This could be a remote
	 * endpoint error or a communication problem.
	 */
	public void dataModelError();

}
