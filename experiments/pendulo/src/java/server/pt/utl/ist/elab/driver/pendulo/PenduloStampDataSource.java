/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.pendulo;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.pendulo.processors.StampPenduloProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PenduloStampDataSource extends AbstractStampDataSource {

	private int counter = 0;
	private int total_samples = 0;

	public static String PENDULO_DS_LOGGER = "PenduloDataSource.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(PENDULO_DS_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(PENDULO_DS_LOGGER));
		}
	}

	/** Creates a new instance of RadioactividadeStampDataSource */
	public PenduloStampDataSource() {
	}

	public void processDataCommand(StampCommand cmd) {
		if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null)
			return;

		if (cmd.getCommandIdentifier().equals(StampPenduloProcessor.COMMAND_IDENTIFIER)) {

			Float angle_vel;
			PhysicsValue[] values = new PhysicsValue[1];
			try {
				angle_vel = (Float) cmd.getCommandData(StampPenduloProcessor.ANGLE_VEL);
			} catch (ClassCastException e) {
				e.printStackTrace();
				return;
			}
			float valorAngleVel = angle_vel.floatValue();
			values[0] = PhysicsValueFactory.fromFloat(valorAngleVel, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale());
			super.addDataRow(values);

			counter++;

			if (counter == total_samples) {
				Logger.getLogger(PENDULO_DS_LOGGER).log(Level.INFO, "DataSource Ended");
				setDataSourceEnded();
			}
		}

	}

	public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);
		// setPacketSize((int)Math.ceil(config.getSelectedFrequency().getFrequency()/8.));
		// setPacketSize(1);
		total_samples = config.getTotalSamples();
		// Logger.getLogger(PENDULO_DS_LOGGER).log(Level.INFO,"Total samples = "
		// + total_samples);
	}

	private boolean stopped = false;

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

}
