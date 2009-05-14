/*
 * SoundWaves.java
 *
 * Created on November 3, 2005, 12:29 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package pt.utl.ist.elab.driver.doppler.sound;


/**
 *
 * @author andre
 */
public abstract class SoundWaves
{
    //returns a sine wave with a lenght of seconds
    public static byte[] getSineWave(float seconds, byte amplitude, int sampleRate, float frequency)
    {
        byte[] buf = new byte[(int)(seconds * sampleRate)];
        
        if(buf.length % 2 != 0)
            buf = new byte[buf.length + 1];
            
        float theta = 2.0f * (float) Math.PI * frequency / sampleRate;
        for (int i = 0; i < buf.length; i++)
            buf[i] = (byte)(amplitude * (float) Math.sin(i*theta));
        
        return buf;
    }
    
    //returns a sine wave with a lenght of 1 period
    public static byte[] getSineWave(byte amplitude, int sampleRate, float frequency)
    {        
        return getSineWave(1 / frequency, amplitude, sampleRate, frequency);                
    }
    
    public static byte[] getCosWave(float  seconds, byte amplitude, int sampleRate, float frequency)
    {
        byte[] buf = new byte[(int)(seconds * sampleRate)];
        
        if(buf.length % 2 != 0)
            buf = new byte[buf.length + 1];
        
        float theta = 2.0f * (float) Math.PI * frequency / sampleRate;
        for (int i = 0; i < buf.length; i++)
            buf[i] = (byte)(amplitude * (float) Math.cos(i*theta));
        
        return buf;
    }
    
    //returns a cosine wave with a lenght of one period
    public static byte[] getCosWave(byte amplitude, int sampleRate, float frequency)
    {        
        return getCosWave(1/frequency, amplitude, sampleRate, frequency);
    }
    
    public static byte[] getSquareWave(float seconds, byte amplitude, int sampleRate, float frequency)
    {
        byte[] buf = new byte[(int)(seconds * sampleRate)];
        
        if(buf.length % 2 != 0)
            buf = new byte[buf.length + 1];
        
        float p = 2.0f * (float)frequency / sampleRate;
        for (int i = 0; i <buf.length; i++)
            buf[i] = Math.round(i*p) % 2  == 0 ?  (byte)amplitude : (byte)(-1*amplitude);
        
        return buf;
    }
    
    //returns a square wave with a lenght of one period
    public static byte[] getSquareWave(byte amplitude, int sampleRate, float frequency)
    {        
        return getSquareWave(1/frequency, amplitude, sampleRate, frequency);
    }
    
    public static byte[] getTriangleWave(float seconds, byte amplitude, int sampleRate, float frequency)
    {
        byte[] buf = new byte[(int)(seconds * sampleRate)];
        
        if(buf.length % 2 != 0)
            buf = new byte[buf.length + 1];
        
        float p = 2.0f * frequency / sampleRate;
        for (int i = 0; i < buf.length; i++)
        {
            int ip = Math.round(i*p);
            buf[i] = (byte)(2.0f*amplitude*(1 - 2*(ip % 2)) * (i*p - ip));
        }
        
        return buf;
    }
    
    //returns a triangle wave with a lenght of one period
    public static byte[] getTriangleWave(byte amplitude, int sampleRate, float frequency)
    {        
        return getTriangleWave(1/frequency, amplitude, sampleRate, frequency);
    }
    
    public static byte[] getSawtoothWave(float seconds, byte amplitude, int sampleRate, float frequency)
    {
        byte[] buf = new byte[(int)(seconds * sampleRate)];
        
        if(buf.length % 2 != 0)
            buf = new byte[buf.length + 1];
        
        float theta = 2.0f * (float) Math.PI * (float)frequency / sampleRate;
        for (int i = 0; i < buf.length; i++)
            buf[i] = (byte)(amplitude * (float) Math.cos(i*theta));
        
        return buf;
    }
    
    //returns a triangle wave with a lenght of one period
    public static byte[] getSawtoothWave(byte amplitude, int sampleRate, float frequency)
    {        
        return getSawtoothWave(1/frequency, amplitude, sampleRate, frequency);
    }
}
