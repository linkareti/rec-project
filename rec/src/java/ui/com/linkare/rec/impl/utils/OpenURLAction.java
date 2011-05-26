/*
 * OpenURLAction.java
 *
 * Created on 19 de Dezembro de 2002, 16:42
 */

package com.linkare.rec.impl.utils;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.Action;

/**
 * 
 * @author bruno
 */
public class OpenURLAction extends javax.swing.AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6874035185430577183L;
	private BasicService bs = null;
	private URL url = null;

	/** Creates a new instance of OpenURLAction */
	public OpenURLAction(final String strName, final String strURLResource) {
		putValue(Action.NAME, strName);

		try {
			url = new URL(strURLResource);
		} catch (final MalformedURLException e) {
			setEnabled(false);
			e.printStackTrace();
			return;
		}

		try {
			bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
		} catch (final UnavailableServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Invoked when an action occurs.
	 */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		if (bs == null || url == null) {
			return;
		}

		if (!bs.isOffline()) {
			bs.showDocument(url);
			// if(bs.showDocument(url))
			// System.out.println("A abrir o URL...");
			// else
			// System.out.println("Nao foi possivel abrir o URL.");
		}
		// else {
		// //
		// System.out.println("Esta off-line... Nao e possivel abrir o URL.");
		// }
	}

}
