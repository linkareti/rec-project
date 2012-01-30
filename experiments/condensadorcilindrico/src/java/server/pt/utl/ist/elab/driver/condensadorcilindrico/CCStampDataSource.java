/*
 * CCStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.condensadorcilindrico;

import pt.utl.ist.elab.driver.condensadorcilindrico.processors.StampCCProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class CCStampDataSource extends AbstractStampDataSource {

	private int counter = 0;
	private int total_samples = 0;
    private static final Logger LOGGER = Logger.getLogger(CCStampDataSource.class.getName());

	public CCStampDataSource() {
	}

	@Override
	public void processDataCommand(final StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
            LOGGER.finest("discarting a non data or null command");
			return;
		}

		if (cmd.getCommandIdentifier().equals(StampCCProcessor.COMMAND_IDENTIFIER)) {
                        
			final Float capacity;
			final Float distance;
			final PhysicsValue[] values = new PhysicsValue[2];
			try {
				capacity = (Float) cmd.getCommandData(StampCCProcessor.CAPACITY);
				distance = (Float) cmd.getCommandData(StampCCProcessor.DISTANCE);
			} catch (final ClassCastException e) {
				LOGGER.log(Level.SEVERE, "Couldn't parse the capacity or distance while processing data!", e);
				return;
			}
			final float valorDistancia = distance.floatValue();
			final float valorCapacidade = capacity.floatValue();
            
            values[0] = new PhysicsValue(PhysicsValFactory.fromFloat(valorDistancia), getAcquisitionHeader()
						.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
						.getChannelsConfig(0).getSelectedScale().getMultiplier());
            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(valorCapacidade), getAcquisitionHeader()
						.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
						.getChannelsConfig(1).getSelectedScale().getMultiplier());
            
            
			super.addDataRow(values);

            LOGGER.finest("Added two new values as capacity: " + valorCapacidade + " and distance: " + valorDistancia);
            
			counter++;
			if (counter == total_samples) {
				try {
					Thread.currentThread();
					Thread.sleep(1000);
					setDataSourceEnded();
				} catch (final InterruptedException ignored) {}
			}
		}
	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);

		total_samples = config.getTotalSamples();
	}

	@Override
	public void stopNow() {
		setDataSourceStoped();
	}
}
