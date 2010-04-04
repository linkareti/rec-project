/*
 * WebCamInit.java
 *
 * Created on 5 de Junho de 2003, 13:49
 */

package pt.utl.ist.elab.driver.aleatorio.Hardware;

import java.awt.Image;
import java.io.IOException;

import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Controller;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class WebCamThread implements Runnable {

	private static boolean debugDeviceList = false;

	private static CaptureDeviceInfo captureVideoDevice = null;
	private DataSink outputDataSink;

	private static Player videoPlayer;
	private int imageWidth = 640;
	private int imageHeight = 480;
	public long milisecs; // length of the movie file in milisecs
	private DataSource videoDataSource = null;

	public boolean recording = false;

	private VideoFormat wantedFormat = null;

	// private Control[] controls =null;
	// private FrameGrabbingControl fgc=null;

	/** Creates a new instance of WebCam */
	@SuppressWarnings("unchecked")
	public WebCamThread() {

		// get a list of all media devices, search default devices and formats,
		// and print it out if args[x] = "-dd"
		// --------------------------------------------------------------------------------------------------------

		System.out.println(">>> get list of all media devices ...");

		java.util.Vector<CaptureDeviceInfo> deviceListVector = CaptureDeviceManager.getDeviceList(null);
		if (deviceListVector == null) {
			System.out.println("... error: media device list vector is null, program aborted");
			System.exit(0);
		}
		if (deviceListVector.size() == 0) {
			System.out.println("... error: media device list vector size is 0, program aborted");
			System.exit(0);
		}

		for (int x = 0; x < deviceListVector.size(); x++) {
			// display device name
			CaptureDeviceInfo deviceInfo = deviceListVector.elementAt(x);
			String deviceInfoText = deviceInfo.getName();
			if (debugDeviceList)
				System.out.println("device " + x + ": " + deviceInfoText);

			// display device formats
			Format deviceFormat[] = deviceInfo.getFormats();
			for (int y = 0; y < deviceFormat.length; y++) {
				// search for default video device
				if (captureVideoDevice == null)
					if (deviceFormat[y] instanceof VideoFormat)
					// if (deviceInfo.getName().indexOf(defaultVideoDeviceName)
					// >= 0)
					{
						captureVideoDevice = deviceInfo;
						System.out.println(">>> capture video device = " + deviceInfo.getName());
						Format[] videoFormats = captureVideoDevice.getFormats();

						wantedFormat = null;
						int w = 0;
						int h = 0;
						for (int i = 0; i < videoFormats.length; i++) {
							wantedFormat = (VideoFormat) videoFormats[i];
							w = (int) wantedFormat.getSize().getWidth();
							h = (int) wantedFormat.getSize().getHeight();

							if (w == imageWidth && h == imageHeight)
								break;
						}
					}

				// search for default video format
				/*
				 * if (captureVideoDevice == deviceInfo) if (captureVideoFormat
				 * == null) if
				 * (DeviceInfo.formatToString(deviceFormat[y]).indexOf
				 * (defaultVideoFormatString) >= 0) { captureVideoFormat =
				 * (VideoFormat) deviceFormat[y];
				 * System.out.println(">>> capture video format = " +
				 * DeviceInfo.formatToString(deviceFormat[y])); }
				 */
				// if (debugDeviceList)
				// System.out.println(" - format: " +
				// DeviceInfo.formatToString(deviceFormat[y]));
			}
		}
		// System.out.println("... list completed.");

		// if args[x] = "-dd" terminate now
		// --------------------------------
		// if (debugDeviceList)
		// System.exit(0);

		// setup video data source
		// -----------------------
		MediaLocator ml = captureVideoDevice.getLocator();

		try {
			if (ml != null) {

				videoDataSource = javax.media.Manager.createDataSource(ml);

				FormatControl[] formatControls = ((CaptureDevice) videoDataSource).getFormatControls();

				Format finalFormat = null;
				for (int i = 0; i < formatControls.length; i++) {
					if (formatControls == null)
						continue;
					if ((finalFormat = formatControls[i].setFormat(wantedFormat)) != null)
						break;
				}

				if (finalFormat == null)
					System.out.println("Couldn't set the desired format...using the default!");

				videoDataSource.connect();
				videoPlayer = Manager.createRealizedPlayer(videoDataSource);
				videoPlayer.start();
			} else {
				System.err.println("Error : No MediaLocator for " + captureVideoDevice.getName());
			}
		} catch (IOException e) {
			System.out.println("Creating DataSource" + e);
			System.exit(0);
		} catch (NoDataSourceException e) {
			System.out.println("Creating DataSource" + e);
			System.exit(0);
		} catch (CannotRealizeException nre) {
			nre.printStackTrace();
		} catch (NoPlayerException npe) {
			npe.printStackTrace();
		}

		/*
		 * if (! DeviceInfo.setFormat(videoDataSource, captureVideoFormat)) {
		 * System
		 * .out.println("Error: unable to set video format - program aborted");
		 * System.exit(0); }
		 */

		// create a new processor
		// ----------------------
		// setup output file format ->> msvideo
		/*
		 * FileTypeDescriptor outputType = new
		 * FileTypeDescriptor(FileTypeDescriptor.MSVIDEO);
		 * 
		 * // setup output video and audio data format Format outputFormat[] =
		 * new Format[1]; outputFormat[0] = new
		 * VideoFormat(VideoFormat.INDEO50); //outputFormat[1] = new
		 * AudioFormat(AudioFormat.MPEGLAYER3 );
		 * 
		 * // create processor ProcessorModel processorModel = new
		 * ProcessorModel(videoDataSource, outputFormat, outputType);
		 * 
		 * Processor videoProcessor = null; try {videoProcessor =
		 * Manager.createRealizedProcessor(processorModel);} catch (IOException
		 * e) { System.out.println(e); System.exit(0); } catch
		 * (NoProcessorException e) { System.out.println(e); System.exit(0); }
		 * catch (CannotRealizeException e) { System.out.println(e);
		 * System.exit(0); }
		 * 
		 * // get the output of the processor processorDataSource =
		 * videoProcessor.getDataOutput();
		 * //System.out.println("DataSource:"+videoDataSource);
		 * 
		 * 
		 * System.out.println(">>> creating the videoPlayer"); try { videoPlayer
		 * = Manager.createRealizedPlayer(videoDataSource); //or mediaLocator
		 * //or 'videoDataSource' //or URL //videoPlayer =
		 * Manager.createRealizedPlayer(videoDataSource); } catch(IOException e)
		 * {System.out.println("Can't create Player. Check Webcam!");
		 * System.exit(1);} catch(NoPlayerException e) {e.printStackTrace();
		 * System.exit(1);} catch(CannotRealizeException e)
		 * {System.out.println(e.toString()); System.exit(1);}
		 * 
		 * //videoPlayer.start(); //while (videoPlayer.getState() !=
		 * videoPlayer.Started){} //wait for player to start!
		 */
	}

	public void videoPlayerStart() {
		videoPlayer.start();
		while (videoPlayer.getState() != Controller.Started) {
		} // wait for player to start!
	}// videoPlayerStart

	public void videoPlayerStop() {
		videoPlayer.stop();
	}// videoPlayerStop

	public boolean isVideoPlayerStarted() {
		return (videoPlayer.getState() == Controller.Started);
	}// isVideoPlayerStarted

	public DataSource getVideoDataSource() {
		return videoDataSource;
	}// getVideoDataSource

	/**
     */
	/**
	 * public WebCamThread(String fileName) { this.movieFileName = fileName; //
	 * get a list of all media devices, search default devices and formats, and
	 * print it out if args[x] = "-dd" //
	 * ----------------------------------------
	 * ----------------------------------------------------------------
	 * 
	 * System.out.println(">>> get list of all media devices ...");
	 * 
	 * java.util.Vector deviceListVector =
	 * CaptureDeviceManager.getDeviceList(null); if (deviceListVector == null) {
	 * System.out.println(
	 * "... error: media device list vector is null, program aborted");
	 * System.exit(0); } if (deviceListVector.size() == 0) { System.out.println(
	 * "... error: media device list vector size is 0, program aborted");
	 * System.exit(0); }
	 * 
	 * for (int x = 0; x < deviceListVector.size(); x++) { // display device
	 * name CaptureDeviceInfo deviceInfo = (CaptureDeviceInfo)
	 * deviceListVector.elementAt(x); String deviceInfoText =
	 * deviceInfo.getName(); if (debugDeviceList) System.out.println("device " +
	 * x + ": " + deviceInfoText);
	 * 
	 * // display device formats Format deviceFormat[] =
	 * deviceInfo.getFormats(); for (int y = 0; y < deviceFormat.length; y++) {
	 * // serach for default video device if (captureVideoDevice == null) if
	 * (deviceFormat[y] instanceof VideoFormat) //if
	 * (deviceInfo.getName().indexOf(defaultVideoDeviceName) >= 0) {
	 * captureVideoDevice = deviceInfo;
	 * System.out.println(">>> capture video device = " + deviceInfo.getName());
	 * }
	 * 
	 * // search for default video format if (captureVideoDevice == deviceInfo)
	 * if (captureVideoFormat == null) if
	 * (DeviceInfo.formatToString(deviceFormat
	 * [y]).indexOf(defaultVideoFormatString) >= 0) { captureVideoFormat =
	 * (VideoFormat) deviceFormat[y];
	 * System.out.println(">>> capture video format = " +
	 * DeviceInfo.formatToString(deviceFormat[y])); }
	 * 
	 * } } // setup video data source // ----------------------- MediaLocator
	 * videoMediaLocator = captureVideoDevice.getLocator();
	 * 
	 * DataSource videoDataSource = null; try { videoDataSource =
	 * javax.media.Manager.createDataSource(videoMediaLocator); } catch
	 * (IOException e) { System.out.println("Creating DataSource" + e);
	 * System.exit(0); } catch (NoDataSourceException e) {
	 * System.out.println("Creating DataSource" + e); System.exit(0); }
	 * 
	 * if (! DeviceInfo.setFormat(videoDataSource, captureVideoFormat)) {
	 * System.
	 * out.println("Error: unable to set video format - program aborted");
	 * System.exit(0); }
	 * 
	 * // create a new processor // ----------------------
	 * 
	 * // setup output file format ->> msvideo FileTypeDescriptor outputType =
	 * new FileTypeDescriptor(FileTypeDescriptor.MSVIDEO);//isto � o INDEO5
	 * 
	 * // setup output video and audio data format Format outputFormat[] = new
	 * Format[1]; outputFormat[0] = new VideoFormat(VideoFormat.INDEO50);
	 * //outputFormat[1] = new AudioFormat(AudioFormat.MPEGLAYER3 );
	 * 
	 * // create processor ProcessorModel processorModel = new
	 * ProcessorModel(videoDataSource, outputFormat, outputType); Processor
	 * videoProcessor = null; try {videoProcessor =
	 * Manager.createRealizedProcessor(processorModel);} catch (IOException e) {
	 * System.out.println(e); System.exit(0); } catch (NoProcessorException e) {
	 * System.out.println(e); System.exit(0); } catch (CannotRealizeException e)
	 * { System.out.println(e); System.exit(0); }
	 * 
	 * // get the output of the processor processorDataSource =
	 * videoProcessor.getDataOutput();
	 * 
	 * System.out.println(">>> creating the videoPlayer"); try { videoPlayer =
	 * Manager.createRealizedPlayer(videoDataSource); //or mediaLocator //or
	 * 'videoDataSource' //or URL //videoPlayer =
	 * Manager.createRealizedPlayer(videoDataSource); } catch(IOException e)
	 * {System.out.println("Can't create Player. Check Webcam!");
	 * System.exit(1);} catch(NoPlayerException e) {e.printStackTrace();
	 * System.exit(1);} catch(CannotRealizeException e)
	 * {System.out.println(e.toString()); System.exit(1);} videoPlayer.start();
	 * while (videoPlayer.getState() != videoPlayer.Started){} //wait for player
	 * to start!
	 * 
	 * 
	 * //commented so no file is recorded if (movieFileName == null)
	 * movieFileName = "//pt//utl//ist//elab//Aleatorio//movie0.avi";
	 * 
	 * // Generate the output media locator. MediaLocator outputMediaLocator =
	 * new MediaLocator(movieFileName); //� aqui, penso eu, que se diz que o
	 * output vai para o file
	 * 
	 * try {outputDataSink =
	 * Manager.createDataSink(processorDataSource,outputMediaLocator);}
	 * catch(NoDataSinkException e){} try {outputDataSink.open();}
	 * catch(IOException e){System.out.println("Cannot Write in " +
	 * movieFileName);}
	 * 
	 * }
	 */
	/**
	 *Configure the length of the movie file in milisecs
	 */
	public void configure(long milisecs) {
		System.out.println(">>> Configuring webcam for recording for " + milisecs + " milisecs.");
		this.milisecs = milisecs;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 *Acquires an image from the webcam
	 */
	public Image getImage() {
		if (!isVideoPlayerStarted()) {
			System.out.println("Starting Video Player for getting image!");
			videoPlayer.start();
		}

		videoPlayer.getControls();
		FrameGrabbingControl fgc = (FrameGrabbingControl) videoPlayer
				.getControl("javax.media.control.FrameGrabbingControl");

		Image imagemCapturada = null;
		if (fgc == null) {
			System.out.println("Problem: No frameGrabbingControl\nCannot acquire image!");
		} else {
			Buffer buf = fgc.grabFrame(); // Here you get a frame
			// Byte[] data=buf.getData();

			javax.media.util.BufferToImage bti = new javax.media.util.BufferToImage((VideoFormat) buf.getFormat());
			imagemCapturada = bti.createImage(buf);

			// System.out.println(">>graphics:"+imagemCapturada.getGraphics());
			/*
			 * int inPixels[] = new int[640480];
			 * 
			 * java.awt.image.PixelGrabber pg = new
			 * java.awt.image.PixelGrabber(imagemCapturada, 0, 0, 640, 480,
			 * inPixels, 0, 640); try { pg.grabPixels();
			 * }catch(InterruptedException e) { e.printStackTrace();
			 * System.exit(1); }
			 */

		}
		// videoPlayer.stop();
		return imagemCapturada;
	}

	/**
	 *Grabs a frame and returns the buffer of that frame
	 */
	public Buffer grabFrameBuffer() {
		// Control[] controls = videoPlayer.getControls();
		FrameGrabbingControl fgc = (FrameGrabbingControl) videoPlayer
				.getControl("javax.media.control.FrameGrabbingControl");
		if (fgc == null) {
			System.out.println("Problem: No frameGrabbingControl\nCannot acquire image!");
		} else {
			return fgc.grabFrame(); // Here you get a frame
		}
		return null;
	}// grabFrameBuffer

	/**
	 *Runs the thread. This is where the actual recording is done!
	 */
	public void run() { // man, this doesn't do anything but wait for milisecs
		recording = true; // It's amazing how I say this is where the actual
		// recording is done!!!

		System.out.println("Filming!");
		Waiting waiting = new Waiting();
		synchronized (waiting.synchStop) {
			synchronized (waiting.synchStart) {
				waiting.start();
				try {
					waiting.synchStart.wait();
				} catch (Exception e) {
				}

			}
			try {
				waiting.synchStop.wait();
			} catch (Exception e) {
			}
		}
		// try {Thread.currentThread().sleep(milisecs);}
		// catch(InterruptedException e){}

		recording = false;
		System.out.println("Done Filming!");
	}

	/**
	 *Used for emergency stopping
	 */
	public void stopRec() {
		try {
			outputDataSink.stop();
		} catch (IOException e) {
		}
		recording = false;
	}

	public void finalize() throws Throwable {
		try {
			super.finalize();
		} catch (Exception e) {
			throw e.fillInStackTrace();
		}
	}// finalize

	class Waiting extends Thread {
		public Object synchStart = new Object();
		public Object synchStop = new Object();

		public void run() {
			synchronized (synchStart) {
				synchStart.notifyAll();
				try {
					Thread.sleep(milisecs);
				} catch (InterruptedException e) {
				}
			}
			synchronized (synchStop) {
				synchStop.notifyAll();
			}
		}// run
	}// Waiting
}
