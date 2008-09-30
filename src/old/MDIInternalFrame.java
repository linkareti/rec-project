/*
 * MDIInternalFrame.java
 *
 * Created on July 21, 2004, 2:42 PM
 */

package old;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class MDIInternalFrame extends javax.swing.JInternalFrame implements MDIPositions 
{
    private byte position = 0;
    
    /** Creates a new instance of MDIInternalFrame */
    public MDIInternalFrame(byte position) 
    {
        this.position = position;
    }
    
    public byte getPosition() 
    {
        return position;
    }    
}
