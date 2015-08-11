/* 
 * OneParameterNodeComparator.java created on 19 Aug 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric.translator;

import java.util.Comparator;

import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneParameterNode;

/**
 * 
 * @author npadriano
 */
public class OneParameterNodeOrderComparator implements Comparator<OneParameterNode> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(final OneParameterNode arg0, final OneParameterNode arg1) {
		return arg0.getOrder().compareTo(arg1.getOrder());
	}

}
