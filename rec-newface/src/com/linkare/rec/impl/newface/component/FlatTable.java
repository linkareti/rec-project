
package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.linkare.rec.impl.newface.laf.flat.FlatTableCellRenderer;

/**
 * 
 * @author João FLorindo
 */
public class FlatTable extends JTable{

	
	private static final String uiClassID = "FlatTableUI";
	private TableColumn tc = null;
	
    public FlatTable(){
        super();

       if (UIManager.get(getUIClassID()) == null) {
            setUI(new BasicTableUI());
        }
		
       	setRowHeight(30);
	}
	
	public String getUIClassID() {
		return uiClassID ;
	}

	/**
	 * @param tm the TableModel to set
	 */
	public void setTableModel(TableModel tm) {
		if(tm == null)
			return;
		
		setModel(tm);	
		for (int i = 0; i < getColumnCount(); i++) {
			tc = getColumnModel().getColumn(i);
//			tc.setCellRenderer(new FlatTableCellRenderer());
//			tc.setHeaderRenderer(new FlatTableCellRenderer());
		}
	}
	
 
	/**
	 * 
	 * @param data set the Table's Data
	 * @param columnNames set the name of each Column's Table
	 */
	public void setTableModel(Object[][] data, Object[] columnNames) {
		if(data == null || columnNames == null)
			return;
		
		DefaultTableModel model = new DefaultTableModel(data,columnNames);
		setTableModel(model);
	}
}

