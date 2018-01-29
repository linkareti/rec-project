#ifndef PFEIFFER_GAUGES_H
#define PFEIFFER_GAUGES_H

#define GET 10
#define SET 11
#define RESET 12
#define INC 13

#define PFEIFFER_NO_RESPONSE_FROM_BUS -10
#define PFEIFFER_WRONG_SIZE_RESPONSE_FROM_BUS -11
#define PFEIFFER_BAD_FORMAT_RESPONSE_FROM_BUS -12
#define PFEIFFER_BAD_CRC_FROM_BUS -13

#define GAUGE_01 0

int get_gauge_01_pressure_S ( unsigned char * );

float acquire_gauge_01_pressure ();

int gauge_error_count ( int , int );

float convert_pressure_string_2_float ( unsigned char * );

#endif
