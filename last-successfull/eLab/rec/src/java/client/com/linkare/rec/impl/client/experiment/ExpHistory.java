/*
 * ExperimentHistory.java
 *
 * Created on 8 de Maio de 2003, 16:38
 */

package com.linkare.rec.impl.client.experiment;

import javax.swing.Icon;

import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ExpHistory {

	private ExpHistoryDisplayFactory expHistoryDisplayFactory = null;
	private DataProducerWrapper producerWrapper = null;
	private final Icon apparatusIcon = null;
	private boolean deleted = false;
	private long expCount = 0;

	/** Utility field used by event firing mechanism. */
	private final javax.swing.event.EventListenerList listenerList = null;

	/** Holds value of property locallyOwned. */
	private boolean locallyOwned = false;

	private String ownerUserName = null;

	private com.linkare.rec.impl.client.apparatus.Apparatus apparatus = null;

	// private com.linkare.rec.impl.baseUI.config.Apparatus apparatusConfig =
	// null;

	/** Creates a new instance of ExperimentHistory 
	 * @param expHistoryDisplayFactory 
	 * @param producerWrapper 
	 * @param apparatus */
	public ExpHistory(final ExpHistoryDisplayFactory expHistoryDisplayFactory,
			final DataProducerWrapper producerWrapper, final com.linkare.rec.impl.client.apparatus.Apparatus apparatus/*
																													 * ,
																													 * com
																													 * .
																													 * linkare
																													 * .
																													 * rec
																													 * .
																													 * impl
																													 * .
																													 * baseUI
																													 * .
																													 * config
																													 * .
																													 * Apparatus
																													 * apparatusConfig
																													 */) {

		this.expHistoryDisplayFactory = expHistoryDisplayFactory;
		this.producerWrapper = producerWrapper;
		expCount = ExpHistoryCounter.getExperimentNumber(apparatus.getHardwareInfo().getHardwareUniqueID());
		this.apparatus = apparatus;
		// this.apparatusConfig = apparatusConfig;
	}

	public DataProducerWrapper getProducerWrapper() {
		return producerWrapper;
	}

	public com.linkare.rec.impl.client.apparatus.Apparatus getApparatus() {
		return apparatus;
	}

	// public com.linkare.rec.impl.baseUI.config.Apparatus getApparatusConfig()
	// {
	// return this.apparatusConfig;
	// }

	public void setDelete() {
		deleted = true;
	}

	public void startExperiment() {
		if (expHistoryDisplayFactory != null) {
			expHistoryDisplayFactory.startExperiment(this);
		}
	}

	public void showExperimentHeader() {
		if (expHistoryDisplayFactory != null) {
			expHistoryDisplayFactory.showExperimentHeader(this);
		}
	}

	public long getExpCount() {
		return expCount;
	}

	/**
	 * Getter for property apparatusID.
	 * 
	 * @return Value of property apparatusID.
	 */
	public String getApparatusID() {
		return apparatus.getHardwareInfo().getHardwareUniqueID();
	}

	/**
	 * Getter for property apparatusName.
	 * 
	 * @return Value of property apparatusName.
	 */
	public String getApparatusName() {
		return apparatus.getHardwareInfo().getFamiliarName();
	}

	// /**
	// * Getter for property apparatusIcon.
	// *
	// * @return Value of property apparatusIcon.
	// */
	// public Icon getApparatusIcon() {
	// return apparatusConfig.getIcon();
	// }

	/**
	 * Getter for property locallyOwned.
	 * 
	 * @return Value of property locallyOwned.
	 */
	public boolean isLocallyOwned() {
		return locallyOwned;
	}

	/**
	 * Setter for property locallyOwned.
	 * 
	 * @param locallyOwned New value of property locallyOwned.
	 */
	public void setLocallyOwned(final boolean locallyOwned) {
		this.locallyOwned = locallyOwned;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(final String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
}
