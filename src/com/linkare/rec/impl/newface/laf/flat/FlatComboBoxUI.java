
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;

import com.linkare.rec.impl.newface.laf.flat.elabtheme.FlatComboBoxIcon;

/**
 *
 * @author João
 */
public class FlatComboBoxUI extends MetalComboBoxUI{
	
	private static final Color COLOR_ARROWBUTTON = new Color(0x64797F);
	private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x8CABB3);	
	
    public static ComponentUI createUI(JComponent c) {
        return new FlatComboBoxUI();
    }
    
    /**
     * Creates an button which will be used as the control to show or hide
     * the popup portion of the combo box.
     *
     * @return a button which represents the popup control
     */
    @Override
    protected JButton createArrowButton() {
    	JButton button = new JButton();
    	button.setIcon(new FlatComboBoxIcon());
    	button.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
		button.setBackground(COLOR_ARROWBUTTON);
		
		// ASK Joao, why did you remove this?
		button.setFocusable(false);
		button.setFocusPainted(false);
		button.setRolloverEnabled(false);
		return button;
    }
   
}
