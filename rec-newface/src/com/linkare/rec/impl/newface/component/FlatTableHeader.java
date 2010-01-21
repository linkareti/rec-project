package com.linkare.rec.impl.newface.component;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * 
 * @author João FLorindo
 */
public class FlatTableHeader extends JTableHeader {

	private String uiClassID = "FlatTableHeaderUI";

	public FlatTableHeader() {
		this(null);
	}

	public FlatTableHeader(TableColumnModel cm) {
		super();

		setFocusable(false);

		if (cm == null)
			cm = createDefaultColumnModel();
		setColumnModel(cm);

		// Initialize local ivars
		initializeLocalVars();

		// Get UI going
		updateUI();
	}

	@Override
	public String getUIClassID() {
		return uiClassID;
	}

}
