#ifndef HARDWARE_UART_H
#define HARDWARE_UART_H

#define HW_UART_NUMBER_RECEIVE_BUFFERS 1
#define HW_UART_RECEIVE_BUFFER_SIZE 32

#define HW_UART_NUMBER_SEND_BUFFERS 1
#define HW_UART_SEND_BUFFER_SIZE 32


int hw_uart1_send_string ( unsigned char * , int );
int hw_uart1_receive_string ( unsigned char * , int );
int hw_uart1_init();

int hw_uart2_send_string ( unsigned char * , int );
int hw_uart2_receive_string ( unsigned char * , int );
int hw_uart2_init();

int receive_message_from_codac ( unsigned char * , int , int * );

#endif
