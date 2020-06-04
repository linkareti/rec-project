package com.linkare.irn.nascimento.web.listener;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.primefaces.component.captcha.Captcha;

import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

    @Inject
    private ConfigurationManager configurationManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	sce.getServletContext().setInitParameter(Captcha.PUBLIC_KEY, configurationManager.getCaptchaPublicKey());
	sce.getServletContext().setInitParameter(Captcha.PRIVATE_KEY, configurationManager.getCaptchaPrivateKey());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}