/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.config.Apparatus;

/**
 *
 * @author Henrique Fernandes
 */
public class ApparatusRenderer extends JLabel implements ListCellRenderer {

    private Apparatus apparatus;

    public ApparatusRenderer() {
    	setOpaque(false);
    	//setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        // Value must be an Apparatus
        apparatus = (Apparatus) value;

        // FIXME Set colors
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        // Icon
        setIcon(ReCResourceBundle.findImageIconOrDefault(apparatus.getIconLocationBundleKey(), null));
        
        // Text
        setText(ReCResourceBundle.findString(apparatus.getDisplayStringBundleKey()));
        
        // State
        //setEnabled(apparatus.isEnabled());

        return this;
    }

}
