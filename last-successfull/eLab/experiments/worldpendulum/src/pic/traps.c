/******************************************************************************/
/* Files to Include                                                           */
/******************************************************************************/

#include <p30F4011.h>

static void u2_send_trap(char*);

/******************************************************************************/
/* Trap Function Prototypes                                                   */
/******************************************************************************/

/* <Other function prototypes for debugging trap code may be inserted here> */

/* Use if INTCON2 ALTIVT=1 */
void __attribute__((interrupt,no_auto_psv)) _OscillatorFail(void);
void __attribute__((interrupt,no_auto_psv)) _AddressError(void);
void __attribute__((interrupt,no_auto_psv)) _StackError(void);
void __attribute__((interrupt,no_auto_psv)) _MathError(void);

/* Use if INTCON2 ALTIVT=0 */
void __attribute__((interrupt,no_auto_psv)) _AltOscillatorFail(void);
void __attribute__((interrupt,no_auto_psv)) _AltAddressError(void);
void __attribute__((interrupt,no_auto_psv)) _AltStackError(void);
void __attribute__((interrupt,no_auto_psv)) _AltMathError(void);

/* Default interrupt handler */
void __attribute__((interrupt,no_auto_psv)) _DefaultInterrupt(void);

/******************************************************************************/
/* Trap Handling                                                              */
/*                                                                            */
/* These trap routines simply ensure that the device continuously loops       */
/* within each routine.  Users who actually experience one of these traps     */
/* can add code to handle the error.  Some basic examples for trap code,      */
/* including assembly routines that process trap sources, are available at    */
/* www.microchip.com/codeexamples                                             */
/******************************************************************************/

/* Primary (non-alternate) address error trap function declarations */
void __attribute__((interrupt,no_auto_psv)) _OscillatorFail(void) {
	INTCON1bits.OSCFAIL = 0;        /* Clear the trap flag */
	u2_send_trap("\r\n_OscillatorFail trap!\r\n");
}

void __attribute__((interrupt,no_auto_psv)) _AddressError(void) {
	INTCON1bits.ADDRERR = 0;        /* Clear the trap flag */
	u2_send_trap("\r\n_AddressError trap!\r\n");
}

void __attribute__((interrupt,no_auto_psv)) _StackError(void) {
	INTCON1bits.STKERR = 0;         /* Clear the trap flag */
	u2_send_trap("\r\n_StackError!\r\n");
}

void __attribute__((interrupt,no_auto_psv)) _MathError(void) {
	INTCON1bits.MATHERR = 0;         /* Clear the trap flag */
	u2_send_trap("\r\n_MathError trap!\r\n");
}

/* Alternate address error trap function declarations */
void __attribute__((interrupt,no_auto_psv)) _AltOscillatorFail(void) {
	INTCON1bits.OSCFAIL = 0;        /* Clear the trap flag */
	u2_send_trap("\r\n_AltOscillatorFail trap!\r\n");
}

void __attribute__((interrupt,no_auto_psv)) _AltAddressError(void) {
	INTCON1bits.ADDRERR = 0;        /* Clear the trap flag */
	u2_send_trap("\r\n_AltAddressError trap!\r\n");
}

void __attribute__((interrupt,no_auto_psv)) _AltStackError(void) {
	INTCON1bits.STKERR = 0;         /* Clear the trap flag */
	u2_send_trap("\r\n_AltStackError trap!\r\n");
}

void __attribute__((interrupt,no_auto_psv)) _AltMathError(void) {
	INTCON1bits.MATHERR = 0;        /* Clear the trap flag */
	u2_send_trap("\r\n_AltMathError trap!\r\n");
}

/******************************************************************************/
/* Default Interrupt Handler                                                  */
/*                                                                            */
/* This executes when an interrupt occurs for an interrupt source with an     */
/* improperly defined or undefined interrupt handling routine.                */
/******************************************************************************/
void __attribute__((interrupt,no_auto_psv)) _DefaultInterrupt(void) {
	u2_send_trap("\r\n_DefaultInterrupt!\r\n");
}

static void u2_send_trap(char * str) {
	char *p;
	p = &(str[0]);
	U2BRG = 1843200/115200/16-1; /*set baud rate*/
	while (*p != 0) {
		while(U2STAbits.UTXBF == 1);
		U2TXREG = *p++;
	}
	while(U2STAbits.TRMT == 0);	//while transmit buffer is not empty
}
