/*
 * ExperimentHistoryCellEditorRenderer.java
 *
 * Created on 8 de Maio de 2003, 16:27
 */

package com.linkare.rec.impl.baseUI;

import javax.swing.JTable;
import javax.swing.JComponent;
import com.linkare.rec.impl.client.experiment.ExpHistory;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;
/**
 *
 * @author  jp
 */
public class ExperimentHistoryRenderer 
{
	private static String UI_CLIENT_LOGGER="ReC.baseUI";

	static
	{
		Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
		}
	}

	public ExperimentHistoryRenderer(ExpHistoryPanelNew panelExp)
	{
	    this.panelExp=panelExp;
	}
	
	private ExpHistoryPanelNew panelExp=null;
	
	public JComponent getJComponent(ExpHistory value)
	{
		return new ExperimentHistoryPanel(panelExp,value);
	}

}
