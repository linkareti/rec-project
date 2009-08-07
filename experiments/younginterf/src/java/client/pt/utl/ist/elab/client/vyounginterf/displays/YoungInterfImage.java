/*
 * YoungInterfImage.java
 *
 * Created on 20 de Mar�o de 2005, 5:31
 */

package pt.utl.ist.elab.client.vyounginterf.displays;

/**
 *
 * @author   Emanuel Antunes
 */


import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;


public class YoungInterfImage extends JPanel implements ExpDataDisplay, ExpDataModelListener {
    
    public JLabel label = null;
    public ImageIcon icon = null;
    public JScrollPane scroll = null;
    
    /** Creates a new instance of YoungInterfImage */
    public YoungInterfImage() {
        
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(1200, 400));
        scroll = new JScrollPane();
        this.add(scroll);
    }
    
    public void dataModelEnded() {
    }
    
    public void dataModelError() {
    }
    
    public void dataModelStarted() {
    }
    
    public void dataModelStartedNoData() {
    }
    
    public void dataModelStoped() {
    }
    
    public void dataModelWaiting() {
    }
    
    public javax.swing.JComponent getDisplay() {
        return this;}
    
    public javax.swing.Icon getIcon() {
        return null;}
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;}
    
    public String getName() {
        return null;}
    
    public javax.swing.JToolBar getToolBar() {
        return null;}
    
    public void newSamples(com.linkare.rec.impl.client.experiment.NewExpDataEvent evt) {
        
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++){
            //sample, canal
            
            if(model.getValueAt(i, model.getChannelIndex("imageIcon")) != null){ 
                icon = (ImageIcon) ByteUtil.byteArrayToObject(model.getValueAt(i, model.getChannelIndex("imageIcon")).getValue().getByteArrayValue().getData());
                label = new JLabel(icon);
                scroll.setViewportView(label);
                repaint();
            }
        }
    }
    
    private ExpDataModel model=null;
    public void setExpDataModel(ExpDataModel model)
    { if(this.model!=null)
          this.model.removeExpDataModelListener(this);
      this.model=model;
      if(this.model!=null)
          this.model.addExpDataModelListener(this); }
    
    
}
