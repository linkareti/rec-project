package com.linkare.rec.impl.newface;

import java.util.EventObject;

public class ReCAppEvent extends EventObject {

	private static final long serialVersionUID = -466417696213838689L;

	public enum ReCCommand {
		SHOW_LOGIN, SELECTED_APPARATUS_CHANGE, APPARATUS_CONFIGURED, CUSTOMIZER_CANCELED;
	}

	private final ReCCommand command;

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
