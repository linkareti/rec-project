package com.linkare.rec.web.bean;

import javax.annotation.ManagedBean;
import javax.ejb.Schedule;

@ManagedBean
public class RefreshConfigSchedulerBean {

    //Will run every minute
    @Schedule(minute = "*", persistent = false)
    public void refreshConfigTest() {
        System.out.println("------ refreshConfigTest Acordei -----");
    }
}
