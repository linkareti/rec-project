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

package org.jdesktop.laffy.texts;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

import javax.swing.JTextField;
import java.awt.Color;

/** @author Richard Bair */
public class TextFieldPanel extends OptionsSettablePanel {

    private final Color DEFAULT_BACKGROUND;
    private final boolean DEFAULT_OPAQUE;

    /** Creates new form ButtonPanel */
    public TextFieldPanel() {
        initComponents();
        DEFAULT_BACKGROUND = jTextField1.getBackground();
        DEFAULT_OPAQUE = jTextField1.isOpaque();
    }

    // =================================================================================================================
    // OptionsSettablePanel Methods

    @Override public void setForceComponentsBackgroundColor(boolean force) {
        System.out.println("ButtonPanel.setForceComponetsbackgroundRed " + force);
        Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
//        boolean o = force ? true : DEFAULT_OPAQUE;
        jTextField1.setBackground(c);
        jTextField10.setBackground(c);
        jTextField11.setBackground(c);
        jTextField12.setBackground(c);
        jTextField13.setBackground(c);
        jTextField14.setBackground(c);
        jTextField15.setBackground(c);
        jTextField16.setBackground(c);
        jTextField17.setBackground(c);
        jTextField18.setBackground(c);
        jTextField2.setBackground(c);
        jTextField3.setBackground(c);
        jTextField4.setBackground(c);
        jTextField5.setBackground(c);
        jTextField6.setBackground(c);
        jTextField7.setBackground(c);
        jTextField8.setBackground(c);
        jTextField9.setBackground(c);
//        jTextField1.setOpaque(o);
//        jTextField10.setOpaque(o);
//        jTextField11.setOpaque(o);
//        jTextField12.setOpaque(o);
//        jTextField13.setOpaque(o);
//        jTextField14.setOpaque(o);
//        jTextField15.setOpaque(o);
//        jTextField16.setOpaque(o);
//        jTextField17.setOpaque(o);
//        jTextField18.setOpaque(o);
//        jTextField2.setOpaque(o);
//        jTextField3.setOpaque(o);
//        jTextField4.setOpaque(o);
//        jTextField5.setOpaque(o);
//        jTextField6.setOpaque(o);
//        jTextField7.setOpaque(o);
//        jTextField8.setOpaque(o);
//        jTextField9.setOpaque(o);
    }

    @Override public void setForceComponentsNonOpaque(boolean force) {
        setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
        boolean o = force ? false : DEFAULT_OPAQUE;
        jTextField1.setOpaque(o);
        jTextField10.setOpaque(o);
        jTextField11.setOpaque(o);
        jTextField12.setOpaque(o);
        jTextField13.setOpaque(o);
        jTextField14.setOpaque(o);
        jTextField15.setOpaque(o);
        jTextField16.setOpaque(o);
        jTextField17.setOpaque(o);
        jTextField18.setOpaque(o);
        jTextField2.setOpaque(o);
        jTextField3.setOpaque(o);
        jTextField4.setOpaque(o);
        jTextField5.setOpaque(o);
        jTextField6.setOpaque(o);
        jTextField7.setOpaque(o);
        jTextField8.setOpaque(o);
        jTextField9.setOpaque(o);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new FocusedTextField();
        jTextField5 = new FocusedTextField();
        jTextField6 = new FocusedTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()-2));
        jLabel1.setForeground(java.awt.Color.darkGray);
        //jLabel1.setText("Editable");
        jLabel1.setText(I18nResourceHandler.getMessage("Editable"));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel1, gridBagConstraints);

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
        //jLabel3.setText("Custom Background");
        jLabel3.setText(I18nResourceHandler.getMessage("Custom_Background"));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel3, gridBagConstraints);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize()-2));
        jLabel4.setForeground(java.awt.Color.darkGray);
        //jLabel4.setText("Custom Foreground");
        jLabel4.setText(I18nResourceHandler.getMessage("Custom_Foreground"));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel4, gridBagConstraints);

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD, jLabel5.getFont().getSize()-2));
        jLabel5.setForeground(java.awt.Color.darkGray);
        //jLabel5.setText("Read Only");
        jLabel5.setText(I18nResourceHandler.getMessage("Read_Only"));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel5, gridBagConstraints);

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getStyle() | java.awt.Font.BOLD, jLabel7.getFont().getSize()-2));
        jLabel7.setForeground(java.awt.Color.darkGray);
        //jLabel7.setText("Disabled");
        jLabel7.setText(I18nResourceHandler.getMessage("Disabled"));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel7, gridBagConstraints);

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

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD, jLabel9.getFont().getSize()-2));
        jLabel9.setForeground(java.awt.Color.darkGray);
        //jLabel9.setText("Custom Font");
        jLabel9.setText(I18nResourceHandler.getMessage("Custom_Font"));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel9, gridBagConstraints);

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getStyle() | java.awt.Font.BOLD, jLabel10.getFont().getSize()-2));
        jLabel10.setForeground(java.awt.Color.darkGray);
        //jLabel10.setText("Custom Border");
        jLabel10.setText(I18nResourceHandler.getMessage("Custom_Border"));
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel10, gridBagConstraints);

        //jTextField1.setText("The Quick Brown Fox");
        jTextField1.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField1, gridBagConstraints);

        jTextField2.setEditable(false);
        //jTextField2.setText("The Quick Brown Fox");
        jTextField2.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField2, gridBagConstraints);

        //jTextField3.setText("The Quick Brown Fox");
        jTextField3.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField3, gridBagConstraints);

        //jTextField4.setText("The Quick Brown Fox");
        jTextField4.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField4, gridBagConstraints);

        jTextField5.setEditable(false);
        //jTextField5.setText("The Quick Brown Fox");
        jTextField5.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField5, gridBagConstraints);

        //jTextField6.setText("The Quick Brown Fox");
        jTextField6.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField6.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField6, gridBagConstraints);

        jTextField7.setBackground(java.awt.Color.red);
        //jTextField7.setText("The Quick Brown Fox");
        jTextField7.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField7.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField7, gridBagConstraints);

        jTextField8.setBackground(java.awt.Color.red);
        jTextField8.setEditable(false);
        //jTextField8.setText("The Quick Brown Fox");
        jTextField8.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField8, gridBagConstraints);

        jTextField9.setBackground(java.awt.Color.red);
        //jTextField9.setText("The Quick Brown Fox");
        jTextField9.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField9, gridBagConstraints);

        jTextField10.setEditable(false);
        jTextField10.setForeground(java.awt.Color.red);
        //jTextField10.setText("The Quick Brown Fox");
        jTextField10.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField10, gridBagConstraints);

        jTextField11.setForeground(java.awt.Color.red);
        //jTextField11.setText("The Quick Brown Fox");
        jTextField11.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField11, gridBagConstraints);

        jTextField12.setForeground(java.awt.Color.red);
        //jTextField12.setText("The Quick Brown Fox");
        jTextField12.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField12.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField12, gridBagConstraints);

        jTextField13.setFont(jTextField13.getFont().deriveFont((jTextField13.getFont().getStyle() | java.awt.Font.ITALIC)));
        //jTextField13.setText("The Quick Brown Fox");
        jTextField13.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField13, gridBagConstraints);

        jTextField14.setEditable(false);
        jTextField14.setFont(jTextField14.getFont().deriveFont((jTextField14.getFont().getStyle() | java.awt.Font.ITALIC)));
        //jTextField14.setText("The Quick Brown Fox");
        jTextField14.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField14, gridBagConstraints);

        jTextField15.setFont(jTextField15.getFont().deriveFont((jTextField15.getFont().getStyle() | java.awt.Font.ITALIC)));
        //jTextField15.setText("The Quick Brown Fox");
        jTextField15.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField15.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField15, gridBagConstraints);

        //jTextField16.setText("The Quick Brown Fox");
        jTextField16.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField16.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField16, gridBagConstraints);

        jTextField17.setEditable(false);
        //jTextField17.setText("The Quick Brown Fox");
        jTextField17.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField17.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField17, gridBagConstraints);

        //jTextField18.setText("The Quick Brown Fox");
        jTextField18.setText(I18nResourceHandler.getMessage("The_Quick_Brown_Fox"));
        jTextField18.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        jTextField18.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTextField18, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    // =================================================================================================================
    // Custom Text Fields

    public static final class FocusedTextField extends JTextField {
        @Override
        public boolean isFocusOwner() {
            return true;
        }

        @Override
        public boolean hasFocus() {
            return true;
        }
    }
}
