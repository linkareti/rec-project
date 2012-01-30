/*
 * IndexedPropertyEditor.java
 *
 * Created on 4 de Dezembro de 2003, 1:19
 */

package com.linkare.editor;

import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * 
 * @author jp
 */
public abstract class AbstractIndexedPropertyEditor extends PropertyEditorSupport {
	private PropertyEditor componentPropertyEditor = null;
	private Class<?> baseArrayClassDefined = null;
	DefaultIndexedPropertyCustomEditor customEditor = null;

	/** Creates a new instance of IndexedPropertyEditor */
	protected AbstractIndexedPropertyEditor() {
		super();
	}

	public AbstractIndexedPropertyEditor(final PropertyEditor componentPropertyEditor) {
		super();
		this.componentPropertyEditor = componentPropertyEditor;
		customEditor = new DefaultIndexedPropertyCustomEditor(this.componentPropertyEditor);
	}

	public AbstractIndexedPropertyEditor(final PropertyEditor componentPropertyEditor,
			final Class<?> baseArrayClassDefined) {
		this(componentPropertyEditor);
		this.baseArrayClassDefined = baseArrayClassDefined;
		customEditor.setBaseArrayClass(baseArrayClassDefined);
	}

	/**
	 * Getter for property componentPropertyEditor.
	 * 
	 * @return Value of property componentPropertyEditor.
	 * 
	 */
	public java.beans.PropertyEditor getComponentPropertyEditor() {
		return componentPropertyEditor;
	}

	/**
	 * Getter for property baseArrayClassDefined.
	 * 
	 * @return Value of property baseArrayClassDefined.
	 * 
	 */
	public java.lang.Class<?> getBaseArrayClassDefined() {
		return baseArrayClassDefined;
	}

	@Override
	public String getAsText() {
		componentPropertyEditor.setValue(null);

		if (componentPropertyEditor.getAsText() == null) {
			return null;
		}

		if (getValue() == null) {
			return "null";
		}

		final StringBuffer retVal = new StringBuffer("{");
		final int length = Array.getLength(getValue());

		for (int i = 0; i < length; i++) {
			componentPropertyEditor.setValue(Array.get(getValue(), i));
			retVal.append(escapeIndexedString(componentPropertyEditor.getAsText()));
			if (i < length - 1) {
				retVal.append(",");
			}
		}

		retVal.append("}");

		return retVal.toString();
	}

	@Override
	public void setValue(final Object value) {
		if (value != null) {
			if (value.getClass().isArray()) {
				super.setValue(value);
			} else {
				JOptionPane.showMessageDialog(null,
						"This editor is being set a value that doesn't represent an array!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			super.setValue(value);
		}
	}

	@Override
	public String getJavaInitializationString() {
		componentPropertyEditor.setValue(null);

		if (componentPropertyEditor.getJavaInitializationString() == null) {
			return null;
		}

		if (getValue() == null) {
			return "null";
		}

		final StringBuffer retVal = new StringBuffer("{");
		final int length = Array.getLength(getValue());

		for (int i = 0; i < length; i++) {
			componentPropertyEditor.setValue(Array.get(getValue(), i));
			retVal.append(componentPropertyEditor.getJavaInitializationString());
			if (i < length - 1) {
				retVal.append(",");
			}
		}

		retVal.append("}");

		final Class<?> baseArrayClass = getBaseArrayClass();

		if (baseArrayClass != null) {
			String indexedArrayDeepness = "[]";
			Class<?> c = baseArrayClass;
			while (c.isArray()) {
				c = c.getComponentType();
				indexedArrayDeepness += "[]";
			}

			return "new " + c.getName() + indexedArrayDeepness + retVal.toString();
		}

		throw new IllegalArgumentException(
				"As neither componentPropertyEditor as a non null value neither do I, I can't determine Base Array Class... Please set it explicitly!");

	}

	private Class<?> getBaseArrayClass() throws IllegalArgumentException {
		Class<?> baseArrayClass = null;
		if (baseArrayClassDefined != null) {
			baseArrayClass = baseArrayClassDefined;
		} else if (getValue() != null) {
			baseArrayClass = getValue().getClass().getComponentType();
		} else if (componentPropertyEditor.getValue() != null) {
			baseArrayClass = componentPropertyEditor.getValue().getClass();
		}

		return baseArrayClass;
	}

	@Override
	public void setAsText(String text) throws java.lang.IllegalArgumentException {
		componentPropertyEditor.setValue(null);

		if (componentPropertyEditor.getAsText() == null) {
			throw new IllegalArgumentException(
					"Unable to set indexed value property in String format! Base Editor doesn't support it!");
		}

		if (text == null || text.equals("") || text.equals("null")) {
			setValue(null);
			return;
		}
		text = text.trim();
		if (!text.startsWith("{") || !text.endsWith("}")) {
			throw new IllegalArgumentException(
					"Unable to set indexed value property in String format! Should be in format {value1,value2,value3,...}! Please include the brackets!");
		}

		text = text.substring(1, text.length() - 1);
		final String[] splitValues = splitArrayString(text);

		final Class<?> baseArrayClass = getBaseArrayClass();

		if (baseArrayClass != null) {
			final Object oNewValue = Array.newInstance(baseArrayClass, splitValues.length);
			for (int i = 0; i < splitValues.length; i++) {
				try {
					componentPropertyEditor.setAsText(unEscapeIndexedString(splitValues[i]));
					Array.set(oNewValue, i, componentPropertyEditor.getValue());
				} catch (final IllegalArgumentException e) {
					throw new IllegalArgumentException("Couldn't set Indexed Value at index " + i + " because:\n\r"
							+ e.getMessage());
				} catch (final ArrayIndexOutOfBoundsException e2) {
					throw new IllegalArgumentException("Couldn't set Indexed Value at index " + i + " because:\n\r"
							+ e2.getMessage());
				}
			}

			setValue(oNewValue);
		}

		throw new IllegalArgumentException(
				"As neither componentPropertyEditor as a non null value neither do I, I can't determine Base Array Class... Please set it explicitly!");

	}

	private String escapeIndexedString(final String strIn) {
		if (strIn == null) {
			return "null";
		}
		return strIn.replaceAll(",", "\\\\,");
	}

	private String unEscapeIndexedString(final String strIn) {
		if (strIn == null) {
			return "null";
		}
		return strIn.replaceAll("\\\\,", ",");
	}

	private String[] splitArrayString(String strCSArray) {

		final ArrayList<String> splitStrList = new ArrayList<String>();
		int locatePos = 0;
		while (strCSArray.length() > 0) {
			locatePos = strCSArray.indexOf(',', locatePos);
			if (locatePos == -1) {
				splitStrList.add(strCSArray);
				break;
			} else if (locatePos == 0) {
				splitStrList.add("");
				strCSArray = strCSArray.substring(1);
				locatePos = 0;

			} else {
				if (strCSArray.charAt(locatePos - 1) == '\\') {
					locatePos += 1;
					continue;
				} else {
					splitStrList.add(strCSArray.substring(0, locatePos));
					strCSArray = strCSArray.substring(locatePos + 1);
					locatePos = 0;
				}
			}
		}

		final Object[] splitStrObj = splitStrList.toArray();
		final String[] splitStr = new String[splitStrObj.length];
		System.arraycopy(splitStrObj, 0, splitStr, 0, splitStrObj.length);

		return splitStr;
	}

	@Override
	public boolean isPaintable() {
		// return componentPropertyEditor.isPaintable() &&
		// !componrntPropertyEditor.getAsText()!=null)
		return componentPropertyEditor.isPaintable();
	}

	@Override
	public void paintValue(final java.awt.Graphics g, final java.awt.Rectangle r) {
		if (!isPaintable()) {
			return;
		}

		final FontMetrics fm = g.getFontMetrics();
		// int descent = fm.getDescent();
		final int ypos = (int) (r.y + (r.height + fm.getStringBounds("{", g).getHeight()) / 2.) - fm.getDescent();
		// g.setClip(r.x,r.y,r.width,r.height);
		if (getValue() == null) {
			g.drawString("null", r.x + 1, ypos);
			return;
		}
		final int length = Array.getLength(getValue());
		if (length == 0) {
			g.drawString("{}", r.x + 1, ypos);
			return;
		}
		g.drawString("{", r.x + 1, ypos);
		int leftpos = (int) Math.ceil(fm.getStringBounds("{", g).getWidth()) + 1 + r.x;
		final int commaDiscount = (int) Math.ceil(fm.getStringBounds(",", g).getWidth());
		final int discountRight = (int) Math.ceil(fm.getStringBounds("}", g).getWidth());
		final int widthTotal = r.width + r.x - leftpos - discountRight - (length - 1) * commaDiscount - 2
				* (length - 1);
		final int displaceX = widthTotal / length;

		if (displaceX <= 0) {
			g.drawString("...}", leftpos, ypos);
			return;
		}

		for (int i = 0; i < length; i++) {
			componentPropertyEditor.setValue(Array.get(getValue(), i));
			if (i > 0) {
				g.setClip(r.x, r.y, r.width, r.height);
				g.drawString(",", leftpos + 1, ypos);
				leftpos += commaDiscount + 2;
			}
			final Rectangle rValue = new Rectangle(leftpos, r.y, displaceX, r.height);
			g.setClip(rValue.x, rValue.y, rValue.width, rValue.height);
			componentPropertyEditor.paintValue(g, rValue);
			leftpos += displaceX;
		}
		g.setClip(r.x, r.y, r.width, r.height);
		g.drawString("}", leftpos, ypos);
	}

	@Override
	public boolean supportsCustomEditor() {
		return getCustomEditor() != null;
	}

	@Override
	public java.awt.Component getCustomEditor() {
		customEditor.setValue(getValue());
		customEditor.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				setValue(evt.getNewValue());
			}
		});

		return customEditor;
	}

}
