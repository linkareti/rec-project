/*
 * SoundThread.java
 *
 * Created on 5 de Junho de 2003, 17:26
 */

package pt.utl.ist.elab.driver.Aleatorio2.Hardware;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import pt.utl.ist.elab.driver.Aleatorio2.Utils.Oscilador;
//import java.util.Hashtable;
/**
 *
 * @author Pedro Carvalho - LEFT - IST
 */
public class SoundThread implements Runnable{
    
    private static final int	EXTERNAL_BUFFER_SIZE = 44100;
    private float waveSampleRate = 44100.0F; //sampleRate do audio
    //determina o formato do audio
    private AudioFormat waveFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                waveSampleRate, 16, 2, 4, waveSampleRate, false);
    
    private float waveFrequency1;   //frequency of the audio wave in Hz
    private float waveFrequency2;   //frequency of the audio wave in Hz
    private float waveAmplitude = 1;
    private float waveDuration;    //duration of the audio wave in seconds
    private long length;
    
    private AudioInputStream audiostream = null;
    private SourceDataLine linha = null; 
    
    /** Creates a new instance of SoundThread */
    public SoundThread() {
    }
    
    /**
     *Runs the thread.
     *This is where the actual playing is done
     */
    public void run()
    {
        System.out.println(">>>Starting the sound line!");
        //starts the linha
        linha.start();
        
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
        System.out.println(">>>Reading the sound data!");
        while(nBytesRead != -1)
        {
            try
            {
                nBytesRead = audiostream.read(abData,0,abData.length);
            }catch(IOException e)
            {
                e.printStackTrace();
            }
            if(nBytesRead >= 0)
            {
                linha.write(abData,0,nBytesRead);
            }            
        }
        System.out.println(">>>Draining the sound data!");
        linha.drain();
        //System.out.println(">>>Closing the sound line");
        //linha.close();
        
        //Thread.currentThread().interrupt();
    }
    
    public void newLine()
    {
        System.out.println(">>> Creating new oscilator!");
        try {audiostream = new Oscilador(this.waveFrequency1,this.waveFrequency2,waveAmplitude,length,waveFormat);}
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        DataLine.Info informacao = new DataLine.Info(SourceDataLine.class,waveFormat);
        
        try
        {
            linha = (SourceDataLine) AudioSystem.getLine(informacao);
            linha.open(waveFormat);
        }catch(LineUnavailableException e)
        {
            e.printStackTrace();
            System.exit(1);
        }catch(Exception e)
        {            
            e.printStackTrace();
            System.exit(1);
        }
    }//newLine
    
    public void configure(float waveFrequency1, float waveFrequency2, float waveDuration)
    {
        this.waveFrequency1 = waveFrequency1;
        this.waveFrequency2 = waveFrequency2;
        this.waveDuration = waveDuration;
        
        this.length = Math.round(waveSampleRate * this.waveDuration);   //length of stream in frames
        
        /*try {audiostream = new Oscilador(this.waveFrequency1,this.waveFrequency2,waveAmplitude,length,waveFormat);}
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        DataLine.Info informacao = new DataLine.Info(SourceDataLine.class,waveFormat);
        
        try
        {
            linha = (SourceDataLine) AudioSystem.getLine(informacao);
            linha.open(waveFormat);
        }catch(LineUnavailableException e)
        {
            e.printStackTrace();
            System.exit(1);
        }catch(Exception e)
        {            
            e.printStackTrace();
            System.exit(1);
        }*/
    }//configWave()
    
    /*
    public synchronized void playWave()
    {
        
    }*/
    
    
    
    /**
     *Emergency stop playing
     *(don't know if it works)
     */
    public synchronized void stopWave()
    {
        linha.drain();
        linha.close();
    }
    
    
    /**
     *Setters and getters
     */
    public void setFrequency1(float waveFrequency1)
    {
        this.waveFrequency1 = waveFrequency1;
    }
    public float getFrequency1()
    {
        return waveFrequency1;
    }
    public void setFrequency2(float waveFrequency2)
    {
        this.waveFrequency2 = waveFrequency2;
    }
    public float getFrequency2()
    {
        return waveFrequency2;
    }
    public void setAmplitude(float waveAmplitude)
    {
        this.waveAmplitude = waveAmplitude;
    }
    public float getAmplitude()
    {
        return waveAmplitude;
    }
    
    public void setDuration(float waveDuration)
    {
        this.waveDuration = waveDuration;
    }    
    public float getDuration()
    {
        return waveDuration;
    }
    
    
    public void finalize() throws Throwable
    {
        try {super.finalize();}
        catch (Exception e){throw e.fillInStackTrace();}
    }//finalize
    
    
}
