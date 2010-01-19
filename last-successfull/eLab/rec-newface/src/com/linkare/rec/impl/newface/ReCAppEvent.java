package com.linkare.rec.impl.newface;

import java.util.EventObject;

public class ReCAppEvent extends EventObject {

	public enum ReCCommand {
		SHOW_LOGIN, SELECTED_APPARATUS_CHANGE, APPARATUS_CONFIGURED, CUSTOMIZER_CANCELED;
	}

	private ReCCommand command;

	public ReCAppEvent(Object source, ReCCommand event) {
		super(source);
		this.command = event;
	}

	/**
	 * @return the event
	 */
	public ReCCommand getCommand() {
		return command;
	}

}
