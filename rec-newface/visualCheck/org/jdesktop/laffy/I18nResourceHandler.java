/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.laffy;

import java.lang.reflect.Field;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * 
 * @author Ken-ichi Kurosaki
 */
public class I18nResourceHandler {
    private static final ResourceBundle rb = ResourceBundle.getBundle("org.jdesktop.laffy.resources.laffy");

    public static ResourceBundle getResourceBundle() {
	return rb;
    }

    public static String getMessage(String key) {
	try {
	    String msg = rb.getString(key);
	    int idx = 0;
	    String mnemonic = null;

	    while ((idx = msg.indexOf('&', idx)) != -1) {
		mnemonic = msg.substring(idx + 1, idx + 2);
		if (!mnemonic.equals(" ")) {
		    String first = msg.substring(0, idx);
		    String last = msg.substring(idx + 1);
		    String remain = first.concat(last);

		    return remain;
		}
		idx++;
	    }
	    return msg;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static void setMnemonic(JComponent comp, String key) {
	try {
	    String msg = rb.getString(key);
	    int idx = 0;
	    char mnemonic;

	    while ((idx = msg.indexOf('&', idx)) != -1) {
		mnemonic = msg.charAt(idx + 1);
		if (Character.isLowerCase(mnemonic) || Character.isUpperCase(mnemonic)) {
		    Class keyEvent = Class.forName("java.awt.event.KeyEvent");
		    Field field = keyEvent.getDeclaredField("VK_" + Character.toUpperCase(mnemonic));
		    int keyCode = field.getInt(null);

		    if (comp instanceof AbstractButton) {
			((AbstractButton) comp).setMnemonic(keyCode);
			((AbstractButton) comp).setDisplayedMnemonicIndex(idx);
		    } else if (comp instanceof JLabel) {
			((JLabel) comp).setDisplayedMnemonic(keyCode);
			((JLabel) comp).setDisplayedMnemonicIndex(idx);
		    }
		    break;
		}
		idx++;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
