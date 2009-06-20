/*
 * InteractiveSTDMAPAnima.java
 *
 * Created on 28 de Fevereiro de 2005, 5:28
 */

package pt.utl.ist.elab.virtual.client.stdmap;

import org.opensourcephysics.displayejs.*;
import pt.utl.ist.elab.virtual.guipack.*;

/**
 *
 * @author  Antonio Jose Rodrigues Figueiredo
 */
public class InteractiveSTDMAPAnima extends STDMAPAnima implements InteractionListener {
    
    private PopupMenu editPopMenu;
    
    /** Creates a new instance of InteractiveSTDMAPExp */
    public InteractiveSTDMAPAnima() {
        super();
        buildInteractiveSTDMAPAnima();
    }
    
    public InteractiveSTDMAPAnima(InteractiveMenu par) {
        super(par);
        buildInteractiveSTDMAPAnima();
    }
    
    private void buildInteractiveSTDMAPAnima(){
        bola.setEnabled(InteractiveElement.TARGET_ROTATION, true);
        bola.setRotationAxis(new javax.vecmath.Vector3d(1,0,0));
        bola.setRotationPoint(new javax.vecmath.Vector3d(0,0,0));
        
        force.setEnabled(InteractiveElement.TARGET_SIZE, true);
        vel.setEnabled(InteractiveElement.TARGET_SIZE, true);
        
        setListener(this);
        
        editPopMenu = new PopupMenu(par);
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.1"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.1"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.2"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.2"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.3"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.3"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.4"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.4"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.5"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.5"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.6"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.6"));
        editPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.title.7"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/stdmap/resources/ReCExpSTDMAP").getString("rec.exp.customizer.editMenu.tip.7"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void interactionPerformed(InteractionEvent _event) {
        Object eventObj = _event.getSource();
        
        if (eventObj instanceof  InteractiveSphere){
            fio.clear();
            fio.addPoint(0,0,0);
            fio.addPoint(0,bola.getY(),bola.getZ());
            force.setXYZ(0,bola.getY(),bola.getZ());
            vel.setXYZ(0,bola.getY(),bola.getZ());
            setThetaVecVel(getThetaVecVel());
        }
        else if (((InteractiveArrow) eventObj).getStyle().getEdgeColor() == java.awt.Color.GREEN){
            if (getThetaVecVel() > 8*Math.PI)
                setThetaVecVel(8*Math.PI);
            else if (getThetaVecVel() < -8*Math.PI)
                setThetaVecVel(-8*Math.PI);
            else
                setThetaVecVel(getThetaVecVel());
        }
        else {
            if (((InteractiveArrow) eventObj).getSizeZ() > 25)
                ((InteractiveArrow) eventObj).setSizeZ(25);
            else if (((InteractiveArrow) eventObj).getSizeZ() < -25)
                ((InteractiveArrow) eventObj).setSizeZ(-25);
            ((InteractiveArrow) eventObj).setSizeX(0);
            ((InteractiveArrow) eventObj).setSizeY(0);
        }
        updateGUI();
        
        java.awt.event.MouseEvent e = _event.getMouseEvent();
        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
            editPopMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
}
