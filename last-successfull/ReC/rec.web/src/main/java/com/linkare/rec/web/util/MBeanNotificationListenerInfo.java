package com.linkare.rec.web.util;

import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

public class MBeanNotificationListenerInfo {
    
    private final NotificationListener listener;
    private final ObjectName objectName;
    private final NotificationFilter filter;


    public MBeanNotificationListenerInfo(final NotificationListener listener, final ObjectName objectName, final NotificationFilter filter) {
	this.listener = listener;
	this.objectName = objectName;
	this.filter = filter;
    }

    public ObjectName getObjectName() {
	return objectName;
    }

    public NotificationFilter getFilter() {
	return filter;
    }

    public NotificationListener getListener() {
	return listener;
    }

}
