/*
 * ExperimentHistoryCellEditorRenderer.java
 *
 * Created on 8 de Maio de 2003, 16:27
 */

package com.linkare.rec.impl.baseUI;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JComponent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ExperimentHistoryRenderer {
	private static String UI_CLIENT_LOGGER = "ReC.baseUI";

	static {
		final Logger l = LogManager.getLogManager().getLogger(ExperimentHistoryRenderer.UI_CLIENT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ExperimentHistoryRenderer.UI_CLIENT_LOGGER));
		}
	}

	public ExperimentHistoryRenderer(final ExpHistoryPanelNew panelExp) {
		this.panelExp = panelExp;
	}

	private ExpHistoryPanelNew panelExp = null;

	public JComponent getJComponent(final ExpHistoryUINode value) {
		return new ExperimentHistoryPanel(panelExp, value);
	}

}
