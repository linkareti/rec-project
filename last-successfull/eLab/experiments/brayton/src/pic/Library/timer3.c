#include "elab.h"

void open_timer3_for_motor(void){// Timer3
//TMR1: 16-bit timer count register

        T3CON=0;        // Clear the Timer 2 configuration
	TMR3=0;		//O registo inicia a 0x0000
        PR3 = 1179;
//T1CON: Type A Time Base Register

	T3CONbits.TSIDL=0;	// 0-Continue timer operation in Idle mode
						// 1-Discontinue timer operation when device enters Idle mode

	T3CONbits.TGATE=0;	// 0-Gated time accumulation disabled
						// 1-Gated time accumulation enabled	
	
	T3CONbits.TCKPS=0;	// 0- 1:1 prescale value
						// 1- 1:8 prescale value
						// 2- 1:64 prescale value
						// 3- 1:256 prescale value
	
	T3CONbits.TCS=0;	// 0-Internal clock (FOSC/4)
						// 1-External clock from pin T1CK

//Timer Interrupts configuration
	_T3IF=0;	// 0-Interrupt request has not occurred
				// 1-Interrupt request has occurred
	
	_T3IE=0;	// 0-Interrupt request not enabled
				// 1-Interrupt request enabled

	_T3IP=0;	// Timer1 Interrutp Priority bits (0-7)

//OSCCON: Oscillator Control Register – Oscillator System VERSION 1
	_LPOSCEN=0;		// 0-LP oscillator is disabled
				// 1-LP oscillator is enabled
}

/*
void __attribute__((__interrupt__,auto_psv)) _T2Interrupt(void)
{
    _T2IF = 0;
}
*/
