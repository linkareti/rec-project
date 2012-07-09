package pt.utl.ist.elab.driver.vpoisson;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author André
 */

public class PoissonDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(PoissonDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "POISSON_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	public PoissonDriver() {
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

		// int nSamples = config.getTotalSamples();

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

}
