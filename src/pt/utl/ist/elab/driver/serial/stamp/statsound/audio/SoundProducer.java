/*
 * Sound.java
 *
 * Created on September 22, 2003, 11:13 AM
 */

package pt.utl.ist.elab.driver.serial.stamp.statsound.audio;

import javax.media.*;
import javax.media.format.*;
import javax.media.control.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.*;
import java.util.*;
import pt.utl.ist.elab.driver.serial.stamp.statsound.*;
import pt.utl.ist.elab.driver.serial.stamp.statsound.audio.media.protocol.sinewavegenerator.*;
/**
 *
 * @author  andre
 */

public class SoundProducer implements javax.media.ControllerListener, Runnable
{            
    private double freq = 50.0;
    private double lastFreq = 50.0;
    
    private boolean playing = true;
    private javax.media.Player p=null;
    private InterLacedSineWaveStream baseStream = null;
    
    /** Creates a new instance of Sound */
    public SoundProducer() 
    {
        System.out.println("Starting Sound Producer");
        new Thread(this).start();        
    }
    
    public void startPlayingAudioFile(java.io.File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
    	/*
        System.out.println("Playing audio file!");
        playing = true;
        try
        {
            p = null;
            p = javax.media.Manager.createPlayer(file.toURL());			
            
            p.addControllerListener(this);
            
            p.realize();
            if (!waitForState(p.Realized))
            {
                System.out.println("Failed to realize the processor.");
		return;
            }            
            
            p.start();
            if(!waitForState(p.Started))
            {
                System.out.println("Failed to start the processor.");
		return;
            }         
        }
        catch(Exception e)
        {
            System.out.println("Failed playing audio file");
            e.printStackTrace();
        }
        */
    	
    	   	
    	{
			AudioInputStream AIStream = AudioSystem.getAudioInputStream(file);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, AIStream.getFormat());
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open( AIStream.getFormat() );
			line.start();
			byte[] buffer = new byte[16384]; 
			int count;
			while ((count = AIStream.read(buffer)) > 0)
				line.write(buffer, 0, count);
			AIStream.close();
			line.drain();
			line.stop();
			line.close();
			line = null;
    	}
    }
    
    public void startPlayingAudioFile(int waveForm) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if(waveForm==0)
        {
            //startPlayingAudioFile(new java.io.File("/home/elab/java/pink.wav"));
            //startPlayingAudioFile(new java.io.File("/usr/local/ReC6.0/driver/eLab/StatSound/pink.wav"));
        	startPlayingAudioFile(new java.io.File("/home/elab/whitenoise.wav"));
        }
        else if(waveForm==1)
        {
            //startPlayingAudioFile(new java.io.File("/usr/local/ReC6.0/driver/eLab/StatSound/pulse.wav"));
        	startPlayingAudioFile(new java.io.File("/home/elab/whitenoise.wav"));
        }
    }    
        
    public void startPlayingSinWave(double freq)
    {    
        System.out.println("Playing sin wave with freq="+freq);                
        
        playing = true;
        this.freq = freq;
        try
	{
            javax.media.PackageManager.getProtocolPrefixList().add("pt.utl.ist.elab.driver.serial.stamp.statsound.audio");
            javax.media.MediaLocator loc=new javax.media.MediaLocator("sinewavegenerator:");
            DataSource source=(DataSource)javax.media.Manager.createDataSource(loc);
            
            baseStream=(InterLacedSineWaveStream)source.getStreams()[0];
            baseStream.setWaveLeftFreq(freq);
            baseStream.setWaveRightFreq(freq);            
        
            p = null;
            p = javax.media.Manager.createPlayer(source);	                        
                       
            p.addControllerListener(this);                                                
            
            p.realize();
            if (!waitForState(p.Realized))
            {
                System.out.println("Failed to realize the processor.");
		return;
            }            
            
            p.prefetch();
            if (!waitForState(p.Prefetched)) {
                System.out.println("Failed to prefetch the processor.");
                return;
            }
                        
            p.start();
            if (!waitForState(p.Started))
            {
                System.out.println("Failed to start the processor.");
		return;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void stopPlaying()
    {
        if(!playing)
        {
            return;
        }
        System.out.println("Stoping");
        playing = false;        
        if(p!=null)
	{            
            p.stop();
            p.deallocate();
	} 
    }       
           
    public static void main(String args[])
    {       
        new SoundProducer();
    }            
    
    public void setFrequency(double freq)
    {
        lastFreq=this.freq;
        this.freq=freq;
        if(baseStream!=null)
        {
            baseStream.setWaveLeftFreq(freq);
            baseStream.setWaveRightFreq(freq);
        }
    }
    
    public boolean isFreqPlayed()
    {
        if(baseStream!=null)
        {
            return baseStream.isFreqPlayed();
        }
        return false;
    }
        

    public boolean isPlaying()
    {
        return playing;
    }
        
    private Object waitSync = new Object();
    private boolean stateTransitionOK = true;
	
    public void controllerUpdate(javax.media.ControllerEvent evt) 
    {
        if (evt instanceof javax.media.ConfigureCompleteEvent ||
            evt instanceof javax.media.RealizeCompleteEvent ||
            evt instanceof javax.media.PrefetchCompleteEvent ||
            evt instanceof javax.media.StartEvent 
            )
	{
            synchronized (waitSync)
            {
                stateTransitionOK = true;
		waitSync.notifyAll();
            }
	}
	else if (evt instanceof javax.media.ResourceUnavailableEvent)
	{
            synchronized (waitSync)
            {
                stateTransitionOK = false;
		waitSync.notifyAll();
            }
	}
	else if (evt instanceof javax.media.EndOfMediaEvent)
	{
            System.out.println("Reached end of media...");
            playing = false;
            stopPlaying();            
	}
    }
		
    private boolean waitForState(int state)
    {
        synchronized (waitSync)
	{
            try
            {
                while (p.getState() != state && stateTransitionOK)
                {                    
                    waitSync.wait();
                }
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return stateTransitionOK;
    }	                  
    
    public void run() 
    {        
        boolean fileFound=false;
        
        while(true)
        {
            synchronized(this)
            {
                fileFound=false;                
                try
                {                                   
                    double freq=Double.parseDouble(getData(new java.io.File("/tmp/startFreq")));
                    startPlayingSinWave(freq);                    
                    fileFound=true;
                }
                catch(NumberFormatException nfe)
                {
                }

                if(!fileFound)
                {
                    try
                    {                                   
                        int wave=Integer.parseInt(getData(new java.io.File("/tmp/startFile")));
                        startPlayingAudioFile(wave);                    
                        fileFound=true;
                    }
                    catch(NumberFormatException nfe)
                    {                        
                    } catch (UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}                
                }
                
                if(!fileFound)
                {
                    try
                    {                                   
                        double freq=Double.parseDouble(getData(new java.io.File("/tmp/freq")));
                        setFrequency(freq);                    
                        fileFound=true;
                    }
                    catch(NumberFormatException nfe)
                    {
                    }                
                }                
                
                if(!fileFound)
                {
                    try
                    {                                   
                        Integer.parseInt(getData(new java.io.File("/tmp/stop")));
                        stopPlaying();
                        fileFound=true;
                    }
                    catch(NumberFormatException nfe)
                    {
                    }
                }                                
                
                try
                {
                    wait(100);
                }
                catch(InterruptedException ie)
                {
                }
            }
        }
    }
    
    private String getData(java.io.File file) 
    {
        java.io.FileReader fr;
        int c;
        StringBuffer sb = new StringBuffer();;               
        try
        {
            fr=new java.io.FileReader(file);
            while((c=fr.read())!=-1)
            {
                sb.append((char)c);
            }
            fr.close();
            file.delete();
        }
        catch(java.io.FileNotFoundException fnfe)
        {
        }                            
        catch(java.io.IOException ioe)
        {
        }        
        return sb.toString().trim();
    }    
}
