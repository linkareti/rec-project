package pt.utl.ist.elab.driver.statsound.audio.media.protocol.sinewavegenerator;

import java.io.IOException;

import javax.media.Time;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PullBufferDataSource;

public class DataSource extends PullBufferDataSource
{
	
	
	protected boolean started = false;
	protected String contentType = ContentDescriptor.RAW;
	protected boolean connected = false;
	protected Time duration = DURATION_UNKNOWN;
	protected InterLacedSineWaveStream [] streams = null;
	protected InterLacedSineWaveStream stream = new InterLacedSineWaveStream();
	//protected Object [] controls = new Object[]{new FrequencyControl(stream)};
	protected Object [] controls = new Object[0];
	
	public DataSource()
	{
	}
	
	public String getContentType()
	{
		if (!connected)
		{
			System.err.println("Error: DataSource not connected");
			return null;
		}
		return contentType;
	}
	
	public void connect() throws IOException
	{
		if (connected)
			return;
		connected = true;
	}
	
	public void disconnect()
	{
		try
		{
			if (started)
				stop();
		} catch (IOException e)
		{}
		connected = false;
	}
	
	public void start() throws IOException
	{
		// we need to throw error if connect() has not been called
		if (!connected)
			throw new java.lang.Error("DataSource must be connected before it can be started");
		if (started)
			return;
		started = true;
		stream.start(true);
	}
	
	public void stop() throws IOException
	{
		if ((!connected) || (!started))
			return;
		started = false;
		stream.start(false);
	}
	
	public Object [] getControls()
	{
		return controls;
	}
	
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
	
	public Time getDuration()
	{
		return duration;
	}
	
	public javax.media.protocol.PullBufferStream[] getStreams()
	{
		if (streams == null)
		{
			streams = new InterLacedSineWaveStream[1];
			streams[0]=stream ;
		}
		
		return streams;
	}
	
}
