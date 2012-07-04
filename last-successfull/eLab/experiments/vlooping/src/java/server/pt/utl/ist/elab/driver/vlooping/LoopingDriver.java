/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vlooping;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author Emanuel Antunes
 */

public class LoopingDriver extends VirtualBaseDriver {
	// O código desta classe é sempre igual!!! Alterar só os nomes para o vosso
	// caso!
	private static String LOOPING_DRIVER_LOGGER = "LOOPING.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(LoopingDriver.LOOPING_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(LoopingDriver.LOOPING_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Looping Driver)";
	private static final String DRIVER_UNIQUE_ID = "LOOPING_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of RobotDriver */
	public LoopingDriver() {
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(LoopingDriver.LOOPING_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// Recebemos ordem para configurar, no HardwareAcquisitionConfig estao
		// todas as informacoes escolhidas pelo cliente...agora e' so' pedir
		this.config = config;
		this.info = info;

		final float xini = Float.parseFloat(config.getSelectedHardwareParameterValue("xini"));
		final float vini = Float.parseFloat(config.getSelectedHardwareParameterValue("vini"));
		final float h1 = Float.parseFloat(config.getSelectedHardwareParameterValue("h1"));
		final float h2 = Float.parseFloat(config.getSelectedHardwareParameterValue("h2"));
		final float r = Float.parseFloat(config.getSelectedHardwareParameterValue("r"));
		final float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		// Vamos criar o nosso produtor de dados!
		dataSource = new LoopingDataProducer(this, xini, h1, h2, r, vini, g, tbs, nSamples);

		// NECESSARIO!! Colocar o numero de amostra para todos os canais!
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		// NAO ESQUECER ESTA LINHA!
		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return LoopingDriver.DRIVER_UNIQUE_ID;
	}

	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.stopNow();
		}
		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopNow();
		fireIDriverStateListenerDriverStoped();
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
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger("LOOPING"));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger("LOOPING"));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
