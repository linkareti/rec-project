#include <p30F4011.h>
#include "world_pendulum.h"

#define HALF_STEP

#define SET_WINDING_1 asm("bset LATE, #4"); asm("bset LATB, #8");
#define SET_WINDING_2 asm("bset LATF, #6"); asm("bset LATD, #0");
#define SET_WINDING_3 asm("bset LATE, #5"); asm("bset LATD, #2");
#define SET_WINDING_4 asm("bset LATD, #1"); asm("bset LATD, #3");

#define RESET_WINDING_1 asm("bclr LATE, #4"); asm("bclr LATB, #8");
#define RESET_WINDING_2 asm("bclr LATF, #6"); asm("bclr LATD, #0");
#define RESET_WINDING_3 asm("bclr LATE, #5"); asm("bclr LATD, #2");
#define RESET_WINDING_4 asm("bclr LATD, #1"); asm("bclr LATD, #3");

void stepUnipolar();
void freeUnipolar();
void holdUnipolar();



void stepUnipolar(int direction) {
	static int pos;
	
	TRISEbits.TRISE4 = 0; TRISBbits.TRISB8 = 0;
	TRISFbits.TRISF6 = 0; TRISDbits.TRISD0 = 0;
	TRISDbits.TRISD1 = 0; TRISDbits.TRISD3 = 0;
	TRISEbits.TRISE5 = 0; TRISDbits.TRISD2 = 0;
	
#ifdef HALF_STEP
	if(direction == DIRECTION_FORWARD) {
		if(pos == 0)      { SET_WINDING_1   RESET_WINDING_2 RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 1) { SET_WINDING_1   SET_WINDING_2   RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 2) { RESET_WINDING_1 SET_WINDING_2   RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 3) { RESET_WINDING_1 SET_WINDING_2   SET_WINDING_3   RESET_WINDING_4 }
		else if(pos == 4) { RESET_WINDING_1 RESET_WINDING_2 SET_WINDING_3   RESET_WINDING_4 }
		else if(pos == 5) { RESET_WINDING_1 RESET_WINDING_2 SET_WINDING_3   SET_WINDING_4   }
		else if(pos == 6) { RESET_WINDING_1 RESET_WINDING_2 RESET_WINDING_3 SET_WINDING_4   }
		else if(pos == 7) { SET_WINDING_1   RESET_WINDING_2 RESET_WINDING_3 SET_WINDING_4   }
		pos++;
	}
	else if(direction == DIRECTION_BACKWARD) {
		if(pos == 0)      { RESET_WINDING_1 RESET_WINDING_2 RESET_WINDING_3 SET_WINDING_4   }
		else if(pos == 1) { RESET_WINDING_1 RESET_WINDING_2 SET_WINDING_3   SET_WINDING_4 }
		else if(pos == 2) { RESET_WINDING_1 RESET_WINDING_2 SET_WINDING_3   RESET_WINDING_4 }
		else if(pos == 3) { RESET_WINDING_1 SET_WINDING_2   SET_WINDING_3   RESET_WINDING_4 }
		else if(pos == 4) { RESET_WINDING_1 SET_WINDING_2   RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 5) { SET_WINDING_1   SET_WINDING_2   RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 6) { SET_WINDING_1   RESET_WINDING_2 RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 7) { SET_WINDING_1   RESET_WINDING_2 RESET_WINDING_3 SET_WINDING_4 }
		pos--;
	}
	pos &= 0x0007;
#else
	if(direction == DIRECTION_FORWARD) {
		if(pos == 0)      { SET_WINDING_1 RESET_WINDING_2 RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 1) { RESET_WINDING_1 SET_WINDING_2 RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 2) { RESET_WINDING_1 RESET_WINDING_2 SET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 3) { RESET_WINDING_1 RESET_WINDING_2 RESET_WINDING_3 SET_WINDING_4 }
		pos++;
	}
	else if(direction == DIRECTION_BACKWARD) {
		if(pos == 0)      { RESET_WINDING_1 RESET_WINDING_2 SET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 1) { RESET_WINDING_1 SET_WINDING_2 RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 2) { SET_WINDING_1 RESET_WINDING_2 RESET_WINDING_3 RESET_WINDING_4 }
		else if(pos == 3) { RESET_WINDING_1 RESET_WINDING_2 RESET_WINDING_3 SET_WINDING_4 }
		pos--;
	}
	pos &= 0x0003;
#endif
}





void freeUnipolar() {
	TRISEbits.TRISE4 = 1; TRISBbits.TRISB8 = 1;
	TRISFbits.TRISF6 = 1; TRISDbits.TRISD0 = 1;
	TRISDbits.TRISD1 = 1; TRISDbits.TRISD3 = 1;
	TRISEbits.TRISE5 = 1; TRISDbits.TRISD2 = 1;
}

void holdUnipolar() {
	TRISEbits.TRISE4 = 0; TRISBbits.TRISB8 = 0;
	TRISFbits.TRISF6 = 0; TRISDbits.TRISD0 = 0;
	TRISDbits.TRISD1 = 0; TRISDbits.TRISD3 = 0;
	TRISEbits.TRISE5 = 0; TRISDbits.TRISD2 = 0;
}
