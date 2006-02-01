/*
 * IndexedPropertyObject.java
 *
 * Created on 14 de Dezembro de 2003, 18:50
 */

package com.linkare.customizer;

import java.beans.*;

/**
 *
 * @author  jp
 */
public class PropertyObject
{
    
    /** Holds value of property value. */
    private Object value;
    
    /** Holds value of property propertyEditor. */
    private PropertyEditor propertyEditor;
    
    /** Holds value of property valueClass. */
    private Class valueClass;
    
    /** Holds value of property readOnly. */
    private boolean editable;
    
    /** Holds value of property name. */
    private String name;
    
    /** Creates a new instance of IndexedPropertyObject */
    public PropertyObject(String name,PropertyEditor propertyEditor, Class valueClass,boolean editable)
    {
	setName(name);
	setPropertyEditor(propertyEditor);
	setValueClass(valueClass);
	setEditable(editable);
    }
    
    /** Creates a new instance of IndexedPropertyObject */
    public PropertyObject(String name,PropertyEditor propertyEditor, Class valueClass, boolean editable,Object value)
    {
	this(name,propertyEditor,valueClass,editable);
	setValue(value);
    }
    
    /** Getter for property value.
     * @return Value of property value.
     *
     */
    public Object getValue()
    {
	return this.value;
    }
    
    /** Setter for property value.
     * @param value New value of property value.
     *
     */
    public void setValue(Object value)
    {
	this.value = value;
	if(this.value==null && getValueClass().isPrimitive())
	{
	    try
	    {
		if(getValueClass()==boolean.class)
		    this.value=new Boolean(false);
		else if(getValueClass()==byte.class)
		    this.value=new Byte((byte)0);
		else if(getValueClass()==char.class)
		    this.value=new Character(' ');
		else if(getValueClass()==short.class)
		    this.value=new Short((short)0);
		else if(getValueClass()==int.class)
		    this.value=new Integer(0);
		else if(getValueClass()==long.class)
		    this.value=new Long(0);
		else if(getValueClass()==float.class)
		    this.value=new Float(0.F);
		else if(getValueClass()==double.class)
		    this.value=new Double(0.);
		
	    }catch(Exception e)
	    {
		javax.swing.JOptionPane.showMessageDialog(null,"Couldn't init value...\n\r"+e.getMessage());
	    }
	}
    }
    
    /** Getter for property propertyEditor.
     * @return Value of property propertyEditor.
     *
     */
    public PropertyEditor getPropertyEditor()
    {
	return this.propertyEditor;
    }
    
    /** Setter for property propertyEditor.
     * @param propertyEditor New value of property propertyEditor.
     *
     */
    public void setPropertyEditor(PropertyEditor propertyEditor)
    {
	this.propertyEditor = propertyEditor;
    }
    
    /** Getter for property valueClass.
     * @return Value of property valueClass.
     *
     */
    public Class getValueClass()
    {
	return this.valueClass;
    }
    
    /** Setter for property valueClass.
     * @param valueClass New value of property valueClass.
     *
     */
    public void setValueClass(Class valueClass)
    {
	this.valueClass = valueClass;
    }
    
    /** Getter for property readOnly.
     * @return Value of property readOnly.
     *
     */
    public boolean isEditable()
    {
	return this.editable;
    }
    
    /** Setter for property readOnly.
     * @param readOnly New value of property readOnly.
     *
     */
    public void setEditable(boolean editable)
    {
	this.editable = editable;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public String getName()
    {
	return this.name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     *
     */
    public void setName(String name)
    {
	this.name = name;
    }
    
}
