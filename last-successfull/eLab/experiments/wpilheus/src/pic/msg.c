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

#define SERIAL_BUFFER_SIZE 64

static char RXbuffer[SERIAL_BUFFER_SIZE];		//buffer used to store message from serial port
static volatile int messageReceivedFlag = NO;	//indicates arrival of message through serial port~

void processMessage(int mode) {
	static unsigned int u1;
	static unsigned int u2;
	static int flag;
	static float myFloat;
	static int myInt;
	static char s[16];
	static char message[SERIAL_BUFFER_SIZE];

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
			sprintf(s, "cfg\t%u\t%u", u1, u2);
			if(strcmp(s, message) == 0 ) {
				sprintf(s, "CFG\t%u\t%u", u1, u2);
				printf("%s\r", s);
				if(get_state() == STATE_STARTED || get_state() == STATE_SENDING_DATA) return;
				if(u1 < DELTAX_MIN || u1 > DELTAX_MAX || u2 < NOSC_MIN || u2 > NOSC_MAX) return;
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
		go_to_origin(u1);
		printf("GO TO ORIGIN OK\r");
	}

	else if(sscanf(message, "move forward %u %u", &u1, &u2) == 2) {
		move(u1, DIRECTION_FORWARD, u2);
		printf("MOVE FORWARD %u %u OK\r", u1, u2);
	}

	else if(sscanf(message, "move backward %d %d", &u1, &u2) == 2) {
		move(u1, DIRECTION_BACKWARD, u2);
		printf("MOVE BACKWARD %u %u OK\r", u1, u2);
	}
	
	else if(sscanf(message, "set sphere diameter %f", &myFloat) == 1) {
		if(myFloat < 1.) myFloat = 1.;
		saveSphereDiameter_CM(myFloat);
		printf("SPHERE DIAMETER: %f cm\r", (double)myFloat);
	}
	
	else if(sscanf(message, "set pendulum length %f", &myFloat) == 1) {
		if(myFloat < 0.1) myFloat = 0.1;
		savePendulumLength_M(myFloat);
		printf("PENDULUM LENGTH: %f m\r", (double)myFloat);
	}
	
	else if(sscanf(message, "set distance from laser to start %f", &myFloat) == 1) {
		if(myFloat > 50.0) myFloat = 50.0;
		saveDistanceLaserToStart_CM(myFloat);
		printf("SET DISTANCE FROM LASER TO START: %f cm\r", (double)myFloat);
	}
	
	else if(sscanf(message, "set stepper max freq %d", &myInt) == 1) {
		if(myInt < 1) myInt = 1;
		saveStepperMaxFreq_HZ(myInt);
		printf("SET STEPPER MAX FREQ: %d Hz\r", myInt);
	}
	
	else if(strcmp(message, "get sphere diameter") == 0) {
		printf("SPHERE DIAMETER: %f cm\r", getSphereDiameter_CM());
	}
	
	else if(strcmp(message, "get pendulum length") == 0) {
		printf("PENDULUM LENGTH: %f m\r", getPendulumLength_M());
	}
	
	else if(strcmp(message, "get distance from laser to start") == 0) {
		printf("DISTANCE FROM LASER TO START: %f cm\r", getDistanceLaserToStart_CM());
	}
	
	else if(strcmp(message, "get expected period") == 0) {
		printf("EXPECTED PERIOD: %f s\r", getExpectedPeriod_S());
	}
	
	else if(strcmp(message, "get stepper max freq") == 0) {
		printf("MAX FREQ FOR STEPPER: %d Hz\r", getStepperMaxFreq_HZ());
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
		printf("REBOOTING...\r");
		delay_ms(200);
		asm("reset");
	}

	else if(strcmp(message, "get uptime") == 0) {
		printf("UPTIME: %ld s\r", get_uptime());
	}

	else if(strcmp(message, "reset global oscillation counter") == 0) {
		resetGlobalNumberOfOscs();
		printf("RESET GLOBAL OSCILLATION COUNTER OK\r");
	}

	else if(strcmp(message, "get global oscillation counter") == 0) {
		printf("GET GLOBAL OSCILLATION COUNTER: %lu\r", getGlobalNumberOfOscs());
	}
	
	else if(message[0] == '?' && strlen(message) == 1) sendHelp();
}




void sendHelp() {
	printf("\r\r\r");
	printf("HELP:\r");
	printf("cur\r");
	printf("str\r");
	printf("stp\r");
	printf("rst\r");
	printf("ids\r");
	printf("cfg\tdeltaX[%u:%u]\tN[%u:%u]\r", DELTAX_MIN, DELTAX_MAX, NOSC_MIN, NOSC_MAX);
	printf("stop ball\r");
	printf("prepare launch deltaX[%u:%u]\r", DELTAX_MIN, DELTAX_MAX);
	printf("launch ball\r");
	printf("test laser\r");
	printf("go to origin freq[1:%u]\r", getStepperMaxFreq_HZ());
	printf("move forward deltaX[1:%u] freq[1:%u]\r", MAX_SHOVEL_DISPLACEMENT_CM, getStepperMaxFreq_HZ());
	printf("move backward deltaX[1:%u] freq[1:%u]\r", MAX_SHOVEL_DISPLACEMENT_CM, getStepperMaxFreq_HZ());
	printf("\r");

	printf("set sphere diameter %%f\r");
	printf("set pendulum length %%f\r");
	printf("set distance from laser to start %%f\r");
	printf("set stepper max freq %%d\r");
	printf("\r");

	printf("get sphere diameter\r");
	printf("get pendulum length\r");
	printf("get distance from laser to start\r");
	printf("get stepper max freq\r");
	printf("get expected period\r");
	printf("\r");

	printf("release stepper\r");
	printf("hold stepper\r");
	printf("blue led on\r");
	printf("blue led off\r");
	printf("laser on\r");
	printf("laser off\r");
	printf("reboot\r");
	printf("get uptime\r");

	printf("reset global oscillation counter\r");
	printf("get global oscillation counter\r");

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
