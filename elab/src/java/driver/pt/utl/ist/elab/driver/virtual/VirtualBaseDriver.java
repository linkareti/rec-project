/*
 * VirtualBaseDriver.java
 *
 * Created on October 16, 2004, 6:39 PM
 */

package pt.utl.ist.elab.driver.virtual;

/**
 *
 * @author  andre
 */

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;

public abstract class VirtualBaseDriver extends BaseDriver {
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
}
