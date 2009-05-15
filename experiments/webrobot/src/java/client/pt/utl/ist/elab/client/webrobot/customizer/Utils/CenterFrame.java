/*
 * CenterFrame.java
 *
 * Created on 28 de Janeiro de 2003, 11:47
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class CenterFrame {
    
    /** Creates a new instance of CenterFrame */
    public CenterFrame(javax.swing.JFrame child) 
    {
            java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
            java.awt.Dimension dimensionScreen = toolkit.getScreenSize();
            child.setLocation(dimensionScreen.getSize().width/2-child.getSize().width/2,
                                dimensionScreen.getSize().height/2-child.getSize().height/2);
    }
    
    public CenterFrame(javax.swing.JFrame parent, javax.swing.JFrame child) 
    {
                child.setLocation(parent.getLocation().x+parent.getSize().width/2-child.getSize().width/2,
                                parent.getLocation().y+parent.getSize().height/2-child.getSize().height/2);
    }

    public CenterFrame(javax.swing.JFrame parent, javax.swing.JDialog child) 
    {
                child.setLocation(parent.getLocation().x+parent.getSize().width/2-child.getSize().width/2,
                                parent.getLocation().y+parent.getSize().height/2-child.getSize().height/2);
    }

    public CenterFrame(javax.swing.JDialog parent, javax.swing.JDialog child) 
    {
                child.setLocation(parent.getLocation().x+parent.getSize().width/2-child.getSize().width/2,
                                parent.getLocation().y+parent.getSize().height/2-child.getSize().height/2);
    }
    
    public CenterFrame(javax.swing.JPanel parent, javax.swing.JDialog child) 
    {
                child.setLocation(parent.getLocation().x+parent.getSize().width/2-child.getSize().width/2,
                                parent.getLocation().y+parent.getSize().height/2-child.getSize().height/2);
    }    
}
