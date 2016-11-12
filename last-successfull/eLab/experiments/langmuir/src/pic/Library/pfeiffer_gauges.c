/**************************************************************************
This code handles the communication with the pfeiffer gauges
It sends telegrams for:
- reading the measured pressure by the gauge

TODO:
- degas
- switch on or off the filament (only for hot cathode gauges)

**************************************************************************/

#include <p30F4011.h>
#include "pfeiffer_gauges.h"
#include "hardware_uart.h"
#include <string.h>
#include <math.h>
#include "pfeiffer_crc.h"


unsigned char pressure_gauge_01_S[6];
int pressure_gauge_01_status = -1;



/*
[Adress][Action][ParameterName][DataLength][Data][CheckSum]
Example query telegram: 001 00 740 02=?106(cr)
Example response telegram: 001 10 740 06 100023 025(cr)
*/

int get_gauge_01_pressure_S ( unsigned char * s ) {
	if ( pressure_gauge_01_status == 0 ) memcpy ( s , pressure_gauge_01_S , 6 );
	else memcpy ( s , "000000" , 6 );
	return pressure_gauge_01_status;
}


float acquire_gauge_01_pressure () {
	unsigned char query[25];
	int j;
	static int error_count = 0;
	float retVal, pressure_gauge_01_F = 1;

	memcpy ( query , "0010074002=?106" , 15 );
	query[15] = 0x0D;
	hw_uart1_send_string ( query , 16 );

	j = hw_uart1_receive_string ( query , 25 );

#ifdef DEBUG
	memcpy ( query , "0011074006100017028" , 19 );
	query[19] = 13;
	j = 20;
#endif

	if ( j < 0 ) {
		gauge_error_count ( GAUGE_01 , INC );
		error_count++;
		if ( error_count < 5 ) retVal = -1;
		pressure_gauge_01_status = PFEIFFER_NO_RESPONSE_FROM_BUS;
	}
	else if ( j != 20 ) {
		gauge_error_count ( GAUGE_01 , INC );
		error_count++;
		if ( error_count < 5 ) retVal = -1;
		pressure_gauge_01_status = PFEIFFER_WRONG_SIZE_RESPONSE_FROM_BUS;
	}
	else if ( memcmp ( query , "0011074006" , 10 ) != 0 ) {
		gauge_error_count ( GAUGE_01 , INC );
		error_count++;
		if ( error_count < 5 ) retVal = -1;
		pressure_gauge_01_status = PFEIFFER_BAD_FORMAT_RESPONSE_FROM_BUS;
	}
	else if ( check_pfeiffer_crc ( query ) < 0 ) {
		gauge_error_count ( GAUGE_01 , INC );
		error_count++;
		if ( error_count < 5 ) retVal = -1;
		pressure_gauge_01_status = PFEIFFER_BAD_CRC_FROM_BUS;
	}
	else {
		error_count = 0;
		memcpy ( pressure_gauge_01_S , query + 10 , 6 );
		pressure_gauge_01_status = 0;
		pressure_gauge_01_F = convert_pressure_string_2_float ( pressure_gauge_01_S );

		retVal = pressure_gauge_01_F;
	}

	return retVal;
}

int gauge_error_count ( int gauge , int op ) {
	int retVal;
	static unsigned int error_count_01 = 0;

	switch ( op ) {

		case ( INC ):
			switch ( gauge ) {
				case ( GAUGE_01 ) : if ( error_count_01 != 65535 ) error_count_01++; retVal = 0; break;
				default: break;
			}
		break;

		case ( RESET ):
			switch ( gauge ) {
				case ( GAUGE_01 ) : error_count_01 = 0; retVal = 0; break;
				default: break;
			}
		break;

		case ( GET ):
			switch ( gauge ) {
				case ( GAUGE_01 ) : retVal = error_count_01; break;
				default: break;
			}
		break;

		default: break;
	}

	return retVal;
}

float convert_pressure_string_2_float ( unsigned char * buf ) {
	float mantissa, pressure;
	int exponent;


	mantissa  = ( buf[0] - 48 ) * 1000;
	mantissa += ( buf[1] - 48 ) * 100;
	mantissa += ( buf[2] - 48 ) * 10;
	mantissa += ( buf[3] - 48 );

	mantissa /= 1000;

	exponent  = ( buf[4] - 48 ) * 10;
	exponent += buf[5] - 48;
	exponent -= 20;

	pressure = mantissa * powf ( 10 , exponent );

	return pressure;
}

