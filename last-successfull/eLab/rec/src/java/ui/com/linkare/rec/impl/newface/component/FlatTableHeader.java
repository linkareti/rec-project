package com.linkare.rec.impl.newface.component;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * @deprecated
 * @author João FLorindo
 */
@Deprecated
public class FlatTableHeader extends JTableHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3074273916069416941L;
	private final String uiClassID = "FlatTableHeaderUI";

	public FlatTableHeader() {
		this(null);
	}

	public FlatTableHeader(final TableColumnModel cm) {
		// João: Este construtor não devia invocar o super(cm) ?
	}

	@Override
	public String getUIClassID() {
		return uiClassID;
	}

}