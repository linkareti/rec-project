/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.condensadorcilindrico;

import pt.utl.ist.elab.driver.condensadorcilindrico.processors.StampCCProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class CCStampDataSource extends AbstractStampDataSource {

	private int counter = 0;
	private int total_samples = 0;

	/** Creates a new instance of RadioactividadeStampDataSource */
	public CCStampDataSource() {
	}

	@Override
	public void processDataCommand(final StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
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
				e.printStackTrace();
				return;
			}
			final float valorCapacidade = capacity.floatValue();
			final float valorDistancia = distance.floatValue();
			values[1] = PhysicsValueFactory.fromFloat(valorCapacidade, getAcquisitionHeader().getChannelsConfig(1)
					.getSelectedScale());
			values[2] = PhysicsValueFactory.fromFloat(valorDistancia, getAcquisitionHeader().getChannelsConfig(2)
					.getSelectedScale());
			super.addDataRow(values);

			counter++;
			if (counter == total_samples) {
				try {
					Thread.currentThread();
					Thread.sleep(1000);
					setDataSourceEnded();
				} catch (final InterruptedException ignored) {
				}
			}
		}

	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);

		total_samples = config.getTotalSamples();

		// setPacketSize((int)Math.ceil(1./(8.*config.getSelectedFrequency().getFrequency()*config.getSelectedFrequency().getMultiplier().getExpValue())));
	}

	@Override
	public void stopNow() {
		setDataSourceStoped();
	}
}
