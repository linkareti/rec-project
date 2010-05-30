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

	public void processDataCommand(StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null)
			return;

		if (cmd.getCommandIdentifier().equals(StampCCProcessor.COMMAND_IDENTIFIER)) {
			Float freq;
			Float capacidade;
			Float distancia;
			PhysicsValue[] values = new PhysicsValue[3];
			try {
				freq = (Float) cmd.getCommandData(StampCCProcessor.CAPACIDADE_FREQ_STR);
				capacidade = (Float) cmd.getCommandData(StampCCProcessor.CAPACIDADE_FARAD_STR);
				distancia = (Float) cmd.getCommandData(StampCCProcessor.DISTANCIA_STR);
			} catch (ClassCastException e) {
				e.printStackTrace();
				return;
			}
			float valorFreq = freq.floatValue();
			float valorCapacidade = capacidade.floatValue();
			float valorDistancia = distancia.floatValue();
			values[0] = PhysicsValueFactory.fromFloat(valorFreq, getAcquisitionHeader().getChannelsConfig(0)
					.getSelectedScale());
			values[1] = PhysicsValueFactory.fromFloat(valorCapacidade, getAcquisitionHeader().getChannelsConfig(1)
					.getSelectedScale());
			values[2] = PhysicsValueFactory.fromFloat(valorDistancia, getAcquisitionHeader().getChannelsConfig(2)
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
