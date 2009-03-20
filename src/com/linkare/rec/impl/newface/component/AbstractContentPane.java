/* 
 * AbstractContentPane.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Represents the base for all ReC standart panels.
 * 
 * @author Henrique Fernandes
 */
public abstract class AbstractContentPane extends JPanel {

	private static final long serialVersionUID = -4706961491108859138L;

	protected ActionSupport actionSupport;
	
	public AbstractContentPane() {
		super();
		actionSupport = new ActionSupport(this, listenerList);
	}

	/**
	 * Adds an <code>ActionListener</code> to the bean.
	 * @param l the <code>ActionListener</code> to be added
	 */
	public void addActionListener(ActionListener l) {
		actionSupport.addActionListener(l);
	}

	/**
	 * Removes an <code>ActionListener</code> from the bean.
	 * @param l the listener to be removed
	 */
	public void removeActionListener(ActionListener l) {
		actionSupport.removeActionListener(l);
	}

	/**
	 * Returns an array of all the <code>ActionListener</code>s added
	 * to the bean with addActionListener().
	 *
	 * @return all of the <code>ActionListener</code>s added or an empty
	 *         array if no listeners have been added
	 */
	public ActionListener[] getActionListeners() {
		return actionSupport.getActionListeners();
	}

	/**
	 * Notifies all listeners that have registered interest for
	 * notification on this event type.
	 *
	 * @param event  the <code>ActionEvent</code> object
	 */
	protected void fireActionPerformed(ActionEvent event) {
		actionSupport.fireActionPerformed(event);
	}

}
