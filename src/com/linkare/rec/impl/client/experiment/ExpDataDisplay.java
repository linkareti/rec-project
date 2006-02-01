/*
 * ExpDataDisplay.java
 *
 * Created on 7 de Maio de 2003, 12:44
 */

package com.linkare.rec.impl.client.experiment;

import javax.swing.Icon;
/**
 *
 * @author  jp
 */
public interface ExpDataDisplay
{
	public void setExpDataModel(ExpDataModel model);
	public javax.swing.JComponent getDisplay();
	public String getName();
	public Icon getIcon();
	public javax.swing.JMenuBar getMenuBar();
	public javax.swing.JToolBar getToolBar();
}
