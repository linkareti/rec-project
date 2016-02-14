#include <p30F4011.h>
#include "world_pendulum.h"

#define HALF_STEP

#define WINDING_1_UP   asm("bset LATF, #0"); asm("bset LATE, #0"); asm("bclr LATE, #1");
#define WINDING_1_DOWN asm("bset LATF, #0"); asm("bclr LATE, #0"); asm("bset LATE, #1");
#define WINDING_1_OFF  asm("bclr LATF, #0");

#define WINDING_2_UP   asm("bset LATF, #1"); asm("bset LATE, #2"); asm("bclr LATE, #3");
#define WINDING_2_DOWN asm("bset LATF, #1"); asm("bclr LATE, #2"); asm("bset LATE, #3");
#define WINDING_2_OFF  asm("bclr LATF, #1");

static unsigned int f0, f1;

void stepBipolar(int direction) {
	static int pos;
	
	TRISFbits.TRISF0 = 0;
	TRISFbits.TRISF1 = 0;
	TRISEbits.TRISE0 = 0;
	TRISEbits.TRISE1 = 0;
	TRISEbits.TRISE2 = 0;
	TRISEbits.TRISE3 = 0;
	
#ifdef HALF_STEP
	if(direction == DIRECTION_FORWARD) {
		if(pos == 0)      { WINDING_1_UP	WINDING_2_OFF	}//1
		else if(pos == 1) { WINDING_1_UP	WINDING_2_UP	}//2
		else if(pos == 2) { WINDING_1_OFF	WINDING_2_UP	}//3
		else if(pos == 3) { WINDING_1_DOWN	WINDING_2_UP	}//4
		else if(pos == 4) { WINDING_1_DOWN	WINDING_2_OFF	}//5
		else if(pos == 5) { WINDING_1_DOWN	WINDING_2_DOWN	}//6
		else if(pos == 6) { WINDING_1_OFF	WINDING_2_DOWN	}//7
		else if(pos == 7) { WINDING_1_UP	WINDING_2_DOWN	}//0
		pos++;
	}
	else if(direction == DIRECTION_BACKWARD) {
		if(pos == 0)      { WINDING_1_OFF	WINDING_2_DOWN	}//7
		else if(pos == 1) { WINDING_1_UP	WINDING_2_DOWN	}//0
		else if(pos == 2) { WINDING_1_UP	WINDING_2_OFF	}//1
		else if(pos == 3) { WINDING_1_UP	WINDING_2_UP	}//2
		else if(pos == 4) { WINDING_1_OFF	WINDING_2_UP	}//3
		else if(pos == 5) { WINDING_1_DOWN	WINDING_2_UP	}//4
		else if(pos == 6) { WINDING_1_DOWN	WINDING_2_OFF	}//5
		else if(pos == 7) { WINDING_1_DOWN	WINDING_2_DOWN	}//6
		pos--;
	}
	pos &= 0x0007;
#else
	if(direction == DIRECTION_FORWARD) {
		if(pos == 0)      { WINDING_1_UP	WINDING_2_OFF	}//1
		else if(pos == 1) { WINDING_1_OFF	WINDING_2_UP	}//2
		else if(pos == 2) { WINDING_1_DOWN	WINDING_2_OFF	}//3
		else if(pos == 3) { WINDING_1_OFF	WINDING_2_DOWN	}//0
		pos++;
	}
	else if(direction == DIRECTION_BACKWARD) {
		if(pos == 0)      { WINDING_1_DOWN	WINDING_2_OFF	}//3
		else if(pos == 1) { WINDING_1_OFF	WINDING_2_DOWN	}//0
		else if(pos == 2) { WINDING_1_UP	WINDING_2_OFF	}//1
		else if(pos == 3) { WINDING_1_OFF	WINDING_2_UP	}//2
		pos--;
	}
	pos &= 0x0003;
#endif
}




void releaseBipolar() {
	TRISFbits.TRISF0 = 0;
	TRISFbits.TRISF1 = 0;
	TRISEbits.TRISE0 = 0;
	TRISEbits.TRISE1 = 0;
	TRISEbits.TRISE2 = 0;
	TRISEbits.TRISE3 = 0;
	f0 = PORTFbits.RF0;
	f1 = PORTFbits.RF1;
	asm("bclr LATF, #0");
	asm("bclr LATF, #1");
}

void holdBipolar() {
	TRISFbits.TRISF0 = 0;
	TRISFbits.TRISF1 = 0;
	TRISEbits.TRISE0 = 0;
	TRISEbits.TRISE1 = 0;
	TRISEbits.TRISE2 = 0;
	TRISEbits.TRISE3 = 0;
	if(f0 == 0 && f1 == 0) {
		f0 = 1;
		WINDING_1_UP
	}
	LATFbits.LATF0 = f0;
	LATFbits.LATF1 = f1;
}

