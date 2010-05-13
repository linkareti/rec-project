package com.linkare.rec.am.web.auth;

import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class LoginProviderFactory {

    public static LoginProvider getLoginProvider(final String domain) {
	if (ConstantUtils.INTERNAL_DOMAIN_NAME.equalsIgnoreCase(domain)) {
	    return new LocalLoginProvider();
	}
	return new MoodleLoginProvider();
    }
}