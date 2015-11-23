#include "world_pendulum.h"

//FCY is defined in world_pendulum.h

//delay in miliseconds
void delay_ms(unsigned int delay) {
	static unsigned int cycles;
	for(;delay;delay--) {
		for(cycles=FCY/3636; cycles; cycles--);
		asm("clrwdt");
	}
}
