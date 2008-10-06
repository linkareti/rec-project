/*
 * PolaroidStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */
package pt.utl.ist.elab.driver.serial.stamp.polaroid;

import com.linkare.rec.impl.data.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.acquisition.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import pt.utl.ist.elab.driver.serial.stamp.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.polaroid.processors.*;

/**
 *
 * @author  jp
 */
public class PolaroidStampDataSource extends AbstractStampDataSource {

    /** Creates a new instance of RadioactividadeStampDataSource */
    public PolaroidStampDataSource() {
    }
    
    private int counter = 0;
    private int total_samples = 0;

    public void processDataCommand(StampCommand cmd) {
        Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"Processing DATA command "+cmd.getCommandIdentifier());
        Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"stoped= "+stopped+", cmd="+cmd+", cmd.isData="+cmd.isData());
        
        if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
            return;
        }
         
        if (cmd.getCommandIdentifier().equals(StampPolaroidProcessor.COMMAND_IDENTIFIER)) {
            int angulo;
            float intensidade;
            PhysicsValue[] values = new PhysicsValue[2];
            try {
                 Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"reading data values");
               
                angulo = ((Integer) cmd.getCommandData(StampPolaroidProcessor.ANGULO)).intValue();
                intensidade = ((Float) cmd.getCommandData(StampPolaroidProcessor.INTENSIDADE)).floatValue();
            } catch (ClassCastException e) {
                e.printStackTrace();
                return;
            }

            Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"Creating fisics values "+angulo+","+intensidade);

            values[0] = new PhysicsValue(PhysicsValFactory.fromInt(angulo),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier());

            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(intensidade),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier());
            
            Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"Dispatching new rows...");

            super.addDataRow(values);

            counter++;
            Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"Total samples is now ..."+counter);

            if (counter > total_samples) {
                Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO, "DataSource Ended");
                setDataSourceEnded();
            }
        }
    }
    
   public static String POLAROID_DS_LOGGER = "PolaroidDataSource.Logger";
    
    static {
        Logger l = LogManager.getLogManager().getLogger(POLAROID_DS_LOGGER);
        if (l == null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(POLAROID_DS_LOGGER));
        }
    }


    public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
        super.setAcquisitionHeader(config);

        total_samples = config.getTotalSamples();

        Logger.getLogger(POLAROID_DS_LOGGER).log(Level.INFO,"Total samples as defined in datasource is now "+config.getTotalSamples());

    }
    private boolean stopped = false;

    public void stopNow() {
        stopped = true;
        setDataSourceStoped();
    }
}
