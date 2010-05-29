/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.pv;

import pt.utl.ist.elab.driver.pv.processors.StampPVProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PVStampDataSource extends AbstractStampDataSource {

	private int counter = 0;
	private int total_samples = 0;

	/** Creates a new instance of RadioactividadeStampDataSource */
	public PVStampDataSource() {
	}

	public void processDataCommand(StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null)
			return;

		if (cmd.getCommandIdentifier().equals(StampPVProcessor.COMMAND_IDENTIFIER)) {
			Integer pressure;
			Float volume;
			PhysicsValue[] values = new PhysicsValue[2];
			try {
				pressure = (Integer) cmd.getCommandData(StampPVProcessor.PRESSAO);
				volume = (Float) cmd.getCommandData(StampPVProcessor.VOLUME);
			} catch (ClassCastException e) {
				e.printStackTrace();
				return;
			}
			int valorPressao = pressure.intValue();
			float valorVolume = volume.floatValue();
			values[0] = PhysicsValueFactory.fromFloat(valorPressao, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale());
			values[1] = PhysicsValueFactory.fromFloat(valorVolume, getAcquisitionHeader().getChannelsConfig(1)
					.getSelectedScale());
			super.addDataRow(values);

			counter++;
			if (counter == total_samples) {
				try {
					Thread.currentThread().sleep(1000);
					setDataSourceEnded();
				} catch (InterruptedException ignored) {
				}
			}
		}

	}

	public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);

		total_samples = config.getTotalSamples();

		// setPacketSize((int)Math.ceil(1./(8.*config.getSelectedFrequency().getFrequency()*config.getSelectedFrequency().getMultiplier().getExpValue())));
	}

	private boolean stopped = false;

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
