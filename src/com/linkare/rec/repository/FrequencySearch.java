package com.linkare.rec.repository;

import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.data.FrequencyUtil;
import org.omg.CORBA.portable.IDLEntity;

public final class FrequencySearch implements IDLEntity
{
    private Frequency minFrequency = null;
    private Frequency maxFrequency = null;
    
    public FrequencySearch()
    {
    }
    
    public FrequencySearch(Frequency minFrequency, Frequency maxFrequency)
    {
	setMinFrequency(minFrequency);
	setMaxFrequency(maxFrequency);
    }
    
    /** Getter for property minFrequency.
     * @return Value of property minFrequency.
     *
     */
    public Frequency getMinFrequency()
    {
	return minFrequency;
    }
    
    /** Setter for property minFrequency.
     * @param minFrequency New value of property minFrequency.
     *
     */
    public void setMinFrequency(Frequency minFrequency)
    {
	this.minFrequency = minFrequency;
    }
    
    /** Getter for property maxFrequency.
     * @return Value of property maxFrequency.
     *
     */
    public Frequency getMaxFrequency()
    {
	return maxFrequency;
    }
    
    /** Setter for property maxFrequency.
     * @param maxFrequency New value of property maxFrequency.
     *
     */
    public void setMaxFrequency(Frequency maxFrequency)
    {
	this.maxFrequency = maxFrequency;
    }
    
    public boolean isValid(Frequency f)
    {
	Frequency tempMinFrequency=getMinFrequency();
	Frequency tempMaxFrequency=getMaxFrequency();
	if(getMinFrequency()==null)
	    tempMinFrequency=f;
	if(getMaxFrequency()==null)
	    tempMaxFrequency=f;
	
	return (FrequencyUtil.isLessThanOrEqual(f, tempMinFrequency) && FrequencyUtil.isMoreThanOrEqual(f,tempMaxFrequency));
    }
    
} // class FrequencySearch
