/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.radioactividade;

import pt.utl.ist.elab.driver.radioactividade.processors.StampCounterProcessor;
import pt.utl.ist.elab.driver.radioactividade.processors.StampTimeProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class RadioactividadeStampDataSource extends AbstractStampDataSource {

	private int counter = 0;
	private int total_samples = 0;

	/** Creates a new instance of RadioactividadeStampDataSource */
	public RadioactividadeStampDataSource() {
	}

	@Override
	public void processDataCommand(final StampCommand cmd) {
		if (stopped) {
			return;
		}

		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			return;
		}

		if (cmd.getCommandIdentifier().equals(StampCounterProcessor.COMMAND_IDENTIFIER)) {
			Integer hits;
			final PhysicsValue[] values = new PhysicsValue[1];
			try {
				hits = (Integer) cmd.getCommandData(StampCounterProcessor.COUNTER);
			} catch (final ClassCastException e) {
				e.printStackTrace();
				return;
			}
			final int valorHits = hits.intValue();
			final PhysicsVal valueHits = PhysicsValFactory.fromInt(valorHits);
			final PhysicsVal errorHits = getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
					.getDefaultErrorValue();
			values[0] = new PhysicsValue(valueHits, errorHits, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale().getMultiplier());
			super.addDataRow(values);
		} else if (cmd.getCommandIdentifier().equals(StampTimeProcessor.COMMAND_IDENTIFIER)) {
			Integer hits;
			final PhysicsValue[] values = new PhysicsValue[1];
			try {
				hits = (Integer) cmd.getCommandData(StampTimeProcessor.HITS);
			} catch (final ClassCastException e) {
				e.printStackTrace();
				return;
			}
			final int valorHits = hits.intValue();
			values[0] = PhysicsValueFactory.fromInt(valorHits, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale());
			super.addDataRow(values);
		}

		counter++;
		if (counter == total_samples) {
			setDataSourceEnded();
		}

	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);

		total_samples = config.getTotalSamples();
	}

	private boolean stopped = false;

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}