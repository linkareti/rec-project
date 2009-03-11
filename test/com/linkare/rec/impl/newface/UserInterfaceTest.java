/* 
 * UserInterfaceTest.java created on Mar 10, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface;

import java.util.logging.Logger;

import javax.swing.UIManager;

/**
 * Base class for User Interface Tests.
 * 
 * @author Henrique Fernandes
 */
public abstract class UserInterfaceTest extends ReCLauncher {
    
    private static final Logger log = Logger.getLogger(UserInterfaceTest.class.getName());

//    public static final String GLOBAL_UI_TEST_LAF_CLASSNAME = UIManager.getCrossPlatformLookAndFeelClassName();
//    public static final String GLOBAL_UI_TEST_LAF_CLASSNAME = UIManager.getSystemLookAndFeelClassName();
    public static final String GLOBAL_UI_TEST_LAF_CLASSNAME = null; // FlatLAF
    
    protected String lafClassName;
    
    public UserInterfaceTest() {
	initLafClassName();
    }

    /**
     * Sets the Global LAF for User Interface Testing.
     */
    protected void initLafClassName() {
	lafClassName = GLOBAL_UI_TEST_LAF_CLASSNAME;
    }
    
    @Override
    public void run() {
	log.info("Running User Interface Test for " + this.getClass().getSimpleName());
	super.run(lafClassName);
    }

    /**
     * @return the lafClassName
     */
    public String getLafClassName() {
        return lafClassName;
    }

    /**
     * @param lafClassName the lafClassName to set
     */
    public void setLafClassName(String lafClassName) {
        this.lafClassName = lafClassName;
    }
    
}
