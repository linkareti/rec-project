/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.rayleigh;

import pt.utl.ist.elab.driver.rayleigh.processors.StampRayleighProcessor;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class RayleighStampDataSource extends AbstractStampDataSource
{   
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public RayleighStampDataSource()
    {
    }
    
    private float delay_time = 0;
    private float timeDelay = -1;
    private double division = 1;
    private double lnvalue = 0;
    private boolean first = true;
    
    public void processDataCommand(StampCommand cmd)
    {
	if(cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
	    return;
        
	if(cmd.getCommandIdentifier().equalsIgnoreCase(StampRayleighProcessor.COMMAND_IDENTIFIER))
	{	    	    
            PhysicsValue[] values=new PhysicsValue[3];
            int pos = Integer.parseInt(cmd.getCommandData(StampRayleighProcessor.POSITION).toString());
	    double value = Double.parseDouble(cmd.getCommandData(StampRayleighProcessor.SIGNAL).toString()); 
            values[0] = PhysicsValueFactory.fromInt(pos,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
	    values[1] = PhysicsValueFactory.fromDouble(value,getAcquisitionHeader().getChannelsConfig(1).getSelectedScale());                                   
            if(first)
            {
                division = value;
                first = false;
            }
            
            lnvalue = Math.log(value/division);
            
            values[2] = PhysicsValueFactory.fromDouble(lnvalue,getAcquisitionHeader().getChannelsConfig(2).getSelectedScale());                                   
	    super.addDataRow(values);
	}
    }        
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
	super.setAcquisitionHeader(config);
        delay_time = 1000/(float)(getAcquisitionHeader().getSelectedFrequency().getFrequency());
        setPacketSize(1);
	//setPacketSize((int)Math.ceil(1./(8.*config.getSelectedFrequency().getFrequency()*config.getSelectedFrequency().getMultiplier().getExpValue())));
    }

    public void stopNow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
