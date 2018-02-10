#ifndef USER_DEFINES_H
#define USER_DEFINES_H

//
//Common
//
#define DUTTY_MAX 500
#define DUTTY_MIN 0


//
// uart.c
//
//#define FCY ((long) 1843)	//instruction frequency in kHz
#define FCY ((long) 29491) //instruction frequency in kHz
#define BAUD_RATE 115200

//
// rec_generic_driver.c
//
#define ID_DO_HARDWARE "ELAB_LANGMUIR_PROBE_DSPIC_V0.1"
#define TIME_OUT 180 // * ~0.5s, this value should be relatively well majored (2x higher)

#endif
