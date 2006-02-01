package com.linkare.rec.data.acquisition;

import com.linkare.rec.impl.data.PhysicsValueUtil;

public final class PhysicsValue implements org.omg.CORBA.portable.IDLEntity
{
    //
    // Struct member the_value
    //
    private com.linkare.rec.data.acquisition.PhysicsVal the_value;
    
    //
    // Struct member the_error
    //
    private com.linkare.rec.data.acquisition.PhysicsVal the_error;
    
    //
    // Struct member applied_multiplier
    //
    private com.linkare.rec.data.Multiplier applied_multiplier;
    
    //
    // Default constructor
    //
    public PhysicsValue()
    { }
    
    //
    // Constructor with fields initialization
    // @param	the_value	the_value struct member
    // @param	the_error	the_error struct member
    // @param	applied_multiplier	applied_multiplier struct member
    //
    public PhysicsValue( com.linkare.rec.data.acquisition.PhysicsVal the_value, com.linkare.rec.data.acquisition.PhysicsVal the_error, com.linkare.rec.data.Multiplier applied_multiplier )
    {
	this.the_value = the_value;
	this.the_error = the_error;
	this.applied_multiplier = applied_multiplier;
    }
    
    //
    // Copy Constructor
    //
    public PhysicsValue( PhysicsValue other )
    {
	this.setValue(  new PhysicsVal( other.getValue() )  );
	this.setError(  new PhysicsVal( other.getError() )  );
	this.setAppliedMultiplier(  new com.linkare.rec.data.Multiplier( other.getAppliedMultiplier() )  );
    }
    /** Getter for property the_value.
     * @return Value of property the_value.
     */
    public com.linkare.rec.data.acquisition.PhysicsVal getValue()
    {
	return this.the_value;
    }
    
    /** Setter for property the_value.
     * @param the_value New value of property the_value.
     */
    public void setValue(com.linkare.rec.data.acquisition.PhysicsVal the_value)
    {
	this.the_value = the_value;
    }
    
    /** Getter for property the_error.
     * @return Value of property the_error.
     */
    public com.linkare.rec.data.acquisition.PhysicsVal getError()
    {
	return this.the_error;
    }
    
    /** Setter for property the_error.
     * @param the_error New value of property the_error.
     */
    public void setError(com.linkare.rec.data.acquisition.PhysicsVal the_error)
    {
	this.the_error = the_error;
    }
    
    /** Getter for property applied_multiplier.
     * @return Value of property applied_multiplier.
     */
    public com.linkare.rec.data.Multiplier getAppliedMultiplier()
    {
	return this.applied_multiplier;
    }
    
    /** Setter for property applied_multiplier.
     * @param applied_multiplier New value of property applied_multiplier.
     */
    public void setAppliedMultiplier(com.linkare.rec.data.Multiplier applied_multiplier)
    {
	this.applied_multiplier = applied_multiplier;
    }
    
    /** Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return  a string representation of the object.
     */
      
    public String toString()
    {
	return toScientificNotation();
    }
    
    public double valueToDouble()
    {
	return the_value.toDouble()*applied_multiplier.getExpValue();
    }
    
    public double errorToDouble()
    {
	return the_error.toDouble()*applied_multiplier.getExpValue();
    }
    
    public Number getValueNumber()
    {
	return new Double(the_value.toDouble());
    }
    
    public Number getValuePlusErrorNumber()
    {
	return new Double(the_value.toDouble()+the_error.toDouble());
    }
    
    public Number getValueMinusErrorNumber()
    {
	return new Double(the_value.toDouble()-the_error.toDouble());
    }
    
    public String toScientificNotation()
    {
	return PhysicsValueUtil.toScientificNotation(this);
    }
    
    public String toEngineeringNotation()
    {
	return PhysicsValueUtil.toEngineeringNotation(this);
    }
    
} // class PhysicsValue
