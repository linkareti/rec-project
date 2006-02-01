/*
 * FileObjectOutputStream.java
 *
 * Created on 13 de Janeiro de 2004, 15:40
 */

package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author  Administrator
 */
public class FileObjectOutputStream extends ObjectOutputStream
{
    private File file=null;
    /** Creates a new instance of FileObjectOutputStream */
    public FileObjectOutputStream(File file) throws IOException
    {
	super(new FileOutputStream(file));
	setFile(file);
    }
    
    /** Getter for property file.
     * @return Value of property file.
     *
     */
    public File getFile()
    {
	return file;
    }
    
    /** Setter for property file.
     * @param file New value of property file.
     *
     */
    public void setFile(File file)
    {
	this.file = file;
    }
    
}
