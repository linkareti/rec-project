/*
 * DataCollector.java
 *
 * Created on 12 de Janeiro de 2004, 18:28
 */

package com.linkare.rec.impl.utils;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.MaxPacketNumUnknown;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
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

	private static final Logger LOGGER = Logger.getLogger(DataCollector.class.getName());

	private transient Thread acquisitionThread = null;
	private final Object synchWaitFetchData = new boolean[0];
	private SamplesPacketMatrix samplesPackets = null;
	private int largestPacketKnown = MaxPacketNumUnknown.value;
	private DataProducerState remoteDataProducerState = DataProducerState.DP_WAITING;
	private DataCollectorState dataCollectorState = DataCollectorState.DP_WAITING;
	private boolean startedSamples = false;
	private int totalSamples;
	private static final int DEFAULT_FREQUENCY_TIME = 100;
	private long frequency = DataCollector.DEFAULT_FREQUENCY_TIME;
	private long lastFetchPacketsTimestamp = System.currentTimeMillis();
	private long lastPacketFetchedTimestamp = System.currentTimeMillis();
	private transient DataCollectorFetchPacketCheck fetchPacketCheck = null;

	protected transient DataProducerWrapper remoteDataProducer;

	protected HardwareAcquisitionConfig cachedAcqHeader;

	public DataCollector() {
		samplesPackets = new SamplesPacketMatrix();
		// releaseAcquisitionThread();
		setName(getName() + " - DataCollector");
	}

	protected DataCollector(final DataCollectorState collectorState, final SamplesPacketMatrix samplesPacketMAtrix) {
		this.samplesPackets = samplesPacketMAtrix;
		this.dataCollectorState = collectorState;
		if (samplesPacketMAtrix != null && samplesPacketMAtrix.size() > 0) {
			this.largestPacketKnown = samplesPacketMAtrix.size();
		}
	}

	public SamplesPacketSource getSamplesPacketSource() {
		return samplesPackets;
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() throws NotAvailableException {
		if (cachedAcqHeader == null && remoteDataProducer != null) {
			try {
				cachedAcqHeader = remoteDataProducer.getAcquisitionHeader();
			} catch (final NotAvailableException e) {
				LOGGER.log(Level.SEVERE, "Couldn't get Acquisition Header! - Rethrowing exception...", e);
				throw e;
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE,
						"Other reason - Couldn't get Acquisition Header! Throwing not available exception...", e);
				throw new NotAvailableException();
			}
		}

		return cachedAcqHeader;
	}

	public void setRemoteDataProducer(final DataProducer remoteDataProducer) {
		this.remoteDataProducer = new DataProducerWrapper(remoteDataProducer);
		setPriority(Thread.MAX_PRIORITY - 1);
		try {
			setTotalSamples(getAcquisitionHeader().getTotalSamples());
			setFrequency((long) getAcquisitionHeader().getSelectedFrequency().getFrequency());
		} catch (final NotAvailableException e) {
			LOGGER.log(Level.SEVERE, "Error getting AcquisitionHeader info", e);
		}
		try {
			setLargestPacketKnown(remoteDataProducer.getMaxPacketNum());
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting Remote Data Producer Max Packet Num", e);
		}
		try {
			setRemoteDataProducerState(remoteDataProducer.getDataProducerState());
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting Remote Data Producer Max Packet Num", e);
		}
	}

	protected void setLargestPacketKnown(final int largestPacketKnown) {
		synchronized (synchWaitFetchData) {
			LOGGER.log(Level.FINEST, "Setting DataCollector for the largest packet know = " + largestPacketKnown);
			if (largestPacketKnown > this.largestPacketKnown) {
				this.largestPacketKnown = largestPacketKnown;
				notified = true;
				synchWaitFetchData.notify();
			}
			if ((this.getDataCollectorState().equals(DataCollectorState.DP_WAITING) || this.getDataCollectorState()
					.equals(DataCollectorState.DP_STARTED_NODATA)) && largestPacketKnown > 0) {
				if (isStateNew()) {
					initAcquisitionThread();
				}
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

	private void initAcquisitionThread() {
		if (isStateNew()) {
			start();
		}
	}

	public void addSamplesPackets(final SamplesPacket[] samples_packet) {
		lastPacketFetchedTimestamp = System.currentTimeMillis();

		samplesPackets.addSamplesPackets(samples_packet);
		if (!startedSamples && samplesPackets.size() > 0) {
			startedSamples = true;
			if (DataCollectorState.DP_STARTED_NODATA.equals(dataCollectorState)
					|| DataCollectorState.DP_WAITING.equals(dataCollectorState)) {
				setDataCollectorState(DataCollectorState.DP_STARTED);
			}
		}
		fireNewSamples();
	}

	private void setDataCollectorState(final DataCollectorState newState) {
		if (newState.equals(dataCollectorState)) {
			return;
		}

		if (newState.equals(DataCollectorState.DP_WAITING)) {
			LOGGER.log(Level.INFO, "Trying to set Data Collector State to " + newState
					+ " while current DataProducer State is " + dataCollectorState
					+ " - this transition is not possible!");
		} else if (newState.equals(DataCollectorState.DP_STARTED_NODATA)) {
			if (!dataCollectorState.equals(DataCollectorState.DP_WAITING)) {
				LOGGER.log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			} else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_STARTED)) {
			if (!(dataCollectorState.equals(DataCollectorState.DP_WAITING) || dataCollectorState
					.equals(DataCollectorState.DP_STARTED_NODATA))) {
				LOGGER.log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			} else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_ENDED)) {
			if (!(dataCollectorState.equals(DataCollectorState.DP_WAITING)
					|| dataCollectorState.equals(DataCollectorState.DP_STARTED_NODATA) || dataCollectorState
						.equals(DataCollectorState.DP_STARTED))) {
				LOGGER.log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			} else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_STOPED)) {
			if (!(dataCollectorState.equals(DataCollectorState.DP_WAITING)
					|| dataCollectorState.equals(DataCollectorState.DP_STARTED_NODATA) || dataCollectorState
						.equals(DataCollectorState.DP_STARTED))) {
				LOGGER.log(Level.INFO, "Trying to set Data Collector State to " + newState
						+ " while current DataProducer State is " + dataCollectorState
						+ " - this transition is not possible!");
			} else {
				dataCollectorState = newState;
				fireStateChanged();
			}
		} else if (newState.equals(DataCollectorState.DP_ERROR)) {
			dataCollectorState = newState;
			fireStateChanged();

		} else {
			LOGGER.log(Level.INFO, "Unknown Data Collector State " + newState);
		}
	}

	public DataCollectorState getDataCollectorState() {
		return dataCollectorState;
	}

	public void setRemoteDataProducerState(final DataProducerState newState) {
		remoteDataProducerState = newState;
		if (newState.equals(DataProducerState.DP_WAITING)) {
			remoteDataProducerWaiting();
		} else if (newState.equals(DataProducerState.DP_STARTED_NODATA)) {
			remoteDataProducerStartedNoData();
		} else if (newState.equals(DataProducerState.DP_STARTED)) {
			remoteDataProducerStarted();
		} else if (newState.equals(DataProducerState.DP_ENDED)) {
			remoteDataProducerEnded();
		} else if (newState.equals(DataProducerState.DP_STOPED)) {
			remoteDataProducerStoped();
		} else if (newState.equals(DataProducerState.DP_ERROR)) {
			remoteDataProducerError();
		} else {
			LOGGER.log(Level.INFO, "Unknown Remote Data Collector State " + newState);
		}
	}

	private void remoteDataProducerWaiting() {
		// initAcquisitionThread();
	}

	private void remoteDataProducerStartedNoData() {
		// initAcquisitionThread();
	}

	private void remoteDataProducerStarted() {
		initAcquisitionThread();
	}

	private void remoteDataProducerEnded() {
		if (isStateNew()) {
			setDataCollectorState(DataCollectorState.DP_STARTED);
			finishDataCollectorRun();
		}
		
		setDataCollectorState(DataCollectorState.DP_ENDED);
		setExit(true);
	}

	private transient ReentrantReadWriteLock threadStateChangeRWLock = new ReentrantReadWriteLock();

	public void start() {
		try {
			threadStateChangeRWLock.writeLock().lock();
			super.start();
		} finally {
			threadStateChangeRWLock.writeLock().unlock();
		}
	}

	private boolean isStateNew() {
		try {
			threadStateChangeRWLock.readLock().lock();
			return Thread.State.NEW == this.getState();
		} finally {
			threadStateChangeRWLock.readLock().unlock();
		}
	}

	private void remoteDataProducerStoped() {
		if (isStateNew()) {
			setDataCollectorState(DataCollectorState.DP_STARTED);
			finishDataCollectorRun();
		}
		setExit(true);
	}

	private void remoteDataProducerError() {
		if (isStateNew()) {
			setDataCollectorState(DataCollectorState.DP_STARTED);
			finishDataCollectorRun();
		}
		setExit(true);
	}

	private volatile boolean exit = true;

	private volatile boolean pause = true;

	private volatile boolean notified = false;

	abstract public void fireNewSamples();

	abstract public void fireStateChanged();

	@Override
	public void run() {
		exit = false;
		pause = false;
		acquisitionThread = Thread.currentThread();

		LOGGER.log(Level.INFO, "DataCollector started. Remote data producer state = " + remoteDataProducerState);

		try {
			// Thread to check is the data collector is receiving samples
			lastPacketFetchedTimestamp = System.currentTimeMillis();
			fetchPacketCheck = new DataCollectorFetchPacketCheck();

			setDataCollectorState(DataCollectorState.DP_STARTED_NODATA);

			LOGGER.log(Level.FINEST, "DataCollector is going to fetch available packets");
			fetchAvailablePackets();

			while (samplesPackets.size() < totalSamples && !exit) {
				while (pause && !exit) {
					synchronized (synchWaitFetchData) {
						final DataCollectorState stateBeforePausing = getDataCollectorState();
						setDataCollectorState(new DataCollectorState(DataCollectorState.DP_WAITING));
						try {
							synchWaitFetchData.wait(calcWaitTime());
						} catch (final Exception e) {
							LOGGER.log(Level.SEVERE, "Exception while pausing", e);
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

			LOGGER.log(Level.INFO, "DataCollector ended. Remote data producer state = " + remoteDataProducerState);

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Unexpected exception while running DataCollector!", e);
			return;
		}

	}

	/**
	 * Cleanup before the data collector thread terminates
	 */
	protected void finishDataCollectorRun() {
		LOGGER.log(Level.FINEST, "DataCollector is going to finish the run");

		fetchAvailablePackets();
		setDataCollectorState(new DataCollectorState(remoteDataProducerState));

		acquisitionThread = null;

		exit = true;
		// pause = true;

		fetchPacketCheck.shutdown();
	}

	protected void shutdownThread() {
		if (acquisitionThread != null) {
			try {
				acquisitionThread.join(1000);
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Error waiting for acquisitionThread shutdown...", e);
			}
		}
	}

	private void fetchPackets() {
		lastFetchPacketsTimestamp = System.currentTimeMillis();
		int lastPacket = samplesPackets.size();
		if (lastPacket > largestPacketKnown || largestPacketKnown == MaxPacketNumUnknown.value) {
			return;
		}
		try {
			LOGGER.log(Level.FINE, "DataCollector is going to fetch packets from " + lastPacket + " to "
					+ largestPacketKnown);
			addSamplesPackets(remoteDataProducer.getSamples(lastPacket, largestPacketKnown));
		} catch (final NotAnAvailableSamplesPacketException e) {
			LOGGER.log(Level.SEVERE, "Error fetching samples Packet " + e.firstPacketNotFound, e);
			if (!isConnected()) {
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
			}
			lastPacket = e.firstPacketNotFound - 1;
			if (lastPacket > largestPacketKnown) {
				return;
			}
			try {
				addSamplesPackets(remoteDataProducer.getSamples(lastPacket, largestPacketKnown));
			} catch (final NotAnAvailableSamplesPacketException e2) {
				LOGGER.log(Level.SEVERE, "Error fetching samples Packet " + e2.firstPacketNotFound, e2);
				if (!isConnected()) {
					setRemoteDataProducerState(DataProducerState.DP_ERROR);
				}
			}
		}
	}

	public void fetchAvailablePackets() {
		updateLargestPacketKnown();
		fetchPackets();
		if (largestPacketKnown != samplesPackets.size() - 1) {
			if (!isConnected()) {
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
			}
		}
	}

	private void updateLargestPacketKnown() {
		try {
			synchronized (synchWaitFetchData) {
				largestPacketKnown = Math.max(remoteDataProducer.getMaxPacketNum(), largestPacketKnown);
				LOGGER.log(Level.FINEST, "Updated DataCollector for the largest packet known = " + largestPacketKnown);
			}
		} catch (final Exception e) {
			if (!isConnected()) {
				setRemoteDataProducerState(DataProducerState.DP_ERROR);
			}
		}
	}

	private boolean isConnected() {
		return remoteDataProducer.isConnected();
	}

	/**
	 * Getter for property exit.
	 * 
	 * @return Value of property exit.
	 */
	public boolean isExit() {
		return exit;
	}

	/**
	 * Setter for property exit.
	 * 
	 * @param exit New value of property exit.
	 */
	public void setExit(final boolean exit) {
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
		return pause;
	}

	/**
	 * Setter for property pause.
	 * 
	 * @param pause New value of property pause.
	 */
	public void setPause(final boolean pause) {
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
		final long time = frequency - (System.currentTimeMillis() - lastFetchPacketsTimestamp);
		return time > 0 ? time : DataCollector.DEFAULT_FREQUENCY_TIME;
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
	protected void setTotalSamples(final int totalSamples) {
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
	protected void setFrequency(final long frequency) {
		this.frequency = frequency;
	}

	private class DataCollectorFetchPacketCheck extends ScheduledWorkUnit {

		private final int periodChecker = 60; // seconds
		// property
		private final int timeSpend = 5 * 60 * 1000; // milliseconds

		DataCollectorFetchPacketCheck() {
			ExecutorScheduler.scheduleAtFixedRate(this, 1, periodChecker, SECONDS);
		}

		@Override
		public void run() {
			if (System.currentTimeMillis() - lastPacketFetchedTimestamp >= timeSpend) {
				LOGGER.log(Level.INFO, "Going to exit because it has passed more than " + ((timeSpend / 1000))
						+ " seconds since it was received a sample.");
				exit = true;
				shutdown();
			}
			if (!DataCollector.this.remoteDataProducer.isConnected()) {
				LOGGER.log(Level.INFO, "Going to exit Remote Data Producer is not connected anymore... Ooops!!!");
				exit = true;
				shutdown();
			}
		}

	}

}
