/*
 * InteractiveTiro.java
 *
 * Created on 16 de Fevereiro de 2005, 0:30
 */

package pt.utl.ist.elab.client.vtiro;

import org.opensourcephysics.displayejs.*;

/**
 *
 * @author  nomead
 */
public class InteractiveTiro extends Tiro implements InteractionListener {
    
    /** Creates a new instance of InteractiveBalancaTorsao */
    public InteractiveTiro() {
        super();
        buildInteractiveTiro();
    }
    
    public void buildInteractiveTiro(){
        target.setEnabled(InteractiveParticle.TARGET_POSITION, true);
        vel.setEnabled(InteractiveArrow.TARGET_SIZE, true);
        setListener(this);
    }
    
    public void interactionPerformed(InteractionEvent _event) {
        Object eventObj = _event.getSource();
        
        if (eventObj == vel){
            if (getTheta() < 0)
                setTheta(0);
            else if (getTheta() > Math.PI/2)
                setTheta(Math.PI/2);
            if (getVel() > 20)
                setVel(20);
            else if (getVel() < 1e-1)
                setVel(1e-1);
            updateLinha();
        }
        else if (eventObj == target){
            if (target.getY() > getPreferredYMax())
                target.setY(getPreferredYMax());
            else if (target.getY() < getPreferredYMin())
                target.setY(getPreferredYMin());
            if (target.getX() > getPreferredXMax())
                target.setX(getPreferredXMax());
            else if (target.getX() < getPreferredXMin()+1)
                target.setX(getPreferredXMin()+1);
            updateDistances();
            updateLinha();
        }
    }
    
    public static void main(String args[]){
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        test.getContentPane().add(new InteractiveTiro());
        test.pack();
        test.show();
    }
}
