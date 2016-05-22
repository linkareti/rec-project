#include "elab.h"

short int inicioubem;     //O primeiro valor de rotacao vem enganado entao e preciso deita-lo fora

void open_inputcapture1(){
    _IC1IF = 0;                         //Clear IC1 interrupt flag
    IC1CONbits.ICSIDL = 0;		//O modulo funciona durante o estado idle
    IC1CONbits.ICTMR = 1;		//Usar o timer2
    IC1CONbits.ICI = 0; 		//Gerar a interrupcao a cada evento de captura
}

void turn_off_inputcapture1()
{
    T2CONbits.TON = 0;                  //Turn off Timer 3
    IC1CONbits.ICM = 0;                 //desliga o IC
    _IC1IE = 0;                         //IC Interrupt disabled
    inicioubem = 0;
}

void turn_on_inputcapture1()
{
    TMR2 = 0;                           //Reset timer3 counter
    IC1BUF = 0;                         //Reset IC1BUF
    IC1CONbits.ICM = 2;                 //liga o IC e captura em cada flanco descendente
    _IC1IE = 1;                         //IC Interrupt enabled
    T2CONbits.TON = 1;                  //Turn on Timer 2
    inicioubem = 0;
}

/*
int rpmteste;
long int t, tintegrado;
int tensao;
*/

short int interrompeu;
unsigned int capture_counter;

void __attribute__((__interrupt__,auto_psv)) _IC1Interrupt(void)
{
    _IC1IF = 0;     //reset da interruptflag do input capture
    TMR2 = 0;

    capture_counter = IC1BUF;
    interrompeu = 1;

    /*
    rpmteste = 6912086/IC1BUF;

    tensao = 5*read_adc(1)/4.096;
    //tensao = 5*ADCBUF0/4.096;
    //tensao = read_adc(1);

    if(rpmteste > 1400 && rpmteste < 1500)
        inicioubem = 1;
    if(rpmteste < 1500 && rpmteste > 100 && inicioubem == 1){
        rpm = rpmteste;
        t = 10000000/rpm;
        tintegrado = tintegrado + t;
        printf("%d,%d,%d\n",IC1BUF,rpm,tensao);
    }*/
}
