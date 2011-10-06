/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.pendulogravitico;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.pendulogravitico.processors.StampPenduloProcessor;
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
	private float calib_angle = -1;

	public static String PENDULO_DS_LOGGER = "PenduloDataSource.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(PenduloStampDataSource.PENDULO_DS_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(PenduloStampDataSource.PENDULO_DS_LOGGER));
		}
	}

	/** Creates a new instance of RadioactividadeStampDataSource */
	public PenduloStampDataSource() {
	}

	@Override
	public void processDataCommand(final StampCommand cmd) {
		if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			return;
		}

		if (cmd.getCommandIdentifier().equals(StampPenduloProcessor.COMMAND_IDENTIFIER)) {
			float angle = 0;
			float angle_adc = 0;

			final PhysicsValue[] values = new PhysicsValue[1];
			try {
				if (calib_angle == -1) {
					calib_angle = ((Float) cmd.getCommandData(StampPenduloProcessor.ANGLE_ADC)).floatValue();
				} else {
					angle_adc = ((Float) cmd.getCommandData(StampPenduloProcessor.ANGLE_ADC)).floatValue();

					angle = (calib_angle - angle_adc) / 49.41f;

					values[0] = PhysicsValueFactory.fromFloat(angle, getAcquisitionHeader().getChannelsConfig(0)
							.getSelectedScale());
					super.addDataRow(values);

				}
			} catch (final ClassCastException e) {
				e.printStackTrace();
				return;
			}

			if (counter == total_samples) {
				Logger.getLogger(PenduloStampDataSource.PENDULO_DS_LOGGER).log(Level.INFO, "DataSource Ended");
				setDataSourceEnded();
			}

			counter++;
		}

	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);
		total_samples = config.getTotalSamples();
		calib_angle = -1;
	}

	private boolean stopped = false;

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
