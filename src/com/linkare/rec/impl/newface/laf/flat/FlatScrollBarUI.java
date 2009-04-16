/* 
 * FlatScrollBarUI.java created on 2009/04/16
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.plaf.metal.MetalScrollButton;

import org.jfree.util.Log;

import com.linkare.rec.impl.newface.laf.flat.elabtheme.FlatScrollButton;

/**
 * 
 * @author JOE
 */
public class FlatScrollBarUI extends MetalScrollBarUI{
	
	//FIXME: Corrigir esta forma de obter as cores. Tem de ser por intermédio de propriedades
	//ENABLED COLORS
	public static final Color COLOR_BORDER = new Color(0x848485);	
	public static final Color COLOR_BACKGROUND = new Color(0xDCE6F2);
	//OTHER COLORS
	public static final Color COLOR_DIS_BACKGROUND = new Color(0xFFFFFF);
	public static final Color COLOR_PRESS_BACKGROUND = new Color(0xFFFFFF);
	//INSETS
	public static final Integer INSETS = 10;


    protected FlatScrollButton increaseButton;
    protected FlatScrollButton decreaseButton;
	
	
    public static ComponentUI createUI( JComponent c )
    {
        return new FlatScrollBarUI();
    }
	
    protected void paintTrack( Graphics g, JComponent c, Rectangle trackBounds ){
        
        g.translate( trackBounds.x, trackBounds.y );

		if ( scrollbar.getOrientation() == JScrollBar.VERTICAL )
		{
			//TOP INSETS
		    g.setColor( COLOR_DIS_BACKGROUND );
			for(int i=0;i < INSETS;i++){
				g.drawLine( 0, i, trackBounds.width - 1, i );	// INSETS	
			}
			
			//BOTTOM INSETS
			for(int i=trackBounds.height - INSETS;i < trackBounds.height;i++){
				g.drawLine( 0, i, trackBounds.width - 1, i);	// INSETS	
			}
			
			//FIXME: COMO É ?? CORES ENABLE/DISABLE
		    if ( c.isEnabled() ) {
		        g.setColor( COLOR_BORDER );
		      //FIXME: COMO É ?? DISTANCIA ENTRE BOTÃO E SLIDE
		        g.drawLine( 0, INSETS, 0, trackBounds.height - INSETS );	//LEFT
				g.drawLine( trackBounds.width - 1, INSETS, trackBounds.width - 1, trackBounds.height - INSETS ); //RIGHT
				g.drawLine( 1, trackBounds.height - INSETS, trackBounds.width - 1, trackBounds.height - INSETS);//BOTTOM
				g.drawLine( 1, INSETS, trackBounds.width - 1, INSETS );	//TOP
		
		    } else {
//			    MetalUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height );
		    }

		}
		else  // HORIZONTAL
		{
			//LEFT INSETS
		    g.setColor( COLOR_DIS_BACKGROUND );
			for(int i=0;i < INSETS;i++){
				g.drawLine( i, 0, i, trackBounds.height - 1 );	// INSETS	
			}
			
			//RIGHT INSETS
			for(int i=trackBounds.width - INSETS;i < trackBounds.width;i++){
				g.drawLine( i, 0, i, trackBounds.height - 1 );	// INSETS	
			}
			
		    if ( c.isEnabled() ) {
		        g.setColor( COLOR_BORDER );
				g.drawLine( INSETS, 0, trackBounds.width - INSETS, 0 );  // TOP
				g.drawLine( INSETS, 1, INSETS, trackBounds.height - 1 ); // LEFT
				g.drawLine( INSETS, trackBounds.height - 1, trackBounds.width - INSETS, trackBounds.height - 1 ); // BOTTOM
				g.drawLine( trackBounds.width - INSETS, 1, trackBounds.width - INSETS, trackBounds.height - 1 ); // RIGHT
	
		    } else {
//		        MetalUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height );
		    }
		}

        g.translate( -trackBounds.x, -trackBounds.y );
    }
   
    protected void paintThumb( Graphics g, JComponent c, Rectangle thumbBounds )
    {
        if (!c.isEnabled()) {
    	    return;
    	}

        g.translate( thumbBounds.x, thumbBounds.y );

    	if ( scrollbar.getOrientation() == JScrollBar.VERTICAL )
    	{
    	    if ( !isFreeStanding ) {
                thumbBounds.width += 2;
    	    }
    	    g.setColor( COLOR_BACKGROUND );
    	    g.fillRect( 2, 1, thumbBounds.width - 4, thumbBounds.height - 2 );
    	    
    	    g.setColor( COLOR_BORDER );
    	    g.drawLine( 1, 0, thumbBounds.width - 2, 0 );//TOP
    	    g.drawLine( 1, 1, 1, thumbBounds.height - 2 );//LEFT
    	    g.drawLine( 1, thumbBounds.height - 2, thumbBounds.width - 2, thumbBounds.height - 2 );//BOTTOM
    	    g.drawLine( thumbBounds.width - 2, 1, thumbBounds.width - 2, thumbBounds.height - 2 );//RIGHT
    	    
    	    if ( !isFreeStanding ) {
                thumbBounds.width -= 2;
    	    }

    	}
    	else  // HORIZONTAL
    	{
    	    if ( !isFreeStanding ) {
    	        thumbBounds.height += 2;
    	    }

    	    g.setColor( COLOR_BACKGROUND );
    	    g.fillRect( 0, 0, thumbBounds.width - 1, thumbBounds.height - 2 );
    	    
    	    if ( !isFreeStanding ) {
    	        thumbBounds.height -= 2;
    	    }
    	}
            
    	g.translate( -thumbBounds.x, -thumbBounds.y );
    }
    
    
	/**
	 * This is overridden only to increase the invalid area.  This
	 * ensures that the "Shadow" below the thumb is invalidated
	 */
    protected void setThumbBounds(int x, int y, int width, int height)
	{
		/* If the thumbs bounds haven't changed, we're done.*/
		if ((thumbRect.x == x) && 
		    (thumbRect.y == y) && 
		    (thumbRect.width == width) && 
		    (thumbRect.height == height)) {
		    return;
		}
	
		/* Update thumbRect, and repaint the union of x,y,w,h and 
		 * the old thumbRect.
		 */
		int minX = Math.min(x, thumbRect.x);
		int minY = Math.min(y, thumbRect.y);
		int maxX = Math.max(x + width, thumbRect.x + thumbRect.width);
		int maxY = Math.max(y + height, thumbRect.y + thumbRect.height);
	
		thumbRect.setBounds(x, y, width, height);
		scrollbar.repaint(minX, minY, (maxX - minX)+1, (maxY - minY)+1);
	}
    
    
    public Dimension getPreferredSize( JComponent c )
    {
        if ( scrollbar.getOrientation() == JScrollBar.VERTICAL )
        {
        	return new Dimension( scrollBarWidth, scrollBarWidth * 3 + 10 );
        }
        else  // Horizontal
        {
            return new Dimension( scrollBarWidth * 3 + 10, scrollBarWidth );
        }

    }
    
	/** Returns the view that represents the decrease view. 
	*/
	protected JButton createDecreaseButton( int orientation )
	{
		decreaseButton = new FlatScrollButton( orientation, scrollBarWidth, isFreeStanding );
		return decreaseButton;
	}
	
	/** Returns the view that represents the increase view. */
	protected JButton createIncreaseButton( int orientation )
	{
		increaseButton =  new FlatScrollButton( orientation, scrollBarWidth, isFreeStanding );
		return increaseButton;
	}
}
