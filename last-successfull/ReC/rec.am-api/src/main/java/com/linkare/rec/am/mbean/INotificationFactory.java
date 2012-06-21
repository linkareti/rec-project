package com.linkare.rec.am.mbean;

import javax.management.Notification;

public interface INotificationFactory {

    Notification createNotif(final Object source);

}
