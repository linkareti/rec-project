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
static int dir;
static int volatile shovel_is_moving = NO;
static int volatile ballAndShovelStopped = YES;
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
	move (2 * distance + 1, DIRECTION_BACKWARD, 300);
	while (shovel_is_moving == YES) asm("clrwdt");
	go_to_origin(100);
	while (shovel_is_moving == YES) asm("clrwdt");
	if(shovel_is_at_origin() == NO) panic("ERR 2");
}

void stop_ball() {
	static int ok;

	ok = 0;

	while (shovel_is_moving == YES) asm("clrwdt");
	if(laser_is_on() == NO) laser_on();

	if(ballAndShovelStopped == YES) {
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
			go_to_origin(40);
			while (shovel_is_moving == YES && photodiode_is_on() == YES) asm("clrwdt");
			stop_shovel();
			delay_ms(1000);
			ballAndShovelStopped = YES;
			return;
		}
	}
	go_to_origin(100);
	while (shovel_is_at_origin() == NO && shovel_is_moving == YES) asm("clrwdt");
	if(shovel_is_at_origin() == NO) panic("ERR 2");

	if(ball_is_stopped() != YES) delay_ms(1500 * getExpectedPeriod_S());

	if(get_oscillation_mode() == OSC_LONG) {
		while(get_ball_direction() != DIRECTION_BACKWARD && get_ball_direction() != DIRECTION_FORWARD);
		while (get_ball_direction() == DIRECTION_BACKWARD);
		while (get_ball_direction() == DIRECTION_FORWARD);
		double v0 = get_ball_velocity();
		//delay_ms(1000 - v0 * 10);
		delay_ms(1000);
		double xMax = v0 / 100 * sqrt((getPendulumLength_M() - 0.25 * v0 * v0 / 10000 / 9.8)/9.8);
		xMax *= 100;
		move (getDistanceLaserToStart_CM() + xMax, DIRECTION_FORWARD, 300);
		while (shovel_is_moving == YES) asm("clrwdt");
		move (1, DIRECTION_FORWARD, 100);
	}
	else {
		move (15, DIRECTION_FORWARD, 150);
		while (shovel_is_moving == YES) asm("clrwdt");
		move (15, DIRECTION_FORWARD, 50);
	}

	while (shovel_is_moving == YES) asm("clrwdt");
	delay_ms(1000);
	if(test_laser() == NOT_OK) panic("ERR 1");
	go_to_origin(40);
	while (shovel_is_moving == YES && photodiode_is_on() == YES) asm("clrwdt");
	stop_shovel();
	ballAndShovelStopped = YES;
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
}

void move(double cm, unsigned int direction, unsigned int speed) {
	//1 cm = 2400/187 steps
	if(direction == DIRECTION_FORWARD || direction == DIRECTION_BACKWARD) dir = direction;
	else return;
	if(cm > MAX_SHOVEL_DISPLACEMENT_CM) cm = MAX_SHOVEL_DISPLACEMENT_CM;
	if(shovel_is_at_origin() == YES && dir == DIRECTION_BACKWARD) return;
	if(speed > getStepperMaxFreq_HZ()) speed = getStepperMaxFreq_HZ();
	if(speed < 1) speed = 1;
	T1CONbits.TON = 0;
	target = floor(cm * 2400 / 187 + 0.5);
	TMR1 = 0;
	PR1 = FCY/256/speed - 1;
	shovel_is_moving = YES;
	ballAndShovelStopped = NO;
	T1CONbits.TON = 1;
}

void stop_shovel() {
	target = 0;
	T1CONbits.TON = 0;
	releaseBipolar();
	shovel_is_moving = NO;
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
	ballAndShovelStopped = NO;
	stepBipolar(dir);
}
