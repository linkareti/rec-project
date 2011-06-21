/*
 * DIDriver.java
 *
 * Created on 3 de Abril de 2005, 6:09
 */

package pt.utl.ist.elab.driver.vdi;

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
 * @author Pedro Queiro'
 */

public class DIDriver extends VirtualBaseDriver {
	// O codigo desta classe e' sempre igual!!! Alterar so' os nomes para o
	// vosso caso!
	private static String DI_DRIVER_LOGGER = "DI.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(DIDriver.DI_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DIDriver.DI_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Discos de Inercia Driver)";
	private static final String DRIVER_UNIQUE_ID = "DISCOS_INERCIA_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of DIDriver */
	public DIDriver() {
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
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(DIDriver.DI_DRIVER_LOGGER));
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

		final double r1i = Double.parseDouble(config.getSelectedHardwareParameterValue("r1i"));
		final double r1e = Double.parseDouble(config.getSelectedHardwareParameterValue("r1e"));
		final double r2i = Double.parseDouble(config.getSelectedHardwareParameterValue("r2i"));
		final double r2e = Double.parseDouble(config.getSelectedHardwareParameterValue("r2e"));
		final double m1 = Double.parseDouble(config.getSelectedHardwareParameterValue("m1"));
		final double m2 = Double.parseDouble(config.getSelectedHardwareParameterValue("m2"));
		final double inc = Double.parseDouble(config.getSelectedHardwareParameterValue("inc"));

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		// Vamos criar o nosso produtor de dados!
		dataSource = new DIDataProducer(this, r1i, r1e, r2i, r2e, m1, m2, inc, tbs, nSamples);

		// NECESSARIO!! Colocar o numero de amostras para todos os canais!
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		// NAO ESQUECER ESTA LINHA!
		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return DIDriver.DRIVER_UNIQUE_ID;
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
			url = ReCProtocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			// Nao sera' de alterar isto para DI, ou algo do genero?
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger("DPendulum"));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				// E isto?
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger("DPendulum"));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}