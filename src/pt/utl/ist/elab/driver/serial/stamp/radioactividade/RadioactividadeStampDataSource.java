/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.radioactividade;

import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.data.*;
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
import pt.utl.ist.elab.driver.serial.stamp.radioactividade.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.radioactividade.translators.*;
/**
 *
 * @author  jp
 */
public class RadioactividadeStampDataSource extends AbstractStampDataSource
{
    
    private int counter = 0;
    private int total_samples = 0;
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public RadioactividadeStampDataSource()
    {
    }
    
    public void processDataCommand(StampCommand cmd)
    {
        if(stopped)
            return;
        
        if(cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equals(StampCounterProcessor.COMMAND_IDENTIFIER))
        {
            Integer hits;
            PhysicsValue[] values=new PhysicsValue[1];
            try
            {
                hits = (Integer)cmd.getCommandData(StampCounterProcessor.COUNTER);
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            int valorHits = hits.intValue();
            PhysicsVal valueHits=PhysicsValFactory.fromInt( valorHits );
            PhysicsVal errorHits=getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue();
            values[0]=new PhysicsValue(valueHits,errorHits,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier());
            super.addDataRow(values);
        }
        else if(cmd.getCommandIdentifier().equals(StampTimeProcessor.COMMAND_IDENTIFIER))
        {
            Integer hits;
            PhysicsValue[] values=new PhysicsValue[1];
            try
            {
                hits = (Integer)cmd.getCommandData(StampTimeProcessor.HITS);
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            int valorHits = hits.intValue();
            values[0]=PhysicsValueFactory.fromInt(valorHits,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
            super.addDataRow(values);
        }
        
        counter++;
        if(counter == total_samples)
            setDataSourceEnded();
        
    }
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        
        total_samples = config.getTotalSamples();
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
