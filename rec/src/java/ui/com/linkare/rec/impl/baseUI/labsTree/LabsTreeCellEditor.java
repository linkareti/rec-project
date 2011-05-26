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
	private final java.awt.Color unSelectedColor = UIManager.getColor("Tree.textBackground");
	private final java.awt.Color selectedColor = UIManager.getColor("Tree.selectionBorderColor");

	@Override
	public Component getTreeCellEditorComponent(final JTree tree, final Object value, final boolean isSelected,
			final boolean expanded, final boolean leaf, final int row) {
		this.value = value;
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		final Object o = node.getUserObject();

		if (o instanceof DisplayNode) {
			dtn = (DisplayNode) o;

			final JPanel panel = new JPanel(new java.awt.BorderLayout());
			panel.setBackground(UIManager.getColor("Tree.textBackground"));

			final JCheckBox checkBox = new JCheckBox();
			checkBox.setBackground(UIManager.getColor("Tree.textBackground"));

			if (o instanceof Display) {
				if (dtn.getIcon() != null) {
					final JLabel labelIcon = new JLabel();
					labelIcon.setBackground(UIManager.getColor("Tree.textBackground"));
					panel.add(labelIcon, java.awt.BorderLayout.WEST);
					labelIcon.setIcon(dtn.getIcon());
					labelIcon.setEnabled(dtn.isEnabled());
					labelIcon.setOpaque(false);
				}

				if (dtn.getText() != null) {
					final JLabel label = new JLabel();
					label.setFont(new java.awt.Font("Dialog", 0, 12));
					label.setBackground(UIManager.getColor("Tree.textBackground"));
					label.setText(dtn.getText());
					label.setEnabled(dtn.isEnabled());

					final JTree treef = tree;
					final int rowf = row;

					label.addMouseListener(new java.awt.event.MouseAdapter() {
						@Override
						public void mousePressed(final java.awt.event.MouseEvent evt) {
							if (evt.getClickCount() == 2) {
								treef.firePropertyChange("ddc", 0, rowf);
							}
						}
					});

					panel.add(label, java.awt.BorderLayout.EAST);
				}

				panel.add(checkBox, java.awt.BorderLayout.CENTER);
				checkBox.setVisible(true);
				checkBox.setSelected(dtn.isSelected());

				checkBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent evt) {
						dtn.setSelected(evt.getStateChange() == ItemEvent.SELECTED);
					}
				});

				final JCheckBox checkBoxf = checkBox;
				dtn.addDisplayNodePropertyChangeListener(new java.beans.PropertyChangeListener() {
					@Override
					public void propertyChange(final java.beans.PropertyChangeEvent event) {
						if (event.getPropertyName().equals("selected")) {
							if (event.getNewValue().toString().equals("true")) {
								checkBoxf.setSelected(true);
							} else {
								checkBoxf.setSelected(false);
							}
						}
					}
				});

				if (isSelected) {
					panel.setBackground(selectedColor);
				} else {
					panel.setBackground(unSelectedColor);
				}

				if (dtn.getToolTipText() != null) {
					panel.setToolTipText("<html>" + dtn.getToolTipText() + "</html>");
					ToolTipManager.sharedInstance().registerComponent(panel);
				}

				return panel;
			}
		}
		return new JPanel();
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}

	@Override
	public void addCellEditorListener(final CellEditorListener l) {
	}

	@Override
	public void removeCellEditorListener(final CellEditorListener l) {
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public boolean stopCellEditing() {
		return true;
	}

	@Override
	public boolean isCellEditable(final EventObject event) {
		if (event.getSource() instanceof JTree) {
			final JTree source = (JTree) event.getSource();
			Object selected = null;
			if (event instanceof java.awt.event.MouseEvent) {
				final java.awt.event.MouseEvent evt = (java.awt.event.MouseEvent) event;
				selected = ((JTree) event.getSource()).getClosestPathForLocation(evt.getX(), evt.getY())
						.getLastPathComponent();
			} else {
				selected = ((JTree) event.getSource()).getLastSelectedPathComponent();
			}

			if (selected instanceof DefaultMutableTreeNode) {
				final Object o = ((DefaultMutableTreeNode) selected).getUserObject();
				if (o instanceof Display) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean shouldSelectCell(final EventObject selectCell) {
		if (selectCell instanceof MouseEvent) {
			final MouseEvent e = (MouseEvent) selectCell;

			return e.getID() != MouseEvent.MOUSE_DRAGGED;
		}
		return false;
	}
}
