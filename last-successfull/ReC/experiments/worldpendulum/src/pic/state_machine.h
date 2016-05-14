#ifndef _STATE_MACHINE_H_
#define _STATE_MACHINE_H_

#define STATE_RESET	        110
#define STATE_STOPPED       111
#define STATE_CONFIGURED    112
#define STATE_STARTED       113

#define STATE_SENDING_DATA  114

#define ECHO				220
#define NO_ECHO				221

void state_machine();
void reset_idmsg_timer();

void set_state(int st, int mode);
int get_state();

unsigned long get_uptime();
void schedule_send_id();

void startExperiment();

void set_deltaX(unsigned int x);
unsigned int get_deltaX();

void set_Noscillations(unsigned int n);
unsigned int get_Noscillations();

#endif
