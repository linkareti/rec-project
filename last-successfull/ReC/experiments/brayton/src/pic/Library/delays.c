#include "elab.h"

void delay_ms(unsigned int delay){   //delay in miliseconds 
 unsigned int cycles;
 for(;delay;delay--)
    for(cycles=FCY/4;cycles;cycles--);
}

void delay_100ys(unsigned int delay){   //delay in 100 yseconds 
 unsigned int cycles;
 for(;delay;delay--)
    for(cycles=FCY/40;cycles;cycles--);
}

void delay_10ys(unsigned int delay){   //delay in 10 yseconds (approximately) 
 unsigned int cycles;
 for(;delay;delay--)
    for(cycles=FCY/400;cycles;cycles--);
}
