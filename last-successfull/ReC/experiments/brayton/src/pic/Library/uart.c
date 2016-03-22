#include "elab.h"

Communication Serial;

void configure_uart2(void){

	Serial.receiveddata = Serial.rbuf;

//****************************//
//U2MODE: UART2 Mode Register //
//****************************//
	U2MODEbits.UARTEN=1; 	//**UART Enable bit**//
							// 1 = UART is enabled. UART pins are controlled by UART as defined by UEN<1:0> and UTXEN control bits.
							// 0 = UART is disabled. UART pins are controlled by corresponding PORT, LAT, and TRIS bits.

	U2MODEbits.USIDL=0;		//**Stop in Idle Mode bit**//
							// 1 = Discontinue operation when device enters Idle mode
							// 0 = Continue operation in Idle mode

//bit 12 Unimplemented: Read as ‘0’
//bit 11 Reserved: Write ‘0’ to this location

	//U2MODEbits.ALTIO=0; 		//**UART Alternate I/O Selection bit**//
							// 1 = UART communicates using UxATX and UxARX I/O pins
							// 0 = UART communicates using UxTX and UxRX I/O pins
							//Note: The alternate UART I/O pins are not available on all devices. See device data sheet for details.

//bit 9-8 Reserved: Write ‘0’ to these locations

	U2MODEbits.WAKE=1; 		//**Enable Wake-up on Start bit Detect During Sleep Mode bit**//
							// 1 = Wake-up enabled
							// 0 = Wake-up disabled

	U2MODEbits.LPBACK=0; 	//**UART Loopback Mode Select bit**//
							// 1 = Enable Loopback mode
							// 0 = Loopback mode is disabled

	U2MODEbits.ABAUD=0;		//**Auto Baud Enable bit**//
							// 1 = Input to Capture module from UxRX pin
							// 0 = Input to Capture module from ICx pin

//bit 4-3 Unimplemented: Read as ‘0’

	U2MODEbits.PDSEL=0;		//**Parity and Data Selection bits**//
							// 3 = 9-bit data, no parity
							// 2 = 8-bit data, odd parity
							// 1 = 8-bit data, even parity
							// 0 = 8-bit data, no parity

	U2MODEbits.STSEL=0;		//**Stop Selection bit**//
							// 1 = 2 Stop bits
							// 0 = 1 Stop bit



//*****************************************//
//U2STA: UART2 Status and Control Register //
//*****************************************//
	U2STAbits.UTXISEL=1; 	//**Transmission Interrupt Mode Selection bit**//
							// 1 = Interrupt when a character is transferred to the Transmit Shift register and as result, the transmit buffer becomes empty
							// 0 = Interrupt when a character is transferred to the Transmit Shift register (this implies that there is at least one character open in the transmit buffer)

//bit 14-12 Unimplemented: Read as ‘0’

	U2STAbits.UTXBRK=0;		//**Transmit Break bit**//
							// 1 = UxTX pin is driven low, regardless of transmitter state
							// 0 = UxTX pin operates normally

	U2STAbits.UTXEN=1; 		//**Transmit Enable bit**//
							// 1 = UART transmitter enabled, UxTX pin controlled by UART (if UARTEN = 1)
							// 0 = UART transmitter disabled, any pending transmission is aborted and buffer is reset. UxTX pin controlled by PORT.

//	U2STAbits.UTXBF 		//**Transmit Buffer Full Status bit (Read Only)*//
							// 1 = Transmit buffer is full
							// 0 = Transmit buffer is not full, at least one more data word can be written

//	U2STAbits.TRMT: 		//**Transmit Shift Register is Empty bit (Read Only)**//
							// 1 = Transmit shift register is empty and transmit buffer is empty (the last transmission has completed)
							// 0 = Transmit shift register is not empty, a transmission is in progress or queued in the transmit buffer

	U2STAbits.URXISEL=0;	//**Receive Interrupt Mode Selection bit**//
							// 3 = Interrupt flag bit is set when Receive Buffer is full (i.e., has 4 data characters)
							// 2 = Interrupt flag bit is set when Receive Buffer is 3/4 full (i.e., has 3 data characters)
							// 1-0 = Interrupt flag bit is set when a character is received

	U2STAbits.ADDEN=0; 		//**Address Character Detect (bit 8 of received data = 1)**//
							// 1 = Address Detect mode enabled. If 9-bit mode is not selected, this control bit has no effect.
							// 0 = Address Detect mode disabled

//	U2STAbits.RIDLE: 		//**Receiver Idle bit (Read Only)**//
							// 1 = Receiver is Idle
							// 0 = Data is being received

//	U2STAbits.PERR: 		//**Parity Error Status bit (Read Only)**//
							// 1 = Parity error has been detected for the current character
							// 0 = Parity error has not been detected

//	U2STAbits.FERR: 		//**Framing Error Status bit (Read Only)**//
							// 1 = Framing Error has been detected for the current character
							// 0 = Framing Error has not been detected

//	U2STAbits.OERR=0; 		//**Receive Buffer Overrun Error Status bit (Read/Clear Only)**//
							// 1 = Receive buffer has overflowed
							// 0 = Receive buffer has not overflowed

//	U2STAbits.URXDA: 		//**Receive Buffer Data Available bit (Read Only)**//
							// 1 = Receive buffer has data, at least one more character can be read
							// 0 = Receive buffer is empty


//*******************************//
//U2BRG: UART2 Baud Rate Register//
//*******************************//
	U2BRG = (int) ((double) ((FCY/16.)*1000./((double)BAUD_RATE)-1) + 0.5);


//**************************************//
//IFS1: Interrupt Flag Status Register 1//
//**************************************//
	IFS1bits.U2RXIF=0;


//*****************************************//
//IEC1: Interrupt Enable Control Register 1//
//*****************************************//
	IEC1bits.U2TXIE=0;
	IEC1bits.U2RXIE=1;


//*******************************************//
//IPC6: Interrupt Priority Control Register 6//
//*******************************************//
	IPC6bits.U2RXIP=3;


	__C30_UART = 2;
}


int command_received(void){
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

	*Serial.receiveddata = ReadUART2();
	Serial.receiveddata++;

	if(Serial.rbuf[3] == '\r'){
		if((Serial.rbuf[0] == 's') && 
		   (Serial.rbuf[1] == 't') && 
		   (Serial.rbuf[2] == 'p')) 
		{
			stop = 1;
			Serial.rbuf[3] = '\0';	//para remover o '\r' de modo a nao voltar a entrar no if...
			Serial.receiveddata = Serial.rbuf;	//para garantir que quando voltar a escrever comeca do inicio
		}
		if((Serial.rbuf[0] == 'r') && 
		   (Serial.rbuf[1] == 's') && 
		   (Serial.rbuf[2] == 't'))
		{
			printf("RST\r");
			delay_ms(500); //para o pic ter tempo de mandar o comando correctamente
			asm("RESET");
		}
	}
}
