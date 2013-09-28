#include <p30F4011.h>
#include <stdio.h>			//biblioteca standart IO do C
#include <libpic30.h>		//definicoes extra para as bibliotecas do C30
#include <uart.h>			//biblioteca com as funcoes para usar a UART (porta serie)
#include <string.h>             //string library
#include <adc10.h>              //ADC library
#include "Library/user_defines.h"

#include "Library/elab.h"

//Configuration bits
_FOSC(CSW_FSCM_OFF & XT_PLL16);  //oscilator
_FWDT(WDT_ON & WDTPSA_512 & WDTPSB_16);  //watchdog timer (interval of ~ 16s)
//_FWDT(WDT_OFF);

_FBORPOR(PBOR_OFF & MCLR_EN & PWRT_OFF & PWMxH_ACT_HI);   	//Liga o o pino de reset do MCLR
															//e desliga os timers de energia.
_FGS(CODE_PROT_OFF);             	//Desliga a proteccao de codigo.

int main(){
	
	//TRISB=0xFFFF;	
	//ADPCFG=0xFFFF;
	configure_uart2();
	open_timer2();
	open_timer3();
	//open_servo();
	configure_adc();
	open_output_compare3();
	open_output_compare4();
	hw_uart1_init ();

	//configure_adc_channel(0);
	//configure_adc_channel(2);

	//inicia no estado STOPED
	sprintf(state,"STOPED");
	//No caso de ter havido um reset
	rec_generic_driver();
	//Importante iniciar o timer somente depois do rec_generic_driver
	//Para que não haja msg de IDS antes de ter corrido pelo
	//menos uma vez o rec_generic_driver.
	open_timer1_for_communication();

	T2CONbits.TON= 1;	//Enable Timer2
	PR2= 589;		//Timer2 Period
	OC3RS= 0;  			//sets duty-cycle to 0

	while(1){

		maq_de_estados();
		ClrWdt();



	}// END OF WHILE(1)
}// END OF MAIN
