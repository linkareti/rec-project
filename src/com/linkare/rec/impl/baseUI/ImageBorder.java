/*
 * ImageBorder.java
 *
 * Created on August 18, 2004, 11:04 AM
 */

package com.linkare.rec.impl.baseUI;

/**
 *
 * @author  andre
 *
 * Use this class to set images in the background of any container...
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.border.*;
import com.linkare.rec.impl.i18n.*;


public class ImageBorder implements Border
{
    private Image image;
    
    /** Creates a new instance of ImageBorder */
    public ImageBorder(Image image, boolean fill) 
    {
        this.image = image;
        this.fill = fill;
    }
        

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
	if(image == null)
	{	    
	    return;
	}
        if(!fill)
        {
            int x0 = x + (width-image.getWidth(null))/2;
            int y0 = y + (height-image.getHeight(null))/2;
            g.drawImage(image, x0, y0, null);
        }
        else
        {
            g.drawImage(image, 0, 0, width, height,  null);
        }
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
    
    private boolean fill = true;
    public void setFill(boolean fill)
    {
        this.fill = fill;
    }
}

