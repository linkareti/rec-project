package com.linkare.rec.impl.newface.component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPopupMenu;

public class InfoPopup extends JPopupMenu implements PropertyChangeListener {

	private MessagePane messagePane;

	public InfoPopup() {
		setOpaque(false);
		add(messagePane = new MessagePane());
		messagePane.addPropertyChangeListener(this);
	}

	public MessagePane getMessagePane() {
		return messagePane;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt != null) {
			if (evt.getSource() == messagePane) {
				if (MessagePane.CLOSE_ME == evt.getPropertyName()) {
					this.setVisible(false);
				}
			}
		}
	}

}
