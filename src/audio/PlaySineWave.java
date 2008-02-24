/*
 * PlaySineWave.java
 *
 * Created on 25 de Marco de 2003, 19:14
 */

package audio;

import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.control.*;
import java.util.*;
import java.io.*;
import java.awt.*;
/**
 *
 * @author  jp
 */
public class PlaySineWave extends javax.swing.JFrame implements ControllerListener
{
	
	/** Creates new form PlaySineWave */
	public PlaySineWave()
	{
		initComponents();
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	
	private void initComponents()//GEN-BEGIN:initComponents
	{
	    jPanel1 = new javax.swing.JPanel();
	    startPlay = new javax.swing.JButton();
	    startCapture = new javax.swing.JButton();
	    
	    addWindowListener(new java.awt.event.WindowAdapter()
	    {
		public void windowClosing(java.awt.event.WindowEvent evt)
		{
		    exitForm(evt);
		}
	    });
	    
	    startPlay.setText("Start Playing");
	    startPlay.addActionListener(new java.awt.event.ActionListener()
	    {
		public void actionPerformed(java.awt.event.ActionEvent evt)
		{
		    startPlayActionPerformed(evt);
		}
	    });
	    
	    jPanel1.add(startPlay);
	    
	    startCapture.setText("Start Capturing");
	    startCapture.addActionListener(new java.awt.event.ActionListener()
	    {
		public void actionPerformed(java.awt.event.ActionEvent evt)
		{
		    startCaptureActionPerformed(evt);
		}
	    });
	    
	    jPanel1.add(startCapture);
	    
	    getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
	    
	    pack();
	}//GEN-END:initComponents
	
	
	Processor capture_processor=null;
	
	private void startCaptureActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startCaptureActionPerformed
	{//GEN-HEADEREND:event_startCaptureActionPerformed
		if(capture_processor==null || capture_processor.getState()!=capture_processor.Started)
			startCaptureSound();
		else
			stopCaptureSound();
		
	}//GEN-LAST:event_startCaptureActionPerformed
	
	private void startCaptureSound()
	{
		
		CaptureDeviceInfo di = null;
		
		StateHelper sh = null;
		java.util.Vector deviceList = CaptureDeviceManager.getDeviceList(
		new	AudioFormat(AudioFormat.LINEAR, 44100, 16, 2)
		);
		if (deviceList.size() > 0)
			di = (CaptureDeviceInfo)deviceList.firstElement();
		else
			// Exit if we can't find a device that does linear,
			// 44100Hz, 16 bit,
			// stereo audio.
			return;
		
		for(int i=0; i<deviceList.size();i++)
		{
			System.out.println("DeviceInfo["+i+"]="+((CaptureDeviceInfo)deviceList.get(i)).toString());
		}
		
		try
		{
			capture_processor = Manager.createProcessor(di.getLocator());
			sh = new StateHelper(capture_processor );
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		} catch (NoProcessorException e)
		{
			e.printStackTrace();
			return;
		}
		// Configure the processor
		if (!sh.configure(10000))
			return;
		// Set the output content type and realize the processor
		capture_processor.setContentDescriptor(new
		FileTypeDescriptor(FileTypeDescriptor.WAVE));
		if (!sh.realize(10000))
			return;
		// get the output of the processor
		DataSource source = capture_processor.getDataOutput();
		// create a File protocol MediaLocator with the location of the
		// file to which the data is to be written
		MediaLocator dest = new MediaLocator("file:///d:\\Projects\\Java\\SourceSafe\\Rec\\capture_audio.wav");
		// create a datasink to do the file writing & open the sink to
		// make sure we can write to it.
		DataSink filewriter = null;
		try
		{
			filewriter = Manager.createDataSink(source, dest);
			filewriter.open();
		} catch (NoDataSinkException e)
		{
			e.printStackTrace();
			return;
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		} catch (SecurityException e)
		{
			e.printStackTrace();
			return;
		}
		
		// if the Processor implements StreamWriterControl, we can
		// call setStreamSizeLimit
		// to set a limit on the size of the file that is written.
		StreamWriterControl swc = (StreamWriterControl)
		capture_processor.getControl("javax.media.control.StreamWriterControl");
		//set limit to 5MB
		if (swc != null)
			swc.setStreamSizeLimit(5000000);
		
		// now start the filewriter and processor
		try
		{
			filewriter.start();
		} catch (IOException e)
		{
			System.exit(-1);
		}
		// Capture for 5 seconds
		sh.playToEndOfMedia(10000);
		sh.close();
		// Wait for an EndOfStream from the DataSink and close it...
		filewriter.close();
		
		//startCapture.setText("Stop Capturing");
	}
	
	private void stopCaptureSound()
	{
		if(capture_processor!=null)
		{
			capture_processor.stop();
			capture_processor.deallocate();
			startCapture.setText("Start Capturing");
		}
	}
	
	private void startPlayActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startPlayActionPerformed
	{//GEN-HEADEREND:event_startPlayActionPerformed
		if(p==null || p.getState()!=p.Started)
			startPlaySound();
		else
			stopPlaySound();
	}//GEN-LAST:event_startPlayActionPerformed
	
	Player p=null;
	java.awt.Component freq_control=null;
	
	private void startPlaySound()
	{
		try
		{
			//InterLacedSineWaveDataSource sinewds=new InterLacedSineWaveDataSource();
			//InterLacedSineWaveDataSource ilds=new InterLacedSineWaveDataSource();
			PackageManager.getProtocolPrefixList().add("test.audio");
			//PackageManager.commitProtocolPrefixList();
			
			MediaLocator loc=new MediaLocator("sinewavegenerator:");
			DataSource source=Manager.createDataSource(loc);
			freq_control=((Control)(source.getControls()[0])).getControlComponent();
			getContentPane().add(freq_control,java.awt.BorderLayout.CENTER);
			pack();
			
			p=Manager.createPlayer(source);
			//p=Manager.createPlayer(loc);
			
			
			p.addControllerListener(this);
			
			/*p.configure();
			if (!waitForState(p.Configured))
			{
				System.err.println("Failed to configure the processor.");
				return;
			}*/
			
			p.realize();
			if (!waitForState(p.Realized))
			{
				System.err.println("Failed to realize the processor.");
				return;
			}
			
			p.start();
			if (!waitForState(p.Started))
			{
				System.err.println("Failed to start the processor.");
				return;
			}
			
			startPlay.setText("Stop Playing");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void stopPlaySound()
	{
		if(p!=null)
		{
			p.stop();
			p.deallocate();
			if(freq_control!=null)
			{
				getContentPane().remove(freq_control);
				pack();
				freq_control=null;
			}
			
			startPlay.setText("Start Playing");
		}
	}
	/** Exit the Application */
	private void exitForm(java.awt.event.WindowEvent evt)
	{//GEN-FIRST:event_exitForm
		System.exit(0);
	}//GEN-LAST:event_exitForm
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		new PlaySineWave().show();
	}
	
	private Object waitSync = new Object();
	private boolean stateTransitionOK = true;
	
	public void controllerUpdate(ControllerEvent evt)
	{
		
		if (evt instanceof ConfigureCompleteEvent ||
		evt instanceof RealizeCompleteEvent ||
		evt instanceof PrefetchCompleteEvent)
		{
			synchronized (waitSync)
			{
				stateTransitionOK = true;
				waitSync.notifyAll();
			}
		}
		else if (evt instanceof ResourceUnavailableEvent)
		{
			synchronized (waitSync)
			{
				stateTransitionOK = false;
				waitSync.notifyAll();
			}
		}
		else if (evt instanceof EndOfMediaEvent)
		{
			p.close();
			System.exit(0);
		}
	}
	
	
	private boolean waitForState(int state)
	{
		synchronized (waitSync)
		{
			try
			{
				while (p.getState() != state && stateTransitionOK)
					waitSync.wait();
			} catch (Exception e)
			{}
		}
		return stateTransitionOK;
	}
	
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton startCapture;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JButton startPlay;
	// End of variables declaration//GEN-END:variables
	
}
