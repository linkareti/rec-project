//
// optica_exp.c
//
#define LASER_TRIS _TRISD8
#define LASER _LATD8
#define ON 1
#define OFF 0

//
// uart.c
//
#define FCY ((long) 29491) //instruction frequency in kHz
#define BAUD_RATE 115200

//
// rec_generic_driver.c
//
#define ID_DO_HARDWARE "ELAB_OPTICA_DSPIC_V1.0"
#define N_PARAMETERS_MAX 8
#define TIME_OUT 180 //* ~0.5s, this value should be relatively well majored (2x higher)

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
#define POSITION_MAX 1852
#define POSITION_MIN 0
#define MOTOR_SPEED 100
#define FOLGA 10
#define VAL_THRES_MIN 1400
#define CALIB1_AUX 4	//4

//
//step_motor2.c
//
#define CALIBRATE_PORT _RC14  //C32
#define CALIBRATE_TRIS _TRISC14
#define MOTOR2_LAT LATD
#define MOTOR2_TRIS TRISD
#define MOTOR2_FIRST_BIT 0
#define UP_WAY2 1
#define POSITION2_MAX 1662	//1662
#define REAL_MAX_POS 1700	//1700	(1708)
#define POSITION2_MIN 38	//38
#define CALIB_AUX 200	//se está antes do sensor calibracao
#define CALIB_AUX2 187	//define a posicao onde detectou
#define MOTOR2_SPEED 100
#define FOLGA2 13

//
//servo.c
//
#define SERVO _LATD9                                                                                 
#define SERVO_TRIS _TRISD9
#define FREE 160
#define POL_VER 44.5	//For PLL 16x
#define POL_HOR 132		//For PLL 16x
