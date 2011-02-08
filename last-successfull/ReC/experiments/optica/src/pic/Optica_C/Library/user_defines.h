//
// optica_exp.c
//
#define LASER_TRIS _TRISD8
#define LASER _LATD8
#define ON 1
#define OFF 0

//
// uart_stdio.c
//
#define FCY ((long) 7373) //instruction frequency in kHz
#define BAUD_RATE 115200

//
// rec_generic_driver.c
//
#define ID_DO_HARDWARE "OPTICA"
#define N_PARAMETERS_MAX 5

//
// adc.c
//
#define ADC_12_BITS 4096
#define ADC_REF 5.21
#define ADC_CHANNEL 0

//
//step_motor.c
//
//#define END_CURSE _RD9 //C30
//#define END_CURSE_TRIS _TRISD9
#define MOTOR_LAT LATB
#define MOTOR_TRIS TRISB
#define MOTOR_FIRST_BIT 8
#define UP_WAY 0
#define POSITION_MAX 1853
#define POSITION_MIN 0
#define MOTOR_SPEED 100
#define FOLGA 10
#define VAL_THRES_MIN 1400
#define CALIB1_AUX 4

//
//step_motor2.c
//
#define CALIBRATE_PORT _RC14  //C32
#define CALIBRATE_TRIS _TRISC14
#define MOTOR2_LAT LATD
#define MOTOR2_TRIS TRISD
#define MOTOR2_FIRST_BIT 0
#define UP_WAY2 1
#define POSITION2_MAX 1662	//1674
#define REAL_MAX_POS 1700	//1700	(1708)
#define POSITION2_MIN 38	//38
#define CALIB_AUX 200
#define CALIB_AUX2 191
#define MOTOR2_SPEED 100
#define FOLGA2 13

//
//servo.c
//
#define SERVO _LATD9                                                                                 
#define SERVO_TRIS _TRISD9
#define FREE 150
#define POL_VER 40
#define POL_HOR 119
