/*
 * IndexedPropertyTableCellEditor.java
 *
 * Created on 14 de Dezembro de 2003, 18:55
 */

package com.linkare.customizer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author jp
 */
public class PropertyObjectTableCellEditor extends AbstractCellEditor implements TableCellEditor {
	static final long serialVersionUID = 3067172209892390454L;
	private PropertyObject value = null;

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
			final int row, final int column) {
		if (value instanceof PropertyObject) {
			this.value = (PropertyObject) value;
		} else {
			return null;
		}

		if (this.value.getPropertyEditor() != null) {
			panelEdit = new JPanel(new BorderLayout());
			final JButton btn = PropertyObjectTableCellRenderer.createRendererButton(value, isSelected, true, table);
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent evt) {
					replaceEditingPanel();
				}
			});
			panelEdit.add(btn, BorderLayout.CENTER);
			return panelEdit;
		} else {
			return null;
		}
	}

	@Override
	public boolean shouldSelectCell(final EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			final MouseEvent e = (MouseEvent) anEvent;
			return e.getID() != MouseEvent.MOUSE_DRAGGED;
		}
		return false;
	}

	private JPanel panelEdit = null;

	private void replaceEditingPanel() {
		final JComponent editingComp = createEditingComponent();
		final JButton customEditorButton = createCustomEditorButton();
		if (editingComp != null) {
			panelEdit.remove(0);
			panelEdit.add(editingComp, BorderLayout.CENTER);
			if (customEditorButton != null) {
				panelEdit.add(customEditorButton, BorderLayout.EAST);
			}
			panelEdit.setFocusCycleRoot(true);
			editingComp.requestFocus();

			if (editingComp instanceof JTextField) {
				((JTextField) editingComp).selectAll();
			}

			panelEdit.validate();

		}
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}

	private JComboBox combo = null;
	private JTextField tf = null;

	private JComponent createEditingComponent() {
		tf = null;
		combo = null;
		if (value.getPropertyEditor() != null) {
			value.getPropertyEditor().setValue(value.getValue());

			if (value.getPropertyEditor().getTags() != null) {

				combo = new JComboBox(value.getPropertyEditor().getTags());
				combo.setSelectedItem(value.getPropertyEditor().getAsText());
				combo.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(final ItemEvent evt) {
						comboBoxItemStateChanged(evt);
					}
				});
				return combo;
			} else if (value.getPropertyEditor().getAsText() != null) {
				tf = new JTextField(value.getPropertyEditor().getAsText());
				tf.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent evt) {
						tfActionPerformed(evt);
					}
				});
				/*
				 * tf.addFocusListener( new FocusListener() { public void
				 * focusGained(FocusEvent evt){} public void
				 * focusLost(FocusEvent evt) { tfFocusLost(evt); }
				 * 
				 * } );
				 */
				return tf;
			}
		}

		return null;
	}

	public void comboBoxItemStateChanged(final ItemEvent evt) {
		final JComboBox eventSourceCombo = (JComboBox) evt.getSource();
		final String tag = (String) eventSourceCombo.getSelectedItem();
		try {
			value.getPropertyEditor().setAsText(tag);
			value.setValue(value.getPropertyEditor().getValue());
			fireEditingStopped();
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to change value\n\rReason:" + e.getMessage());
		}
	}

	public void tfActionPerformed(final ActionEvent evt) {
		final JTextField eventSourceTF = (JTextField) evt.getSource();
		final String val = eventSourceTF.getText();
		try {
			value.getPropertyEditor().setAsText(val);
			value.setValue(value.getPropertyEditor().getValue());
			fireEditingStopped();
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to change value\n\rReason:" + e.getMessage());
			eventSourceTF.requestFocus();
		}

	}

	public void tfFocusLost(final FocusEvent evt) {
		/*
		 * JTextField tf=(JTextField)evt.getSource(); String val=tf.getText();
		 * try { this.value.getPropertyEditor().setAsText(val);
		 * this.value.setValue(this.value.getPropertyEditor().getValue());
		 * fireEditingStopped(); } catch(Exception e) { fireEditingCanceled(); }
		 */

	}

	private JButton createCustomEditorButton() {
		if (value != null && value.getPropertyEditor() != null && value.getPropertyEditor().supportsCustomEditor()) {
			return new PropertyCustomEditorButton();
		}
		return null;
	}

	/************************************************************/
	public class PropertyCustomEditorButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 487401167672368488L;

		/** Creates a new instance of IndexedPropertyCustomEditorButton */
		public PropertyCustomEditorButton() {
			super("...");
			setFont(getFont().deriveFont(Font.BOLD));
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent evt) {
					btnClicked();
				}
			});
		}

		private JDialog dialog = null;

		public void btnClicked() {
			final Object oldValue = value.getValue();
			if (value.getPropertyEditor().supportsCustomEditor()) {
				value.getPropertyEditor().setValue(oldValue);
				final Component comp = value.getPropertyEditor().getCustomEditor();

				dialog = new JDialog(new JDialog(), "Property Custom Editing", true);

				final JButton btnOK = new JButton("OK");

				btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent evt) {
						btnOKPressed();
					}
				});

				final JButton btnCancel = new JButton("Cancel");

				btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent evt) {
						btnCancelPressed();
					}
				});

				final JPanel panel = new JPanel();
				panel.add(btnOK);
				panel.add(btnCancel);

				dialog.getContentPane().setLayout(new BorderLayout());
				dialog.getContentPane().add(comp, BorderLayout.CENTER);
				dialog.getContentPane().add(panel, BorderLayout.SOUTH);

				dialog.pack();

				dialog.setVisible(true);
			}
		}

		public void btnOKPressed() {
			dialog.setVisible(false);
			dialog.dispose();
			value.setValue(value.getPropertyEditor().getValue());
			// ((DefaultTableModel)table.getModel()).fireTableDataChanged();
			fireEditingStopped();
		}

		public void btnCancelPressed() {
			dialog.setVisible(false);
			dialog.dispose();
			fireEditingCanceled();
		}

	}

}
