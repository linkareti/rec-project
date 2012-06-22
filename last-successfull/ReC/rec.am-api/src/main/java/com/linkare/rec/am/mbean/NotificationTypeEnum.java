package com.linkare.rec.am.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;

public enum NotificationTypeEnum implements INotificationFactory {
    HARDWARE_STATE_CHANGE {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    },
    REGISTER_NEW_HARDWARE {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    },
    REGISTER_NEW_CLIENT_MC {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    },
    UNREGISTER_HARDWARE {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    },
    UNREGISTER_CLIENT_MC {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    },
    REGISTER_NEW_CLIENT_HARDWARE {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    },
    UNREGISTER_CLIENT_HARDWARE {

	@Override
	public Notification createNotif(Object source) {
	    return new Notification(getType(), source, 0, System.currentTimeMillis(), name());
	}
    };

    private final String type;

    private NotificationTypeEnum() {
	this.type = "jmx.rec." + name().toLowerCase().replaceAll("_", ".");
    }

    public String getType() {
	return type;
    }

    private static final MBeanNotificationInfo[] MBEAN_NOTIFICATION_INFO;

    private static final Map<String, NotificationTypeEnum> notificationTypeMap;

    static {

	final List<MBeanNotificationInfo> notificationInfos = new ArrayList<MBeanNotificationInfo>(NotificationTypeEnum.values().length);

	for (final NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
	    final MBeanNotificationInfo mBeanNotificationInfo = new MBeanNotificationInfo(new String[] { notificationTypeEnum.getType() },
											  notificationTypeEnum.name(), notificationTypeEnum.name());

	    notificationInfos.add(mBeanNotificationInfo);
	}

	MBEAN_NOTIFICATION_INFO = notificationInfos.toArray(new MBeanNotificationInfo[0]);

	final NotificationTypeEnum[] values = NotificationTypeEnum.values();
	notificationTypeMap = new HashMap<String, NotificationTypeEnum>(values.length);
	for (final NotificationTypeEnum notificationTypeEnum : values) {
	    notificationTypeMap.put(notificationTypeEnum.getType(), notificationTypeEnum);
	}

    }

    public static final MBeanNotificationInfo[] getMBeanNotificationInfo() {
	return MBEAN_NOTIFICATION_INFO;
    }

    public static NotificationTypeEnum fromType(final String type) {
	return notificationTypeMap.get(type);
    }

}
