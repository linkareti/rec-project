/*
 * MeteoDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.telescopio;

import java.util.logging.Logger;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author Andr�
 */

public class TelescopioDriver extends BaseDriver {
	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab Telescopio Driver";
	private static final String DRIVER_UNIQUE_ID = "ELAB_TELESCOPIO_V01";
	private static final String HW_VERSION = "0.1";

	protected TelescopioDataProducer dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of RobotDriver */
	public TelescopioDriver() {
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		try {
			configure(config, info);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new WrongConfigurationException("Erro no config...", 20);
		}
	}

	private int expo = 0;
	private String command = "";

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		if (dataSource != null) {
			dataSource = null;
		}

		dataSource = new TelescopioDataProducer(this);

		this.config = config;
		this.info = info;

		expo = Integer.parseInt(config.getSelectedHardwareParameter("TempoExp").getParameterValue().trim());

		command = config.getSelectedHardwareParameter("Comando").getParameterValue().trim();

		final int nSamples = 1;

		config.setTotalSamples(nSamples);

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(nSamples);
		}
		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		/** not going to use */
	}

	@Override
	public String getDriverUniqueID() {
		return TelescopioDriver.DRIVER_UNIQUE_ID;
	}

	@Override
	public void init(final HardwareInfo info) {
		/*
		 * SQLConnector sqlc = new SQLConnector(SQLConnector.MYSQL); conn =
		 * sqlc.connect("192.168.0.111/meteo", "root", "");
		 */
		fireIDriverStateListenerDriverInited();
	}

	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
		/** Reset in not supported in webrobot */
	}

	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.shutdown();
		}
		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		if (dataSource != null) {
			fireIDriverStateListenerDriverStarting();
			dataSource.start(command, "" + expo);
			fireIDriverStateListenerDriverStarted();
			return dataSource;
		} else {
			return null;
		}
	}

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		/** Don't know what is startOutput... */
		return null;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {

	}

	@Override
	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();

		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = new java.net.URL(prop);
			fireIDriverStateListenerDriverReseted();
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger("Telescopio"));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
				fireIDriverStateListenerDriverReseted();
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger("Telescopio"));
			}
		}

		System.out.println("Loading url = " + url);

		return url;
	}

	protected void stop() {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopProduction();
		fireIDriverStateListenerDriverStoped();

	}

}
