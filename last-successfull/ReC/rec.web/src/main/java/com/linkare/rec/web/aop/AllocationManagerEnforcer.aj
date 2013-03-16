package com.linkare.rec.web.aop;

import com.linkare.rec.web.auth.UserView;
import com.linkare.rec.web.moodle.SessionHelper;
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
