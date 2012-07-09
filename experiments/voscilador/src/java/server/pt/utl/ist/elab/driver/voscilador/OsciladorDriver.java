/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.voscilador;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author RF
 */

public class OsciladorDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(CartPoleDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "Oscilador_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of RobotDriver */
	public OsciladorDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// Recebemos ordem para configurar, no HardwareAcquisitionConfig estao
		// todas as informacoes escolhidas pelo cliente...agora e' so' pedir
		this.config = config;
		this.info = info;

		final float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
		final float a = Float.parseFloat(config.getSelectedHardwareParameterValue("a"));
		final float frequencia = Float.parseFloat(config.getSelectedHardwareParameterValue("frequencia"));
		final float alturaInicial = Float.parseFloat(config.getSelectedHardwareParameterValue("alturaInicial"));
		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		// Vamos criar o nosso produtor de dados!
		dataSource = new OsciladorDataProducer(this, g, a, frequencia, alturaInicial, tbs, nSamples);

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
		return OsciladorDriver.DRIVER_UNIQUE_ID;
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
