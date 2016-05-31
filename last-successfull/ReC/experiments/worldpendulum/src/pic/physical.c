#include <p30F4011.h>
#include <math.h>
#include "eeprom.h"
#include "etc.h"
#include "world_pendulum.h"

static int SphereDiameter_FLAG;
static int PendulumLength_FLAG;
static int ExpectedPeriod_FLAG;
static int DistanceLaserToStart_FLAG;
static int StepperMaxFreq_FLAG;
static int IdString_FLAG;
static int DeltaXMax_FLAG;

static double SphereDiameter_CM;
static double PendulumLength_M;
static double ExpectedPeriod_S;
static double DistanceLaserToStart_CM;
static int    StepperMaxFreq_HZ;
static int    DeltaXMax_CM;

static char IdString[32];

//distance from laser to start in centimeters
void saveDistanceLaserToStart_CM(double distance) {
	if(distance < 10) distance = 10;
	if(distance > 50) distance = 50;
	eepromSaveDouble(distance, 0x007F, 0xFC08);
	DistanceLaserToStart_CM = distance;
	DistanceLaserToStart_FLAG = 1;
}

//diameter of sphere in centimeters
double getDistanceLaserToStart_CM() {
	static double distance;

	if(DistanceLaserToStart_FLAG == 1) return DistanceLaserToStart_CM;
	
	distance = eepromGetDouble(0x007F, 0xFC08);

	if(distance < 10.0) { saveDistanceLaserToStart_CM(10.0); distance = 10.0; }
	else if(distance > 50.0) { saveDistanceLaserToStart_CM(50.0); distance = 50.0; }
	else if(isNaN((float*)&distance)) { saveDistanceLaserToStart_CM(20.0); distance = 20.0; }

	DistanceLaserToStart_CM = distance;
	DistanceLaserToStart_FLAG = 1;
	return distance;
}

//diameter of sphere in centimeters
void saveSphereDiameter_CM(double diameter) {
	if(diameter < 1.) diameter = 1.;
	if(diameter > 20.) diameter = 20.;
	eepromSaveDouble(diameter, 0x007F, 0xFC00);
	SphereDiameter_CM = diameter;
	SphereDiameter_FLAG = 1;
}

//diameter of sphere in centimeters
double getSphereDiameter_CM() {
	static double diameter;

	if(SphereDiameter_FLAG == 1) return SphereDiameter_CM;
	
	diameter = eepromGetDouble(0x007F, 0xFC00);

	if(diameter < 1.0) { saveSphereDiameter_CM(1.0); diameter = 1.0; }
	else if(diameter > 20.0) { saveSphereDiameter_CM(20.0); diameter = 20.0; }
	else if(isNaN((float*)&diameter)) { saveSphereDiameter_CM(10.0); diameter = 10.0; }
	SphereDiameter_CM = diameter;
	SphereDiameter_FLAG = 1;
	return diameter;
}

//length of pendulum in meters
void savePendulumLength_M(double length) {
	if(length < 1.) length = 1.;
	if(length > 10.) length = 10.;
	eepromSaveDouble(length, 0x007F, 0xFC04);
	PendulumLength_M = length;
	PendulumLength_FLAG = 1;
	ExpectedPeriod_S = 2 * 3.14 * sqrt(PendulumLength_M / 9.8);
	ExpectedPeriod_FLAG = 1;
}

//length of pendulum in meters
double getPendulumLength_M() {
	static double length;

	if(PendulumLength_FLAG == 1) return PendulumLength_M;
	
	length = eepromGetDouble(0x007F, 0xFC04);

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
void saveStepperMaxFreq_HZ(int maxFreq) {
	if(maxFreq < 100) maxFreq = 100;
	if(maxFreq > 500) maxFreq = 500;
	eepromSaveInt(maxFreq, 0x007F, 0xFC10);
	StepperMaxFreq_HZ = maxFreq;
	StepperMaxFreq_FLAG = 1;
}

//maximum frequency for stepper in Hertz
int getStepperMaxFreq_HZ() {
	static int maxFreq;

	if(StepperMaxFreq_FLAG == 1) return StepperMaxFreq_HZ;

	maxFreq = eepromGetInt(0x007F, 0xFC10);

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

	eeprom_write_block(0x007F, 0xFC20, (int*)buf);

	for(i=0; i<32; i++) IdString[i] = buf[i];

	IdString_FLAG = 1;
}

//get ID string
char* getIDstring_CHAR(char *str) {
	static char buf[32];
	static int i;
	static int ok;

	if(IdString_FLAG == 1) {
		ok = 1;
		for(i=0; i<32 && ok==1; i++) {
			str[i] = IdString[i];
			if(str[i] == 0) ok = 0;
		}
		str[31] = 0;
		return str;
	}

	eeprom_read_block(0x007F, 0xFC20, (int*)buf);

	ok = 1;
	for(i=0; i<32 && ok==1; i++) {
		IdString[i] = buf[i];
		str[i] = buf[i];
		if(buf[i] == 0) ok = 0;
	}

	IdString_FLAG = 1;
	str[31] = 0;
	return str;
}



//maximum initial displacement for experiments in centimeters
void saveDeltaXMax_CM(int deltaXmax) {
	if(deltaXmax < 5) deltaXmax = 5;
	if(deltaXmax > 20) deltaXmax = 20;
	eepromSaveInt(deltaXmax, 0x007F, 0xFC40);
	DeltaXMax_CM = deltaXmax;
	DeltaXMax_FLAG = 1;
}

//maximum initial displacement for experiments in centimeters
int getDeltaXMax_CM() {
	static int delta;

	if(DeltaXMax_FLAG == 1) return DeltaXMax_CM;

	delta = eepromGetInt(0x007F, 0xFC40);

	if(delta < 5) { saveDeltaXMax_CM(5); delta = 5; }
	else if(delta > 20) { saveDeltaXMax_CM(20); delta = 20; }
	DeltaXMax_CM = delta;
	DeltaXMax_FLAG = 1;
	return DeltaXMax_CM;
}
