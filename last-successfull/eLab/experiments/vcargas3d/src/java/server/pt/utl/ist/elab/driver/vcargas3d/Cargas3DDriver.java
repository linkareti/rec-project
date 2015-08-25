/*
 * Cargas3DDriver.java
 *
 * Created on 22 de Mar�o de 2005, 14:38
 */

package pt.utl.ist.elab.driver.vcargas3d;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author n0dP2
 */
public class Cargas3DDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(Cargas3DDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "CARGAS3D_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// Recebemos ordem para configurar, no HardwareAcquisitionConfig estao
		// todas as informacoes escolhidas pelo cliente...agora e' so' pedir
		this.config = config;
		this.info = info;

		final String sist = config.getSelectedHardwareParameterValue("Sistema");

		System.out.println("Received = " + sist);

		// Vamos criar o nosso produtor de dados!
		dataSource = new Cargas3DDataProducer(this, Sistema.stringToSistema(sist));

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
		return Cargas3DDriver.DRIVER_UNIQUE_ID;
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
