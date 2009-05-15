/*
 * ReCListResourceBundle.java
 *
 * Created on 22 de Janeiro de 2004, 0:15
 */

package com.linkare.rec.impl.i18n;

import java.util.Enumeration;
import java.util.ListResourceBundle;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCListResourceBundle extends ReCResourceBundle
{
    private ListResourceBundle delegate=null;
    /** Creates a new instance of ReCListResourceBundle */
    public ReCListResourceBundle(ListResourceBundle delegate)
    {
	this.delegate=delegate;
    }
     
    public final Object handleGetObject(String key) {
        return delegate.handleGetObject(key); 
    }

    /**
     * Implementation of ResourceBundle.getKeys.
     */
    public Enumeration<String> getKeys() {
        return delegate.getKeys(); 
    }
}
