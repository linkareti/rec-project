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

	public static long getExperimentNumber(final String apparatusName) {
		if (ExpHistoryCounter.expCounter.get(apparatusName) == null) {
			ExpHistoryCounter.expCounter.put(apparatusName, Long.valueOf(1));
		}

		final Long counter = ExpHistoryCounter.expCounter.get(apparatusName);
		final long retorna = counter.longValue();
		ExpHistoryCounter.expCounter.put(apparatusName, Long.valueOf(retorna + 1));

		return retorna;
	}
}
