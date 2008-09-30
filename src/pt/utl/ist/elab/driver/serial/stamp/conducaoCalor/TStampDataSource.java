/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.conducaoCalor;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.conducaoCalor.processors.StampTProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class TStampDataSource extends AbstractStampDataSource
{
    private static final float TEMP_MAX_BRASS = 90;
    private static final float TEMP_MAX_IRON = 90;
    private static final float TEMP_MAX_COPPER = 90;
    
    public static String HEAT_DS_LOGGER="HeatDataSource.Logger";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(HEAT_DS_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(HEAT_DS_LOGGER));
        }
    }
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public TStampDataSource()
    {
    }
    
    private float delay_time = 0;
    private long timeDelay = -1;
    private int counter = 0;
    private int total_samples = 0;
    
    public void processDataCommand(StampCommand cmd)
    {
        if(cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equals(StampTProcessor.COMMAND_IDENTIFIER))
        {
            
            float temperature1Brass;
            float temperature2Brass;
            float temperature3Brass;
            float temperature1Iron;
            float temperature2Iron;
            float temperature3Iron;
            float temperature1Copper;
            float temperature2Copper;
            float temperature3Copper;
            PhysicsValue[] values=new PhysicsValue[10];
            try
            {
                temperature1Brass= ((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_1_BRASS)).floatValue();
                temperature2Brass=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_2_BRASS)).floatValue();
                temperature3Brass=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_3_BRASS)).floatValue();
                temperature1Iron=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_1_IRON)).floatValue();
                temperature2Iron=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_2_IRON)).floatValue();
                temperature3Iron=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_3_IRON)).floatValue();
                temperature1Copper=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_1_COPPER)).floatValue();
                temperature2Copper=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_2_COPPER)).floatValue();
                temperature3Copper=((Float)cmd.getCommandData(StampTProcessor.TEMPERATURE_3_COPPER)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            if(temperature1Brass > TEMP_MAX_BRASS)
            {
                temperature1Brass = TEMP_MAX_BRASS;
            }
            if(temperature2Brass > TEMP_MAX_BRASS)
            {
                temperature2Brass = TEMP_MAX_BRASS;
            }
            if(temperature3Brass > TEMP_MAX_BRASS)
            {
                temperature3Brass = TEMP_MAX_BRASS;
            }
            if(temperature1Iron > TEMP_MAX_IRON)
            {
                temperature1Iron = TEMP_MAX_IRON;
            }
            if(temperature2Iron > TEMP_MAX_IRON)
            {
                temperature2Iron = TEMP_MAX_IRON;
            }
            if(temperature3Iron > TEMP_MAX_IRON)
            {
                temperature3Iron = TEMP_MAX_IRON;
            }
            if(temperature1Copper > TEMP_MAX_COPPER)
            {
                temperature1Copper = TEMP_MAX_COPPER;
            }
            if(temperature2Copper > TEMP_MAX_COPPER)
            {
                temperature2Copper = TEMP_MAX_COPPER;
            }
            if(temperature3Copper > TEMP_MAX_COPPER)
            {
                temperature3Copper = TEMP_MAX_COPPER;
            }
            
            values[0]=PhysicsValueFactory.fromFloat(temperature1Brass,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
            values[1]=PhysicsValueFactory.fromFloat(temperature2Brass,getAcquisitionHeader().getChannelsConfig(1).getSelectedScale());
            values[2]=PhysicsValueFactory.fromFloat(temperature3Brass,getAcquisitionHeader().getChannelsConfig(2).getSelectedScale());
            values[3]=PhysicsValueFactory.fromFloat(temperature1Iron,getAcquisitionHeader().getChannelsConfig(3).getSelectedScale());
            values[4]=PhysicsValueFactory.fromFloat(temperature2Iron,getAcquisitionHeader().getChannelsConfig(4).getSelectedScale());
            values[5]=PhysicsValueFactory.fromFloat(temperature3Iron,getAcquisitionHeader().getChannelsConfig(5).getSelectedScale());
            values[6]=PhysicsValueFactory.fromFloat(temperature1Copper,getAcquisitionHeader().getChannelsConfig(6).getSelectedScale());
            values[7]=PhysicsValueFactory.fromFloat(temperature2Copper,getAcquisitionHeader().getChannelsConfig(7).getSelectedScale());
            values[8]=PhysicsValueFactory.fromFloat(temperature3Copper,getAcquisitionHeader().getChannelsConfig(8).getSelectedScale());
            
            if(timeDelay ==-1)
            {
                timeDelay = 0;
            }
            else
            {
                timeDelay += delay_time;
            }
            
            values[9]=PhysicsValueFactory.fromFloat(timeDelay,getAcquisitionHeader().getChannelsConfig(9).getSelectedScale());
            
            super.addDataRow(values);
            counter++;
            if(counter == total_samples || stopped)
                setDataSourceEnded();
        }
        
    }
    
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        total_samples = config.getTotalSamples();
        delay_time = (float)(getAcquisitionHeader().getSelectedFrequency().getFrequency());
        //setPacketSize((int)Math.ceil(1./(8.*config.getSelectedFrequency().getFrequency()*config.getSelectedFrequency().getMultiplier().getExpValue())));
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
