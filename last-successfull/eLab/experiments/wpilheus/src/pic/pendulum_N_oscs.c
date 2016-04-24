#include <p30F4011.h>
#include "pendulum_N_oscs.h"
#include "eeprom.h"

static unsigned long N;

unsigned long getGlobalNumberOfOscs() {
	return N;
}

void saveGlobalNumberOfOscs() {
	unsigned int ui;

	eeprom_erase_word(0x007F, 0xFC0C);
	eeprom_erase_word(0x007F, 0xFC0E);
	
	ui = *((unsigned int*)(&N) + 0);
	eeprom_write_word(0x007F, 0xFC0C, ui);
	ui = *((unsigned int*)(&N) + 1);
	eeprom_write_word(0x007F, 0xFC0E, ui);
}

void incGlobalNumberOfOscs() {
	N++;
}

void retrieveGlobalNumberOfOscs() {
	unsigned int ui;
	
	eeprom_read_word(0x007F, 0xFC0C, &ui);
	*((unsigned int*)(&N) + 0) = ui;
	eeprom_read_word(0x007F, 0xFC0E, &ui);
	*((unsigned int*)(&N) + 1) = ui;
}

void resetGlobalNumberOfOscs() {
	N = 0;
	saveGlobalNumberOfOscs();
}
