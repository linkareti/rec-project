/*
 * VelocityChart.java
 *
 * Created on 20 de Março de 2005, 15:21
 */

package pt.utl.ist.elab.virtual.client.movproj.displays;

import pt.utl.ist.elab.virtual.guipack.graphs.*;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
/**
 *
 * @author  nomead
 */
public class VelocityChart extends MultSeriesXYExperimentGraphExtended implements GraphSamplesFunction {
    
    private boolean isAbs;
    
    /** Creates a new instance of PositionChart */
    public VelocityChart() {
        super();
    }
    
    public double getValueX(int ch, double val){
        return val;
    }
    
    public double getValueY(int ch, double val){
        if (isAbs)
            return Math.abs(val);
        return val;
    }
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        setChannelDisplayX(6);
        if (header.getSelectedHardwareParameterValue("velModulus").trim().equals("1")?true:false)
            setChannelDisplayYArray(new int[]{3,4,5,11});
        else
            setChannelDisplayYArray(new int[]{3,4,5});

        isAbs = header.getSelectedHardwareParameterValue("velAbs").trim().equals("1")?true:false;
        super.dataModelStartedNoData();
    }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) 
    {
        super.setExpDataModel(model);
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
}
