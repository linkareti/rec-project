/*
 * IndexedPropertyObjectTableModel.java
 *
 * Created on 15 de Dezembro de 2003, 1:31
 */

package com.linkare.customizer;

/**
 * 
 * @author jp
 */
public class PropertyObjectTableModel extends
		javax.swing.table.DefaultTableModel {
	static final long serialVersionUID = -8067094998233082207L;

	/** Creates a new instance of IndexedPropertyObjectTableModel */
	public PropertyObjectTableModel() {
		super(new String[] { "Name", "Value" }, 0);
	}

	public boolean isCellEditable(int row, int column) {
		if (column == 0)
			return false;
		else if (column == 1)
			return ((PropertyObject) getValueAt(row, column)).isEditable();
		return true;
	}

	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return String.class;
		if (columnIndex == 1)
			return PropertyObject.class;
		else
			return Object.class;
	}

}
