/*
 * Painel.java
 *
 * Created on 28 de Março de 2005, 14:21
 */

package pt.utl.ist.elab.virtual.client.cargas3d.displays;
import org.opensourcephysics.displayejs.DrawingPanel3D;
/**
 *
 * @author n0dP2
 */
public class Painel extends DrawingPanel3D{
    
    /** Creates a new instance of Painel */
    public Painel() {
            setPreferredSize(new java.awt.Dimension(500,500));
            setPreferredMinMax(0,10,0,10,0,10);
            setBackground(java.awt.Color.white);
            setUseColorDepth(false);
            setDecorationType(DECORATION_AXES);
            setDisplayMode(DISPLAY_NO_PERSPECTIVE);
            setShowCoordinates(false);
            showAllTrackers(false);
            setAlpha(Math.PI/7);
            setBeta(Math.PI/8);
            setEnabledDrag(false);
    }
    
}
