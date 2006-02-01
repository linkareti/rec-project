package old.configOld;

/*
 * DisplayConfig.java
 *
 * Created on 22 de Maio de 2003, 16:50
 */

/**
 *
 * @author  bruno
 */
public class Display implements Comparable
{
    
    /** Holds value of property href. */
    private String bean;
    
    /** Holds value of property order. */
    private int order;
    
    
    /** Creates a new instance of DisplayConfig */
    public Display()
    {
	super();
    }
    
    /** Getter for property href.
     * @return Value of property href.
     */
    public String getBean()
    {
	return this.bean;
    }
    
    /** Setter for property href.
     * @param href New value of property href.
     */
    public void setBean(String bean)
    {
	this.bean = bean;
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
    
    public int compareTo(Object o)
    {
	Display other=(Display)o;
	
	return (getOrder()-other.getOrder());
    }
    
}
