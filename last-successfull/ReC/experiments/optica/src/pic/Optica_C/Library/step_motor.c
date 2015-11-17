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
	while(i <= (POSITION_MAX+100)){
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
			i = POSITION_MAX+100;
			real_position = angle_1_conversion(90) + (int) (j_aux/2. + 1.5) - CALIB1_AUX;
			walk_motor_to(0, MOTOR_SPEED);
		}	

		if((detected == 0) && (i == (POSITION_MAX+100))){
			i = POSITION_MAX+100;		//To exit the while
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







