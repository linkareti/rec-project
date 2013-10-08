#ifndef _PHYSICAL_H_
#define _PHYSICAL_H_

void saveDistanceLaserToStart_CM(double distance);
double getDistanceLaserToStart_CM();

void saveSphereDiameter_CM(double);
double getSphereDiameter_CM();

void savePendulumLength_M(double);
double getPendulumLength_M();

double getExpectedPeriod_S();

void saveStepperMaxFreq_HZ(unsigned int maxFreq);
int getStepperMaxFreq_HZ();

void saveIDstring_CHAR(char *str);
int getIDstring_CHAR(char *str);

#endif
