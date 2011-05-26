package com.linkare.customizer;

import java.beans.PropertyEditor;

/**
 * 
 * @author jp
 */
public class PropertyObject {

	/** Holds value of property value. */
	private Object value;

	/** Holds value of property propertyEditor. */
	private PropertyEditor propertyEditor;

	/** Holds value of property valueClass. */
	private Class<?> valueClass;

	/** Holds value of property readOnly. */
	private boolean editable;

	/** Holds value of property name. */
	private String name;

	/**
	 * Creates a new instance of IndexedPropertyObject
	 * 
	 * @param name The name of the javabean property being edited
	 * @param propertyEditor The property editor associated with that property
	 * @param valueClass the type of the value that should be edited
	 * @param editable Is the property editable?
	 */
	public PropertyObject(final String name, final PropertyEditor propertyEditor, final Class<?> valueClass,
			final boolean editable) {
		setName(name);
		setPropertyEditor(propertyEditor);
		setValueClass(valueClass);
		setEditable(editable);
	}

	/**
	 * Creates a new instance of IndexedPropertyObject
	 * 
	 * @param name The name of the javabean property being edited
	 * @param propertyEditor The property editor associated with that property
	 * @param valueClass the type of the value that should be edited
	 * @param editable Is the property editable?
	 * @param value The current property's value
	 */
	public PropertyObject(final String name, final PropertyEditor propertyEditor, final Class<?> valueClass,
			final boolean editable, final Object value) {
		this(name, propertyEditor, valueClass, editable);
		setValue(value);
	}

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 * 
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */
	public void setValue(final Object value) {
		this.value = value;
		if (this.value == null && getValueClass().isPrimitive()) {
			try {
				if (getValueClass() == boolean.class) {
					this.value = new Boolean(false);
				} else if (getValueClass() == byte.class) {
					this.value = new Byte((byte) 0);
				} else if (getValueClass() == char.class) {
					this.value = new Character(' ');
				} else if (getValueClass() == short.class) {
					this.value = new Short((short) 0);
				} else if (getValueClass() == int.class) {
					this.value = new Integer(0);
				} else if (getValueClass() == long.class) {
					this.value = new Long(0);
				} else if (getValueClass() == float.class) {
					this.value = new Float(0.F);
				} else if (getValueClass() == double.class) {
					this.value = new Double(0.);
				}

			} catch (final Exception e) {
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
		return propertyEditor;
	}

	/**
	 * Setter for property propertyEditor.
	 * 
	 * @param propertyEditor New value of property propertyEditor.
	 * 
	 */
	public void setPropertyEditor(final PropertyEditor propertyEditor) {
		this.propertyEditor = propertyEditor;
	}

	/**
	 * Getter for property valueClass.
	 * 
	 * @return Value of property valueClass.
	 * 
	 */
	public Class<?> getValueClass() {
		return valueClass;
	}

	/**
	 * Setter for property valueClass.
	 * 
	 * @param valueClass New value of property valueClass.
	 * 
	 */
	public void setValueClass(final Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	/**
	 * Getter for property readOnly.
	 * 
	 * @return Value of property readOnly.
	 * 
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Setter for property readOnly.
	 * 
	 * @param editable New value of property editable.
	 * 
	 */
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	/**
	 * Getter for property name.
	 * 
	 * @return Value of property name.
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for property name.
	 * 
	 * @param name New value of property name.
	 * 
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
