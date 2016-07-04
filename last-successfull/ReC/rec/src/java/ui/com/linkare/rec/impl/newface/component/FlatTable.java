package com.linkare.rec.impl.newface.component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableUI;

/**
 * 
 * @author João FLorindo
 */
public class FlatTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3193305648628424644L;
	private static final String uiClassID = "FlatTableUI";

	public FlatTable() {
		super();

		if (UIManager.get(getUIClassID()) == null) {
			setUI(new BasicTableUI());
		}

		setRowHeight(30);
		getTableHeader().setResizingAllowed(false);
		getTableHeader().setReorderingAllowed(false);

	}

	@Override
	public String getUIClassID() {
		return FlatTable.uiClassID;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
