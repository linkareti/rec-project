/*
 * RadioactividadeStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */
package pt.utl.ist.elab.driver.serial.stamp.gamma;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.gamma.processors.StampGammaProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class GammaStampDataSource extends AbstractStampDataSource {

    private int counter = 0;
    private int total_samples = 0;
    private float timeDelayMillis = 0;
    private float tbs = 0;
    public static String GAMMA_DS_LOGGER = "GammaDataSource.Logger";
    

    static {
        Logger l = LogManager.getLogManager().getLogger(GAMMA_DS_LOGGER);
        if (l == null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(GAMMA_DS_LOGGER));
        }
    }

    /** Creates a new instance of RadioactividadeStampDataSource */
    public GammaStampDataSource() {
    }

    public void processDataCommand(StampCommand cmd) {
        if (stopped || cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
            return;
        }
        if (cmd.getCommandIdentifier().equals(StampGammaProcessor.COMMAND_IDENTIFIER)) {
            float mic=0;
            float pressao=0;
            float time=0;
            PhysicsValue[] values = new PhysicsValue[4];
            try {
			   if(counter>0){
                mic = ((Float) cmd.getCommandData(StampGammaProcessor.ONDA_MIC)).floatValue();
                pressao = ((Float) cmd.getCommandData(StampGammaProcessor.PRESSAO)).floatValue();
                time = ((Float) cmd.getCommandData(StampGammaProcessor.TIME)).floatValue();
				}
            } catch (ClassCastException e) {
                e.printStackTrace();
                return;
            }
			
			if(counter>0){
            values[0] = new PhysicsValue(PhysicsValFactory.fromFloat(timeDelayMillis),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier());

            values[1] = new PhysicsValue(PhysicsValFactory.fromFloat(pressao),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier());

            values[2] = new PhysicsValue(PhysicsValFactory.fromFloat(mic),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier());

            values[3] = new PhysicsValue(PhysicsValFactory.fromFloat(time*(counter-1)),
                    getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier());
            
            super.addDataRow(values);
			}

        }
        

        counter++;
        if (counter > total_samples) {
            Logger.getLogger(GAMMA_DS_LOGGER).log(Level.INFO, "DataSource Ended");
            setDataSourceEnded();

        }

        timeDelayMillis += tbs;

    }

    @Override
    public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
        super.setAcquisitionHeader(config);

        total_samples = config.getTotalSamples();

        tbs = 1000 / (float) config.getSelectedFrequency().getFrequency();
    }
    private boolean stopped = false;

    public void stopNow() {
        stopped = true;
        setDataSourceStoped();
    }
}
