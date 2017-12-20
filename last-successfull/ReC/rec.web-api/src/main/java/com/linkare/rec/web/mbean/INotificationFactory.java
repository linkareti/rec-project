package com.linkare.rec.web.mbean;

import javax.management.Notification;

public interface INotificationFactory {

    Notification createNotif(final Object source);

}
