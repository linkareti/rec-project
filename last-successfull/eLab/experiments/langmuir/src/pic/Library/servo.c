#include "elab.h"

void open_servo(){
  SERVO_TRIS=OUTPUT;
}

void move_servo(unsigned int pwm){
  	if(pwm < PWM_MIN) pwm = PWM_MIN;
  	if(pwm > PWM_MAX) pwm = PWM_MAX;
	OC1RS = pwm;
}
