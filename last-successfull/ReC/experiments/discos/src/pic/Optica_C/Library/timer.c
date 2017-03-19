#include "elab.h"


void open_timer1(void){	// Timer1
//TMR1: 16-bit timer count register
	TMR1=0;		//O registo inicia a 0x0000

//T1CON: Type A Time Base Register
	T1CONbits.TON=1;	// 0-Stops the timer
						// 1-Starts the timer

	T1CONbits.TSIDL=0;	// 0-Continue timer operation in Idle mode
						// 1-Discontinue timer operation when device enters Idle mode

	T1CONbits.TGATE=0;	// 0-Gated time accumulation disabled
						// 1-Gated time accumulation enabled	
	
	T1CONbits.TCKPS=3;	// 0- 1:1 prescale value
						// 1- 1:8 prescale value
						// 2- 1:64 prescale value
						// 3- 1:256 prescale value
	
	T1CONbits.TCS=0;	// 0-Internal clock (FOSC/4)
						// 1-External clock from pin T1CK

//PR1: 16-bit period register associated with the timer
	PR1=28800;	//Ciclos correspondente ao tempo de 1 segundo para XT_PLL4

//Timer Interrupts configuration
	_T1IF=0;	// 0-Interrupt request has not occurred
				// 1-Interrupt request has occurred
	
	_T1IE=1;	// 0-Interrupt request not enabled
				// 1-Interrupt request enabled

	_T1IP=3;	// Timer1 Interrutp Priority bits (0-7)

//OSCCON: Oscillator Control Register – Oscillator System VERSION 1
	_LPOSCEN=0;		// 0-LP oscillator is disabled
					// 1-LP oscillator is enabled

}

void open_timer1_for_communication(void){	// Timer1
//TMR1: 16-bit timer count register
	TMR1=0;		//O registo inicia a 0x0000

//T1CON: Type A Time Base Register
	T1CONbits.TON=1;	// 0-Stops the timer
						// 1-Starts the timer

	T1CONbits.TSIDL=0;	// 0-Continue timer operation in Idle mode
						// 1-Discontinue timer operation when device enters Idle mode

	T1CONbits.TGATE=0;	// 0-Gated time accumulation disabled
						// 1-Gated time accumulation enabled	
	
	T1CONbits.TCKPS=3;	// 0- 1:1 prescale value
						// 1- 1:8 prescale value
						// 2- 1:64 prescale value
						// 3- 1:256 prescale value
	
	T1CONbits.TCS=0;	// 0-Internal clock (FOSC/4)
						// 1-External clock from pin T1CK

//PR1: 16-bit period register associated with the timer
	PR1=0xFFFF;	//Ciclos correspondente ao tempo de aprox. 0.5 segundo para XT_PLL16

//Timer Interrupts configuration
	_T1IF=0;	// 0-Interrupt request has not occurred
				// 1-Interrupt request has occurred
	
	_T1IE=0;	// 0-Interrupt request not enabled
				// 1-Interrupt request enabled

	_T1IP=0;	// Timer1 Interrutp Priority bits (0-7)

//OSCCON: Oscillator Control Register – Oscillator System VERSION 1
	_LPOSCEN=0;		// 0-LP oscillator is disabled
					// 1-LP oscillator is enabled

}


/*
void __attribute__((__interrupt__,auto_psv)) _T1Interrupt(void){

}
*/

