package com.linkare.rec.impl.newface;

import java.util.EventObject;

public class ReCAppEvent extends EventObject {

	private static final long serialVersionUID = -466417696213838689L;

	public enum ReCCommand {
		SHOW_LOGIN, SELECTED_APPARATUS_CHANGE, CUSTOMIZER_DONE, CUSTOMIZER_CANCELED, ASK_FOR_VLC, EXPERIMENT_HISTORY_ADDED, SHOW_EXPERIMENT_HISTORY_HEADER_INFO, SHOW_EXPERIMENT_HISTORY;
	}

	private final ReCCommand command;

	private final Object value;

	public ReCAppEvent(final Object source, final ReCCommand event) {
		this(source, event, null);
	}

	public ReCAppEvent(final Object source, final ReCCommand event, final Object value) {
		super(source);
		command = event;
		this.value = value;
	}

	/**
	 * @return the event
	 */
	public ReCCommand getCommand() {
		return command;
	}

	public Object getValue() {
		return value;
	}

}
