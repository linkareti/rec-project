/*
 * LabsTreeCellEditor.java
 *
 * Created on August 17, 2004, 3:21 PM
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;

import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.DisplayNode;

public class LabsTreeCellEditor implements TreeCellEditor {
	public LabsTreeCellEditor() {
	}

	private Object value = null;
	private DisplayNode dtn = null;
	private java.awt.Color unSelectedColor = UIManager.getColor("Tree.textBackground");
	private java.awt.Color selectedColor = UIManager.getColor("Tree.selectionBorderColor");

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		this.value = value;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object o = node.getUserObject();

		if (o instanceof DisplayNode) {
			dtn = (DisplayNode) o;

			JPanel panel = new JPanel(new java.awt.BorderLayout());
			panel.setBackground(UIManager.getColor("Tree.textBackground"));

			JCheckBox checkBox = new JCheckBox();
			checkBox.setBackground(UIManager.getColor("Tree.textBackground"));

			if (o instanceof Display) {
				if (dtn.getIcon() != null) {
					JLabel labelIcon = new JLabel();
					labelIcon.setBackground(UIManager.getColor("Tree.textBackground"));
					panel.add(labelIcon, java.awt.BorderLayout.WEST);
					labelIcon.setIcon(dtn.getIcon());
					labelIcon.setEnabled(dtn.isEnabled());
					labelIcon.setOpaque(false);
				}

				if (dtn.getText() != null) {
					JLabel label = new JLabel();
					label.setFont(new java.awt.Font("Dialog", 0, 12));
					label.setBackground(UIManager.getColor("Tree.textBackground"));
					label.setText(dtn.getText());
					label.setEnabled(dtn.isEnabled());

					final JTree treef = tree;
					final int rowf = row;

					label.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mousePressed(java.awt.event.MouseEvent evt) {
							if (evt.getClickCount() == 2)
								treef.firePropertyChange("ddc", 0, rowf);
						}
					});

					panel.add(label, java.awt.BorderLayout.EAST);
				}

				panel.add(checkBox, java.awt.BorderLayout.CENTER);
				checkBox.setVisible(true);
				checkBox.setSelected(dtn.isSelected());

				checkBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent evt) {
						dtn.setSelected(evt.getStateChange() == ItemEvent.SELECTED);
					}
				});

				final JCheckBox checkBoxf = checkBox;
				dtn.addDisplayNodePropertyChangeListener(new java.beans.PropertyChangeListener() {
					public void propertyChange(java.beans.PropertyChangeEvent event) {
						if (event.getPropertyName().equals("selected")) {
							if (event.getNewValue().toString().equals("true"))
								checkBoxf.setSelected(true);
							else
								checkBoxf.setSelected(false);
						}
					}
				});

				if (isSelected)
					panel.setBackground(selectedColor);
				else
					panel.setBackground(unSelectedColor);

				if (dtn.getToolTipText() != null) {
					panel.setToolTipText("<html>" + dtn.getToolTipText() + "</html>");
					ToolTipManager.sharedInstance().registerComponent(panel);
				}

				return panel;
			}
		}
		return new JPanel();
	}

	public Object getCellEditorValue() {
		return value;
	}

	public void addCellEditorListener(CellEditorListener l) {
	}

	public void removeCellEditorListener(CellEditorListener l) {
	}

	public void cancelCellEditing() {
	}

	public boolean stopCellEditing() {
		return true;
	}

	public boolean isCellEditable(EventObject event) {
		if (event.getSource() instanceof JTree) {
			JTree source = (JTree) event.getSource();
			Object selected = null;
			if (event instanceof java.awt.event.MouseEvent) {
				java.awt.event.MouseEvent evt = (java.awt.event.MouseEvent) event;
				selected = ((JTree) event.getSource()).getClosestPathForLocation(evt.getX(), evt.getY())
						.getLastPathComponent();
			} else {
				selected = ((JTree) event.getSource()).getLastSelectedPathComponent();
			}

			if (selected instanceof DefaultMutableTreeNode) {
				Object o = ((DefaultMutableTreeNode) selected).getUserObject();
				if (o instanceof Display) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean shouldSelectCell(EventObject selectCell) {
		if (selectCell instanceof MouseEvent) {
			MouseEvent e = (MouseEvent) selectCell;

			return e.getID() != MouseEvent.MOUSE_DRAGGED;
		}
		return false;
	}
}
