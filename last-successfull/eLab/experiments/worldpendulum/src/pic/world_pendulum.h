#ifndef WORLD_PENDULUM
#define WORLD_PENDULUM

//#define FCY 29491200UL	//instruction frequency in Hz (used by the pic30 libraries in the function __delay_ms)
#define FCY 1843200UL	//instruction frequency in Hz (used by the pic30 libraries in the function __delay_ms)

#define TOGGLE_LED1			asm("btg LATD, #3")		//macro for toggling led1
#define LED1				LATDbits.LATD3			//macro for led1
#define LED1_ON				asm("bclr LATD, #3")	//macro for led1 on
#define LED1_OFF			asm("bset LATD, #3")	//macro for led1 off

#define TOGGLE_LED2			asm("btg LATD, #2")		//macro for toggling led2
#define LED2				LATDbits.LATD2			//macro for led2
#define LED2_ON				asm("bclr LATD, #2")	//macro for led2 on
#define LED2_OFF			asm("bset LATD, #2")	//macro for led2 off

#define PUSH_BUTTON			PORTEbits.RE8			//macro for push-button
#define MICROSWITCH			PORTBbits.RB3
#define PHOTODIODE			PORTBbits.RB2

#define OK					33
#define NOT_OK				34

#define YES					13
#define NO					14
#define NOT_AVAIL			-1

#define DELTAX_MIN 5
#define DELTAX_MAX 15
#define NOSC_MIN 10
#define NOSC_MAX 1000

#define MAX_SHOVEL_DISPLACEMENT_CM	50

#define DIRECTION_FORWARD			88
#define DIRECTION_BACKWARD			99


#endif
