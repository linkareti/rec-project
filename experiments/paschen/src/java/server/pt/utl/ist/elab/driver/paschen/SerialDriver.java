package pt.utl.ist.elab.driver.paschen;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialDriver {
    
	public static String comand_out1 = String.format("PR1%c%c", 13, 10);//13 = CR, 10=LineFeed
	public static String comand_in1  = String.format("%c%c%c", 6, 13, 10);//6 = AKW
	
	public static String comand_out2 = String.format("%c", 5);//5 = Enquiry
	public static String comand_in2  = String.format("lalalalalalalalala %c %c", 13, 10);
	
    public SerialPort serialPort;
   
    public InputStream in ;
    public OutputStream out;
	
		
	public SerialDriver()
    {
        super();
    }
    
	void sendToOutStream (OutputStream out, char[] message){
        try
        {
            for(int i=0; i<message.length; i++){
            	out.write(message[i]);
            } 
        }
        catch ( IOException e ){e.printStackTrace();} 
	}
	
	String getFromInStream (InputStream in){
		
        byte[] buffer = new byte[1024];
        //int len = -1;
        
        StringBuffer inbuffer = new StringBuffer();
		
        try
        {
        	in.read(buffer);
            
            int i=0;
            while(buffer[i]!=(byte) 0){
            	inbuffer.append((char) buffer[i]);
            	i++;
            }
            //System.out.println("Got message: "+inbuffer);
            
        }
        catch ( IOException e ){e.printStackTrace();} 
		return inbuffer.toString();
	}
	
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            //portIdentifier.g
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(38400,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
               
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }

    
    public int configureGauge(){
    	
        sendToOutStream (out, comand_out1.toCharArray());
        
		try {Thread.sleep(100);} 
		catch (InterruptedException e1) {}
           			
		StringBuffer inbuffer = new StringBuffer(getFromInStream (in));
        inbuffer.trimToSize();
        
        if(comand_in1.contentEquals(inbuffer.toString())){return 0;}
        else{return -1;}
    	
    }
    
    public double getValuefromGauge(){
		sendToOutStream (out, comand_out2.toCharArray());
        
		try {Thread.sleep(300);} 
		catch (InterruptedException e1) {}

		StringBuffer inbuffer = new StringBuffer(getFromInStream (in));
        inbuffer.trimToSize();
        
        if(Double.valueOf(inbuffer.toString().split(",")[0])==0){
    	return Double.valueOf(inbuffer.toString().split(",")[1]);
        }
        else{return -1;}
    }
    
    
    public void closeCommPort(){
    	try{
	    	in.close();
	    	out.close();
	    	serialPort.close();
	    }
    	catch ( IOException e ){e.printStackTrace();} 
    }
    
    public static void main ( String[] args )
    {
        
    	SerialDriver sd;
    	try
        {
    		sd = new SerialDriver();
    		sd.connect("COM5");
    		sd.configureGauge();
    		while(true){
    			System.out.println("Pressure is: "+sd.getValuefromGauge()+" mBar");
    			Thread.sleep(1000);
    		}
    		
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
