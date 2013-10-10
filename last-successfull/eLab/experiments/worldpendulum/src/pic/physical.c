#include <p30F4011.h>
#include <math.h>
#include "eeprom.h"
#include "world_pendulum.h"

static int SphereDiameter_FLAG;
static int PendulumLength_FLAG;
static int ExpectedPeriod_FLAG;
static int DistanceLaserToStart_FLAG;
static int StepperMaxFreq_FLAG;
static int IdString_FLAG;

static double SphereDiameter_CM;
static double PendulumLength_M;
static double ExpectedPeriod_S;
static double DistanceLaserToStart_CM;
static int StepperMaxFreq_HZ;

static char IdString[32];

static int isNaN (const float* f) {
	const int* rep = ((const int*) f) + 1;
	return ((*rep & 0x7F00) == 0x7F00);
}

//distance from laser to start in centimeters
void saveDistanceLaserToStart_CM(double distance) {
	static unsigned int ui;

	eeprom_erase_word(0x007F, 0xFC08);
	eeprom_erase_word(0x007F, 0xFC0A);
	
	ui = *((unsigned int*)(&distance) + 0);
	eeprom_write_word(0x007F, 0xFC08, ui);
	ui = *((unsigned int*)(&distance) + 1);
	eeprom_write_word(0x007F, 0xFC0A, ui);

	DistanceLaserToStart_CM = distance;
	DistanceLaserToStart_FLAG = 1;
}

//diameter of sphere in centimeters
double getDistanceLaserToStart_CM() {
	static double distance;
	static unsigned int ui;

	if(DistanceLaserToStart_FLAG == 1) return DistanceLaserToStart_CM;
	
	eeprom_read_word(0x007F, 0xFC08, &ui);
	*((unsigned int*)(&distance) + 0) = ui;
	eeprom_read_word(0x007F, 0xFC0A, &ui);
	*((unsigned int*)(&distance) + 1) = ui;
	if(distance < 10.0) { saveDistanceLaserToStart_CM(10.0); distance = 10.0; }
	else if(distance > 50.0) { saveDistanceLaserToStart_CM(50.0); distance = 50.0; }
	else if(isNaN((float*)&distance)) { saveDistanceLaserToStart_CM(20.0); distance = 20.0; }
	DistanceLaserToStart_CM = distance;
	DistanceLaserToStart_FLAG = 1;
	return distance;
}

//diameter of sphere in centimeters
void saveSphereDiameter_CM(double diameter) {
	static unsigned int ui;
	
	eeprom_erase_word(0x007F, 0xFC00);
	eeprom_erase_word(0x007F, 0xFC02);
	
	ui = *((unsigned int*)(&diameter) + 0);
	eeprom_write_word(0x007F, 0xFC00, ui);
	ui = *((unsigned int*)(&diameter) + 1);
	eeprom_write_word(0x007F, 0xFC02, ui);

	SphereDiameter_CM = diameter;
	SphereDiameter_FLAG = 1;
}

//diameter of sphere in centimeters
double getSphereDiameter_CM() {
	static double diameter;
	static unsigned int ui;

	if(SphereDiameter_FLAG == 1) return SphereDiameter_CM;
	
	eeprom_read_word(0x007F, 0xFC00, &ui);
	*((unsigned int*)(&diameter) + 0) = ui;
	eeprom_read_word(0x007F, 0xFC02, &ui);
	*((unsigned int*)(&diameter) + 1) = ui;
	if(diameter < 1.0) { saveSphereDiameter_CM(1.0); diameter = 1.0; }
	else if(diameter > 20.0) { saveSphereDiameter_CM(20.0); diameter = 20.0; }
	else if(isNaN((float*)&diameter)) { saveSphereDiameter_CM(10.0); diameter = 10.0; }
	SphereDiameter_CM = diameter;
	SphereDiameter_FLAG = 1;
	return diameter;
}

//length of pendulum in meters
void savePendulumLength_M(double length) {
	static unsigned int ui;
	
	eeprom_erase_word(0x007F, 0xFC04);
	eeprom_erase_word(0x007F, 0xFC06);
	
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
	static double length;
	static unsigned int ui;

	if(PendulumLength_FLAG == 1) return PendulumLength_M;
	
	eeprom_read_word(0x007F, 0xFC04, &ui);
	*((unsigned int*)(&length) + 0) = ui;
	eeprom_read_word(0x007F, 0xFC06, &ui);
	*((unsigned int*)(&length) + 1) = ui;
	if(length < 1.0) { savePendulumLength_M(1.0); length = 1.0; }
	else if(length > 10.0) { savePendulumLength_M(10.0); length = 10.0; }
	else if(isNaN((float*)&length)) { savePendulumLength_M(5.0); length = 5.0; }
	PendulumLength_M = length;
	PendulumLength_FLAG = 1;
	ExpectedPeriod_S = 2 * 3.14 * sqrt(PendulumLength_M / 9.8);
	ExpectedPeriod_FLAG = 1;
	return length;
}

//expected period in seconds
double getExpectedPeriod_S() {
	if(ExpectedPeriod_FLAG == 1) return ExpectedPeriod_S;
	ExpectedPeriod_S = 2 * 3.14 * sqrt(getPendulumLength_M() / 9.8);
	ExpectedPeriod_FLAG = 1;
	return ExpectedPeriod_S;
}



//maximum frequency for stepper in Hertz
void saveStepperMaxFreq_HZ(unsigned int maxFreq) {
	eeprom_erase_word(0x007F, 0xFC10);
	eeprom_write_word(0x007F, 0xFC10, (unsigned int)maxFreq);

	StepperMaxFreq_HZ = maxFreq;
	StepperMaxFreq_FLAG = 1;
}

//maximum frequency for stepper in Hertz
int getStepperMaxFreq_HZ() {
	static int maxFreq;

	if(StepperMaxFreq_FLAG == 1) return StepperMaxFreq_HZ;
	
	eeprom_read_word(0x007F, 0xFC10, (unsigned int*)&maxFreq);
	if(maxFreq < 100) { saveStepperMaxFreq_HZ(100); maxFreq = 100; }
	else if(maxFreq > 500) { saveStepperMaxFreq_HZ(500); maxFreq = 500; }
	StepperMaxFreq_HZ = maxFreq;
	StepperMaxFreq_FLAG = 1;
	return maxFreq;
}


//save ID string
void saveIDstring_CHAR(char *str) {
	static int i;
	static int ok;
	static char buf[32];

	eeprom_erase_block(0x007F, 0xFC20);

	for(i=0; i<32; i++) buf[i] = 0;

	ok = 1;
	for(i=0; i<32 && ok==1; i++) {
		buf[i] = str[i];
		if(str[i] == 0) ok = 0;
	}
	buf[31] = 0;

	eeprom_write_block(0x007F, 0xFC20, (unsigned int*)buf);

	for(i=0; i<32; i++) IdString[i] = buf[i];

	IdString_FLAG = 1;
}

//get ID string
int getIDstring_CHAR(char *str) {
	static char buf[32];
	static int i;
	static int ok;

	if(IdString_FLAG == 1) {
		ok = 1;
		for(i=0; i<32 && ok==1; i++) {
			str[i] = IdString[i];
			if(str[i] == 0) ok = 0;
		}
		return i - 1;
	}

	eeprom_read_block(0x007F, 0xFC20, (unsigned int*)buf);
	buf[31] = 0;

	ok = 1;
	for(i=0; i<32 && ok==1; i++) {
		IdString[i] = buf[i];
		str[i] = buf[i];
		if(buf[i] == 0) ok = 0;
	}

	IdString_FLAG = 1;
	return i - 1;
}
