/*
 * InteractiveBalancaTorsao.java
 *
 * Created on 2 de Dezembro de 2004, 6:30
 */

package pt.utl.ist.elab.virtual.client.cg;

import org.opensourcephysics.displayejs.*;
import java.awt.event.*;
import javax.swing.*;
import com.linkare.rec.impl.i18n.ReCResourceBundle;


/**
 *
 * @author  nomead
 */
public class InteractiveBalancaTorcao extends BalancaTorcao implements InteractionListener, ActionListener {
    
    private PopupMenu mmPopMenu;
    private Object eventObj;
    
    /** Creates a new instance of InteractiveBalancaTorsao */
    public InteractiveBalancaTorcao() {
        super();
        
        mM[0].setEnabled(true);
        mM[1].setEnabled(true);
        
        Group mms = new Group();
        mm[0].setGroup(mms);
        mm[1].setGroup(mms);
        
        mm[0].setEnabled(InteractiveSphere.TARGET_ROTATION, true);
        mm[0].setRotationAxis(new javax.vecmath.Vector3d(0,0,1));
        mm[0].setRotationPoint(new javax.vecmath.Vector3d(0,0,0));
        
        mm[1].setEnabled(InteractiveSphere.TARGET_ROTATION, true);
        mm[1].setRotationAxis(new javax.vecmath.Vector3d(0,0,1));
        mm[1].setRotationPoint(new javax.vecmath.Vector3d(0,0,0));
        
        regua.setEnabled(InteractiveSphere.TARGET_POSITION, true);
        
        setListener(this);
        mmPopMenu = new PopupMenu(this);
        mmPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.title.1","Edit"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.tip.1","Edit the selected object"));
    }
    
    private void checkLimits(){
        if (getL() > 1000)
            updateL(1000);
        else if (getL() < 200)
            updateL(200);
        else if (getS0() > 100)
            updateS0(100);
        else if (getS0() < 40)
            updateS0(40);
    }
    
    public void interactionPerformed(InteractionEvent _event) {
        checkLimits();
        
        eventObj = _event.getSource();
        
        mM[0].setX(-d);
        mM[1].setX(d);
        mM[0].setZ(0);
        mM[1].setZ(0);

        if (-mM[0].getY() != mM[1].getY() && eventObj.getClass().toString().indexOf("InteractiveSphere") != -1){
            InteractiveSphere esf = (InteractiveSphere) eventObj;
            mM[0].setY(Math.abs(esf.getY()));
            mM[1].setY(-Math.abs(esf.getY()));
        }
        
        regua.setXYZ(0, -Math.abs(regua.getY()),0);
        
        updateAngle();
        
        java.awt.event.MouseEvent e = _event.getMouseEvent();
        if (javax.swing.SwingUtilities.isRightMouseButton(e))
            mmPopMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    public double [] getMasses(){
        return new double[]{mm[0].getMass(), mm[1].getMass(), mM[0].getMass(), mM[1].getMass()};
    }
    
    public void setMasses(double [] m){
        mm[0].setMass(m[0]);
        mm[1].setMass(m[1]);
        mM[0].setMass(m[2]);
        mM[1].setMass(m[3]);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Edit")){
            if (eventObj.getClass().toString().indexOf("InteractivePlane") != -1)
                regua.setSizeX(PopupMenu.dialog(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.title.15","Edit Target Ruler (cm)"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.15","Ruler length"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.16","Accept the configuration"), (int) Math.round(regua.getSizeX()), new int[]{100,500,50,50}));
            else
                ((InteractiveSphereMass) eventObj).setMass(PopupMenu.dialog(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.title.17","Edit Mass (g)"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.17","Mass of the selected sphere"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.16","Accept the configuration"), (int) Math.round(((InteractiveSphereMass) eventObj).getMass()*1e3), new int[]{400,1500,15,200})*1e-3);
            repaint();
            updateGUI();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Perspective")){
            setDisplayMode(DISPLAY_PERSPECTIVE);
            setAlphaAndBeta(Math.PI/2, Math.PI/70+Math.PI);
            setPreferredMinMax(-40, 60, -1,1);
            view2D = 0;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Frontal View")){
            setDisplayMode(DISPLAY_PLANAR_XZ);
            setPreferredMinMax(-60,60,-60,60);
            setAlphaAndBeta(Math.PI/2, 0);
            view2D = 1;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Top View")){
            setDisplayMode(DISPLAY_PLANAR_XY);
            setPreferredMinMax(-60,60,-60,60);
            view2D = 2;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Lateral View")){
            setDisplayMode(DISPLAY_PLANAR_YZ);
            setPreferredMinMaxY(-60,60);
            view2D = 3;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Free 3D")){
            setDisplayMode(DISPLAY_3D);
            switch (view2D){
                case 1 : setAlphaAndBeta(-Math.PI/2, Math.PI);
                break;
                case 2 : setAlphaAndBeta(-Math.PI/2, Math.PI/2);
                break;
                case 3 : setAlphaAndBeta(0, Math.PI);
            }
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Measure")){
            measure();
            repaint();
        }
    }
}
