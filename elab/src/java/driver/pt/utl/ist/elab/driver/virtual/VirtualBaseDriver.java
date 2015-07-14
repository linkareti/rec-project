package pt.utl.ist.elab.driver.virtual;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author andre
 */
public abstract class VirtualBaseDriver extends BaseDriver {

	private static final Logger LOGGER = Logger.getLogger(VirtualBaseDriver.class.getName());

	/** Creates a new instance of VirtualBaseDriver */
	public VirtualBaseDriver() {
	}

	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		/** not going to use */
	}

	@Override
	public void init(final HardwareInfo info) {
		fireIDriverStateListenerDriverInited();
	}

	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
	}

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		return null;
	}

	public void stopVirtualHardware() {
		fireIDriverStateListenerDriverStoping();
		fireIDriverStateListenerDriverStoped();
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
			LOGGER.log(Level.SEVERE, "Error configuring hardware " + e.getMessage(), e);
			throw new WrongConfigurationException(e.getMessage(),1);
		}
	}

	
}
