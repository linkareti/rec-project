package com.linkare.rec.repository;

import org.omg.CORBA.portable.IDLEntity;

public final class SamplesNumSearch implements IDLEntity
{
    private VTLong minSamplesNum = null;
    private VTLong maxSamplesNum = null;
    
    public SamplesNumSearch()
    {
    } // ctor
    
    public SamplesNumSearch(VTLong minSamplesNum, VTLong maxSamplesNum)
    {
	setMinSamplesNum(minSamplesNum);
	setMaxSamplesNum(maxSamplesNum);
    }
    
    /** Getter for property minSamplesNum.
     * @return Value of property minSamplesNum.
     *
     */
    public VTLong getMinSamplesNum()
    {
	return minSamplesNum;
    }
    
    /** Setter for property minSamplesNum.
     * @param minSamplesNum New value of property minSamplesNum.
     *
     */
    public void setMinSamplesNum(VTLong minSamplesNum)
    {
	this.minSamplesNum = minSamplesNum;
    }
    
    public int getMinSamplesNumValue()
    {
	return minSamplesNum==null?Integer.MIN_VALUE:minSamplesNum.getValue();
    }
    
    
    /** Setter for property minSamplesNum.
     * @param minSamplesNum New value of property minSamplesNum.
     *
     */
    public void setMinSamplesNum(int minSamplesNum)
    {
	setMinSamplesNum(new VTLong(minSamplesNum));
    }
    
    /** Getter for property maxSamplesNum.
     * @return Value of property maxSamplesNum.
     *
     */
    public VTLong getMaxSamplesNum()
    {
	return maxSamplesNum;
    }
    
    
    public int getMaxSamplesNumValue()
    {
	return maxSamplesNum==null?Integer.MAX_VALUE:maxSamplesNum.getValue();
    }
    
    /** Setter for property maxSamplesNum.
     * @param maxSamplesNum New value of property maxSamplesNum.
     *
     */
    public void setMaxSamplesNum(VTLong maxSamplesNum)
    {
	this.maxSamplesNum = maxSamplesNum;
    }
    
    /** Setter for property maxSamplesNum.
     * @param maxSamplesNum New value of property maxSamplesNum.
     *
     */
    public void setMaxSamplesNum(int maxSamplesNum)
    {
	setMaxSamplesNum(new VTLong(maxSamplesNum));
    }
    
    
    public boolean isValid(int samplesNum)
    {
	return (samplesNum>=getMinSamplesNumValue() && samplesNum<=getMaxSamplesNumValue());
    }
    
    
} // class SamplesNumSearch
