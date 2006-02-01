/*
 * MyBoundedRangeModel.java
 *
 * Created on 18 de Dezembro de 2003, 22:34
 */

package test;

import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author  jp
 */
public class MyBoundedRangeModel extends DefaultBoundedRangeModel
{
    
    private int step=1;
    
    /** Creates a new instance of MyBoundedRangeModel */
    public MyBoundedRangeModel()
    {
    }
    
    boolean updating=false;
    public void setValue(int value)
    {
	if(updating) return;
	System.out.println("Setting value in model="+value);
	int newValue=value;
	if(step!=0)
	{
	    int division=(int)Math.round((double)(value-getMinimum())/(double)step);
	    newValue=division*step+getMinimum();
	    if(newValue==getValue())
	    {
		if(value<getValue() && getValue()-getStep()>=getMinimum())
		    newValue=getValue()-getStep();
		else if(value>getValue() && getValue()+getStep()<=getMaximum())
		    newValue=getValue()+getStep();
	    }
	}
	
	System.out.println("Setting real value in model to="+newValue);
	updating=true;
	super.setValue(newValue);
	updating=false;
    }
    
    
    public Integer getValueInteger()
    {
	return new Integer(getValue());
    }
    
    /** Getter for property step.
     * @return Value of property step.
     *
     */
    public int getStep()
    {
	return step;
    }
    
    /** Setter for property step.
     * @param step New value of property step.
     *
     */
    public void setStep(int step)
    {
	this.step = step;
    }

  
    
}
