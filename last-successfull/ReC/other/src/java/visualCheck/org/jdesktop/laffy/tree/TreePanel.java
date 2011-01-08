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

package org.jdesktop.laffy.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

/** @author Richard Bair */
public class TreePanel extends OptionsSettablePanel {

	private final Color DEFAULT_BACKGROUND;
	private final boolean DEFAULT_OPAQUE;

	/** Creates new form TablePanel */
	public TreePanel() {
		initComponents();
		DEFAULT_BACKGROUND = jTree1.getBackground();
		DEFAULT_OPAQUE = jTree1.isOpaque();

		jTree1.setEditable(true);
		jTree2.setEditable(true);
		jTree3.setEditable(true);
		jTree4.setEditable(true);
		jTree5.setEditable(true);
		jTree6.setEditable(true);
		jTree7.setEditable(true);
		jTree8.setEditable(true);
		jTree9.setEditable(true);
		jTree10.setEditable(true);

		CustomCellRenderer r = new CustomCellRenderer();
		jTree7.setCellRenderer(r);
		jTree8.setCellRenderer(r);

		jTree2.setSelectionRow(2);
		jTree4.setSelectionRow(2);
		jTree6.setSelectionRow(2);
		jTree8.setSelectionRow(2);
		jTree10.setSelectionRow(2);
	}

	// =================================================================================================================
	// OptionsSettablePanel Methods

	@Override
	public void setForceComponentsBackgroundColor(boolean force) {
		Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
		// apply to all
		for (Component component : getComponents()) {
			if (component instanceof JScrollPane) {
				component = ((JScrollPane) component).getViewport().getView();
			}
			if (component instanceof JTree) {
				component.setBackground(c);
			}
		}
	}

	@Override
	public void setForceComponentsNonOpaque(boolean force) {
		setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
		boolean o = !force && DEFAULT_OPAQUE;
		// apply to all
		for (Component component : getComponents()) {
			if (component instanceof JScrollPane) {
				component = ((JScrollPane) component).getViewport().getView();
			}
			if (component instanceof JTree) {
				((JTree) component).setOpaque(o);
			}
		}
	}

	@Override
	public void setForceComponentsToBasicUI(boolean force) {
		// apply to all
		for (Component component : getComponents()) {
			if (component instanceof JScrollPane) {
				component = ((JScrollPane) component).getViewport().getView();
			}
			if (component instanceof JTree) {
				if (force) {
					((JTree) component).setUI(new BasicTreeUI());
				} else {
					((JTree) component).updateUI();
				}
			}
		}
		revalidate();
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
		jLabel1 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTree2 = new javax.swing.JTree();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTree3 = new FocusedTree();
		jScrollPane4 = new javax.swing.JScrollPane();
		jTree4 = new FocusedTree();
		jTree5 = new javax.swing.JTree();
		jTree6 = new javax.swing.JTree();
		jScrollPane5 = new javax.swing.JScrollPane();
		jTree7 = new javax.swing.JTree();
		jScrollPane6 = new javax.swing.JScrollPane();
		jTree8 = new javax.swing.JTree();
		jScrollPane7 = new javax.swing.JScrollPane();
		jTree9 = new javax.swing.JTree();
		jScrollPane8 = new javax.swing.JScrollPane();
		jTree10 = new javax.swing.JTree();

		setLayout(new java.awt.GridBagLayout());

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
		//jLabel3.setText("Without ScrollPane");
		jLabel3.setText(I18nResourceHandler.getMessage("Without_ScrollPane"));
		jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel3, gridBagConstraints);

		jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize() - 2));
		jLabel4.setForeground(java.awt.Color.darkGray);
		//jLabel4.setText("Custom Cell Renderer");
		jLabel4.setText(I18nResourceHandler.getMessage("Custom_Cell_Renderer"));
		jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel4, gridBagConstraints);

		jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize() - 2));
		jLabel1.setForeground(java.awt.Color.darkGray);
		//jLabel1.setText("Normal");
		jLabel1.setText(I18nResourceHandler.getMessage("Normal"));
		jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel1, gridBagConstraints);

		jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getStyle() | java.awt.Font.BOLD, jLabel6.getFont().getSize() - 2));
		jLabel6.setForeground(java.awt.Color.darkGray);
		//jLabel6.setText("Disabled");
		jLabel6.setText(I18nResourceHandler.getMessage("Disabled"));
		jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel6, gridBagConstraints);

		jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD, jLabel9.getFont().getSize() - 2));
		jLabel9.setForeground(java.awt.Color.darkGray);
		//jLabel9.setText("Custom Colors");
		jLabel9.setText(I18nResourceHandler.getMessage("Custom_Colors"));
		jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel9, gridBagConstraints);

		jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 100));
		jScrollPane1.setViewportView(jTree1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane1, gridBagConstraints);

		jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 100));

		jTree2.setEnabled(false);
		jScrollPane2.setViewportView(jTree2);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane2, gridBagConstraints);

		jScrollPane3.setPreferredSize(new java.awt.Dimension(250, 100));
		jScrollPane3.setViewportView(jTree3);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane3, gridBagConstraints);

		jScrollPane4.setPreferredSize(new java.awt.Dimension(250, 100));

		jTree4.setEnabled(false);
		jScrollPane4.setViewportView(jTree4);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane4, gridBagConstraints);

		jTree5.setPreferredSize(new java.awt.Dimension(250, 100));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTree5, gridBagConstraints);

		jTree6.setEnabled(false);
		jTree6.setPreferredSize(new java.awt.Dimension(250, 100));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jTree6, gridBagConstraints);

		jScrollPane5.setPreferredSize(new java.awt.Dimension(250, 100));
		jScrollPane5.setViewportView(jTree7);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane5, gridBagConstraints);

		jScrollPane6.setPreferredSize(new java.awt.Dimension(250, 100));

		jTree8.setEnabled(false);
		jScrollPane6.setViewportView(jTree8);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane6, gridBagConstraints);

		jScrollPane7.setPreferredSize(new java.awt.Dimension(250, 100));

		jTree9.setBackground(java.awt.Color.gray);
		jTree9.setFont(jTree9.getFont().deriveFont((jTree9.getFont().getStyle() | java.awt.Font.ITALIC)));
		jTree9.setForeground(java.awt.Color.red);
		jScrollPane7.setViewportView(jTree9);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane7, gridBagConstraints);

		jScrollPane8.setPreferredSize(new java.awt.Dimension(250, 100));

		jTree10.setBackground(java.awt.Color.gray);
		jTree10.setFont(jTree10.getFont().deriveFont((jTree10.getFont().getStyle() | java.awt.Font.ITALIC)));
		jTree10.setForeground(java.awt.Color.red);
		jTree10.setEnabled(false);
		jScrollPane8.setViewportView(jTree10);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
		add(jScrollPane8, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JTree jTree1;
	private javax.swing.JTree jTree10;
	private javax.swing.JTree jTree2;
	private javax.swing.JTree jTree3;
	private javax.swing.JTree jTree4;
	private javax.swing.JTree jTree5;
	private javax.swing.JTree jTree6;
	private javax.swing.JTree jTree7;
	private javax.swing.JTree jTree8;
	private javax.swing.JTree jTree9;

	// End of variables declaration//GEN-END:variables

	public static final class FocusedTree extends JTree {
		@Override
		public boolean hasFocus() {
			return true;
		}

		@Override
		public boolean isFocusOwner() {
			return true;
		}
	}

	public static final class CustomCellRenderer extends DefaultTreeCellRenderer {
		private NumberFormat format = NumberFormat.getCurrencyInstance();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			Color fg = getForeground();
			boolean bold = false;
			boolean italic = false;
			value = value == null ? null : value.toString();
			if ("colors".equals(value)) {
				bold = true;
				fg = Color.LIGHT_GRAY;
			} else if ("blue".equals(value)) {
				fg = Color.BLUE;
			} else if ("violet".equals(value)) {
				fg = Color.MAGENTA;
			} else if ("red".equals(value)) {
				fg = Color.RED;
			} else if ("yellow".equals(value)) {
				fg = Color.YELLOW;
			} else if ("sports".equals(value)) {
				bold = true;
				fg = Color.GRAY;
			} else if ("basketball".equals(value)) {
				bold = true;
			} else if ("soccor".equals(value)) {
				italic = true;
			} else if ("football".equals(value)) {
				bold = true;
				italic = true;
			} else if ("hockey".equals(value)) {
				bold = true;
				italic = true;
				fg = Color.ORANGE;
			} else if ("food".equals(value)) {
				bold = true;
				fg = Color.DARK_GRAY;
			} else if ("hot_dogs".equals(value)) {
				fg = Color.PINK;
			} else if ("pizza".equals(value)) {
				fg = Color.ORANGE;
			} else if ("ravioli".equals(value)) {
				fg = Color.RED;
			} else if ("bananas".equals(value)) {
				fg = Color.YELLOW;
			}

			setBackground(Color.ORANGE);
			setForeground(fg);
			Font f = getFont();
			int style = 0;
			if (bold) {
				style |= Font.BOLD;
			}
			if (italic) {
				style |= Font.ITALIC;
			}
			setFont(f.deriveFont(style));

			return this;
		}
	}
}
