#include "elab.h"

//macros
//#define MOTOR2_BITS_LOW (~(0xF<<MOTOR2_FIRST_BIT))

unsigned int motor2_actual_position;
int real_position2;
int last_way2;

void open_motor2(){
     MOTOR2_LAT&=MOTOR2_BITS_LOW; //put MOTOR pin LOW (motor stoped)
     MOTOR2_TRIS&=MOTOR2_BITS_LOW; //define MOTOR pins as output
     CALIBRATE_TRIS=INPUT;       //define the calibrate pin as input
     motor2_actual_position=POSITION2_MAX;
     }

void walk_motor2(unsigned int way, unsigned int steps, unsigned int delay){ //the motor walks steps
  unsigned int i;
  for(i=steps; i<1 ; i-- ){                           //there is only end_curse for way==1
    MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[way][i%8]<<MOTOR2_FIRST_BIT);//walk the following step
    delay_100ys(delay);
    }
  MOTOR2_LAT&=MOTOR2_BITS_LOW; //put MOTOR pins LOW (motor stoped)
}

void walk_motor2_to(unsigned int to, unsigned int delay){
	int limit = 0;
	int way; 
	int i_aux = 0;
	if(to > POSITION2_MAX) to = POSITION2_MAX;
	if(to < POSITION2_MIN) to = POSITION2_MIN;
	(real_position2 < to) ? (way = 1) : (way = -1);
	if(real_position2 == to) way = 0;
	motor2_compensation(MOTOR_SPEED, way);
	if(last_way2 == -way){
		MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //put the actual position on
    	delay_100ys(delay);		
		while(i_aux < FOLGA2){
    		motor2_actual_position += way;
    		MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //walks the following step
    		delay_100ys(delay);
			i_aux++;
   		}
		//printf("Entrou Folga2\r");
	}
	if(real_position2 != to) last_way2 = way;
	if((real_position2 == POSITION2_MAX) || (real_position2 == POSITION2_MIN)){
		limit = 1;
		real_position2 += way;
	}
	MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //put the actual position on
    delay_100ys(delay);	
	while((real_position2 != to) && (real_position2 < POSITION2_MAX) && (real_position2 > POSITION2_MIN)){
		motor2_actual_position += way;    	
		MOTOR2_LAT=(MOTOR2_LAT&MOTOR2_BITS_LOW)+(step[UP_WAY2][motor2_actual_position%8]<<MOTOR2_FIRST_BIT); //walks the following step
		real_position2 += way;
    	delay_100ys(delay);
		ClrWdt();
   	}
	if(limit == 1) real_position2 -= way;
	MOTOR2_LAT &= MOTOR2_BITS_LOW; //put MOTOR pins LOW (motor stoped)
}

void calibrate_motor2(){
	int i = POSITION2_MIN;
	int find = 0;
	int calibrated = 0;
	real_position2 = POSITION2_MIN;
	while(i < CALIB_AUX){
		walk_motor2_to(i,MOTOR2_SPEED);
		if(CALIBRATE_PORT == 1){
			find = 1;
			i = CALIB_AUX;
		}
		i++;
	}
	ClrWdt();	
	if(find == 0){
		walk_motor2_to(POSITION2_MIN, MOTOR2_SPEED);
		real_position2 = POSITION2_MAX;
		i = POSITION2_MAX;
		while((CALIBRATE_PORT == 0) && (i >= POSITION2_MIN)){
			ClrWdt();
			walk_motor2_to(i, MOTOR2_SPEED);
			i--;
		}
		real_position2 = POSITION2_MIN + 4;
		walk_motor2_to(POSITION2_MIN, MOTOR2_SPEED);
		i = POSITION2_MIN;
		last_way2 = 1;
		while((CALIBRATE_PORT == 1) && (i <= POSITION2_MAX)){
			ClrWdt();
			walk_motor2_to(i, MOTOR2_SPEED);
			i++;
		}
		if((i <= POSITION2_MAX) && (i != POSITION2_MIN)) calibrated = 1;
		real_position2 = CALIB_AUX2;
	}
	if(find == 1){
		real_position2 = POSITION2_MIN;
		i = POSITION2_MIN;
		while((CALIBRATE_PORT == 1) && (i <= POSITION2_MAX)){
			ClrWdt();
			walk_motor2_to(i, MOTOR2_SPEED);
			i++;
		}
		if((i <= POSITION2_MAX) && (i != POSITION2_MIN)) calibrated = 1;
		real_position2 = CALIB_AUX2;
	}
	if(calibrated == 0) printf("ERR\t%d\r", 2);
	//printf("real_position2: %d\r", real_position2);
}

void motor2_compensation(unsigned int delay, int way){
	int i_aux = 0;
	if(way == -last_way){
		//delay_ms(1500);
		//printf("Compensou o motor 1.\r");
		MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //put the actual position on
    	delay_100ys(delay);
		while(i_aux < FOLGA){
    		motor_actual_position += way;
    		MOTOR_LAT=(MOTOR_LAT&MOTOR_BITS_LOW)+(step[UP_WAY][motor_actual_position%8]<<MOTOR_FIRST_BIT); //walks the following step
    		delay_100ys(delay);
			i_aux++;
			//printf("m_a_p = %d\r", motor_actual_position%8);
   		}
		last_way = -last_way;
		MOTOR_LAT &= MOTOR_BITS_LOW;
		//printf("Compensou o motor 1.\r");
	}
}








