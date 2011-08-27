/*
 * SortPotentials.java
 *
 * Created on 1 de Abril de 2005, 1:13
 */

package pt.utl.ist.elab.driver.vquantum;

import java.util.Comparator;

/**
 * 
 * @author nomead
 */
public class SortPotentials implements Comparator<Object> {

	/**
	 * Compara a posicao dos potenciais
	 * 
	 * @param o1 objecto 1
	 * @param o2 objecto 2
	 */
	@Override
	public int compare(final Object o1, final Object o2) {
		final double[] comp1 = (double[]) o1;
		final double[] comp2 = (double[]) o2;

		if (comp1[0] > comp2[0]) {
			return 1;
		} else if (comp1[0] < comp2[0]) {
			return -1;
		} else {
			return 0;
		}
	}

}
