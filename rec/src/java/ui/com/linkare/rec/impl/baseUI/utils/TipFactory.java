/*
 * Tips.java
 *
 * Created on July 29, 2004, 6:04 PM
 */

package com.linkare.rec.impl.baseUI.utils;

/**
 *
 * @author André Neto - LEFT - IST
 */

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public class TipFactory {
	public void showTipWithTimeout(final JComponent component, final String tooltipText, final int timeoutHide,
			final Point location) {
		showTipWindow(component, tooltipText, location);
		timerPopup = new javax.swing.Timer(timeoutHide, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				hideTipWindow();
			}
		});
		timerPopup.setRepeats(false);
		timerPopup.start();
	}

	private javax.swing.Timer timerPopup = null;

	public void showTipWindow(final JComponent component, final String tooltipText, final Point location) {
		if (component == null || !component.isShowing()) {
			return;
		}

		Dimension size;
		final Point screenLocation = component.getLocationOnScreen();
		final Rectangle sBounds = component.getBounds();

		// Just to be paranoid
		hideTipWindow();

		final JToolTip tip = component.createToolTip();
		tip.setTipText(tooltipText);
		size = tip.getPreferredSize();

		final Rectangle popupRect = new Rectangle();
		popupRect.setBounds(location.x, location.y, size.width, size.height);

		final PopupFactory popupFactory = PopupFactory.getSharedInstance();
		tipWindow = popupFactory.getPopup(component, tip, location.x, location.y);
		tipWindow.show();

	}

	private Popup tipWindow = null;

	private void hideTipWindow() {
		if (tipWindow != null) {
			tipWindow.hide();
			tipWindow = null;
		}
		if (timerPopup != null) {
			timerPopup.stop();
		}
	}
}
