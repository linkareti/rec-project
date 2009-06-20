/*
 * InteractiveMovProj.java
 *
 * Created on 28 de Fevereiro de 2005, 5:28
 */

package pt.utl.ist.elab.virtual.client.movproj;

import org.opensourcephysics.displayejs.*;
import pt.utl.ist.elab.virtual.guipack.*;

/**
 *
 * @author  nomead
 */
public class InteractiveMovProj extends MovProj implements InteractionListener {
    
    private PopupMenu editPopMenu;
    
    private double mass = 46; //Kg/1000
    
    /** Creates a new instance of InteractiveMovProjExp */
    public InteractiveMovProj() {
        super();
        buildInteractiveMovProj();
    }
    
    public InteractiveMovProj(InteractiveMenu par) {
        super(par);
        buildInteractiveMovProj();
    }
    
    private void buildInteractiveMovProj(){
        
        bola.setEnabled(InteractiveElement.TARGET_POSITION, true);
        
        vel.setEnabled(InteractiveElement.TARGET_SIZE, true);
        spin.setEnabled(InteractiveElement.TARGET_SIZE, true);
        acel.setEnabled(InteractiveElement.TARGET_SIZE, true);
        
        setListener(this);
        
        editPopMenu = new PopupMenu(par);
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.editMenu.title.1"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.editMenu.tip.1"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.editMenu.title.2"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.editMenu.tip.2"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void setMass(double m){
        this.mass = m;
    }
    
    public double getMass(){
        return mass;
    }
    
    public void interactionPerformed(InteractionEvent _event) {
        
        Object eventObj = _event.getSource();
        
        if (eventObj instanceof InteractiveArrow && ((InteractiveArrow) eventObj) == acel){
            if (acel.getSizeX() != 0)
                acel.setSizeX(0);
            if (acel.getSizeZ() != 0)
                acel.setSizeZ(0);
            if (acel.getSizeY() > 0)
                acel.setSizeY(0);
            else if (acel.getSizeY() < -50)
                acel.setSizeY(-50);
        }
        else if (eventObj instanceof InteractiveArrow && ((InteractiveArrow) eventObj) == spin){
            if (getSpin() > 40)
                setSpin(40);
        }
        else if (eventObj instanceof InteractiveArrow && ((InteractiveArrow) eventObj) == vel){
            if (getVel() > 40)
                setVel(40);
        }
        else if (eventObj instanceof InteractiveSphere && ((InteractiveSphere) eventObj) == bola){
            if (bola.getGroup().getY() > 50+bola.getSizeX()/2)
                bola.getGroup().setY(50+bola.getSizeX()/2);
            if (bola.getGroup().getX() < -25)
                bola.getGroup().setX(-25);
            else if (bola.getGroup().getX() > 25)
                bola.getGroup().setX(25);
            if (bola.getGroup().getZ() < -25)
                bola.getGroup().setZ(-25);
            else if (bola.getGroup().getZ() > 25)
                bola.getGroup().setZ(25);
            if (bola.getGroup().getY() < bola.getSizeX()/2)
                bola.getGroup().setY(bola.getSizeX()/2);
        }

        updateGUI();
        
        java.awt.event.MouseEvent e = _event.getMouseEvent();
        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
            editPopMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
