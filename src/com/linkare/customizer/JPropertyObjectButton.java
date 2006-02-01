/*
 * JIndexedPropertyObjectButton.java
 *
 * Created on 14 de Dezembro de 2003, 20:23
 */

package com.linkare.customizer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author  jp
 */
public class JPropertyObjectButton extends JButton
{
    PropertyObject value=null;
    
    private boolean isSelected=false;
    private boolean hasFocus=false;
    
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    
    // We need a place to store the color the JButton should be returned
    // to after its foreground and background colors have been set
    // to the selection background color.
    // These ivars will be made protected when their names are finalized.
    private Color unselectedForeground;
    private Color unselectedBackground;
    private JTable table;
    
    
    /** Creates a new instance of JIndexedPropertyObjectButton */
    public JPropertyObjectButton(PropertyObject value,boolean isSelected,boolean hasFocus,JTable table)
    {
	this.table=table;
	this.isSelected=isSelected;
	this.hasFocus=hasFocus;
	this.value=value;
	setHorizontalAlignment(LEFT);
	setMargin(new java.awt.Insets(0, 0, 0, 0));
	setOpaque(true);
	setBorder(noFocusBorder);
    }
    
    
        /**
     * Overrides <code>JComponent.setForeground</code> to assign
     * the unselected-foreground color to the specified color.
     * 
     * @param c set the foreground color to this value
     */
    public void setForeground(Color c) {
        super.setForeground(c); 
        unselectedForeground = c; 
    }
    
    /**
     * Overrides <code>JComponent.setBackground</code> to assign
     * the unselected-background color to the specified color.
     *
     * @param c set the background color to this value
     */
    public void setBackground(Color c) {
        super.setBackground(c); 
        unselectedBackground = c; 
    }

    /**
     * Notification from the <code>UIManager</code> that the look and feel
     * [L&F] has changed.
     * Replaces the current UI object with the latest version from the 
     * <code>UIManager</code>.
     *
     * @see JComponent#updateUI
     */
    public void updateUI() {
        super.updateUI(); 
	setForeground(null);
	setBackground(null);
    }
    
    protected void paintComponent(Graphics g)
    {
	if (isSelected)
	{
	    super.setForeground(table.getSelectionForeground());
	    super.setBackground(table.getSelectionBackground());
	}
	else
	{
	    super.setForeground((unselectedForeground != null) ? unselectedForeground
	    : table.getForeground());
	    super.setBackground((unselectedBackground != null) ? unselectedBackground
	    : table.getBackground());
	}
	
	setFont(table.getFont());
	
	if (hasFocus)
	{
	    setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
	    if (value.getPropertyEditor()!=null)
	    {
		super.setForeground( UIManager.getColor("Table.focusCellForeground") );
		super.setBackground( UIManager.getColor("Table.focusCellBackground") );
	    }
	} else
	{if (isSelected) {
	   super.setForeground(table.getSelectionForeground());
	   super.setBackground(table.getSelectionBackground());
	}
	else {
	    super.setForeground((unselectedForeground != null) ? unselectedForeground 
	                                                       : table.getForeground());
	    super.setBackground((unselectedBackground != null) ? unselectedBackground 
	                                                       : table.getBackground());
	}
	
	    setBorder(noFocusBorder);
	}
	
	if(value.getPropertyEditor()!=null)
	{
	    value.getPropertyEditor().setValue(value.getValue());
	    if(value.getPropertyEditor().isPaintable())
	    {
		setText("a");
		super.paintComponent(g);
		Rectangle draw=new Rectangle(g.getClipBounds().x+2,g.getClipBounds().y+2,g.getClipBounds().width-4,g.getClipBounds().height-4);
		value.getPropertyEditor().paintValue(g,draw);
	    }
	    else if(value.getPropertyEditor().getAsText()!=null)
	    {
		setText(value.getPropertyEditor().getAsText());
		super.paintComponent(g);
	    }
	    
	}
	else
	{
	    setText("null");
	    super.paintComponent(g);
	}
	
    }
    
}
