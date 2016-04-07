package pt.utl.ist.elab.driver.paschen;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface TUeTdApi extends Library {
	TUeTdApi INSTANCE = (TUeTdApi) Native.loadLibrary("tdApi", TUeTdApi.class);

	
/*************************/
/** MicroGiantLibrary.h **/
/*************************/	
	
	
    int mgConfig(Pointer NumberOfmg);
//    int mgGetInfo(short mgIndex, MG_INTERFACE_INFO **Info);
    int mgGenerateCommonSoftwareTrigger(short mgIndex);
    int mgGenerateCommonTriggerInput(short mgIndex);
    int mgGenerateCommonTriggerOutput(short mgIndex);

//    int mgGetTriggerInputTimeStamp(short mgIndex, MG_TIME *TimeStamp);
//    int mgGetTriggerOutputTimeStamp(short mgIndex, MG_TIME *TimeStamp);

//    int mgRegisterForMotionControl(short mgIndex, TD_MOTION_CONTROL_TIMES MotionControlTimes);

    int mgGetTUeDACSDeviceIndex(short mgIndex, Pointer TUeDACSDeviceIndex);

//    int mgTimeCompare(MG_TIME TimeA, MG_TIME TimeB);

    int mg_adcReset(short mgIndex);
//    int mg_adcSetMode(short mgIndex, MG_ADC_SETTINGS Settings);
    int mg_adcSetModeLv(short mgIndex, short MemoryMode,
    								   short TriggerModeCh1, short MaxSampleCountCh1, boolean ExternalTriggerModeCh1, boolean ExternalClockModeCh1, boolean EnhancedClockModeCh1, 
    								   short TriggerModeCh2, short MaxSampleCountCh2, boolean ExternalTriggerModeCh2, boolean ExternalClockModeCh2, boolean EnhancedClockModeCh2);

    int mg_adcSetRange(short mgIndex, short Channel, short Range);
    int mg_adcSetFilterOrder(short mgIndex, short Channel, short FilterOrder);
    int mg_adcSetSampleRate(short mgIndex, short Channel, short SampleRateIndex);
    int mg_adcGetNumberOfSampleRates(Pointer NumberOfSampleRates);
//    int mg_adcGetSampleRate(short SampleRateIndex, MG_SAMPLE_RATES_INFO *SampleRate);

    int mg_adcSoftwareTrigger(short mgIndex, boolean Channel1, boolean Channel2);
    int mg_adcGetValue(short mgIndex, short Channel, Pointer adcValue);
    int mg_adcReadValues(short mgIndex, short Channel, Pointer adcValues);
    int mg_adcEnable(short mgIndex, boolean Channel1, boolean Channel2);
    int mg_adcDisable(short mgIndex, boolean Channel1, boolean Channel2);
//    int mg_adcGetStatus(short mgIndex, MG_ADC_STATUS *Status);
    int mg_adcGetStatusLv(short mgIndex, Pointer Configured,
    									 Pointer EnableCh1, Pointer BusyCh1, Pointer FunctionReadyCh1, Pointer WarningCh1, 
    									 Pointer EnableCh2, Pointer BusyCh2, Pointer FunctionReadyCh2, Pointer WarningCh2);
    int mg_adcSetFilterFrequency(short mgIndex, short Channel, int Frequency);
    int mg_adcSetCalibrateMode(short mgIndex, short CalibrationMode);


    int mg_dacReset(short mgIndex);
//    int mg_dacSetMode(short mgIndex, MG_DAC_SETTINGS Settings);
    int mg_dacSetModeLv(short mgIndex, short MemoryMode,
    									 short TriggerModeCh1, short SwitchMemoryModeCh1, short MaxSampleCountCh1, boolean RepatCountEnableCh1, int RepeatCountCh1, boolean ExternalTriggerModeCh1, boolean ExternalClockModeCh1, boolean EnhancedClockModeCh1, 
    									 short TriggerModeCh2, short SwitchMemoryModeCh2, short MaxSampleCountCh2, boolean RepatCountEnableCh2, int RepeatCountCh2, boolean ExternalTriggerModeCh2, boolean ExternalClockModeCh2, boolean EnhancedClockModeCh2);
    int mg_dacPutValue(short mgIndex, short Channel, short dacValue);
//    int mg_dacGetStatus(short mgIndex, MG_DAC_STATUS *Status);
    int mg_dacGetStatusLv(short mgIndex, 
    									   Pointer Configured,
    									   Pointer EnableCh1, Pointer BusyCh1, Pointer DoneCh1, Pointer MemoryEmptyCh1, 
    									   Pointer EnableCh2, Pointer BusyCh2, Pointer DoneCh2, Pointer MemoryEmptyCh2);
    int mg_dacSetSampleRate(short mgIndex,
                                 short Channel, short SampleRateIndex);
    int mg_dacGetNumberOfSampleRates(Pointer NumberOfSampleRates);
//    int mg_dacGetSampleRate(short SampleRateIndex, MG_SAMPLE_RATES_INFO *SampleRate);
    int mg_dacEnable(short mgIndex, boolean Channel1, boolean Channel2);
    int mg_dacDisable(short mgIndex, boolean Channel1, boolean Channel2);
    int mg_dacSwitchMemory(short mgIndex, boolean Channel1, boolean Channel2);
    int mg_dacWriteValues(short mgIndex, short Channel, Pointer dacValues);
//    int mg_dacPutReconstructionCoefficients(short mgIndex, short Channel, MG_DAC_REC_COEFF Coefficients);


    int mg_iopReset(short mgIndex);
    int mg_iopInit(short mgIndex, short iopOutputMask);
    int mg_iopGetState(short mgIndex, Pointer iopOutputMask); 
    int mg_iopSetInputTriggerMode(short mgIndex, short TriggerMode);
    int mg_iopSetOutputTriggerMode(short mgIndex, short TriggerMode);
    int mg_iopOutputData(short mgIndex, short Data);
    int mg_iopSetOutputBits(short mgIndex, short BitMask);
    int mg_iopClearInputBits(short mgIndex, short BitMask);
    int mg_iopGetInputData(short mgIndex, Pointer Data);
//    int mg_iopGetInputStatus(short mgIndex, MG_IOP_STATUS *Status);
    int mg_iopGetInputStatusLv(short mgIndex, Pointer Configured, Pointer Warning);
//    int mg_iopGetOutputStatus(short mgIndex, MG_IOP_STATUS *Status);
    int mg_iopGetOutputStatusLv(short mgIndex, Pointer Configured, Pointer Warning);


    int mg_qcReset(short mgIndex, short Channel);
    int mg_qcClearCounter(short mgIndex, short Channel);
    int mg_qcGetCountValue(short mgIndex, short Channel, Pointer qcCountValue);
//    int mg_qcGetStatus(short mgIndex, MG_QC_STATUS *Status);
//    int mg_qcSetMode(short mgIndex, short Channel, MG_QC_CHANNEL_SETTINGS Settings);
    int mg_qcGetHomePosition(short mgIndex, short Channel, Pointer qcHomePosition);
       

    int mg_tsReset(short mgIndex, short Channel);
//    int mg_tsSetMode(short mgIndex, short Channel, MG_TS_SETTINGS Settings);
    int mg_tsEnable(short mgIndex, short Channel);
    int mg_tsDisable(short mgIndex, short Channel);
    int mg_tsSoftwareTrigger(short mgIndex, short Channel);
//    int mg_tsGetTimeStamps(short mgIndex, short Channel, MG_QC_TIME_STAMP *TimeStamps);
//    int mg_tsGetStatus(short mgIndex, int Channel, MG_TS_STATUS *Status);
    

/************************/
/** PicoGiantLibrary.h **/
/************************/	 
 
    int pgConfig(Pointer NumberOfpg);
//    int pgGetInfo(short pgIndex, PG_INTERFACE_INFO **Info);

    int pgGetNumberOfVariantTypes(Pointer NumberOfVariantTypes);
 //   int pgGetVariantTypes(short VariantIndex, PG_VARIANT *VariantType);
    int pgSetVariantType(short pgIndex, short VariantIndex);
    int pgGetCurrentVariant(short pgIndex, Pointer VariantIndex);
    int pgDownloadFpga(short pgIndex, Pointer FpgaFilename);

//    int pgGenerateCommonTriggerInput(short pgIndex, PG_INPUT_TRIGGER_DESC Trigger);
//    int pgGenerateCommonTriggerOutput(short pgIndex, PG_OUTPUT_TRIGGER_DESC Trigger);
//    int pgGetTriggerInputTimeStamp(short pgIndex, PG_TIME *TimeStamp);
//    int pgGetTriggerOutputTimeStamp(short pgIndex, PG_TIME *TimeStamp);
//    int pgRegisterForMotionControl(short pgIndex, TD_MOTION_CONTROL_TIMES MotionControlTimes,
//    													PG_INPUT_TRIGGER_DESC InputTrigger, PG_OUTPUT_TRIGGER_DESC OutputTrigger);
    int pgGetTUeDACSDeviceIndex(short pgIndex, Pointer TUeDACSDeviceIndex);
//    int pgTimeCompare(PG_TIME TimeA, PG_TIME TimeB);

    /************** Global Function Declarations **************/

    int pg_adcReset(short pgIndex);
//    int pg_adcSetMode(short pgIndex, PG_ADC_SETTINGS Settings);
//    int pg_adcSoftwareTrigger(short pgIndex);
    int pg_adcSoftwareStart(short pgIndex);
    int pg_adcGetValue(short pgIndex, short Channel, Pointer adcValue);
//    int pg_adcGetAllValues(short pgIndex, PG_ADC_VALUES *adcValues);
    int pg_adcGetNumberOfGain(short pgIndex, Pointer NumberOfGain);
//    int pg_adcGetResolution(short pgIndex, short GainIndex, double *Resolution, Pointer NumberOfAdcBits, Pointer BipolarMode);
//    int pg_adcGetCurrentResolution(short pgIndex, short Channel, double *Resolution, Pointer NumberOfAdcBits, Pointer BipolarMode);
    int pg_adcSetGain(short pgIndex, short Channel, short GainIndex);
    int pg_adcGetGain(short pgIndex, short Channel, Pointer GainIndex);
//    int pg_adcGetStatus(short pgIndex, PG_ADC_STATUS *Status);
//    int pg_adcGetTriggerTimestamp(short pgIndex, PG_TIME *TimeStamp);
    int pg_adcGetNumberOfSampleRates(Pointer NumberOfSampleRates);
//    int pg_adcGetSampleRate(short SampleRateIndex,PG_SAMPLE_RATES_INFO *SampleRate);
    int pg_adcSetModeLv(short pgIndex, boolean MemoryMode,
    									 boolean TriggerEnable, boolean TriggerExternal, boolean Differential, 
    									 short SampleCount, short SampleRate,
    									 boolean ExternalClockMode, boolean EnhancedClockMode);
    int pg_adcGetStatusLv(short pgIndex, 
									    Pointer Configured,
									    Pointer Enable, Pointer Busy, Pointer FunctionReady, Pointer MemoryFull,
									    boolean[] Overflow);
    int pg_adcReadValues(short pgIndex, Pointer adcValuesChannel1, Pointer adcValuesChannel2);
    int pg_adcReadValuesEnhanced(short pgIndex, Pointer adcValues);
    int pg_adcSoftwareTrigger(short pgIndex);
    int pg_adcEnable(short pgIndex);
    int pg_adcDisable(short pgIndex);

    int pg_dacReset(short pgIndex, short Channel);
    int pg_dacGetNumberOfGain(short pgIndex, Pointer NumberOfGain);
    int pg_dacGetResolution(short pgIndex, short Channel, Pointer Resolution, Pointer NumberOfDacBits, Pointer BipolarMode);
    int pg_dacGetCurrentResolution(short pgIndex ,short Channel, Pointer Resolution, Pointer NumberOfDacBits, Pointer BipolarMode);
//    int pg_dacSetMode(short pgIndex, short Channel, PG_DAC_SETTINGS Settings);
    int pg_dacPutValue(short pgIndex, short Channel, short dacValue);
//    int pg_dacGetTriggerTimestamp(short pgIndex, short Channel, PG_TIME *TimeStamp);
    int pg_dacSoftwareTrigger(short pgIndex, short Channel);
//    int pg_dacGetStatus(short pgIndex, short Channel, PG_DAC_STATUS *Status);
    int pg_dacGetNumberOfSampleRates(Pointer NumberOfSampleRates);
//    int pg_dacGetSampleRate(short SampleRateIndex, PG_SAMPLE_RATES_INFO *SampleRate);
    int pg_dacSetModeLv(short pgIndex, short Channel, boolean MemoryMode,
    									 boolean TriggerEnable, boolean TriggerExternal, 
    									 short SampleCount, short SampleRate,
    									 boolean EnhancedClockMode, boolean RepeatCountEnable, int RepeatCount);
    int pg_dacEnable(short pgIndex, short Channel);
    int pg_dacDisable(short pgIndex, short Channel);
    int pg_dacWriteValues(short pgIndex, short Channel, Pointer dacValues);


    int pg_dioReset(short pgIndex);
    int pg_dioInit(short pgIndex, short dioOutputNibbleMask);
    int pg_dioSetInputTriggerMode(short pgIndex, boolean TriggerMode);
    int pg_dioSetOutputTriggerMode(short pgIndex, boolean TriggerMode);
    int pg_dioGetState(short pgIndex, Pointer dioOutputNibbleMask);
    int pg_dioOutputData(short pgIndex, short Data);
    int pg_dioSetOutputBits(short pgIndex, short BitMask);
    int pg_dioClearInputBits(short pgIndex, short BitMask);
    int pg_dioGetInputData(short pgIndex, Pointer Data);
    int pg_dioTurnRelayOn(short pgIndex);
    int pg_dioTurnRelayOff(short pgIndex);
    int pg_dioSetBufferOutputHigh(short pgIndex, short Channel);
    int pg_dioSetBufferOutputLow(short pgIndex, short Channel);

//    int pg_clkSetMode(short pgIndex, PG_CLK_SETTINGS Settings);
    int pg_clkEnable(short pgIndex);
    int pg_clkDisable(short pgIndex);
    int pg_clkSetClockRate(short pgIndex, int SampleClockIndex);
    int pg_clkGetNumberOfClockRates(Pointer NumberOfClockRates);
//    int pg_clkGetClockRate(short ClockRateIndex, PG_SAMPLE_RATES_INFO *ClockRate);


    int pg_pscReset(short pgIndex);
//    int pg_pscSetMode(short pgIndex, PG_PSC_SETTINGS Settings);
    int pg_pscEnable(short pgIndex, int PresetCount);
    int pg_pscDisable(short pgIndex);
    int pg_pscSoftwareTrigger(short pgIndex);
//    int pg_pscGetStatus(short pgIndex, PG_PSC_STATUS *Status);
    int pg_pscGetIntermediateCountValue(short pgIndex, Pointer IntermediateCountValue);
    int pg_pscSetModeLv(short pgIndex, short ClockSource, boolean EventGeneratorMode, boolean ExternalStartMode);

    int pg_scaReset(short pgIndex);
//    int pg_scaSetMode(short pgIndex, PG_SCA_SETTINGS Settings);
    int pg_scaEnable(short pgIndex);
    int pg_scaClear(short pgIndex);
    int pg_scaGetCountValue(short pgIndex, Pointer CountValue);
    int pg_scaDisable(short pgIndex);
//    int pg_scaGetStatus(short pgIndex, PG_SCA_STATUS *Status);
    int pg_scaGetIntermediateCountValue(short pgIndex, Pointer IntermediateCountValue);
    int pg_scaSetModeLv(short pgIndex, short ClockSource, short GateSource);

    int pg_smcReset(short pgIndex, short Channel);
//    int pg_smcSetMode(short pgIndex, short Channel, PG_SMC_SETTINGS Settings);
//    int pg_smcStart(short pgIndex, short Channel, PG_SMC_MOVE_PARM Parameters);
    int pg_smcAbort(short pgIndex, short Channel);
//    int pg_smcGetStatus(short pgIndex, short Channel, PG_SMC_STATUS *Status);
    int pg_smcSetModeLv(short pgIndex, short Channel, boolean EnhancedMode, 
    									 short PulseTimeWidth, short PulseTimeDelay, 
    									 boolean EndstopsActiveLow, boolean EndstopsEnable);    
    
    
}