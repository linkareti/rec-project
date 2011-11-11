/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.repository;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class PhysicsValDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PhysicsValueTypeEnum valueType;
    private Object value;

    public PhysicsValueTypeEnum getValueType() {
	return valueType;
    }

    public void setValueType(PhysicsValueTypeEnum discriminatorValue) {
	this.valueType = discriminatorValue;
    }

    public Object getValue() {
	return value;
    }

    public void setValue(Object value) {
	this.value = value;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("PhysicsValDTO [valueType=");
	builder.append(valueType);
	builder.append(", value=");
	builder.append(value);
	builder.append("]");
	return builder.toString();
    }

}
