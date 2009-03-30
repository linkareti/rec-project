/*
 * Laffy - Swing Look and Feel Sampler
 * Copyright (C) Sun Microsystems
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */

package org.jdesktop.laffy.combospinner;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

import java.awt.Color;

/** @author Jasper Potts */
public class SpinnerPanel extends OptionsSettablePanel {
    //private static final java.util.ResourceBundle laffy = org.jdesktop.laffy.I18nResourceHandler.getResourceBundle();

    private final Color DEFAULT_BACKGROUND;
    private final boolean DEFAULT_OPAQUE;

    /** Creates new form ComboPanel */
    public SpinnerPanel() {
        initComponents();
        DEFAULT_BACKGROUND = jSpinner1.getBackground();
        DEFAULT_OPAQUE = jSpinner1.isOpaque();
    }

    // =================================================================================================================
    // OptionsSettablePanel Methods

    @Override public void setForceComponentsBackgroundColor(boolean force) {
        System.out.println("ButtonPanel.setForceComponetsbackgroundRed " + force);
        Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
//        boolean o = force ? true : DEFAULT_OPAQUE;
        jSpinner1.setBackground(c);
        jSpinner2.setBackground(c);
        jSpinner3.setBackground(c);
        jSpinner4.setBackground(c);
        jSpinner5.setBackground(c);
        jSpinner6.setBackground(c);
        jSpinner7.setBackground(c);
        jSpinner8.setBackground(c);
//        jSpinner1.setOpaque(o);
//        jSpinner2.setOpaque(o);
//        jSpinner3.setOpaque(o);
//        jSpinner4.setOpaque(o);
//        jSpinner5.setOpaque(o);
//        jSpinner6.setOpaque(o);
//        jSpinner7.setOpaque(o);
//        jSpinner8.setOpaque(o);
    }

    @Override public void setForceComponentsNonOpaque(boolean force) {
        setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
        boolean o = force ? false : DEFAULT_OPAQUE;
        jSpinner1.setOpaque(o);
        jSpinner2.setOpaque(o);
        jSpinner3.setOpaque(o);
        jSpinner4.setOpaque(o);
        jSpinner5.setOpaque(o);
        jSpinner6.setOpaque(o);
        jSpinner7.setOpaque(o);
        jSpinner8.setOpaque(o);
    }

    // =================================================================================================================
    // Netbeans Code

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner4 = new javax.swing.JSpinner();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jSpinner7 = new javax.swing.JSpinner();
        jSpinner8 = new javax.swing.JSpinner();

        setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() | java.awt.Font.BOLD, jLabel8.getFont().getSize()-2));
        jLabel8.setForeground(java.awt.Color.darkGray);
        //jLabel8.setText("Normal");
        jLabel8.setText(I18nResourceHandler.getMessage("Normal"));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel8, gridBagConstraints);

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD, jLabel2.getFont().getSize()-2));
        jLabel2.setForeground(java.awt.Color.darkGray);
        //jLabel2.setText("Focused");
        jLabel2.setText(I18nResourceHandler.getMessage("Focused"));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel2, gridBagConstraints);

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() | java.awt.Font.BOLD, jLabel3.getFont().getSize()-2));
        jLabel3.setForeground(java.awt.Color.darkGray);
        //jLabel3.setText("Date Model");
        jLabel3.setText(I18nResourceHandler.getMessage("Date_Model"));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel3, gridBagConstraints);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()-2));
        jLabel1.setForeground(java.awt.Color.darkGray);
        //jLabel1.setText("Normal");
        jLabel1.setText(I18nResourceHandler.getMessage("Normal"));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel1, gridBagConstraints);

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getStyle() | java.awt.Font.BOLD, jLabel6.getFont().getSize()-2));
        jLabel6.setForeground(java.awt.Color.darkGray);
        //jLabel6.setText("Disabled");
        jLabel6.setText(I18nResourceHandler.getMessage("Disabled"));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner1, gridBagConstraints);

        jSpinner2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner3, gridBagConstraints);

        jSpinner4.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner4, gridBagConstraints);

        jSpinner5.setModel(new javax.swing.SpinnerDateModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner5, gridBagConstraints);

        jSpinner6.setModel(new javax.swing.SpinnerDateModel());
        jSpinner6.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner6, gridBagConstraints);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize()-2));
        jLabel4.setForeground(java.awt.Color.darkGray);
        //jLabel4.setText("List Model");
        jLabel4.setText(I18nResourceHandler.getMessage("List_Model"));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel4, gridBagConstraints);

        //jSpinner7.setModel(new javax.swing.SpinnerListModel(new String[]{"Item 0", "Item 1", "Item 2", "Item 3"}));
        jSpinner7.setModel(new javax.swing.SpinnerListModel(new String[] {I18nResourceHandler.getMessage("Item_0"), I18nResourceHandler.getMessage("Item_1"), I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3")}));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner7, gridBagConstraints);

        //jSpinner8.setModel(new javax.swing.SpinnerListModel(new String[]{"Item 0", "Item 1", "Item 2", "Item 3"}));
        jSpinner8.setModel(new javax.swing.SpinnerListModel(new String[] {I18nResourceHandler.getMessage("Item_0"), I18nResourceHandler.getMessage("Item_1"), I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3")}));
        jSpinner8.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jSpinner8, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JSpinner jSpinner7;
    private javax.swing.JSpinner jSpinner8;
    // End of variables declaration//GEN-END:variables

}
