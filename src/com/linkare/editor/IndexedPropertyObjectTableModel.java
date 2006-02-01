/*
 * IndexedPropertyObjectTableModel.java
 *
 * Created on 15 de Dezembro de 2003, 1:31
 */

package com.linkare.editor;

import java.beans.*;

/**
 *
 * @author  jp
 */
public class IndexedPropertyObjectTableModel extends javax.swing.table.DefaultTableModel
{
    
    /** Creates a new instance of IndexedPropertyObjectTableModel */
    public IndexedPropertyObjectTableModel()
    {
	super(new String[]{"Index","Indexed Property"},0);
    }
    
    public boolean isCellEditable(int row, int column)
    {
	if(column==0) return false;
	
	return true;
    }
    
    public Class getColumnClass(int columnIndex)
    {
	if(columnIndex==0)
	    return Integer.class;	
	if(columnIndex==1)
	    return IndexedPropertyObject.class;
	else
	    return Object.class;
    }
    
    public Object getValueAt(int row,int column)
    {
	if(column==0) 
	    return new Integer(row+1);
	else
	    return super.getValueAt(row,column);
    }
    
    
}
