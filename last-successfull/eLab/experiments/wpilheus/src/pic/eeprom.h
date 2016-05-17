#ifndef _EEPROM_H_
#define _EEPROM_H_

extern void eeprom_erase_block ( unsigned int page , unsigned int offset );
extern void eeprom_erase_word ( unsigned int page , unsigned int offset );
extern void eeprom_erase_all ();

extern void eeprom_write_block ( unsigned int page , unsigned int offset , unsigned int *data );
extern void eeprom_write_word ( unsigned int page , unsigned int offset , unsigned int data );

extern void eeprom_read_block ( unsigned int page , unsigned int offset , unsigned int *data );
extern void eeprom_read_word ( unsigned int page , unsigned int offset , unsigned int *data );

#endif
