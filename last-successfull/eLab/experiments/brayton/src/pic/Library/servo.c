#include "elab.h"

void move_servo(short servo_state){
    T3CONbits.TON = 0;          //desliga o timer3
    TMR3=0;                     //efectua o reset ao timer3
    PR3=9217;                   //period register3 colocado a 9217 para gerar 50Hz
    OC1CONbits.OCM = 6;         //110 em decimal, output compare no modo 6, PWM com fault pin disabled
    OC1CONbits.OCTSEL = 1;      //seleccionamos a fonte timer3
    T3CONbits.TCS = 0;          //usa-se o clock interno
    T3CONbits.TCKPS = 2;        //Prescaler (0=1:1, 1=1:8, 2=1:64, 3=1:256)

    OC1RS = servo_state;

    T3CONbits.TON = 1;
}
