#include <p30F4011.h>
#include <stdio.h>

#include "world_pendulum.h"
#include "msg.h"
#include "laser.h"
#include "delays.h"

void panic(char *msg) {
	static int counter1;
	static int counter2;
	static long counter3;
	static int flag;
	
	counter1 = 0;
	counter2 = 0;
	counter3 = 0;
	flag = 0;

	laser_off();
	blue_led_off();

	while(1) {
		if(counter1 == 0) { TOGGLE_LED1; TOGGLE_LED2; }
		if(counter2 == 0) printf("%s\r", msg);
		counter1++;
		counter2++;
		counter3++;
		if(counter1 == 10) counter1 = 0;		//1 second
		if(counter2 == 50) counter2 = 0;		//5 seconds
		if(counter3 == 432000) asm("reset");	//12 hours
		ClrWdt();
		processMessage(PANIC_MODE);

		if(PUSH_BUTTON == 0 && flag == 0) {
			flag = 1;
			laser_toggle();
			if(laser_is_on() == YES) blue_led_on();
			else blue_led_off();
		}
		if(PUSH_BUTTON == 1) flag = 0;

		delay_ms(100);
	}
}
