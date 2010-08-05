/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.g;

import pt.utl.ist.elab.driver.g.processors.StampGProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GStampDataSource extends AbstractStampDataSource {

	/** Creates a new instance of RadioactividadeStampDataSource */
	public GStampDataSource() {
	}

	private int timeDelayMillis = -1;
	private float delay_time = 0;
	private int counter = 0;
	private int total_samples = 0;

	public void processDataCommand(StampCommand cmd) {
		if (stopped)
			return;

		if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null)
			return;

		if (cmd.getCommandIdentifier().equals(StampGProcessor.COMMAND_IDENTIFIER)) {

			float height;
			PhysicsValue[] values = new PhysicsValue[2];
			try {
				height = ((Float) cmd.getCommandData(StampGProcessor.ALTURA)).floatValue();
				if (timeDelayMillis == -1) {
					timeDelayMillis = (int) (0.0088 * 1000.0 + height * 2.0 * 1000.0 / 340.0);
				} else {
					timeDelayMillis += (int) (0.0088 * 1000.0 + height * 2.0 * 1000.0 / 340.0 + delay_time * 1000.0);
				}
			} catch (ClassCastException e) {
				e.printStackTrace();
				return;
			}

			values[0] = new PhysicsValue(PhysicsValFactory.fromFloat(height), getAcquisitionHeader().getChannelsConfig(
					0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale().getMultiplier());
			values[1] = new PhysicsValue(PhysicsValFactory.fromInt(timeDelayMillis), getAcquisitionHeader()
					.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
					.getChannelsConfig(1).getSelectedScale().getMultiplier());
			super.addDataRow(values);

			counter++;
			if (counter == total_samples)
				setDataSourceEnded();
		}
	}

	public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);

		total_samples = config.getTotalSamples();

		delay_time = (float) (getAcquisitionHeader().getSelectedFrequency().getFrequency() * getAcquisitionHeader()
				.getSelectedFrequency().getMultiplier().getExpValue());
		setPacketSize((int) Math.ceil(1. / (8. * config.getSelectedFrequency().getFrequency() * config
				.getSelectedFrequency().getMultiplier().getExpValue())));
	}

	private boolean stopped = false;

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
