package com.linkare.rec.impl.client.experiment;

import javax.swing.Icon;

import com.linkare.rec.impl.client.apparatus.Apparatus;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * @author Henrique Fernandes
 */
public class ExperimentHistory extends ExpHistory {

    protected com.linkare.rec.impl.newface.config.Apparatus newApparatusConfig;

    /** Creates a new instance of ExperimentHistory */
    public ExperimentHistory(ExpHistoryDisplayFactory expHistoryDisplayFactory, DataProducerWrapper producerWrapper,
	    Apparatus apparatus, com.linkare.rec.impl.newface.config.Apparatus apparatusConfig) {
	super(expHistoryDisplayFactory, producerWrapper, apparatus, null);
	this.newApparatusConfig = apparatusConfig;
    }

    public com.linkare.rec.impl.newface.config.Apparatus getNewApparatusConfig() {
	return this.newApparatusConfig;
    }

    public Icon getApparatusIcon() {
	return newApparatusConfig.getIcon();
    }

}
