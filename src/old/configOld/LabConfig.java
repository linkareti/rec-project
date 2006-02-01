package old.configOld;

/*
 * LabConfig.java
 *
 * Created on 22 de Maio de 2003, 16:49
 */

/**
 *
 * @author  bruno
 */
public class LabConfig implements Comparable
{
    
    /** Holds value of property nsAddress. */
    private String nsAddress;
    
    /** Holds value of property displayName. */
    private String displayName;
    
    /** Holds value of property order. */
    private int order;
    
    /** Holds value of property webResources. */
    private WebResourceCollection webResources=new WebResourceCollection("Laboratory","recresource:///com/linkare/rec/impl/baseUI/resources/LabIcon16_2.gif","Lab WebResources");
    
    /** Creates a new instance of LabConfig */
    public LabConfig()
    {
    }
    
    /** Getter for property nsAddress.
     * @return Value of property nsAddress.
     */
    public String getNsAddress()
    {
	return this.nsAddress;
    }
    
    /** Setter for property nsAddress.
     * @param nsAddress New value of property nsAddress.
     */
    public void setNsAddress(String nsAddress)
    {
	this.nsAddress = nsAddress;
    }
    
    /** Getter for property displayName.
     * @return Value of property displayName.
     */
    public String getDisplayName()
    {
	return this.displayName;
    }
    
    /** Setter for property displayName.
     * @param displayName New value of property displayName.
     */
    public void setDisplayName(String displayName)
    {
	this.displayName = displayName;
    }
    
    /** Getter for property order.
     * @return Value of property order.
     */
    public int getOrder()
    {
	return this.order;
    }
    
    /** Setter for property order.
     * @param order New value of property order.
     */
    public void setOrder(int order)
    {
	this.order = order;
    }
    
    /** Indexed getter for property webResources.
     * @param index Index of the property.
     * @return Value of the property at <CODE>index</CODE>.
     */
/*    public WebResource getWebResource(int index)
    {
	return this.webResources[index];
    }
    
    /** Getter for property webResources.
     * @return Value of property webResources.
     */
    public WebResourceCollection getWebResources()
    {
	return this.webResources;
    }
    
    /** Indexed setter for property webResources.
     * @param index Index of the property.
     * @param webResources New value of the property at <CODE>index</CODE>.
     */
/*    public void setWebResource(int index, WebResource webResource)
    {
	this.webResources[index] = webResource;
    }
    
    /** Setter for property webResources.
     * @param webResources New value of property webResources.
     */
    public void setWebResources(WebResourceCollection webResources)
    {
	this.webResources = webResources;
    }
    
    /** Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.<p>
     *
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of <i>expression</i>
     * is negative, zero or positive.
     *
     * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)<p>
     *
     * The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.<p>
     *
     * Finally, the implementer must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.<p>
     *
     * It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * @param   o the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     * 		is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object o)
    {
	LabConfig other=(LabConfig)o;
	
	return (getOrder()-other.getOrder());
    }
  
}
