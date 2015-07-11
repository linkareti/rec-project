/**************************************************************************
This code controls the 2 hardware uarts in the pic30f4013.
The function hw_uartX_init must be called before any communications
take place.
Received messages must end with 0x0D and must not be longer than 30 bytes.

int hw_uartX_init () - initializes baudrate, interrupts, etc

int hw_uartX_send_string ( int nbytes , unsigned char * buf ) - sends nbytes
	from buf to the uartX

int hw_uartX_receive_string ( int nbytes , unsigned char * buf ) - receives
	up to nbytes from the uartX (message must be terminated by 0x0D).
	Returns the number of bytes actually received or -1 if it no message
	has arrived. If a message has arrived, no further bytes will be received
	until this function is called.
**************************************************************************/

#include <p30F4011.h>
#include <string.h>
#include "hardware_uart.h"
#include "elab.h"

void __attribute__((__interrupt__, __no_auto_psv__)) _U1RXInterrupt (void);

void __attribute__((__interrupt__, __no_auto_psv__)) _U1TXInterrupt (void);

typedef struct myRecvStruct {
	unsigned char buf[HW_UART_RECEIVE_BUFFER_SIZE];
	int recv_cnt;
	struct myRecvStruct * ptr_next;
} struct_recv_buffer;

struct_recv_buffer hw_uart1_recv_buffer[HW_UART_NUMBER_RECEIVE_BUFFERS];
struct_recv_buffer * hw_uart1_recv_write_buffer_ptr;
struct_recv_buffer * hw_uart1_recv_read_buffer_ptr;
int hw_uart1_recv_count;

typedef struct mySendStruct {
	unsigned char buf[HW_UART_SEND_BUFFER_SIZE];
	int length:8;
	int send_ptr:8;
	struct mySendStruct * ptr_next;
} struct_send_buffer;

struct_send_buffer hw_uart1_send_buffer[HW_UART_NUMBER_SEND_BUFFERS];
struct_send_buffer * hw_uart1_send_write_buffer_ptr;
struct_send_buffer * hw_uart1_send_read_buffer_ptr;
int hw_uart1_send_count;

int hw_uart1_initialized = 0;

int hw_uart1_init () {
	int i;
	if ( hw_uart1_initialized == 0 ) {
		hw_uart1_initialized = 1;

/***********************
* receive buffers
***********************/
		hw_uart1_recv_count = 0;
		for ( i=0 ; i < HW_UART_NUMBER_RECEIVE_BUFFERS-1 ; i++ ) {
			hw_uart1_recv_buffer[i].ptr_next = &(hw_uart1_recv_buffer[i+1]);
			hw_uart1_recv_buffer[i].recv_cnt = 0;
		}
		hw_uart1_recv_buffer[HW_UART_NUMBER_RECEIVE_BUFFERS-1].ptr_next = &(hw_uart1_recv_buffer[0]);
		hw_uart1_recv_buffer[HW_UART_NUMBER_RECEIVE_BUFFERS-1].recv_cnt = 0;
		hw_uart1_recv_read_buffer_ptr = &(hw_uart1_recv_buffer[0]);
		hw_uart1_recv_write_buffer_ptr = &(hw_uart1_recv_buffer[0]);


/***********************
* configuration bits
***********************/
		U1MODEbits.STSEL = 0; //one stop bit
		U1MODEbits.PDSEL = 0; //data length and parity: 8 bits, no parity
		U1BRG = 191; //set baud rate
		U1MODEbits.UARTEN = 1;	//enable uart 1
		U1STAbits.UTXEN = 1;	//enable uart 1 tx

		U1MODEbits.ALTIO = 1;	//use alternate RX and TX pins

		TRISFbits.TRISF6 = 0;	//data enable for rs485
		TRISBbits.TRISB8 = 0;	//read enable for rs485

		LATFbits.LATF6 = 0;	//data enable for rs485 (DE)
		LATBbits.LATB8 = 0;	//read enable for rs485 (RE is inverted logic)

		U1STAbits.URXISEL = 0; //uart1 receive interrupt at each received character
		U1STAbits.UTXISEL = 0; //uart1 send interrupt at each transmited character
	
		IFS0bits.U1RXIF = 0;
		IFS0bits.U1TXIF = 0;
	
		IEC0bits.U1TXIE = 0; //enable uart1 transmit interrupts
		IEC0bits.U1RXIE = 1; //enable uart1 receive interrupts
	}
	return 0;
}


//int hw_uart2_init () {
//	int i;
//	if ( hw_uart2_initialized == 0 ) {
//		hw_uart2_initialized = 1;
//
///***********************
//* receive buffers
//***********************/
//		hw_uart2_recv_count = 0;
//		for ( i=0 ; i < HW_UART_NUMBER_RECEIVE_BUFFERS-1 ; i++ ) {
//			hw_uart2_recv_buffer[i].ptr_next = &(hw_uart2_recv_buffer[i+1]);
//			hw_uart2_recv_buffer[i].recv_cnt = 0;
//		}
//		hw_uart2_recv_buffer[HW_UART_NUMBER_RECEIVE_BUFFERS-1].ptr_next = &(hw_uart2_recv_buffer[0]);
//		hw_uart2_recv_buffer[HW_UART_NUMBER_RECEIVE_BUFFERS-1].recv_cnt = 0;
//		hw_uart2_recv_read_buffer_ptr = &(hw_uart2_recv_buffer[0]);
//		hw_uart2_recv_write_buffer_ptr = &(hw_uart2_recv_buffer[0]);
//
///***********************
//* send buffers
//***********************/
//		hw_uart2_send_count = 0;
//		for ( i=0 ; i < HW_UART_NUMBER_SEND_BUFFERS-1 ; i++ ) hw_uart2_send_buffer[i].ptr_next = &(hw_uart2_send_buffer[i+1]);
//		hw_uart2_send_buffer[HW_UART_NUMBER_SEND_BUFFERS-1].ptr_next = &(hw_uart2_send_buffer[0]);
//		hw_uart2_send_write_buffer_ptr = &(hw_uart2_send_buffer[0]);
//		hw_uart2_send_read_buffer_ptr = &(hw_uart2_send_buffer[0]);
//
///***********************
//* configuration bits
//***********************/
//		U2MODEbits.STSEL = 0; //one stop bit
//		U2MODEbits.PDSEL = 0; //data length and parity: 8 bits, no parity
//		U2BRG = 15; //set baud rate
//		U2MODEbits.UARTEN = 1; //enable uart 2
//		U2STAbits.UTXEN = 1;	//enable uart 2 tx
//	
//		U2STAbits.URXISEL = 0; //uart2 receive interrupt at each received character
//		U2STAbits.UTXISEL = 0; //uart2 send interrupt at each transmited character
//	
//		IFS1bits.U2RXIF = 0;
//		IFS1bits.U2TXIF = 0;
//	
//		IEC1bits.U2TXIE = 1; //enable uart2 transmit interrupts
//		IEC1bits.U2RXIE = 1; //enable uart2 receive interrupts
//	}
//	return 0;
//}


int hw_uart1_send_string ( unsigned char * buf , int nbytes ) {
	int i=0;
	unsigned char c[64];

	memcpy ( c, buf, nbytes);

	if ( nbytes == 0 ) return -1;
	if ( hw_uart1_initialized == 0 ) return -1;

	//LATFbits.LATF6 = 1;	//data enable for rs485 (DE)
	//LATBbits.LATB8 = 1;	//read enable for rs485 (RE is inverted logic)

	do {
		U1TXREG = buf[i];
		i++;
		while ( U1STAbits.TRMT == 0 );
	} while ( i < nbytes );

	//LATFbits.LATF6 = 0;	//data enable for rs485
	//delay_ms ( 1 );
	//LATBbits.LATB8 = 0;	//read enable for rs485

	delay_ms ( 50 );

	return 0;
}


//int hw_uart2_send_string ( unsigned char * buf , int nbytes ) {
//	int cnt;
//
//	if ( nbytes > HW_UART_SEND_BUFFER_SIZE ) return -1;
//	if ( nbytes == 0 ) return -1;
//	if ( hw_uart2_initialized == 0 ) return -1;
//
//	IEC1bits.U2TXIE = 0;
//	cnt = hw_uart2_send_count;
//	IEC1bits.U2TXIE = 1;
//
//	if ( cnt == HW_UART_NUMBER_SEND_BUFFERS ) return -1;
//
//	IEC1bits.U2TXIE = 0;
//
//	memcpy ( hw_uart2_send_write_buffer_ptr->buf , buf , nbytes );
//	hw_uart2_send_write_buffer_ptr->length = nbytes;
//	hw_uart2_send_write_buffer_ptr->send_ptr = 0;
//
//	if ( hw_uart2_send_count == 0 ) {
//		U2TXREG = hw_uart2_send_write_buffer_ptr->buf[0];
//		hw_uart2_send_write_buffer_ptr->send_ptr = 1;
//	}
//
//	hw_uart2_send_write_buffer_ptr = hw_uart2_send_write_buffer_ptr->ptr_next;
//	hw_uart2_send_count++;
//
//	IEC1bits.U2TXIE = 1;
//
//	return 0;
//}

/*
hw_uart1_receive_string
*/
int hw_uart1_receive_string ( unsigned char * buf , int nbytes ) {
	int count;
	int sig;

	if ( nbytes > HW_UART_RECEIVE_BUFFER_SIZE ) return -1;
	if ( hw_uart1_initialized == 0 )  return -1;

	IEC0bits.U1RXIE = 0;
	sig = hw_uart1_recv_count;
	IEC0bits.U1RXIE = 1;

	if ( sig == 0 ) return -1;

	IEC0bits.U1RXIE = 0;
	count = nbytes > hw_uart1_recv_read_buffer_ptr->recv_cnt ? hw_uart1_recv_read_buffer_ptr->recv_cnt : nbytes;
	memcpy ( buf , hw_uart1_recv_read_buffer_ptr->buf , count );
	hw_uart1_recv_read_buffer_ptr->recv_cnt = 0;
	hw_uart1_recv_count--;
	hw_uart1_recv_read_buffer_ptr = hw_uart1_recv_read_buffer_ptr->ptr_next;
	IEC0bits.U1RXIE = 1;

	return count;
}

void _U1RXInterrupt (void) {
	unsigned char c;
	int f;

	IFS0bits.U1RXIF = 0;
	while ( U1STAbits.URXDA == 0 );		//wait until there is at least one character to read in the receive buffer
	f = U1STAbits.FERR;
	c = U1RXREG;

	if ( f ) {
		hw_uart1_recv_write_buffer_ptr->recv_cnt = 0;
	}
	else if ( hw_uart1_recv_count < HW_UART_NUMBER_RECEIVE_BUFFERS ) {
		hw_uart1_recv_write_buffer_ptr->buf[hw_uart1_recv_write_buffer_ptr->recv_cnt] = c;
		hw_uart1_recv_write_buffer_ptr->recv_cnt++;
		if ( c == 13 ) {
			hw_uart1_recv_count++;
			hw_uart1_recv_write_buffer_ptr = hw_uart1_recv_write_buffer_ptr->ptr_next;
		}
	}

	if ( hw_uart1_recv_write_buffer_ptr->recv_cnt >= HW_UART_RECEIVE_BUFFER_SIZE ) hw_uart1_recv_write_buffer_ptr->recv_cnt = 0;

	U1STAbits.OERR = 0;
}


//void _U2RXInterrupt (void) {
//	unsigned char c;
//	int f;
//
//	IFS1bits.U2RXIF = 0;
//	while ( U2STAbits.URXDA == 0 );		//wait until there is at least one character to read in the receive buffer
//	f = U2STAbits.FERR;
//	c = U2RXREG;
//
//	if ( f ) {
//		hw_uart2_recv_write_buffer_ptr->recv_cnt = 0;
//	}
//	else if ( hw_uart2_recv_count < HW_UART_NUMBER_RECEIVE_BUFFERS ) {
//		hw_uart2_recv_write_buffer_ptr->buf[hw_uart2_recv_write_buffer_ptr->recv_cnt] = c;
//		hw_uart2_recv_write_buffer_ptr->recv_cnt++;
//		if ( c == 13 ) {
//			hw_uart2_recv_count++;
//			hw_uart2_recv_write_buffer_ptr = hw_uart2_recv_write_buffer_ptr->ptr_next;
//		}
//	}
//
//	if ( hw_uart2_recv_write_buffer_ptr->recv_cnt >= HW_UART_RECEIVE_BUFFER_SIZE ) hw_uart2_recv_write_buffer_ptr->recv_cnt = 0;
//
//	U2STAbits.OERR = 0;
//}



void _U1TXInterrupt (void) {
	IFS0bits.U1TXIF = 0;
}


//void _U2TXInterrupt (void) {
//	IFS1bits.U2TXIF = 0;
//
//	if ( hw_uart2_send_read_buffer_ptr->send_ptr < hw_uart2_send_read_buffer_ptr->length ) {
//		U2TXREG = hw_uart2_send_read_buffer_ptr -> buf [ hw_uart2_send_read_buffer_ptr->send_ptr ];
//		hw_uart2_send_read_buffer_ptr->send_ptr++;
//	}
//	else {
//		hw_uart2_send_count--;
//		hw_uart2_send_read_buffer_ptr = hw_uart2_send_read_buffer_ptr->ptr_next;
//		if ( hw_uart2_send_count > 0 ) {
//			U2TXREG = hw_uart2_send_read_buffer_ptr -> buf [ hw_uart2_send_read_buffer_ptr -> send_ptr ];
//			hw_uart2_send_read_buffer_ptr -> send_ptr = 1;
//		}
//	}
//}

