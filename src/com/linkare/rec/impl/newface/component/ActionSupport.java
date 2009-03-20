/* 
 * ActionSupport.java created on Mar 19, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.EventListenerList;

/**
 * This is a utility class that can be used to support beans on
 * <code>ActionEvent</code> firing. 
 * <p>
 * You can use an instance of this class as a
 * member field of your bean and delegate various work to it.
 * 
 * @author Henrique Fernandes
 */
public class ActionSupport {

	private static final String ACTION_COMMAND_NOT_DEFINED = "ACTION_COMMAND_NOT_DEFINED";

	/** A list of event listeners for the source component. */
	protected EventListenerList listenerList;

	/** The source bean */
    protected Object source;

    /**
	 * Creates a new <code>ActionSupport</code> with the given source bean.
	 * 
	 * @see ActionSupport#ActionSupport(EventListenerList, Object)
	 * 
	 * @param source
	 *            The source bean.
	 */
	public ActionSupport(Object source) {
		this(source, new EventListenerList());
	}

	/**
	 * Use this constructor if the source bean is a <code>JComponent</code> and
	 * already has a <code>EventListenerList</code>.
	 * 
	 * @param listenerList
	 *            The bean listenerList
	 * @param source
	 *            The source bean.
	 */
	public ActionSupport(Object source, EventListenerList listenerList) {
		super();
		this.source = source;
		this.listenerList = listenerList;
	}

	/**
     * Adds an <code>ActionListener</code> to the bean.
     * @param l the <code>ActionListener</code> to be added
     */
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }
    
    /**
     * Removes an <code>ActionListener</code> from the bean.
     * @param l the listener to be removed
     */
    public void removeActionListener(ActionListener l) {
	    listenerList.remove(ActionListener.class, l);
    }
    
    /**
     * Returns an array of all the <code>ActionListener</code>s added
     * to the bean with addActionListener().
     *
     * @return all of the <code>ActionListener</code>s added or an empty
     *         array if no listeners have been added
     */
    public ActionListener[] getActionListeners() {
        return (ActionListener[])(listenerList.getListeners(
            ActionListener.class));
    }
    
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the <code>event</code> 
     * parameter.
     *
     * @param event  the <code>ActionEvent</code> object
     * @see EventListenerList
     */
    protected void fireActionPerformed(ActionEvent event) {
    	
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                // Lazily create the event:
                if (e == null) {
                      String actionCommand = event.getActionCommand();
                      if(actionCommand == null) {
                         actionCommand = getActionCommand();
                      }
                      e = new ActionEvent(source,
                                          ActionEvent.ACTION_PERFORMED,
                                          actionCommand,
                                          event.getWhen(),
                                          event.getModifiers());
                }
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }          
        }
    }

    /**
     * @return The default action command.
     */
	protected String getActionCommand() {
		return source.toString().length() < 50 ? source.toString() : ACTION_COMMAND_NOT_DEFINED;
	}
}
