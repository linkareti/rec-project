/*
 * IndexedPropertyObject.java
 *
 * Created on 14 de Dezembro de 2003, 18:50
 */

package com.linkare.editor;

import java.beans.PropertyEditor;

/**
 * 
 * @author jp
 */
public class IndexedPropertyObject {

	/** Holds value of property value. */
	private Object value;

	/** Holds value of property propertyEditor. */
	private PropertyEditor propertyEditor;

	/** Holds value of property valueClass. */
	private Class<?> valueClass;

	/** Creates a new instance of IndexedPropertyObject */
	public IndexedPropertyObject(PropertyEditor propertyEditor, Class<?> valueClass) {
		setPropertyEditor(propertyEditor);
		setValueClass(valueClass);
		setValue(null);
	}

	/** Creates a new instance of IndexedPropertyObject */
	public IndexedPropertyObject(PropertyEditor propertyEditor, Class<?> valueClass, Object value) {
		this(propertyEditor, valueClass);
		setValue(value);
	}

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 * 
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */
	public void setValue(Object value) {
		this.value = value;
		if (this.value == null && getValueClass().isPrimitive()) {
			try {
				if (getValueClass() == boolean.class)
					this.value = new Boolean(false);
				else if (getValueClass() == byte.class)
					this.value = new Byte((byte) 0);
				else if (getValueClass() == char.class)
					this.value = new Character(' ');
				else if (getValueClass() == short.class)
					this.value = new Short((short) 0);
				else if (getValueClass() == int.class)
					this.value = new Integer(0);
				else if (getValueClass() == long.class)
					this.value = new Long(0);
				else if (getValueClass() == float.class)
					this.value = new Float(0.F);
				else if (getValueClass() == double.class)
					this.value = new Double(0.);

			} catch (Exception e) {
				javax.swing.JOptionPane.showMessageDialog(null, "Couldn't init value...\n\r" + e.getMessage());
			}
		}
	}

	/**
	 * Getter for property propertyEditor.
	 * 
	 * @return Value of property propertyEditor.
	 * 
	 */
	public PropertyEditor getPropertyEditor() {
		return this.propertyEditor;
	}

	/**
	 * Setter for property propertyEditor.
	 * 
	 * @param propertyEditor New value of property propertyEditor.
	 * 
	 */
	public void setPropertyEditor(PropertyEditor propertyEditor) {
		this.propertyEditor = propertyEditor;
	}

	/**
	 * Getter for property valueClass.
	 * 
	 * @return Value of property valueClass.
	 * 
	 */
	public Class<?> getValueClass() {
		return this.valueClass;
	}

	/**
	 * Setter for property valueClass.
	 * 
	 * @param valueClass New value of property valueClass.
	 * 
	 */
	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

}
