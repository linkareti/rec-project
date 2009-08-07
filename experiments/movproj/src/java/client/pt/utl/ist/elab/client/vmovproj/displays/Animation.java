/*
 * Animation.java
 *
 * Created on 1 de Mar�o de 2005, 5:52
 */

package pt.utl.ist.elab.client.vmovproj.displays;

import pt.utl.ist.elab.client.vmovproj.MovProj;

import org.opensourcephysics.displayejs.*;
import java.awt.event.*;

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
public class Animation extends MovProj implements ExpDataDisplay, ExpDataModelListener {
    
    private boolean isConnectedTrail = false, closeUp = false;
    private InteractiveTrace rasto;
    private String actionStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.displays.animation.actionStr");
    private String statusStr = "";
    
    /** Creates a new instance of Animation */
    public Animation() {
        super();
        rasto = new InteractiveTrace();
        rasto.getStyle().setEdgeColor(new java.awt.Color(.243f,.4f,.621f));
        viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.7"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.7"), true);
        viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.9"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.9"), true);
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.10"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.10"));
        viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.11"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.11"), false);
        isConnectedTrail = true;
        addDrawable(rasto);
    }
    
    //TESTE
    /*public void start(){
        float R = .05f;
        double r = (double)R*1000d/21d;
        config(0, 0, 0, 400, Math.PI/4, Math.PI/2, 400, Math.PI/2, Math.PI/2, 9.8, r);
        //new pt.utl.ist.elab.virtual.driver.movproj.MovProjDataProducer(null, 0, 0, 0, 55, (float)Math.PI/4, (float)Math.PI/2, 5555, (float)Math.PI/2, (float)Math.PI/2, .0213f, .046f, 0.5f, 0.7f, 1.2f, 4.1e-4f, 9.8f, 10, true, true, true, false, false, 100, 1000).start(this);
        new pt.utl.ist.elab.virtual.driver.movproj.MovProjDataProducer(null, 0, 0, 0, 400, (float)Math.PI/4, (float)Math.PI/2, 400, (float)Math.PI/2, (float)Math.PI/2, R, .046f, 0.5f, 0.7f, 1.2f, 4.1e-4f, 9.8f, 1, true, true, true, false, false, 500, 1000).start(this);
        
    }*/
    
    public void moves(double x, double y, double z, double vx, double vy, double vz, double ax, double ay, double az, double t){
        if (rasto.getSize() == 0 && isConnectedTrail)
            rasto.addPoint(getPosX(),getPosY()+bola.getSizeX()/2,getPosZ());
        
        move(x,y,z,vx,vy,vz,ax,ay,az);
        
        if (!closeUp){
            /*this.setPreferredMinMaxX(Math.min(Math.min(0,bola.getGroup().getX()),getPreferredXMin()),Math.max(Math.max(0,bola.getGroup().getX()),getPreferredXMax()));
            this.setPreferredMinMaxY(Math.min(Math.min(0,bola.getGroup().getY()),getPreferredYMin()),Math.max(Math.max(0,bola.getGroup().getY()),getPreferredYMax()));
            this.setPreferredMinMaxZ(Math.min(Math.min(0,bola.getGroup().getZ()),getPreferredZMin()),Math.max(Math.max(0,bola.getGroup().getZ()),getPreferredZMax()));
             */
            this.setPreferredMinMaxX(Math.min(0,bola.getGroup().getX()),Math.max(0,bola.getGroup().getX()));
            this.setPreferredMinMaxY(Math.min(0,bola.getGroup().getY()),Math.max(0,bola.getGroup().getY()));
            this.setPreferredMinMaxZ(Math.min(0,bola.getGroup().getZ()),Math.max(0,bola.getGroup().getZ()));
        }
        else
            setPreferredMinMax(x-30,x+30,y-30,y+30,z-30,z+30);
        
        if (isConnectedTrail)
            rasto.addPoint(getPosX(),getPosY()+bola.getSizeX()/2, getPosZ());
        
        statusStr = "X : "+pt.utl.ist.elab.client.virtual.guipack.GUtils.trimDecimalN(x,5)+" m | Y : "+pt.utl.ist.elab.client.virtual.guipack.GUtils.trimDecimalN(y,5)+" m | Z : "+pt.utl.ist.elab.client.virtual.guipack.GUtils.trimDecimalN(x,5)+" m | t : "+pt.utl.ist.elab.client.virtual.guipack.GUtils.trimDecimalN(t,5)+" t";
        repaint();
    }
    
    protected void paintEverything(java.awt.Graphics g) {
        if(dimensionSetter!=null){
            java.awt.Dimension interiorDimension=dimensionSetter.getInterior(this);
            if(interiorDimension!=null){
                squareAspect=false;
                leftGutter=rightGutter=Math.max(0,getWidth()- interiorDimension.width)/2;
                topGutter=bottomGutter=Math.max(0,getHeight()- interiorDimension.height)/2;
            }
        }
        java.util.ArrayList tempList= getDrawables();
        scale(tempList);
        setPixelScale();
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(java.awt.Color.black);
        paintDrawableList(g, tempList);
        
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(2, 5, g.getFontMetrics().stringWidth(statusStr)+3, g.getFontMetrics().getHeight());
        
        g.setColor(java.awt.Color.BLACK);
        g.drawString(statusStr, 5, 16);
        
        g.setColor(new java.awt.Color(.6f, .12f, .3f));
        g.drawString(actionStr, 5, getHeight()-10);
    }
    
    public static void main(String args[]) {
      /*  javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        Animation stdim = new Animation();
        test.getContentPane().add(stdim);
        test.pack();
        test.show();
        stdim.start();*/
    }
    
    public void dataModelEnded() {
    }
    
    public void dataModelError() {
    }
    
    public void dataModelStarted() {
    }
    
    public void dataModelStartedNoData() {
        HardwareAcquisitionConfig header = model.getAcquisitionConfig();
        
        float x = Float.parseFloat(header.getSelectedHardwareParameterValue("x"));
        float y = Float.parseFloat(header.getSelectedHardwareParameterValue("y"));
        float z = Float.parseFloat(header.getSelectedHardwareParameterValue("z"));
        
        float velMod = Float.parseFloat(header.getSelectedHardwareParameterValue("velMod"));
        float velTheta = Float.parseFloat(header.getSelectedHardwareParameterValue("velTheta"));
        float velPhi = Float.parseFloat(header.getSelectedHardwareParameterValue("velPhi"));
        
        float spinMod = Float.parseFloat(header.getSelectedHardwareParameterValue("spinMod"));
        float spinTheta = Float.parseFloat(header.getSelectedHardwareParameterValue("spinTheta"));
        float spinPhi = Float.parseFloat(header.getSelectedHardwareParameterValue("spinPhi"));
        
        double radius = (double)Float.parseFloat(header.getSelectedHardwareParameterValue("radius"))*1000d/21d;
        
        float g = Float.parseFloat(header.getSelectedHardwareParameterValue("g"));
        
        config(x,y,z,velMod/10d,velTheta,velPhi,spinMod/10d,spinTheta,spinPhi,g,radius);
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
            if (model.getValueAt(i,0) != null && model.getValueAt(i,1) != null && model.getValueAt(i,2) != null && model.getValueAt(i,3) != null && model.getValueAt(i,4) != null && model.getValueAt(i,5) != null && model.getValueAt(i,6) != null && model.getValueAt(i,7) != null && model.getValueAt(i,8) != null && model.getValueAt(i,9) != null)
                moves(model.getValueAt(i,0).getValue().getFloatValue(),model.getValueAt(i,1).getValue().getFloatValue(),model.getValueAt(i,2).getValue().getFloatValue(),model.getValueAt(i,3).getValue().getFloatValue(),model.getValueAt(i,4).getValue().getFloatValue(), model.getValueAt(i,5).getValue().getFloatValue(),model.getValueAt(i,7).getValue().getFloatValue(),model.getValueAt(i,8).getValue().getFloatValue(),model.getValueAt(i,9).getValue().getFloatValue(),model.getValueAt(i,6).getValue().getFloatValue());
            
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
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.1"))){
            setAlphaAndBeta(-Math.PI/2,Math.PI/2);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.2"))){
            if (getZoom() != 1){
                setZoom(1);
                repaint();
            }
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.3"))){
            if (getZoom() != .5){
                setZoom(.5);
                repaint();
            }
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.4"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_XY);
            setPan(0,0);
            //setZoom(1);
            if (!closeUp){
                this.setPreferredMinMaxX(Math.min(0,bola.getGroup().getX()),Math.max(0,bola.getGroup().getX()));
                this.setPreferredMinMaxY(Math.min(0,bola.getGroup().getY()),Math.max(0,bola.getGroup().getY()));
            }
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.15"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_XZ);
            setPan(0,0);
            //setZoom(1);
            if (!closeUp){
                this.setPreferredMinMaxX(Math.min(0,bola.getGroup().getX()),Math.max(0,bola.getGroup().getX()));
                this.setPreferredMinMaxZ(Math.min(0,bola.getGroup().getZ()),Math.max(0,bola.getGroup().getZ()));
            }
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.16"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_YZ);
            setPan(0,0);
            //setZoom(1);
            if (!closeUp){
                this.setPreferredMinMaxY(Math.min(0,bola.getGroup().getY()),Math.max(0,bola.getGroup().getY()));
                this.setPreferredMinMaxZ(Math.min(0,bola.getGroup().getZ()),Math.max(0,bola.getGroup().getZ()));
            }
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.6"))){
            this.setDisplayMode(this.DISPLAY_NO_PERSPECTIVE);
            setPan(0,0);
            //setZoom(1);
            if (!closeUp){
                this.setPreferredMinMaxX(Math.min(0,bola.getGroup().getX()),Math.max(0,bola.getGroup().getX()));
                this.setPreferredMinMaxY(Math.min(0,bola.getGroup().getY()),Math.max(0,bola.getGroup().getY()));
                this.setPreferredMinMaxZ(Math.min(0,bola.getGroup().getZ()),Math.max(0,bola.getGroup().getZ()));
            }
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.5")))
            snapshot();
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.7"))){
            vel.setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
            spin.setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
            acel.setVisible(((javax.swing.JMenuItem) e.getSource()).isSelected());
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.9"))){
            isConnectedTrail = ((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected();
            
            if (!((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected())
                removeDrawable(rasto);
            else
                addDrawable(rasto);
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.10")))
            rasto.clear();
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.11"))){
            closeUp = ((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected();
            if (closeUp){
                setPreferredMinMax(bola.getGroup().getX()-30,bola.getGroup().getX()+30,bola.getGroup().getY()-30,bola.getGroup().getY()+30,bola.getGroup().getZ()-30,bola.getGroup().getZ()+30);
                repaint();
            }
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
            actionStr = "";
            viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
}
