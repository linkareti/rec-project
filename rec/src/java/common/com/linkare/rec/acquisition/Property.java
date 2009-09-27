package com.linkare.rec.acquisition;

import org.omg.CORBA.Any;
import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.impl.utils.ORBBean;

//Version 7.0 Addition
public final class Property implements IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8649369290925778575L;
	private String name = null;
	private Any value = null;

	public Property() {
		setName("");
		createEmptyAny();
	} // ctor

	public Property(String name, Any value) {
		setName(name);
		setValue(value);
	}

	public Property(Property other) {
		setName(new String(other.name));
		setValue(other.getValue());
	}

	/**
	 * Getter for property name.
	 * 
	 * @return Value of property name.
	 * 
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Setter for property name.
	 * 
	 * @param name New value of property name.
	 * 
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 * 
	 */
	public Any getValue() {
		return value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */
	public void setValue(Any value) {
		if (value != null)
			this.value = value;
		else
			createEmptyAny();
	}

	public int hashCode() {
		if (getName() != null)
			return getName().hashCode();
		else
			return 0;
	}

	public String toString() {
		return "[" + getName() + "]=" + getValue();
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof Property))
			return false;
		Property otherProp = (Property) other;
		if (getName() == null && otherProp.getName() == null)
			return true;
		else if (getName() == null)
			return false;
		else
			return getName().equals(otherProp.getName());
	}

	private void createEmptyAny() {
		setValue(ORBBean.getORBBean().getORB().create_any());
	}

} // class Property
