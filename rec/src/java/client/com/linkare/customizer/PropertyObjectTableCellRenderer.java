/*
 * IndexedPropertyObjectTableCellRenderer.java
 *
 * Created on 14 de Dezembro de 2003, 18:54
 */

package com.linkare.customizer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PropertyObjectTableCellRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		return PropertyObjectTableCellRenderer.createRendererButton(value, isSelected, hasFocus, table);
	}

	public static JButton createRendererButton(final Object value, final boolean isSelected, final boolean hasFocus,
			final JTable table) {
		return new JPropertyObjectButton((PropertyObject) value, isSelected, hasFocus, table);
	}

}
