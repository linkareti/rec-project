/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.virtual.driver.poisson;

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
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author André
 */

public class PoissonDriver extends VirtualBaseDriver {
	private static String POISSON_DRIVER_LOGGER = "Poisson.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(POISSON_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(POISSON_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Poisson)";
	private static final String DRIVER_UNIQUE_ID = "POISSON_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	public PoissonDriver() {
	}

	public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(POISSON_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		String nx = config.getSelectedHardwareParameterValue("Nx");
		String ny = config.getSelectedHardwareParameterValue("Ny");
		String nz = config.getSelectedHardwareParameterValue("Nz");
		String fnFace1 = config.getSelectedHardwareParameterValue("fnFace1");
		String fnFace2 = config.getSelectedHardwareParameterValue("fnFace2");
		String fnFace3 = config.getSelectedHardwareParameterValue("fnFace3");
		String fnFace4 = config.getSelectedHardwareParameterValue("fnFace4");
		String fnFace5 = config.getSelectedHardwareParameterValue("fnFace5");
		String fnFace6 = config.getSelectedHardwareParameterValue("fnFace6");
		String fnRho = config.getSelectedHardwareParameterValue("fnRho");

		int nSamples = config.getTotalSamples();

		// Vamos criar o nosso produtor de dados!
		dataSource = new PoissonDataProducer(this, nx, ny, nz, fnFace1, fnFace2, fnFace3, fnFace4, fnFace5, fnFace6,
				fnRho);

		// NECESSARIO!! Colocar o numero de amostra para todos os canais!
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		// NAO ESQUECER ESTA LINHA!
		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	public String getDriverUniqueID() {
		return DRIVER_UNIQUE_ID;
	}

	public void shutdown() {
		if (dataSource != null) {
			dataSource.stopNow();
		}
		super.shutDownNow();
	}

	public IDataSource start(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	public void stop(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopNow();
		fireIDriverStateListenerDriverStoped();
	}

	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();
		// MUITO IMPORTANTE!!! ALTERAR AS PROXIMAS DUAS LINHAS!!!
		String baseHardwareInfoFile = "recresource://pt/utl/ist/elab/driver/virtual/poisson/PoissonBaseHardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("eLab.Poisson.HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger("Poisson.logger"));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger("Poisson.logger"));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
