/*
 * PaschenDataProducer.java
 *
 * Created on 19 de Fevereiro de 2013
 */

package pt.utl.ist.elab.driver.paschen;


import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.sun.jna.Memory;

/**
 * 
 * @author jloureiro
 */
public class PaschenDataProducer extends VirtualBaseDataSource implements Runnable {

	private final double volt_ini;
	private final double volt_fin;
	private final double volt_step;
	private final double press_set;
		
	private final double[] state; // voltage, current, pressure, index
	
	
	static TUeTdIo TUeIO = TUeTdIo.INSTANCE;
	static TUeTdApi TUeApi = TUeTdApi.INSTANCE;
	
	static short zero = (short) 0;
    
	
	//Serial Port Stuff
	public static SerialDriver serialgauge;
	

	private final int NUM_CHANNELS = 4;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	public PaschenDataProducer(final VirtualBaseDriver driver, final double _volt_ini, 
	final double _volt_fin, final double _volt_step, final double _press_set) {
		this.driver = driver;

		volt_ini = _volt_ini;
		volt_fin = _volt_fin;
		volt_step = _volt_step;
		press_set = _press_set;
		//press_set = 1.2;

		state = new double[] { volt_ini, 0 , press_set, 0};
	}

	public void step() {
		state[0] += volt_step;
		state[1] = state[0] < press_set*10 ? 0 : 2000; 
//		state[1] += volt_step*volt_step/10000.;
		state[2] = 1000*(press_set/100 + (Math.random()-0.5)/100);
		state[3] += 1;

	}


	//private Thread animaThread;

	@Override
	public void run() {
		while (!stopped) {
			step();
			// an.move(state[0], state[2], state[1], state[3], state[5]);
			System.out.println(state[4]);
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
			}
		}
	}

	private class ProducerThread extends Thread {

		@Override
		public void run() {

	
			
	    TUeIO.tdOpen();
		Memory config = new Memory(2);  // allocating space for config
		config.setShort(0,(short) -1);  // setting the -1 value, if mgConfig fails this stays -1;
		TUeApi.pgConfig(config);
		
		TUeApi.pg_dioInit(zero, (short) 0x05);
		TUeApi.pg_dioSetOutputTriggerMode(zero, false);
		TUeApi.pg_dioSetOutputBits(zero , (short) 0xFFFF);
		TUeApi.pg_dioOutputData(zero, (short) 0x100);
		
		TUeApi.pg_adcSetModeLv(zero, false, false, false, false, zero, zero, false, false);
		TUeApi.pg_dacSetModeLv(zero, (short) 1, false, false, false, zero, zero, false, false, 0);	
		TUeApi.pg_dacSetModeLv(zero, (short) 0, false, false, false, zero, zero, false, false, 0);
		
        try{
        	serialgauge = new SerialDriver();
        	serialgauge.connect("/dev/ttyUSB0");
        	serialgauge.configureGauge();
		} 
        catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
			
        Memory adcValue = new Memory(2);  // allocating space
        adcValue.clear();
        
        short dacValue;

        double adcValChannel1 = 0;
        double adcValChannel2 = 0;
        
        short initdac = (short) ((((double) volt_ini)/3500.)*32768);
        short finaldac= (short) ((((double) volt_fin)/3500.)*32768);
        short stepdac = (short) ((((double) volt_step)/3500.)*32768);
             
        int timestamp = 0;
        
        PhysicsValue[] value;
        
        int i1=0;
        
        double pressure_inside=0;
        pressure_inside = serialgauge.getValuefromGauge();
        try {
        	if(pressure_inside> press_set/100*5){
			    while(pressure_inside> press_set/100*5){
	       			pressure_inside = serialgauge.getValuefromGauge();
	       			Thread.sleep(1000);
			    }
        	}
        	
	       	if(pressure_inside<= press_set/100*5){
	       		while(pressure_inside<= press_set/100*5){
	       			TUeApi.pg_dacPutValue(zero, zero, (short) 30000);
	       			Thread.sleep(10);
	       			pressure_inside = serialgauge.getValuefromGauge();
	       			i1++;
	       			if(i1==500){
	       				i1=0;
	       				timestamp++;
	       				value = new PhysicsValue[NUM_CHANNELS];
						value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(0), getAcquisitionHeader()
								.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
								.getChannelsConfig(0).getSelectedScale().getMultiplier());
						value[1] = new PhysicsValue(PhysicsValFactory.fromFloat(0), getAcquisitionHeader()
								.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
								.getChannelsConfig(1).getSelectedScale().getMultiplier());
						value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) pressure_inside), getAcquisitionHeader()
								.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
								.getChannelsConfig(2).getSelectedScale().getMultiplier());
						value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) timestamp), getAcquisitionHeader()
								.getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
								.getChannelsConfig(3).getSelectedScale().getMultiplier());
						addDataRow(value);
	       			}
				//TUeApi.pg_dacPutValue(zero, zero, zero);
	       		}
	       		TUeApi.pg_dacPutValue(zero, zero, (short) 0);
	       		Thread.sleep(100);
	       	}
        }
       	catch (final InterruptedException ex){}
        
		try {
			Thread.sleep(100);

			

	        for(dacValue = initdac; !stopped && dacValue<=finaldac; dacValue+=stepdac){
	            
	            TUeApi.pg_dacPutValue(zero, (short) 1, dacValue);//Value to DAC Channel 2 controls Fug Voltage
                                     
	            Thread.sleep((long) (volt_step*10));
	                
                TUeApi.pg_adcSoftwareTrigger(zero);//Trig ADC to get data

                TUeApi.pg_adcGetValue(zero, zero, adcValue);//Retrieve data from channel1
	                //adcValChannel1 = (20./32768.)*adcValue.getShort(0);
                adcValChannel1 = (3500./32768.)*adcValue.getShort(0);
	                //adcValChannel1 = (3500./32768.)*dacValue < 350 ? (3500./32768.)*dacValue : 302 ;
	                

                TUeApi.pg_adcGetValue(zero, (short) 1, adcValue);//Retrieve data from channel2
	                //adcValChannel2 = (20./32768.)*adcValue.getShort(0);
                adcValChannel2 = (4/32768.)*adcValue.getShort(0);
	                //adcValChannel2 = (3500./32768.)*dacValue < 350 ? 0 : 3.98 ;

	                
					value = new PhysicsValue[NUM_CHANNELS];

					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) adcValChannel1), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) adcValChannel2), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) serialgauge.getValuefromGauge()), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) timestamp), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getMultiplier());
					
					Thread.sleep(100);
					
					addDataRow(value);
	                
	                timestamp++;
	        
	        }

	        TUeApi.pg_dacPutValue(zero, (short) 1, zero);//Value to DAC Channel 2 controls Fug Voltage
	        TUeApi.pg_dacPutValue(zero, zero, zero);//Value to DAC Channel 1 controls Pressure (close the valve)
	        
	        Thread.sleep(100); 
		
	        TUeApi.pg_dacPutValue(zero, zero, zero);//Value to DAC Channel 1 controls Pressure (close the valve)
	        Thread.sleep(100);
		

		TUeApi.pg_dioOutputData(zero, (short) 0);

		
	    while(pressure_inside>= 0.05){
	       			pressure_inside = serialgauge.getValuefromGauge();
	       			Thread.sleep(5000);
       				timestamp++;
       				value = new PhysicsValue[NUM_CHANNELS];
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(0), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat(0), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) pressure_inside), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) timestamp), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getMultiplier());
					addDataRow(value);
	       			
	    }

		Thread.sleep(100);

	    	TUeIO.tdClose();
	    	serialgauge.closeCommPort();
			
			driver.stopVirtualHardware();
			join(100);
			endProduction();
			}
		catch (final InterruptedException ie) {}
		}
	}

	@Override
	public void startProduction() {
		stopped = false;
		new ProducerThread().start();
	}

	public void endProduction() {
		
		stopped = true;
		setDataSourceStoped();
	}

	@Override
	public void stopNow() {
		
        TUeApi.pg_dacPutValue(zero, (short) 1, zero);//Value to DAC Channel 2 controls Fug Voltage
        TUeApi.pg_dacPutValue(zero, zero, zero);//Value to DAC Channel 1 controls Pressure
        
        try {
	        Thread.sleep(100);
			TUeApi.pg_dioOutputData(zero, (short) 0);
			
			double pressure_inside = serialgauge.getValuefromGauge();
		    while(pressure_inside>= 0.05){
       			pressure_inside = serialgauge.getValuefromGauge();
       			Thread.sleep(1000);
		    }
	
        }
        catch (final InterruptedException ex) {
        } 
		
    	TUeIO.tdClose();
    	serialgauge.closeCommPort();
    	
		stopped = true;
		setDataSourceStoped();
	}

}
