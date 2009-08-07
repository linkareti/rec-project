/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vtiro.displays;

import javax.swing.*;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

import pt.utl.ist.elab.client.vtiro.Tiro;
/**
 *
 * @author  nomead
 */
public class Animation extends Tiro implements ExpDataDisplay, ExpDataModelListener {
    
    /** Creates a new instance of Animation */
    public Animation() {
        super();
    }
    
    protected void config(){
    }
    
    //TESTE
    public void start(){
        pt.utl.ist.elab.driver.vtiro.TiroDataProducer data = new pt.utl.ist.elab.driver.vtiro.TiroDataProducer(null,10,10,6.7,Math.PI/4,9.8);
        data.start(this);
    }
    
    public static void main(String args[]) {
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        Animation stdim = new Animation();
        test.getContentPane().add(stdim);
        test.pack();
        test.show();
        stdim.start();
    }
    
    public void dataModelEnded() {
    }
    
    public void dataModelError() {
    }
    
    public void dataModelStarted() {
    }
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        double w = Float.parseFloat(header.getSelectedHardwareParameterValue("w"));
        double h = Float.parseFloat(header.getSelectedHardwareParameterValue("h"));
        double v = Float.parseFloat(header.getSelectedHardwareParameterValue("v"));
        double theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
        
        config(w,h,v,theta);
    }
    
    public void dataModelStoped() {
    }
    
    public void dataModelWaiting() {
    }
    
    public javax.swing.JComponent getDisplay() {
        return this;
    }
    
    public javax.swing.Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/sensor16.gif"));
    }
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
    
    public javax.swing.JToolBar getToolBar() {
        return null;
    }
    
    public void newSamples(NewExpDataEvent evt) {
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++) {
            //sample, canal
            if (model.getValueAt(i,0) != null && model.getValueAt(i,1) != null && model.getValueAt(i,2) != null && model.getValueAt(i,3) != null && model.getValueAt(i,4) != null)
                move(model.getValueAt(i,0).getValue().getFloatValue(), model.getValueAt(i,1).getValue().getFloatValue(), model.getValueAt(i,2).getValue().getFloatValue(), model.getValueAt(i,3).getValue().getFloatValue(),model.getValueAt(i,4).getValue().getFloatValue());
            
        }
    }
    
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model) {
        if(this.model!=null)
            this.model.removeExpDataModelListener(this);
        this.model=model;
        if(this.model!=null)
            this.model.addExpDataModelListener(this);
    }
    
}
