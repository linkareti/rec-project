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

package org.jdesktop.laffy.progress;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

import java.awt.Color;

/** @author Jasper Potts */
public class VerticalProgressPanel extends OptionsSettablePanel {

    private final Color DEFAULT_BACKGROUND;
    private final boolean DEFAULT_OPAQUE;

    /** Creates new form ProgressPanel */
    public VerticalProgressPanel() {
        initComponents();
        DEFAULT_BACKGROUND = jProgressBar9.getBackground();
        DEFAULT_OPAQUE = jProgressBar9.isOpaque();
    }

    // =================================================================================================================
    // OptionsSettablePanel Methods

    @Override public void setForceComponentsBackgroundColor(boolean force) {
        System.out.println("ButtonPanel.setForceComponetsbackgroundRed " + force);
        Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
//        boolean o = force ? true : DEFAULT_OPAQUE;
        jProgressBar10.setBackground(c);
        jProgressBar11.setBackground(c);
        jProgressBar12.setBackground(c);
        jProgressBar13.setBackground(c);
        jProgressBar14.setBackground(c);
        jProgressBar15.setBackground(c);
        jProgressBar16.setBackground(c);
        jProgressBar9.setBackground(c);
//        jProgressBar10.setOpaque(o);
//        jProgressBar11.setOpaque(o);
//        jProgressBar12.setOpaque(o);
//        jProgressBar13.setOpaque(o);
//        jProgressBar14.setOpaque(o);
//        jProgressBar15.setOpaque(o);
//        jProgressBar16.setOpaque(o);
//        jProgressBar9.setOpaque(o);
    }

    @Override public void setForceComponentsNonOpaque(boolean force) {
        setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
        boolean o = force ? false : DEFAULT_OPAQUE;
        jProgressBar10.setOpaque(o);
        jProgressBar11.setOpaque(o);
        jProgressBar12.setOpaque(o);
        jProgressBar13.setOpaque(o);
        jProgressBar14.setOpaque(o);
        jProgressBar15.setOpaque(o);
        jProgressBar16.setOpaque(o);
        jProgressBar9.setOpaque(o);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jProgressBar9 = new javax.swing.JProgressBar();
        jProgressBar10 = new javax.swing.JProgressBar();
        jProgressBar11 = new javax.swing.JProgressBar();
        jProgressBar12 = new javax.swing.JProgressBar();
        jProgressBar13 = new javax.swing.JProgressBar();
        jProgressBar14 = new javax.swing.JProgressBar();
        jProgressBar15 = new javax.swing.JProgressBar();
        jProgressBar16 = new javax.swing.JProgressBar();

        setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() | java.awt.Font.BOLD, jLabel8.getFont().getSize()-2));
        jLabel8.setForeground(java.awt.Color.darkGray);
        //jLabel8.setText("Determinate 0%");
        jLabel8.setText(I18nResourceHandler.getMessage("Determinate_0%"));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel8, gridBagConstraints);

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD, jLabel2.getFont().getSize()-2));
        jLabel2.setForeground(java.awt.Color.darkGray);
        //jLabel2.setText("Determinate 50%");
        jLabel2.setText(I18nResourceHandler.getMessage("Determinate_50%"));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel2, gridBagConstraints);

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() | java.awt.Font.BOLD, jLabel3.getFont().getSize()-2));
        jLabel3.setForeground(java.awt.Color.darkGray);
        //jLabel3.setText("Determinate 100%");
        jLabel3.setText(I18nResourceHandler.getMessage("Determinate_100%"));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel3, gridBagConstraints);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize()-2));
        jLabel4.setForeground(java.awt.Color.darkGray);
        //jLabel4.setText("Indeterminate");
        jLabel4.setText(I18nResourceHandler.getMessage("Indeterminate"));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel4, gridBagConstraints);

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD, jLabel5.getFont().getSize()-2));
        jLabel5.setForeground(java.awt.Color.darkGray);
        //jLabel5.setText("Normal");
        jLabel5.setText(I18nResourceHandler.getMessage("Normal"));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        add(jLabel5, gridBagConstraints);

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getStyle() | java.awt.Font.BOLD, jLabel7.getFont().getSize()-2));
        jLabel7.setForeground(java.awt.Color.darkGray);
        //jLabel7.setText("Disabled");
        jLabel7.setText(I18nResourceHandler.getMessage("Disabled"));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 6);
        add(jLabel7, gridBagConstraints);

        jProgressBar9.setOrientation(1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar9, gridBagConstraints);

        jProgressBar10.setOrientation(1);
        jProgressBar10.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar10, gridBagConstraints);

        jProgressBar11.setOrientation(1);
        jProgressBar11.setValue(50);
        jProgressBar11.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar11, gridBagConstraints);

        jProgressBar12.setOrientation(1);
        jProgressBar12.setValue(50);
        jProgressBar12.setEnabled(false);
        jProgressBar12.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar12, gridBagConstraints);

        jProgressBar13.setOrientation(1);
        jProgressBar13.setValue(100);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar13, gridBagConstraints);

        jProgressBar14.setOrientation(1);
        jProgressBar14.setValue(100);
        jProgressBar14.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar14, gridBagConstraints);

        jProgressBar15.setOrientation(1);
        jProgressBar15.setIndeterminate(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar15, gridBagConstraints);

        jProgressBar16.setOrientation(1);
        jProgressBar16.setEnabled(false);
        jProgressBar16.setIndeterminate(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        add(jProgressBar16, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProgressBar10;
    private javax.swing.JProgressBar jProgressBar11;
    private javax.swing.JProgressBar jProgressBar12;
    private javax.swing.JProgressBar jProgressBar13;
    private javax.swing.JProgressBar jProgressBar14;
    private javax.swing.JProgressBar jProgressBar15;
    private javax.swing.JProgressBar jProgressBar16;
    private javax.swing.JProgressBar jProgressBar9;
    // End of variables declaration//GEN-END:variables

}
