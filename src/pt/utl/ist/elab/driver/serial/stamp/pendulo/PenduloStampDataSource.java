/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.pendulo;

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
import pt.utl.ist.elab.driver.serial.stamp.pendulo.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.pendulo.translators.*;
/**
 *
 * @author  jp
 */
public class PenduloStampDataSource extends AbstractStampDataSource
{
    
    private int counter = 0;
    private int total_samples = 0;
    
    public static String PENDULO_DS_LOGGER="PenduloDataSource.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(PENDULO_DS_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(PENDULO_DS_LOGGER));
	}
    }
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public PenduloStampDataSource()
    {
    }
    
    public void processDataCommand(StampCommand cmd)
    {
	if(stopped || cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
	    return;
	
	if(cmd.getCommandIdentifier().equals(StampPenduloProcessor.COMMAND_IDENTIFIER))
	{
	    
	    Float angle_vel;
	    PhysicsValue[] values=new PhysicsValue[1];
	    try
	    {
		angle_vel = (Float)cmd.getCommandData(StampPenduloProcessor.ANGLE_VEL);
	    }
	    catch(ClassCastException e)
	    {
		e.printStackTrace();
		return;
	    }
	    float valorAngleVel = angle_vel.floatValue();
	    values[0]=PhysicsValueFactory.fromFloat(valorAngleVel,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
	    super.addDataRow(values);
            
            counter++;
            
            if(counter == total_samples)
            {
                Logger.getLogger(PENDULO_DS_LOGGER).log(Level.INFO,"DataSource Ended");
                setDataSourceEnded();
            }
	}
	
    }
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
	super.setAcquisitionHeader(config);
	//setPacketSize((int)Math.ceil(config.getSelectedFrequency().getFrequency()/8.));
	//setPacketSize(1);
        total_samples = config.getTotalSamples();
        //Logger.getLogger(PENDULO_DS_LOGGER).log(Level.INFO,"Total samples = " + total_samples);
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }    
    
}
