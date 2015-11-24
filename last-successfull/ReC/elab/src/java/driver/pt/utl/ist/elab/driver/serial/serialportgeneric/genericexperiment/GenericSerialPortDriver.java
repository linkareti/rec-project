/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

import pt.utl.ist.elab.driver.serial.serialportgeneric.AbstractSerialPortDriver;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GenericSerialPortDriver extends AbstractSerialPortDriver {

	public GenericSerialPortDriver() {
		super();
	}

	@Override
	protected void loadExtraCommandHandlers() {

	}

	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

	}

}
