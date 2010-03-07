package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class FlatTableCellRenderer extends JLabel implements TableCellRenderer {

	public static final Color SELECTION_ROW_COLOR = new Color(0xF4F3A3);

	// This method is called each time a column header
	// using this renderer needs to be rendered.
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex,
			int vColIndex) {

		// 'value' is column header value of column 'vColIndex'
		// rowIndex is always -1
		// isSelected is always false
		// hasFocus is always false

		// Configure the component with the specified value
		setOpaque(false);

		if (rowIndex == 0) {
			setOpaque(true);
			setBackground(SELECTION_ROW_COLOR);
		}

		this.setText(value.toString());
		
		return this;
	}

	// The following methods override the defaults for performance reasons
	@Override
	public void validate() {
		// noop for performance
	}

	@Override
	public void revalidate() {
		// noop for performance
	}

	@Override
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		// noop for performance
	}

	@Override
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
		// noop for performance
	}
}
