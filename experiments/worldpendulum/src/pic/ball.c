#include <p30F4011.h>
#include <math.h>
#include "world_pendulum.h"
#include "delays.h"
#include "ball.h"
#include "laser.h"
#include "shovel.h"
#include "physical.h"
#include "state_machine.h"
#include "pendulum_N_oscs.h"

static volatile double valOscillationPeriod;
static volatile double valLinearVelocity;
static volatile double calculatedG;
static volatile int ballDirection = NOT_AVAIL;
static volatile int stopped = NOT_AVAIL;
static volatile int oscillationMode = NOT_AVAIL;
static volatile int movementCounter;
static volatile int photoDiode;
static volatile int atOrigin = NO;
static volatile int dataPointAvailable = NO;
static volatile int RESTART = YES;
static volatile int acquisitionOngoing = NO;


void __attribute__((__interrupt__, __no_auto_psv__)) _CNInterrupt(void) {
	static unsigned int u1, u2, u3, u4;
	static int LASTphotoDiode;
	static unsigned long myLong;
	static double valPeriodPART1;
	static double valPeriodPART2;
	static double myDouble;
	static double TTT;
	static int counter;

	IFS0bits.CNIF = 0;

	//Should be like this:
	//light -> photoDiode = 1
	//dark -> photoDiode = 0
	if(PHOTODIODE == 0) { photoDiode = 0; LED1_OFF; }
	else                { photoDiode = 1; LED1_ON; }

	if(MICROSWITCH == 1) { atOrigin = NO; LED2_OFF; }
	else                 { atOrigin = YES; LED2_ON; }

	if(photoDiode == LASTphotoDiode) return;

	movementCounter = 50;
	stopped = NO;
	LASTphotoDiode = photoDiode;

	if(RESTART == YES && photoDiode == 0) {
		RESTART = NO;
		TMR3HLD = 0;
		TMR2 = 0;
		counter = 0;
		return;
	}

	if(RESTART == YES) return;

	if(photoDiode == 0) {
		u1 = TMR2;
		u2 = TMR3HLD;
		TMR3HLD = 0;
		TMR2 = 0;
		myLong = u2;
		myLong = myLong << 16;
		myLong |= u1;
		valPeriodPART2 = valPeriodPART1;
		valPeriodPART1 = (double)myLong / (double)FCY;

		if(valPeriodPART1 > 0 && valPeriodPART2 > 0) {
			TTT = 0.9 * getExpectedPeriod_S();
			if(valPeriodPART1 >= TTT) {
				oscillationMode = OSC_SHORT;
				valOscillationPeriod = valPeriodPART1;
				ballDirection = NOT_AVAIL;
				counter = 2;
			}
			else if(valPeriodPART2 >= TTT) {
				oscillationMode = OSC_SHORT;
				ballDirection = NOT_AVAIL;
				counter = 0;
			}
			else if(valPeriodPART2 < TTT) {
				oscillationMode = OSC_LONG;
				valOscillationPeriod = valPeriodPART1 + valPeriodPART2;
				counter++;
				if(valPeriodPART1 > valPeriodPART2) ballDirection = DIRECTION_BACKWARD;
				else ballDirection = DIRECTION_FORWARD;
			}
		}

		if(counter == 2) {
			incGlobalNumberOfOscs();
			counter = 0;
			if(acquisitionOngoing == YES) {
				dataPointAvailable = YES;
				//g = 4 * pi^2 * L / T^2
				calculatedG = 39.478417604 * getPendulumLength_M() / valOscillationPeriod / valOscillationPeriod;
			}
		}
	}

	//velocity measurement
	if(photoDiode == 1) {
		u3 = TMR2;
		u4 = TMR3HLD;
		myLong = u4;
		myLong = myLong << 16;
		myLong |= u3;
		myDouble = (double)myLong / (double)FCY;
		TTT = 0.9 * getExpectedPeriod_S();
		if(myDouble < TTT && myDouble > 0 && oscillationMode == OSC_LONG) valLinearVelocity = getSphereDiameter_CM() / myDouble;
		else valLinearVelocity = 0;
	}
	//end of velocity measurement
}

void Yaiks() {
	//Should be like this:
	//light - photoDiode = 1
	//dark - photoDiode = 0
	if(PHOTODIODE == 0) { photoDiode = 0; LED1_OFF; }
	else                { photoDiode = 1; LED1_ON; }

	if(MICROSWITCH == 1) { atOrigin = NO; LED2_OFF; }
	else                 { atOrigin = YES; LED2_ON; }
}

int is_data_point_available() {
	int retVal;
	IEC0bits.CNIE = 0;
	asm("nop");
	retVal = dataPointAvailable;
	dataPointAvailable = NO;
	IEC0bits.CNIE = 1;
	return retVal;
}

double get_oscillation_period() {
	double retVal;
	IEC0bits.CNIE = 0;
	asm("nop");
	retVal = valOscillationPeriod;
	IEC0bits.CNIE = 1;
	return retVal;
}

double get_ball_velocity() {
	double retVal;
	IEC0bits.CNIE = 0;
	asm("nop");
	retVal = valLinearVelocity;
	IEC0bits.CNIE = 1;
	return retVal;
}

double get_calculated_G() {
	double retVal;
	IEC0bits.CNIE = 0;
	asm("nop");
	retVal = calculatedG;
	IEC0bits.CNIE = 1;
	return retVal;
}

void start_acquisition() {
	acquisitionOngoing = YES;
}

void reset_acquisition() {
	IEC0bits.CNIE = 0;
	asm("nop");
	RESTART = YES;
	oscillationMode = NOT_AVAIL;
	dataPointAvailable = NO;
	acquisitionOngoing = NO;
	calculatedG = 0;
	IEC0bits.CNIE = 1;
}

void stop_acquisition() {
	IEC0bits.CNIE = 0;
	asm("nop");
	dataPointAvailable = NO;
	acquisitionOngoing = NO;
	calculatedG = 0;
	IEC0bits.CNIE = 1;
}

int get_ball_direction() {
	return ballDirection;
}

int ball_is_stopped() {
	return stopped;
}

int photodiode_is_on() {
	if(photoDiode == 1) return YES;
	else return NO;
}

int photodiode_is_off() {
	if(photoDiode == 0) return YES;
	else return NO;
}

int get_oscillation_mode() {
	return oscillationMode;
}

int shovel_is_at_origin() {
	return atOrigin;
}

void doAdvancedCalculations(double G) {
	static double calculatedG_Arr[50];
	static double averageG, sigmaG;
	static int Gptr;
	int cnt, i;

	calculatedG_Arr[Gptr++] = G;
	if(Gptr == 50) Gptr = 0;

	cnt = 0;
	averageG = 0;
	for (i=0; i<50; i++) if(calculatedG_Arr[i] > 0) { averageG += calculatedG_Arr[i]; cnt++; }
	if(cnt > 0) averageG = averageG / cnt;
	else averageG = 0;

	cnt = 0;
	sigmaG = 0;
	for (i=0; i<50; i++) if(calculatedG_Arr[i] > 0) { sigmaG += pow(calculatedG_Arr[i] - averageG, 2); cnt++; }
	if(cnt > 0) sigmaG /= cnt;
	else sigmaG = 0;
	sigmaG = sqrt(sigmaG);
}


//T5 f=5Hz
void __attribute__((__interrupt__, __no_auto_psv__)) _T5Interrupt(void) {
	static int laser_ON;

	IFS1bits.T5IF = 0;

	laser_ON = laser_is_on();

	IEC0bits.CNIE = 0;
	asm("nop");

	if(laser_ON == YES && movementCounter > 0) movementCounter--;

	IEC0bits.CNIE = 1;

	if(laser_ON == NO) {
		movementCounter = 50;
		stopped = NOT_AVAIL;
		ballDirection = NOT_AVAIL;
		oscillationMode = NOT_AVAIL;
	}

	if(movementCounter == 0) {
		stopped = YES;
		ballDirection = NOT_AVAIL;
		oscillationMode = NOT_AVAIL;
	}

}
