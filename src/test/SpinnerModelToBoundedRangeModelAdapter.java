/*
 * SpinnerModelToBoundedRangeModelAdapter.java
 *
 * Created on 18 de Dezembro de 2003, 23:13
 */

package test;

import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author  jp
 */
public class SpinnerModelToBoundedRangeModelAdapter extends SpinnerNumberModel
{
    private MyBoundedRangeModel rangeModelDelegate=null;
    /** Creates a new instance of SpinnerModelToBoundedRangeModelAdapter */
    public SpinnerModelToBoundedRangeModelAdapter()
    {
	setMyBoundedRangeModelDelegate(new MyBoundedRangeModel());
	
    }
    
    public Object getNextValue()
    {
	int max=rangeModelDelegate.getMaximum();
	int value=rangeModelDelegate.getValue();
	int step=rangeModelDelegate.getStep();
	if(step+value<=max)
	{
	    System.out.println("Next Value="+(value+step));
	    return new Integer(step+value);
	}
	else
	    return new Integer(max);
	
	
    }
    
    public Object getPreviousValue()
    {
	
	int min=rangeModelDelegate.getMinimum();
	int value=rangeModelDelegate.getValue();
	int step=rangeModelDelegate.getStep();
	if(value-step>=min)
	{
	    System.out.println("Previous Value="+(value-step));
	    return new Integer(value-step);
	    
	}
	else
	    return new Integer(min);
    }
    
    
    
    public void setValue(Object value)
    {
	if(!updating)
	    rangeModelDelegate.setValue(((Number)value).intValue());
	fireStateChanged();
    }
    
    public Object getValue()
    {
	return new Integer(rangeModelDelegate.getValue());
    }
    /** Getter for property rangeModelDelegate.
     * @return Value of property rangeModelDelegate.
     *
     */
    public MyBoundedRangeModel getMyBoundedRangeModelDelegate()
    {
	return rangeModelDelegate;
    }
    
    /** Setter for property rangeModelDelegate.
     * @param rangeModelDelegate New value of property rangeModelDelegate.
     *
     */
    public void setMyBoundedRangeModelDelegate(MyBoundedRangeModel rangeModelDelegate)
    {
	this.rangeModelDelegate = rangeModelDelegate;
	if(rangeModelDelegate!=null)
	{
	    rangeModelDelegate.addChangeListener(
		new ChangeListener()
		{
		    public void stateChanged(ChangeEvent evt)
		    {
			updateValue();
		    }
		}
	    );
	}
	else
	    setMyBoundedRangeModelDelegate(new MyBoundedRangeModel());
	
	updateValue();
    }
    
    private boolean updating=false;
    private void updateValue()
    {
	updating=true;
	if(!rangeModelDelegate.getValueIsAdjusting())
	    setValue(new Integer(rangeModelDelegate.getValue()));
	updating=false;
    }
    
}
