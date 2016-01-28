#include "elab.h"

void configure_pwm_compressor(int dutycycle){
    OC3CONbits.OCM = 0;         //Desliga PWM
    OC3RS = (dutycycle/100.)*PR3;	//Duty cycle é 50% de PR2, isto é, metade do período do PWM
    OC3CONbits.OCTSEL = 1;	//A fonte de impulsos é o timer 3
}
