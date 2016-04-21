package pt.utl.ist.elab.driver.vdi;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author Pedro Queiró
 */

public class DIDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(DIDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "DISCOS_INERCIA_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of DIDriver */
	public DIDriver() {
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
}