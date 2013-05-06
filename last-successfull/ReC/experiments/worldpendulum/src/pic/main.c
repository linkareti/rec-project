/* 
 * File:   main.c
 * Author: tpereira
 *
 * Created on 10 de Novembro de 2012, 12:53
 * 
 * Code for the world pendulum
 *
 * Photogate on pin ----------- AN2/RB2
 * End microswitch on pin ----- AN3/RB3
 * LM35 on pin ---------------- AN4/RB4
 */

#include "world_pendulum.h"
#include "physical.h"
#include "shovel.h"
#include "delays.h"
#include "ball.h"
#include "laser.h"
#include "msg.h"
#include "bipolar.h"
#include "adc.h"
#include "state_machine.h"

#include <p30F4011.h>
#include <stdio.h>
#include <string.h>
#include <libpic30.h>
#include <math.h>


//Configuration bits
//_FOSC(CSW_FSCM_OFF & XT_PLL16);			//oscilator at 16x cristal frequency
_FOSC(CSW_FSCM_OFF & XT);			//oscilator at cristal frequency
_FWDT(WDT_ON & WDTPSA_512 & WDTPSB_16);	//watchdog timer (interval of ~ 16s)


/* main code */
int main(void) {
	static int pushButtonFlag;
	static int i;
	static double myDouble;

	pushButtonFlag = 0;
	
	//Leds
	TRISDbits.TRISD2 = 0;
	TRISDbits.TRISD3 = 0;
	
	for (i=0; i<10; i++) {
		TOGGLE_LED1;
		TOGGLE_LED2;
		delay_ms(100);
	}

	//Enable interrupt nesting
	INTCON1bits.NSTDIS = 0;

	//Push button
	TRISEbits.TRISE8 = 1;
	
	//Laser and iluminating blue led YEAH!!!!
	laser_off();
	blue_led_off();
	TRISEbits.TRISE4 = 0;
	TRISEbits.TRISE5 = 0;
	
	//Photodiode input
	ADPCFGbits.PCFG2 = 1;
	TRISBbits.TRISB2 = 1;
	
	//Microswitch input
	ADPCFGbits.PCFG3 = 1;
	TRISBbits.TRISB3 = 1;
	
	//Change notification config (period measurement and microswitch)
	IPC3bits.CNIP = 6;
	CNEN1bits.CN4IE = 1;	//photodiode
	CNEN1bits.CN5IE = 1;	//microswitch
	CNPU1bits.CN5PUE = 1;	//weak pullup for microswitch
	IFS0bits.CNIF = 0;
	IEC0bits.CNIE = 1;
	
	//Stepper motor
	releaseBipolar();
	
	//Initialize adc
	adc_init();
	
	//T1 for stepping
	T1CONbits.TCKPS = 0b11;	//prescaler for timer 2:1 1:8 1:64 1:256
	T1CONbits.TGATE = 0;
	T1CONbits.TSYNC = 0;
	T1CONbits.TCS = 0;
	TMR1 = 0;
	PR1 = 0xFFFF;
	IFS0bits.T1IF = 0;
	IEC0bits.T1IE = 1;
	T1CONbits.TON = 0;
	
	//T2 (32bit) for period measurement
	T2CONbits.TCKPS = 0b00;		//prescaler for timer 2:1 1:8 1:64 1:256
	T2CONbits.TGATE = 0;
	T2CONbits.TCS = 0;
	//TMR3HLD = 0;
	TMR2 = 0;
	TMR3 = 0;
	PR3 = 0xFFFF;
	PR2 = 0xFFFF;
	T2CONbits.T32 = 1;
	IFS0bits.T3IF = 0;
	IEC0bits.T3IE = 0;
	T2CONbits.TON = 1;	
	
	//T4 for messaging (f=2Hz)
	T4CONbits.TCKPS = 0b11;	//prescaler for timer 2:1 1:8 1:64 1:256
	T4CONbits.TGATE = 0;
	T4CONbits.TCS = 0;
	TMR4 = 0;
	PR4 = FCY / 256 / 2 - 1;
	IFS1bits.T4IF = 0;
	IEC1bits.T4IE = 1;
	T4CONbits.TON = 1;
	
	//T5 for checking if ball is stopped (f=5Hz)
	T5CONbits.TCKPS = 0b11;	//prescaler for timer 2:1 1:8 1:64 1:256
	T5CONbits.TGATE = 0;
	T5CONbits.TCS = 0;
	TMR5 = 0;
	PR5 = FCY / 256 / 5 - 1;
	IFS1bits.T5IF = 0;
	IEC1bits.T5IE = 1;
	T5CONbits.TON = 1;
	
	//Configuration of uart 2
	U2BRG = 0;
	U2MODEbits.PDSEL = 0;
	U2MODEbits.STSEL = 0;
	U2STAbits.URXISEL = 0;
	U2MODEbits.UARTEN = 1;
	U2STAbits.UTXEN = 1;
	IFS1bits.U2RXIF = 0;
	IEC1bits.U2RXIE = 1;
	
	__C30_UART = 2; 	//define UART2 as predefined for use with stdio library, printf etc

	Yaiks();
	
	myDouble = getSphereDiameter_CM();
	if(myDouble < 1.0) saveSphereDiameter_CM(1.0);

	myDouble = getPendulumLength_M();
	if(myDouble < 1.0) savePendulumLength_M(1.0);

	set_state(STATE_RESET, NO_ECHO);

	reset_idmsg_timer();

	while(1) {
		asm("clrwdt");

		if(PUSH_BUTTON == 0 && pushButtonFlag == 0) {
			pushButtonFlag = 1;
			delay_ms(100);
			if(get_state() == STATE_RESET || get_state() == STATE_STOPPED) {
				laser_toggle();
				if(laser_is_on() == YES) blue_led_on();
				else blue_led_off();
			}
		}
		
		if(PUSH_BUTTON == 1) pushButtonFlag = 0;

		processMessage(NORMAL_MODE);
		state_machine();
	}
}


//Period measurement
void __attribute__((__interrupt__, __no_auto_psv__)) _T3Interrupt(void) {
	IFS0bits.T3IF = 0;
}
