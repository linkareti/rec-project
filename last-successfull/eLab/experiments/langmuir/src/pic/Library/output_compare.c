#include "elab.h"

void open_output_compare1(){
//OC1CON 
	OC1CONbits.OCSIDL = 0;	// 0 = Output compare x will continue to operate in CPU Idle mode
							// 1 = Output compare x will halt in CPU Idle mode

	OC1CONbits.OCFLT = 0;	// 0 = No PWM Fault condition has occurred
							// 1 = PWM Fault condition has occurred (cleared in HW only)

	OC1CONbits.OCTSEL = 0;	// 0 = Timer2 is the clock source for compare x
							// 1 = Timer3 is the clock source for compare x

	OC1CONbits.OCM = 6;		// 111 = PWM mode on OCx, Fault pin enabled
							// 110 = PWM mode on OCx, Fault pin disabled
							// 101 = Initialize OCx pin low, generate continuous output pulses on OCx pin
							// 100 = Initialize OCx pin low, generate single output pulse on OCx pin
							// 011 = Compare event toggles OCx pin
							// 010 = Initialize OCx pin high, compare event forces OCx pin low
							// 001 = Initialize OCx pin low, compare event forces OCx pin high
							// 000 = Output compare channel is disabled

	OC1R = 0;				// No initial delay
	//OC1RS = vol_to_pwm_converter(VOL_MAX);		// Volume MAX
}

void open_output_compare3(){
//OC3CON 
	TRISDbits.TRISD2 = 0;//Set D2 as a output
	LATDbits.LATD2 = 0;//Set D2 to low

	OC3CONbits.OCSIDL = 0;	// 0 = Output compare x will continue to operate in CPU Idle mode
							// 1 = Output compare x will halt in CPU Idle mode

	OC3CONbits.OCFLT = 0;	// 0 = No PWM Fault condition has occurred
							// 1 = PWM Fault condition has occurred (cleared in HW only)

	OC3CONbits.OCTSEL = 0;	// 0 = Timer2 is the clock source for compare x
							// 1 = Timer3 is the clock source for compare x

	OC3CONbits.OCM = 0b110; // 111 = PWM mode on OCx, Fault pin enabled
							// 110 = PWM mode on OCx, Fault pin disabled
							// 101 = Initialize OCx pin low, generate continuous output pulses on OCx pin
							// 100 = Initialize OCx pin low, generate single output pulse on OCx pin
							// 011 = Compare event toggles OCx pin
							// 010 = Initialize OCx pin high, compare event forces OCx pin low
							// 001 = Initialize OCx pin low, compare event forces OCx pin high
							// 000 = Output compare channel is disabled

	OC3R = 0;				// No initial delay
	OC3RS = 512;			//Duty_Cycle

}

void open_output_compare4(){
//OC4CON 
	TRISDbits.TRISD3 = 0;//Set D3 as a output
	LATDbits.LATD3 = 0;//Set D3 to low

	OC4CONbits.OCSIDL = 0;	// 0 = Output compare x will continue to operate in CPU Idle mode
							// 1 = Output compare x will halt in CPU Idle mode

	OC4CONbits.OCFLT = 0;	// 0 = No PWM Fault condition has occurred
							// 1 = PWM Fault condition has occurred (cleared in HW only)

	OC4CONbits.OCTSEL = 1;	// 0 = Timer2 is the clock source for compare x
							// 1 = Timer3 is the clock source for compare x

	OC4CONbits.OCM = 0b110;		// 111 = PWM mode on OCx, Fault pin enabled
							// 110 = PWM mode on OCx, Fault pin disabled
							// 101 = Initialize OCx pin low, generate continuous output pulses on OCx pin
							// 100 = Initialize OCx pin low, generate single output pulse on OCx pin
							// 011 = Compare event toggles OCx pin
							// 010 = Initialize OCx pin high, compare event forces OCx pin low
							// 001 = Initialize OCx pin low, compare event forces OCx pin high
							// 000 = Output compare channel is disabled

	OC4R = 0;				// No initial delay
	OC4RS = 512;		//Duty_Cycle

}

/*Simple PWM using the timer2*/
void open_PWM1(int period,int duty){
	T2CONbits.TON= 1;	//Enable Timer2
	PR2  = period;		//defines Timer2 Period
	OC3RS= duty;		//defines Duty_Cycle
}

/*Simple PWM using the timer3*/
void open_PWM2(int period,int duty){
	T3CONbits.TON= 1;	//Enable Timer2
	PR3  = period;		//defines Timer2 Period
	OC4RS= duty;		//defines Duty_Cycle
}

/*Simple PWM using the timer2*/
void close_PWM1(void){
	T2CONbits.TON= 0;	//Disable Timer2
	OC3CONbits.OCM = 0; //Disable OutputCompare3
}

/*Simple PWM using the timer3*/
void close_PWM2(void){
	T3CONbits.TON= 0;	//Disable Timer2
	OC4CONbits.OCM = 0; //Disable OutputCompare4
}
void close_pwm(void){
	OC1CONbits.OCM = 0;
	_LATD0 = 0;
	T2CONbits.TON=0;
}
