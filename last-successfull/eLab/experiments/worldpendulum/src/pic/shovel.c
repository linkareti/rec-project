#include <p30F4011.h>
#include <math.h>
#include "world_pendulum.h"
#include "shovel.h"
#include "delays.h"
#include "ball.h"
#include "laser.h"
#include "bipolar.h"
#include "physical.h"
#include "etc.h"

static int volatile target;
static int volatile dir;
static int volatile shovel_is_moving = NO;
static int volatile ball_and_shovel_at_photodiode = NO;
static int distance;

void prepare_launch(unsigned int cm) {
	if(cm > DELTAX_MAX) cm = DELTAX_MAX;
	if(cm < DELTAX_MIN) cm = DELTAX_MIN;

	stop_ball();
	while (shovel_is_moving == YES) asm("clrwdt");

	stop_shovel();
	move (cm, DIRECTION_FORWARD, 40);
	while (shovel_is_moving == YES) asm("clrwdt");

	delay_ms(5000);
	if(test_laser() == NOT_OK) panic("ERR 1");
	distance = cm;
}

void launch_ball() {
	move (1, DIRECTION_BACKWARD, 200);
	while (shovel_is_moving == YES) asm("clrwdt");

	move (2 * distance + 2, DIRECTION_BACKWARD, getStepperMaxFreq_HZ());
	while (shovel_is_moving == YES) asm("clrwdt");

	go_to_origin(100);
}

void stop_ball() {
	static int ok;
	static int st;
	static double v0;
	static double xMax;

	ok = 0;

	while (shovel_is_moving == YES) asm("clrwdt");
	st = laser_is_on();
	laser_on();

	if(ball_and_shovel_at_photodiode == YES) {
		move (0.5, DIRECTION_FORWARD, 40);
		while (shovel_is_moving == YES) asm("clrwdt");
		delay_ms(50);
		if(photodiode_is_on() == YES) ok++;
	
		move (1.0, DIRECTION_BACKWARD, 40);
		while (shovel_is_moving == YES) asm("clrwdt");
		delay_ms(50);
		if(photodiode_is_off() == YES) ok++;
	
		move (1.0, DIRECTION_FORWARD, 40);
		while (shovel_is_moving == YES) asm("clrwdt");
		delay_ms(50);
		if(photodiode_is_on() == YES) ok++;
	
		if(ok == 3) {
			move_to_photodiode(40);
			delay_ms(1000);
			move_to_photodiode(40);
			if(st == NO) laser_off();
			return;
		}
	}

	go_to_origin(100);

	if(ball_is_stopped() != YES) delay_ms(1500 * getExpectedPeriod_S());

	if(get_oscillation_mode() == OSC_LONG) {
		while (get_ball_direction() != DIRECTION_BACKWARD && get_ball_direction() != DIRECTION_FORWARD);
		while (get_ball_direction() == DIRECTION_BACKWARD);
		while (get_ball_direction() == DIRECTION_FORWARD);
		v0 = get_ball_velocityCM();
		delay_ms(1000 - v0 * 10);
		xMax = v0 / 100 * sqrt((getPendulumLength_M() - 0.25 * v0 * v0 / 10000 / 9.8)/9.8);
		xMax *= 100 - 2;
		move (getDistanceLaserToStart_CM() + xMax, DIRECTION_FORWARD, getStepperMaxFreq_HZ());
		while (shovel_is_moving == YES) asm("clrwdt");
		move (1, DIRECTION_FORWARD, 100);
	}
	else {
		//the sphere diameter is rougly 8cm
		move(getDistanceLaserToStart_CM() - 10, DIRECTION_FORWARD, 150);
		while (shovel_is_moving == YES) asm("clrwdt");
		move(20, DIRECTION_FORWARD, 50);
	}

	while (shovel_is_moving == YES) asm("clrwdt");
	delay_ms(1000);
	move_to_photodiode(40);
	delay_ms(1000);
	move_to_photodiode(40);
	if(st == NO) laser_off();
}

void go_to_origin(unsigned int speed) {
	if(shovel_is_at_origin() == YES) return;
	if(speed > getStepperMaxFreq_HZ()) speed = getStepperMaxFreq_HZ();
	if(speed < 1) speed = 1;

	T1CONbits.TON = 0;
	TMR1 = 0;
	target = 1000u;
	dir = DIRECTION_BACKWARD;
	PR1 = FCY/256/speed - 1;
	shovel_is_moving = YES;
	T1CONbits.TON = 1;
	while (shovel_is_moving == YES) asm("clrwdt");

	delay_ms(1000);

	T1CONbits.TON = 0;
	TMR1 = 0;
	target = 10u;
	dir = DIRECTION_BACKWARD;
	speed = 20;
	PR1 = FCY/256/speed - 1;
	shovel_is_moving = YES;
	T1CONbits.TON = 1;
	while (shovel_is_moving == YES) asm("clrwdt");

	delay_ms(1000);

	T1CONbits.TON = 0;
	TMR1 = 0;
	target = 10u;
	dir = DIRECTION_BACKWARD;
	speed = 10;
	PR1 = FCY/256/speed - 1;
	shovel_is_moving = YES;
	T1CONbits.TON = 1;
	while (shovel_is_moving == YES) asm("clrwdt");

	if(shovel_is_at_origin() == NO) panic("ERR 2");
}

void move(double cm, unsigned int direction, unsigned int speed) {
	if(direction == DIRECTION_FORWARD || direction == DIRECTION_BACKWARD) dir = direction;
	else return;
	if(cm > MAX_SHOVEL_DISPLACEMENT_CM) cm = MAX_SHOVEL_DISPLACEMENT_CM;
	if(shovel_is_at_origin() == YES && dir == DIRECTION_BACKWARD) return;
	if(speed > getStepperMaxFreq_HZ()) speed = getStepperMaxFreq_HZ();
	if(speed < 1) speed = 1;
	T1CONbits.TON = 0;
	target = convert_cm_to_steps(cm);
	TMR1 = 0;
	PR1 = FCY/256/speed - 1;
	shovel_is_moving = YES;
	T1CONbits.TON = 1;
}

void move_to_photodiode(unsigned int speed) {
	static int st;

	if(shovel_is_at_origin() == YES) return;
	if(speed > getStepperMaxFreq_HZ()) speed = getStepperMaxFreq_HZ();
	if(speed < 1) speed = 1;

	st = laser_is_on();
	laser_on();

	move(10, DIRECTION_FORWARD, speed);
	while (shovel_is_moving == YES && photodiode_is_on() == NO) asm("clrwdt");
	stop_shovel();
	
	move(MAX_SHOVEL_DISPLACEMENT_CM, DIRECTION_BACKWARD, speed);
	while (shovel_is_moving == YES && photodiode_is_on() == YES) asm("clrwdt");
	stop_shovel();

	if(st == NO) laser_off();
	ball_and_shovel_at_photodiode = YES;
}

void stop_shovel() {
	target = 0;
	T1CONbits.TON = 0;
	releaseBipolar();
	shovel_is_moving = NO;
}

int shovel_is_at_origin() {
	if(MICROSWITCH == 1) { LED2_OFF; return NO; }
	else { LED2_ON; return YES; }
}

int convert_cm_to_steps(float cm) {
	//1 cm = 2400/187 steps = 12.8 steps
	if (cm < 0) return 0;
	return floor(cm * 12.8 + 0.5);
}

//stepping
void __attribute__((__interrupt__, __no_auto_psv__)) _T1Interrupt(void) {
	IFS0bits.T1IF = 0;
	if(target == 0) {
		T1CONbits.TON = 0;
		releaseBipolar();
		shovel_is_moving = NO;
		return;
	}

	if(shovel_is_at_origin() == YES && dir == DIRECTION_BACKWARD) {
		T1CONbits.TON = 0;
		releaseBipolar();
		shovel_is_moving = NO;
		return;
	}

	target--;
	stepBipolar(dir);
	ball_and_shovel_at_photodiode = NO;
}
