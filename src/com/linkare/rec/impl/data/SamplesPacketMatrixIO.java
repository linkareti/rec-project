/*
 * SamplesPacketMatrixIO.java
 *
 * Created on 5 de Janeiro de 2004, 17:59
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.SamplesPacket;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author  Administrator
 */
public class SamplesPacketMatrixIO implements Serializable
{
    SequencedObjectIO ioDelegate=null;
    
    public SamplesPacketMatrixIO() throws IOException
    {
	ioDelegate=new SequencedObjectIO();
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
    public void setFile(File file) throws IOException
    {
	ioDelegate.setFile(file);
    }
    
    public SamplesPacket[] getSamplesPackets(int packetStart, int packetEnd)
    throws SamplesPacketReadException
    {
	try
	{
	    Object[] oRead=ioDelegate.readObjects(packetStart,packetEnd);
	    SamplesPacket[] retVal=new SamplesPacket[packetEnd-packetStart+1];
	    System.arraycopy(oRead, 0, retVal, 0, retVal.length);
	    return retVal;
	}
	catch(SequencedObjectReadException e)
	{
	    throw new SamplesPacketReadException(e,e.getErrorIndex());
	}
    }
    
    public void write(SamplesPacket[] packets)
    throws IOException
    {
	ioDelegate.writeObjects(packets);
    }
    
    public void write(Object[] packets)
    throws IOException
    {
	ioDelegate.writeObjects(packets);
    }
    
    public void write(ArrayList packets)
    throws IOException
    {
	ioDelegate.writeObjects(packets.toArray());
    }
    
    public int size()
    {
	return ioDelegate.size();
    }
    
}
