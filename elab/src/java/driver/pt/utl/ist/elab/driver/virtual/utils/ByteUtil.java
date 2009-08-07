/*
 * ByteUtil.java
 *
 * Created on 27 de Fevereiro de 2005, 2:55
 */
package pt.utl.ist.elab.driver.virtual.utils;

import java.nio.ByteBuffer;
/**
 *
 * @author  nomead
 */
public class ByteUtil {
    
    /** Creates a new instance of ByteUtil */
    public ByteUtil() {
    }
    
    public static byte[] floatToByteArray(float value) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        
        buf.putFloat(value);
        return buf.array();
    }
    
    public static byte[] floatArrayToByteArray(float[] values) 
    {
        return getObjectAsByteArray(values);
        /*byte[] toRet = new byte[4*values.length];
        
        for(int i=0; i<values.length; i++) {
            byte[] converted = floatToByteArray(values[i]);
            for(int j=0; j<converted.length; j++) {
                toRet[4*i] = converted[0];
                toRet[4*i + 1] = converted[1];
                toRet[4*i + 2] = converted[2];
                toRet[4*i + 3] = converted[3];
            }
        }
        return toRet;*/
        
    }
    
    public static byte[] getObjectAsByteArray(Object o)
    {
        System.out.println("Converting object as byte array...");          
        byte[] objectAsByteArray = null;
        try
        {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();            
            java.util.zip.GZIPOutputStream gz = new java.util.zip.GZIPOutputStream(baos);
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(gz);           

            oos.writeObject(o);
            gz.finish();
            objectAsByteArray = baos.toByteArray();
            
            oos.close();
            gz.close();
            baos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return objectAsByteArray;
    }
    
    public static Object byteArrayToObject(byte[] p)
    {
        System.out.println("Converting byte array to object...");
        Object obj = null;
        try
        {
            java.io.ByteArrayInputStream braw = new java.io.ByteArrayInputStream(p);
            java.util.zip.GZIPInputStream gzis = new java.util.zip.GZIPInputStream(braw);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(gzis);
            
            obj = ois.readObject();
            
            ois.close();
            gzis.close();
            braw.close();
        }
        catch(java.io.IOException ie)
        {
            ie.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        
        return obj;
    }
    
    public static float byteArrayToFloat(byte[] fvalue) {
        ByteBuffer buf = ByteBuffer.allocate(fvalue.length);
        buf.put(fvalue);
        return buf.getFloat(0);
    }
    
    public static float[] byteArrayToFloatArray(byte[] values) 
    {
        if(values == null)
            return null;
        
        /*float[] toRet = new float[values.length / 4];
        for(int i=0; i<toRet.length; i++) {
            byte[] toConvert = new byte[4];
            toConvert[0] = values[4*i];
            toConvert[1] = values[4*i + 1];
            toConvert[2] = values[4*i + 2];
            toConvert[3] = values[4*i + 3];
            toRet[i] = byteArrayToFloat(toConvert);
        }
        return toRet;*/
        return (float[])byteArrayToObject(values);     
    }
    
    public static void main(String args[])
    {
        float[] test = (float[])byteArrayToObject(getObjectAsByteArray(new float[1458000]));
        System.out.println(test.length);
        
        
    }
}
