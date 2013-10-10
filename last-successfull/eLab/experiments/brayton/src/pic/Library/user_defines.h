//
// discos_exp.c
//

//
// uart.c
//
//#define FCY ((long) 8000*4) //instruction frequency in kHz
#define FCY ((long) 29491) //instruction frequency in kHz
#define BAUD_RATE 115200

//
// rec_generic_driver.c
//
#define ID_DO_HARDWARE "ELAB_BRAYTON_DSPIC_V1.0"
#define N_PARAMETERS_MAX 8
#define TIME_OUT 180 //* ~0.5s, this value should be relatively well majored (2x higher)

//
// adc.c
//
#define ADC_12_BITS 4096
#define ADC_REF 5.21
#define ADC_CHANNEL 0

//
//relay.c
//
#define RELAYCC _LATB7
#define RELAYCARGA _LATB8
#define RELAYCC_TRIS _TRISB7
#define RELAYCARGA_TRIS _TRISB8
#define OFF 0
#define ON 1
