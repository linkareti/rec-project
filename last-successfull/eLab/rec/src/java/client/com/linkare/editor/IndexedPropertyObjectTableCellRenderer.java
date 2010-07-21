/*
 * IndexedPropertyObjectTableCellRenderer.java
 *
 * Created on 14 de Dezembro de 2003, 18:54
 */

package com.linkare.editor;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class IndexedPropertyObjectTableCellRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return createRendererButton(value, isSelected, hasFocus, table);
	}

	public static JButton createRendererButton(Object value, boolean isSelected, boolean hasFocus, JTable table) {
		return new JIndexedPropertyObjectButton((IndexedPropertyObject) value, isSelected, hasFocus, table);
	}
}
