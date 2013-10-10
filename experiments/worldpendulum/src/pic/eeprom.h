#ifndef _EEPROM_H_
#define _EEPROM_H_

extern void eeprom_erase_block(int page, int offset);
extern void eeprom_erase_word(int page, int offset);
extern void eeprom_erase_all();

extern void eeprom_write_block(int page, int offset, int *data);
extern void eeprom_write_word(int page, int offset, int data);

extern void eeprom_read_block(int page, int offset, int *data);
extern int eeprom_read_word(int page, int offset);

extern void eepromSaveDouble(double val, int page, int offset);
extern double eepromGetDouble(int page, int offset);

extern void eepromSaveInt(int val, int page, int offset);
extern int eepromGetInt(int page, int offset);

extern void eepromSaveLong(long val, int page, int offset);
extern long eepromGetLong(int page, int offset);

#endif
