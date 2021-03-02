package com.linkare.rec.web.bean;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class RefreshConfigSchedulerBean {

    //Will run every minute
    @Schedule(minute = "*", hour = "*", persistent = false)
    public void refreshConfigTest() {
        System.out.println("------ refreshConfigTest Acordei -----");
    }
}
