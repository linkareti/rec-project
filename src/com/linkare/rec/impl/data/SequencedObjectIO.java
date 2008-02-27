/*
 * SamplesPacketMatrixIO.java
 *
 * Created on 5 de Janeiro de 2004, 17:59
 */

package com.linkare.rec.impl.data;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author  Administrator
 */
public class SequencedObjectIO implements Serializable {
    private transient File file;
    private boolean file_dirty=false;
    private boolean isTemporary=false;
    private transient RandomAccessFile raf;
    private transient ObjectOutputStream seqWriteStream;
    private ArrayList<long[]> objectsLocations=null;
    private int currentIndex=0;
    
    public SequencedObjectIO() throws IOException {
        setFile(null);
    }
    public SequencedObjectIO(File f) throws IOException {
        setFile(f);
    }
    public SequencedObjectIO(String path) throws IOException {
        setFile(new File(path));
    }
    
    /** Getter for property file.
     * @return Value of property file.
     *
     */
    public File getFile() throws IOException {
        return file;
    }
    
    /** Setter for property file.
     * @param file New value of property file.
     *
     */
    public void setFile(File f) throws IOException {
        if(file!=null && f!=null && f.equals(file))
            return;
        else {
            
            if(f==null) {
                f=File.createTempFile("TempIndexedObjectIO_",".mser");
                objectsLocations=new ArrayList<long[]>();
                isTemporary=true;
                if(file!=null)
                    file.delete();
            }
            else {
                isTemporary=false;
                if(!f.exists())
                    f.createNewFile();
                if(file!=null) {
                    closeIO();
                    
                    //System.out.println("Renaming file from:" + file.getAbsolutePath() + "to:" + f.getAbsolutePath());
                    
                    FileInputStream fis=new FileInputStream(file);
                    FileOutputStream fos=new FileOutputStream(f);
                    byte[] dataChunk=new byte[1024];
                    int bytesCount=0;
                    while((bytesCount=fis.read(dataChunk))!=-1) {
                        fos.write(dataChunk,0, bytesCount);
                    }
                    fis.close();
                    fos.flush();
                    fos.close();
                    
                    //System.out.println("Renamed and moved contents of file from:" + file.getAbsolutePath() + "to:" + f.getAbsolutePath());
                }
                else
                    objectsLocations=new ArrayList<long []>();
                
            }
            
            this.file=f;
            
            file_dirty=true;
            
            openIO();
        }
    }
    
    
    
    private void closeIO() throws IOException {
        try {
            if(seqWriteStream!=null) {
                seqWriteStream.close();
                seqWriteStream=null;
            }
            if(raf!=null) {
                raf.close();
                raf=null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        //System.out.println("No exception here at closeIO");
    }
    private void openIO() throws IOException {
        if(file_dirty) {
            closeIO();
            //nc
            raf=new RandomAccessFile(getFile(),"rw");
            //*nc
            //raf=new RandomAccessFile(getFile(),"r");
            
            //seqWriteStream=new ObjectOutputStream(new FileOutputStream(getFile()));
            
            file_dirty=false;
        }
    }
    
    public Object[] readObjects(int startIndex,int endIndex)
    throws SequencedObjectReadException {
        Object[] retVal=null;
        if(startIndex<0 || endIndex>=objectsLocations.size()
        || endIndex<startIndex )
            throw new SequencedObjectReadException(new IOException("Unable to read Objects"),Math.min(startIndex,endIndex));
        
        retVal=new Object[endIndex-startIndex+1];
        
        for(int i=startIndex;i<=endIndex;i++) {
            retVal[i-startIndex]=readObject(i);
        }
        
        return retVal;
        
    }
    
    public Object readObject(int index) throws SequencedObjectReadException {
        
        long[] location=(long[])objectsLocations.get(index);
        
        if(location==null)
            throw new SequencedObjectReadException(new IOException("Unable to read Object!"),index);
        
        byte[] data=new byte[(int)(location[1]-location[0]+1)];
        Object retVal=null;
        synchronized(raf) {
            try {
                /*raf.seek(location[0]);
                raf.readFully(data);
                ByteArrayInputStream bais=new ByteArrayInputStream(data);
                ObjectInputStream ois=new ObjectInputStream(bais);
                retVal=ois.readObject();
                ois.close();
                bais.close();*/
                
                raf.seek(location[0]);
                raf.readFully(data);
                ByteArrayInputStream bais=new ByteArrayInputStream(data);
                
                //nc
                BufferedInputStream bis = new BufferedInputStream(bais);
                //*nc
                
                //ObjectInputStream ois=new ObjectInputStream(bais);
                ObjectInputStream ois=new ObjectInputStream(bis);
                retVal=ois.readObject();
                ois.close();
                
                //nc
                bis.close();
                bais.close();
                //*nc
            }
            catch(IOException e) {
                throw new SequencedObjectReadException(e,index);
            }
            catch(ClassNotFoundException e) {
                throw new SequencedObjectReadException(new IOException(e.getMessage()),index);
            }
        }
        
        return retVal;
    }
    
    public void writeObject(Object value)
    throws IOException {
        try {
            synchronized(raf)
            
            {
                /*long startPos=getFile().length();
                seqWriteStream.writeObject(value);
                seqWriteStream.flush();
                long endPos=getFile().length()-1;
                //oos.close();
                objectsLocations.add(new long[]
                {startPos,endPos});
                currentIndex++;*/
                
                long startPos = 0;
                
                if(currentIndex > 0) {
                    long[] location=(long[])objectsLocations.get(currentIndex - 1);
                    startPos = location[1] + 1;
                }
                
                //THE FILE LENGTH WASN'T WORKING IN WINDOWS...
                //long startPos=getFile().length();
                                
                //nc
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                seqWriteStream = new ObjectOutputStream(baos);
                //*nc
                
                seqWriteStream.writeObject(value);
                seqWriteStream.flush();
                
                //nc
                raf.seek(startPos);
                raf.write(baos.toByteArray());
                //*nc
                
                //TESTING
                baos.close();
                seqWriteStream.close();
                //END TESTING
                
                
                //long endPos=getFile().length()-1;
                long endPos=startPos + baos.toByteArray().length-1;
                
                objectsLocations.add(new long[]
                {startPos,endPos});
                currentIndex++;                
            }
        }
        catch(java.io.IOException ioe) {
            /*System.out.println("********************");
            System.out.println("Exception AT indexed Object IO");
            System.out.println("********************");*/
            ioe.printStackTrace();
        }
    }
    
    public void writeObjects(Object[] objects)
    throws IOException {
        try {
            for(int i=0;i<objects.length;i++)
                writeObject(objects[i]);
        }
        catch(Throwable t) {
            //System.out.println("Error at writeObjects in SequencedObjectIO...");
            t.printStackTrace(System.out);
            throw new IOException(t.getMessage());
        }
        
    }
    
    public int size() {
        return objectsLocations==null?0:objectsLocations.size();
    }
    
    public boolean moveToFile(File newFile) throws IOException {
        boolean retVal=getFile().renameTo(newFile);
        if(!retVal) return retVal;
        
        isTemporary=false;
        closeIO();
        openIO();
        return retVal;
    }
    
    
    //private static final long serialVersionUID=1938846425062959110L;
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(getFile());
    }
    
    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException, NotActiveException {
        ois.defaultReadObject();
        
        File old_data_file=(File)ois.readObject();
        
        if(old_data_file!=null && old_data_file.exists()) {
            closeIO();
            this.file=old_data_file;
            raf=new RandomAccessFile(file,"rw");
        }
        
        
    }
    
    protected void finalize() throws Throwable {
        try {
            //System.out.println("Class not need any more  - SequencedObjectIO...");
            closeIO();
            /*System.out.println("file is temporary? "+isTemporary);
            System.out.println("*******************************");
            System.out.println("Where is the file? "+getFile().getAbsolutePath());*/
            if(isTemporary && getFile()!=null) {
                //System.out.println("Deleting File...!");
                getFile().delete();
            }
        }
        catch(Exception e) {
            /*System.out.println("*******************************");
            System.out.println("*******************************");
            System.out.println("GOT AN EXCEPTION WHILE FINALIZING");
            e.printStackTrace();
            System.out.println("*******************************");
            System.out.println("*******************************");*/
        }
    }
    /*
    public Object removeObject(Object key) throws IndexedObjectReadException
    {
        Object retVal=readObject(key);
        objectsLocations.remove(key);
        return retVal;
    }
     
    public Object[] removeObjects(Object[] keys) throws IndexedObjectReadException
    {
        Object[] retVal=readObjects(keys);
        for(int i=0;i<keys.length;i++)
            objectsLocations.remove(keys[i]);
     
        return retVal;
    }
     */
}
