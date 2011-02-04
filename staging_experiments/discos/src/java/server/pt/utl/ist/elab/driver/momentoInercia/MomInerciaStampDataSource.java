/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.momentoInercia;

import pt.utl.ist.elab.driver.momentoInercia.processors.StampMomInerciaProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author jp
 */
public class MomInerciaStampDataSource extends AbstractStampDataSource {

	private static final int MAX_VELOCITY = 7200;

	/** Creates a new instance of RadioactividadeStampDataSource */
	public MomInerciaStampDataSource() {
	}

	private float delay_time = 0;
	private float timeDelay = -1;
	private float stop_time = 1000;

	public void processDataCommand(StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null)
			return;

		if (cmd.getCommandIdentifier().equals(StampMomInerciaProcessor.COMMAND_IDENTIFIER)) {

			float velocity;
			float power;
			long time;

			PhysicsValue[] values = new PhysicsValue[3];
			try {
				velocity = ((Float) cmd.getCommandData(StampMomInerciaProcessor.VELOCITY)).floatValue();
				power = ((Float) cmd.getCommandData(StampMomInerciaProcessor.POWER)).floatValue();
			} catch (ClassCastException e) {
				e.printStackTrace();
				return;
			}

			if (velocity > 7200) {
				velocity = 7200;
			}

			if (timeDelay == -1) {
				timeDelay = 0;
			} else {
				timeDelay += delay_time / 1000f;
			}

			if (!stopEnabled || timeDelay < stop_time) {
				/*
				 * System.out.println("1->" + timeDelay);
				 * System.out.println("2->" + stop_time);
				 */
				power = 0;
			}

			// System.out.println(timeDelay);

			values[0] = PhysicsValueFactory.fromFloat(velocity, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale());
			values[1] = PhysicsValueFactory.fromFloat(power, getAcquisitionHeader().getChannelsConfig(1)
					.getSelectedScale());
			values[2] = PhysicsValueFactory.fromFloat(timeDelay, getAcquisitionHeader().getChannelsConfig(2)
					.getSelectedScale());

			super.addDataRow(values);
		}

	}

	private boolean stopEnabled = false;

	public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);
		delay_time = (float) (getAcquisitionHeader().getSelectedFrequency().getFrequency());
		stop_time = Integer.parseInt(config.getSelectedHardwareParameterValue("Stop Iteration")) * delay_time / 1000f;

		String iteration = config.getSelectedHardwareParameterValue("Iteration");

		if (iteration.equalsIgnoreCase("Both") || iteration.equalsIgnoreCase("Stop")) {
			stopEnabled = true;
		} else {
			stopEnabled = false;
		}

		setPacketSize((int) Math.ceil(1. / (8. * config.getSelectedFrequency().getFrequency() * config
				.getSelectedFrequency().getMultiplier().getExpValue())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopNow() {
	}

}
