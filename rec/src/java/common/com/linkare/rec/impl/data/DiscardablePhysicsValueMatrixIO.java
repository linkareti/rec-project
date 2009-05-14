/*
 * SamplesPacketMatrixIO.java
 *
 * Created on 5 de Janeiro de 2004, 17:59
 */

package com.linkare.rec.impl.data;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.linkare.rec.data.acquisition.PhysicsValue;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class DiscardablePhysicsValueMatrixIO
{
    IndexedObjectIO ioDelegate=null;
    
    public DiscardablePhysicsValueMatrixIO() throws IOException
    {
	ioDelegate=new IndexedObjectIO();
    }
    /** Getter for property file.
     * @return Value of property file.
     *
     */
    public File getFile() throws IOException
    {
	return ioDelegate.getFile();
    }
    
    /** Setter for property file.
     * @param file New value of property file.
     *
     */
    private void setFile(File file) throws IOException
    {
	ioDelegate.setFile(file);
    }
    
    public PhysicsValue[][] remove(int sampleStart, int sampleEnd)
    throws SamplesReadException
    {
	try
	{
	    Object[] keys=new Object[sampleEnd-sampleStart+1];
	    for(int i=sampleStart;i<=sampleEnd;i++)
	    {
		keys[i-sampleStart]=new Integer(i);
	    }
	    Object[] oRead=ioDelegate.removeObjects(keys);
	
	    PhysicsValue[][] retVal=new PhysicsValue[keys.length][];
	    System.arraycopy(oRead, 0, retVal, 0, retVal.length);
	    return retVal;
	}
	catch(IndexedObjectReadException e)
	{
	    throw new SamplesReadException(e,((Integer)e.getErrorObjectKey()).intValue());
	}
    }
    
    public void write(Map samples)
    throws IOException
    {
	ioDelegate.writeObjects(samples);
    }
    
    public int size()
    {
	return ioDelegate.size();
    }   
    
}
