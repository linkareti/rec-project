#include <p30f4011.h>
#include "eeprom.h"

/*
;codes for NVMCON:
	;Erase 1 data word 0x4044
	;Erase 16 data words 0x4045
	;Erase Entire EEPROM 0x4046
	;Program 1 data word 0x4004
	;Program 16 data words 0x4005
*/



void eepromDo() {
//Disable interrupts while the KEY sequence is written
	asm("DISI #6");
//Write the KEY sequence
	asm("MOV #0x55, W2");
	asm("MOV W2, NVMKEY");
	asm("MOV #0xAA, W2");
	asm("MOV W2, NVMKEY");
//Start the write/erase cycle
	asm("BSET NVMCON,#15");
	asm("NOP");
	asm("NOP");
//Wait for eeprom to finish
	while(NVMCONbits.WR == 1);
}



void eeprom_read_block(int page, int offset, int *data) {
// READ EEPROM BLOCK
// (one block is 16 words or 32 bytes)
//W0=page
//W1=offset
//W2=buffer
//Setup pointer to EEPROM memory
	asm("MOV W0,TBLPAG");
//Read the EEPROM data
	asm("DO #15,table_read_end");
	asm("TBLRDL [W1],[W2]");
	asm("INC2 W1,W1");
	asm("table_read_end:INC2 W2,W2");
}


void eeprom_erase_block(int page, int offset) {
//ERASE EEPROM BLOCK
//(one block is 16 words or 32 bytes)
//W0=page
//W1=offset
//Set up a pointer to the EEPROM location to be erased.
	asm("MOV W0,NVMADRU");
	asm("MOV W1,NVMADR");
//Setup NVMCON to erase one block of data EEPROM
	asm("MOV #0x4045,W0");
	asm("MOV W0,NVMCON");
//Wait for eeprom
	eepromDo();
}


void eeprom_write_block(int page, int offset, int *data) {
//WRITE EEPROM BLOCK
//(one block is 16 words or 32 bytes)
//W0=page
//W1=offset
//W2=pointer to data

//Setup a pointer to data EEPROM
	asm("MOV W0,TBLPAG");
//Write data value to holding latch
	asm("DO #15,table_write_end");
	asm("TBLWTL [W2],[W1]");
	asm("INC2 W1,W1");
	asm("table_write_end:INC2 W2,W2");
//NVMADR captures write address from the TBLWTL instruction.
//Setup NVMCON for programming one block to data EEPROM
	asm("MOV #0x4005,W0");
	asm("MOV W0,NVMCON");
//Wait for eeprom
	eepromDo();
}

void eeprom_erase_all() {
//Setup NVMCON to erase all of data EEPROM
	asm("MOV #0x4046,W2");
	asm("MOV W2,NVMCON");
//Wait for eeprom
	eepromDo();
}


void eeprom_erase_word(int page, int offset) {
//W0=page
//W1=offset
//Set up a pointer to the EEPROM location to be erased.
	asm("MOV W0,NVMADRU");
	asm("MOV W1,NVMADR");
//Setup NVMCON to erase one word of data EEPROM
	asm("MOV #0x4044,W0");
	asm("MOV W0,NVMCON");
//Wait for eeprom
	eepromDo();
}

int eeprom_read_word(int page, int offset) {
	int a;
//W0=page
//W1=offset
//W2=buffer
//Setup pointer to EEPROM memory
	asm("MOV W0,TBLPAG");
//Read the EEPROM data
	asm("TBLRDL [W1],W2");
	asm("MOV 0x001C, W1");
	asm("MOV W2,[W1]");
	return a;
}

void eeprom_write_word(int page, int offset, int val) {
//W0=page
//W1=offset
//W2=value to write

//Setup pointer to EEPROM memory
	asm("MOV W0,TBLPAG");
//Write data value to holding latch
	asm("TBLWTL W2,[W1]");
//NVMADR captures write address from the TBLWTL instruction.
//Setup NVMCON for programming one word to data EEPROM
	asm("MOV #0x4004,W0");
	asm("MOV W0,NVMCON");
//Wait for eeprom
	eepromDo();
}


void eepromSaveDouble(double val, int page, int offset) {
	static unsigned int ui;

	eeprom_erase_word(page, offset);
	eeprom_erase_word(page, offset + 2);
	
	ui = *((unsigned int*)(&val) + 0);
	eeprom_write_word(page, offset, ui);
	ui = *((unsigned int*)(&val) + 1);
	eeprom_write_word(page, offset + 2, ui);
}


double eepromGetDouble(int page, int offset) {
	static double myDouble;
	static unsigned int ui;
	
	ui = eeprom_read_word(page, offset);
	*((unsigned int*)(&myDouble) + 0) = ui;
	ui = eeprom_read_word(page, offset + 2);
	*((unsigned int*)(&myDouble) + 1) = ui;

	return myDouble;
}



void eepromSaveInt(int val, int page, int offset) {
	eeprom_erase_word(page, offset);
	eeprom_write_word(page, offset, val);
}


int eepromGetInt(int page, int offset) {
	static int myInt;
	myInt = eeprom_read_word(page, offset);
	return myInt;
}




void eepromSaveLong(long val, int page, int offset) {
	static int i;

	eeprom_erase_word(page, offset);
	eeprom_erase_word(page, offset + 2);

	i = *((int*)(&val) + 0);
	eeprom_write_word(page, offset, i);

	i = *((int*)(&val) + 1);
	eeprom_write_word(page, offset + 2, i);
}


long eepromGetLong(int page, int offset) {
	static int i;
	static long l;
	i = eeprom_read_word(page, offset);
	*((int*)(&l) + 0) = i;

	i = eeprom_read_word(page, offset + 2);
	*((int*)(&l) + 1) = i;

	return l;
}

