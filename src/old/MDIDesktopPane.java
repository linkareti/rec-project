package old;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/**
 * An extension of WDesktopPane that supports often used MDI functionality. This
 * class also handles setting scroll bars for when windows move too far to the left or
 * bottom, providing the MDIDesktopPane is in a ScrollPane.
 */
public class MDIDesktopPane extends JDesktopPane 
{
    private static int FRAME_OFFSET=20;
    private MDIDesktopManager manager;
    private java.util.Vector vecFrames = null;

    public MDIDesktopPane() 
    {
        manager=new MDIDesktopManager(this);
        vecFrames = new java.util.Vector(5);
        setDesktopManager(manager);
        setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    public void setBounds(int x, int y, int w, int h) 
    {
        super.setBounds(x,y,w,h);
        checkDesktopSize();
    }

    public Component add(MDIInternalFrame frame)
    {
        Component retval=super.add(frame);
        addMDIFrame(frame);
        
        if(frame.getPosition() == MDIInternalFrame.TOP)
        {
            frame.setLocation(0, 0);
            frame.setSize(getWidth(), frame.getHeight());
        }
        else if(frame.getPosition() == MDIInternalFrame.BOTTOM)
        {
            frame.setLocation(0, getHeight() - frame.getHeight());
            frame.setSize(getWidth(), frame.getHeight());
        }
        else if(frame.getPosition() == MDIInternalFrame.LEFT)
        {
            frame.setLocation(0, 0);
            frame.setSize(frame.getWidth(), getHeight());
        }
        else if(frame.getPosition() == MDIInternalFrame.RIGHT)
        {
            frame.setLocation(getWidth() - frame.getWidth(), 0);
            frame.setSize(getWidth(), frame.getHeight());
        }
        else if(frame.getPosition() == MDIInternalFrame.CENTER)
        {
            frame.setLocation(0, 0);
            frame.setSize(getWidth(), getHeight());
        }
        
        moveToFront(frame);
        frame.setVisible(true);
        try
        {
            frame.setSelected(true);
        }
        catch (PropertyVetoException e) 
        {
            frame.toBack();
        }
        return retval;
    }

    public void remove(Component c) 
    {
        super.remove(c);
        checkDesktopSize();
    }

    /**
     * Sets all component size properties ( maximum, minimum, preferred)
     * to the given dimension.
     */
    public void setAllSize(Dimension d)
    {
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);
    }

    /**
     * Sets all component size properties ( maximum, minimum, preferred)
     * to the given width and height.
     */
    public void setAllSize(int width, int height)
    {
        setAllSize(new Dimension(width,height));
    }

    private void checkDesktopSize() 
    {
        if (getParent()!=null&&isVisible()) 
            manager.resizeDesktop();
    }
    
    private void addMDIFrame(MDIInternalFrame frame)
    {
        vecFrames.add(frame);
    }
    
    public MDIInternalFrame[] getAllMDIFrames()
    {
        return (MDIInternalFrame[])vecFrames.toArray();
    }
    
    public MDIInternalFrame getMDIFrame(byte position)
    {
        MDIInternalFrame[] frames = getAllMDIFrames();
        for(int i = 0; i < frames.length; i++)
        {
            if(frames[i].getPosition() == position)
            {
                return frames[i];
            }
        }
        return null;
    }
}