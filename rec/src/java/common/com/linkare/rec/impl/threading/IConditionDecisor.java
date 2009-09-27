/*
 * IConditionDecisor.java
 *
 * Created on 11 May 2003, 12:17
 */

package com.linkare.rec.impl.threading;

/**
 * 
 * @author Jose Pedro Pereira
 */
public interface IConditionDecisor {
	public enum ConditionResult {
		CONDITION_NOT_MET, CONDITION_MET_TRUE, CONDITION_MET_FALSE
	};

	public ConditionResult getConditionResult();

	public void onConditionMetTrue();

	public void onConditionMetFalse();

	public void onConditionTimeOut();
}
