package pt.utl.ist.elab.driver.paschen;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface TUeTdIo extends Library {

	TUeTdIo INSTANCE = (TUeTdIo) Native.loadLibrary("TUeTdIo", TUeTdIo.class);
	
	
/*************************/
/**		  tdIo.h 		**/
/*************************/	
		
	
	int tdOpen();
	int tdClose();
	int tdOpenWithoutDataTesting();
	int tdTestOpen(Pointer Open);

	int tdInit();
	int tdReset(short DeviceIndex);
//	int tdGetSerialNumber(short DeviceIndex, TD_SERIAL_NUMBER *SerialNumber);

	int tdPutWord(int Address, short Data);
	int tdGetWord(int Address, Pointer Data);
	int tdPutLong(int Address, int Data);
	int tdGetLong(int Address, Pointer Data);

	int tdPutWordBlock(int Address, Pointer Data, int BlockLength);
	int tdGetWordBlock(int Address, Pointer Data, int BlockLength);
	int tdPutLongBlock(int Address, Pointer Data,  int BlockLength);
	int tdGetLongBlock(int Address, Pointer Data,  int BlockLength);

	int tdPutWordBlockAutoIncrAddr(int Address, Pointer Data, int BlockLength);
	int tdGetWordBlockAutoIncrAddr(int Address, Pointer Data, int BlockLength);
	int tdPutLongBlockAutoIncrAddr(int Address, Pointer Data, int BlockLength);
	int tdGetLongBlockAutoIncrAddr(int Address, Pointer Data, int BlockLength);

	int tdSetBitsWord(int Address, short BitMask);
	int tdSetBitsLong(int Address, int BitMask);
	int tdClearBitsWord(int Address, short BitMask);
	int tdClearBitsLong(int Address, int BitMask);

	int tdFindNextBaseAddress(short InterfaceID,
													  Pointer BaseAddress,
													  Pointer Found);
	int tdFindFirstBaseAddress(short InterfaceID,
													   Pointer BaseAddress,
													   Pointer Found);

	int tdPutWordProbe(int Address,
												short Data, Pointer BusErrorFlag);
	int tdPutLongProbe(int Address,
												int Data, Pointer BusErrorFlag);
	int tdGetWordProbe(int Address,
												Pointer Data, Pointer BusErrorFlag);
	int tdGetLongProbe(int Address,
												Pointer Data, Pointer BusErrorFlag);

	int tdClearMultiplePutGetList(short DeviceIndex);

	int tdPutMultiplePutList(short DeviceIndex);
	int tdPutMultipleGetList(short DeviceIndex);

	int tdAppendToMultiplePutList(int Address, Pointer Data, boolean B32Flag);
	int tdAppendToMultipleGetList(int Address, Pointer Data, boolean B32Flag);

	int tdMultiplePut();
	int tdMultipleGet();
	int tdMultiplePutGet();

//	int tdSetupMotionControl(short DeviceIndex, TD_MOTION_CONTROL_SETUP *Setup);
//	int tdSetMotionControlTimes(short DeviceIndex, TD_MOTION_CONTROL_TIMES *Times);
//	int tdGetMotionControlTimes(short DeviceIndex, TD_MOTION_CONTROL_TIMES *Times);
	int tdEnableMotionControl(short DeviceIndex, short SkipFactor);
	int tdArmMotionControlTrigger(byte TriggerDelay);
	int tdDisableMotionControl(short DeviceIndex);
	int tdMotionControlMultiplePutGet(short DeviceIndex);
//	int tdGetMotionControlStatus(short DeviceIndex, TD_MOTION_CONTROL_STATUS *MotionControlStatus);
	int tdMotionControlMultiplePutGetStart(short DeviceIndex);
	int tdMotionControlMultiplePutGetReady(short DeviceIndex);

	int tdSetSwapMode(short SwapMode);
	void  tdGetNumberOfDevices(Pointer NumberOfDevices);
	int tdMapAddress(int UserTdAddress, Pointer MappedTdAddress, Pointer DeviceIndex);
	int tdGetMaxNumberOfAddresses(Pointer NumberOfAddresses);

	String tdErrorMessage(int MessageNumber);

//	void  tdGetLastStatus(TD_TRANSFER_STATUS *Status);
//	String tdStatusMessage(TD_TRANSFER_STATUS Status);
	int tdDownloadEpld(short DeviceIndex, short EpldNumber, Pointer EpldData, int DataSize);
	
}
