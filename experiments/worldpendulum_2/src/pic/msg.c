#include <p30F4011.h>
#include <stdio.h>
#include <string.h>

#include "world_pendulum.h"
#include "msg.h"
#include "physical.h"
#include "shovel.h"
#include "laser.h"
#include "bipolar.h"
#include "delays.h"
#include "state_machine.h"
#include "pendulum_N_oscs.h"
#include "ball.h"
#include "adc.h"

#define SERIAL_BUFFER_SIZE 64

static char RXbuffer[SERIAL_BUFFER_SIZE];		//buffer used to store message from serial port
static volatile int messageReceivedFlag = NO;	//indicates arrival of message through serial port~

void processMessage(int mode) {
	static int flag;
	static char buf[32];
	static char message[SERIAL_BUFFER_SIZE];
	static unsigned int u1;
	static unsigned int u2;
	static unsigned long myULong;
	static float myFloat;
	static int myInt;

	IEC1bits.U2RXIE = 0;
	asm("nop");
	flag = messageReceivedFlag;
	messageReceivedFlag = NO;
	if(flag == YES) strcpy(message, RXbuffer);
	IEC1bits.U2RXIE = 1;

	if(flag == NO) return;

	if(mode == NORMAL_MODE) {
		//cur
		if(strcmp(message, "cur") == 0) {
			if(get_state() == STATE_SENDING_DATA) return;
			printf("CUR\t%u\t%u\r", get_deltaX(), get_Noscillations());
			reset_idmsg_timer();
		}
		
		//str
		else if(strcmp(message, "str") == 0) {
			printf("STR\r");
			set_state(STATE_STARTED, ECHO);
		}
		
		//stp
		else if(strcmp(message, "stp") == 0) {
			printf("STP\r");
			set_state(STATE_STOPPED, ECHO);
		}
		
		//rst
		else if(strcmp(message, "rst") == 0) {
			printf("RST\r");
			set_state(STATE_RESET, ECHO);
		}
		
		//ids
		else if(strcmp(message, "ids") == 0) {
			if(get_state() == STATE_SENDING_DATA) return;
			schedule_send_id();
		}
		
		//cfg \t ## \t ####
		else if(sscanf(message, "cfg\t%u\t%u", &u1, &u2) == 2) {
			sprintf(buf, "cfg\t%u\t%u", u1, u2);
			if(strcmp(buf, message) == 0 ) {
				sprintf(buf, "CFG\t%u\t%u", u1, u2);
				printf("%s\r", buf);
				if(get_state() == STATE_STARTED || get_state() == STATE_SENDING_DATA) return;
				if(u1 < DELTAX_MIN) u1 = DELTAX_MIN;
				if(u1 > getDeltaXMax_CM()) u1 = getDeltaXMax_CM();
				if(u2 < NOSC_MIN) u2 = NOSC_MIN;
				if(u2 > NOSC_MAX) u2 = NOSC_MAX;
				set_deltaX(u1);
				set_Noscillations(u2);
				set_state(STATE_CONFIGURED, ECHO);
			}
		}
	}
	
	if(strcmp(message, "stop ball") == 0) {
		stop_ball();
		printf("STOP BALL OK\r");
	}
	
	else if(sscanf(message, "prepare launch %u", &u1) == 1) {
		prepare_launch(u1);
		printf("PREPARE LAUNCH %d OK\r", u1);
	}
	
	else if(strcmp(message, "launch ball") == 0) {
		launch_ball();
		printf("LAUNCH BALL OK\r");
	}

	else if(strcmp(message, "test laser") == 0) {
		if(test_laser() == OK) printf("LASER IS OK\r");
		else printf("LASER IS NOT OK\r");
	}

	else if(sscanf(message, "go to origin %u", &u1) == 1) {
		if(u1 < 1) return;
		if(u1 > getStepperMaxFreq_HZ()) return;
		go_to_origin(u1);
		printf("GO TO ORIGIN %u OK\r", u1);
	}

	else if(sscanf(message, "move forward %u %u", &u1, &u2) == 2) {
		move(u1, DIRECTION_FORWARD, u2);
		printf("MOVE FORWARD %u %u OK\r", u1, u2);
	}

	else if(sscanf(message, "move backward %d %d", &u1, &u2) == 2) {
		move(u1, DIRECTION_BACKWARD, u2);
		printf("MOVE BACKWARD %u %u OK\r", u1, u2);
	}
	
	else if(sscanf(message, "move to photodiode %d", &u1) == 1) {
		if(u1 < 1) return;
		if(u1 > getStepperMaxFreq_HZ()) return;
		move_to_photodiode(u1);
		printf("MOVE TO PHOTODIODE %u OK\r", u1);
	}

	else if(sscanf(message, "set sphere diameter %f", &myFloat) == 1) {
		if(myFloat < 1.) myFloat = 1.;
		saveSphereDiameter_CM(myFloat);
		printf("SPHERE DIAMETER: %f cm OK\r", getSphereDiameter_CM());
	}

	else if(sscanf(message, "set delta X max %d", &myInt) == 1) {
		if(myInt < 5) myInt = 5;
		if(myInt > 20) myInt = 20;
		saveDeltaXMax_CM(myInt);
		printf("DELTA X MAX: %d cm OK\r", getDeltaXMax_CM());
	}
	
	else if(sscanf(message, "set pendulum length %f", &myFloat) == 1) {
		if(myFloat < 0.1) myFloat = 0.1;
		savePendulumLength_M(myFloat);
		printf("PENDULUM LENGTH: %f m OK\r", getPendulumLength_M());
	}
	
	else if(sscanf(message, "set distance from laser to start %f", &myFloat) == 1) {
		if(myFloat > MAX_SHOVEL_DISPLACEMENT_CM - 20) myFloat = MAX_SHOVEL_DISPLACEMENT_CM - 20;
		if(myFloat < 15) myFloat = 15;
		saveDistanceLaserToStart_CM(myFloat);
		printf("DISTANCE FROM LASER TO START: %f cm OK\r", getDistanceLaserToStart_CM());
	}
	
	else if(sscanf(message, "set stepper max freq %d", &myInt) == 1) {
		if(myInt < 1) myInt = 1;
		saveStepperMaxFreq_HZ(myInt);
		printf("STEPPER MAX FREQ: %d Hz OK\r", getStepperMaxFreq_HZ());
	}

	else if(strcmp(message, "release stepper") == 0) {
		releaseBipolar();
		printf("RELEASE STEPPER OK\r");
	}
	
	else if(strcmp(message, "hold stepper") == 0) {
		holdBipolar();
		printf("HOLD STEPPER OK\r");
	}
	
	else if(strcmp(message, "blue led on") == 0) {
		blue_led_on();
		printf("BLUE LED ON OK\r");
	}
	
	else if(strcmp(message, "blue led off") == 0) {
		blue_led_off();
		printf("BLUE LED OFF OK\r");
	}
	
	else if(strcmp(message, "laser on") == 0) {
		laser_on();
		printf("LASER ON OK\r");
	}
	
	else if(strcmp(message, "laser off") == 0) {
		laser_off();
		printf("LASER OFF OK\r");
	}

	else if(strcmp(message, "reboot") == 0) {
		saveGlobalNumberOfOscs();
		printf("REBOOTING...\r");
		delay_ms(200);
		asm("reset");
	}

	else if(sscanf(message, "set global oscillation counter %lu", &myULong) == 1) {
		setGlobalNumberOfOscs(myULong);
		printf("GLOBAL OSCILLATION COUNTER: %lu OK\r", getGlobalNumberOfOscs());
	}

	else if(strncmp(message, "set ID string ", strlen("set ID string ")) == 0) {
		if(strlen(message) > strlen("set ID string ")) {
			strncpy(buf, message + strlen("set ID string "), 32);
			buf[31] = 0;
			saveIDstring_CHAR(buf);
			printf("ID STRING: %s OK\r", getIDstring_CHAR(buf));
		}
	}
	
	else if(message[0] == '?' && strlen(message) == 1) sendHelp();
}




void sendHelp() {
	static char idstring[32];
	getIDstring_CHAR(idstring);

	printf("\r\r\r");
	printf("HELP:\r");
	printf("\r");
	printf("cur\r");
	printf("str\r");
	printf("stp\r");
	printf("rst\r");
	printf("ids\r");
	printf("cfg\tdeltaX[%u:%u]\tN[%u:%u]\r", DELTAX_MIN, getDeltaXMax_CM(), NOSC_MIN, NOSC_MAX);
	printf("\r");

	printf("stop ball\r");
	printf("prepare launch deltaX[%u:%u]\r", DELTAX_MIN, getDeltaXMax_CM());
	printf("launch ball\r");
	printf("test laser\r");
	printf("go to origin freq[1:%u]\r", getStepperMaxFreq_HZ());
	printf("move forward deltaX[1:%u] freq[1:%u]\r", MAX_SHOVEL_DISPLACEMENT_CM, getStepperMaxFreq_HZ());
	printf("move backward deltaX[1:%u] freq[1:%u]\r", MAX_SHOVEL_DISPLACEMENT_CM, getStepperMaxFreq_HZ());
	printf("move to photodiode freq[1:%u]\r", getStepperMaxFreq_HZ());
	printf("\r");

	printf("release stepper\r");
	printf("hold stepper\r");
	printf("blue led on\r");
	printf("blue led off\r");
	printf("laser on\r");
	printf("laser off\r");
	printf("reboot\r");
	printf("\r");

	printf("set delta X max %%d\r");
	printf("set sphere diameter %%f\r");
	printf("set pendulum length %%f\r");
	printf("set distance from laser to start %%f [%d %d]\r", 15, MAX_SHOVEL_DISPLACEMENT_CM - 20);
	printf("set stepper max freq %%d [100 500]\r");
	printf("set global oscillation counter %%lu\r");
	printf("set ID string %%s\r");
	printf("\r");

	printf("delta X max: %d cm\r",                  getDeltaXMax_CM());
	printf("sphere diameter: %f cm\r",              getSphereDiameter_CM());
	printf("pendulum length: %f m\r",               getPendulumLength_M());
	printf("distance from laser to start: %f cm\r", getDistanceLaserToStart_CM());
	printf("stepper max freq: %d Hz\r",             getStepperMaxFreq_HZ());
	printf("expected period: %f s\r",               getExpectedPeriod_S());
	printf("global oscillation counter: %lu\r",     getGlobalNumberOfOscs());
	printf("uptime: %ld s\r",                       get_uptime());
	printf("ID string: %s\r",                       idstring);
	printf("shovel is at origin: %s\r",             shovel_is_at_origin() == YES ? "yes" : "no");
	printf("laser is on: %s\r",                     laser_is_on() == YES ? "yes" : "no");
	printf("photodiode is on: %s\r",                photodiode_is_on() == YES ? "yes" : "no");
	printf("temperature: %.1fC\r",                  get_temperature());
	printf("\r");

	printf("?\r\r\r");
}


/* This is UART2 receive ISR */
void __attribute__((__interrupt__, __no_auto_psv__)) _U2RXInterrupt(void) {
	static char RXb[SERIAL_BUFFER_SIZE];
	static unsigned int counter;
	static int o;
	static char c;
	
   	IFS1bits.U2RXIF = 0;
	
	o = U2STA & 0x0002;		/*OERR bit*/
	
    while(U2STAbits.URXDA) {
	    c = U2RXREG;
	    RXb[counter++] = c;

	    if(c == '\r') {
		    memcpy(RXbuffer, RXb, counter);
		    RXbuffer[counter - 1] = 0;
		    counter = 0;
		    messageReceivedFlag = YES;
		}
		if(counter == SERIAL_BUFFER_SIZE) counter = 0;
	}

	if(o) U2STAbits.OERR = 0;
}
