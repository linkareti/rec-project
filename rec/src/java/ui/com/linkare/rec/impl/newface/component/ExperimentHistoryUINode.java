package com.linkare.rec.impl.newface.component;

import javax.swing.Icon;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.impl.client.apparatus.Apparatus;
import com.linkare.rec.impl.client.experiment.ExpHistory;
import com.linkare.rec.impl.client.experiment.ExpHistoryDisplayFactory;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * @author Henrique Fernandes
 */
public class ExperimentHistoryUINode extends ExpHistory {

	private final com.linkare.rec.web.config.Apparatus apparatusConfig;

	/**
	 * Creates the <code>ExpHistoryUINode</code>.
	 * 
	 * @param expHistoryDisplayFactory
	 * @param producerWrapper
	 * @param apparatus
	 * @param apparatusConfig
	 */
	public ExperimentHistoryUINode(final ExpHistoryDisplayFactory expHistoryDisplayFactory,
			final DataProducer producer, final Apparatus apparatus,
			final com.linkare.rec.web.config.Apparatus apparatusConfig) {
		super(expHistoryDisplayFactory, producer, apparatus);
		this.apparatusConfig = apparatusConfig;
	}

	public com.linkare.rec.web.config.Apparatus getApparatusConfig() {
		return apparatusConfig;
	}

	/**
	 * Getter for property apparatusIcon.
	 * 
	 * @return Value of property apparatusIcon.
	 */
	public Icon getApparatusIcon() {
		return ReCResourceBundle.findImageIconOrDefault(apparatusConfig.getIconLocationBundleKey(), null);
	}

}
