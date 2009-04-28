/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.config.Apparatus;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Henrique Fernandes
 */
public class ApparatusRenderer extends JLabel implements ListCellRenderer {

    private ResourceMap resourceMap;

    private Apparatus apparatus;

    public ApparatusRenderer(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        // Value must be an Apparatus
        apparatus = (Apparatus) value;

        // Set colors
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        // Set Icon
        setIcon(resourceMap.getIcon(apparatus.getIconLocationBundleKey()));

        // Set Text
        setText(resourceMap.getString(apparatus.getDisplayStringBundleKey()));

        return this;
    }

}
