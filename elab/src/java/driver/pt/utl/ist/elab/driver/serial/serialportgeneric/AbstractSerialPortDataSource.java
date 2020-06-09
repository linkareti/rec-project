/*
 * AbstractStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 18:03
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.HardwareNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneChannelNode;

import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.driver.BaseDataSource;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */
public abstract class AbstractSerialPortDataSource extends BaseDataSource {

	private static final Logger LOGGER = Logger.getLogger(AbstractSerialPortDataSource.class.getName());

	private int counter = 0;
	private int total_samples = 0;
	private HardwareNode rs232configs = null;

	public final static int MAX_PARAMETERS_ON_CONFIG = 8;

	/**
	 * @param rs232configs the rs232configs to set
	 */
	public void setRs232configs(final HardwareNode rs232configs) {
		this.rs232configs = rs232configs;
	}

	public PhysicsValue[] processDataCommand(final SerialPortCommand cmd) {

		// _DAT e _BIN
		if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			return null;
		}

		final PhysicsValue[] values = new PhysicsValue[cmd.getDataHashMap().size()];

		if (cmd.getCommandIdentifier().equals(SerialPortCommandList.DAT.toString())) {

			for (int channelNumber = 0; channelNumber < cmd.getDataHashMap().size(); channelNumber++) {
				PhysicsVal phValue = null;
				PhysicsVal phError = null;
				Multiplier phMultiplier = null;

				final OneChannelNode oneChannelNode = rs232configs.getRs232().getChannels()
						.getChannelToOrder(channelNumber);

				if (channelNumber < getAcquisitionHeader().getChannelsConfig().length) {
					// these are pure data channels
					phValue = PhysicsValFactory.fromDouble(oneChannelNode.calculate(Double.valueOf(cmd.getDataHashMap()
							.get(channelNumber))));
					phError = getAcquisitionHeader().getChannelsConfig(channelNumber).getSelectedScale()
							.getDefaultErrorValue();
					phMultiplier = getAcquisitionHeader().getChannelsConfig(channelNumber).getSelectedScale()
							.getMultiplier();
				} else if (channelNumber == getAcquisitionHeader().getChannelsConfig().length) {
					// this is a clock value channel
					// so it is a direct value without transformation
					phValue = PhysicsValFactory.fromLong(Long.valueOf(cmd.getDataHashMap().get(channelNumber)));
					// phError =
					// getAcquisitionHeader().getChannelsConfig(channelNumber).getSelectedScale()
					// .getDefaultErrorValue();
					// phMultiplier =
					// getAcquisitionHeader().getChannelsConfig(channelNumber).getSelectedScale()
					// .getMultiplier();
				} else {
					// this is trash and should be ignored
				}

				if (phValue != null) {
					values[channelNumber] = new PhysicsValue(phValue, phError, phMultiplier);
				}
			}
			super.addDataRow(values);
		} else if (cmd.getCommandIdentifier().equals(SerialPortCommandList.BIN)) {
			// TODO : what must I do with this?
		}

		counter++;
		if (counter == total_samples) {
			setDataSourceEnded();
		}

		return values;

	}

	public void processBinaryCommand(final SerialPortCommand cmd) {
		// TODO : what must I do with this?

	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		super.setAcquisitionHeader(config);
		LOGGER.log(Level.FINE, "Setting Hardware Acquisition Config [" + config + "]");

		total_samples = config.getTotalSamples();

		// this is a formula from g experiment
		final int packetSize = calcPacketSize(config);
		LOGGER.log(Level.FINEST, "Setting packet size to [" + packetSize + "]");

		setPacketSize(packetSize);
	}

	/**
	 * Method with the packet size formula.
	 * 
	 * @param config experiment configuration with the frequency defined
	 * @return
	 */
	protected int calcPacketSize(final HardwareAcquisitionConfig config) {
		return (int) Math.ceil(1. / (8. * config.getSelectedFrequency().getFrequency() * config.getSelectedFrequency()
				.getMultiplier().getExpValue()));
	}

	private boolean stopped = false;

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

}