package com.linkare.rec.am.aop;

import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.zas.api.Decider;
import com.linkare.zas.aspectj.Enforcer;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public aspect AllocationManagerEnforcer extends Enforcer<UserView> {

    @Override
    public UserView currentSubject() {
	return SessionHelper.getUserView();
    }

    @Override
    public Class<? extends Decider<? extends Object>> defaultDeciderClass() {
	return AllocationManagerDecider.class;
    }
}