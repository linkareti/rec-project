package com.linkare.rec.impl.newface.component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableColumn;

/**
 * 
 * @author João FLorindo
 */
public class FlatTable extends JTable {

	private static final String uiClassID = "FlatTableUI";
	private TableColumn tc = null;

	public FlatTable() {
		super();

		if (UIManager.get(getUIClassID()) == null) {
			setUI(new BasicTableUI());
		}

		setRowHeight(30);
		getTableHeader().setResizingAllowed(false);
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
