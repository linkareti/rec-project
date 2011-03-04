/*
 * DataCollector.java
 *
 * Created on 12 de Janeiro de 2004, 18:28
 */

package com.linkare.rec.impl.utils;

import static java.util.concurrent.TimeUnit.SECONDS;

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
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class DataCollector extends Thread implements Serializable {

	private static final long serialVersionUID = -5606372174593582642L;

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
	private static final int DEFAULT_FREQUENCY_TIME = 100;
	private long frequency = DEFAULT_FREQUENCY_TIME;
	private long lastFetchPacketsTimestamp = System.currentTimeMillis();
	private long lastPacketFetchedTimestamp = System.currentTimeMillis();
	private transient DataCollectorFetchPacketCheck fetchPacketCheck = null;

	static {
		Logger l = LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DATA_RECEIVER_LOGGER));
		}
	}

	public DataCollector() {
		samplesPackets = new SamplesPacketMatrix();
		// releaseAcquisitionThread();
		setName(getName() + " - DataCollector");
	}

	public SamplesPacketSource getSamplesPacketSource() {
		return samplesPackets;
	}

	protected void setLargestPacketKnown(int largestPacketKnown) {
		synchronized (synchWaitFetchData) {
			log(Level.FINEST, "Setting DataCollector for the largest packet know = " + largestPacketKnown);
			if (largestPacketKnown > this.largestPacketKnown) {
				this.largestPacketKnown = largestPacketKnown;
				notified = true;
				synchWaitFetchData.notify();
			}
		}
	}

	protected int getLargestPacketKnown() {
		return largestPacketKnown;
	}

	// private void releaseAcquisitionThread() {
	// initAcquisitionThread();
	//
	// synchronized (synchWaitFetchData) {
	// synchWaitFetchData.notify();
	// }
	// }

	public void initAcquisitionThread() {
		if (!acquisitionThreadInited) {
			acquisitionThreadInited = true;
			start();
		}
	}

	public void addSamplesPackets(SamplesPacket[] samples_packet) {
		lastPacketFetchedTimestamp = System.currentTimeMillis();

		samplesPackets.addSamplesPackets(samples_packet);
		if (!startedSamples && samplesPackets.size() > 0) {
			startedSamples = true;
			if (DataCollectorState.DP_STARTED_NODATA.equals(dataCollectorState)) {
				setDataCollectorState(DataCollectorState.DP_STARTED);
			}
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

	private volatile boolean notified = false;

	abstract public void log(Level debugLevel, String message);

	abstract public void logThrowable(String message, Throwable t);

	abstract public void fireNewSamples();

	abstract public void fireStateChanged();

	abstract public DataProducerWrapper getRemoteDataProducer();

	public void run() {
		exit = false;
		pause = false;
		acquisitionThread = currentThread();

		log(Level.INFO, "DataCollector started. Remote data producer state = " + remoteDataProducerState);

		try {
			// Thread to check is the data collector is receiving samples
			lastPacketFetchedTimestamp = System.currentTimeMillis();
			fetchPacketCheck = new DataCollectorFetchPacketCheck();

			setDataCollectorState(DataCollectorState.DP_STARTED_NODATA);

			log(Level.FINEST, "DataCollector is going to fetch available packets");
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

				synchronized (synchWaitFetchData) {
					// se ainda nao obteve tudo e entretanto nao chegou
					// notificacao de nova amostra fica 'a espera
					if (samplesPackets.size() < totalSamples && !exit
							&& samplesPackets.size() - 1 == largestPacketKnown) {
						synchWaitFetchData.wait(calcWaitTime());
					}
				}

				if (!exit) {
					// se nao sai' do wait por ter sido notificado vai
					// actualizar o numero do ultimo pacote
					if (!notified) {
						updateLargestPacketKnown();
					}
					fetchPackets();
				}
			}

			finishDataCollectorRun();

			log(Level.INFO, "DataCollector ended. Remote data producer state = " + remoteDataProducerState);

		} catch (Exception e) {
			logThrowable("Unexpected exception while running DataCollector!", e);
			return;
		}

	}

	/**
	 * Cleanup before the data collector thread terminates
	 */
	protected void finishDataCollectorRun() {
		log(Level.FINEST, "DataCollector is going to finish the run");

		fetchAvailablePackets();
		setDataCollectorState(new DataCollectorState(remoteDataProducerState));

		acquisitionThread = null;

		exit = true;
		// pause = true;

		fetchPacketCheck.shutdown();
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
			log(Level.FINE, "DataCollector is going to fetch packets from " + lastPacket + " to " + largestPacketKnown);
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
		updateLargestPacketKnown();
		fetchPackets();
		if (largestPacketKnown != samplesPackets.size() - 1)
			if (!isConnected())
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
	}

	private void updateLargestPacketKnown() {
		try {
			synchronized (synchWaitFetchData) {
				largestPacketKnown = Math.max(getRemoteDataProducer().getMaxPacketNum(), largestPacketKnown);
				log(Level.FINEST, "Updated DataCollector for the largest packet know = " + largestPacketKnown);
			}
		} catch (Exception e) {
			if (!isConnected())
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
		}
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
			notified = true;
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

	/**
	 * This method should be used as a way to change the behaviour of the data
	 * collector on the GUI and on the multicast. This way, we are allowed to
	 * make both collectors behave differently, as needed.
	 * 
	 * @return the waiting time to check for new packets
	 */
	protected long calcWaitTime() {
		long time = frequency - (System.currentTimeMillis() - lastFetchPacketsTimestamp);
		return time > 0 ? time : DEFAULT_FREQUENCY_TIME;
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

	private class DataCollectorFetchPacketCheck extends ScheduledWorkUnit {

		private int periodChecker = 60; // seconds // TODO must be a system
										// property
		private int timeSpend = 5 * 60 * 1000; // milliseconds // TODO must be a
												// system property

		DataCollectorFetchPacketCheck() {
			ExecutorScheduler.scheduleAtFixedRate(this, 1, periodChecker, SECONDS);
		}

		public void run() {
			if (System.currentTimeMillis() - lastPacketFetchedTimestamp >= timeSpend) {
				log(Level.INFO, "Going to exit because it has passed more than " + ((int) (timeSpend / 1000))
						+ " seconds since it was received a sample.");
				exit = true;
				shutdown();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void logThrowable(String message, Throwable throwable) {
			logThrowable(message, throwable);
		}
	}

}
