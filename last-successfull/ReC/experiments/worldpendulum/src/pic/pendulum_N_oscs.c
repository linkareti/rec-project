#include <p30F4011.h>
#include "pendulum_N_oscs.h"
#include "eeprom.h"

static unsigned long N;

unsigned long getGlobalNumberOfOscs() {
	return N;
}

void saveGlobalNumberOfOscs() {
	eepromSaveLong((long)N, 0x007F, 0xFC0C);
}

void incGlobalNumberOfOscs() {
	N++;
}

void retrieveGlobalNumberOfOscs() {
	N = (unsigned long)eepromGetLong(0x007F, 0xFC0C);
}

void resetGlobalNumberOfOscs() {
	N = 0;
	saveGlobalNumberOfOscs();
}

void setGlobalNumberOfOscs(unsigned long val) {
	N = val;
	saveGlobalNumberOfOscs();
}
