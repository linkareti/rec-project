package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


public class FlatTableCellRenderer extends JLabel implements TableCellRenderer {
	// This method is called each time a column header
	// using this renderer needs to be rendered.
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		// 'value' is column header value of column 'vColIndex'
		// rowIndex is always -1
		// isSelected is always false
		// hasFocus is always false

		// Configure the component with the specified value
		setText(value.toString());

		// Set tool tip if desired
		setToolTipText((String)value);
		
		setFont(getFont().deriveFont(java.awt.Font.BOLD));
		
		setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
		
		//Contemplated on the constructor's FlatTable
//		table.getTableHeader().setResizingAllowed(false);
//		table.getTableHeader().setReorderingAllowed(false);
//		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		switch(vColIndex){
			case 0:	setHorizontalAlignment(SwingConstants.LEFT);break;
			case 1: setHorizontalAlignment(SwingConstants.RIGHT);break;
			default: setHorizontalAlignment(SwingConstants.LEFT);
		}

		// Since the renderer is a component, return itself
		return this;
	}

	// The following methods override the defaults for performance reasons
	public void validate() {}
	public void revalidate() {}
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}


