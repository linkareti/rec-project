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
	public void showTipWithTimeout(JComponent component, String tooltipText, int timeoutHide, Point location) {
		showTipWindow(component, tooltipText, location);
		timerPopup = new javax.swing.Timer(timeoutHide, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				hideTipWindow();
			}
		});
		timerPopup.setRepeats(false);
		timerPopup.start();
	}

	private javax.swing.Timer timerPopup = null;

	public void showTipWindow(JComponent component, String tooltipText, Point location) {
		if (component == null || !component.isShowing())
			return;

		Dimension size;
		Point screenLocation = component.getLocationOnScreen();
		Rectangle sBounds = component.getBounds();

		// Just to be paranoid
		hideTipWindow();

		JToolTip tip = component.createToolTip();
		tip.setTipText(tooltipText);
		size = tip.getPreferredSize();

		Rectangle popupRect = new Rectangle();
		popupRect.setBounds(location.x, location.y, size.width, size.height);

		PopupFactory popupFactory = PopupFactory.getSharedInstance();
		tipWindow = popupFactory.getPopup(component, tip, location.x, location.y);
		tipWindow.show();

	}

	private Popup tipWindow = null;

	private void hideTipWindow() {
		if (tipWindow != null) {
			tipWindow.hide();
			tipWindow = null;
		}
		if (timerPopup != null)
			timerPopup.stop();
	}
}
