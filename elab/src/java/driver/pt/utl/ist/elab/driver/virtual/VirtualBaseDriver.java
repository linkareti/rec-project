package pt.utl.ist.elab.driver.virtual;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.utils.Defaults;

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
			throw new WrongConfigurationException();
		}
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
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {

			LOGGER.log(Level.WARNING, "Unable to load resource: " + prop, e);
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LOGGER.log(Level.SEVERE, "Unable to load resource: " + baseHardwareInfoFile, e);

			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
