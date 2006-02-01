/*
 * RobotServer.java
 *
 * Created on 14 de Dezembro de 2002, 14:52
 */

package pt.utl.ist.elab.driver.webrobot;


import com.linkare.rec.impl.data.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.webrobot.serial.*;

/**
 *
 * @author  Andr�
*/

import pt.utl.ist.elab.driver.webrobot.debug.*;

public class RobotStateMachine extends BaseDataSource implements pt.utl.ist.elab.driver.webrobot.interfaces.AuxVars,
                                                                pt.utl.ist.elab.driver.webrobot.interfaces.Sensors,
                                                                pt.utl.ist.elab.driver.webrobot.interfaces.InputOutput
                                        
{
    protected static String WR_DS_LOGGER="WebRobotStateMachine.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(WR_DS_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(WR_DS_LOGGER));
	}
    }
    
    //All default values...please change them with the config GUI
    
    /**The values of the infrared sensors (0-255) transmitted by the robot
     */
    private int i0State=255;
    private int i1State=255;
    private int i2State=255;
    private int i3State=255;
    private int i4State=255;
    private int i5State=255;
    private int i6State=255;
    private int i7State=255;
    /**Digital values 0 or 1 transmitted by the robot
     */
    private int b0State=0;
    private int b1State=0;
    private int b2State=0;
    private int b3State=0;
    private int b4State=0;
    private int b5State=0;
    private int b6State=0;
    private int b7State=0;
    private int c0State=0;
    private int c3State=0;
    /**Analagic values 0-255 transmitted by the robot
     */
    private int a1State=0;
    private int a2State=0;
    private int a3State=0;
    private int a4State=0;
    private int a5State=0;
    /**Auxiliary values 0-255 
     */
    private int v1State=0;
    private int v2State=0;
    private int v3State=0;
    private int v4State=0;
    private int v5State=0;
    /**Is b configured as IN or OUT?
     */
    private int b0InOut=0;
    private int b1InOut=0;
    private int b2InOut=0;
    private int b3InOut=0;
    private int b4InOut=0;
    private int b5InOut=0;
    private int b6InOut=0;
    private int b7InOut=0;
    private int c0InOut=0;
    private int c3InOut=0;
    /**The sensitivity of the IR sensors: If I > sensitivity it implies it is over
     *a reflecting surface. If the user wants to do something when it is a reflecting
     *surface, he must say I=0, otherwise say I=1...for more informations consult
     *the help from prograf or JPrograf
     */
    private int i0Sensitivity=0;
    private int i1Sensitivity=0;
    private int i2Sensitivity=0;
    private int i3Sensitivity=0;
    private int i4Sensitivity=0;
    private int i5Sensitivity=0;
    private int i6Sensitivity=0;
    private int i7Sensitivity=0;
    /**Is the program supposed to test Ix? If so then 0, otherwise 1;
     */
    private int i0OnOff=0;  
    private int i1OnOff=0;
    private int i2OnOff=0;
    private int i3OnOff=0;
    private int i4OnOff=0;
    private int i5OnOff=0;
    private int i6OnOff=0;
    private int i7OnOff=0;

    /**Properties stuff...*/     
    java.io.InputStream is=null;
    java.io.File propFile=null;
    String resourceLocation=null;
    java.util.Properties props=null;
    
    /**The file the user will send...actually he sends a String...a file is constructed...next version upgrade this pasticio
     */
    private java.io.File file;    
    
    /**Utility to open the file the user sent
     */
    private pt.utl.ist.elab.driver.webrobot.utils.OpenFile openFile;
    
    /**Matrix that will containy the blocks the user sent!
     */
    private pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock[][] matrix;
    
    /**Matrixs for the IVPWM block
     */
    private pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock[][] matrixIVPWM;
    private Object[][] iValues;
    
    /**Matrix for the connections
     */
    private String[][] matrixWiring;

    /**A general block, all blocks sent will be an instance of this one!
     */
    private pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock block;
        
    /**The current row and column, in the state machine
     */
    private int currentRow=2;
    private int currentCol=1;
    private int currentIVPWMcol=0;
    private int rowToSend=currentRow;
    private int colToSend=currentCol;
    private boolean restarted=false;
    private boolean finished=false;
    
    /**The block type
     */
    private int tipo=-1;    
    
    /**Auxiliary variables to keep the original row and column when treating and
     *type components
     */
    private int tempCurrentRow;
    private int tempCurrentCol;    
    private boolean wasAnd=false;
    
    /**Used for the components of type 3,4,5 and 6, that make robot walk in a 
     *direction for numberWalks*0,25 seconds
     */
    private int numberWalks=0;
    
    /**Use to yield the state machine, only used when the robot is "walking" with 
     *a component of type 3,4,5 or 6
     */
    private boolean walking=false;
    
    /**Use to reset the state machine
     */
    private boolean reset=false;
    
    /**Stop the state machine until new data comes from the robot
     */
    private boolean waitSerial=true;
    
    /**Thread variables
     */
    private boolean runningGeneralThread=false;
    private boolean runningDataThread=false;
    
    /**Is it a block of type 2 or 13?
     */
    private boolean blockBin=false;
    
    /**The two threads! One, generalThread, keeps the program alive and change the 
     *states in the state machine. The  other is for the blocks of type 3,4,5 and 6.
     */ 
    private Thread generalThread;
    private Thread dataProducerThread;

    /**Ask for serial data every x miliseconds*/
    private int ASKER_INT=250;
    
    /**The experiment duration in seconds*/
    private int EXP_DURATION=240;
    
    /**Counts the seconds for the pre-valued actuation block...*/
    private int counter=0;
    
    /**New data from the serial port is here!!!*/
    private boolean newData=true;
    
    /**The Driver*/
    private RobotDriver hardware=null;
    
    /**Is the robot parked*/
    private boolean parked=true;
    
    /**The IR values when is parked*/
    private int I0STATE_PARKED=255;
    private int I1STATE_PARKED=255;
    private int I2STATE_PARKED=255;
    private int I3STATE_PARKED=255;
    private int I4STATE_PARKED=255;
    private int I5STATE_PARKED=255;
    private int I6STATE_PARKED=255;
    private int I7STATE_PARKED=255;
    
    /**We want the value of the park to be less or more than(LMT) the sensor value?*/
    private String I0LMT_PARKED="<";
    private String I1LMT_PARKED="<";
    private String I2LMT_PARKED="<";
    private String I3LMT_PARKED="<";
    private String I4LMT_PARKED="<";
    private String I5LMT_PARKED="<";
    private String I6LMT_PARKED="<";
    private String I7LMT_PARKED="<";
    
    /**Variables for walking*/
    private int WALK_SPEED_AHEAH=200;
    private int WALK_SPEED_BACK=55;
    
    /**Counter to confirm the parking*/     
    private int counterParked=0;
    
    /**Battery vars*/
    private boolean batFull=false;
    private boolean batDown=false;
    private int counterBat=0;
    private int batDownVal=150;
    private int batFullVal=245;
    
    /**REC*/
    /**This running is different from the runningGeneralThread!! When running=false, 
     *it stops sending samples...the exp has not endend because I need to park the robot
     *first...but it will end soon...Conclusion:don't send more packets...*/
    volatile private boolean reseting=false;
    volatile private boolean stoping=false;    
    volatile private boolean stoped=false;
    private int numChannels=23;
    private PhysicsValue[] values=null;
    
    /**Register the number of samples sent to the user*/
    private int nSamples=0;
    private int totalSamples=0;
          
    /** Holds value of property flowString. */
    private String flowString=null;
    
    /**sensorsValues are the ones received from the robot, the problem is that the
     *digital values are encoded in one number, channelsValues are the values actually
     *sent to the client!*/
    private int[] sensorsValues;
    private int[] channelsValues;
    
    /**If the state machine reaches its end without sending any pwm...let's send
     *the last one*/
    private int tempPWM1=127;
    private int tempPWM2=127;
    private boolean sentPWM=false;
        
    /**To comunicate with the robot*/
    private SerialComm serialComm;    
    
    /**Border test vars*/
    private int borderSens=235;
    private int chargingValue=50;
    
    /**Vels max values*/
    private int MAX_PWM=255;
    private int MIN_PWM=0;
    
    /**Values for the different colors*/
    private int I2BORDER=242;
    private int I3BORDER=242;
    private int I4BORDER=242;
    private int I5BORDER=242;    
    
    /**The time that the data producer thread sleeps...*/
    private int acqTime=500;
    
    /** Creates a new instance of RobotServer */
    public RobotStateMachine(RobotDriver hardware) 
    {
        /**Read the properties from the file*/
        readProps();
        this.hardware=hardware;
        this.serialComm=hardware.getSerialComm();
        this.serialComm.registerStateMachine(this);
        
        /**Just for security, lets send the PWM to stop the robot*/
        sendPWM(127,127,false);
        
        /**Initialize sensorsValues*/
        sensorsValues=new int[13];
        for(int i=0;i<sensorsValues.length;i++)
        {
            sensorsValues[i]=0;
        }
        
        /**Initialize channelsValues*/        
        channelsValues=new int[numChannels];
        for(int i=0;i<numChannels;i++)
        {
            channelsValues[i]=0;
        }   
        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"State Machine Started!");
    }
    
    /*Method that reads the properties...*/
    private void readProps()            
    {
        resourceLocation="WebRobotProps.properties";
        propFile=new java.io.File(resourceLocation);
        try
        {
            is=new java.io.FileInputStream(propFile);
            props=new java.util.Properties();
            props.load(is);            
            is.close();
        }
        catch(java.io.FileNotFoundException fnfe)
        {          
            Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Couldn't found the file...\n"+fnfe);
            resourceLocation=getClass().getResource("/pt/utl/ist/elab/driver/webrobot/configs/WebRobotProps.properties").getFile();            
            try
            {
                propFile=new java.io.File(resourceLocation);
                is=new java.io.FileInputStream(propFile);
                props=new java.util.Properties();
                props.load(is);            
                is.close();
            }            
            catch(java.io.FileNotFoundException fnfe2)
            {          
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Couldn't found the file...\n"+fnfe2);
            }  
            catch(java.io.IOException ioe2)
            {
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Exception...\n"+ioe2);
            }                  
        }
        catch(java.io.IOException ioe)
        {
            Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Exception...\n"+ioe);
        } 
        try
        {
            WALK_SPEED_AHEAH=Integer.parseInt(props.getProperty("velAhead"),10);
            WALK_SPEED_BACK=Integer.parseInt(props.getProperty("velAback"),10);
            EXP_DURATION=Integer.parseInt(props.getProperty("expDuration"),10);
            I0STATE_PARKED=Integer.parseInt(props.getProperty("i0parked"),10);
            I1STATE_PARKED=Integer.parseInt(props.getProperty("i1parked"),10);
            I2STATE_PARKED=Integer.parseInt(props.getProperty("i2parked"),10);
            I3STATE_PARKED=Integer.parseInt(props.getProperty("i3parked"),10);
            I4STATE_PARKED=Integer.parseInt(props.getProperty("i4parked"),10);
            I5STATE_PARKED=Integer.parseInt(props.getProperty("i5parked"),10);
            I6STATE_PARKED=Integer.parseInt(props.getProperty("i6parked"),10);
            I7STATE_PARKED=Integer.parseInt(props.getProperty("i7parked"),10);
            batDownVal=Integer.parseInt(props.getProperty("batDownVal"),10);
            batFullVal=Integer.parseInt(props.getProperty("batFullVal"),10);
            I0LMT_PARKED=props.getProperty("I0LMT");
            I1LMT_PARKED=props.getProperty("I1LMT");
            I2LMT_PARKED=props.getProperty("I2LMT");
            I3LMT_PARKED=props.getProperty("I3LMT");
            I4LMT_PARKED=props.getProperty("I4LMT");
            I5LMT_PARKED=props.getProperty("I5LMT");
            I6LMT_PARKED=props.getProperty("I6LMT");
            I7LMT_PARKED=props.getProperty("I7LMT");
            MAX_PWM=Integer.parseInt(props.getProperty("MAXPWM"));
            MIN_PWM=Integer.parseInt(props.getProperty("MINPWM"));
            I2BORDER=Integer.parseInt(props.getProperty("I2BORDER"));
            I3BORDER=Integer.parseInt(props.getProperty("I3BORDER"));
            I4BORDER=Integer.parseInt(props.getProperty("I4BORDER"));
            I5BORDER=Integer.parseInt(props.getProperty("I5BORDER"));
        }
        catch(Exception e)
        {
            Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Erro ao ler as propriedades\n"+e);            
        }
    }

    /**Ask the robot to send serial data*/
    private synchronized void askSerial()
    {
        serialComm.write(83);
        serialComm.write(69);
        serialComm.write(78);
        serialComm.write(68);
        
        /**CHANGED*/
        //serialComm.write(65);
        serialComm.setReadMode();  
        
        /**CHANGED*/
        /*try
        {
            Thread.currentThread().sleep(15);
        }
        catch(InterruptedException ie)
        {
        }*/
    }
               
    /*Sends the data to the robot*/
    private class dataProducerThread extends Thread
    {
        public void run() 
        {
            while (runningDataThread)
            {			
                while(!reseting && !stoping)
                {                    
                    synchronized(channelsValues)
                    {                        
                        if(nSamples>totalSamples)
                        {
                            stoping=true;       			                            
                        }
                        nSamples++;
                        values=new PhysicsValue[numChannels];
                        
                        /**The 8 values from the infra-red sensors!!*/
                        for(int i=0;i<8;i++)
                        {
                            values[i]=new PhysicsValue(PhysicsValFactory.fromInt(channelsValues[i]),
                                                          getAcquisitionHeader().getChannelsConfig(i).getSelectedScale().getDefaultErrorValue(),                                                          
                                                          getAcquisitionHeader().getChannelsConfig(i).getSelectedScale().getMultiplier()
                                                       );
                        }
                        
                        /**The 8 values from the digital sensors!!*/
                        for(int i=8;i<16;i++)
                        {
                            boolean intToBoolean=false;
                            if(channelsValues[i]==1)
                            {
                                intToBoolean=true;
                            }
                            else
                            {
                                intToBoolean=false;
                            }
                            values[i]=new PhysicsValue(PhysicsValFactory.fromBoolean(intToBoolean),
                                                          getAcquisitionHeader().getChannelsConfig(i).getSelectedScale().getDefaultErrorValue(),                                                          
                                                          getAcquisitionHeader().getChannelsConfig(i).getSelectedScale().getMultiplier()
                                                       );
                        }

                        /**The 4 values from the analogic sensors!!*/
                        for(int i=16;i<20;i++)
                        {
                            values[i]=new PhysicsValue(PhysicsValFactory.fromInt(channelsValues[i]),
                                                          getAcquisitionHeader().getChannelsConfig(i).getSelectedScale().getDefaultErrorValue(),                                                          
                                                          getAcquisitionHeader().getChannelsConfig(i).getSelectedScale().getMultiplier()
                                                       );                            
                        }
                        
                        /**The current column in the IVPWM matrix*/
                        values[20]=new PhysicsValue(PhysicsValFactory.fromInt(currentIVPWMcol),
                                                        getAcquisitionHeader().getChannelsConfig(20).getSelectedScale().getDefaultErrorValue(),                                                          
                                                        getAcquisitionHeader().getChannelsConfig(20).getSelectedScale().getMultiplier()
                                                    );                            
                        
                        /**The current row in the main matrix*/
                        values[21]=new PhysicsValue(PhysicsValFactory.fromInt(rowToSend),
                                                        getAcquisitionHeader().getChannelsConfig(21).getSelectedScale().getDefaultErrorValue(),                                                          
                                                        getAcquisitionHeader().getChannelsConfig(21).getSelectedScale().getMultiplier()
                                                    );         
                        
                        /**The current column in the main matrix*/
                        values[22]=new PhysicsValue(PhysicsValFactory.fromInt(colToSend),
                                                        getAcquisitionHeader().getChannelsConfig(22).getSelectedScale().getDefaultErrorValue(),                                                          
                                                        getAcquisitionHeader().getChannelsConfig(20).getSelectedScale().getMultiplier()
                                                    );                                                    
                    
                        /**If the state machine has reseted I'll block the actualization
                         *of the row to send and column to send, since now I've sent the values,
                         *new values are very welcome*/                           
                        restarted=false;                       
                        
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Sending sample number:"+nSamples);
                        
                        /**Send...............................*/
                        addDataRow(values); 
                    }                                        
                    try 
                    {
                        dataProducerThread.sleep(2000);
                    }
                    catch(InterruptedException e)
                    {
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,e.toString());
                    }                                        
                } //end while(!reseting && !stoping)

                /**Change the hardware state to stoping*/
                hardware.setStoping();           

                endProduction(); 
                
                /**Park the robot*/
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Parking the robot");
                parkRobot();

                /**While the robot isn't parked stay in the stoping state. This
                 *way no one will take over the experiment*/
                while(stoping)
                {
                    /**Is the robot parked?*/
                    testPark();
                    /**You say it's parked...but please be sure...ask again
                     *If you've asked 2 times and the answer is always the same,
                     *then I guess it's parked...*/
                    if(parked&&counterParked>2)
                    {
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"The robot is Parked!!!");
                        stoping=false;
                        counterParked=0;
                        sendPWM(127,127,false);
                        killGeneralThread();
                    }
                    else
                    {
                        if(parked)
                        {
                            counterParked++;
                        }
                        else
                        {
                            counterParked=0;
                        }
                    }
                    try
                    {
                        Thread.currentThread().sleep(acqTime);
                    }
                    catch(InterruptedException e)
                    {
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,e.toString());
                    }                                            
                }//end while stoping
                
                /**If the robot has the battery low...I'm sorry but I can't change
                 *the state, it will remain stoping till the battery has a value 
                 *good enough*/
                int batCounter=0;
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"The min value for the bat is:"+batDownVal);
                
                while(batCounter<4)
                {
                    Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Current battery value is:"+a1State);
                    if(!isBatteryDown())
                    {
                        batCounter++;
                    }
                    else
                    {                        
                        batCounter=0;
                    }
                    try
                    {
                        sensorsValues=getSensorsValues();                        
                        Thread.currentThread().sleep(2000);
                    }
                    catch(InterruptedException ie)
                    {
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Interrupted Exception\n"+ie);
                    }                                            
                }
                stoped=true;
                
                /**OK! The experiment ended!!!*/
                hardware.setStoped();
                
                /**Join the Thread*/
                killProcucerThread();
            }        
            try
            {
                dataProducerThread.join(50); 
            }
            catch(InterruptedException ie)
            {                       
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Error in the thread\n"+ie);
            }       
            killGeneralThread();
            serialTimer.cancel();
        }
    }

    /**Since I send the values from an inner class, I must override this method in
     *the super class*/
    public void addDataRow(PhysicsValue[] dataSample)
    {
        super.addDataRow(dataSample);
    }     
    
    
    
    /*Simply join the thread*/
    private void killProcucerThread()
    {
        if(dataProducerThread==null||!dataProducerThread.isAlive())
        {
            return;
        }
        runningDataThread=false;        
    }
        
    private boolean init=false;
    
    /**Very important thread, it's the key of the experiment...controls the incrementation
     *of blocks in the stateMachine, and controls the pre-valued actuation blocks (frente,
     *direita,marchAtras e Esquerda*/
    private class generalThread extends Thread
    {
        public synchronized void run() 
        {
            while (runningGeneralThread)
            {             
                newData=false;
                sensorsValues=getSensorsValues();                        
                if(!stoping&&isOverBorder(init))
                {
                    sendPWM(127,127,false);
                    /**Let's wait to send to the user the reason why it overborded..*/
                    try
                    {
                        Thread.currentThread().sleep(acqTime);
                    }
                    catch(InterruptedException ie)
                    {
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Interrupted Exception:\n"+ie);
                    }
                    stoping=true;
                    finished=true;
                    walking=false;
                }

                /**I have new data, let's update the state machine, till it's
                 *restarted, then finished=true*/
                while(runningGeneralThread&&!finished&&!walking)
                {
                    stateMachine();                    
                    
                    /**CHANGED
                    try
                    {   
                        generalThread.sleep(5);//original 30
                    }
                    catch(InterruptedException ie)
                    {
                        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Interrupted Exception:\n"+ie);                        
                    }*/                          
                }                
                finished=false;
                
                /**Let's make the robot walk! (this blocks don't care about the
                 *state of the sensors...ugly blocks...*/               
                if(runningGeneralThread&&walking)
                {   
                    while(runningGeneralThread&&numberWalks>0)
                    {                    
                        numberWalks--; 
                        if(tipo==3)
                        {
                            sendPWM(WALK_SPEED_AHEAH,WALK_SPEED_AHEAH,false); 
                        }            
                        else if(tipo==4)
                        {
                            sendPWM(WALK_SPEED_BACK,WALK_SPEED_BACK,false);
                        }
                        else if(tipo==5)
                        {
                            sendPWM(WALK_SPEED_AHEAH,127,false);
                        }
                        else if(tipo==6)
                        {
                            sendPWM(127,WALK_SPEED_AHEAH,false);
                        }
                        
                        /**If the robot has walked for 1/2 second, then
                        *ask for serial data, I'll use it to control the border...
                        *and to send to the user...
                        */                                            
                        //askSerial();
                        sensorsValues=getSensorsValues();                        
                        if(!stoping&&isOverBorder(false))
                        {
                            sendPWM(127,127,false);
                            stoping=true;
                            walking=false;     
                            numberWalks=-1;
                        }                            
                        /**Make the thread sleep for 250ms*/ 
                        try 
                        {
                            generalThread.sleep(250);
                        }
                        catch (InterruptedException e) 
                        {
                            Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Error" + e);
                        }
                    }
                    counter=0;
                    setWalking(false);                                     
                } 
                /**Make the thread sleep for 25ms*/
                
                /***CHANGED*/
                /*try 
                {
                    generalThread.sleep(10);//original 25
                }
                catch (InterruptedException e) 
                {
                    Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Error" + e);
                }*/                            
            }
            try
            {
                generalThread.join(100); 
            }
            catch(InterruptedException ie)
            {                       
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Error in the thread\n"+ie);
            }                                
        }
    }    


    /**Join the general thread*/
    private void killGeneralThread()
    {
        if(generalThread==null||!generalThread.isAlive())
        {
            return;
        }
        runningGeneralThread=false;
        sendPWM(127,127,false);
    }
    
    /**Starts the state machine!*/
    public void startStateMachine(String flowString)
    {
        java.io.File flowFile=new java.io.File("tempFlow.grf");                
        try
        {
            java.io.FileWriter fileWriter=new java.io.FileWriter(flowFile);
            fileWriter.write(flowString);
            fileWriter.close();
        }
        catch(java.io.IOException ioe)
        {
            Logger.getLogger(WR_DS_LOGGER).log(Level.INFO, "Error when writing the flow file\n"+ioe);
        }
        startStateMachine(flowFile);
    }
    
    public void startStateMachine(java.io.File file)
    {
        /**Get the file the user sent!*/
        /**Open the file, construct the matrixs and come back!*/
        openFile=new pt.utl.ist.elab.driver.webrobot.utils.OpenFile(this,file);
        matrix=openFile.getMatrix();
        matrixWiring=openFile.getMatrixWiring();
        iValues=openFile.getIValues();
        
        /**Create the matrix for the IVPWM blocks!
         */
        createIVPWMatrix(iValues);        
        
        /**Restart the state machine (set the state machine in the beggining positions
         */
        restartStateMachine();                        
    }
        
    /**This method creates the ivpwm matrix!*/
    private void createIVPWMatrix(Object[][] iValues)
    {
        /**How big will be this matrix?*/
        int numRowsIValue=iValues.length;
        int iRowIVPWM=0;
        int numCols=countRealColumns();
        matrixIVPWM=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock[numCols+1][numRowsIValue];
        
        /**If the sensor X is to be read that one creates a row for this sensor
         *if we have 5 sensors to be read, we have 5 rows, and then columns with
         *combinations from this sensors!
         */
        for(int iRow=0;iRow<numRowsIValue;iRow++)
        {       
                iRowIVPWM=0;
                if(getI0OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I0");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(7,8))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI0Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI1OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I1");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(6,7))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI1Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI2OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I2");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(5,6))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI2Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI3OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I3");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(4,5))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI3Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI4OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I4");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(3,4))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI4Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI5OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I5");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(2,3))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI5Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI6OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I6");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(1,2))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI6Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
                if(getI7OnOff()==0)
                {
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(12);
                    block.setD1("I7");
                    if(Integer.parseInt(iValues[iRow][0].toString().substring(0,1))==0)
                    {
                        block.setD2(">");
                    }
                    else
                    {
                        block.setD2("<");
                    }
                    block.setValor(getI7Sensitivity());
                    matrixIVPWM[iRowIVPWM][iRow]=block;
                    iRowIVPWM++;
                }
            /**At the end of every column create a PWM block, that will send
             *to the robot the pwm values if all the conditions above it were 
             *satisfied 
             */
            block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();                
            block.setTipo(8);
            block.setValor(Integer.parseInt(iValues[iRow][1].toString()));
            block.setValor2(Integer.parseInt(iValues[iRow][2].toString()));
            matrixIVPWM[iRowIVPWM][iRow]=block;
        }  
    }
    
    /**Count the real number of columns. If there were 5 sensors selected, then
     *it will return 5!
     */
    private int countRealColumns()
    {
        int total=0;        
        if(getI0OnOff()==0)
        {
            total++;
        }
        if(getI1OnOff()==0)
        {
            total++;
        }
        if(getI2OnOff()==0)
        {
            total++;
        }
        if(getI3OnOff()==0)
        {
            total++;
        }
        if(getI4OnOff()==0)
        {
            total++;
        }
        if(getI5OnOff()==0)
        {
            total++;
        }
        if(getI6OnOff()==0)
        {
            total++;
        }
        if(getI7OnOff()==0)
        {
            total++;
        }
        return total;
    }

    /**The state machine!!!!!*/
    private void stateMachine()
    {        
        if(!restarted)
        {
            rowToSend=currentRow;
            colToSend=currentCol;
        }
        /**Gets the block to test!
         */               
        pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock stateBlock=matrix[currentRow][currentCol];
        
        /**If the block is null do one of two:
         *1-Restart the state machine (if there isn't a connection)
         *2-If there's a connection increment the column
         */       
        if(stateBlock==null)
        {
            if(matrixWiring[currentRow][currentCol]==null)
            {
                restartStateMachine();
                return;
            }
            else
            {
                currentCol++;
                return;
            }
        }
        /**Gets the values to compare with
         */
        int d1=getDIntValue(stateBlock.getD1());
        int d3=getDIntValue(stateBlock.getD3());
        int valor=stateBlock.getValor();
        int valor2=stateBlock.getValor2();
        int flag=stateBlock.getFlag();
           
        if(stateBlock.getTipo()==1||stateBlock.getTipo()==2||
            stateBlock.getTipo()==12||stateBlock.getTipo()==13)
        {
            /**If the flag is equal to 1, then the comparation is to be done with
             *a number, otherwise it will be done with a variable*/
            if(flag==1)
            {
                d3=valor;                
            }
            if(stateBlock.getTipo()==2||stateBlock.getTipo()==13)
            {
                blockBin=true;
            }
            else
            {
                blockBin=false;
            }
            if((stateBlock.getTipo()==12||stateBlock.getTipo()==13)&&!wasAnd)
            {
                wasAnd=true;
                tempCurrentRow=currentRow;
            }
            if(stateBlock.getD2().trim().equalsIgnoreCase("<"))
            {
                if(d1<d3)
                {
                    currentRow++;
                }                
                else
                {                    
                    if(stateBlock.getTipo()==12||stateBlock.getTipo()==13)
                    {                 
                        currentRow=tempCurrentRow;
                        wasAnd=false;
                    }                    
                    currentCol++;
                    if(matrixWiring[currentRow][currentCol]==null)
                    {
                        restartStateMachine();
                    }
                }
                return;
            }
            else if(stateBlock.getD2().trim().equalsIgnoreCase("=")||blockBin)                
            {
                if(blockBin)
                {
                    d3=valor;
                }
                if(d1==d3)
                {
                    currentRow++;
                }                
                else
                {
                    if(stateBlock.getTipo()==12||stateBlock.getTipo()==13)
                    {
                        currentRow=tempCurrentRow;
                        wasAnd=false;
                    }
                    currentCol++;
                    if(matrixWiring[currentRow][currentCol]==null)
                    {
                        restartStateMachine();
                    }
                }
                return;
            }
            else if(stateBlock.getD2().trim().equalsIgnoreCase(">"))                
            {
                if(d1>d3)
                {
                    currentRow++;
                }                
                else
                {
                    if(stateBlock.getTipo()==12||stateBlock.getTipo()==13)
                    {
                        currentRow=tempCurrentRow;
                        wasAnd=false;
                    }
                    currentCol++;
                    if(matrixWiring[currentRow][currentCol]==null)
                    {
                        restartStateMachine();
                    }
                }
                return;
            }
        }
        else if(stateBlock.getTipo()==11)
        {
            int v;
            if(stateBlock.getD1().trim().equalsIgnoreCase("V1"))
            {
                v=getV1State();
                if(flag!=1)
                {
                    v++;
                    setV1State(v);
                }
                else
                {
                    v--;
                    setV1State(v);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V2"))
            {
                v=getV2State();
                if(flag!=1)
                {
                    v++;
                    setV2State(v);
                }
                else
                {
                    v--;
                    setV2State(v);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V3"))
            {
                v=getV4State();
                if(flag!=1)
                {
                    v++;
                    setV3State(v);
                }
                else
                {
                    v--;
                    setV3State(v);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V4"))
            {
                v=getV4State();
                if(flag!=1)
                {
                    v++;
                    setV4State(v);
                }
                else
                {
                    v--;
                    setV4State(v);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V5"))
            {
                v=getV5State();
                if(flag!=1)
                {
                    v++;
                    setV5State(v);
                }
                else
                {
                    v--;
                    setV5State(v);
                }
            }
            currentRow++;
            return;
        }
        else if(stateBlock.getTipo()==10)
        {
            int v=-1;
            if(stateBlock.getFlag()==1)
            {
                v=stateBlock.getValor();
            }
            if(stateBlock.getD1().trim().equalsIgnoreCase("V1"))
            {
                if(stateBlock.getFlag()==1)
                {                    
                    setV1State(v);
                }
                else
                {
                    setV1State(d3);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V2"))
            {
                if(stateBlock.getFlag()==1)
                {
                    setV2State(v);
                }
                else
                {
                    setV2State(d3);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V3"))
            {
                if(stateBlock.getFlag()==1)
                {
                    setV3State(v);
                }
                else
                {
                    setV3State(d3);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V4"))
            {
                if(stateBlock.getFlag()==1)
                {
                    setV4State(v);
                }
                else
                {
                    setV4State(d3);
                }
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("V5"))
            {
                if(stateBlock.getFlag()==1)
                {
                    setV5State(v);
                }
                else
                {
                    setV5State(d3);
                }
            }                        
        currentRow++;
        return;            
        }
        else if(stateBlock.getTipo()==9)
        {
            if(stateBlock.getD1().trim().equalsIgnoreCase("b0"))
            {
                setB0State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b1"))
            {
                setB1State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b2"))
            {
                setB2State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b3"))
            {
                setB3State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b4"))
            {
                setB4State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b5"))
            {
                setB5State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b6"))
            {
                setB6State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("b7"))
            {
                setB7State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("c0"))
            {
                setC0State(valor);
            }
            else if(stateBlock.getD1().trim().equalsIgnoreCase("c3"))
            {
                setC3State(valor);
            }
            currentRow++;
            return;            
        }
        else if(stateBlock.getTipo()==8)
        {
            sendPWM(valor,valor2,true);
            currentRow++;
            return;
        }
        else if(stateBlock.getTipo()==3||stateBlock.getTipo()==4||
                    stateBlock.getTipo()==5||stateBlock.getTipo()==6)
        {
            tipo=stateBlock.getTipo();
            numberWalks=stateBlock.getValor();
            currentRow++;
            setWalking(true);
            return;
        }
        
        /**Do an internal cicle in the IVPWM matrix
         */
        else if(stateBlock.getTipo()==14)
        {
            for(int iCol=0;iCol<matrixIVPWM[0].length;iCol++)
            {
                for(int iRow=0;iRow<matrixIVPWM.length;iRow++)
                {
                    if(matrixIVPWM[iRow][iCol]==null)
                    {
                        continue;
                    }
                    d1=getDIntValue(matrixIVPWM[iRow][iCol].getD1());
                    d3=matrixIVPWM[iRow][iCol].getValor();
                    if(matrixIVPWM[iRow][iCol].getD2().trim().equalsIgnoreCase("<"))
                    {
                        if(d1>d3)
                        {
                            break;
                        }
                    }            
                    else if(matrixIVPWM[iRow][iCol].getD2().trim().equalsIgnoreCase(">"))                
                    {
                        if(d1<d3)
                        {
                            break;
                        }                
                    }
                    if(matrixIVPWM[iRow][iCol].getTipo()==8)
                    {
                        currentIVPWMcol=iCol;
                        sendPWM(matrixIVPWM[iRow][iCol].getValor(), matrixIVPWM[iRow][iCol].getValor2(),true);
                        currentRow++;
                        return;
                    }
                }
            }
            /**If arrived here then none of the conditions were satisfied...
             *let's send PWM=127
             */
            sendPWM(127, 127,true);
            currentRow++;
            return;
        }
    }
    
    /**Sends the pwm to the motors
     */
    private void sendPWM(int valor, int valor2, boolean waitSerial)
    {   
        if(waitSerial)
        {
            setWaitSerial(true);
        }
        else
        {
            setWaitSerial(false);
        }   
        
        /**CHANGED*/
        /*try
        {
            Thread.currentThread().sleep(50);//original 50
        }
        catch(InterruptedException ie)
        {
            Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,ie);
        }*/
        if(valor>MAX_PWM)
        {
            valor=MAX_PWM;
        }
        if(valor<MIN_PWM)
        {
            valor=MIN_PWM;
        }
        if(valor2>MAX_PWM)
        {
            valor2=MAX_PWM;
        }
        if(valor2<MIN_PWM)
        {
            valor2=MIN_PWM;
        }        
        
        currentPWM1=valor;
        currentPWM2=valor2;
    }
    
    private int currentPWM1=127;
    private int currentPWM2=127;
    
    private void sendSerialPWM(int PWM1, int PWM2)
    {
        serialComm.write(80);
        serialComm.write(87);
        serialComm.write(77);
        serialComm.write(PWM1);
        serialComm.write(PWM2);                 
    }

    /**Returns the value of the asked sensors
     */
    private int getDIntValue(String strd1)
    {
        if(strd1.trim().equalsIgnoreCase("I0"))
        {
            return getI0State();
        }
        else if(strd1.trim().equalsIgnoreCase("I1"))
        {
            return getI1State();
        }
        else if(strd1.trim().equalsIgnoreCase("I2"))
        {
            return getI2State();
        }
        else if(strd1.trim().equalsIgnoreCase("I3"))
        {
            return getI3State();
        }
        else if(strd1.trim().equalsIgnoreCase("I4"))
        {
            return getI4State();
        }
        else if(strd1.trim().equalsIgnoreCase("I5"))
        {
            return getI5State();
        }
        else if(strd1.trim().equalsIgnoreCase("I6"))
        {
            return getI6State();
        }
        else if(strd1.trim().equalsIgnoreCase("I7"))
        {
            return getI7State();
        }
        else if(strd1.trim().equalsIgnoreCase("b0"))
        {
            return getB0State();
        }
        else if(strd1.trim().equalsIgnoreCase("b1"))
        {
            return getB1State();
        }
        else if(strd1.trim().equalsIgnoreCase("b2"))
        {
            return getB2State();
        }
        else if(strd1.trim().equalsIgnoreCase("b3"))
        {
            return getB3State();
        }
        else if(strd1.trim().equalsIgnoreCase("b4"))
        {
            return getB4State();
        }
        else if(strd1.trim().equalsIgnoreCase("b5"))
        {
            return getB5State();
        }
        else if(strd1.trim().equalsIgnoreCase("b6"))
        {
            return getB6State();
        }
        else if(strd1.trim().equalsIgnoreCase("b7"))
        {
            return getB7State();
        }
        else if(strd1.trim().equalsIgnoreCase("c0"))
        {
            return getC0State();
        }
        else if(strd1.trim().equalsIgnoreCase("c3"))
        {
            return getC3State();
        }
        else if(strd1.trim().equalsIgnoreCase("V1"))
        {
            return getV1State();
        }
        else if(strd1.trim().equalsIgnoreCase("V2"))
        {
            return getV2State();
        }
        else if(strd1.trim().equalsIgnoreCase("V3"))
        {
            return getV3State();
        }
        else if(strd1.trim().equalsIgnoreCase("V4"))
        {
            return getV4State();
        }
        else if(strd1.trim().equalsIgnoreCase("V5"))
        {
            return getV5State();
        }
        else if(strd1.trim().equalsIgnoreCase("A1"))
        {
            return getA1State();
        }
        else if(strd1.trim().equalsIgnoreCase("A2"))
        {
            return getA2State();
        }
        else if(strd1.trim().equalsIgnoreCase("A3"))
        {
            return getA3State();
        }
        else if(strd1.trim().equalsIgnoreCase("A4"))
        {
            return getA4State();
        }
        else if(strd1.trim().equalsIgnoreCase("A5"))
        {
            return getA5State();
        }          
        return -1;
    }

    /**Restarts the state machine
     */
    private void restartStateMachine()
    {
        rowToSend=currentRow-1;
        colToSend=currentCol;
        restarted=true;
        
        /**CHANGED*/
        /*try
        {
            Thread.currentThread().sleep(20);
        }
        catch(InterruptedException ie)
        {
        }*/
        finished=true;
        currentRow=2;
        currentCol=1;
    }

    public synchronized int[] getSensorsValues()
    {
        synchronized(sensorsValues)
        {           
            sensorsValues=serialComm.getSensorsState();
            if(sensorsValues==null||sensorsValues.length<12)
            {
                sensorsValues=new int[13];
                for(int i=0;i<sensorsValues.length;i++)
                {
                    sensorsValues[i]=0;
                }
                return sensorsValues;
            }
            if(channelsValues==null||channelsValues.length<numChannels)
            {
                channelsValues=new int[numChannels];
                for(int i=0;i<numChannels;i++)
                {   
                    channelsValues[i]=0;
                }            
            }
            
            /**Convert byte to int*/
            for(int i=0;i<sensorsValues.length;i++)
            {
                if(sensorsValues[i]<0)
                {
                    sensorsValues[i]+=256;
                }
            }
            setI0State(sensorsValues[0]);
            setI1State(sensorsValues[1]);
            setI2State(sensorsValues[2]);
            setI3State(sensorsValues[3]);
            setI4State(sensorsValues[4]);
            setI5State(sensorsValues[5]);
            setI6State(sensorsValues[6]);
            setI7State(sensorsValues[7]);
            
            /**Write the infrared sensors values to the channelsvalues*/
            for(int i=0;i<8;i++)
            {
                channelsValues[i]=sensorsValues[i];
            }
            
            /**Digital sensors*/
            if((sensorsValues[8]&1)==1)
            {
                setB0State(1);
            }
            else
            {
                setB0State(0);
            }
            channelsValues[8]=b0State;
            
            if((sensorsValues[8]&2)==2)
            {
                setB1State(1);
            }
            else
            {
                setB1State(0);
            }
            channelsValues[9]=b1State;
            
            if((sensorsValues[8]&4)==4)
            {
                setB2State(1);
            }
            else
            {
                setB2State(0);
            }
            channelsValues[10]=b2State;
            
            if((sensorsValues[8]&8)==8)
            {
                setB3State(1);
            }
            else
            {
                setB3State(0);
            }
            channelsValues[11]=b3State;
            
            if((sensorsValues[8]&16)==16)
            {
                setB4State(1);
            }
            else
            {
                setB4State(0);
            }
            channelsValues[12]=b4State;
            
            if((sensorsValues[8]&32)==32)
            {
                setB5State(1);
            }
            else
            {
                setB5State(0);
            }
            channelsValues[13]=b5State;
            
            if((sensorsValues[8]&64)==64)
            {
                setB6State(1);
            }
            else
            {
                setB6State(0);
            }
            channelsValues[14]=b6State;
            
            if((sensorsValues[8]&128)==128)
            {
                setB7State(1);
            }
            else
            {
                setB7State(0);
            }
            channelsValues[15]=b7State;
            
            /**Analogic sensors*/
            setA1State(sensorsValues[9]);
            setA2State(sensorsValues[10]);
            setA3State(sensorsValues[11]);
            setA4State(sensorsValues[12]);            
            for(int i=16;i<20;i++)
            {
                channelsValues[i]=sensorsValues[i-7];
            }        
            return sensorsValues;
        }
    }    
     
    private synchronized void parkRobot()
    {
        killGeneralThread();
        try
        {
            Thread.currentThread().sleep(5000);
        }
        catch(InterruptedException ie)
        {
        }               
        sendPWM(127,127,false);
        file=new java.io.File("parkRobot.grf");                
        startStateMachine(file);   
        generalThread=null;
        runningGeneralThread=true;
        generalThread=new generalThread();
        generalThread.setPriority(generalThread.MAX_PRIORITY);
        generalThread.start();  
        sendPWM(127,127,false);
        stoping=true;        
    }
    
    private boolean testPark()
    {
        parked=false;
        if(a2State>20)
        {
            parked=true;
        }
        return parked;               
    }
    
    private boolean isBatteryDown()
    {
        if(a1State<batDownVal)
        {
            batDown=true;
        }
        else
        {
            batDown=false;
        }
        return batDown;
    }
    
    private boolean isBatteryFull()
    {
        if(a1State>batFullVal)
        {
            batFull=true;
        }
        else
        {
            batFull=false;
        }
        return batFull;
    }
    
    private boolean isOverBorder(boolean init)
    {
        boolean overBoarder=false;
        if((i2State>I2BORDER||i3State>I3BORDER||
            i4State>I4BORDER||i5State>I5BORDER))
        {
            overBoarder=true;
            if(!init)
            {
                sendPWM(127,127,false);
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"The border was crossed!!!!");
            }
            
        }
        return overBoarder;
    }
            
    /** Getter for property a1State.
     * @return Value of property a1State.
     */
    public int getA1State() {
        return a1State;
    }
    
    /** Getter for property a2State.
     * @return Value of property a2State.
     */
    public int getA2State() {
        return a2State;
    }
    
    /** Getter for property a3State.
     * @return Value of property a3State.
     */
    public int getA3State() {
        return a3State;
    }
    
    /** Getter for property a4State.
     * @return Value of property a4State.
     */
    public int getA4State() {
        return a4State;
    }
    
    /** Getter for property a5State.
     * @return Value of property a5State.
     */
    public int getA5State() {
        return a5State;
    }
    
    /** Getter for property b0State.
     * @return Value of property b0State.
     */
    public int getB0State() {
        return b0State;
    }
    
    /** Getter for property b1State.
     * @return Value of property b1State.
     */
    public int getB1State() {
        return b1State;
    }
    
    /** Getter for property b2State.
     * @return Value of property b2State.
     */
    public int getB2State() {
        return b2State;
    }
    
    /** Getter for property b3State.
     * @return Value of property b3State.
     */
    public int getB3State() {
        return b3State;
    }
    
    /** Getter for property b4State.
     * @return Value of property b4State.
     */
    public int getB4State() {
        return b4State;
    }
    
    /** Getter for property b5State.
     * @return Value of property b5State.
     */
    public int getB5State() {
        return b5State;
    }
    
    /** Getter for property b6State.
     * @return Value of property b6State.
     */
    public int getB6State() {
        return b6State;
    }
    
    /** Getter for property b7State.
     * @return Value of property b7State.
     */
    public int getB7State() {
        return b7State;
    }
    
    /** Getter for property i0State.
     * @return Value of property i0State.
     */
    public int getI0State() {
        return i0State;
    }
    
    /** Getter for property i1State.
     * @return Value of property i1State.
     */
    public int getI1State() {
        return i1State;
    }
    
    /** Getter for property i2State.
     * @return Value of property i2State.
     */
    public int getI2State() {
        return i2State;
    }
    
    /** Getter for property i3State.
     * @return Value of property i3State.
     */
    public int getI3State() {
        return i3State;
    }
    
    /** Getter for property i4State.
     * @return Value of property i4State.
     */
    public int getI4State() {
        return i4State;
    }
    
    /** Getter for property i1State.
     * @return Value of property i5State.
     */
    public int getI5State() {
        return i5State;
    }
    
    /** Getter for property i6State.
     * @return Value of property i6State.
     */
    public int getI6State() {
        return i6State;
    }
    
    /** Getter for property i7State.
     * @return Value of property i7State.
     */
    public int getI7State() {
        return i7State;
    }
    
    /** Getter for property v1State.
     * @return Value of property v1State.
     */
    public int getV1State() {
        return v1State;
    }
    
    /** Getter for property v2State.
     * @return Value of property v2State.
     */
    public int getV2State() {
        return v2State;
    }
    
    /** Getter for property v3State.
     * @return Value of property v3State.
     */
    public int getV3State() {
        return v3State;
    }
    
    /** Getter for property v4State.
     * @return Value of property v4State.
     */
    public int getV4State() {
        return v4State;
    }
    
    /** Getter for property v5State.
     * @return Value of property v5State.
     */
    public int getV5State() {
        return v5State;
    }
    
    /** Getter for property c0State.
     * @return Value of property c0State.
     */
    public int getC0State() {
        return c0State;
    }       
    
    /** Getter for property c3State.
     * @return Value of property c3State.
     */
    public int getC3State() {
        return c3State;
    }
        
    /** Setter for property a1State.
     * @param a1State New value of property a1State.
     */
    public void setA1State(int a1State) {
        this.a1State=a1State;
    }
    
    /** Setter for property a2State.
     * @param a2State New value of property a2State.
     */
    public void setA2State(int a2State) {
        this.a2State=a2State;
    }
    
    /** Setter for property a3State.
     * @param a3State New value of property a3State.
     */
    public void setA3State(int a3State) {
        this.a3State=a3State;
    }
    
    /** Setter for property a4State.
     * @param a4State New value of property a4State.
     */
    public void setA4State(int a4State) {
        this.a4State=a4State;
    }
    
    /** Setter for property a5State.
     * @param a5State New value of property a5State.
     */
    public void setA5State(int a5State) {
        this.a5State=a5State;
    }
    
    /** Setter for property b0State.
     * @param b0State New value of property b0State.
     */
    public void setB0State(int b0State) {
        this.b0State=b0State;
    }
    
    /** Setter for property b1State.
     * @param b1State New value of property b1State.
     */
    public void setB1State(int b1State) {
        this.b1State=b1State;
    }
    
    /** Setter for property b2State.
     * @param b2State New value of property b2State.
     */
    public void setB2State(int b2State) {
        this.b2State=b2State;
    }
    
    /** Setter for property b3State.
     * @param b3State New value of property b3State.
     */
    public void setB3State(int b3State) {
        this.b3State=b3State;
    }
    
    /** Setter for property b4State.
     * @param b4State New value of property b4State.
     */
    public void setB4State(int b4State) {
        this.b4State=b4State;
    }
    
    /** Setter for property b5State.
     * @param b5State New value of property b5State.
     */
    public void setB5State(int b5State) {
        this.b5State=b5State;
    }
    
    /** Setter for property b6State.
     * @param b6State New value of property b6State.
     */
    public void setB6State(int b6State) {
        this.b6State=b6State;
    }
    
    /** Setter for property b7State.
     * @param b7State New value of property b7State.
     */
    public void setB7State(int b7State) {
        this.b7State=b7State;
    }
    
    /** Setter for property i0State.
     * @param i0State New value of property i0State.
     */
    public void setI0State(int i0State) {
        this.i0State=i0State;
    }
    
    /** Setter for property i1State.
     * @param i1State New value of property i1State.
     */
    public void setI1State(int i1State) {
        this.i1State=i1State;
    }
    
    /** Setter for property i2State.
     * @param i1State New value of property i2State.
     */
    public void setI2State(int i2State) {
        this.i2State=i2State;
    }
    
    /** Setter for property i3State.
     * @param i1State New value of property i3State.
     */
    public void setI3State(int i3State) {
        this.i3State=i3State;
    }
    
    /** Setter for property i4State.
     * @param i1State New value of property i4State.
     */
    public void setI4State(int i4State) {
        this.i4State=i4State;
    }
    
    /** Setter for property i5State.
     * @param i1State New value of property i5State.
     */
    public void setI5State(int i5State) {
        this.i5State=i5State;
    }
    
    /** Setter for property i6State.
     * @param i1State New value of property i6State.
     */
    public void setI6State(int i6State) {
        this.i6State=i6State;
    }
    
    /** Setter for property i7State.
     * @param i1State New value of property i7State.
     */
    public void setI7State(int i7State) {
        this.i7State=i7State;
    }
    
    /** Setter for property v1State.
     * @param v1State New value of property v1State.
     */
    public void setV1State(int v1State) {
        this.v1State=v1State;
    }
    
    /** Setter for property v2State.
     * @param v2State New value of property v2State.
     */
    public void setV2State(int v2State) {
        this.v2State=v2State;
    }
    
    /** Setter for property v3State.
     * @param v3State New value of property v3State.
     */
    public void setV3State(int v3State) {
        this.v3State=v3State;
    }
    
    /** Setter for property v4State.
     * @param v4State New value of property v4State.
     */
    public void setV4State(int v4State) {
        this.v4State=v4State;
    }
    
    /** Setter for property v5State.
     * @param v5State New value of property v5State.
     */
    public void setV5State(int v5State) {
        this.v5State=v5State;
    }

    /** Setter for property c0State.
     * @param c0State New value of property c0State.
     */
    public void setC0State(int c0State) {
        this.c0State=c0State;
    }
        
    /** Setter for property c3State.
     * @param c3State New value of property c3State.
     */
    public void setC3State(int c3State) {
        this.c3State=c3State;
    }
    
    /** Getter for property b0InOut.
     * @return Value of property b0InOut.
     */
    public int getB0InOut() {
        return b0InOut;
    }
    
    /** Getter for property b1InOut.
     * @return Value of property b1InOut.
     */
    public int getB1InOut() {
        return b1InOut;
    }
    
    /** Getter for property b2InOut.
     * @return Value of property b2InOut.
     */
    public int getB2InOut() {
        return b2InOut;
    }
    
    /** Getter for property b3InOut.
     * @return Value of property b3InOut.
     */
    public int getB3InOut() {
        return b3InOut;
    }
    
    /** Getter for property b4InOut.
     * @return Value of property b4InOut.
     */
    public int getB4InOut() {
        return b4InOut;
    }
    
    /** Getter for property b5InOut.
     * @return Value of property b5InOut.
     */
    public int getB5InOut() {
        return b5InOut;
    }
    
    /** Getter for property b7InOut.
     * @return Value of property b7InOut.
     */
    public int getB7InOut() {
        return b7InOut;
    }
    
    /** Getter for property c0InOut.
     * @return Value of property c0InOut.
     */
    public int getC0InOut() {
        return c0InOut;
    }
    
    /** Getter for property c3InOut.
     * @return Value of property c3InOut.
     */
    public int getC3InOut() {
        return c3InOut;
    }
    
    /** Getter for property b6InOut.
     * @return Value of property b6InOut.
     */
    public int getB6InOut() {
        return b6InOut;
    }    
    
    /** Setter for property b0InOut.
     * @param b0InOut New value of property b0InOut.
     */
    public void setB0InOut(int b0InOut) {
        this.b0InOut=b0InOut;
    }
    
    /** Setter for property b1InOut.
     * @param b1InOut New value of property b1InOut.
     */
    public void setB1InOut(int b1InOut) {
        this.b1InOut=b1InOut;
    }
    
    /** Setter for property b2InOut.
     * @param b2InOut New value of property b2InOut.
     */
    public void setB2InOut(int b2InOut) {
        this.b2InOut=b2InOut;
    }
    
    /** Setter for property b3InOut.
     * @param b3InOut New value of property b3InOut.
     */
    public void setB3InOut(int b3InOut) {
        this.b3InOut=b3InOut;
    }
    
    /** Setter for property b4InOut.
     * @param b4InOut New value of property b4InOut.
     */
    public void setB4InOut(int b4InOut) {
        this.b4InOut=b4InOut;
    }
    
    /** Setter for property b5InOut.
     * @param b5InOut New value of property b5InOut.
     */
    public void setB5InOut(int b5InOut) {
        this.b5InOut=b5InOut;
    }
    
    /** Setter for property b7InOut.
     * @param b7InOut New value of property b7InOut.
     */
    public void setB7InOut(int b7InOut) {
        this.b7InOut=b7InOut;
    }
    
    /** Setter for property c0InOut.
     * @param c0InOut New value of property c0InOut.
     */
    public void setC0InOut(int c0InOut) {
        this.c0InOut=c0InOut;
    }
    
    /** Setter for property c3InOut.
     * @param c3InOut New value of property c3InOut.
     */
    public void setC3InOut(int c3InOut) {
        this.c3InOut=c3InOut;
    }
    
    /** Setter for property b6InOut.
     * @param b6InOut New value of property b6InOut.
     */
    public void setB6InOut(int b6InOut) {
        this.b6InOut=b6InOut;
    }
    
    /** Getter for property i0Sensitivity.
     * @return Value of property i0Sensitivity.
     */
    public int getI0Sensitivity() {
        return i0Sensitivity;
    }
    
    /** Setter for property i0Sensitivity.
     * @param i0Sensitivity New value of property i0Sensitivity.
     */
    public void setI0Sensitivity(int i0Sensitivity) {
        this.i0Sensitivity=i0Sensitivity;
    }
    
    /** Getter for property i1Sensitivity.
     * @return Value of property i1Sensitivity.
     */
    public int getI1Sensitivity() {
        return i1Sensitivity;
    }
    
    /** Setter for property i1Sensitivity.
     * @param i1Sensitivity New value of property i1Sensitivity.
     */
    public void setI1Sensitivity(int i1Sensitivity) {
        this.i1Sensitivity=i1Sensitivity;
    }
    
    /** Getter for property i2Sensitivity.
     * @return Value of property i2Sensitivity.
     */
    public int getI2Sensitivity() {
        return i2Sensitivity;
    }
    
    /** Setter for property i2Sensitivity.
     * @param i2Sensitivity New value of property i2Sensitivity.
     */
    public void setI2Sensitivity(int i2Sensitivity) {
        this.i2Sensitivity=i2Sensitivity;
    }
    
    /** Getter for property i3Sensitivity.
     * @return Value of property i3Sensitivity.
     */
    public int getI3Sensitivity() {
        return i3Sensitivity;
    }
    
    /** Setter for property i3Sensitivity.
     * @param i3Sensitivity New value of property i3Sensitivity.
     */
    public void setI3Sensitivity(int i3Sensitivity) {
        this.i3Sensitivity=i3Sensitivity;
    }
    
    /** Getter for property i4Sensitivity.
     * @return Value of property i4Sensitivity.
     */
    public int getI4Sensitivity() {
        return i4Sensitivity;
    }
    
    /** Setter for property i4Sensitivity.
     * @param i4Sensitivity New value of property i4Sensitivity.
     */
    public void setI4Sensitivity(int i4Sensitivity) {
        this.i4Sensitivity=i4Sensitivity;
    }
    
    /** Getter for property i5Sensitivity.
     * @return Value of property i5Sensitivity.
     */
    public int getI5Sensitivity() {
        return i5Sensitivity;
    }
    
    /** Setter for property i5Sensitivity.
     * @param i5Sensitivity New value of property i5Sensitivity.
     */
    public void setI5Sensitivity(int i5Sensitivity) {
        this.i5Sensitivity=i5Sensitivity;
    }
    
    /** Getter for property i6Sensitivity.
     * @return Value of property i6Sensitivity.
     */
    public int getI6Sensitivity() {
        return i6Sensitivity;
    }
    
    /** Setter for property i6Sensitivity.
     * @param i6Sensitivity New value of property i6Sensitivity.
     */
    public void setI6Sensitivity(int i6Sensitivity) {
        this.i6Sensitivity=i6Sensitivity;
    }
    
    /** Getter for property i7Sensitivity.
     * @return Value of property i7Sensitivity.
     */
    public int getI7Sensitivity() {
        return i7Sensitivity;
    }    

    
    /** Setter for property i7Sensitivity.
     * @param i7Sensitivity New value of property i7Sensitivity.
     */
    public void setI7Sensitivity(int i7Sensitivity) {
        this.i7Sensitivity=i7Sensitivity;
    }

    /** Getter for property i0OnOff.
     * @return Value of property i0OnOff.
     */
    public int getI0OnOff() {
        return i0OnOff;    
    }
    
    /** Setter for property i0OnOff.
     * @param i0OnOff New value of property i0OnOff.
     */
    public void setI0OnOff(int i0OnOff) {
        this.i0OnOff=i0OnOff;
    }
           
    /** Getter for property i1OnOff.
     * @return Value of property i1OnOff.
     */
    public int getI1OnOff() {
        return i1OnOff;
    }
    
    /** Setter for property i1OnOff.
     * @param i1OnOff New value of property i1OnOff.
     */
    public void setI1OnOff(int i1OnOff) {
        this.i1OnOff=i1OnOff;
    }
    
    /** Getter for property i2OnOff.
     * @return Value of property i2OnOff.
     */
    public int getI2OnOff() {
        return i2OnOff;
    }
    
    /** Setter for property i2OnOff.
     * @param i2OnOff New value of property i2OnOff.
     */
    public void setI2OnOff(int i2OnOff) {
        this.i2OnOff=i2OnOff;
    }
    
    /** Getter for property i3OnOff.
     * @return Value of property i3OnOff.
     */
    public int getI3OnOff() {
        return i3OnOff;
    }
    
    /** Setter for property i3OnOff.
     * @param i3OnOff New value of property i3OnOff.
     */
    public void setI3OnOff(int i3OnOff) {
        this.i3OnOff=i3OnOff;
    }
    
    /** Getter for property i4OnOff.
     * @return Value of property i4OnOff.
     */
    public int getI4OnOff() {
        return i4OnOff;
    }
    
    /** Setter for property i4OnOff.
     * @param i4OnOff New value of property i4OnOff.
     */
    public void setI4OnOff(int i4OnOff) {
        this.i4OnOff=i4OnOff;
    }
    
    /** Getter for property i5OnOff.
     * @return Value of property i5OnOff.
     */
    public int getI5OnOff() {
        return i5OnOff;
    }
    
    /** Setter for property i5OnOff.
     * @param i5OnOff New value of property i5OnOff.
     */
    public void setI5OnOff(int i5OnOff) {
        this.i5OnOff=i5OnOff;
    }
    
    /** Getter for property i6OnOff.
     * @return Value of property i6OnOff.
     */
    public int getI6OnOff() {
        return i6OnOff;
    }
    
    /** Setter for property i6OnOff.
     * @param i6OnOff New value of property i6OnOff.
     */
    public void setI6OnOff(int i6OnOff) {
        this.i6OnOff=i6OnOff;
    }
    
    /** Getter for property i7OnOff.
     * @return Value of property i7OnOff.
     */
    public int getI7OnOff() {
        return i7OnOff;
    }
    
    /** Setter for property i7OnOff.
     * @param i7OnOff New value of property i7OnOff.
     */
    public void setI7OnOff(int i7OnOff) {
        this.i7OnOff=i7OnOff;
    }
    
    public void setWalking(boolean walking)
    {
        this.walking=walking;
    }    
    
    public void setWaitSerial(boolean waitSerial)
    {
        this.waitSerial=waitSerial;
    }
       
    /** Getter for property flowString.
     * @return Value of property flowString.
     */
    public String getFlowString() {
        return this.flowString;
    }
    
    /** Setter for property flowString.
     * @param flowString New value of property flowString.
     */
    public void setFlowString(String flowString) {
        this.flowString = flowString;
    }
    
    public void setNewData(boolean newData)
    {
        this.newData=newData;
    }    
    
    public void resetProduction() 
    {
        parkRobot();
    }
    
    public void setAtStartPosition()
    {
        serialTimer=new java.util.Timer();
        serialTimer.schedule(new SerialTask(),0,125);        
        int counter=0;
        int counterTime=0;
        sendPWM(127,127,false);
        file=new java.io.File("startRobot.grf");                
        startStateMachine(file);   
        generalThread=null;
        runningGeneralThread=true;
        generalThread=new generalThread();
        generalThread.setPriority(generalThread.MAX_PRIORITY);
        generalThread.start();  
        init=true;     
        while(counter<3)
        {
            if(currentPWM1==126&&currentPWM2==126)
            {
                counter++;
            }
            else
            {
                counter=0;
            }
            counterTime++;
            if(counterTime>40)
            {
                Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"Exit because of timeout");
                break;
            }
            try
            {
                Thread.currentThread().sleep(1500);
            }
            catch(InterruptedException ie)
            {
            }            
        }   
        init=false;
        sendPWM(127,127,false);
        killGeneralThread();
        try
        {
            Thread.currentThread().sleep(1000);
        }
        catch(InterruptedException ie)
        {
        }    
        atStartPosition=true;        
    }
    
    private boolean atStartPosition=false;
    
    public boolean isAtStartPosition()
    {
        return atStartPosition;
    }
    
    public void startProduction() 
    {        
        atStartPosition=false;
        if(flowString!=null)
        {
            startStateMachine(flowString);     
        }
        nSamples=0;        
                
        totalSamples=getAcquisitionHeader().getTotalSamples();
        setTotalSamples(totalSamples);
        acqTime=(int)(1/getAcquisitionHeader().getSelectedFrequency().getFrequency()*1000);
        Logger.getLogger(WR_DS_LOGGER).log(Level.INFO,"acqTime="+acqTime);
        counter=0;
        runningGeneralThread=true;
        generalThread=new generalThread();
        generalThread.setPriority(generalThread.MAX_PRIORITY);
        generalThread.start();        
        if(hardware!=null)
        {
            runningDataThread=true;
            dataProducerThread=new dataProducerThread();
            dataProducerThread.start();        
        }
        reseting=false;
        stoping=false;
        stoped=false;
        parked=false;                        
    }
    
    public void stopProduction() 
    {
        stoping=true;
    }
    
    public void endProduction() 
    {
        setDataSourceEnded();
    }
    
    public void stopNow()
    {
        stopProduction(); 
        setDataSourceStoped();
    }
            
    public void shutdown()
    {
        serialComm.closePort();        
    }
    
    private java.util.Timer serialTimer;
    class SerialTask extends java.util.TimerTask
    {
        boolean sentPWM=false;
        public void run()
        {            
            if(!sentPWM)
            {
                sendSerialPWM(currentPWM1,currentPWM2);
                sentPWM=true;
            }
            else
            {
                askSerial();
                sentPWM=false;
            }
        }
    }
}
