/*
 * SimulationHardwareImpl.java
 *
 * Created on 24 de Junho de 2002, 19:52
 */

package com.linkare.rec.impl.driver;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.HardwareOperations;
import com.linkare.rec.acquisition.HardwarePOATie;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.events.HardwareStateChangeEvent;
import com.linkare.rec.impl.exceptions.IncorrectStateExceptionConstants;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.impl.utils.HardwareBinder;
import com.linkare.rec.impl.utils.HardwareInfoXMLReader;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.QueueLogger;
import com.linkare.rec.impl.wrappers.DataClientWrapper;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class BaseHardware implements HardwareOperations, BaseDataProducerListener, QueueLogger {

	private static final Logger LOGGER = Logger.getLogger(BaseHardware.class.getName());

	private static final boolean SHOW_GUI = Boolean.parseBoolean(Defaults.defaultIfEmpty(
			System.getProperty("rec.driver.show.gui"), "false"));

	private final HardwareBinder refBinder = new HardwareBinder();

	private IDriver driver = null;

	private HardwareInfo info = null;

	// private HardwareAcquisitionConfig config = null;

	private BaseDataProducer dataProducerInEffect = null;

	private final List<BaseDataProducer> oldDataProducers = new Vector<BaseDataProducer>();

	protected DataClientWrapper dataClient = null;

	private Hardware _this = null;

	private Hardware _this() {
		if (_this != null) {
			return _this;
		}

		try {
			final HardwarePOATie thisRemoteObject = (HardwarePOATie) ORBBean.getORBBean().registerAutoIdRootPOAServant(
					com.linkare.rec.acquisition.Hardware.class, this, null);

			_this = thisRemoteObject._this();

			LOGGER.info("Registering this hardware with name " + "com/linkare/rec/hardwares/"
					+ driver.getDriverUniqueID());

			refBinder.setHardware(_this);

			return _this;
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't register BaseHardware with ORB...", e);
			try {
				driver.shutdown();
			} catch (final Exception ignored) {
			}
			driver = null;
			System.exit(-1);
			return null;
		}

	}

	private class StateMachine implements IDriverStateListener {
		@Override
		public void driverInited() {
			startup();
		}

		@Override
		public void driverConfiguring() {
			setHardwareState(HardwareState.CONFIGURING);
		}

		@Override
		public void driverConfigured() {
			setHardwareState(HardwareState.CONFIGURED);
		}

		@Override
		public void driverStarting() {
			setHardwareState(HardwareState.STARTING);
		}

		@Override
		public void driverStarted() {
			setHardwareState(HardwareState.STARTED);
		}

		@Override
		public void driverStoping() {
			setHardwareState(HardwareState.STOPING);
		}

		@Override
		public void driverStoped() {
			setHardwareState(HardwareState.STOPED);
		}

		@Override
		public void driverReseting() {
			setHardwareState(HardwareState.RESETING);
		}

		@Override
		public void driverReseted() {
			setHardwareState(HardwareState.RESETED);
		}

		@Override
		public void driverShutdown() {
			shutdown();
		}
	}

	EventQueue eventQueue = null;

	public BaseHardware() {
		LOGGER.info("Instatiating the BaseHardware.");

		LOGGER.info("Creating EventQueue for data client dispatcher.");
		eventQueue = new EventQueue(new BaseHardwareDataClientDispatcher(), this.getClass().getSimpleName(), this);

		if (!GraphicsEnvironment.isHeadless() && BaseHardware.SHOW_GUI) {
			final JFrame frameForKill = new JFrame();
			final JButton btnExit = new JButton("End Driver!");
			btnExit.setBackground(Color.blue);
			btnExit.setForeground(Color.white);
			btnExit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					System.exit(-1);

				}
			});
			frameForKill.getContentPane().add(btnExit);
			frameForKill.setVisible(true);
			frameForKill.pack();
		}
	}

	/**
	 * Creates a new instance of SimulationHardwareImpl
	 * 
	 * @param driver
	 */
	public BaseHardware(final IDriver driver) {
		this();
		setDriver(driver);
	}

	/**
	 * Getter for property driver.
	 * 
	 * @return Value of property driver.
	 */
	public IDriver getDriver() {
		return driver;
	}

	StateMachine stateMachine = new StateMachine();

	/**
	 * Setter for property driver.
	 * 
	 * @param driver New value of property driver.
	 */
	public final void setDriver(final IDriver driver) {
		LOGGER.log(Level.FINE, "Setting the driver for the BaseHardware.");

		if (this.driver != null) {
			this.driver.removeIDriverStateListener(stateMachine);
		}
		this.driver = driver;
		if (driver == null) {
			shutdown();
		} else {
			try {
				driver.addIDriverStateListener(stateMachine);
			} catch (final java.util.TooManyListenersException e) {
				LOGGER.log(Level.WARNING, "Couldn't register IDriverStateListener with Driver...", e);
			}
			readInHardwareInfo(driver.getHardwareInfo());
			driver.init(getHardwareInfo());
		}

	}

	private void shutdown() {
		// do nothing for now... should unregister client, or somethin'
	}

	private void startup() {
		LOGGER.info("Driver started up... Register with ORB...");
		_this();
	}

	private HardwareState state = HardwareState.UNKNOWN;

	@Override
	public HardwareState getHardwareState() {
		synchronized (state) {
			return state;
		}
	}

	public void setHardwareState(final HardwareState state) {
		synchronized (state) {
			if (!this.state.equals(state)) {
				this.state = state;
				eventQueue.addEvent(new HardwareStateChangeEvent(state));
			}
		}
	}

	private void readInHardwareInfo(final Object o) {
		if (o == null) {
			return;
		}
		try {
			if (o instanceof HardwareInfo) {
				setHardwareInfo((HardwareInfo) o);
			} else if (o instanceof String) {
				setHardwareInfo(HardwareInfoXMLReader.readHardwareInfo((String) o));
			} else if (o instanceof URL) {
				setHardwareInfo(HardwareInfoXMLReader.readHardwareInfo((URL) o));
			} else if (o instanceof InputStream) {
				setHardwareInfo(HardwareInfoXMLReader.readHardwareInfo((InputStream) o));
			} else {
				throw new RuntimeException("Not possible to read HardwareInfo");
			}
		} catch (final Exception e) {
			LOGGER.log(Level.WARNING, "Couldn't read HardwareInfo from Driver", e);
		}
	}

	public void setHardwareInfo(final HardwareInfo info) {
		this.info = info;
	}

	@Override
	public HardwareInfo getHardwareInfo() {
		return info;
	}

	@Override
	public DataProducer getDataProducer() throws IncorrectStateException, NotAvailableException {
		if (dataProducerInEffect == null) {
			throw new com.linkare.rec.acquisition.NotAvailableException();
		}

		return dataProducerInEffect._this();

	}

	@Override
	public void registerDataClient(final DataClient data_client) {
		dataClient = new DataClientWrapper(data_client);
	}

	@Override
	public DataClient getDataClient() {
		return dataClient.getDelegate();
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config) throws IncorrectStateException,
			WrongConfigurationException {

		if (driver == null) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR, state,
					HardwareState.CONFIGURING);
		}

		if (!state.equals(HardwareState.CONFIGURED) && !state.equals(HardwareState.STOPED)
				&& !state.equals(HardwareState.RESETED)) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE, state,
					HardwareState.CONFIGURING);
		}

		try {
			driver.config(config, getHardwareInfo());
		} catch (final WrongConfigurationException e) {
			LOGGER.log(Level.WARNING, "Invalid configuration. Thowing the exception.", e);
			throw e;
		} catch (final Exception e) {
			LOGGER.log(Level.WARNING, "Error configuring the hardware.", e);
		}

	}

	@Override
	public DataProducer start(final DataReceiver receiver) throws IncorrectStateException {
		if (driver == null) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR, state,
					HardwareState.STARTING);
		}

		if (!state.equals(HardwareState.STOPED) && !state.equals(HardwareState.CONFIGURED)) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE, state,
					HardwareState.STARTING);
		}

		try {
			dataProducerInEffect = new BaseDataProducer(receiver);
			dataProducerInEffect.addBaseDataProducerListener(this);

			oldDataProducers.add(dataProducerInEffect);
			final DataProducer dataProducer = dataProducerInEffect._this();

			final IDataSource ds = driver.start(getHardwareInfo());
			dataProducerInEffect.setDataSource(ds);

			return dataProducer;
		} catch (final IncorrectStateException e) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					getHardwareState(), HardwareState.STARTING);
		}

	}

	@Override
	public DataProducer startOutput(final DataReceiver receiver, final DataProducer data_source)
			throws IncorrectStateException {
		if (driver == null) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR, state,
					HardwareState.STARTING);
		}

		if (!state.equals(HardwareState.STOPED) && !state.equals(HardwareState.CONFIGURED)) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE, state,
					HardwareState.STARTING);
		}

		try {
			dataProducerInEffect = new BaseDataProducer(receiver);
			oldDataProducers.add(dataProducerInEffect);
			dataProducerInEffect.addBaseDataProducerListener(this);
			final DataProducer dataProducer = dataProducerInEffect._this();

			final IDataSource ds = driver.startOutput(getHardwareInfo(), new DataProducer2IDataSourceAdapter(
					new DataProducerWrapper(data_source)));
			dataProducerInEffect.setDataSource(ds);

			return dataProducer;
		} catch (final IncorrectStateException e) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					getHardwareState(), HardwareState.STARTING);
		}
	}

	@Override
	public void stop() throws IncorrectStateException {
		if (driver == null) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR, state,
					HardwareState.STOPING);
		}

		if (!state.equals(HardwareState.STARTED)) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE, state,
					HardwareState.STOPING);
		}

		LOGGER.info("Reseting Driver...");
		try {
			if (dataProducerInEffect != null) {
				dataProducerInEffect.stopNow();

				dataProducerInEffect.dataSourceStateStoped();
			}

			driver.stop(getHardwareInfo());
		} catch (final IncorrectStateException e) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					getHardwareState(), HardwareState.STOPING);
		}
	}

	@Override
	public void reset() throws IncorrectStateException {
		if (driver == null) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR, state,
					HardwareState.RESETING);
		}

		if (!state.equals(HardwareState.STARTED) && !state.equals(HardwareState.CONFIGURED)
				&& !state.equals(HardwareState.UNKNOWN)) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE, state,
					HardwareState.RESETING);
		}

		LOGGER.info("Reseting Driver...");
		try {
			driver.reset(getHardwareInfo());
		} catch (final IncorrectStateException e) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					getHardwareState(), HardwareState.RESETING);
		}
	}

	@Override
	public void baseDataProducerIsEmpty(final BaseDataProducer producer) {
		if (producer != null) {
			oldDataProducers.remove(producer);
		}

		if (producer == dataProducerInEffect) {
			dataProducerInEffect = null;
		}
	}

	private class BaseHardwareDataClientDispatcher implements EventQueueDispatcher {
		@Override
		public void dispatchEvent(final Object o) {
			try {
				if (o instanceof HardwareStateChangeEvent) {
					if (dataClient != null) {
						LOGGER.log(Level.FINE,
								"Dispatching hardware state [" + ((HardwareStateChangeEvent) o).getNewState() + "]");
						dataClient.hardwareStateChange(((HardwareStateChangeEvent) o).getNewState());
					}
				}
			} catch (final Exception e) {
				LOGGER.log(Level.WARNING, "Error comunicating HardwareStateChange to dataClient", e);
			}
		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void log(final Level debugLevel, final String message) {
		LOGGER.log(debugLevel, message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logThrowable(final String message, final Throwable t) {
		LOGGER.log(Level.SEVERE, message, t);
	}

}
