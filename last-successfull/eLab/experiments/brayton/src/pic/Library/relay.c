#include "elab.h"

void open_relay(){
  RELAYCC=0;
  RELAYCARGA=0;
  RELAYCC_TRIS=OUTPUT;
  RELAYCARGA_TRIS=OUTPUT;
}

void relay_cc(short relay_state){
    LATBbits.LATB7 = relay_state;
}

void relay_carga(short relay_state){
    LATBbits.LATB8 = relay_state;
}
