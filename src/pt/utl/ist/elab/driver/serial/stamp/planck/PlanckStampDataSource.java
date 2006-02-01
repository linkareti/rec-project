/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.planck;

import com.linkare.rec.impl.data.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.serial.stamp.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.planck.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.planck.translators.*;
/**
 *
 * @author  jp
 */
public class PlanckStampDataSource extends AbstractStampDataSource
{
    private int counter = 0;
    private int total_samples = 0;
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public PlanckStampDataSource()
    {
    }
    
    private float delay_time = 0;
    private float timeDelay = -1;
    
    public void processDataCommand(StampCommand cmd)
    {
        if(cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equalsIgnoreCase(StampPlanck2Processor.COMMAND_IDENTIFIER))
        {
            long time;
            float potential;
            
            PhysicsValue[] values=new PhysicsValue[4];
            try
            {
                potential = ((Float)cmd.getCommandData(StampPlanck2Processor.FOTOCEL)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            if(timeDelay ==-1)
            {
                timeDelay = 0;
            }
            else
            {
                timeDelay += delay_time;
            }
            
            values[0] = PhysicsValueFactory.fromFloat(timeDelay,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
            values[3] = PhysicsValueFactory.fromFloat(potential,getAcquisitionHeader().getChannelsConfig(1).getSelectedScale());
            super.addDataRow(values);
        }
        else if(cmd.getCommandIdentifier().equalsIgnoreCase(StampPlanck0Processor.COMMAND_IDENTIFIER))
        {
            float teta;
            float potential;
            
            PhysicsValue[] values=new PhysicsValue[4];
            try
            {
                potential = ((Float)cmd.getCommandData(StampPlanck0Processor.FOTOCEL)).floatValue();
                teta = ((Float)cmd.getCommandData(StampPlanck0Processor.TETA)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            values[1] = PhysicsValueFactory.fromFloat(teta,getAcquisitionHeader().getChannelsConfig(1).getSelectedScale());
            values[2] = PhysicsValueFactory.fromFloat(potential,getAcquisitionHeader().getChannelsConfig(2).getSelectedScale());
            super.addDataRow(values);
        }
        else if(cmd.getCommandIdentifier().equalsIgnoreCase(StampPlanck1Processor.COMMAND_IDENTIFIER))
        {
            float teta;
            float potential;
            
            PhysicsValue[] values=new PhysicsValue[4];
            try
            {
                potential = ((Float)cmd.getCommandData(StampPlanck0Processor.FOTOCEL)).floatValue();
                teta = ((Float)cmd.getCommandData(StampPlanck0Processor.TETA)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            values[1] = PhysicsValueFactory.fromFloat(teta,getAcquisitionHeader().getChannelsConfig(1).getSelectedScale());
            values[2] = PhysicsValueFactory.fromFloat(potential,getAcquisitionHeader().getChannelsConfig(2).getSelectedScale());
            super.addDataRow(values);
        }
        else if(cmd.getCommandIdentifier().equalsIgnoreCase(StampPlanck3Processor.COMMAND_IDENTIFIER))
        {
            PhysicsValue[] values=new PhysicsValue[4];
            super.addDataRow(values);
        }
        counter++;
        if(counter == total_samples)
            setDataSourceEnded();
    }
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        delay_time = 1000/(float)(getAcquisitionHeader().getSelectedFrequency().getFrequency());
        setPacketSize((int)Math.ceil(1./(8.*config.getSelectedFrequency().getFrequency()*config.getSelectedFrequency().getMultiplier().getExpValue())));
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
    
    
}
