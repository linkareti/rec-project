#include <p30f4011.h>
#include <stdio.h>		//biblioteca standart IO do C
#include <libpic30.h>		//definicoes extra para as bibliotecas do C30
#include <uart.h>		//biblioteca com as funcoes para usar a UART (porta serie)
#include <string.h>
#include "user_defines.h"

#define INPUT  1
#define OUTPUT 0

//
// timer1.c
//
void open_timer1(void);
void open_timer1_for_communication(void);

//
// timer2.c
//
void open_timer2(void);

//
// timer3.c
//
void open_timer3_for_motor(void);

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
extern int tensao;

//
//relay.c
//
void open_relay();
void relay(short relay_state);

//
//captures.c
//
extern short int interrompeu;
extern unsigned int capture_counter;
void open_inputcapture1();
void turn_on_inputcapture1();
void turn_off_inputcapture1();

extern int rpm;

//
//(main_file).c
//
int main();
//void __attribute__((__interrupt__,auto_psv)) _ADCInterrupt();
//void __attribute__((__interrupt__,auto_psv)) _T2Interrupt(void);
void __attribute__((__interrupt__,auto_psv)) _IC1Interrupt(void);

//
// rec_generic_driver.c
//
extern char state[20];
extern char parameters[N_PARAMETERS_MAX][20];
extern int stop;
void rec_generic_driver(void);

extern int protocolo;
extern int param_1;

//
// maq_de_estados.c
//
void maq_de_estados(void);

//
// outputcompare.c
//
void configure_pwm_compressor(int dutycycle);

//
// sendUInt_fast.c
//
void printval(unsigned int val);

//
// protocolos.c
//
void protocolo_0_help(void);
void protocolo_1_configuring(void);
void protocolo_2_configuring(void);
void protocolo_3_configuring(void);
void protocolo_4_configuring(void);
void protocolo_5_configuring(void);
void protocolo_6_configuring(void);
void protocolo_1_starting(void);
void protocolo_2_starting(void);
void protocolo_3_starting(void);
void protocolo_4_starting(void);
void protocolo_5_starting(void);
void protocolo_6_starting(void);
void protocolo_1_started();
void protocolo_2_started();
void protocolo_3_started();
void protocolo_4_started();
void protocolo_5_started();
void protocolo_6_started();
void stopping();
