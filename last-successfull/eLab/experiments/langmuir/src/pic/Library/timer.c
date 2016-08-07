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

void open_timer2(void){	// Timer2
//TMR2: 16-bit timer count register
	TMR2=0;		//O registo inicia a 0x0000

//T2CON: Type B Time Base Register
	T2CONbits.TON=0;	// 0-Stops the timer
						// 1-Starts the timer

	T2CONbits.TSIDL=0;	// 0-Continue timer operation in Idle mode
						// 1-Discontinue timer operation when device enters Idle mode

	T2CONbits.TGATE=0;	// 0-Gated time accumulation disabled
						// 1-Gated time accumulation enabled	
	
	T2CONbits.TCKPS=0;	// 0- 1:1 prescale value
						// 1- 1:8 prescale value
						// 2- 1:64 prescale value
						// 3- 1:256 prescale value

	T2CONbits.T32=0;	// 1 = TMRx and TMRy form a 32-bit timer
						// 0 = TMRx and TMRy form separate 16-bit timer

	
	T2CONbits.TCS=0;	// 0-Internal clock (FOSC/4)
						// 1-External clock from pin T1CK

//PR2: 16-bit period register associated with the timer
	PR2=1023;	//Timer2 period

//Timer Interrupts configuration
	_T2IF=0;	// 0-Interrupt request has not occurred
				// 1-Interrupt request has occurred
	
	_T2IE=0;	// 0-Interrupt request not enabled
				// 1-Interrupt request enabled

	_T2IP=2;	// Timer1 Interrutp Priority bits (0-7)
}

/*
void __attribute__((__interrupt__,auto_psv)) _T2Interrupt(void){

}
*/


void open_timer3(void){	// Timer3
//TMR3: 16-bit timer count register
	TMR3=0;		//O registo inicia a 0x0000

//T3CON: Type C Time Base Register
	T3CONbits.TON=0;	// 0-Stops the timer
						// 1-Starts the timer

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

//PR3: 16-bit period register associated with the timer
	PR3= 65535;		//Timer3 Period 

//Timer Interrupts configuration
	_T3IF=0;	// 0-Interrupt request has not occurred
				// 1-Interrupt request has occurred
	
	_T3IE=1;	// 0-Interrupt request not enabled
				// 1-Interrupt request enabled

	_T3IP=2;	// Timer1 Interrutp Priority bits (0-7)

//OSCCON: Oscillator Control Register – Oscillator System VERSION 1
	_LPOSCEN=0;		// 0-LP oscillator is disabled
					// 1-LP oscillator is enabled
}

//void __attribute__((__interrupt__,auto_psv)) _T3Interrupt(void){
//	_T3IF=0;
//}

void open_timer4(void){	// Timer4
//TMR4: 16-bit timer count register
	TMR4=0;		//O registo inicia a 0x0000

//T4CON: Type A Time Base Register
	T4CONbits.TON=0;	// 0-Stops the timer
						// 1-Starts the timer

	T4CONbits.TSIDL=0;	// 0-Continue timer operation in Idle mode
						// 1-Discontinue timer operation when device enters Idle mode

	T4CONbits.TGATE=0;	// 0-Gated time accumulation disabled
						// 1-Gated time accumulation enabled	
	
	T4CONbits.TCKPS=0;	// 0- 1:1 prescale value
						// 1- 1:8 prescale value
						// 2- 1:64 prescale value
						// 3- 1:256 prescale value
	
	T4CONbits.TCS=0;	// 0-Internal clock (FOSC/4)
						// 1-External clock from pin T1CK

//PR3: 16-bit period register associated with the timer
	PR4= 65535;		//Timer3 Period 

//Timer Interrupts configuration
	_T4IF=0;	// 0-Interrupt request has not occurred
				// 1-Interrupt request has occurred
	
	_T4IE=0;	// 0-Interrupt request not enabled
				// 1-Interrupt request enabled

	_T4IP=2;	// Timer1 Interrutp Priority bits (0-7)

}

//void __attribute__((__interrupt__,auto_psv)) _T4Interrupt(void){
//	_T4IF=0;
//}
