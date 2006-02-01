/*
 * testeIORW.java
 *
 * Created on 5 de Janeiro de 2004, 19:44
 */

package test;

import java.io.*;
/**
 *
 * @author  Administrator
 */
public class testeIORW
{
    
    /** Creates a new instance of testeIORW */
    public testeIORW()
    {
    }
    
    
    public static void main(String[] args)
    {
	try
	{
	    File f=File.createTempFile("TempSamplesPacketMatrix_",".tspm");
	    f.deleteOnExit();
	    InputStream is=new FileInputStream(f);
	    OutputStream os=new FileOutputStream(f);
	    
	    os.write(new byte[]{1,2,3,4,5});
	    byte[] data=new byte[5];
	    is.read(data);
	    System.out.println("data is\n");
	    for(int i=0;i<data.length;i++)
	    {
		System.out.println("data["+i+"]="+data[i]);
	    }
	}catch(Exception e)
	{
	}
    }
}
