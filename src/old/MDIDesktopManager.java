package old;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/**
 * class used to replace the standard DesktopManager for JDesktopPane.
 * Used to provide scrollbar functionality.
 */
public class MDIDesktopManager extends DefaultDesktopManager 
{
    private MDIDesktopPane desktop;

    public MDIDesktopManager(MDIDesktopPane desktop) 
    {
        this.desktop = desktop;
    }

    public void endResizingFrame(JComponent f) {
        super.endResizingFrame(f);
        resizeDesktop();
    }

    public void endDraggingFrame(JComponent f) {
        super.endDraggingFrame(f);
        resizeDesktop();
    }

    public void setNormalSize() 
    {
        JScrollPane scrollPane=getScrollPane();
        int x = 0;
        int y = 0;
        Insets scrollInsets = getScrollPaneInsets();

        if (scrollPane != null) {
            Dimension d = scrollPane.getVisibleRect().getSize();
            if (scrollPane.getBorder() != null) {
               d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right,
                         d.getHeight() - scrollInsets.top - scrollInsets.bottom);
            }

            d.setSize(d.getWidth() - 20, d.getHeight() - 20);
            desktop.setAllSize(x,y);
            scrollPane.invalidate();
            scrollPane.validate();
        }
    }

    private Insets getScrollPaneInsets() {
        JScrollPane scrollPane=getScrollPane();
        if (scrollPane==null) return new Insets(0,0,0,0);
        else return getScrollPane().getBorder().getBorderInsets(scrollPane);
    }

    private JScrollPane getScrollPane() {
        if (desktop.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport)desktop.getParent();
            if (viewPort.getParent() instanceof JScrollPane)
                return (JScrollPane)viewPort.getParent();
        }
        return null;
    }

    protected void resizeDesktop() 
    {
        int x = 0;
        int y = 0;
        JScrollPane scrollPane = getScrollPane();
        Insets scrollInsets = getScrollPaneInsets();

        MDIInternalFrame frame = null;
        boolean top = false;
        boolean bottom = false;
        boolean left = false;
        boolean right = false;
        boolean center = false;
        int w;
        int h;
        
        if (scrollPane != null) 
        {
            //draw the top frame                     
            frame = desktop.getMDIFrame(MDIInternalFrame.TOP);
            if(frame != null)
            {
                top = true;
                frame.setLocation(0, 0);
                frame.setSize(desktop.getSize());
            }
            
            //draw the left frame
            frame = desktop.getMDIFrame(MDIInternalFrame.LEFT);
            if(frame != null)
            {
                left = true;
                frame.setLocation(0, 0);
                frame.setSize(desktop.getSize());
            }
            
            scrollPane.invalidate();
            scrollPane.validate();
        }
    }
}