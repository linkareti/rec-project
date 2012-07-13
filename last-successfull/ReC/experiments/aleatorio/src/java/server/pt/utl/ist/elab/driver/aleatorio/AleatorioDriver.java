/*
 * AleatorioDriver.java
 *
 * Created on 6 de Junho de 2003, 11:23
 */

package pt.utl.ist.elab.driver.aleatorio;

import java.io.IOException;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.aleatorio.Hardware.HardwareInit;
import pt.utl.ist.elab.driver.aleatorio.Hardware.SoundThread;
import pt.utl.ist.elab.driver.aleatorio.Hardware.WebCamThread;
import pt.utl.ist.elab.driver.aleatorio.Utils.VideoReader;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class AleatorioDriver extends BaseDriver {

	// private transient com.linkare.rec.impl.driver.IDriverStateListener =
	// null;
	public static final String APPLICATION_IDENTIFIER = "E-Lab (Aleatorio Driver)";
	public static final String DRIVER_UNIQUE_ID = "ELAB_ALEATORIO_V02";
	public static final String HW_VERSION = "0.1";

	private HardwareAcquisitionConfig config = null;
	// private HardwareInfo info = null;
	private AleatorioDataSource aleatorioDataSource = null;

	public WebCamThread webcam = null;
	public SoundThread sound = null;
	private boolean recording = false;
	// private DataSourceReader dataSourceReader = null;
	private VideoReader videoReader = null;
	/*
	 * private FileOps file = null; public int[] statisticX = null; public int[]
	 * statisticY = null;
	 */

	private long movieLength; // length of movie file in miliseconds
	private static final long MOVIE_LENGTH = 10000; // Default movie length in
	// miliseconds
	// private float waveFrequency; // frequency of sound wave in Hz
	private static final float WAVE_FREQUENCY = 32.0F; // Default wave frequency
	// inHz
	private float frequency1, frequency2;
	private float waveDuration; // duration of the sound wave in seconds
	private static final float WAVE_DURATION = 3.0F; // Default wave duration in
	// seconds
	private boolean movieOnOff; // true if the user wants to view the movie
	// file; false otherwise
	private static final boolean MOVIE_ON_OFF = false; // by default we'return
	// not going to send
	// the movie file, since it takes up too much bandwidth
	private int numberOfSamples; // number of images to be analyzed.
	private static final int NUMBER_OF_SAMPLES = 10; // by default, we'll use 10
	// to get good
	// statistics

	// private static final int NUMBER_OF_DICE = 14; // this is the original
	// number
	// of dice

	public static final int FRAME_RATE = 5; // number of frames per second
	pt.utl.ist.elab.driver.aleatorio.Utils.ImageAnalyser imageAnalyser = null;
	private java.awt.Image imageToAnalyze = null;

	// private int centerCounter;
	private int[] centerCounterArray;

	private int[][] centers;

	// private final boolean waitingStart = false;

	/** Creates a new instance of AleatorioDriver */
	public AleatorioDriver() {
	}

	/*
	 * public static AleatorioDriver Create() { if (SingletonDriver==null)
	 * SingletonDriver = new AleatorioDriver(); return SingletonDriver; }
	 */

	/**
	 * BaseDriver implementation
	 */
	@Override
	public Object getHardwareInfo() {
		// com.linkare.net.protocols.Protocols.init();

		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = Protocols.getURL(prop);
			fireIDriverStateListenerDriverReseted();// why is this here and not
			// in GDriver?
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger("Aleatorio.logger"));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
				fireIDriverStateListenerDriverReseted();// And again???
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger("Aleatorio.logger"));
			}
		}
		return url;
	}// getHardwareInfo()

	@Override
	public String getDriverUniqueID() {
		return AleatorioDriver.DRIVER_UNIQUE_ID;
	}// getDriverUniqueID()

	@Override
	public void init(final HardwareInfo info) {
		// this.info = info;
		// System.out.println(" HardwareInfo read from a file is= " + info);
		// System.out.println(" Hardware Unique ID from file is: " +
		// info.getHardwareUniqueID());
		// aleatorioDataSource = new AleatorioDataSource(this);
		final HardwareInit hardware = new HardwareInit();
		webcam = hardware.getWebCamThread();
		sound = hardware.getSoundThread();
		readProperties();
		// while(!webcam.isVideoPlayerStarted()){System.out.println("waiting for videoplayer to be started!");}//wait
		// for videoPlayer to start!
		// dataSourceReader = new DataSourceReader(webcam);

		fireIDriverStateListenerDriverInited();
	}// init(HardwareInfo info)

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		// aleatorioDataSource = new AleatorioDataSource(this);
		info.validateConfig(config);

		// config.getSelectedHardwareParameter("BWThreshold").setParameterValue(getProps().getProperty("BWThreshold"));
		// e assim por diante... entendido? yep.

		try {
			configure(config, info);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new WrongConfigurationException("Erro no config...", 20);
		}// aqui parece estar certo, mas precisas de fazer mais que isso...
			// deves definir as totalSamples para cada canal, e aqueles
			// par�metros pmarados que est�s a definir l� para baixo algures...
	}// config(HardwareAcquisitionConfig config,HardwareInfo info)

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();

		// this.info = info;
		aleatorioDataSource = new AleatorioDataSource(this);
		final ParameterConfig[] selectedParams = config.getSelectedHardwareParameters();

		for (final ParameterConfig selectedParam : selectedParams) {
			if (selectedParam.getParameterName().equalsIgnoreCase("SoundWaveDuration")) {
				try {
					waveDuration = Float.valueOf(selectedParam.getParameterValue()).floatValue() / 1000;
				} catch (final NumberFormatException e) {
					waveDuration = AleatorioDriver.WAVE_DURATION;
				}
			}
			// System.out.println("soundDuration:"+waveDuration);
			if (selectedParam.getParameterName().equalsIgnoreCase("MovieOnOff")) {
				try {
					final int onOff = Integer.valueOf(selectedParam.getParameterValue()).intValue();
					movieOnOff = (onOff == 0 ? false : true);
				}
				// try {movieOnOff =
				// Boolean.valueOf(selectedParams[index].getParameterValue()).booleanValue();}
				catch (final NumberFormatException e) {
					movieOnOff = AleatorioDriver.MOVIE_ON_OFF;
				}
			}
			// System.out.println("show movie? " + movieOnOff);
			if (selectedParam.getParameterName().equalsIgnoreCase("NumberOfSamples")) {
				try {
					numberOfSamples = Integer.valueOf(selectedParam.getParameterValue()).intValue();
				} catch (final NumberFormatException e) {
					numberOfSamples = AleatorioDriver.NUMBER_OF_SAMPLES;
				}
			}
			// System.out.println("NumberOfSamples:"+numberOfSamples);
			if (selectedParam.getParameterName().equalsIgnoreCase("InitialFrequency")) {
				try {
					frequency1 = Float.valueOf(selectedParam.getParameterValue()).floatValue();
				} catch (final NumberFormatException e) {
					frequency1 = AleatorioDriver.WAVE_FREQUENCY;
				}
			}
			// System.out.println("Initial Freq:"+frequency1);
			if (selectedParam.getParameterName().equalsIgnoreCase("FinalFrequency")) {
				try {
					frequency2 = Float.valueOf(selectedParam.getParameterValue()).floatValue();
				} catch (final NumberFormatException e) {
					frequency2 = AleatorioDriver.WAVE_FREQUENCY;
				}
				// System.out.println("final Freq:"+frequency2);
			}
		}
		// the movie lasts as long as the wave plus one second to watch the dice
		// stopping
		this.config = config;

		movieLength = java.lang.Math.round(waveDuration * 1000 + 1000);
		webcam.configure(movieLength);

		webcam.videoPlayerStart();
		/*
		 * if (movieOnOff) { //dataSourceReader = new DataSourceReader(webcam);
		 * //dataSourceReader.open(webcam.getVideoDataSource()); videoReader =
		 * new VideoReader(aleatorioDataSource, webcam,
		 * (int)(FRAME_RATE*movieLength/1000), FRAME_RATE); }
		 */
		sound.configure(frequency1, frequency2, waveDuration);
		sound.newLine();
		fireIDriverStateListenerDriverConfigured();
	}// configure(HardwareAcquisitionConfig config, HardwareInfo info)

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		// return config;

		return aleatorioDataSource.getAcquisitionHeader();
	}// getAcquisitionHeader()

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		try {
			if (webcam != null && sound != null) {
				aleatorioDataSource = new AleatorioDataSource(this);
				fireIDriverStateListenerDriverStarting();
				centerCounterArray = new int[numberOfSamples];

				if (movieOnOff) {
					videoReader = new VideoReader(aleatorioDataSource, webcam, (int) (AleatorioDriver.FRAME_RATE
							* (double) movieLength / 1000.), AleatorioDriver.FRAME_RATE);
				}

				config.setTotalSamples(numberOfSamples
						+ (movieOnOff ? (numberOfSamples * (int) ((double) AleatorioDriver.FRAME_RATE
								* (double) movieLength / 1000.)) : 0));
				System.out
						.println("Smples expected: "
								+ (numberOfSamples + (movieOnOff ? (numberOfSamples * (int) ((double) AleatorioDriver.FRAME_RATE
										* (double) movieLength / 1000.))
										: 0)));
				config.setTimeStart(new DateTime());

				for (int i = 0; i < config.getChannelsConfig().length; i++) {
					config.getChannelsConfig(i).setTimeStart(config.getTimeStart());
					config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
				}// for_i

				aleatorioDataSource.setAcquisitionHeader(config); // o teu
				// current
				// HardwareAcquisitionConfig...

				aleatorioDataSource.setTotalSamples(config.getTotalSamples());

				aleatorioDataSource.setPacketSize(1 + (movieOnOff ? Math.min(10,
						((int) ((double) AleatorioDriver.FRAME_RATE * (double) movieLength / 1000.))) : 0));

				fireIDriverStateListenerDriverStarted();

				if (aleatorioDataSource != null)// Aqui nunca seria null ou
				// comes uma excep�~~ao antes
				{
					// System.out.println(">>Starting Experiment!!");
					// aleatorioDataSource.setRunning(true);

					(new Thread(new AleatorioThread())).start();

					// System.out.println("Ja mesmo antes de retornar a DataSource="+aleatorioDataSource);
					// aleatorioDataSource.setRunning(false);
					return aleatorioDataSource;// Da mto trabalho tentares?se
					// funcionar, nao!Nao sei se vai
					// funcionar!olha
				}// if
			}// if
		} catch (final Exception e) {
			System.out.println("Error occured while on Start...");
			e.printStackTrace();
		}
		return null;
	}// start(HardwareInfo info)

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		/**
		 * Current Version doesn't support startOutput Does nothing!
		 */
		return null;
	}// startOutput(HardwareInfo info,IDataSource source)

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		/*
		 * JP says: Don't use this!! And we don't use it!! unless it's an
		 * emergency...
		 */
		fireIDriverStateListenerDriverStoping();
		aleatorioDataSource.stopNow();
		webcam.stopRec();
		sound.stopWave();
		fireIDriverStateListenerDriverStoped();
	}// stop(HardwareInfo info)

	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverReseting();
		if (webcam != null) {
			try {
				webcam.finalize();
			} catch (final Exception e) {
				e.printStackTrace();
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}
		if (sound != null) {
			try {
				sound.finalize();
			} catch (final Exception e) {
				e.printStackTrace();
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}
		// initializes the hardware
		final HardwareInit hardware = new HardwareInit();
		webcam = hardware.getWebCamThread();
		sound = hardware.getSoundThread();

		// reconfigures with the default values
		webcam.configure(AleatorioDriver.MOVIE_LENGTH);
		sound.configure(AleatorioDriver.WAVE_FREQUENCY, AleatorioDriver.WAVE_FREQUENCY, AleatorioDriver.WAVE_DURATION);
		sound.newLine();
		// dataSourceReader = new DataSourceReader(webcam);
		// dataSourceReader.open(webcam.getVideoDataSource());

		fireIDriverStateListenerDriverReseted();
	}// reset(HardwareInfo info)

	@Override
	public void shutdown() {
		// There is nothing to shut down...
		// except the computer, but that's not going to happen, unless the power
		// goes down...
		super.shutDownNow();
	}// shutdown()

	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// Not necessary
	}// extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)

	public void setStoping() {
		fireIDriverStateListenerDriverStoping();
		webcam.stopRec();
		sound.stopWave();
	}

	public void setStoped() {
		fireIDriverStateListenerDriverStoped();
	}

	/** Properties stuff... */
	// private final java.io.InputStream is = null;
	// private final java.io.File propFile = null;
	// private final String propertiesLocation = null;
	private java.util.Properties props = null;

	/**
	 * Loads the properties File
	 */
	public void readProperties() {

		props = new java.util.Properties();
		try {
			props.load(getClass().getResourceAsStream(
					"/pt/utl/ist/elab/driver/aleatorio/configurator/AleatorioConfigurator.properties"));
		} catch (final IOException e) {
			System.out.println("Error loading the configurations.");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public java.util.Properties getProps() {
		return props;
	}

	// private void analyseImage() {
	// java.awt.Image image;
	// if (movieOnOff) {
	// // while(dataSourceReader.lastFrame == null){}
	// while (imageToAnalyze == null) {
	// }
	// image = imageToAnalyze;
	// } else {
	// image = imageToAnalyze;
	// // image = webcam.getImage();
	// }
	//
	// imageAnalyser = new
	// pt.utl.ist.elab.driver.aleatorio.Utils.ImageAnalyser(image);
	// imageAnalyser.setParams(Integer.valueOf(props.getProperty("BWThreshold")).intValue(),
	// Integer.valueOf(props.getProperty("radiusOfSpot")).intValue(),
	// Integer.valueOf(props.getProperty("houghThreshold1")).intValue(),
	// Integer.valueOf(props.getProperty("houghThreshold2")).intValue(),
	// Integer.valueOf(props.getProperty("houghThreshold3")).intValue(),
	// Integer.valueOf(props.getProperty("convThreshold")).intValue(),
	// Integer.valueOf(props.getProperty("maxWidthOfDie")).intValue(),
	// Integer.valueOf(props.getProperty("numberOfDice")).intValue());
	//
	// imageAnalyser.conversionBW();
	// imageAnalyser.edgeDetector();
	// imageAnalyser.houghTransform();
	// imageAnalyser.houghCount();
	// imageAnalyser.refineCount(ImageAnalyser.IMAGE_HOUGH);
	// // imageAnalyser.convolutionTransform();
	// // imageAnalyser.fullCount();
	// centers = imageAnalyser.getCenters();
	// // centerCounter = imageAnalyser.getCenterCounter();
	// /*
	// * javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
	// * imagemDialog.setTitle("Imagem Capturada"); ImageStorePanel
	// * imagemCapturadaPanel = new
	// * ImageStorePanel(imageAnalyser.getImage("count"));
	// * imagemDialog.getContentPane().add(imagemCapturadaPanel); int[] dimIm
	// * = imagemCapturadaPanel.imageSize(); imagemDialog.setSize(dimIm[0]+8,
	// * dimIm[1]+27); //imagemDialog.pack();
	// * imagemDialog.setDefaultCloseOperation
	// * (javax.swing.JFrame.DISPOSE_ON_CLOSE); imagemDialog.show();
	// * imagemDialog.repaint();
	// */
	// imageAnalyser.resetImages();
	// // int pos = indexOf(aleatorioDataSource.statisticX, centerCounter);
	// // aleatorioDataSource.updateStatisticsFile(centerCounter,
	// // aleatorioDataSource.statisticY[pos]++);
	// }

	/*
	 * returns the index of the first occurrence of 'val' in 'array' 'array' is
	 * an array of ints and 'val' is an int
	 */
	// private int indexOf(final int[] array, final int val) {
	// for (int index = 0; index < array.length; index++) {
	// if (array[index] == val) {
	// return index;
	// }
	// }
	// return -1;
	// }// indexOf(int[] array, int val)

	public int[][] getCenters() {
		return centers;
	}// getCenters()

	/*
	 * public javax.imageio.stream.ImageOutputStream
	 * image2jpegStream(java.awt.Image image) { byte[] tempArray = new
	 * byte[Integer.MAX_VALUE]; javax.imageio.stream.ImageOutputStream ios =
	 * null; //tu já viste a ByteArrayValBuffer... aceita ler de uma InputStream
	 * e dá-te um ByteArray já porreiro para enviar pelo rec... try{ ios =
	 * javax.imageio.ImageIO.createImageOutputStream(tempArray);
	 * javax.imageio.ImageIO.write((java.awt.image.RenderedImage) image, "jpg",
	 * ios); } catch(java.io.IOException e){} return ios; }
	 */
	class AleatorioThread implements java.lang.Runnable {
		@Override
		public void run() {
			for (int index = 0; index < numberOfSamples; index++) {
				imageToAnalyze = null;
				webcam.recording = true;
				recording = true;
				final int pauseBetweenFrames = 1000 / AleatorioDriver.FRAME_RATE;

				if (movieOnOff) {
					// while(!webcam.isVideoPlayerStarted()){System.out.println("waiting for videoplayer to be started!");}//wait
					// for videoPlayer to start!
					// System.out.println("videoPlayer started!");
					final RecordingThread recordingThread = new RecordingThread();
					recordingThread.start();
					while (!recording) {
						try {
							Thread.currentThread();
							Thread.sleep(pauseBetweenFrames * 2);
						} catch (final InterruptedException e) {
						}
					}// do nothing while not recording
					(new Thread(sound)).start();
					while (recording) {
						try {
							Thread.currentThread();
							Thread.sleep(pauseBetweenFrames * 2);
						} catch (final InterruptedException e) {
						}
					} // do nothing here while recording!
						// try{recordingThread.waiting.synchStop.wait();}
						// catch(Exception e){}
						// java.awt.Image[] movieFrames =
						// recordingThread.getMovieFrames();
						// for (int i=0; i < movieFrames.length; i++)
						// {
						// if (movieFrames[i] != null)
						// aleatorioDataSource.sendMovieFrame(movieFrames[i]);
						// }//for_i
						// movieFrames=null;
				}// if
				else {// se nao quisermos filme segue por aqui. isto funciona!
					while (!webcam.isVideoPlayerStarted()) {
					}// wait for videoPlayer to start!
					(new Thread(webcam)).start();
					(new Thread(sound)).start();

					while (webcam.recording) {
						try {
							Thread.currentThread();
							Thread.sleep(pauseBetweenFrames);
						} catch (final InterruptedException e) {
						}
					} // do nothing while recording!
				}// else

				// reset sound so the image sent is stopped!
				sound.newLine();
				try {
					Thread.currentThread();
					Thread.sleep(pauseBetweenFrames);
				} catch (final InterruptedException e) {
				}

				try {
					imageToAnalyze = webcam.getImage();
				} catch (final Exception e) {
					LoggerUtil
							.logThrowable("Unable to get image from webcam!", e, Logger.getLogger("Aleatorio.logger"));
					// TODO FIXME Handle exception and shutdown experiment!
				}

				// 09/10/2008
				// change to speed up transmission of image and avoid crashing
				// due to poor lighting!
				// this means, no more statistics.... but it's been like that
				// for eons!
				// System.out.println(">>> Analysing Image...");
				// analyseImage(); //first, analyse the current image
				// centerCounterArray[index] = imageAnalyser.getCenterCounter();
				centerCounterArray[index] = 0; // something must be sent, here!

				// aleatorioDataSource.sendImageCenters(webcam.getImage(),
				// getCenters()); //then supply the data to the client
				// System.out.println(">>> Sending Centers!");
				// aleatorioDataSource.sendCenters(getCenters());

				System.out.println(">>> Sending Image!");
				java.awt.Image webcamImage;
				if (movieOnOff) {
					// while(dataSourceReader.lastFrame == null){}
					while (videoReader.lastFrame == null) {
					}
					webcamImage = imageToAnalyze;
				} else {
					// webcamImage = webcam.getImage();
					webcamImage = imageToAnalyze;
				}
				while (webcamImage.getHeight(null) == 0) {
				} // do nothing while image is not ok
				aleatorioDataSource.sendImage(webcamImage, centerCounterArray[index]);
				// aleatorioDataSource.sendCenterCounter(centerCounterArray[index]);

				webcamImage.flush();

				try {
					Thread.currentThread();
					Thread.sleep(50);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}// end -for
			fireIDriverStateListenerDriverStoping();
			aleatorioDataSource.endAcquisition();
			webcam.videoPlayerStop();
			// // updates the Statistics data file after all samples (of this
			// // experiment) have been processed!
			// int numberOfDice;
			// try {
			// numberOfDice =
			// Integer.parseInt(props.getProperty("numberOfDice"));
			// } catch (NumberFormatException e) {
			// e.printStackTrace();
			// numberOfDice = NUMBER_OF_DICE;
			// }
			// // int numberOfDice =
			// //
			// Integer.getInteger(props.getProperty("numberOfDice")).intValue();
			// // for (int i = 0; i < numberOfSamples; i++) {
			// for (int i = 0; i < centerCounterArray.length; i++) {
			// System.out.println("numberOfspotsDetected=" +
			// centerCounterArray[i]);
			// aleatorioDataSource.updateStatisticsFile(centerCounterArray[i],
			// aleatorioDataSource.statisticY[centerCounterArray[i] -
			// numberOfDice]++);
			// }// for_i
			// aleatorioDataSource.sessionStatisticsFile(centerCounterArray,
			// Integer.parseInt(props
			// .getProperty("numberOfDice")));
			System.out.println("Ended acquisition...");
			fireIDriverStateListenerDriverStoped();

		}// run
	}// AleatorioThread

	class RecordingThread extends Thread {
		// private java.awt.Image[] movieFrames = null;
		// private int frameCount = 0;
		// private static final int PAUSE_BETWEEN_FRAMES = 1000 /
		// AleatorioDriver.FRAME_RATE;
		private int totalFrames = 0;

		// public Waiting waiting = new Waiting();

		public RecordingThread() {
			setPriority(Thread.MAX_PRIORITY - 1);// e a thread do som?..
			// MAX_PRIORITY?
			// movieFrames = new
			// java.awt.Image[FRAME_RATE*(int)movieLength/1000];
		}// RecordingThread()

		@Override
		public void run() {
			totalFrames = AleatorioDriver.FRAME_RATE * (int) movieLength / 1000;
			// movieFrames = new java.awt.Image[totalFrames];

			System.out.println("Filming!");

			/*
			 * try {dataSourceReader.startBuff(aleatorioDataSource,
			 * totalFrames);} catch (Exception e) {
			 * System.out.println("O StartBuff Tripou!!!"); e.printStackTrace();
			 * recording = false; }
			 * 
			 * while (dataSourceReader.startedFilming==false)
			 * try{this.sleep(50);} catch(InterruptedException e){}
			 */
			recording = true;
			videoReader.start();
			// while(dataSourceReader.getFrameCounter() < totalFrames)
			while (videoReader.getFrameCounter() < totalFrames) {
				synchronized (this) {// if (dataSourceReader.getFrameCounter()
					// >= totalFrames/2) recording=true;
					try {
						this.wait(movieLength);
					} catch (final InterruptedException e) {
					}
				}
			}// while

			// while (dataSourceReader.lastFrame == null){
			while (videoReader.lastFrame == null) {
				synchronized (this) {
					try {
						this.wait(movieLength);
					} catch (final InterruptedException e) {
					}
				}
			}// while
				// imageToAnalyze = dataSourceReader.lastFrame;
			imageToAnalyze = videoReader.lastFrame;

			// System.out.println("frames caught:"+ totalFrames);
			System.out.println("Done Filming!");
			recording = false;
		}// run

		/*
		 * public java.awt.Image[] getMovieFrames() {
		 * System.out.println("Getting movieFrames!"); return movieFrames;//isto
		 * devia tar bom e nao retornar null... //nao sei se ja percebeste, mas
		 * o erro nao e ai... }//getMovieFrames
		 */
	}// RecordingThread

	/*
	 * class Waiting extends Thread//esta class implementa aquilo que me tinhas
	 * explicado com os synchStart e os synchStop { public Object synchStart =
	 * new Object(); public Object synchStop = new Object();
	 * 
	 * public void run() { synchronized(synchStart){ synchStart.notifyAll(); }
	 * 
	 * try{sleep(webcam.milisecs);} catch(InterruptedException e){}
	 * 
	 * recording = false;
	 * 
	 * synchronized(synchStop){ synchStop.notifyAll(); } }//run }//Waiting
	 */
}