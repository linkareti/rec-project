#include "Library/elab.h"


//Configuration bits
_FOSC(CSW_FSCM_OFF & XT_PLL16);  //oscilator
//_FWDT(WDT_ON & WDTPSA_512 & WDTPSB_16);  //watchdog timer (interval of ~ 16s)
_FWDT(WDT_OFF);


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

/*
///////////////////////////////
// PROTOCOLO 7 - Calibration //
///////////////////////////////
		if(protocolo == 7 && start == 1){
			start = 0;
			LASER = ON;
			delay_ms(200);
			adc_value = read_adc(ADC_CHANNEL);
			delay_ms(100);
			LASER = OFF;
			printf("ADC = %d\r", adc_value);
			sprintf(state,"READY");
		}


///////////////////////////////
// PROTOCOLO 8 - Calibration //
///////////////////////////////
		if(protocolo == 8 && start == 1){
			
			walk_motor_to((int) param_1, MOTOR_SPEED);

		}


///////////////////////////////
// PROTOCOLO 9 - Calibration //
///////////////////////////////
		if(protocolo == 9 && start == 1){
			
			if((int) param_1 == 1) LASER = ON;
			if((int) param_1 == 0) LASER = OFF;

		}


///////////////////////////////
// PROTOCOLO 10 - Calibration //
///////////////////////////////
		if(protocolo == 10 && start == 1){
			walk_motor2_to((int) param_1, MOTOR2_SPEED);			
		}


///////////////////////////////
// PROTOCOLO 12 - Calibration //
///////////////////////////////
		if(protocolo == 12 && start == 1){
			int i_aux = 0;
			if((real_position2 <= POSITION2_MAX - FOLGA2) && (real_position2 >= POSITION2_MIN + FOLGA2)){
				printf("Compensou o motor 2.\r");
				MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //put the actual position on
				while(i_aux < (int) param_2){
    				motor2_actual_position += (int) param_1;
    				MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //walks the following step
    				delay_100ys(MOTOR2_SPEED);
					i_aux++;
					printf("m_a_p = %d\r", motor2_actual_position%8);
   				}
				MOTOR2_LAT &= MOTOR2_BITS_LOW;
				printf("Compensou o motor 2.\r");
			}
		}


///////////////////////////////
// PROTOCOLO 13 - Calibration //
///////////////////////////////
		if(protocolo == 13 && start == 1){
			int i_aux = 0;
			printf("Compensou o motor 1.\r");
			MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //put the actual position on
			while(i_aux < (int) param_2){
    			motor_actual_position += (int) param_1;
    			MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //walks the following step
    			delay_100ys(MOTOR_SPEED);
				i_aux++;
				printf("m_a_p = %d\r", motor_actual_position%8);
   			}
			last_way = -last_way;
			MOTOR_LAT &= MOTOR_BITS_LOW;
			printf("Compensou o motor 1.\r");
		}

*/

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




