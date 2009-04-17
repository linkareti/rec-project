
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalComboBoxIcon;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.linkare.rec.impl.newface.laf.flat.elabtheme.ElabTheme2;
import com.linkare.rec.impl.newface.laf.flat.elabtheme.FlatComboBoxIcon;
import com.sun.media.ui.ComboBox;


/**
 *
 * @author João
 */
public class FlatComboBoxUI extends MetalComboBoxUI{
	
	private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x8CABB3);	
	private static final Color COLOR_ARROWBUTTON = new Color(0x64797F);
    
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
		button.setMargin( new Insets( 0, 3, 1, 3 ) );
		button.setBackground(COLOR_ARROWBUTTON);
		button.setFocusPainted(false);
		button.setRolloverEnabled(false);
		return button;
    }
   

}
