/*
 * ExpHistoryCounter.java
 *
 * Created on 28 May 2003, 23:12
 */

package com.linkare.rec.impl.client.experiment;

import java.util.Hashtable;

/**
 * 
 * @author Jose Pedro Pereira
 */
public class ExpHistoryCounter {
	public static Hashtable<String, Long> expCounter = new Hashtable<String, Long>();

	private ExpHistoryCounter() {
	}

	public static long getExperimentNumber(String apparatusName) {
		if (expCounter.get(apparatusName) == null)
			expCounter.put(apparatusName, new Long(1));

		Long counter = (Long) expCounter.get(apparatusName);
		long retorna = counter.longValue();
		expCounter.put(apparatusName, new Long(retorna + 1));

		return retorna;
	}
}
