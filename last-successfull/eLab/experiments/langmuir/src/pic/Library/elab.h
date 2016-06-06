#ifndef ELAB_H
#define ELAB_H

#define INPUT  1
#define OUTPUT 0

//
//Includes
//

#include <p30F4011.h>
#include <stdio.h>			//biblioteca standart IO do C
#include <libpic30.h>		//definicoes extra para as bibliotecas do C30
#include <uart.h>			//biblioteca com as funcoes para usar a UART (porta serie)
#include <string.h>             //string library
#include <adc10.h>              //ADC library
#include <math.h> 

#include "user_defines.h"

#include "hardware_uart.h"
#include "pfeiffer_gauges.h"


//
// timer.c
//
void open_timer1(void);
void open_timer1_for_communication(void);
void open_timer2(void);
void open_timer3(void);

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
//output_compare.c
//
void open_output_compare1(void);
void open_output_compare3(void);
void open_output_compare4(void);
void open_pwm(void);
void close_pwm(void);

//
//(main_file).c
//
int main();
//void __attribute__((__interrupt__,auto_psv)) _ADCInterrupt();
//void __attribute__((__interrupt__,auto_psv)) _T3Interrupt(void);
//void __attribute__((__interrupt__,auto_psv)) _IC1Interrupt(void);

//
// rec_generic_driver.c
//
extern char state[20];
//extern char parameters[N_PARAMETERS_MAX][20];
extern int stop;
void rec_generic_driver(void);
//extern int protocolo;
extern int param_1;
extern int param_2;
extern int param_3;
extern int param_4;
extern int param_5;
extern int param_6;
extern int param_7;

//
// protocolos.c
//
void configuring(void);
void starting(void);
void started(void);
void stopping(void);

//
// maq_de_estados.c
//
void maq_de_estados(void);



#endif
