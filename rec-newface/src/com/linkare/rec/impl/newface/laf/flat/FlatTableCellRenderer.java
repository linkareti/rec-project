package com.linkare.rec.impl.newface.laf.flat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.linkare.rec.impl.newface.laf.flat.elabtheme.DefaultStyle;

import sun.swing.DefaultLookup;


public class FlatTableCellRenderer implements TableCellRenderer {
	// This method is called each time a column header
	// using this renderer needs to be rendered.
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		
		JLabel lbl=new JLabel();
		
		// 'value' is column header value of column 'vColIndex'
		// rowIndex is always -1
		// isSelected is always false
		// hasFocus is always false

		// Configure the component with the specified value
		lbl.setText(value.toString());
		
		// Set tool tip if desired
		lbl.setToolTipText((String)value);
		
		lbl.setFont(lbl.getFont().deriveFont(java.awt.Font.BOLD));
		
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		
		if(rowIndex==-1)
		{
			lbl.setBorder(DefaultStyle.EMPTY_BORDER_MARGIN_4);
		}
		
		
		lbl.setOpaque(isSelected);
		lbl.setBackground(FlatTableUI.SELECTION_ROW_COLOR);
			
		
		//Contemplated on the constructor's FlatTable
//		table.getTableHeader().setResizingAllowed(false);
//		table.getTableHeader().setReorderingAllowed(false);
//		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		switch(vColIndex){
			case 0:	lbl.setHorizontalAlignment(SwingConstants.LEFT);break;
			case 1: lbl.setHorizontalAlignment(SwingConstants.RIGHT);break;
			default: lbl.setHorizontalAlignment(SwingConstants.LEFT);
		}

		return lbl;
	}

	// The following methods override the defaults for performance reasons
	public void validate() {}
	public void revalidate() {}
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}


