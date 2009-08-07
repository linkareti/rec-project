/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vstdmap.displays;

import pt.utl.ist.elab.client.vstdmap.STDMAPAnima;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 *
 * @author  Antonio Jose Rodrigues Figueiredo
 */
public class Animation extends STDMAPAnima implements ExpDataDisplay, ExpDataModelListener {
    
    /** Creates a new instance of Animation */
    public Animation() {
        super();
        // TESTE
        //setTheta(1);
        //setThetaVecVel(3);
        //setForce(5);
        //repaint();
        //new pt.utl.ist.elab.virtual.driver.stdmap.STDMAPDataProducer(null,(float) 1, -5, 1, 1, 5, 1000,10000).startAnima(this);
        
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
    }
    
    public void dataModelEnded() {
    }
    
    public void dataModelError() {
    }
    
    public void dataModelStarted() {
    }
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        if (Byte.parseByte(header.getSelectedHardwareParameterValue("simulType")) != 2)
            setVisible(false);
        else {
            float theta = Float.parseFloat(header.getSelectedHardwareParameterValue("theta"));
            float thetaDot = Float.parseFloat(header.getSelectedHardwareParameterValue("thetaDot"));
            float length = Float.parseFloat(header.getSelectedHardwareParameterValue("length"));
            float force = Float.parseFloat(header.getSelectedHardwareParameterValue("force"));
            int forceDt = (int) header.getSelectedFrequency().getFrequency();
            
            config(length*10, theta, thetaDot, 0, force, forceDt);
        }
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
            if (model.getValueAt(i,0) != null && model.getValueAt(i,1) != null)
                move(model.getValueAt(i,0).getValue().getFloatValue(), model.getValueAt(i,1).getValue().getFloatValue());
            else if (model.getValueAt(i,0) != null)
                setTheta(model.getValueAt(i,0).getValue().getFloatValue());
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
