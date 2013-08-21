#ifndef _BALL_H_
#define _BALL_H_

void Yaiks();

int get_ball_direction();
double get_ball_velocity();
int ball_is_stopped();
int photodiode_is_on();
int photodiode_is_off();
int get_oscillation_mode();
int shovel_is_at_origin();
void measurement();
int is_data_point_available();
void start_acquisition();
void reset_acquisition();
void stop_acquisition();
double get_oscillation_period();
double get_calculated_G();

#define OSC_SHORT 10
#define OSC_LONG 11

#endif
