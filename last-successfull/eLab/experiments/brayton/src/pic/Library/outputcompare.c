#include "elab.h"

void configure_pwm_compressor(int dutycycle){
    OC3CONbits.OCM = 0;         //Desliga PWM
    OC3RS = (dutycycle/100.)*PR3;	//Duty cycle � 50% de PR2, isto �, metade do per�odo do PWM
    OC3CONbits.OCTSEL = 1;	//A fonte de impulsos � o timer 3
}
