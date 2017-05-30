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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

/** @author Jasper Potts */
public class ComboPanel extends OptionsSettablePanel {

	private final Color DEFAULT_BACKGROUND;
	private final boolean DEFAULT_OPAQUE;

	/** Creates new form ComboPanel */
	public ComboPanel() {
		initComponents();
		DEFAULT_BACKGROUND = jComboBox1.getBackground();
		DEFAULT_OPAQUE = jComboBox1.isOpaque();

		smallIconComboBox.setModel(new DefaultComboBoxModel(new Object[] { new NamedIcon("FileChooser.fileIcon"),
				new NamedIcon("FileChooser.directoryIcon"), new NamedIcon("FileChooser.upFolderIcon"),
				new NamedIcon("FileChooser.newFolderIcon"), new NamedIcon("FileChooser.hardDriveIcon"),
				new NamedIcon("FileChooser.floppyDriveIcon"), new NamedIcon("FileChooser.homeFolderIcon"),
				new NamedIcon("FileChooser.detailsViewIcon"), new NamedIcon("FileChooser.listViewIcon"), }));
		smallIconComboBox.setRenderer(new IconRender(smallIconComboBox.getRenderer()));
		largeIconComboBox.setModel(new DefaultComboBoxModel(new Object[] { new NamedIcon("OptionPane.errorIcon"),
				new NamedIcon("OptionPane.informationIcon"), new NamedIcon("OptionPane.questionIcon"),
				new NamedIcon("OptionPane.warningIcon"), }));
		largeIconComboBox.setRenderer(new IconRender(smallIconComboBox.getRenderer()));
		jComboBox1.setRenderer(new FontRenderer());
		jComboBox2.setRenderer(new FontRenderer());
		jComboBox3.setRenderer(new FontRenderer());
		jComboBox4.setRenderer(new FontRenderer());
		jComboBox2.putClientProperty("Nimbus.State", "MouseOver");
		jComboBox5.putClientProperty("Nimbus.State", "MouseOver");
		jComboBox8.putClientProperty("Nimbus.State", "MouseOver");
		jComboBox3.putClientProperty("Nimbus.State", "Pressed");
		jComboBox6.putClientProperty("Nimbus.State", "Pressed");
		jComboBox9.putClientProperty("Nimbus.State", "Pressed");
	}

	// =================================================================================================================
	// OptionsSettablePanel Methods

	@Override
	public void setForceComponentsBackgroundColor(boolean force) {
		System.out.println("ButtonPanel.setForceComponetsbackgroundRed " + force);
		Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
		//        boolean o = force ? true : DEFAULT_OPAQUE;
		jComboBox1.setBackground(c);
		jComboBox2.setBackground(c);
		jComboBox3.setBackground(c);
		jComboBox4.setBackground(c);
		jComboBox7.setBackground(c);
		jComboBox8.setBackground(c);
		jComboBox9.setBackground(c);
		jComboBox10.setBackground(c);
		jComboBox5.setBackground(c);
		jComboBox6.setBackground(c);
		jComboBox16.setBackground(c);
		largeIconComboBox.setBackground(c);
		smallIconComboBox.setBackground(c);
		//        jComboBox1.setOpaque(o);
		//        jComboBox16.setOpaque(o);
		//        jComboBox2.setOpaque(o);
		//        jComboBox3.setOpaque(o);
		//        jComboBox4.setOpaque(o);
		//        jComboBox5.setOpaque(o);
		//        jComboBox6.setOpaque(o);
		//        largeIconComboBox.setOpaque(o);
		//        smallIconComboBox.setOpaque(o);
	}

	@Override
	public void setForceComponentsNonOpaque(boolean force) {
		setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
		boolean o = force ? false : DEFAULT_OPAQUE;
		jComboBox1.setOpaque(o);
		jComboBox2.setOpaque(o);
		jComboBox3.setOpaque(o);
		jComboBox4.setOpaque(o);
		jComboBox5.setOpaque(o);
		jComboBox6.setOpaque(o);
		jComboBox7.setOpaque(o);
		jComboBox8.setOpaque(o);
		jComboBox9.setOpaque(o);
		jComboBox10.setOpaque(o);
		jComboBox16.setOpaque(o);
		largeIconComboBox.setOpaque(o);
		smallIconComboBox.setOpaque(o);
	}

	// =================================================================================================================
	// Netbeans Code

	private static class NamedIcon {
		public Icon icon;
		public String name;

		public NamedIcon(String name) {
			this.name = name;
			this.icon = UIManager.getIcon(name);
		}

		public NamedIcon(String name, Icon icon) {
			this.name = name;
			this.icon = icon;
		}
	}

	private static class FontRenderer extends DefaultListCellRenderer {
		private Font font;

		FontRenderer() {
			this.font = getFont();
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (I18nResourceHandler.getMessage("Item_1").equals(value)) {
				setFont(font.deriveFont(Font.BOLD));
			} else if (I18nResourceHandler.getMessage("Item_4").equals(value)) {
				setFont(font.deriveFont(Font.ITALIC));
			} else {
				setFont(font);
			}
			return this;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
		}
	}

	private static class IconRender implements ListCellRenderer {
		private ListCellRenderer defaultRenderer;

		public IconRender(ListCellRenderer defaultRenderer) {
			this.defaultRenderer = defaultRenderer;
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (value instanceof NamedIcon) {
				NamedIcon namedIcon = (NamedIcon) value;
				JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, namedIcon.name, index, isSelected, cellHasFocus);
				label.setIcon(namedIcon.icon);
				return label;
			} else {
				return defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jLabel8 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jComboBox2 = new javax.swing.JComboBox();
		jComboBox3 = new javax.swing.JComboBox();
		jComboBox4 = new javax.swing.JComboBox();
		jComboBox5 = new JComboBox() {
			@Override
			public boolean hasFocus() {
				return true;
			}
		};
		jComboBox6 = new JComboBox() {
			@Override
			public boolean hasFocus() {
				return true;
			}
		};
		jComboBox16 = new JComboBox() {
			@Override
			public boolean hasFocus() {
				return true;
			}
		};
		jLabel9 = new javax.swing.JLabel();
		smallIconComboBox = new javax.swing.JComboBox();
		largeIconComboBox = new javax.swing.JComboBox();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jComboBox7 = new javax.swing.JComboBox();
		jComboBox8 = new javax.swing.JComboBox();
		jComboBox9 = new javax.swing.JComboBox();
		jComboBox10 = new javax.swing.JComboBox();

		setLayout(new java.awt.GridBagLayout());

		jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() | java.awt.Font.BOLD, jLabel8.getFont().getSize() - 2));
		jLabel8.setForeground(java.awt.Color.darkGray);
		//jLabel8.setText("Custom Renderer");
		jLabel8.setText(I18nResourceHandler.getMessage("Custom_Renderer"));
		jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
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

		jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD, jLabel5.getFont().getSize() - 2));
		jLabel5.setForeground(java.awt.Color.darkGray);
		//jLabel5.setText("Over");
		jLabel5.setText(I18nResourceHandler.getMessage("Over"));
		jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel5, gridBagConstraints);

		jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getStyle() | java.awt.Font.BOLD, jLabel7.getFont().getSize() - 2));
		jLabel7.setForeground(java.awt.Color.darkGray);
		//jLabel7.setText("Pressed");
		jLabel7.setText(I18nResourceHandler.getMessage("Pressed"));
		jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel7, gridBagConstraints);

		jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getStyle() | java.awt.Font.BOLD, jLabel6.getFont().getSize() - 2));
		jLabel6.setForeground(java.awt.Color.darkGray);
		//jLabel6.setText("Disabled");
		jLabel6.setText(I18nResourceHandler.getMessage("Disabled"));
		jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		add(jLabel6, gridBagConstraints);

		//jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox1, gridBagConstraints);

		//jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox2, gridBagConstraints);

		//jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox3, gridBagConstraints);

		//jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		jComboBox4.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox4, gridBagConstraints);

		//jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox5, gridBagConstraints);

		//jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox6, gridBagConstraints);

		//jComboBox16.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox16, gridBagConstraints);

		jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD, jLabel9.getFont().getSize() - 2));
		jLabel9.setForeground(java.awt.Color.darkGray);
		//jLabel9.setText("Small Icons");
		jLabel9.setText(I18nResourceHandler.getMessage("Small_Icons"));
		jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel9, gridBagConstraints);

		//smallIconComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		smallIconComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(smallIconComboBox, gridBagConstraints);

		//largeIconComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		largeIconComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(largeIconComboBox, gridBagConstraints);

		jLabel10.setFont(jLabel10.getFont()
				.deriveFont(jLabel10.getFont().getStyle() | java.awt.Font.BOLD, jLabel10.getFont().getSize() - 2));
		jLabel10.setForeground(java.awt.Color.darkGray);
		//jLabel10.setText("Large Icons");
		jLabel10.setText(I18nResourceHandler.getMessage("Large_Icons"));
		jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel10, gridBagConstraints);

		jLabel11.setFont(jLabel11.getFont()
				.deriveFont(jLabel11.getFont().getStyle() | java.awt.Font.BOLD, jLabel11.getFont().getSize() - 2));
		jLabel11.setForeground(java.awt.Color.darkGray);
		//jLabel11.setText("Normal");
		jLabel11.setText(I18nResourceHandler.getMessage("Normal"));
		jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
		add(jLabel11, gridBagConstraints);

		//jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox7, gridBagConstraints);

		//jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox8, gridBagConstraints);

		//jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox9, gridBagConstraints);

		//jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
		jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { I18nResourceHandler.getMessage("Item_1"),
				I18nResourceHandler.getMessage("Item_2"), I18nResourceHandler.getMessage("Item_3"),
				I18nResourceHandler.getMessage("Item_4") }));
		jComboBox10.setEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
		add(jComboBox10, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JComboBox jComboBox10;
	private javax.swing.JComboBox jComboBox16;
	private javax.swing.JComboBox jComboBox2;
	private javax.swing.JComboBox jComboBox3;
	private javax.swing.JComboBox jComboBox4;
	private javax.swing.JComboBox jComboBox5;
	private javax.swing.JComboBox jComboBox6;
	private javax.swing.JComboBox jComboBox7;
	private javax.swing.JComboBox jComboBox8;
	private javax.swing.JComboBox jComboBox9;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JComboBox largeIconComboBox;
	private javax.swing.JComboBox smallIconComboBox;
	// End of variables declaration//GEN-END:variables

}
