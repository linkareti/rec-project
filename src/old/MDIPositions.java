/*
 * MDIPositions.java
 *
 * Created on July 21, 2004, 2:11 PM
 */

package old;

/**
 *
 * @author André Neto - LEFT - IST
 */
public interface MDIPositions 
{
    public static final byte TOP = 0;
    public static final byte BOTTOM = 1;
    public static final byte LEFT = 2;
    public static final byte RIGHT = 3;
    public static final byte CENTER = 4; 
    
    public byte getPosition();
}
