#include "elab.h"

int angle, angle_2, adc_value;
double param_1_aux, param_4_aux, param_2_aux; 
double angle_2_min = 360./REAL_MAX_POS;

void protocolo_1_configuring(void)
{
	//param_1 -> angulo1_minimo
	//param_2 -> angulo1_maximo
	//param_3 -> delta_angulo1
	//param_4 -> delay
	move_servo(FREE);
	angle = angle_1_conversion(param_1);
	if((angle < POSITION_MIN) || (angle > POSITION_MAX)) printf("ERR\t%d\r", 3);
}


void protocolo_2_configuring(void)
{
	//param_1 -> polarizacao: 1-sim
	//						  0-nao
	//param_2 -> angulo_de_polarizacao de 0º a 90º (pode ser qualquer valor caso param_1 = 0)
	//param_3 -> angulo1
	if((param_2 < 0) || (param_2 > 90)) printf("ERR\t%d\r", 3);
	if((angle_1_conversion(param_3) < POSITION_MIN) || (angle_1_conversion(param_3) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if(param_1 == 1) move_servo((int)(((POL_HOR - POL_VER)/90.)*param_2 + POL_VER));
	if(param_1 == 0) move_servo(FREE);
	walk_motor_to(angle_1_conversion(param_3), MOTOR_SPEED);
	angle = POSITION2_MIN;
	ClrWdt();
	walk_motor2_to(angle, MOTOR2_SPEED);
	param_1_aux = conv_angle_2(angle);
}


void protocolo_3_configuring(void)
{
	//param_1 -> angulo1_minimo
	//param_2 -> angulo1_maximo
	//param_3 -> delta_angulo1
	if((angle_1_conversion(param_1) < POSITION_MIN) || (angle_1_conversion(param_1) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if((angle_1_conversion(param_2) < POSITION_MIN) || (angle_1_conversion(param_2) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	move_servo(FREE);
	walk_motor_to(angle_1_conversion(param_1), MOTOR_SPEED);	
	ClrWdt();
	walk_motor2_to(POSITION2_MIN, MOTOR2_SPEED);		
	angle = angle_1_conversion(param_1);
}

void protocolo_4_configuring(void)
{
	//param_1 -> angulo_de_polarizacao		
	//param_2 -> angulo1_minimo
	//param_3 -> angulo1_maximo
	//param_4 -> delta_angulo1
	if((angle_1_conversion(param_2) < POSITION_MIN) || (angle_1_conversion(param_2) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if((angle_1_conversion(param_3) < POSITION_MIN) || (angle_1_conversion(param_3) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if((param_1 < 0) || (param_1 > 90)) printf("ERR\t%d\r", 3);
	move_servo((int)(((POL_HOR - POL_VER)/90.)*param_1 + POL_VER));		
	walk_motor_to(angle_1_conversion(param_2), MOTOR_SPEED);
	ClrWdt();
	walk_motor2_to(POSITION2_MIN, MOTOR2_SPEED);			
	angle = angle_1_conversion(param_2);
}


void protocolo_1_starting(void)
{
	LASER = ON;
}


void protocolo_2_starting(void)
{
	LASER = ON;
}


void protocolo_3_starting(void)
{
	LASER = ON;
}

void protocolo_4_starting(void)
{			
	LASER = ON;
}

void protocolo_1_started(void)
{
	//DAT:
	//param_1 -> angulo1
	//END
	printf("DAT\r");			
	while((angle <= angle_1_conversion(param_2)) && !stop && !reset && (angle >= POSITION_MIN) && (angle <= POSITION_MAX)){
		ClrWdt();
		walk_motor_to(angle, MOTOR_SPEED);
		printf("%lf\tX\tX\r", conv_angle_1(angle));
		delay_ms(param_4*1000);
		param_1_aux += param_3;
		angle = angle_1_conversion(param_1_aux);
		if((angle < (POSITION_MAX + angle_1_conversion(param_3))) && (angle > POSITION_MAX)) angle = POSITION_MAX;	
	}
	printf("END\r");
	LASER = OFF;
}


void protocolo_2_started(void)
{
	//DAT:
	//param_1 -> angulo2
	//param_2 -> adc_value
	//END
	printf("DAT\r");			
	while((angle <= POSITION2_MAX) && !stop && !reset){
		ClrWdt();
		walk_motor2_to(angle, MOTOR2_SPEED);
		adc_value = read_adc(ADC_CHANNEL);
		printf("%lf\t%d\tX\r", conv_angle_2(angle), adc_value);
		param_1_aux += angle_2_min;
		angle = angle_2_conversion(param_1_aux);		
	}	
	printf("END\r");
	LASER = OFF;
}


void protocolo_3_started(void)
{
		//DAT:
		//param_1 -> angulo1
		//param_2 -> angulo2
		//param_3 -> adc_value
		//END
			printf("DAT\r");
			while((angle <= angle_1_conversion(param_2)) && !stop && (angle >= POSITION_MIN) && (angle <= POSITION_MAX)){
				ClrWdt();
				walk_motor_to(angle, MOTOR_SPEED);
				angle_2 = POSITION2_MIN;
				param_4_aux = conv_angle_2(POSITION2_MIN);
				while((angle_2 <= POSITION2_MAX) && !stop){
					ClrWdt();
					walk_motor2_to(angle_2, MOTOR2_SPEED);
					adc_value = read_adc(ADC_CHANNEL);
					printf("%lf\t%lf\t%d\r", conv_angle_1(angle), conv_angle_2(angle_2), adc_value);
					param_4_aux += angle_2_min;
					angle_2 = angle_2_conversion(param_4_aux);
					if((angle_2 < (POSITION2_MAX + angle_2_conversion(angle_2_min))) && (angle_2 > POSITION2_MAX)) angle_2 = POSITION2_MAX;
				}
				param_1_aux += param_3;
				angle = angle_1_conversion(param_1_aux);
			}
			printf("END\r");
			LASER = OFF;
}


void protocolo_4_started(void)
{
	//DAT:
	//param_1 -> angulo1
	//param_2 -> angulo2
	//param_3 -> adc_value
	//END
	printf("DAT\r");
	while((angle <= angle_1_conversion(param_3)) && !stop && (angle >= POSITION_MIN) && (angle <= POSITION_MAX)){
		ClrWdt();
		walk_motor_to(angle, MOTOR_SPEED);
		angle_2 = POSITION2_MIN;
		param_1_aux = conv_angle_2(POSITION2_MIN);
		while((angle_2 <= POSITION2_MAX) && !stop){
			ClrWdt();
			walk_motor2_to(angle_2, MOTOR2_SPEED);
			adc_value = read_adc(ADC_CHANNEL);
			printf("%lf\t%lf\t%d\r", conv_angle_1(angle), conv_angle_2(angle_2), adc_value);
			param_1_aux += angle_2_min;
			angle_2 = angle_2_conversion(param_1_aux);
			if((angle_2 < (POSITION2_MAX + angle_2_conversion(angle_2_min))) && (angle_2 > POSITION2_MAX)) angle_2 = POSITION2_MAX;
		}
		param_2_aux += param_4;
		angle = angle_1_conversion(param_2_aux);
	}
	printf("END\r");
	LASER = OFF;
}


void protocolo_5_started(void)
{
	calibrate_motor2();
	calibrate_motor();
}
