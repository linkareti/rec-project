/* 
 * File:   d7seg.h
 * Author: samuel
 *
 * Created on 18 de Abril de 2013, 12:26
 */

#ifndef D7SEG_H
#define	D7SEG_H

//Definição dos pinos do dspic
//D7SEG_DIO -> DIO -> canal de dados
//D7SEG_SCK -> SCK -> clock
//D7SEG_RCK -> RCK -> "permissão" de escrita (a 0)
//cópia para o registo de armazenamento (1)
#define D7SEG_DIO LATCbits.LATC13
#define D7SEG_SCK LATCbits.LATC14
#define D7SEG_RCK LATFbits.LATF6

#define D7SEG_DIO_TRIS TRISCbits.TRISC13
#define D7SEG_SCK_TRIS TRISCbits.TRISC14
#define D7SEG_RCK_TRIS TRISFbits.TRISF6

extern void print_d7seg(unsigned long int pint, unsigned long int pdec);
extern void init_d7seg();

#endif	/* D7SEG_H */

