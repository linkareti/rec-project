/*
 * ExpHistoryDisplayFactory.java
 *
 * Created on 9 de Maio de 2003, 11:32
 */

package com.linkare.rec.impl.client.experiment;

/**
 *
 * @author  jp
 */
public interface ExpHistoryDisplayFactory
{
	public void startExperiment(ExpHistory history);
	public void showExperimentHeader(ExpHistory history);
}
