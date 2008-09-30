/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.usb.cypress.gamma;

import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDataSource;
import pt.utl.ist.elab.driver.usb.cypress.gamma.processors.CypressGammaProcessor1;
import pt.utl.ist.elab.driver.usb.cypress.gamma.processors.CypressGammaProcessor2;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class GammaCypressDataSource extends AbstractCypressDataSource
{
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public GammaCypressDataSource()
    {
    }
    
    private float timeDelayMillis=0;
    private float tbs=0;
    private int counter = 0;
    private int total_samples = 0;
    
    public void processDataCommand(CypressCommand cmd)
    {
        if(stopped)
            return;
        
        if(stopped || cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equals(CypressGammaProcessor1.COMMAND_IDENTIFIER))
        {
            float mic;
            float pressao;
            PhysicsValue[] values = new PhysicsValue[4];
            try
            {
                mic = ((Float)cmd.getCommandData(CypressGammaProcessor1.ONDA_MIC)).floatValue();
                pressao = ((Float)cmd.getCommandData(CypressGammaProcessor1.PRESSAO)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            values[0] = new PhysicsValue(PhysicsValFactory.fromFloat(timeDelayMillis),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
            );
            
            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(pressao),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
            );
            
            values[2] = new PhysicsValue(PhysicsValFactory.fromFloat(mic),
            getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
            );
            super.addDataRow(values);
            
        }
        if(cmd.getCommandIdentifier().equals(CypressGammaProcessor2.COMMAND_IDENTIFIER))
        {
            float time;
            PhysicsValue[] values = new PhysicsValue[4];
            try
            {
                time = ((Float)cmd.getCommandData(CypressGammaProcessor2.TIME)).floatValue();
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            /*values[0] = new PhysicsValue(PhysicsValFactory.fromLong(timeDelayMillis),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
            );
            */
            values[3] = new PhysicsValue(PhysicsValFactory.fromFloat(time),
            getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
            getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier()
            );
            super.addDataRow(values);
            
        }
        
        counter++;
        if(counter == total_samples)
        {
            Thread t = new Thread()
            {
                public void run()
                {
                    try
                    {
                        sleep(2000);
                        setDataSourceEnded();
                    }
                    catch(InterruptedException ignored)
                    {                        
                    }
                }
            };
            t.start();
        }
        
        timeDelayMillis += tbs;
    }
    
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        
        total_samples = config.getTotalSamples();
        
        tbs = 1000 / (float)config.getSelectedFrequency().getFrequency();
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
