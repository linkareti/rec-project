/* 
 * FlatScrollBarUI.java created on 2009/04/16
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Scrollbar;

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
 * @author João Florindo
 */
public class FlatScrollBarUI extends MetalScrollBarUI{
	
	//FIXME: Corrigir esta forma de obter as cores. Tem de ser por intermédio de propriedades
	//ENABLED COLORS
	public static final Color COLOR_BORDER = new Color(0x848485);	
	public static final Color COLOR_THUMB_BACKGROUND = new Color(0xDAE1DF);
	public static final Color COLOR_BACKGROUND = new Color(0xE4EEED);
	//OTHER COLORS
	public static final Color COLOR_DIS_BACKGROUND = new Color(0xE4EEED);
	public static final Color COLOR_PRESS_BACKGROUND = new Color(0xE4EEED);
	//INSETS
//	public static final Integer INSETS = 1;
	

    protected FlatScrollButton increaseButton;
    protected FlatScrollButton decreaseButton;
	
	
    public static ComponentUI createUI( JComponent c )
    {
        return new FlatScrollBarUI();
    }
	
    protected void paintTrack( Graphics g, JComponent c, Rectangle trackBounds ){
        
      g.translate( trackBounds.x, trackBounds.y );  
//        scrollbar.setBackground(scrollbar.getParent().getBackground());

		if ( scrollbar.getOrientation() == JScrollBar.VERTICAL )
		{
//		    g.setColor( COLOR_BACKGROUND);
//    	    g.fillRect( 1, INSETS, trackBounds.width - 1, trackBounds.height - INSETS*2);
			
			//FIXME: COMO É ?? CORES ENABLE/DISABLE
		    if ( c.isEnabled() ) {
		        g.setColor( COLOR_BORDER );
		      //FIXME: COMO É ?? DISTANCIA ENTRE BOTÃO E SLIDE
		        g.drawLine( 0, 0, 0, trackBounds.height );	//LEFT
				g.drawLine( trackBounds.width - 1, 0, trackBounds.width - 1, trackBounds.height ); //RIGHT
				
//				g.drawLine( 1, trackBounds.height , trackBounds.width - 1, trackBounds.height );//BOTTOM
//				g.drawLine( 1, 1, trackBounds.width - 1, 1 );	//TOP
		
		    } else {
//			    MetalUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height );
		    }

		}
		else  // HORIZONTAL
		{
//    	    g.setColor( COLOR_BACKGROUND);
//    	    g.fillRect( INSETS + 1, 1, trackBounds.width - INSETS*2, trackBounds.height - 2);
    	    
		    if ( c.isEnabled() ) {
		        g.setColor( COLOR_BORDER );
				g.drawLine( 0, 0, trackBounds.width , 0 );  // TOP
				g.drawLine( 0, trackBounds.height - 1, trackBounds.width , trackBounds.height - 1 ); // BOTTOM
				
//				g.drawLine( 0, 1, 0, trackBounds.height - 1 ); // LEFT
//				g.drawLine( trackBounds.width , 1, trackBounds.width , trackBounds.height - 1 ); // RIGHT
	
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
    	    g.setColor( COLOR_THUMB_BACKGROUND );
    	    g.fillRect( 2, 1, thumbBounds.width - 4, thumbBounds.height - 2 );
    	    
    	    g.setColor( COLOR_BORDER );
    	    g.drawLine( 1, 0, thumbBounds.width - 2, 0 );//TOP
    	    g.drawLine( 1, 1, 1, thumbBounds.height - 2 );//LEFT
    	    g.drawLine( 1, thumbBounds.height - 1, thumbBounds.width - 2, thumbBounds.height - 1 );//BOTTOM
    	    g.drawLine( thumbBounds.width - 2, 1, thumbBounds.width - 2, thumbBounds.height - 2 );//RIGHT
    	}
    	else  // HORIZONTAL
    	{
    	    g.setColor( COLOR_THUMB_BACKGROUND);
    	    g.fillRect( 2, 2, thumbBounds.width - 2, thumbBounds.height - 4);
    	    
    	    g.setColor( COLOR_BORDER );
    	    g.drawLine( 1, 1, thumbBounds.width -1, 1 );//TOP
    	    g.drawLine( 0, 1, 0, thumbBounds.height - 2 );//LEFT
    	    g.drawLine( 1, thumbBounds.height - 2, thumbBounds.width -1, thumbBounds.height - 2 );//BOTTOM
    	    g.drawLine( thumbBounds.width - 1, 1, thumbBounds.width - 1, thumbBounds.height - 2 );//RIGHT
    	}
            
    	g.translate( -thumbBounds.x, -thumbBounds.y );
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
