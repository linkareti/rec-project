/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.usb.cypress.polaroid;

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
import pt.utl.ist.elab.driver.usb.cypress.*;
import pt.utl.ist.elab.driver.usb.cypress.transproc.*;
import pt.utl.ist.elab.driver.usb.cypress.transproc.processors.*;
import pt.utl.ist.elab.driver.usb.cypress.polaroid.processors.*;
import pt.utl.ist.elab.driver.usb.cypress.polaroid.translators.*;
/**
 *
 * @author  jp
 */
public class PolaroidCypressDataSource extends AbstractCypressDataSource
{
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public PolaroidCypressDataSource()
    {
    }
    
    private int timeDelayMillis=-1;
    private float delay_time=0;
    private int counter = 0;
    private int total_samples = 0;
    
    public void processDataCommand(CypressCommand cmd)
    {
        if(stopped)
            return;
        
        if(stopped || cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equals(CypressPolaroidProcessor.COMMAND_IDENTIFIER))
        {
            int angulo;
            float intensidade;
            PhysicsValue[] values=new PhysicsValue[2];
            try
            {
                angulo = ((Integer)cmd.getCommandData(CypressPolaroidProcessor.ANGULO)).intValue();
                intensidade= ((Float)cmd.getCommandData(CypressPolaroidProcessor.INTENSIDADE)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            values[0] = new PhysicsValue(PhysicsValFactory.fromInt(angulo),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
            );
            
            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(intensidade),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
            );
            super.addDataRow(values);
            
            counter++;
            if(counter == total_samples)
                setDataSourceEnded();
        }
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
