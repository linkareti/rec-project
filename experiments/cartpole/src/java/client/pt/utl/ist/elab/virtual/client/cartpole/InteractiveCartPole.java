/*
 * InteractiveCartPole.java
 *
 * Created on 16 de Fevereiro de 2005, 0:30
 */

package pt.utl.ist.elab.virtual.client.cartpole;

import pt.utl.ist.elab.virtual.guipack.*;
import org.opensourcephysics.displayejs.*;
import java.awt.event.*;

/**
 *
 * @author  nomead
 */
public class InteractiveCartPole extends CartPole implements InteractionListener {
    
    private PopupMenu cartPopMenu;
    private PopupMenu polePopMenu;
    private Object eventObj;
    
    /** Creates a new instance of InteractiveBalancaTorsao */
    public InteractiveCartPole() {
        super();
        buildInteractiveCartPole();
    }
    
    public InteractiveCartPole(InteractiveMenu par) {
        super(par);
        buildInteractiveCartPole();
    }
    
    public void buildInteractiveCartPole(){
        pole.setEnabled(InteractiveSphere.TARGET_ROTATION, true);
        pole.setRotationAxis(new javax.vecmath.Vector3d(0,0,1));
        pole.setRotationPoint(new javax.vecmath.Vector3d(0,0,0));
        
        cart.setEnabled(InteractiveSphere.TARGET_POSITION, true);
        
        actArrow.setEnabled(InteractiveSphere.TARGET_SIZE, true);
        
        genVel[0].setEnabled(InteractiveSphere.TARGET_SIZE, true);
        genVel[1].setEnabled(InteractiveSphere.TARGET_SIZE, true);
        
        limits[0].setEnabled(InteractiveSphere.TARGET_POSITION, true);
        limits[1].setEnabled(InteractiveSphere.TARGET_POSITION, true);
        
        setListener(this);
        cartPopMenu = new PopupMenu(par);
        cartPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.1"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.1"));
        cartPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.2"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.2"));
        cartPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.3"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.3"));
        cartPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.4"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.4"));
        
        polePopMenu = new PopupMenu(par);
        polePopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.5"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.5"));
        polePopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.6"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.6"));
        polePopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.7"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.7"));
        polePopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.title.8"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.editMenu.tip.8"));
    }
    
    public void interactionPerformed(InteractionEvent _event) {
        checkLimits();
        
        eventObj = _event.getSource();
        
        if (eventObj.getClass().toString().indexOf("InteractiveArrow") != -1){
            if (((InteractiveArrow) eventObj).getArrowType() == InteractiveArrow.SEGMENT) {
                
                if (limits[0].getX() > 60.1 || limits[1].getX() < -60.1){
                    limits[0].setX(60.1);
                    limits[1].setX(-60.1);
                }
                else if (limits[1].getX() > 60.1 || limits[0].getX() < -60.1){
                    limits[1].setX(60.1);
                    limits[0].setX(-60.1);
                }
                
                if (limits[0].getY() != -15)
                    limits[0].setY(-15);
                else if (limits[1].getY() != -15)
                    limits[1].setY(-15);
                
                double xmax = ((InteractiveArrow) eventObj).getX();
                
                if (xmax == limits[0].getX())
                    limits[1].setX(-xmax);
                else
                    limits[0].setX(-xmax);
            }
            else if (((InteractiveArrow) eventObj).getStyle().getEdgeColor() == java.awt.Color.GREEN)
                setThetaVecVel(getThetaVecVel());
            else if (((InteractiveArrow) eventObj).getSizeY() != 0)
                ((InteractiveArrow) eventObj).setSizeY(0);
        }
        else if (eventObj.getClass().toString().indexOf("InteractiveSphere") != -1){
            setThetaColor(getTheta());
            genVel[1].setXY(pole.getX(), pole.getY());
            setThetaVecVel(getThetaVecVel());
            vara.clear();
            vara.addPoint(cart.getX(), cart.getY());
            vara.addPoint(pole.getX(), pole.getY());
        }
        
        updateGUI();
        
        java.awt.event.MouseEvent e = _event.getMouseEvent();
        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
            if (eventObj.getClass().toString().indexOf("InteractiveBox") != -1 || (eventObj.getClass().toString().indexOf("InteractiveArrow") != -1 && (((InteractiveArrow) eventObj).getStyle().getEdgeColor() == java.awt.Color.RED || ((InteractiveArrow) eventObj).getStyle().getEdgeColor() == java.awt.Color.ORANGE)))
                cartPopMenu.show(e.getComponent(), e.getX(), e.getY());
            else if (eventObj.getClass().toString().indexOf("InteractiveSphere") != -1 || (eventObj.getClass().toString().indexOf("InteractiveArrow") != -1 && ((InteractiveArrow) eventObj).getArrowType() != InteractiveArrow.SEGMENT))
                polePopMenu.show(e.getComponent(), e.getX(), e.getY());
        }
        
    }
    
    public static void main(String args[]){
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        test.getContentPane().add(new InteractiveCartPole());
        test.pack();
        test.show();
    }
}
