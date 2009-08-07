/*
 * Bounded.java
 *
 * Created on 29 de Mar�o de 2005, 19:29
 */

package pt.utl.ist.elab.virtual.client.quantum;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;
/**
 *
 * @author  nomead
 */
public interface Bounded extends Interactive {
    
    public Rectangle2D getBounds(double x);
    public boolean intersect(Rectangle2D rect);
    public void mouseOut(DrawingPanel panel);
    public void mouseOver(DrawingPanel panel);
    public Color getPaintColor();
    public Color getHighLightColor();
}
