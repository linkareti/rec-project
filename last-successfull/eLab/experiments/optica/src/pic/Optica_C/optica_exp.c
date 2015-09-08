#include "Library/elab.h"


//Configuration bits
_FOSC(CSW_FSCM_OFF & XT_PLL16);  //oscilator
_FWDT(WDT_ON & WDTPSA_512 & WDTPSB_16);  //watchdog timer (interval of ~ 16s)
//_FWDT(WDT_OFF);


int main(){
	
	TRISB=0x0000;	
	ADPCFG=0xFFFF;
	configure_uart2();
	open_motor();
	open_motor2();
	open_servo();
	configure_adc();
	configure_adc_channel(0);

	LASER_TRIS = OUTPUT;
	LASER = OFF;

	//Calibracao dos motores
	ClrWdt();
	calibrate_motor2();
	ClrWdt();
	calibrate_motor();

	//inicia no estado STOPED
	sprintf(state,"STOPED");
	//No caso de ter havido um reset
	rec_generic_driver();
	//Importante iniciar somente depois do rec_generic_driver
	//Para que não haja msg de IDS antes de ter corrido pelo
	//menos uma vez o rec_generic_driver.
	open_timer1_for_communication();



	while(1){

		maq_de_estados();
		ClrWdt();

	}// END OF WHILE(1)
}// END OF MAIN


//Functions to convert angles
//Convert angle to steps
int angle_1_conversion(double angle){
	return ((int) ((POSITION_MAX*angle/360.)+0.5));
}
//Convert steps to angle
double conv_angle_1(int angle){
	return ((double) ((angle*360.)/POSITION_MAX));
}
//Convert angle to steps
int angle_2_conversion(double angle){
	return ((int) ((REAL_MAX_POS*angle/360.)+0.5));
}
//Convert steps to angle
double conv_angle_2(int angle){
	return ((double) ((angle*360.)/REAL_MAX_POS));
}




