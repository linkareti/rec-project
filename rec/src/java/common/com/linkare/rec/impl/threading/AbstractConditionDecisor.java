/*
 * AbstractConditionDecisor.java
 *
 * Created on 11 May 2003, 12:20
 */

package com.linkare.rec.impl.threading;

/**
 * 
 * @author Jose Pedro Pereira
 */
public abstract class AbstractConditionDecisor implements IConditionDecisor {

	/** Creates a new instance of AbstractConditionDecisor */
	public AbstractConditionDecisor() {
	}

	public void onConditionMetFalse() {
		// silent noop - meaning it doesn't do anything on False
	}

	public void onConditionMetTrue() {
		// silent noop - meaning it doesn't do anything on True
	}

	public void onConditionTimeOut() {
		// silent noop - meaning it doesn't do anything on True
	}

}
