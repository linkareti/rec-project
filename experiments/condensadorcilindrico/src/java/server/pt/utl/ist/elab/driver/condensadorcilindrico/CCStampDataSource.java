/*
 * CCStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */
package pt.utl.ist.elab.driver.condensadorcilindrico;

import pt.utl.ist.elab.driver.condensadorcilindrico.processors.StampCCProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class CCStampDataSource extends AbstractStampDataSource {

    private int counter = 0;
    private int total_samples = 0;
    private static final Logger LOGGER = Logger.getLogger(CCStampDataSource.class.getName());
    private static final String DISTANCE_CHANNEL_NAME = "Distance"; //TODO This should be read from a resource sth like -> ReCResourceBundle.findString("condensadorcilindrico$rec.exp.cc.hardwareinfo.channel.0.name");
    private static final String CAPACITY_CHANNEL_NAME = "Capacity"; //ReCResourceBundle.findString("condensadorcilindrico$rec.exp.cc.hardwareinfo.channel.1.name");

    public CCStampDataSource() {
    }

    @Override
    public void processDataCommand(final StampCommand cmd) {
        if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
            LOGGER.finest("discarting a non data or null command");
            return;
        }

        if (cmd.getCommandIdentifier().equals(StampCCProcessor.COMMAND_IDENTIFIER)) {

            final Float capacity;
            final Float distance;
            final PhysicsValue[] values = new PhysicsValue[2];
            try {
                capacity = (Float) cmd.getCommandData(StampCCProcessor.CAPACITY);
                distance = (Float) cmd.getCommandData(StampCCProcessor.DISTANCE);
            } catch (final ClassCastException e) {
                LOGGER.log(Level.SEVERE, "Couldn't parse the capacity or distance while processing data!", e);
                return;
            }
            
            final float valorDistancia = distance.floatValue();
            final float valorCapacity = capacity.floatValue();

            
            final ChannelAcquisitionConfig distanceChannel = getAcquisitionHeader().getChannelsConfig(DISTANCE_CHANNEL_NAME);
            final ChannelAcquisitionConfig capacityChannel = getAcquisitionHeader().getChannelsConfig(CAPACITY_CHANNEL_NAME);
            
            values[0] = new PhysicsValue(PhysicsValFactory.fromFloat(valorDistancia),
                distanceChannel.getSelectedScale().getDefaultErrorValue(),
                distanceChannel.getSelectedScale().getMultiplier());
            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(valorCapacity),
                capacityChannel.getSelectedScale().getDefaultErrorValue(),
                capacityChannel.getSelectedScale().getMultiplier());


            super.addDataRow(values);

            LOGGER.finest("Added two new values as capacity: " + valorCapacity + " and distance: " + valorDistancia);

            counter++;
            if (counter == total_samples) {
                try {
                    Thread.currentThread();
                    Thread.sleep(1000);
                    setDataSourceEnded();
                } catch (final InterruptedException ignored) {
                }
            }
        }
    }

    @Override
    public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
        super.setAcquisitionHeader(config);

        total_samples = config.getTotalSamples();
    }

    @Override
    public void stopNow() {
        setDataSourceStoped();
    }
}
