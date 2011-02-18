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
	//param_5 -> 0
	//param_6 -> 0
	//param_7 -> 0
	move_servo(FREE);
	angle = angle_1_conversion(param_1);
	param_1_aux = param_1;	//Inicializacao do param_1_aux
	if((angle < POSITION_MIN) || (angle > POSITION_MAX)) printf("ERR\t%d\r", 3);
	walk_motor_to(angle, MOTOR_SPEED);
}


void protocolo_2_configuring(void)
{
	//param_1 -> angulo1
	//param_2 -> 360
	//param_3 -> 0.2
	//param_4 -> 0
	//param_5 -> polarizacao: 1-sim
	//						  0-nao
	//param_6 -> angulo_de_polarizacao de 0º a 90º (pode ser qualquer valor caso param_1 = 0)
	//param_7 -> 0
	if((param_6 < 0) || (param_6 > 90)) printf("ERR\t%d\r", 3);
	if((angle_1_conversion(param_1) < POSITION_MIN) || (angle_1_conversion(param_1) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if(param_5 == 1) move_servo((int)(((POL_HOR - POL_VER)/90.)*param_6 + POL_VER));
	if(param_5 == 0) move_servo(FREE);
	walk_motor_to(angle_1_conversion(param_1), MOTOR_SPEED);
	angle = POSITION2_MIN;
	ClrWdt();
	walk_motor2_to(angle, MOTOR2_SPEED);
	param_1_aux = conv_angle_2(angle);
}


void protocolo_3_configuring(void)
{
	//param_1 -> angulo1_minimo
	//param_2 -> angulo1_maximo
	//param_3 -> 0.2 (porque quero incrementar com a resolucao maxima)
	//param_4 -> 0
	//param_5 -> 0
	//param_6 -> 0
	//param_7 -> check_box
	if((angle_1_conversion(param_1) < POSITION_MIN) || (angle_1_conversion(param_1) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if((angle_1_conversion(param_2) < POSITION_MIN) || (angle_1_conversion(param_2) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	move_servo(FREE);	//Tira o polarizador para uma zona em que nao interfere
	walk_motor_to(angle_1_conversion(param_1), MOTOR_SPEED);	//Posiciona o pexiglass no inicial
	ClrWdt();
	walk_motor2_to(POSITION2_MIN, MOTOR2_SPEED);	//Posiciona o sensor no anguor inicial
	angle = angle_1_conversion(param_1);
	param_1_aux = param_1;	//Inicializacao do param_1_aux
}

void protocolo_4_configuring(void)
{
	//param_1 -> angulo1_minimo
	//param_2 -> angulo1_maximo
	//param_3 -> 0.2
	//param_4 -> 0
	//param_5 -> 0
	//param_6 -> angulo_de_polarizacao
	//param_7 -> check_box
	if((angle_1_conversion(param_1) < POSITION_MIN) || (angle_1_conversion(param_1) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if((angle_1_conversion(param_3) < POSITION_MIN) || (angle_1_conversion(param_3) > POSITION_MAX)) printf("ERR\t%d\r", 3);
	if((param_6 < 0) || (param_6 > 90)) printf("ERR\t%d\r", 3);
	move_servo((int)(((POL_HOR - POL_VER)/90.)*param_6 + POL_VER));		
	walk_motor_to(angle_1_conversion(param_1), MOTOR_SPEED);
	ClrWdt();
	walk_motor2_to(POSITION2_MIN, MOTOR2_SPEED);			
	angle = angle_1_conversion(param_1);
	param_2_aux = param_1;	//Inicializacao do param_2_aux
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
	//param_1 -> angulo1 (pexiglass)
	//param_2 -> 10.0
	//param_3 -> 0
	//END
	printf("DAT\r");			
	while((angle <= angle_1_conversion(param_2)) && !stop && (angle >= POSITION_MIN) && (angle <= POSITION_MAX)){
		ClrWdt();
		walk_motor_to(angle, MOTOR_SPEED);
		printf("%lf\t%lf\t%d\r", conv_angle_1(angle), 10.0, 0);
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
	//param_1 -> angulo1
	//param_2 -> angulo2 (sensor)
	//param_3 -> adc_value
	//END
	printf("DAT\r");			
	while((angle <= POSITION2_MAX) && !stop){
		ClrWdt();
		walk_motor2_to(angle, MOTOR2_SPEED);
		adc_value = read_adc(ADC_CHANNEL);
		printf("%lf\t%lf\t%d\t\r", param_1, conv_angle_2(angle), adc_value);
		param_1_aux += angle_2_min;
		angle = angle_2_conversion(param_1_aux);		
	}	
	printf("END\r");
	LASER = OFF;
}


void protocolo_3_started(void)
{
	//DAT:
	//param_1 -> angulo1 (pexiglass)
	//param_2 -> angulo2 (sensor)
	//param_3 -> adc_value
	//END
		printf("DAT\r");
		while((angle <= angle_1_conversion(param_2)) && !stop && (angle >= POSITION_MIN) && (angle <= POSITION_MAX)){
			ClrWdt();
			walk_motor_to(angle, MOTOR_SPEED);
			angle_2 = POSITION2_MIN;	//Unidades de step motor
			param_4_aux = conv_angle_2(POSITION2_MIN);	//Unidades de grau angular
			//if para executar a checkbox
			if(((param_7%2 == 1) && (param_1_aux == param_1)) || 
				(((param_7 == 2) || (param_7 == 3) || (param_7 == 6) || (param_7 == 7)) && 
					((param_1_aux >= (param_1 + (double)((int)((param_2 - param_1)/2./param_3 + 0.5)) * param_3 - param_3/2)) &&  
					(param_1_aux < (param_1 + (double)((int)((param_2 - param_1)/2./param_3 + 0.5)) * param_3 + param_3/2)))) ||
				((param_7 >= 4) && (angle > angle_1_conversion(param_2 - param_3)))){
				//Varimento do sensor
				while((angle_2 <= POSITION2_MAX) && !stop){
					ClrWdt();
					walk_motor2_to(angle_2, MOTOR2_SPEED);
					adc_value = read_adc(ADC_CHANNEL);
					printf("%lf\t%lf\t%d\r", conv_angle_1(angle), conv_angle_2(angle_2), adc_value);	//Output dos resultados
					param_4_aux += angle_2_min;	//Incrementa o angulo do valor minimo possivel
					angle_2 = angle_2_conversion(param_4_aux);	//Converte para unidades de step motor
					if((angle_2 < (POSITION2_MAX + angle_2_conversion(angle_2_min))) && (angle_2 > POSITION2_MAX))
						angle_2 = POSITION2_MAX;
				}
			}
			param_1_aux += param_3;
			angle = angle_1_conversion(param_1_aux);
		}
	if(param_7 == 0) printf("0\t8\t0\r");
	printf("END\r");
	LASER = OFF;
}


void protocolo_4_started(void)
{
	//DAT:
	//param_1 -> angulo1 (pexiglass)
	//param_2 -> angulo2 (sensor)
	//param_3 -> adc_value
	//END
	printf("DAT\r");
	while((angle <= angle_1_conversion(param_2)) && !stop && (angle >= POSITION_MIN) && (angle <= POSITION_MAX)){
		ClrWdt();
		walk_motor_to(angle, MOTOR_SPEED);
		angle_2 = POSITION2_MIN;
		param_1_aux = conv_angle_2(POSITION2_MIN);
		//if para executar a checkbox
		if(((param_7%2 == 1) && (param_2_aux == param_1)) || 
				(((param_7 == 2) || (param_7 == 3) || (param_7 == 6) || (param_7 == 7)) && 
					((param_2_aux >= (param_1 + (double)((int)((param_2 - param_1)/2./param_3 + 0.5)) * param_3 - param_3/2)) &&  
					(param_2_aux < (param_1 + (double)((int)((param_2 - param_1)/2./param_3 + 0.5)) * param_3 + param_3/2)))) ||
				((param_7 >= 4) && (angle > angle_1_conversion(param_2 - param_3)))){
			//Varimento do sensor
			while((angle_2 <= POSITION2_MAX) && !stop){
				ClrWdt();
				walk_motor2_to(angle_2, MOTOR2_SPEED);
				adc_value = read_adc(ADC_CHANNEL);
				printf("%lf\t%lf\t%d\r", conv_angle_1(angle), conv_angle_2(angle_2), adc_value);
				param_1_aux += angle_2_min;
				angle_2 = angle_2_conversion(param_1_aux);
				if((angle_2 < (POSITION2_MAX + angle_2_conversion(angle_2_min))) && (angle_2 > POSITION2_MAX)) angle_2 = POSITION2_MAX;
			}
		}
		param_2_aux += param_3;
		angle = angle_1_conversion(param_2_aux);
	}
	if(param_7 == 0) printf("0\t8\t0\r");
	printf("END\r");
	LASER = OFF;
}


void protocolo_5_started(void)
{
	printf("DAT\r");
	if(param_7 == 1 || param_7 == 2) calibrate_motor2();
	printf("0\t8\t0\r");
	if(param_7 == 0 || param_7 == 2) calibrate_motor();
	printf("END\r");
}


void stopping(void)
{
	LASER = OFF;
}
