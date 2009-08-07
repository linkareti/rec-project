/*
 * PositionChart.java
 *
 * Created on 20 de Mar�o de 2005, 15:21
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import pt.utl.ist.elab.virtual.guipack.graphs.*;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
/**
 *
 * @author  nomead
 */
public class PositionChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {
    
    /** Creates a new instance of PositionChart */
    public PositionChart(){
        super();
    }
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        boolean x = header.getSelectedHardwareParameterValue("x").trim().equals("1")?true:false;
        boolean y = header.getSelectedHardwareParameterValue("y").trim().equals("1")?true:false;
        boolean z = header.getSelectedHardwareParameterValue("z").trim().equals("1")?true:false;
        boolean posModulus = header.getSelectedHardwareParameterValue("posModulus").trim().equals("1")?true:false;
        
        if (x || y || z || posModulus){ //grafico activo
            setChannelDisplayX(17); //t
            int [] channels = getYChannels(x,y,z,posModulus,header.getSelectedHardwareParameterValue("posQuad").trim().equals("1")?true:false,false);
            if (channels != null){
                for (int i = 0; i < channels.length; i++){
                    channels[i]--; //acerto para o XML
                    System.out.println(" CH : "+channels[i]);
                }
                setChannelDisplayYArray(channels);
            }
            super.dataModelStartedNoData();
        }
        else {
            setLayout(new java.awt.GridBagLayout());
            java.awt.GridBagConstraints gridBagConstraints;
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight= 1;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            
            javax.swing.JLabel label = new javax.swing.JLabel(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.inactive"),javax.swing.JLabel.CENTER);
            removeAll();
            add(label, gridBagConstraints);
            updateUI();
        }
    }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        super.setExpDataModel(model);
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }
    
    private int[] getYChannels(boolean _x, boolean _y, boolean _z, boolean _rMod, boolean _rQuad, boolean _rAbs){
        int[] channels;
        
        int k = 0;
        if (_x)k++;
        if (_y)k++;
        if (_z)k++;
        if (_rMod)k++;
        channels = new int[k];
        
        if (!_rAbs && !_rQuad){
            int i = 0;
            if (_x)
                channels[i++] = Chart1.nameToChannels("t vs X")[1];
            if (_y)
                channels[i++] = Chart1.nameToChannels("t vs Y")[1];
            if (_z)
                channels[i++] = Chart1.nameToChannels("t vs Z")[1];
            if (_rMod)
                channels[i++] = Chart1.nameToChannels("t vs | r |")[1];
            return channels;
        }
        if (!_rAbs){
            int i = 0;
            if (_x)
                channels[i++] = Chart1.nameToChannels("t vs X^2")[1];
            if (_y)
                channels[i++] = Chart1.nameToChannels("t vs Y^2")[1];
            if (_z)
                channels[i++] = Chart1.nameToChannels("t vs Z^2")[1];
            if (_rMod)
                channels[i++] = Chart1.nameToChannels("t vs | r |^2")[1];
            return channels;
        }
        if (!_rQuad){
            int i = 0;
            if (_x)
                channels[i++] = Chart1.nameToChannels("t vs | X |")[1];
            if (_y)
                channels[i++] = Chart1.nameToChannels("t vs | Y |")[1];
            if (_z)
                channels[i++] = Chart1.nameToChannels("t vs | Z |")[1];
            if (_rMod)
                channels[i++] = Chart1.nameToChannels("t vs | r |")[1];
            return channels;
        }
        return null;
    }
}
