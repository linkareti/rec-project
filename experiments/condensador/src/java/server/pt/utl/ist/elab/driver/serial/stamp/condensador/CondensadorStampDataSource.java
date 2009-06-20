/*
 * CondensadorStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */


//TODO comentar

package pt.utl.ist.elab.driver.serial.stamp.condensador;

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
import pt.utl.ist.elab.driver.serial.stamp.condensador.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.condensador.translators.*;
/**
 *
 * @author  jp
 */
public class CondensadorStampDataSource extends AbstractStampDataSource
{
    
    private int counter = 0;
    private int total_samples = 0;
    
    /** Creates a new instance of RadioactividadeStampDataSource */
    public CondensadorStampDataSource()
    {
    }
    
    public void processDataCommand(StampCommand cmd)
    {
        if(cmd==null || !cmd.isData() || cmd.getCommandIdentifier()==null)
            return;
        
        if(cmd.getCommandIdentifier().equals(StampCondensadorProcessor.COMMAND_IDENTIFIER))
        {
            Integer frequencia;
            Float posicao;
            PhysicsValue[] values=new PhysicsValue[2];
            try
            {
                frequencia = (Integer)cmd.getCommandData(StampCondensadorProcessor.FREQUENCIA);
                posicao = (Float)cmd.getCommandData(StampCondensadorProcessor.POSICAO);
            }
            catch(ClassCastException e)
            {
                e.printStackTrace();
                return;
            }
            int valorFrequencia = frequencia.intValue();
            float valorPosicao = posicao.floatValue();
            values[0]=PhysicsValueFactory.fromFloat(valorFrequencia,getAcquisitionHeader().getChannelsConfig(0).getSelectedScale());
            values[1]=PhysicsValueFactory.fromFloat(valorPosicao,getAcquisitionHeader().getChannelsConfig(1).getSelectedScale());
            super.addDataRow(values);
            
            counter++;
            if(counter == total_samples)
            {
                try
                {
                    Thread.currentThread().sleep(1000);
                    setDataSourceEnded();
                }
                catch(InterruptedException ignored)
                {
                }
            }
        }
        
    }
    
    public void setAcquisitionHeader(HardwareAcquisitionConfig config)
    {
        super.setAcquisitionHeader(config);
        
        total_samples = config.getTotalSamples();
        
        //setPacketSize((int)Math.ceil(1./(8.*config.getSelectedFrequency().getFrequency()*config.getSelectedFrequency().getMultiplier().getExpValue())));
    }
    
    private boolean stopped = false;
    
    public void stopNow()
    {
        stopped = true;
        setDataSourceStoped();
    }
}
