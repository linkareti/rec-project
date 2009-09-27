/*
 * DriverObject.java
 *
 * Created on 29 de Janeiro de 2003, 11:34
 */

package pt.utl.ist.elab.driver.solitoes;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DriverObject {

	public static final byte HARDWARE_STATE_UNKNOWN = 0;
	public static final byte HARDWARE_STATE_RESETING = 1;
	public static final byte HARDWARE_STATE_RESETED = 2;
	public static final byte HARDWARE_STATE_STOPING = 3;
	public static final byte HARDWARE_STATE_STOPED = 4;
	public static final byte HARDWARE_STATE_CONFIGURING = 5;
	public static final byte HARDWARE_STATE_CONFIGURED = 6;
	public static final byte HARDWARE_STATE_STARTING = 7;
	public static final byte HARDWARE_STATE_STARTED = 8;

	public static final byte POS_CHANNEL_NUM = 0;
	public static final byte H1_CHANNEL_NUM = 1;
	public static final byte H2_CHANNEL_NUM = 2;
	public static final byte H3_CHANNEL_NUM = 3;
	public static final byte H4_CHANNEL_NUM = 4;

	static {
		System.loadLibrary("J2CSolitoesProxy");
	}

	/** Creates a new instance of DriverObject */
	public DriverObject() {
	}

	// Give a chance to initiate the Driver dll
	public native void J2C_init();

	// Configure the Hardware
	public native void J2C_configure(long ms_delay_gate1, long ms_delay_gate2, long ms_delay_gate3);

	// Start Acquisition
	public native void J2C_start();

	// Stop Acquisition
	public native void J2C_stop();

	// Reset Acquisition
	public native void J2C_reset();

	// Give a chance to shutdown the Driver dll
	public native void J2C_shutdown();

	// Get samples
	public native void J2C_getSamples(byte channel_num, int from_index, int to_index, short[] data_out_channel);

	/**************** Java CallBack Methods ********************/
	/*
	 * Call this methods from C when necessary
	 */
	public void C2J_stateChange(byte newHardwareState) {

	}

	public void C2J_newSamples(int[][] indexes_per_channel) {

	}
}
