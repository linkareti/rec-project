
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
    
    public static ComponentUI createUI(JComponent c) {
        return new FlatComboBoxUI();
    }
    

    /**
     * Creates the popup portion of the combo box.
     *
     * @return an instance of <code>ComboPopup</code>
     * @see ComboPopup
     */
//    protected ComboPopup createPopup() {
//    	ComboPopup bp = super.createPopup();
//    	JList jl = bp.getList();
////    	jl.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_SOLID_THIN_BLUE, 1) );
//    	jl.setAutoscrolls(false);
////        jl.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
////        jl.setBorderPainted( true );
////        setBorder(LIST_BORDER);
//        jl.setOpaque( false );
//        Border emptyBorder = BorderFactory.createEmptyBorder();
//        bp. .setBorder(emptyBorder);
//    	return bp;
//    }

    
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
		button.setBackground(COLOR_BORDER_SOLID_THIN_BLUE);
		button.setFocusPainted(false);
		button.setRolloverEnabled(false);
		return button;
    }
   
    /**
//     * Paints the currently selected item.
//     */
//    @Override
//    public void paintCurrentValue(Graphics g,Rectangle bounds,boolean hasFocus) {
//        ListCellRenderer renderer = comboBox.getRenderer();
//        Component c;
//listBox.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_SOLID_THIN_BLUE, 1));
//		
//		
//
//        if ( hasFocus && !isPopupVisible(comboBox) ) {
//            c = renderer.getListCellRendererComponent( listBox,
//                                                       comboBox.getSelectedItem(),
//                                                       -1,
//                                                       true,
//                                                       false );
//        }
//        else {
//            c = renderer.getListCellRendererComponent( listBox,
//                                                       comboBox.getSelectedItem(),
//                                                       -1,
//                                                       false,
//                                                       false );
//            c.setBackground(Color.black); //TODO mudar as cores
//            
//            System.out.print("__ENTROU1");
//        }
////        c.setFont(ElabFonts.getFont(Font.PLAIN));
//        if ( hasFocus && !isPopupVisible(comboBox) ) {
//            c.setForeground(new Color(0x0));
//            c.setBackground(new Color(0xFFFFFF));
//        }
//        else {
//            if ( comboBox.isEnabled() ) {
//                c.setForeground(comboBox.getForeground());
//                c.setBackground(comboBox.getBackground());
//                System.out.print("__ENTROU2");
//            }
//            else {
//                c.setForeground(new Color(0x0));
//                c.setBackground(new Color(0xFFFFFF));
//            }
//        }
//
//        // Fix for 4238829: should lay out the JPanel.
//        boolean shouldValidate = false;
//        if (c instanceof JPanel)  {
//            shouldValidate = true;
//        }
//
//        currentValuePane.paintComponent(g,c,comboBox,bounds.x,bounds.y,
//                                        bounds.width,bounds.height, shouldValidate);
//    }
//
//    /**
//     * Paints the background of the currently selected item.
//     */
//    @Override
//    public void paintCurrentValueBackground(Graphics g,Rectangle bounds,boolean hasFocus) {
//        Color t = g.getColor();
//        if ( comboBox.isEnabled() )
//            //TODO corrigir as cores
//            g.setColor(new Color(0xF1FFF6));
//        else
//            g.setColor(new Color(0xF00FFF));
//        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
//        g.setColor(t);
//    }
}
