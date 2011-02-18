#include "elab.h"

//macros
//#define MOTOR_BITS_LOW (~(0xF<<MOTOR_FIRST_BIT))

const unsigned char step [2][8]= {{1,3,2,6,4,12,8,9},   //Sucessive configurations of the motor control pins while motor is moving
                                  {8,12,4,6,2,3,1,9}};

unsigned int motor_actual_position = 0;
int real_position = 500;
int last_way = -1;
//int aux_last;

void open_motor(){
     MOTOR_LAT&=MOTOR_BITS_LOW; //put MOTOR pin LOW (motor stoped)
     MOTOR_TRIS&=MOTOR_BITS_LOW; //define MOTOR pins as output
     //END_CURSE_TRIS=INPUT;       //define end curse pin as input
     motor_actual_position=POSITION_MAX;
     }

void walk_motor(unsigned int way, unsigned int steps, unsigned int delay){ //the motor walks steps
  unsigned int i;
  for(i=steps;i && (way);i--){                           //there is only end_curse for way==1
    MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[way][i%8]<<MOTOR_FIRST_BIT);//walk the following step
    delay_100ys(delay);
    }
  MOTOR_LAT&=MOTOR_BITS_LOW; //put MOTOR pins LOW (motor stoped)
}

/*
void walk_motor_to(unsigned int to, unsigned int delay){  //the motor walks to the position to
   unsigned int i;
   char way;
   i=motor_actual_position;
   if(to==0) {i=POSITION_MAX;}  //if to==0, motor walks until the END_CURSE
   else if(to>POSITION_MAX) to=POSITION_MAX;
   if(i<to) way=1;
   else way=-1;
   while(way==1 && i!=to){
    MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][i%8]<<MOTOR_FIRST_BIT); //walks the following step
    i+=way;
    delay_100ys(delay);
   }
   MOTOR_LAT&=MOTOR_BITS_LOW; //put MOTOR pins LOW (motor stoped)
   if(way==-1) motor_actual_position=0;  //actual position is calibrated in the END_CURSE
   else motor_actual_position=i;
}
*/

void walk_motor_to(unsigned int to, unsigned int delay){
	int limit = 0;
	int way; 
	int i_aux = 0;
	if(to > POSITION_MAX) to = POSITION_MAX;
	if(to < POSITION_MIN) to = POSITION_MIN;
	(real_position < to) ? (way = 1) : (way = -1);
	if(real_position == to) way = 0;
	motor_compensation(MOTOR2_SPEED, way);
	if(last_way == -way){
		MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //put the actual position on
    	delay_100ys(delay);		
		while(i_aux < FOLGA){
    		motor_actual_position += way;
    		MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //walks the following step
    		delay_100ys(delay);
			i_aux++;
   		}
	}
	if(real_position != to) last_way = way;
	if((real_position == POSITION_MAX) || (real_position == POSITION_MIN)){
		limit = 1;
		real_position += way;
	}
	MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //put the actual position on
    delay_100ys(delay);	
	while((real_position != to) && (real_position < POSITION_MAX) && (real_position > POSITION_MIN)){
		motor_actual_position += way;    	
		MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //walks the following step
		real_position += way;
    	delay_100ys(delay);
		ClrWdt();
   	}
	if(limit == 1) real_position -= way;
	MOTOR_LAT &= MOTOR_BITS_LOW; //put MOTOR pins LOW (motor stoped)
	//printf("real_position: %d\r", real_position);
}

/*
void calibrate_motor(){
	int x = 0;
	int i;
	int x_last_0 = 0;
	int x_last_1 = 0;
	int x_last_2 = 0;
	int detected = 0;
	int count = 0;
	int aux = 0, aux_last = 0;
	move_servo(FREE);
	walk_motor2_to(CALIB_AUX2 + 653, MOTOR2_SPEED);
	LASER = ON;
	i = POSITION_MIN;
	last_way = 1;
	real_position = 0;
	while((i >= POSITION_MIN) && (i <= POSITION_MAX)){
		walk_motor_to(i, MOTOR_SPEED);
		x_last_2 = x_last_1;
		x_last_1 = x_last_0;
		x_last_0 = x;
		x = read_adc(ADC_CHANNEL);
		if(x > VAL_THRES_MIN){
			aux = 1;
			if(aux != aux_last){
				printf("***************\r");
				aux_last = 1;
			}
			printf("ADC=%d,  i=%d\r", x, i);
		}
		else if(x < 1100){
			aux = 0;
			aux_last = 0;
		}	
		if((x_last_2 < VAL_THRES_MIN) && (x_last_1 > VAL_THRES_MAX) && (x_last_0 > VAL_THRES_MAX)){
			i = -5;
			detected = 1;
			printf("detectou\r");
		}	
		if((i == POSITION_MAX) && (detected == 0)){		//When de calibration failed one time - make a recalibration til 5 times (count)
			i = POSITION_MIN;
			real_position = 0;
			//ClrWdt();
			count++;
		}
		if(detected == 1){		//For verifiy if the calibration is correct
			count = 0;
			detected = 0;
			real_position = CALIB1_AUX;		//Save the real position
			i = CALIB1_AUX;
			while(i >= POSITION_MIN){
				walk_motor_to(i, MOTOR_SPEED);
				x = read_adc(ADC_CHANNEL);
				if(x > 1600){
					count++;
					printf("ADC=%d,  i=%d\r", x, i);
					if((count > 6) && (i > 400) && (i < 500)){
						detected = 1;
						i = -5;
						//printf("Calibracao correcta\r");
						walk_motor_to(0, MOTOR_SPEED);
					}
				}
				else{count = 0;}
				i--;
			}//End of while(i >= POSITION_MIN)
			if(detected == 0) count = 3;
		}
		if(count == 3){
			i = POSITION_MAX + 1;		//To exit the while
			printf("ERR\t%d\r", 1);
		}
	i++;
	}	
	LASER = OFF;
}*/


void calibrate_motor(){
	int x = 0;
	int i;
	int j = 0;
	int j_aux = 0;
	int detected = 0;
	int count = 0;
	move_servo(FREE);
	walk_motor2_to(angle_2_conversion(180), MOTOR2_SPEED);
	LASER = ON;
	i = POSITION_MIN;
	last_way = 1;
	real_position = 0;
	while(i <= POSITION_MAX){
		ClrWdt();
		walk_motor_to(i, MOTOR_SPEED);
		x = read_adc(ADC_CHANNEL);
		if(x > VAL_THRES_MIN){
			//printf("ADC=%d\r", x);
			count++;
			j++;
			j_aux = j;
			if(count > 12) detected = 1;
		}
		else{
			count = 0;
			j = 0;
		}	
		if((detected == 1) && (j == 0)){
			i = POSITION_MAX;
			real_position = angle_1_conversion(90) + (int) (j_aux/2. + 1.5) - CALIB1_AUX;
			walk_motor_to(0, MOTOR_SPEED);
		}	

		if((detected == 0) && (i == POSITION_MAX)){
			i = POSITION_MAX;		//To exit the while
			printf("ERR\t%d\r", 1);
		}
	i++;
	}	
	LASER = OFF;
}



void motor_compensation(unsigned int delay, int way){
	int i_aux = 0;
	//if((way == -last_way2) && (real_position2 <= POSITION2_MAX - FOLGA2) && (real_position2 >= POSITION2_MIN + FOLGA2)){
	if((way == -last_way2)){
		//delay_ms(1500);
		//printf("Compensou o motor 2.\r");
		MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //put the actual position on
		delay_100ys(delay);
		while(i_aux < FOLGA2){
    		motor2_actual_position += way;
    		MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //walks the following step
    		delay_100ys(delay);
			i_aux++;
			//printf("m_a_p = %d\r", motor2_actual_position%8);
   		}
		last_way2 = -last_way2;
		MOTOR2_LAT &= MOTOR2_BITS_LOW;
		//printf("Compensou o motor 2.\r");
	}	
}







