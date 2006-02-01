/*
 * IndexedPropertyObjectTableCellRenderer.java
 *
 * Created on 14 de Dezembro de 2003, 18:54
 */

package com.linkare.customizer;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author  jp
 */
public class PropertyObjectTableCellRenderer implements TableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
	return createRendererButton(value, isSelected,hasFocus, table);
    }
    
    public static JButton createRendererButton(Object value,boolean isSelected,boolean hasFocus,JTable table)
    {
	return new JPropertyObjectButton((PropertyObject)value,isSelected,hasFocus,table);
    }
    
}
