/*
 * SerializationHelper.java
 *
 * Created on 28 de Outubro de 2002, 12:36
 */

package com.linkare.rec.impl.utils;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */

import java.io.File;

public class SerializationHelper
{
    
    public static void writeObject(String filename, String dir, Object o) throws java.io.IOException
    {
	File f=new File(dir);
	if(!f.exists())
	    f.mkdirs();
	
	File file=new File(getFileName(filename,dir));
	if(file.exists()) return;
	
		/*XMLEncoder encoder=new XMLEncoder(new FileOutputStream(file));
		encoder.writeObject(o);
		encoder.flush();
		encoder.close();*/
	
	FileObjectOutputStream oos=new FileObjectOutputStream(file);
	oos.writeObject(o);
	oos.flush();
	oos.close();
    }
    
    public static Object readObject(String filename, String dir) throws java.io.IOException,ClassNotFoundException
    {
	File f=new File(dir);
	if(!f.exists())
	    f.mkdirs();
	
		/*XMLDecoder decoder=new XMLDecoder(new FileInputStream(getFileName(filename,dir)));
		Object o=decoder.readObject();
		decoder.close();*/
	
	FileObjectInputStream ois=new FileObjectInputStream(new File(getFileName(filename,dir)));
	Object o=ois.readObject();
	ois.close();

	return o;
    }
    
    private static String getFileName(String filename,String dir)
    {
	return dir+System.getProperty("file.separator")+"DataBuffer_"+filename+".ser";
    }
    
    
}
