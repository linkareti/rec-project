package com.linkare.rec.impl.newface;

import java.util.EventObject;

public class ReCAppEvent extends EventObject {

	private static final long serialVersionUID = -466417696213838689L;

	public enum ReCCommand {
		SHOW_LOGIN, SELECTED_APPARATUS_CHANGE, CUSTOMIZER_DONE, CUSTOMIZER_CANCELED, ASK_FOR_VLC, EXPERIMENT_HISTORY_ADDED,
		SHOW_EXPERIMENT_HISTORY_HEADER_INFO, SHOW_EXPERIMENT_HISTORY;
	}

	private final ReCCommand command;

	private Object value;

	public ReCAppEvent(Object source, ReCCommand event) {
		this(source, event, null);
	}

	public ReCAppEvent(Object source, ReCCommand event, Object value) {
		super(source);
		this.command = event;
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
