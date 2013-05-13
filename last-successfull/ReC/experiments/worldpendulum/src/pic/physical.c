#include <p30F4011.h>
#include <math.h>
#include "eeprom.h"

static int SphereDiameter_FLAG, PendulumLength_FLAG, ExpectedPeriod_FLAG;
static double SphereDiameter_CM, PendulumLength_M, ExpectedPeriod_S;

//diameter of sphere in centimeters
void saveSphereDiameter_CM(double diameter) {
	unsigned int ui;
	
	eeprom_erase_word ( 0x007F , 0xFC00 );
	eeprom_erase_word ( 0x007F , 0xFC02 );
	
	ui = *((unsigned int*)(&diameter) + 0);
	eeprom_write_word(0x007F, 0xFC00, ui);
	ui = *((unsigned int*)(&diameter) + 1);
	eeprom_write_word(0x007F, 0xFC02, ui);

	SphereDiameter_CM = diameter;
	SphereDiameter_FLAG = 1;
}

//diameter of sphere in centimeters
double getSphereDiameter_CM() {
	float diameter;
	unsigned int ui;

	if(SphereDiameter_FLAG == 1) return SphereDiameter_CM;
	
	eeprom_read_word(0x007F, 0xFC00, &ui);
	*((unsigned int*)(&diameter) + 0) = ui;
	eeprom_read_word(0x007F, 0xFC02, &ui);
	*((unsigned int*)(&diameter) + 1) = ui;
	SphereDiameter_CM = diameter;
	SphereDiameter_FLAG = 1;
	return diameter;
}

//length of pendulum in meters
void savePendulumLength_M(double length) {
	unsigned int ui;
	
	eeprom_erase_word ( 0x007F , 0xFC04 );
	eeprom_erase_word ( 0x007F , 0xFC06 );
	
	ui = *((unsigned int*)(&length) + 0);
	eeprom_write_word(0x007F, 0xFC04, ui);
	ui = *((unsigned int*)(&length) + 1);
	eeprom_write_word(0x007F, 0xFC06, ui);

	PendulumLength_M = length;
	PendulumLength_FLAG = 1;
	ExpectedPeriod_S = 2 * 3.14 * sqrt(PendulumLength_M / 9.8);
	ExpectedPeriod_FLAG = 1;
}

//length of pendulum in meters
double getPendulumLength_M() {
	float length;
	unsigned int ui;

	if(PendulumLength_FLAG == 1) return PendulumLength_M;
	
	eeprom_read_word(0x007F, 0xFC04, &ui);
	*((unsigned int*)(&length) + 0) = ui;
	eeprom_read_word(0x007F, 0xFC06, &ui);
	*((unsigned int*)(&length) + 1) = ui;
	PendulumLength_M = length;
	PendulumLength_FLAG = 1;
	ExpectedPeriod_S = 2 * 3.14 * sqrt(PendulumLength_M / 9.8);
	ExpectedPeriod_FLAG = 1;
	return length;
}

double getExpectedPeriod_S() {
	if(ExpectedPeriod_FLAG == 1) return ExpectedPeriod_S;
	ExpectedPeriod_S = 2 * 3.14 * sqrt(getPendulumLength_M() / 9.8);
	ExpectedPeriod_FLAG = 1;
	return ExpectedPeriod_S;
}
