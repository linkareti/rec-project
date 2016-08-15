#include "elab.h"

void open_servo(){
  SERVO=0;
  SERVO_TRIS=OUTPUT;
}

void move_servo(unsigned int delay){ //delay in 10ys, 9V
  unsigned int i;
  for(i=0;i<40;i++){
     SERVO=1;
     delay_10ys(delay);
     SERVO=0;
     delay_ms(20);
  }
}
