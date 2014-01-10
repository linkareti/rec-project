#include "elab.h"

int dutycycle;
int rpm;
int tensao;

void protocolo_0_help(void)
{
    printf("1-s/itrcoolr,aberto\t2-s/itrcoolr,c.c.\t3-s/itrcoolr,c/res\t4-c/itrcoolr,aberto\t5-c/itrcoolr,c.c.\t6-c/itrcoolr,c/res");
    sprintf(state,"STOPED");
}

void protocolo_1_configuring(void)
{
    dutycycle = param_1;
    configure_pwm_compressor(dutycycle);
}

void protocolo_2_configuring(void)
{
    dutycycle = param_1;
    configure_pwm_compressor(dutycycle);
}

void protocolo_3_configuring(void)
{
    dutycycle = param_1;
    configure_pwm_compressor(dutycycle);
}

void protocolo_4_configuring(void)
{
    dutycycle = param_1;
    configure_pwm_compressor(dutycycle);
}

void protocolo_5_configuring(void)
{
    dutycycle = param_1;
    configure_pwm_compressor(dutycycle);
}

void protocolo_6_configuring(void)
{
    dutycycle = param_1;
    configure_pwm_compressor(dutycycle);
}

void protocolo_1_starting(void)
{
    //Start timer
    T3CONbits.TON=1;	// 0-Stops the timer
                	// 1-Starts the timer
    OC3CONbits.OCM = 6;
    relay_cc(OFF);
    relay_carga(OFF);
}

void protocolo_2_starting(void)
{
    //Start timer
    T3CONbits.TON=1;	// 0-Stops the timer
                	// 1-Starts the timer
    OC3CONbits.OCM = 6;
    relay_cc(ON);
    relay_carga(OFF);
}

void protocolo_3_starting(void)
{
    //Start timer
    T3CONbits.TON=1;	// 0-Stops the timer
                	// 1-Starts the timer
    OC3CONbits.OCM = 6;
    relay_cc(OFF);
    relay_carga(ON);
}

void protocolo_4_starting(void)
{
    //Start timer
    T3CONbits.TON=1;	// 0-Stops the timer
                	// 1-Starts the timer
    OC3CONbits.OCM = 6;
    relay_cc(OFF);
    relay_carga(OFF);
}

void protocolo_5_starting(void)
{
    //Start timer
    T3CONbits.TON=1;	// 0-Stops the timer
                	// 1-Starts the timer
    OC3CONbits.OCM = 6;
    relay_cc(ON);
    relay_carga(OFF);
}

void protocolo_6_starting(void)
{
    //Start timer
    T3CONbits.TON=1;	// 0-Stops the timer
                	// 1-Starts the timer
    OC3CONbits.OCM = 6;
    relay_cc(OFF);
    relay_carga(ON);
}

long int t;

void protocolo_1_started(void)
{
    printf("DAT\r");

    LATDbits.LATD0 = 1;
    delay_ms(500);
    LATDbits.LATD0 = 0;

    while(1){
        if(interrompeu == 1){
            interrompeu = 0;
            tensao = 5*read_adc(1)/4.096;
            t = (float) capture_counter*2.170138889; //microssegundos
            rpm = (float) 60000000/(t*7/*(nºpás)*/);
            printval(rpm);
            putchar(0x09); //Put tab
            printval(tensao);
            putchar(13); //Put carriage return
            putchar(10); //Put newline
        }
        
        //Rotina para passar para sair do started state:
        if(1==2) break;
    }
    printf("END\r");
}

void protocolo_2_started(void)
{
    printf("DAT\r");

    LATDbits.LATD0 = 1;
    delay_ms(500);
    LATDbits.LATD0 = 0;

    while(1){
        if(interrompeu == 1){
            interrompeu = 0;
            tensao = 5*read_adc(1)/4.096;
            t = (float) capture_counter*2.170138889; //microssegundos
            rpm = (float) 60000000/(t*7/*(nºpás)*/);
            printval(rpm);
            putchar(0x09); //Put tab
            printval(tensao);
            putchar(13); //Put carriage return
            putchar(10); //Put newline
        }

        //Rotina para passar para sair do started state:
        if(1==2) break;
    }
    printf("END\r");
}
void protocolo_3_started(void)
{
    printf("DAT\r");

    LATDbits.LATD0 = 1;
    delay_ms(500);
    LATDbits.LATD0 = 0;

    while(1){
        if(interrompeu == 1){
            interrompeu = 0;
            tensao = 5*read_adc(1)/4.096;
            t = (float) capture_counter*2.170138889; //microssegundos
            rpm = (float) 60000000/(t*7/*(nºpás)*/);
            printval(rpm);
            putchar(0x09); //Put tab
            printval(tensao);
            putchar(13); //Put carriage return
            putchar(10); //Put newline
        }

        //Rotina para passar para sair do started state:
        if(1==2) break;
    }
    printf("END\r");
}

void protocolo_4_started(void)
{
    printf("DAT\r");

    LATDbits.LATD0 = 1;
    delay_ms(500);
    LATDbits.LATD0 = 0;

    while(1){
        if(interrompeu == 1){
            interrompeu = 0;
            tensao = 5*read_adc(1)/4.096;
            t = (float) capture_counter*2.170138889; //microssegundos
            rpm = (float) 60000000/(t*7/*(nºpás)*/);
            printval(rpm);
            putchar(0x09); //Put tab
            printval(tensao);
            putchar(13); //Put carriage return
            putchar(10); //Put newline
        }

        //Rotina para passar para sair do started state:
        if(1==2) break;
    }
    printf("END\r");
}

void protocolo_5_started(void)
{
    printf("DAT\r");

    LATDbits.LATD0 = 1;
    delay_ms(500);
    LATDbits.LATD0 = 0;

    while(1){
        if(interrompeu == 1){
            interrompeu = 0;
            tensao = 5*read_adc(1)/4.096;
            t = (float) capture_counter*2.170138889; //microssegundos
            rpm = (float) 60000000/(t*7/*(nºpás)*/);
            printval(rpm);
            putchar(0x09); //Put tab
            printval(tensao);
            putchar(13); //Put carriage return
            putchar(10); //Put newline
        }

        //Rotina para passar para sair do started state:
        if(1==2) break;
    }
    printf("END\r");
}

void protocolo_6_started(void)
{
    printf("DAT\r");

    LATDbits.LATD0 = 1;
    delay_ms(500);
    LATDbits.LATD0 = 0;

    while(1){
        if(interrompeu == 1){
            interrompeu = 0;
            tensao = 5*read_adc(1)/4.096;
            t = (float) capture_counter*2.170138889; //microssegundos
            rpm = (float) 60000000/(t*7/*(nºpás)*/);
            printval(rpm);
            putchar(0x09); //Put tab
            printval(tensao);
            putchar(13); //Put carriage return
            putchar(10); //Put newline
        }

        //Rotina para passar para sair do started state:
        if(1==2) break;
    }
    printf("END\r");
}

void stopping(void)
{
    //Start timer
    T2CONbits.TON=0;	// 0-Stops the timer
                	// 1-Starts the timer
}
