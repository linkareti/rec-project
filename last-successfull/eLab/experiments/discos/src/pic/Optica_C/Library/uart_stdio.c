#include "elab.h"


Communication Serial;

void config_uart2(void){
	CloseUART2();
	unsigned int UMODEvalue, U2STAvalue, config;
	
	UMODEvalue = UART_EN &
		UART_DIS_WAKE &
		UART_DIS_LOOPBACK &
		UART_DIS_ABAUD &
		UART_IDLE_CON &
		UART_NO_PAR_8BIT &
		UART_1STOPBIT;

	U2STAvalue = UART_INT_TX &
		UART_TX_PIN_NORMAL &
		UART_TX_ENABLE &
		UART_INT_RX_BUF_FUL &
		UART_RX_OVERRUN_CLEAR &
		UART_ADR_DETECT_DIS;

	config = UART_TX_INT_DIS &
		UART_RX_INT_EN &
		UART_INT_RX_BUF_FUL &
		UART_RX_INT_PR4;

	__C30_UART = 2;

	ConfigIntUART2(config);
	OpenUART2(UMODEvalue, U2STAvalue, (int) ((double) ((FCY/16.)*1000/BAUD_RATE-1) + 0.5));

	Serial.receiveddata = Serial.rbuf;
}


int command_received(void){
	while(DataRdyUART2()){
		*Serial.receiveddata = ReadUART2();
		Serial.receiveddata++;	
	}
	if(Serial.receiveddata != Serial.rbuf){
		if(*(Serial.receiveddata-1) == '\r'){
			*(Serial.receiveddata-1) = '\0';
			Serial.receiveddata = Serial.rbuf;
			return 1;		
		}
	}
	return 0;
}

void __attribute__((__interrupt__,auto_psv)) _U2RXInterrupt(void){
	IFS1bits.U2RXIF = 0;
	while(DataRdyUART2())
	{
		*Serial.receiveddata = ReadUART2();
		Serial.receiveddata++;
	}	
	if((Serial.rbuf[0] == 's') && 
	   (Serial.rbuf[1] == 't') && 
	   (Serial.rbuf[2] == 'p') && 
	   (Serial.rbuf[3] == '\r')) 
	{
		stop = 1;
	}
	if((Serial.rbuf[0] == 'r') && 
	   (Serial.rbuf[1] == 's') && 
	   (Serial.rbuf[2] == 't') && 
	   (Serial.rbuf[3] == '\r'))
	{
		reset = 1;
	}
}
