/*
 * DriverObject.java
 *
 * Created on 29 de Janeiro de 2003, 11:34
 */

package ie.elab.driver.solitoes;

/**
 *
 * @author  jp
 */
public class DriverObject
{
	
	private static final byte HARDWARE_STATE_UNKNOWN=0;
	private static final byte HARDWARE_STATE_RESETING=1;
	private static final byte HARDWARE_STATE_RESETED=2;
	private static final byte HARDWARE_STATE_STOPING=3;
	private static final byte HARDWARE_STATE_STOPED=4;
	private static final byte HARDWARE_STATE_CONFIGURING=5;
	private static final byte HARDWARE_STATE_CONFIGURED=6;
	private static final byte HARDWARE_STATE_STARTING=7;
	private static final byte HARDWARE_STATE_STARTED=8;
	
	
	private static final byte POS_CHANNEL_NUM =0;
	private static final byte H1_CHANNEL_NUM  =1;
	private static final byte H2_CHANNEL_NUM  =2;
	private static final byte H3_CHANNEL_NUM  =3;
	private static final byte H4_CHANNEL_NUM  =4;
	
	
	static
	{
		System.loadLibrary("J2CSolitoesProxy");
	}
	
	/** Creates a new instance of DriverObject */
	public DriverObject()
	{
	}
	
	//Give a chance to initiate the Driver dll
	private native void J2C_init();
	
	//Configure the Hardware
	private native void J2C_configure(long ms_delay_gate1,long ms_delay_gate2,long ms_delay_gate3);
	
	//Start Acquisition
	private native void J2C_start();
	
	//Stop Acquisition
	private native void J2C_stop();
	
	//Reset Acquisition
	private native void J2C_reset();
	
	//Give a chance to shutdown the Driver dll
	private native void J2C_shutdown();
	
	//Get samples
	private native void J2C_getSamples(byte channel_num,int from_index,int to_index,short[] data_out_channel);
	

	
	/**************** Java CallBack Methods ********************/
	/*
		Call this methods from C when necessary
	 */
	private void C2J_stateChange(byte newHardwareState)
	{
	
	}

	private void C2J_newSamples(int[][] indexes_per_channel)
	{
	
	}
}



