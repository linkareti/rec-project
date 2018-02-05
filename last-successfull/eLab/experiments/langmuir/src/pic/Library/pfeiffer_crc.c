#include "pfeiffer_crc.h"

/*
check_pfeiffer_crc
Takes as argument a complete pfeiffer telegram (including termination character) and calculates the crc.
Example:
	telegram (buf): 001 00 740 02 =? 106 (0x0D)
	length (len): 16
Then checks if the supplied crc in the telegram is correct.
The len argument should be the complete length of the telegram, including crc and termination character (0x0D).
Returns 0 if message is correct and -1 otherwise.
*/
int check_pfeiffer_crc ( unsigned char * buf ) {
	unsigned int i, crc1, crc2, len;

	for ( len=0 ; buf[len] != 0x0D ; len++ );

	crc1 = 0;
	for ( i=0 ; i<len-3 ; i++ ) crc1 += buf[i];
	crc1 = crc1 & 0xFF;

	crc2  = ( buf [ len-3 ] - 48 ) * 100;
	crc2 += ( buf [ len-2 ] - 48 ) * 10;
	crc2 +=   buf [ len-1 ] - 48;

	if ( crc1 == crc2 ) return 0;
	else return -1;
}


/*
insert_pfeiffer_crc
Inserts the pfeiffer crc and the termination character (0x0D) at the end of the string buf of len length.
The len argument should be the length of the telegram excluding crc and termination character (0x0D).
Example:
	telegram (buf): 001 00 740 02 =?
	length (len): 12
After call to this function, buf will be: 001 00 740 02 =? 106 (0x0D)
The supplied buffer buf must have enough space: 3 bytes for crc and 1 bytes for termination character - 0x0D.
*/
void insert_pfeiffer_crc ( unsigned char *buf , int len ) {
	unsigned int i, centenas, dezenas, unidades;
	unsigned int crc;

	crc = 0;

	for ( i=0 ; i<len ; i++ ) crc += buf[i];

	crc = crc & 0xFF;

	unidades = crc % 10;
	dezenas = ( crc % 100 ) / 10;
	centenas = crc / 100;

	buf[len] = centenas + 48;
	buf[len+1] = dezenas + 48;
	buf[len+2] = unidades + 48;
	buf[len+3] = 0x0D;

	return;

}



