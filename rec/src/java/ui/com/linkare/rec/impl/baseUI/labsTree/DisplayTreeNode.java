/*
 * DisplayTreeNode.java
 *
 * Created on 22 de Janeiro de 2004, 0:54
 */

package com.linkare.rec.impl.baseUI.labsTree;

import javax.swing.Icon;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DisplayTreeNode {
	private Icon icon = null;
	private String text = null;
	private String tooltipText = null;
	private boolean enabled = false;
	private boolean visible = true;

	/** Creates a new instance of DisplayTreeNode */
	public DisplayTreeNode() {
	}

	/**
	 * Getter for property icon.
	 * 
	 * @return Value of property icon.
	 * 
	 */
	public javax.swing.Icon getIcon() {
		return icon;
	}

	/**
	 * Setter for property icon.
	 * 
	 * @param icon New value of property icon.
	 * 
	 */
	public void setIcon(javax.swing.Icon icon) {
		this.icon = icon;
	}

	/**
	 * Getter for property text.
	 * 
	 * @return Value of property text.
	 * 
	 */
	public java.lang.String getText() {
		return text;
	}

	/**
	 * Setter for property text.
	 * 
	 * @param text New value of property text.
	 * 
	 */
	public void setText(java.lang.String text) {
		this.text = text;
	}

	/**
	 * Getter for property tooltipText.
	 * 
	 * @return Value of property tooltipText.
	 * 
	 */
	public java.lang.String getTooltipText() {
		return tooltipText;
	}

	/**
	 * Setter for property tooltipText.
	 * 
	 * @param tooltipText New value of property tooltipText.
	 * 
	 */
	public void setTooltipText(java.lang.String tooltipText) {
		this.tooltipText = tooltipText;
	}

	/**
	 * Getter for property enabled.
	 * 
	 * @return Value of property enabled.
	 * 
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Setter for property enabled.
	 * 
	 * @param enabled New value of property enabled.
	 * 
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void loadIcon(String iconLocationKey) {
		try {
			setIcon(ReCResourceBundle.findImageIcon(iconLocationKey));
		} catch (Exception ignored) {
		}
	}

	public void loadText(String textKey) {
		try {
			setText(ReCResourceBundle.findString(textKey));
		} catch (Exception ignored) {
		}
	}

	public void loadTooltipText(String tooltipTextKey) {
		try {
			setTooltipText(ReCResourceBundle.findString(tooltipTextKey));
		} catch (Exception ignored) {
		}
	}

	/**
	 * Getter for property visible.
	 * 
	 * @return Value of property visible.
	 * 
	 */
	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Setter for property visible.
	 * 
	 * @param visible New value of property visible.
	 * 
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
