/*
 * DataCollector.java
 *
 * Created on 12 de Janeiro de 2004, 18:28
 */

package com.linkare.rec.impl.utils;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.MaxPacketNumUnknown;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.impl.data.SamplesPacketMatrix;
import com.linkare.rec.impl.data.SamplesPacketSource;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class DataCollector extends Thread implements Serializable {
	private static String DATA_RECEIVER_LOGGER = "DataReceiver.Logger";
	private transient Thread acquisitionThread = null;
	private boolean acquisitionThreadInited = false;
	private Object synchWaitFetchData = new boolean[0];
	private SamplesPacketMatrix samplesPackets = null;
	private int largestPacketKnown = MaxPacketNumUnknown.value;
	private DataProducerState remoteDataProducerState = DataProducerState.DP_WAITING;
	private DataCollectorState dataCollectorState = DataCollectorState.DP_WAITING;
	private boolean startedSamples = false;
	private int totalSamples;
	private long frequency = 100; // default
	long lastFetchPacketsTimestamp = System.currentTimeMillis();

	static {
		Logger l = LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DATA_RECEIVER_LOGGER));
		}
	}

	public DataCollector() {
		samplesPackets = new SamplesPacketMatrix();
		releaseAcquisitionThread();
		setName(getName() + " - DataCollector");
	}

	public SamplesPacketSource getSamplesPacketSource() {
		return samplesPackets;
	}

	protected void setLargestPacketKnown(int largestPacketKnown) {
		this.largestPacketKnown = largestPacketKnown;
		
		log(Level.FINEST, "Setting DataCollector for the largest packet know = " + largestPacketKnown);

		synchronized (synchWaitFetchData) {
			synchWaitFetchData.notify();
		}
	}
	
	protected int getLargestPacketKnown() {
		return largestPacketKnown;
	}

	private void releaseAcquisitionThread() {
		initAcquisitionThread();

		synchronized (synchWaitFetchData) {
			synchWaitFetchData.notify();
		}
	}

	private void initAcquisitionThread() {
		if (!acquisitionThreadInited) {
			acquisitionThreadInited = true;
			start();
		}
	}

	public void addSamplesPackets(SamplesPacket[] samples_packet) {
		samplesPackets.addSamplesPackets(samples_packet);
		if (!startedSamples && samplesPackets.size() > 0) {
			startedSamples  = true;
			setDataCollectorState(DataCollectorState.DP_STARTED);
		}
		fireNewSamples();
	}

	private void setDataCollectorState(DataCollectorState newState) {
		if (newState.equals(dataCollectorState))
			return;

		if (newState.equals(DataCollectorState.DP_WAITING)) {
			log(Level.INFO, "Trying to set Data Collector State to " + newState
					+ " while current DataProducer State is " + dataCollectorState
					+ " - this transition is not possible!");
		} else if (newState.equals(DataCollectorState.DP_STARTED_NODATA)) {
			if (!dataCollectorState.equals(DataCollectorState.DP_WAITING))
				log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_STARTED)) {
			if (!(dataCollectorState.equals(DataCollectorState.DP_WAITING) || dataCollectorState
					.equals(DataCollectorState.DP_STARTED_NODATA)))
				log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_ENDED)) {
			if (!(dataCollectorState.equals(DataCollectorState.DP_WAITING)
					|| dataCollectorState.equals(DataCollectorState.DP_STARTED_NODATA) || dataCollectorState
					.equals(DataCollectorState.DP_STARTED)))
				log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_STOPED)) {
			if (!(dataCollectorState.equals(DataCollectorState.DP_WAITING)
					|| dataCollectorState.equals(DataCollectorState.DP_STARTED_NODATA) || dataCollectorState
					.equals(DataCollectorState.DP_STARTED)))
				log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_ERROR)) {
			dataCollectorState = newState;
			fireStateChanged();

		} else
			log(Level.INFO, "Unknown Data Collector State " + newState);
	}

	public DataCollectorState getDataCollectorState() {
		return dataCollectorState;
	}

	public void setRemoteDataProducerState(DataProducerState newState) {
		remoteDataProducerState = newState;
		if (newState.equals(DataProducerState.DP_WAITING))
			remoteDataProducerWaiting();
		else if (newState.equals(DataProducerState.DP_STARTED_NODATA))
			remoteDataProducerStartedNoData();
		else if (newState.equals(DataProducerState.DP_STARTED))
			remoteDataProducerStarted();
		else if (newState.equals(DataProducerState.DP_ENDED))
			remoteDataProducerEnded();
		else if (newState.equals(DataProducerState.DP_STOPED))
			remoteDataProducerStoped();
		else if (newState.equals(DataProducerState.DP_ERROR))
			remoteDataProducerError();
		else
			log(Level.INFO, "Unknown Remote Data Collector State " + newState);
	}

	private void remoteDataProducerWaiting() {
		initAcquisitionThread();
	}

	private void remoteDataProducerStartedNoData() {
		initAcquisitionThread();
	}

	private void remoteDataProducerStarted() {
		initAcquisitionThread();
	}

	private void remoteDataProducerEnded() {
		setExit(true);
	}

	private void remoteDataProducerStoped() {
		setExit(true);
	}

	private void remoteDataProducerError() {
		setExit(true);
	}

	private volatile boolean exit = true;

	private volatile boolean pause = true;

	abstract public void log(Level debugLevel, String message);

	abstract public void logThrowable(String message, Throwable t);

	abstract public void fireNewSamples();

	abstract public void fireStateChanged();

	abstract public DataProducerWrapper getRemoteDataProducer();

	public void run() {
		exit = false;
		pause = false;
		acquisitionThread = currentThread();
		
		try {
			setDataCollectorState(DataCollectorState.DP_WAITING);
	
			while (remoteDataProducerState.equals(DataProducerState.DP_WAITING) && !pause && !exit) {
				try {
					synchronized (synchWaitFetchData) {
						synchWaitFetchData.wait(calcWaitTime());
					}
				} catch (Exception e) {
					logThrowable("Exception while waiting for data available on Thread", e);
					return;
				}
			}
	
			setDataCollectorState(DataCollectorState.DP_STARTED_NODATA);
	
			fetchAvailablePackets();
	
			while (samplesPackets.size() < totalSamples && !exit) {
				while (pause && !exit) {
					synchronized (synchWaitFetchData) {
						DataCollectorState stateBeforePausing = getDataCollectorState();
						setDataCollectorState(new DataCollectorState(DataCollectorState.DP_WAITING));
						try {
							synchWaitFetchData.wait(calcWaitTime());
						} catch (Exception e) {
							logThrowable("Exception while pausing", e);
							return;
						}
						setDataCollectorState(new DataCollectorState(stateBeforePausing));
					}
				}
	
				fetchPackets();
				
				synchronized (synchWaitFetchData) {
					// se ainda nao obteve tudo e entretanto nao chegou notificacao de nova amostra fica 'a espera
					if (samplesPackets.size() < totalSamples && !exit
							&& samplesPackets.size() - 1 == largestPacketKnown) {
						synchWaitFetchData.wait(calcWaitTime());
					}
				}
			}
			
			finishDataCollectorRun();
		
		} catch (Exception e) {
			logThrowable("Unexpected exception while running DataCollector!", e);
			return;
		}

	}
	
	/**
	 * Cleanup before the data collector thread terminates
	 */
	protected void finishDataCollectorRun() {
		fetchAvailablePackets();
		
		setDataCollectorState(new DataCollectorState(remoteDataProducerState));

		acquisitionThread = null;

		exit = true;
		// pause = true;
	}
	
	protected void shutdownThread() {
		if (acquisitionThread != null)
			try {
				acquisitionThread.join(1000);
			} catch (Exception e) {
				logThrowable("Error waiting for acquisitionThread shutdown...", e);
			}
	}

	private void fetchPackets() {
		lastFetchPacketsTimestamp = System.currentTimeMillis();
		int lastPacket = samplesPackets.size();
		if (lastPacket > largestPacketKnown || largestPacketKnown == MaxPacketNumUnknown.value)
			return;
		try {
			addSamplesPackets(getRemoteDataProducer().getSamples(lastPacket, largestPacketKnown));
		} catch (NotAnAvailableSamplesPacketException e) {
			logThrowable("Error fetching samples Packet " + e.firstPacketNotFound, e);
			if (!isConnected())
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
			lastPacket = e.firstPacketNotFound - 1;
			if (lastPacket > largestPacketKnown)
				return;
			try {
				addSamplesPackets(getRemoteDataProducer().getSamples(lastPacket, largestPacketKnown));
			} catch (NotAnAvailableSamplesPacketException e2) {
				logThrowable("Error fetching samples Packet " + e2.firstPacketNotFound, e2);
				if (!isConnected())
					setRemoteDataProducerState(DataProducerState.DP_ERROR);
			}
		}
	}

	public void fetchAvailablePackets() {
		try {
			largestPacketKnown = Math.max(getRemoteDataProducer().getMaxPacketNum(), largestPacketKnown);
		} catch (Exception e) {
			if (!isConnected())
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
		}
		
		fetchPackets();
		if (largestPacketKnown != samplesPackets.size() - 1)
			if (!isConnected())
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
	}

	private boolean isConnected() {
		return getRemoteDataProducer().isConnected();
	}

	/**
	 * Getter for property exit.
	 * 
	 * @return Value of property exit.
	 */
	public boolean isExit() {
		return this.exit;
	}

	/**
	 * Setter for property exit.
	 * 
	 * @param exit New value of property exit.
	 */
	public void setExit(boolean exit) {
		this.exit = exit;
		synchronized (synchWaitFetchData) {
			synchWaitFetchData.notifyAll();
		}
	}

	/**
	 * Getter for property pause.
	 * 
	 * @return Value of property pause.
	 */
	public boolean isPause() {
		return this.pause;
	}

	/**
	 * Setter for property pause.
	 * 
	 * @param pause New value of property pause.
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
		synchronized (synchWaitFetchData) {
			synchWaitFetchData.notifyAll();
		}
	}
	
	private long calcWaitTime() {
		return frequency - (System.currentTimeMillis() - lastFetchPacketsTimestamp);
	}

	/**
	 * @return the totalSamples
	 */
	protected int getTotalSamples() {
		return totalSamples;
	}

	/**
	 * @param totalSamples the totalSamples to set
	 */
	protected void setTotalSamples(int totalSamples) {
		this.totalSamples = totalSamples;
	}

	/**
	 * @return the frequency
	 */
	protected long getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	protected void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	
}
