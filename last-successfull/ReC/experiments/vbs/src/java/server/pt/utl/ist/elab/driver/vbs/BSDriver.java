package pt.utl.ist.elab.driver.vbs;

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
public class BSDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(BSDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "BIOT_SAVART_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of BSDriver */
	public BSDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// Recebemos ordem para configurar, no HardwareAcquisitionConfig estao
		// todas as informacoes escolhidas pelo cliente...agora e' so' pedir
		this.config = config;
		this.info = info;

		final double i1_ini = Double.parseDouble(config.getSelectedHardwareParameterValue("i1_ini"));
		final double i1_fin = Double.parseDouble(config.getSelectedHardwareParameterValue("i1_fin"));
		final double i2_ini = Double.parseDouble(config.getSelectedHardwareParameterValue("i2_ini"));
		final double i2_fin = Double.parseDouble(config.getSelectedHardwareParameterValue("i2_fin"));
		final double dist = Double.parseDouble(config.getSelectedHardwareParameterValue("dist"));
		final double xpto = Double.parseDouble(config.getSelectedHardwareParameterValue("xpto"));
		final double ypto = Double.parseDouble(config.getSelectedHardwareParameterValue("ypto"));
		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		// Vamos criar o nosso produtor de dados!
		dataSource = new BSDataProducer(this, i1_ini, i1_fin, i2_ini, i2_fin, dist, xpto, ypto, tbs, nSamples);

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
		return BSDriver.DRIVER_UNIQUE_ID;
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