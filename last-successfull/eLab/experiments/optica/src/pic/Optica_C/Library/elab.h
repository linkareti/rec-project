#include <p30f4013.h>
#include <stdio.h>			//biblioteca standart IO do C
#include <libpic30.h>		//definicoes extra para as bibliotecas do C30
#include <uart.h>			//biblioteca com as funcoes para usar a UART (porta serie)
#include <string.h>
#include "user_defines.h"

#define INPUT  1
#define OUTPUT 0

//
// timer.c
//
void open_timer1(void);
void open_timer1_for_communication(void);

//
//delays.c
//
void delay_ms(unsigned int delay);
void delay_100ys(unsigned int delay);
void delay_10ys(unsigned int delay);

//
// uart.c
//
typedef struct{
	char rbuf[80];
	char *receiveddata;
} Communication;

extern Communication Serial;

void configure_uart2(void);
int command_received(void);
void __attribute__((__interrupt__,auto_psv)) _U2RXInterrupt(void);

//
//adc.c
//
void configure_adc();
void configure_adc_channel(int);
int read_adc(int);


//
//step_motor.c
//
#define MOTOR_BITS_LOW (~(0xF<<MOTOR_FIRST_BIT))
extern const unsigned char step [2][8];
extern unsigned int motor_actual_position;
extern int last_way;
extern int real_position;
//extern int aux_last;
void open_motor();
void walk_motor(unsigned int way, unsigned int steps, unsigned int delay);
void walk_motor_to(unsigned int to, unsigned int delay);
void calibrate_motor();
void motor_compensation(unsigned int delay, int way);
//void motor_clearance(unsigned int inverse_way, unsigned int steps, unsigned int delay);

//
//step_motor2.c
//
#define MOTOR2_BITS_LOW (~(0xF<<MOTOR2_FIRST_BIT))
extern unsigned int motor2_actual_position;
extern int last_way2;
extern int real_position2;
void open_motor2();
void walk_motor2(unsigned int way, unsigned int steps, unsigned int delay);
void walk_motor2_to(unsigned int to, unsigned int delay);
void calibrate_motor2();
void motor2_compensation(unsigned int delay, int way);
//void motor2_clearance(unsigned int inverse_way, unsigned int steps, unsigned int delay);

//
//servo.c
//
void open_servo();
void move_servo(unsigned int delay);


//
//servo2.c
//
//void open_servo2();
//void move_servo2(unsigned int delay);


//
//captures.c
//
//extern unsigned int Capture_counter;
//void open_inputcapture1();

//
//(main_file).c
//
int main();
int angle_1_conversion(double);
double conv_angle_1(int);
int angle_2_conversion(double);
double conv_angle_2(int);
//void __attribute__((__interrupt__,auto_psv)) _ADCInterrupt();
//void __attribute__((__interrupt__,auto_psv)) _T3Interrupt(void);
//void __attribute__((__interrupt__,auto_psv)) _IC1Interrupt(void);

//
// rec_generic_driver.c
//
extern char state[20];
extern char parameters[N_PARAMETERS_MAX][20];
extern int stop;
void rec_generic_driver(void);

extern int protocolo;
extern double param_1;
extern double param_2;
extern double param_3;
extern double param_4;
extern int param_5;
extern double param_6;
extern int param_7;

//
// maq_de_estados.c
//
//extern int protocolo;
//extern double param_1;
//extern double param_2;
//extern double param_3;
//extern double param_4;
//extern double param_5;
//extern double param_6;
//extern int param_7;
void maq_de_estados(void);


//
// protocolos.c
//
extern double param_1_aux;
void protocolo_1_configuring(void);
void protocolo_2_configuring(void);
void protocolo_3_configuring(void);
void protocolo_4_configuring(void);
void protocolo_1_starting(void);
void protocolo_2_starting(void);
void protocolo_3_starting(void);
void protocolo_4_starting(void);
void protocolo_1_started();
void protocolo_2_started();
void protocolo_3_started();
void protocolo_4_started();
void protocolo_5_started();
void protocolo_6_started();
void stopping();
