/*
 * FileObjectOutputStream.java
 *
 * Created on 13 de Janeiro de 2004, 15:40
 */

package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class FileObjectInputStream extends ObjectInputStream
{
    private File file=null;
    /** Creates a new instance of FileObjectOutputStream */
    public FileObjectInputStream(File file) throws IOException
    {
	super(new FileInputStream(file));
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
