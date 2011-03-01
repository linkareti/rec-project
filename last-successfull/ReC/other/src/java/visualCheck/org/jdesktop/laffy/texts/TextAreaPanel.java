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

import java.awt.Color;

import javax.swing.JTextArea;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

/** @author Richard Bair */
public class TextAreaPanel extends OptionsSettablePanel {

	private final Color DEFAULT_BACKGROUND;
	private final boolean DEFAULT_OPAQUE;

	/** Creates new form ButtonPanel */
	public TextAreaPanel() {
		initComponents();
		DEFAULT_BACKGROUND = jTextArea1.getBackground();
		DEFAULT_OPAQUE = jTextArea1.isOpaque();
	}

	// =================================================================================================================
	// OptionsSettablePanel Methods

	@Override
	public void setForceComponentsBackgroundColor(boolean force) {
		System.out.println("ButtonPanel.setForceComponetsbackgroundRed " + force);
		Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
		//        boolean o = force ? true : DEFAULT_OPAQUE;
		jTextArea1.setBackground(c);
		jTextArea10.setBackground(c);
		jTextArea11.setBackground(c);
		jTextArea12.setBackground(c);
		jTextArea13.setBackground(c);
		jTextArea14.setBackground(c);
		jTextArea15.setBackground(c);
		jTextArea2.setBackground(c);
		jTextArea3.setBackground(c);
		jTextArea4.setBackground(c);
		jTextArea5.setBackground(c);
		jTextArea6.setBackground(c);
		jTextArea7.setBackground(c);
		jTextArea8.setBackground(c);
		jTextArea9.setBackground(c);
		//        jTextArea1.setOpaque(o);
		//        jTextArea10.setOpaque(o);
		//        jTextArea11.setOpaque(o);
		//        jTextArea12.setOpaque(o);
		//        jTextArea2.setOpaque(o);
		//        jTextArea3.setOpaque(o);
		//        jTextArea4.setOpaque(o);
		//        jTextArea5.setOpaque(o);
		//        jTextArea6.setOpaque(o);
		//        jTextArea7.setOpaque(o);
		//        jTextArea8.setOpaque(o);
		//        jTextArea9.setOpaque(o);
	}

	@Override
	public void setForceComponentsNonOpaque(boolean force) {
		setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
		boolean o = force ? false : DEFAULT_OPAQUE;
		jTextArea1.setOpaque(o);
		jTextArea10.setOpaque(o);
		jTextArea11.setOpaque(o);
		jTextArea12.setOpaque(o);
		jTextArea13.setOpaque(o);
		jTextArea14.setOpaque(o);
		jTextArea15.setOpaque(o);
		jTextArea2.setOpaque(o);
		jTextArea3.setOpaque(o);
		jTextArea4.setOpaque(o);
		jTextArea5.setOpaque(o);
		jTextArea6.setOpaque(o);
		jTextArea7.setOpaque(o);
		jTextArea8.setOpaque(o);
		jTextArea9.setOpaque(o);
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
		jTextArea1 = new javax.swing.JTextArea();
		jTextArea2 = new javax.swing.JTextArea();
		jTextArea3 = new javax.swing.JTextArea();
		jTextArea4 = new FocusedTextArea();
		jTextArea5 = new FocusedTextArea();
		jTextArea6 = new FocusedTextArea();
		jTextArea7 = new javax.swing.JTextArea();
		jTextArea8 = new javax.swing.JTextArea();
		jTextArea9 = new javax.swing.JTextArea();
		jTextArea10 = new javax.swing.JTextArea();
		jTextArea11 = new javax.swing.JTextArea();
		jTextArea12 = new javax.swing.JTextArea();
		jLabel9 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea13 = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea14 = new javax.swing.JTextArea();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTextArea15 = new javax.swing.JTextArea();

		setLayout(new java.awt.GridBagLayout());

		jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize() - 2));
		jLabel1.setForeground(java.awt.Color.darkGray);
		//jLabel1.setText("Editable");
		jLabel1.setText(I18nResourceHandler.getMessage("Editable"));
		jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel1, gridBagConstraints);

		jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD, jLabel2.getFont().getSize() - 2));
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

		jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() | java.awt.Font.BOLD, jLabel3.getFont().getSize() - 2));
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

		jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize() - 2));
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

		jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD, jLabel5.getFont().getSize() - 2));
		jLabel5.setForeground(java.awt.Color.darkGray);
		//jLabel5.setText("Read Only");
		jLabel5.setText(I18nResourceHandler.getMessage("Read_Only"));
		jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel5, gridBagConstraints);

		jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getStyle() | java.awt.Font.BOLD, jLabel7.getFont().getSize() - 2));
		jLabel7.setForeground(java.awt.Color.darkGray);
		//jLabel7.setText("Disabled");
		jLabel7.setText(I18nResourceHandler.getMessage("Disabled"));
		jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel7, gridBagConstraints);

		jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() | java.awt.Font.BOLD, jLabel8.getFont().getSize() - 2));
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

		jTextArea1.setColumns(12);
		jTextArea1.setRows(3);
		//jTextArea1.setText("The\nQuick\nBrown Fox");
		jTextArea1.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea1, gridBagConstraints);

		jTextArea2.setColumns(12);
		jTextArea2.setEditable(false);
		jTextArea2.setRows(3);
		//jTextArea2.setText("The\nQuick\nBrown Fox");
		jTextArea2.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea2, gridBagConstraints);

		jTextArea3.setColumns(12);
		jTextArea3.setRows(3);
		//jTextArea3.setText("The\nQuick\nBrown Fox");
		jTextArea3.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		jTextArea3.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea3, gridBagConstraints);

		jTextArea4.setColumns(12);
		jTextArea4.setRows(3);
		//jTextArea4.setText("The\nQuick\nBrown Fox");
		jTextArea4.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea4, gridBagConstraints);

		jTextArea5.setColumns(12);
		jTextArea5.setEditable(false);
		jTextArea5.setRows(3);
		//jTextArea5.setText("The\nQuick\nBrown Fox");
		jTextArea5.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea5, gridBagConstraints);

		jTextArea6.setColumns(12);
		jTextArea6.setRows(3);
		//jTextArea6.setText("The\nQuick\nBrown Fox");
		jTextArea6.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		jTextArea6.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea6, gridBagConstraints);

		jTextArea7.setBackground(java.awt.Color.red);
		jTextArea7.setColumns(12);
		jTextArea7.setRows(3);
		//jTextArea7.setText("The\nQuick\nBrown Fox");
		jTextArea7.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea7, gridBagConstraints);

		jTextArea8.setBackground(java.awt.Color.red);
		jTextArea8.setColumns(12);
		jTextArea8.setEditable(false);
		jTextArea8.setRows(3);
		//jTextArea8.setText("The\nQuick\nBrown Fox");
		jTextArea8.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea8, gridBagConstraints);

		jTextArea9.setBackground(java.awt.Color.red);
		jTextArea9.setColumns(12);
		jTextArea9.setRows(3);
		//jTextArea9.setText("The\nQuick\nBrown Fox");
		jTextArea9.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		jTextArea9.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea9, gridBagConstraints);

		jTextArea10.setColumns(12);
		jTextArea10.setForeground(java.awt.Color.red);
		jTextArea10.setRows(3);
		//jTextArea10.setText("The\nQuick\nBrown Fox");
		jTextArea10.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea10, gridBagConstraints);

		jTextArea11.setColumns(12);
		jTextArea11.setEditable(false);
		jTextArea11.setForeground(java.awt.Color.red);
		jTextArea11.setRows(3);
		//jTextArea11.setText("The\nQuick\nBrown Fox");
		jTextArea11.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea11, gridBagConstraints);

		jTextArea12.setColumns(12);
		jTextArea12.setForeground(java.awt.Color.red);
		jTextArea12.setRows(3);
		//jTextArea12.setText("The\nQuick\nBrown Fox");
		jTextArea12.setText(I18nResourceHandler.getMessage("The_CR_Quick_CR_Brown_Fox"));
		jTextArea12.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTextArea12, gridBagConstraints);

		jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD, jLabel9.getFont().getSize() - 2));
		jLabel9.setForeground(java.awt.Color.darkGray);
		//jLabel9.setText("In ScrollPane");
		jLabel9.setText(I18nResourceHandler.getMessage("In_ScrollPane"));
		jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel9, gridBagConstraints);

		jTextArea13.setColumns(12);
		jTextArea13.setRows(3);
		//jTextArea13.setText("The\nQuick\nBrown Fox\nThe\nQuick\nBrown Fox\nThe\nQuick\nBrown Fox");
		jTextArea13.setText(I18nResourceHandler
				.getMessage("The_CR_Quick_CR_Brown_Fox_CR_The_CR_Quick_CR_Brown_Fox_CR_The_CR_Quick_CR_Brown_Fox"));
		jScrollPane1.setViewportView(jTextArea13);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipady = 50;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane1, gridBagConstraints);

		jTextArea14.setColumns(12);
		jTextArea14.setEditable(false);
		jTextArea14.setRows(3);
		//jTextArea14.setText("The\nQuick\nBrown Fox\nThe\nQuick\nBrown Fox\nThe\nQuick\nBrown Fox");
		jTextArea14.setText(I18nResourceHandler
				.getMessage("The_CR_Quick_CR_Brown_Fox_CR_The_CR_Quick_CR_Brown_Fox_CR_The_CR_Quick_CR_Brown_Fox"));
		jScrollPane2.setViewportView(jTextArea14);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipady = 50;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane2, gridBagConstraints);

		jTextArea15.setColumns(12);
		jTextArea15.setRows(3);
		//jTextArea15.setText("The\nQuick\nBrown Fox\nThe\nQuick\nBrown Fox\nThe\nQuick\nBrown Fox");
		jTextArea15.setText(I18nResourceHandler
				.getMessage("The_CR_Quick_CR_Brown_Fox_CR_The_CR_Quick_CR_Brown_Fox_CR_The_CR_Quick_CR_Brown_Fox"));
		jTextArea15.setEnabled(false);
		jScrollPane3.setViewportView(jTextArea15);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipady = 50;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane3, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea10;
	private javax.swing.JTextArea jTextArea11;
	private javax.swing.JTextArea jTextArea12;
	private javax.swing.JTextArea jTextArea13;
	private javax.swing.JTextArea jTextArea14;
	private javax.swing.JTextArea jTextArea15;
	private javax.swing.JTextArea jTextArea2;
	private javax.swing.JTextArea jTextArea3;
	private javax.swing.JTextArea jTextArea4;
	private javax.swing.JTextArea jTextArea5;
	private javax.swing.JTextArea jTextArea6;
	private javax.swing.JTextArea jTextArea7;
	private javax.swing.JTextArea jTextArea8;
	private javax.swing.JTextArea jTextArea9;

	// End of variables declaration//GEN-END:variables

	// =================================================================================================================
	// Custom Text Fields

	public static final class FocusedTextArea extends JTextArea {
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
