#ifndef _PHYSICAL_H_
#define _PHYSICAL_H_

void saveDistanceLaserToStart_CM(double distance);
double getDistanceLaserToStart_CM();

void saveSphereDiameter_CM(double);
double getSphereDiameter_CM();

void savePendulumLength_M(double);
double getPendulumLength_M();

double getExpectedPeriod_S();

void saveStepperMaxFreq_HZ(int maxFreq);
int getStepperMaxFreq_HZ();

void saveIDstring_CHAR(char *str);
char* getIDstring_CHAR(char *str);

void saveDeltaXMax_CM(int deltaXmax);
int getDeltaXMax_CM();

#endif
