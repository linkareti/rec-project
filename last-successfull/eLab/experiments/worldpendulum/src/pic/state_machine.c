#include <p30F4011.h>
#include <stdio.h>
#include <math.h>
#include "physical.h"
#include "world_pendulum.h"
#include "state_machine.h"
#include "ball.h"
#include "adc.h"
#include "laser.h"
#include "shovel.h"
#include "delays.h"

static int state;
static volatile unsigned long uptime;
static volatile int sendIdFlag;				//indicates that IDS message should be sent
static int deltaX;
static int Noscillations;
static volatile int msgTimerDivider;
static volatile int stateReturnCtr;
static int stateFlag = NOT_OK;
static int echoMode = NO_ECHO;

void state_machine() {
	int flag, s;
	static int oscillationCounter;

	if(sendIdFlag == 1 && state != STATE_SENDING_DATA) {
		reset_idmsg_timer();
		if(state == STATE_RESET) printf("IDS\tELAB_WORLD_PENDULUM_PLANETARIUM\tRESETED\r");
		else if(state == STATE_STOPPED) printf("IDS\tELAB_WORLD_PENDULUM_PLANETARIUM\tSTOPED\r");
		else if(state == STATE_CONFIGURED) printf("IDS\tELAB_WORLD_PENDULUM_PLANETARIUM\tCONFIGURED\r");
		else if(state == STATE_STARTED) printf("IDS\tELAB_WORLD_PENDULUM_PLANETARIUM\tSTARTED\r");
	}

	IEC1bits.T4IE = 0;
	flag = stateFlag;
	s = state;
	stateFlag = OK;
	IEC1bits.T4IE = 1;

	if(s == STATE_RESET) {
		if(flag == NOT_OK) {
			stateReturnCtr = 0;
			stop_acquisition();
			oscillationCounter = 0;
			stop_ball();
			laser_off();
			blue_led_off();
			set_deltaX(0);
			set_Noscillations(0);
			if(echoMode == ECHO) {
				printf("RSTOK\r");
				reset_idmsg_timer();
			}
		}
	}
	else if(s == STATE_STOPPED) {
		if(flag == NOT_OK) {
			stateReturnCtr = 0;
			stop_acquisition();
			oscillationCounter = 0;
			stop_ball();
			laser_off();
			blue_led_off();
			if(echoMode == ECHO) {
				printf("STPOK\r");
				reset_idmsg_timer();
			}
		}
	}
	else if(s == STATE_CONFIGURED) {
		if(flag == NOT_OK) {
			stateReturnCtr = 0;
			laser_on();
			blue_led_on();
			if(echoMode == ECHO) {
				printf("CFGOK\r");
				reset_idmsg_timer();
			}
		}
	}
	else if(s == STATE_STARTED) {
		if(flag == NOT_OK) {
			stateReturnCtr = 0;
			oscillationCounter = 0;
			launch_ball(deltaX);
			reset_acquisition();
			start_acquisition();
			if(echoMode == ECHO) {
				printf("STROK\r");
				reset_idmsg_timer();
			}
			set_state(STATE_SENDING_DATA, NO_ECHO);
		}
	}
	else if(s == STATE_SENDING_DATA) {
		if(is_data_point_available() == YES) {
			oscillationCounter++;
			stateReturnCtr = 0;
			if(oscillationCounter == 1) printf("DAT\r");
			printf("%u\t", oscillationCounter);
			printf("%.7f\t", get_oscillation_period());
			printf("%.7f\t", get_calculated_G());
			printf("%.7f\t", get_ball_velocity());
			printf("%.1f", get_temperature());
			printf("\r");
		}

		if(ball_is_stopped() == YES || oscillationCounter == Noscillations) {
			printf("END\r");
			reset_idmsg_timer();
			stateReturnCtr = 0;
			set_state(STATE_STOPPED, NO_ECHO);
		}
	}
}

unsigned long get_uptime() {
	unsigned long retVal;
	IEC1bits.T4IE = 0;
	asm("nop");
	retVal = uptime;
	IEC1bits.T4IE = 1;
	return retVal;
}

void schedule_send_id() {
	sendIdFlag = 1;
}

void set_state(int st, int mode) {
	echoMode = mode;
	if(echoMode == ECHO) reset_idmsg_timer();

	if(st == STATE_RESET) {
		state = STATE_RESET;
		stateFlag = NOT_OK;
	}
	else if(st == STATE_STOPPED) {
		state = STATE_STOPPED;
		stateFlag = NOT_OK;
	}
	else if(st == STATE_CONFIGURED) {
		if(state == STATE_SENDING_DATA) return;
		if(deltaX < DELTAX_MIN || deltaX > DELTAX_MAX || Noscillations < NOSC_MIN || Noscillations > NOSC_MAX) return;
		state = STATE_CONFIGURED;
		stateFlag = NOT_OK;
	}
	else if(st == STATE_STARTED) {
		if(state != STATE_CONFIGURED) return;
		if(deltaX < DELTAX_MIN || deltaX > DELTAX_MAX || Noscillations < NOSC_MIN || Noscillations > NOSC_MAX) return;
		state = STATE_STARTED;
		stateFlag = NOT_OK;
	}
	else if(st == STATE_SENDING_DATA) {
		if(state != STATE_STARTED) return;
		state = STATE_SENDING_DATA;
		stateFlag = NOT_OK;
	}
}

int get_state() {
	return state;
}

void set_deltaX(unsigned int x) {
//	if(x < DELTAX_MIN) return;
//	if(x > DELTAX_MAX) return;
	deltaX = x;
}

unsigned int get_deltaX() {
	return deltaX;
}

void set_Noscillations(unsigned int n) {
//	if(n < NOSC_MIN) return;
//	if(n > NOSC_MAX) return;
	Noscillations = n;
}

unsigned int get_Noscillations() {
	return Noscillations;
}

void reset_idmsg_timer() {
	IEC1bits.T4IE = 0;
	asm("nop");
	msgTimerDivider = 0;
	sendIdFlag = 0;
	IEC1bits.T4IE = 1;
}


//T4 for messaging (f = 2Hz)
void __attribute__((__interrupt__, __no_auto_psv__)) _T4Interrupt(void) {
	static int i;
	IFS1bits.T4IF = 0;
	i++;
	msgTimerDivider++;
	if(i % 2 == 0) stateReturnCtr++;
	if (stateReturnCtr == 600 && (state == STATE_CONFIGURED || state == STATE_STARTED)) {
		stateReturnCtr = 0;
		set_state(STATE_STOPPED, NO_ECHO);
	}
	if(i % 2 == 0) uptime++;
	if(msgTimerDivider % 4 == 0) if(state != STATE_SENDING_DATA) sendIdFlag = 1;
}
