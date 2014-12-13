#ifndef _SHOVEL_H_
#define _SHOVEL_H_

void stop_ball();
void go_to_origin(unsigned int speed);
void move(double cm, unsigned int direction, unsigned int speed);
void launch_ball();
void prepare_launch(unsigned int cm);
void stop_shovel();
int shovel_is_at_origin();
void move_to_photodiode(unsigned int speed);
int convert_cm_to_steps(float cm);

#endif
