/*
 * AbstractIndexedPropertyCustomEditor.java
 *
 * Created on 14 de Dezembro de 2003, 12:38
 */

package com.linkare.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyEditor;
import java.lang.reflect.Array;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultIndexedPropertyCustomEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3169823411394104976L;

	public DefaultIndexedPropertyCustomEditor(final PropertyEditor componentPropertyEditor) {
		super();
		initComponents();
		this.componentPropertyEditor = componentPropertyEditor;
		tblIndexedProperty.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent evt) {
				final int colHit = tblIndexedProperty.getTableHeader().getColumnModel().getColumnIndexAtX(evt.getX());
				tblIndexedProperty.getSelectionModel().clearSelection();
				tblIndexedProperty.getColumnModel().getSelectionModel().clearSelection();
				tblIndexedProperty.getColumnModel().getSelectionModel().addSelectionInterval(colHit, colHit);
				if (tblIndexedProperty.getRowCount() > 0) {
					tblIndexedProperty.getSelectionModel()
							.addSelectionInterval(0, tblIndexedProperty.getRowCount() - 1);
				}
			}
		});

	}

	public DefaultIndexedPropertyCustomEditor(final PropertyEditor componentPropertyEditor,
			final Class<?> baseArrayClassDefined) {
		this(componentPropertyEditor);
		initComponents();
		this.baseArrayClassDefined = baseArrayClassDefined;
	}

	public DefaultIndexedPropertyCustomEditor(final PropertyEditor componentPropertyEditor,
			final Class<?> baseArrayClassDefined, final Object value) {
		this(componentPropertyEditor);
		initComponents();
		this.baseArrayClassDefined = baseArrayClassDefined;
		setValue(value);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		javax.swing.JPanel jPanel1;
		javax.swing.JScrollPane jScrollPane1;

		indexedPropertyObjectTableModel = new com.linkare.editor.IndexedPropertyObjectTableModel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblIndexedProperty = new JTable(indexedPropertyObjectTableModel);
		final TableColumn columnIndexedProperty = tblIndexedProperty.getColumnModel().getColumn(1);
		columnIndexedProperty.setCellEditor(new IndexedPropertyObjectTableCellEditor());
		columnIndexedProperty.setCellRenderer(new IndexedPropertyObjectTableCellRenderer());
		tblIndexedProperty.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(35);
		tblIndexedProperty.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(250);
		jPanel1 = new javax.swing.JPanel();
		btnRemoveRows = new javax.swing.JButton();
		btnAddRows = new javax.swing.JButton();
		txtAddRows = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		btnMoveUp = new javax.swing.JButton();
		btnMoveDown = new javax.swing.JButton();

		indexedPropertyObjectTableModel.addTableModelListener(new javax.swing.event.TableModelListener() {
			@Override
			public void tableChanged(final javax.swing.event.TableModelEvent evt) {
				indexedPropertyObjectTableModelTableChanged(evt);
			}
		});

		setLayout(new java.awt.BorderLayout());

		tblIndexedProperty.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tblIndexedProperty.setColumnSelectionAllowed(true);
		tblIndexedProperty.setRowHeight(25);
		jScrollPane1.setViewportView(tblIndexedProperty);

		add(jScrollPane1, java.awt.BorderLayout.CENTER);

		btnRemoveRows.setText("Remove Selected Rows");
		btnRemoveRows.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnRemoveRowsActionPerformed(evt);
			}
		});

		jPanel1.add(btnRemoveRows);

		btnAddRows.setText("Add Rows:");
		btnAddRows.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnAddRowsActionPerformed(evt);
			}
		});

		jPanel1.add(btnAddRows);

		txtAddRows.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAddRows.setText("1");
		txtAddRows.setMinimumSize(new java.awt.Dimension(50, 20));
		txtAddRows.setPreferredSize(new java.awt.Dimension(50, 20));
		jPanel1.add(txtAddRows);

		add(jPanel1, java.awt.BorderLayout.SOUTH);

		jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

		btnMoveUp.setText("Move Up!");
		btnMoveUp.setMaximumSize(new java.awt.Dimension(102, 26));
		btnMoveUp.setMinimumSize(new java.awt.Dimension(102, 26));
		btnMoveUp.setPreferredSize(new java.awt.Dimension(102, 26));
		btnMoveUp.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnMoveUpActionPerformed(evt);
			}
		});

		jPanel2.add(btnMoveUp);

		btnMoveDown.setText("Move down!");
		btnMoveDown.setMaximumSize(new java.awt.Dimension(102, 26));
		btnMoveDown.setMinimumSize(new java.awt.Dimension(102, 26));
		btnMoveDown.setPreferredSize(new java.awt.Dimension(102, 26));
		btnMoveDown.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnMoveDownActionPerformed(evt);
			}
		});

		jPanel2.add(btnMoveDown);

		add(jPanel2, java.awt.BorderLayout.EAST);

	}// GEN-END:initComponents

	private void btnMoveUpActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnMoveUpActionPerformed
	{// GEN-HEADEREND:event_btnMoveUpActionPerformed
		final DefaultTableModel model = (DefaultTableModel) tblIndexedProperty.getModel();
		final int[] rowsSelected = tblIndexedProperty.getSelectedRows();
		for (int i = 0; i < rowsSelected.length; i++) {
			if (rowsSelected[i] == 0) {
				return;
			}
			model.moveRow(rowsSelected[i], rowsSelected[i], rowsSelected[i] - 1);
			rowsSelected[i] = rowsSelected[i] - 1;
		}
		tblIndexedProperty.getSelectionModel().clearSelection();
		for (final int element : rowsSelected) {
			tblIndexedProperty.getSelectionModel().addSelectionInterval(element, element);
		}
	}// GEN-LAST:event_btnMoveUpActionPerformed

	private void btnMoveDownActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnMoveDownActionPerformed
	{// GEN-HEADEREND:event_btnMoveDownActionPerformed
		final DefaultTableModel model = (DefaultTableModel) tblIndexedProperty.getModel();
		final int[] rowsSelected = tblIndexedProperty.getSelectedRows();
		for (int i = rowsSelected.length - 1; i >= 0; i--) {
			if (rowsSelected[i] == model.getRowCount() - 1) {
				return;
			}
			model.moveRow(rowsSelected[i], rowsSelected[i], rowsSelected[i] + 1);
			rowsSelected[i] = rowsSelected[i] + 1;
		}
		tblIndexedProperty.getSelectionModel().clearSelection();
		for (final int element : rowsSelected) {
			tblIndexedProperty.getSelectionModel().addSelectionInterval(element, element);
		}

	}// GEN-LAST:event_btnMoveDownActionPerformed

	private void indexedPropertyObjectTableModelTableChanged(final javax.swing.event.TableModelEvent evt)// GEN-FIRST:event_indexedPropertyObjectTableModelTableChanged
	{// GEN-HEADEREND:event_indexedPropertyObjectTableModelTableChanged
		final Object oldValue = getValue();

		final TableModel model = tblIndexedProperty.getModel();
		final int rowCount = model.getRowCount();

		value = Array.newInstance(getBaseArrayClass(), rowCount);

		for (int i = 0; i < rowCount; i++) {
			final IndexedPropertyObject ipo = (IndexedPropertyObject) model.getValueAt(i, 1);
			if (ipo.getValue() == null && getBaseArrayClass().isPrimitive()) {
				continue;
			}

			Array.set(value, i, ipo.getValue());
		}

		firePropertyChange("value", oldValue, value);
	}// GEN-LAST:event_indexedPropertyObjectTableModelTableChanged

	private void btnAddRowsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnAddRowsActionPerformed
	{// GEN-HEADEREND:event_btnAddRowsActionPerformed
		int countRowsAdd = 0;
		try {
			countRowsAdd = Integer.parseInt(txtAddRows.getText());
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(this,
					"Please define a positive integer value for the number of rows to add!", "Error adding rows!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (int i = 0; i < countRowsAdd; i++) {
			final IndexedPropertyObject obj = new IndexedPropertyObject(componentPropertyEditor, getBaseArrayClass(),
					null);
			((DefaultTableModel) tblIndexedProperty.getModel()).addRow(new Object[] {
					new Integer(tblIndexedProperty.getRowCount()), obj });
		}

	}// GEN-LAST:event_btnAddRowsActionPerformed

	private void btnRemoveRowsActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_btnRemoveRowsActionPerformed
	{// GEN-HEADEREND:event_btnRemoveRowsActionPerformed
		// int countTotalRows=tblIndexedProperty.getModel().getRowCount();
		final int[] rowsSelected = tblIndexedProperty.getSelectedRows();
		for (int i = rowsSelected.length - 1; i >= 0; i--) {
			((DefaultTableModel) tblIndexedProperty.getModel()).removeRow(rowsSelected[i]);
		}
	}// GEN-LAST:event_btnRemoveRowsActionPerformed

	/**
	 * Getter for property definedBaseClass.
	 * 
	 * @return Value of property definedBaseClass.
	 * 
	 */
	public Class<?> getBaseArrayClass() {
		Class<?> baseArrayClass = null;
		if (baseArrayClassDefined != null) {
			baseArrayClass = baseArrayClassDefined;
		} else if (getValue() != null) {
			baseArrayClass = getValue().getClass().getComponentType();
		} else if (componentPropertyEditor.getValue() != null) {
			baseArrayClass = componentPropertyEditor.getValue().getClass();
		}

		return baseArrayClass;

	}

	/**
	 * Setter for property definedBaseClass.
	 * 
	 * @param baseArrayClassDefined New value of property definedBaseClass.
	 * 
	 */
	public void setBaseArrayClass(final Class<?> baseArrayClassDefined) {
		this.baseArrayClassDefined = baseArrayClassDefined;
	}

	/**
	 * Getter for property componentPropertyEditor.
	 * 
	 * @return Value of property componentPropertyEditor.
	 * 
	 */
	public PropertyEditor getComponentPropertyEditor() {
		return componentPropertyEditor;
	}

	/**
	 * Setter for property componentPropertyEditor.
	 * 
	 * @param componentPropertyEditor New value of property
	 *            componentPropertyEditor.
	 * 
	 */
	public void setComponentPropertyEditor(final PropertyEditor componentPropertyEditor) {
		this.componentPropertyEditor = componentPropertyEditor;
	}

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 * 
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */
	public void setValue(final Object value) {
		final Object oldValue = value;
		this.value = value;

		((DefaultTableModel) tblIndexedProperty.getModel()).setRowCount(0);

		if (value == null) {
			((DefaultTableModel) tblIndexedProperty.getModel()).setRowCount(0);
			return;
		} else {
			final int length = java.lang.reflect.Array.getLength(value);
			for (int i = 0; i < length; i++) {
				final IndexedPropertyObject obj = new IndexedPropertyObject(componentPropertyEditor,
						getBaseArrayClass(), java.lang.reflect.Array.get(value, i));
				((DefaultTableModel) tblIndexedProperty.getModel()).addRow(new Object[] {
						new Integer(tblIndexedProperty.getRowCount()), obj });
			}
		}

		firePropertyChange("value", oldValue, value);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnAddRows;
	private javax.swing.JButton btnMoveDown;
	private javax.swing.JButton btnMoveUp;
	private javax.swing.JButton btnRemoveRows;
	private com.linkare.editor.IndexedPropertyObjectTableModel indexedPropertyObjectTableModel;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JTable tblIndexedProperty;
	private javax.swing.JTextField txtAddRows;
	// End of variables declaration//GEN-END:variables

	/** Holds value of property definedBaseClass. */
	private Class<?> baseArrayClassDefined;

	/** Holds value of property componentPropertyEditor. */
	private PropertyEditor componentPropertyEditor;

	/** Holds value of property value. */
	private Object value;

}
