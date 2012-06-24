package com.linkare.rec.am.util;

import com.linkare.rec.am.model.Laboratory;

public class LabJMXConnetionHandler {

    private final Laboratory laboratory;

    private final JMXConnectionHandler jmxConnectionHandler;

    public LabJMXConnetionHandler(Laboratory laboratory, JMXConnectionHandler jmxConnectionHandler) {
	super();
	this.laboratory = laboratory;
	this.jmxConnectionHandler = jmxConnectionHandler;
    }

    public Laboratory getLaboratory() {
	return laboratory;
    }

    public JMXConnectionHandler getJmxConnectionHandler() {
	return jmxConnectionHandler;
    }
}
