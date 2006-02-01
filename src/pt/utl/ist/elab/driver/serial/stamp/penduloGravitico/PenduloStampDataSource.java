/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.penduloGravitico;

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
import pt.utl.ist.elab.driver.serial.stamp.penduloGravitico.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.penduloGravitico.translators.*;
/**
 *
 * @author  jp
 */
public class PenduloStampDataSource extends AbstractStampDataSource
{
    
    private int counter = 0;
    private int total_samples = 0;
    private float calib_angle = -1;
    
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
            float angle = 0;
            float angle_adc = 0;
            
            PhysicsValue[] values=new PhysicsValue[1];
            try
            {
                if(calib_angle == -1)
                    calib_angle = ((Float)cmd.getCommandData(StampPenduloProcessor.ANGLE_ADC)).floatValue();
                else
                {
                    angle_adc = ((Float)cmd.getCommandData(StampPenduloProcessor.ANGLE_ADC)).floatValue();
                    
                    angle = (calib_angle - angle_adc) / 49.41f;
                    
                    values[0]=PhysicsValueFactory.fromFloat(angle, getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
                    super.addDataRow(values);
                    
                }
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            
            if(counter == total_samples)
            {
                Logger.getLogger(PENDULO_DS_LOGGER).log(Level.INFO,"DataSource Ended");
                setDataSourceEnded();
            }
            
            counter++;                        
        }
        
    }
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        total_samples = config.getTotalSamples();
        calib_angle = -1;
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
