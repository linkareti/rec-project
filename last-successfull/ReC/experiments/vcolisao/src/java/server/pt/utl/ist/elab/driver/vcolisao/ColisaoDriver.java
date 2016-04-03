package pt.utl.ist.elab.driver.vcolisao;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author Emanuel Antunes
 */

public class ColisaoDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(ColisaoDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "COLISAO_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	public ColisaoDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// Recebemos ordem para configurar, no HardwareAcquisitionConfig estao
		// todas as informacoes escolhidas pelo cliente...agora e' so' pedir
		this.config = config;
		this.info = info;

		final float v1 = Float.parseFloat(config.getSelectedHardwareParameterValue("v1"));
		final float m1 = Float.parseFloat(config.getSelectedHardwareParameterValue("m1"));
		final float r1 = Float.parseFloat(config.getSelectedHardwareParameterValue("r1"));
		final float v2 = Float.parseFloat(config.getSelectedHardwareParameterValue("v1"));
		final float m2 = Float.parseFloat(config.getSelectedHardwareParameterValue("m1"));
		final float r2 = Float.parseFloat(config.getSelectedHardwareParameterValue("r2"));
		final int a = Integer.parseInt(config.getSelectedHardwareParameterValue("a"));
		final int elasticCollision = Integer.parseInt(config.getSelectedHardwareParameterValue("elasticCollision"));
		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		// Vamos criar o nosso produtor de dados!
		dataSource = new ColisaoDataProducer(this, v1, v2, r1, r2, m1, m2, a, elasticCollision, tbs, nSamples);

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
		return ColisaoDriver.DRIVER_UNIQUE_ID;
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
