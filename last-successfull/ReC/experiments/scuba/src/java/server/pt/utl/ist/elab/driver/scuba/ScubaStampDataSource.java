/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.scuba;

import pt.utl.ist.elab.driver.scuba.processors.StampScubaProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ScubaStampDataSource extends AbstractStampDataSource {

	/** Creates a new instance of RadioactividadeStampDataSource */
	public ScubaStampDataSource() {
	}

	private int counter = 0;
	private int total_samples = 0;

	@Override
	public void processDataCommand(final StampCommand cmd) {
		if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			return;
		}

		if (cmd.getCommandIdentifier().equals(StampScubaProcessor.COMMAND_IDENTIFIER)) {
			Float pressure0, pressure1, pressure2, pressure3;
			Integer deep;
			final PhysicsValue[] values = new PhysicsValue[5];
			try {
				pressure0 = (Float) cmd.getCommandData(StampScubaProcessor.PRESSAO_CH_0);
				pressure1 = (Float) cmd.getCommandData(StampScubaProcessor.PRESSAO_CH_1);
				pressure2 = (Float) cmd.getCommandData(StampScubaProcessor.PRESSAO_CH_2);
				pressure3 = (Float) cmd.getCommandData(StampScubaProcessor.PRESSAO_CH_3);
				deep = (Integer) cmd.getCommandData(StampScubaProcessor.PROFUNDIDADE);
			} catch (final ClassCastException e) {
				e.printStackTrace();
				return;
			}
			final float valorPressao0 = pressure0.floatValue();
			final float valorPressao1 = pressure1.floatValue();
			final float valorPressao2 = pressure2.floatValue();
			final float valorPressao3 = pressure3.floatValue();
			final int valorProfundidade = deep.intValue();

			values[0] = PhysicsValueFactory.fromFloat(valorPressao0, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale());
			values[1] = PhysicsValueFactory.fromFloat(valorPressao1, getAcquisitionHeader().getChannelsConfig(1)
					.getSelectedScale());
			values[2] = PhysicsValueFactory.fromFloat(valorPressao2, getAcquisitionHeader().getChannelsConfig(2)
					.getSelectedScale());
			values[3] = PhysicsValueFactory.fromFloat(valorPressao3, getAcquisitionHeader().getChannelsConfig(3)
					.getSelectedScale());
			values[4] = PhysicsValueFactory.fromInt(valorProfundidade, getAcquisitionHeader().getChannelsConfig(3)
					.getSelectedScale());
			super.addDataRow(values);
			counter++;
			if (counter == total_samples) {
				setDataSourceEnded();
			}

		}
	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);
		total_samples = config.getTotalSamples();
		setPacketSize((int) Math.ceil(1. / (8. * config.getSelectedFrequency().getFrequency() * config
				.getSelectedFrequency().getMultiplier().getExpValue())));
	}

	private boolean stopped = false;

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
