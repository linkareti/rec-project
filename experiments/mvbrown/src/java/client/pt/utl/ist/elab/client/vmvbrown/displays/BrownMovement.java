/*
 * BrownMovement.java
 *
 * Created on 26 de Mar�o de 2005, 17:39
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

/**
 *
 * @author  nomead
 */
public interface BrownMovement {
    
    public void moves(byte [] mv);
    
    public void config(int _numPart, byte _animaRadius, java.awt.Color _cor);
    
}
