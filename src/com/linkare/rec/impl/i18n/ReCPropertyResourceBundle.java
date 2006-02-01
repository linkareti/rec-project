/*
 * ReCPropertyResourceBundle.java
 *
 * Created on 22 de Janeiro de 2004, 0:02
 */

package com.linkare.rec.impl.i18n;

import java.util.*;
import java.io.*;
/**
 *
 * @author  jp
 */
public class ReCPropertyResourceBundle extends ReCResourceBundle
{
    public PropertyResourceBundle delegate=null;
    /** Creates a new instance of ReCPropertyResourceBundle */
    public ReCPropertyResourceBundle(InputStream is) throws IOException 
    {
	delegate=new PropertyResourceBundle(is);
    }
    
     // Implements java.util.ResourceBundle.handleGetObject; inherits javadoc specification.
    public Object handleGetObject(String key) {
        return delegate.handleGetObject(key);
    }

    /**
     * Implementation of ResourceBundle.getKeys.
     */
    public Enumeration getKeys() {
	return delegate.getKeys();
    }
}
