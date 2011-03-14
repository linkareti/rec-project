package audio.media.protocol.sinewavegenerator;

import javax.media.Buffer;
import javax.media.Control;
import javax.media.Format;
import javax.media.format.AudioFormat;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushBufferStream;

public class InterLacedSineWaveStream implements PushBufferStream, Runnable
{
    
    protected ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW);
    protected int maxDataLength;
    private float freq_out=44100.0f;
    private int bits_per_channel=16;
    private int num_channels=2;
    protected AudioFormat audioFormat;
    protected boolean started;
    protected Thread thread;
    protected BufferTransferHandler transferHandler;
    protected Control [] controls = new Control[0];
    
    public InterLacedSineWaveStream()
    {
	// audio data
	audioFormat = new AudioFormat(	AudioFormat.LINEAR,
	freq_out,
	bits_per_channel,
	num_channels,
	AudioFormat.LITTLE_ENDIAN,
	AudioFormat.SIGNED,
	bits_per_channel*num_channels,
	freq_out,
	Format.byteArray);
	
	maxDataLength = (int)freq_out*num_channels*bits_per_channel/8;
	max_amplitude=(double)Math.pow(2.,(double)(bits_per_channel-1))-1;
	
	thread = new Thread(this);
    }
    
    /***************************************************************************
     * SourceStream
     ***************************************************************************/
    
    
    public ContentDescriptor getContentDescriptor()
    {
	return cd;
    }
    
    public long getContentLength()
    {
	return LENGTH_UNKNOWN;
    }
    
    public boolean endOfStream()
    {
	return false;
    }
    
    /***************************************************************************
     * PushBufferStream
     ***************************************************************************/
    
    private double wave_left_freq= 220.0f;
    private double wave_right_freq= 440.0f;
    
    private double wave_left_shift= 0;
    private double wave_right_shift= 0;
    
    private double max_amplitude=0;
    
    
    public void setWaveLeftFreq(double wave_left_freq)
    {
	this.wave_left_freq=wave_left_freq;
	this.wave_left_shift=0;
	//this.wave_right_shift=0;
    }
    
    public double getWaveLeftFreq()
    {
	return this.wave_left_freq;
    }
    
    
    public void setWaveRightFreq(double wave_right_freq)
    {
	this.wave_right_freq=wave_right_freq;
	//this.wave_left_shift=0;
	this.wave_right_shift=0;
    }
    
    public double getWaveRightFreq()
    {
	return	this.wave_right_freq;
    }
    
    public Format getFormat()
    {
	return audioFormat;
    }
    
    public void setTransferHandler(BufferTransferHandler transferHandler)
    {
	synchronized (this)
	{
	    this.transferHandler = transferHandler;
	    notifyAll();
	}
    }
    
    void start(boolean started)
    {
	synchronized ( this )
	{
	    this.started = started;
	    if (started && !thread.isAlive())
	    {
		thread = new Thread(this);
		thread.start();
	    }
	    notifyAll();
	}
    }
    
    /***************************************************************************
     * Runnable
     ***************************************************************************/
    
    public void run()
    {
	while (started)
	{
	    synchronized (this)
	    {
		while (transferHandler == null && started)
		{
		    try
		    {
			wait(1000);
		    } catch (InterruptedException ie)
		    {
		    }
		} // while
	    }
	    
	    if (started && transferHandler != null)
	    {
		transferHandler.transferData(this);
		try
		{
			Thread.sleep((long)(((double)maxDataLength)*1000./4./44100.*0.5));
		} catch (InterruptedException ise)
		{
		}
	    }
	} // while (started)
    } // run
    
    // Controls
    
    public Object [] getControls()
    {
	return controls;
    }
    
    @SuppressWarnings("unchecked")
	public Object getControl(String controlType)
    {
	try
	{
	    Class  cls = Class.forName(controlType);
	    Object cs[] = getControls();
	    for (int i = 0; i < cs.length; i++)
	    {
		if (cls.isInstance(cs[i]))
		    return cs[i];
	    }
	    return null;
	    
	} catch (Exception e)
	{   // no such controlType or such control
	    return null;
	}
    }
    
    int seqNo=0;
    
    public void read(javax.media.Buffer buffer) throws java.io.IOException
    {
	synchronized (this)
	{
	    Object outdata = buffer.getData();
	    if (outdata == null || !(outdata.getClass() == Format.byteArray) ||
	    ((byte[])outdata).length > maxDataLength)
	    {
		outdata = new byte[maxDataLength];
		buffer.setData(outdata);
	    }
	    
	    buffer.setFormat( audioFormat );
	    
	    byte[] data_out=(byte[])outdata;
	    
	    
	    double delta_t=1./(double)freq_out;
	    
	    int buffer_len=data_out.length/(num_channels*(bits_per_channel/8));
	    for (int i = 0; i < buffer_len; i++)
	    {
		
		//double time=(double)i*delta_t;
		double d_left_value=Math.sin(2*Math.PI*wave_left_freq*delta_t+wave_left_shift);
		double d_right_value=Math.sin(2*Math.PI*wave_right_freq*delta_t+wave_right_shift);
		
		d_left_value*=max_amplitude;
		d_right_value*=max_amplitude;
		
		wave_left_shift+=2*Math.PI*wave_left_freq*delta_t;
		wave_right_shift+=2*Math.PI*wave_right_freq*delta_t;
		
		
		if(num_channels==1)
		{
		    if(bits_per_channel==16)
		    {
			data_out[(num_channels*bits_per_channel/8)*i]=(byte)((short)d_left_value & 0xFF);
			data_out[(num_channels*bits_per_channel/8)*i+1]=(byte)((short)d_left_value >> 8);
		    }
		    else if(bits_per_channel==8)
		    {
			data_out[(num_channels*bits_per_channel/8)*i]=(byte)d_left_value;
		    }
		}
		else if(num_channels==2)
		{
		    if(bits_per_channel==16)
		    {
			data_out[(num_channels*bits_per_channel/8)*i]=(byte)((short)d_left_value & 0xFF);
			data_out[(num_channels*bits_per_channel/8)*i+1]=(byte)((short)d_left_value >> 8);
			data_out[(num_channels*bits_per_channel/8)*i+2]=(byte)((short)d_right_value & 0xFF);
			data_out[(num_channels*bits_per_channel/8)*i+3]=(byte)((short)d_right_value >> 8);
		    }
		    else if(bits_per_channel==8)
		    {
			data_out[(num_channels*bits_per_channel/8)*i]=(byte)d_left_value;
			data_out[(num_channels*bits_per_channel/8)*i+1]=(byte)d_right_value;
		    }
		}
	    }
	    
	    wave_left_shift=Math.IEEEremainder(wave_left_shift,2*Math.PI);
	    wave_right_shift=Math.IEEEremainder(wave_right_shift,2*Math.PI);
	    
	    buffer.setSequenceNumber( seqNo );
	    buffer.setLength(data_out.length);
	    buffer.setOffset(0);
	    buffer.setFlags(Buffer.FLAG_RELATIVE_TIME);
	    buffer.setTimeStamp(0);
	    buffer.setHeader( null );
	    seqNo++;
	}
	
    }
    
}
