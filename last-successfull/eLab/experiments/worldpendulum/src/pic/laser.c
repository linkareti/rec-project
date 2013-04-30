#include <p30F4011.h>
#include "world_pendulum.h"
#include "delays.h"
#include "ball.h"
#include "laser.h"

int test_laser() {
	static int good;
	static int i;
	static int st;

	IEC0bits.CNIE = 0;
	asm("nop");

	st = laser_is_on();
	good = 0;

	for (i=0; i<20; i++) {
		laser_on();
		delay_ms(10);
		if(PHOTODIODE == 0) good++;

		laser_off();
		delay_ms(10);
		if(PHOTODIODE == 1) good++;
	}

	if(st == YES) laser_on();
	delay_ms(200);

	IFS0bits.CNIF = 0;
	IEC0bits.CNIE = 1;

	Yaiks();
	if(good == 40) return OK;
	else return NOT_OK;
}

int laser_is_on() {
	if(PORTEbits.RE5 == 1) return YES;
	else return NO;
}

int laser_is_off() {
	if(PORTEbits.RE5 == 1) return NO;
	else return YES;
}

void laser_on() { asm("bset LATE, #5"); }
void laser_off() { asm("bclr LATE, #5"); }
void laser_toggle() { asm("btg LATE, #5"); }

void blue_led_on() { asm("bset LATE, #4"); }
void blue_led_off() { asm("bclr LATE, #4"); }
void blue_led_toggle() { asm("btg LATE, #4"); }
