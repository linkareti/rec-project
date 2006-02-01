/*
 * AleatorioDriver.java
 *
 * Created on 6 de Junho de 2003, 11:23
 */

package pt.utl.ist.elab.driver.Aleatorio;

import com.linkare.rec.impl.driver.*;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.data.synch.*;
import java.util.logging.*;
import java.util.*;
import pt.utl.ist.elab.driver.Aleatorio.Hardware.*;
import pt.utl.ist.elab.driver.Aleatorio.Utils.*;



/**
 *
 * @author  PC
 */
public class AleatorioDriver extends BaseDriver{
    
    //private transient com.linkare.rec.impl.driver.IDriverStateListener = null;
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Aleatorio Driver)";
    private static final String DRIVER_UNIQUE_ID = "ELAB_ALEATORIO_V02";
    private static final String HW_VERSION = "0.1";
    
    private static AleatorioDriver SingletonDriver = null;
    
    private HardwareAcquisitionConfig config = null;
    private HardwareInfo info = null;
    private AleatorioDataSource aleatorioDataSource = null;
    
    public WebCamThread webcam = null;
    public SoundThread sound = null;
    private boolean recording = false;
    //private DataSourceReader dataSourceReader = null;
    private VideoReader videoReader = null;
        /*private FileOps file = null;
    public int[] statisticX = null;
    public int[] statisticY = null;*/
    
    private long movieLength;   //length of movie file in miliseconds
    private static final long MOVIE_LENGTH = 10000; //Default movie length in miliseconds
    private float waveFrequency;  //frequency of sound wave in Hz
    private static final float WAVE_FREQUENCY = 32.0F; //Default wave frequency inHz
    private float frequency1, frequency2;
    private float waveDuration;   //duration of the sound wave in seconds
    private static final float WAVE_DURATION = 3.0F; //Default wave duration in seconds
    private boolean movieOnOff; //true if the user wants to view the movie file; false otherwise
    private static final boolean MOVIE_ON_OFF = false;  //by default we'return not going to send
    //the movie file, since it takes up too much bandwidth
    private int numberOfSamples;    //number of images to be analyzed.
    private static final int NUMBER_OF_SAMPLES = 10;    //by default, we'll use 10 to get good statistics
    
    private static final int NUMBER_OF_DICE = 14;   //this is the original number of dice
    
    private static final int FRAME_RATE = 5;   //number of frames per second
    pt.utl.ist.elab.driver.Aleatorio.Utils.ImageAnalyser imageAnalyser = null;
    private java.awt.Image imageToAnalyze = null;
    
    private int centerCounter;
    private int[] centerCounterArray;
     
    private int[][] centers;
    
    private boolean waitingStart=false;
    
    /** Creates a new instance of AleatorioDriver */
    public AleatorioDriver() {
    }
    
    
    /*
    public static AleatorioDriver Create()
    {
        if (SingletonDriver==null)
            SingletonDriver = new AleatorioDriver();
        return SingletonDriver;
    }*/
    
    /**
     *BaseDriver implementation
     */
    public Object getHardwareInfo() {
        //com.linkare.rec.impl.protocols.ReCProtocols.init();
        
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/Aleatorio/AleatorioBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Aleatorio.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
            fireIDriverStateListenerDriverReseted();//why is this here and not in GDriver?
        }
        catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("Aleatorio.logger"));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
                fireIDriverStateListenerDriverReseted();//And again???
            }
            catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("Aleatorio.logger"));
            }
        }
        return url;
    }//getHardwareInfo()
    
    public String getDriverUniqueID() {
        return DRIVER_UNIQUE_ID;
    }//getDriverUniqueID()
    public void init(HardwareInfo info) {
        this.info = info;
        //System.out.println(" HardwareInfo read from a file is= " + info);
        //System.out.println(" Hardware Unique ID from file is: " + info.getHardwareUniqueID());
        //aleatorioDataSource = new AleatorioDataSource(this);
        HardwareInit hardware = new HardwareInit();
        this.webcam = hardware.getWebCamThread();
        this.sound = hardware.getSoundThread();
        readProperties();
        //while(!webcam.isVideoPlayerStarted()){System.out.println("waiting for videoplayer to be started!");}//wait for videoPlayer to start!
        //dataSourceReader = new DataSourceReader(webcam);
        
        fireIDriverStateListenerDriverInited();
    }//init(HardwareInfo info)
    public void config(HardwareAcquisitionConfig config,HardwareInfo info) throws IncorrectStateException,WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        //aleatorioDataSource = new AleatorioDataSource(this);
        info.validateConfig(config);
        
        //config.getSelectedHardwareParameter("BWThreshold").setParameterValue(getProps().getProperty("BWThreshold"));
        //e assim por diante... entendido? yep.
        
        
        try {configure(config, info);}
        catch (Exception e) {
            e.printStackTrace();
            throw new WrongConfigurationException("Erro no config...",20);
        }//aqui parece estar certo, mas precisas de fazer mais que isso... deves definir as totalSamples para cada canal, e aqueles par�metros pmarados que est�s a definir l� para baixo algures...
    }//config(HardwareAcquisitionConfig config,HardwareInfo info)
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        
        this.info = info;
        aleatorioDataSource = new AleatorioDataSource(this);
        ParameterConfig[] selectedParams=config.getSelectedHardwareParameters();
        
        for(int index = 0; index < selectedParams.length; index++) {
            if(selectedParams[index].getParameterName().equalsIgnoreCase("SoundWaveDuration"))
                try {waveDuration = Float.valueOf(selectedParams[index].getParameterValue()).floatValue()/1000;}
                catch(NumberFormatException e){waveDuration = WAVE_DURATION;}
            //System.out.println("soundDuration:"+waveDuration);
            if(selectedParams[index].getParameterName().equalsIgnoreCase("MovieOnOff"))
                try {
                    int onOff = Integer.valueOf(selectedParams[index].getParameterValue()).intValue();
                    movieOnOff = (onOff==0 ? false : true);
                }
                //try {movieOnOff = Boolean.valueOf(selectedParams[index].getParameterValue()).booleanValue();}
                catch(NumberFormatException e){movieOnOff = MOVIE_ON_OFF;}
            //System.out.println("show movie? " + movieOnOff);
            if(selectedParams[index].getParameterName().equalsIgnoreCase("NumberOfSamples"))
                try {numberOfSamples = Integer.valueOf(selectedParams[index].getParameterValue()).intValue();}
                catch(NumberFormatException e){numberOfSamples = NUMBER_OF_SAMPLES;}
            //System.out.println("NumberOfSamples:"+numberOfSamples);
            if(selectedParams[index].getParameterName().equalsIgnoreCase("InitialFrequency"))
                try {frequency1 = Float.valueOf(selectedParams[index].getParameterValue()).floatValue();}
                catch(NumberFormatException e){frequency1 = WAVE_FREQUENCY;}
            //System.out.println("Initial Freq:"+frequency1);
            if(selectedParams[index].getParameterName().equalsIgnoreCase("FinalFrequency"))
                try {frequency2 = Float.valueOf(selectedParams[index].getParameterValue()).floatValue();}
                catch(NumberFormatException e){frequency2 = WAVE_FREQUENCY;}
            //System.out.println("final Freq:"+frequency2);
        }
        //the movie lasts as long as the wave plus one second to watch the dice stopping
        this.config = config;
        
        movieLength = (long)java.lang.Math.round(waveDuration*1000 + 1000);
        webcam.configure(movieLength);
        
        webcam.videoPlayerStart();
        /*
        if (movieOnOff)
        {
            //dataSourceReader = new DataSourceReader(webcam);
            //dataSourceReader.open(webcam.getVideoDataSource());
            videoReader = new VideoReader(aleatorioDataSource, webcam, (int)(FRAME_RATE*movieLength/1000), FRAME_RATE);
        }
         */
        sound.configure(frequency1, frequency2, waveDuration);
        sound.newLine();
        fireIDriverStateListenerDriverConfigured();
    }//configure(HardwareAcquisitionConfig config, HardwareInfo info)
    public HardwareAcquisitionConfig getAcquisitionHeader() {
        //return config;
        
        return aleatorioDataSource.getAcquisitionHeader();
    }//getAcquisitionHeader()
    public IDataSource start(HardwareInfo info) throws IncorrectStateException {
        try
        {
        if(webcam != null && sound != null) {
            aleatorioDataSource = new AleatorioDataSource(this);
            fireIDriverStateListenerDriverStarting();
            centerCounterArray = new int[numberOfSamples];
            
            if (movieOnOff)
                videoReader = new VideoReader(aleatorioDataSource, webcam, (int)(FRAME_RATE*(double)movieLength/1000.), FRAME_RATE);
                     
            config.setTotalSamples(numberOfSamples+(movieOnOff?(numberOfSamples*(int)((double)FRAME_RATE*(double)movieLength/(double)1000.)):0));
            System.out.println("Smples expected: "+(numberOfSamples+(movieOnOff?(numberOfSamples*(int)((double)FRAME_RATE*(double)movieLength/(double)1000.)):0)));
            config.setTimeStart(new DateTime());
            
            for (int i = 0; i < config.getChannelsConfig().length; i++)
            {
                config.getChannelsConfig(i).setTimeStart(config.getTimeStart());
                config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
            }//for_i
            
            aleatorioDataSource.setAcquisitionHeader(config); // o teu current HardwareAcquisitionConfig...
            
            aleatorioDataSource.setTotalSamples(config.getTotalSamples());
            
            aleatorioDataSource.setPacketSize(1+(movieOnOff?Math.min(10,((int)((double)FRAME_RATE*(double)movieLength/(double)1000.))):0));
            
            fireIDriverStateListenerDriverStarted();
            
            if (aleatorioDataSource != null)//Aqui nunca seria null ou comes uma excep�~~ao antes
            {   
                //System.out.println(">>Starting Experiment!!");
                //aleatorioDataSource.setRunning(true);
                
                (new Thread(new AleatorioThread())).start();
                
                //System.out.println("Ja mesmo antes de retornar a DataSource="+aleatorioDataSource);
                //aleatorioDataSource.setRunning(false);
                return aleatorioDataSource;//Da mto trabalho tentares?se funcionar, nao!Nao sei se vai funcionar!olha
            }//if
        }//if
        }catch(Exception e)
        {
            System.out.println("Error occured while on Start...");
            e.printStackTrace();
        }
        return null;
    }//start(HardwareInfo info)
    public IDataSource startOutput(HardwareInfo info,IDataSource source) throws IncorrectStateException {
        /**Current Version doesn't support startOutput
         * Does nothing!*/
        return null;
    }//startOutput(HardwareInfo info,IDataSource source)
    public void stop(HardwareInfo info) throws IncorrectStateException {
        /*JP says: Don't use this!!
         And we don't use it!!  unless it's an emergency...*/
        fireIDriverStateListenerDriverStoping();
        aleatorioDataSource.stopNow();
        webcam.stopRec();
        sound.stopWave();
        fireIDriverStateListenerDriverStoped();
    }//stop(HardwareInfo info)
    public void reset(HardwareInfo info) throws IncorrectStateException {
        fireIDriverStateListenerDriverReseting();
        if (this.webcam != null)
            try {
                this.webcam.finalize();
            }catch(Exception e){e.printStackTrace();}
            catch(Throwable t){t.printStackTrace();}
        if (this.sound != null)
            try {
                this.sound.finalize();
            }catch(Exception e){e.printStackTrace();}
            catch(Throwable t){t.printStackTrace();}
        //initializes the hardware
        HardwareInit hardware = new HardwareInit();
        this.webcam = hardware.getWebCamThread();
        this.sound = hardware.getSoundThread();
        
        //reconfigures with the default values
        webcam.configure(MOVIE_LENGTH);
        sound.configure(WAVE_FREQUENCY, WAVE_FREQUENCY, WAVE_DURATION);
        sound.newLine();
        //dataSourceReader = new DataSourceReader(webcam);
        //dataSourceReader.open(webcam.getVideoDataSource());
        
        fireIDriverStateListenerDriverReseted();
    }//reset(HardwareInfo info)
    public void shutdown() {
        // There is nothing to shut down...
        //except the computer, but that's not going to happen, unless the power goes down...
        super.shutDownNow();
    }//shutdown()
    public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        //Not necessary
    }//extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
    
    public void setStoping() {
        fireIDriverStateListenerDriverStoping();
        webcam.stopRec();
        sound.stopWave();
    }
    
    public void setStoped() {
        fireIDriverStateListenerDriverStoped();
    }
    
    /**Properties stuff...*/
    private java.io.InputStream is=null;
    private java.io.File propFile=null;
    private String propertiesLocation=null;
    private java.util.Properties props=null;
    
    /**
     *Loads the properties File
     */
    public void readProperties() {
        
        propertiesLocation="./configurator/AleatorioConfigurator.properties";
        propFile=new java.io.File(propertiesLocation);
        
        try {
            is=new java.io.FileInputStream(propFile);
            props=new java.util.Properties();
            props.load(is);
            is.close();
        }
        catch(java.io.FileNotFoundException fnfe) {
            System.out.println("Couldn't find the file...\n"+fnfe);
            System.exit(0);
        }
        catch(java.io.IOException ioe) {
            System.out.println("Exception...\n"+ioe);
            System.exit(0);
        }
    }
    
    public java.util.Properties getProps() {
        return props;
    }
    
    private void analyseImage() {
        java.awt.Image image;
        if (movieOnOff)
        {
            //while(dataSourceReader.lastFrame == null){}
            while(imageToAnalyze == null){}
            image = imageToAnalyze;
        }else
            image = imageToAnalyze;
            //image = webcam.getImage();
        
        imageAnalyser = new pt.utl.ist.elab.driver.Aleatorio.Utils.ImageAnalyser(image);
        imageAnalyser.setParams(Integer.valueOf(props.getProperty("BWThreshold")).intValue(),
                                Integer.valueOf(props.getProperty("radiusOfSpot")).intValue(),
                                Integer.valueOf(props.getProperty("houghThreshold1")).intValue(),
                                Integer.valueOf(props.getProperty("houghThreshold2")).intValue(),
                                Integer.valueOf(props.getProperty("houghThreshold3")).intValue(),
                                Integer.valueOf(props.getProperty("convThreshold")).intValue(),
                                Integer.valueOf(props.getProperty("maxWidthOfDie")).intValue(),
                                Integer.valueOf(props.getProperty("numberOfDice")).intValue() );
        
        
        imageAnalyser.conversionBW();
        imageAnalyser.edgeDetector();
        imageAnalyser.houghTransform();
        imageAnalyser.houghCount();
        imageAnalyser.refineCount(imageAnalyser.IMAGE_HOUGH);
        //imageAnalyser.convolutionTransform();
        //imageAnalyser.fullCount();
        centers = imageAnalyser.getCenters();
        centerCounter = imageAnalyser.getCenterCounter();
        /*
        javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
        imagemDialog.setTitle("Imagem Capturada");
        ImageStorePanel imagemCapturadaPanel = new ImageStorePanel(imageAnalyser.getImage("count"));
        imagemDialog.getContentPane().add(imagemCapturadaPanel);
        int[] dimIm = imagemCapturadaPanel.imageSize();
        imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
        //imagemDialog.pack();
        imagemDialog.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        imagemDialog.show();
        imagemDialog.repaint();
        */
        imageAnalyser.resetImages();
        //int pos = indexOf(aleatorioDataSource.statisticX, centerCounter);
        //aleatorioDataSource.updateStatisticsFile(centerCounter, aleatorioDataSource.statisticY[pos]++);
    }
    
    /**
     *returns the index of the first occurrence of 'val' in 'array'
     *'array' is an array of ints and 'val' is an int
     */
    private int indexOf(int[] array, int val) {
        for(int index = 0; index < array.length; index++)
            if (array[index] == val)
                return index;
        return -1;
    }//indexOf(int[] array, int val)
    
    public int[][] getCenters() {
        return centers;
    }//getCenters()
    
    /*
    public javax.imageio.stream.ImageOutputStream image2jpegStream(java.awt.Image image) {
        byte[] tempArray = new byte[Integer.MAX_VALUE];
        javax.imageio.stream.ImageOutputStream ios = null;
        //tu j� viste a ByteArrayValBuffer... aceita ler de uma InputStream e d�-te um ByteArray j� porreiro para enviar pelo ReC...
        try{
            ios = javax.imageio.ImageIO.createImageOutputStream(tempArray);
            javax.imageio.ImageIO.write((java.awt.image.RenderedImage) image, "jpg", ios);
        }
        catch(java.io.IOException e){}
        return ios;
    }
    */
    class AleatorioThread implements java.lang.Runnable
    {
        public void run() {
            for (int index = 0; index < numberOfSamples; index++) {
                imageToAnalyze = null;
                webcam.recording=true;
                recording = true;
                final int pauseBetweenFrames = 1000/FRAME_RATE;
                
                if (movieOnOff)
                {
                    //while(!webcam.isVideoPlayerStarted()){System.out.println("waiting for videoplayer to be started!");}//wait for videoPlayer to start!
                    //System.out.println("videoPlayer started!");
                    RecordingThread recordingThread = new RecordingThread();
                    recordingThread.start();
                    while (!recording){
                        try{Thread.currentThread().sleep(pauseBetweenFrames*2);}
                        catch(InterruptedException e){}
                    }// do nothing while not recording
                    (new Thread(sound)).start();
                    while (recording){
                        try{Thread.currentThread().sleep(pauseBetweenFrames*2);}
                        catch(InterruptedException e){}
                    }  // do nothing here while recording!
                    //try{recordingThread.waiting.synchStop.wait();} catch(Exception e){}
                    //java.awt.Image[] movieFrames = recordingThread.getMovieFrames();
                    //for (int i=0; i < movieFrames.length; i++)
                    //{
                    //    if (movieFrames[i] != null)
                    //        aleatorioDataSource.sendMovieFrame(movieFrames[i]);
                    //}//for_i
                    //movieFrames=null;
                    //System.gc();
                }//if
                else
                {//se nao quisermos filme segue por aqui. isto funciona!
                    while(!webcam.isVideoPlayerStarted()){}//wait for videoPlayer to start!
                    (new Thread(webcam)).start();
                    (new Thread(sound)).start();
                    
                    while (webcam.recording){
                        try{Thread.currentThread().sleep(pauseBetweenFrames);}
                        catch(InterruptedException e){}
                    }  // do nothing while recording!
                }//else
                
                
                //reset sound so the image sent is stopped!
                sound.newLine();
                try{Thread.currentThread().sleep(pauseBetweenFrames);}
                catch(InterruptedException e){}
                
                
                imageToAnalyze = webcam.getImage();
                System.out.println(">>> Analysing Image...");
                analyseImage();                         //first, analyse the current image
                //aleatorioDataSource.sendImageCenters(webcam.getImage(), getCenters());  //then supply the data to the client
                //System.out.println(">>> Sending Centers!");
                //aleatorioDataSource.sendCenters(getCenters());
                centerCounterArray[index] = imageAnalyser.getCenterCounter();
                
                System.out.println(">>> Sending Image!");
                java.awt.Image webcamImage;
                if (movieOnOff)
                {
                    //while(dataSourceReader.lastFrame == null){}
                    while(videoReader.lastFrame == null){}
                    webcamImage = imageToAnalyze;
                }else
                    //webcamImage = webcam.getImage();
                    webcamImage = imageToAnalyze;
                while (webcamImage.getHeight(null) ==0){}   //do nothing while image is not ok
                aleatorioDataSource.sendImage(webcamImage, centerCounterArray[index]);
                //aleatorioDataSource.sendCenterCounter(centerCounterArray[index]);
                
                webcamImage.flush();
                System.gc();
                
                try {Thread.currentThread().sleep(50);}
                catch(InterruptedException e){e.printStackTrace();}
                catch(Exception e){e.printStackTrace();}
            }//end -for
            AleatorioDriver.this.fireIDriverStateListenerDriverStoping();            
            webcam.videoPlayerStop();
            //updates the Statistics data file after all samples (of this experiment) have been processed!
            for (int i = 0; i < numberOfSamples; i++) {
                System.out.println("numberOfspotsDetected="+centerCounterArray[i]);
                int numberOfDice;
                try {numberOfDice= Integer.parseInt(props.getProperty("numberOfDice"));}
                catch(NumberFormatException e){e.printStackTrace(); numberOfDice = NUMBER_OF_DICE;}
                //int numberOfDice = Integer.getInteger(props.getProperty("numberOfDice")).intValue();
                aleatorioDataSource.updateStatisticsFile(centerCounterArray[i], aleatorioDataSource.statisticY[centerCounterArray[i]-numberOfDice]++);
            }//for_i
            aleatorioDataSource.sessionStatisticsFile(centerCounterArray, Integer.parseInt(props.getProperty("numberOfDice")));
            System.out.println("Ended acquisition...");            
            AleatorioDriver.this.fireIDriverStateListenerDriverStoped();            
            aleatorioDataSource.endAcquisition();            
        }//run
    }//AleatorioThread
    
    class RecordingThread extends Thread
    {
        //private java.awt.Image[] movieFrames = null;
        //private int frameCount = 0;
        private static final int PAUSE_BETWEEN_FRAMES= 1000/FRAME_RATE;
        private int totalFrames=0;
        //public Waiting waiting = new Waiting();
        
        public RecordingThread()
        {
            setPriority(Thread.MAX_PRIORITY-1);//e a thread do som?.. MAX_PRIORITY?
            //movieFrames = new java.awt.Image[FRAME_RATE*(int)movieLength/1000];
        }//RecordingThread()
        
        public void run()
        {
            totalFrames=FRAME_RATE*(int)movieLength/1000;
            //movieFrames = new java.awt.Image[totalFrames];
            
            System.out.println("Filming!");
            
            /*
            try {dataSourceReader.startBuff(aleatorioDataSource, totalFrames);}
            catch (Exception e)
            {
                System.out.println("O StartBuff Tripou!!!");
                e.printStackTrace();
                recording = false;
            }
            
            while (dataSourceReader.startedFilming==false)
                try{this.sleep(50);}
                catch(InterruptedException e){}
            */
            recording=true;
            videoReader.start();
            //while(dataSourceReader.getFrameCounter() < totalFrames)
            while(videoReader.getFrameCounter() < totalFrames)
            {
                synchronized(this)
                {//if (dataSourceReader.getFrameCounter() >= totalFrames/2) recording=true;
                    try{this.wait(movieLength);}
                    catch(InterruptedException e){}
                }
            }//while
            
            //while (dataSourceReader.lastFrame == null){
            while (videoReader.lastFrame == null){
                synchronized(this)
                {
                    try{this.wait(movieLength);}
                    catch(InterruptedException e){}
                }
            }//while
            //imageToAnalyze = dataSourceReader.lastFrame;
            imageToAnalyze = videoReader.lastFrame;

            //System.out.println("frames caught:"+ totalFrames);
            System.gc();
            System.out.println("Done Filming!");
            recording=false;
        }//run
        
        /*public java.awt.Image[] getMovieFrames()
        {
            System.out.println("Getting movieFrames!");
            return movieFrames;//isto devia tar bom e nao retornar null...
            //nao sei se ja percebeste, mas o erro nao e ai...
        }//getMovieFrames
         */
    }//RecordingThread
    
   /* class Waiting extends Thread//esta class implementa aquilo que me tinhas explicado com os synchStart e os synchStop
    {
        public Object synchStart = new Object();
        public Object synchStop = new Object();
        
        public void run()
        {
            synchronized(synchStart){
                synchStart.notifyAll();
            }
            
            try{sleep(webcam.milisecs);}
            catch(InterruptedException e){}
            
            recording = false;
            
            synchronized(synchStop){
                synchStop.notifyAll();
            }
        }//run
    }//Waiting
         */  
}