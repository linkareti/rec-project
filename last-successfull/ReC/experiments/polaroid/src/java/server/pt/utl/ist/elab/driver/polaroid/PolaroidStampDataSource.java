/*
 * PolaroidStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.polaroid;

import pt.utl.ist.elab.driver.polaroid.processors.StampPolaroidProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PolaroidStampDataSource extends AbstractStampDataSource {

	/** Creates a new instance of RadioactividadeStampDataSource */
	public PolaroidStampDataSource() {
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

		if (cmd.getCommandIdentifier().equals(StampPolaroidProcessor.COMMAND_IDENTIFIER)) {
			int angulo;
			float intensidade;
			PhysicsValue[] values = new PhysicsValue[2];
			try {
				angulo = ((Integer) cmd.getCommandData(StampPolaroidProcessor.ANGULO)).intValue();
				intensidade = ((Float) cmd.getCommandData(StampPolaroidProcessor.INTENSIDADE)).floatValue();
			} catch (ClassCastException e) {
				e.printStackTrace();
				return;
			}

			values[0] = new PhysicsValue(PhysicsValFactory.fromInt(angulo), getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale().getMultiplier());

			values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(intensidade), getAcquisitionHeader()
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

	}

	private boolean stopped = false;

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
