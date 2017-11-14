#include "elab.h"


void configure_adc(){	// ADC 12-bits
//****************************//
//ADCON1: A/D Control Register 1
//****************************//
	ADCON1bits.ADON=1;		//**A/D Operating Mode bit**//
							// 0-A/D converter is off
							// 1-A/D converter module is operating

	ADCON1bits.ADSIDL=0;	//**Stop in Idle Mode bit**//
							// 0-Continue module operation in Idle mode
							// 1-Discontinue module operation when device enters Idle mode

	ADCON1bits.FORM=0;		//**Sata Output Format bits**//
							// 0-Integer
							// 1-Signed integer
							// 2-Fractional
							// 3-Singed fractional

	ADCON1bits.SSRC=7;		//**Conversion Trigger Source Select bits**//
							// 0-Clearing SAMP bit ends sampling and starts conversion
							// 1-Active transition on INT0 pin ends sampling and starts conversion
							// 2-General purpose Timer3 compare ends sampling and starts conversion
							// 3-Motor Control PWM interval ends sampling and starts conversion
							// 4-Reserved
							// 5-Reserved
							// 6-Reserved
							// 7-Internal counter ends sampling and starts conversion (auto convert)

	ADCON1bits.ASAM=0;		//**A/D Sample Auto-Start bit**//
							// 0-Sampling begins when SAMP bit set
							// 1-Sampling begins immediately agter last conversion completes. SAMP bit is auto set.

	ADCON1bits.SAMP=0;		//**A/D Sample Enable bit**//
							// 0-A/D sample/hold amplifiers are holding
							// 1-At least one A/D sample/hold amplifier is sampling


//****************************//
//ADCON2: A/D Control Register 2
//****************************//
	ADCON2bits.VCFG=0;		//**Voltage Reference Configuration bits**//
							// 0- AVdd		AVss
							// 1- External_Vref+_pin	AVss
							// 2- AVdd		Esternal_Vref-_pin
							// 3- External_Vref+_pin	External_Vref-_pin	
							// (4-7)- AVdd		AVss

	_CSCNA=0;		//**Scan Input Selections for CH0+ S/H Input for MUX A Multiplexer Setting bit**/
					// 0-Do not scan inputs
					// 1-Scan inputs

	_BUFS=0;		//**Buffer Fill Status bit**//
					// 0-A/D is currently filling buffer 0x0-0x7, user should access data in 0x8-0xF
					// 1-A/D is currently filling buffer 0x8-0xF, user should access data in 0x0-0x7

	_SMPI=0;		//**Sample/Convert Sequences Per Interrupt Selection bits**//
					// 0-Interrupts at the completion of conversion for each sample/convert sequence
					// 1-Interrupts at the completion of conversion for each 2nd sample/convert sequence
					// ...
					// 14-Interrupts at the completion of conversion for each 15th sample/convert sequence
					// 15-Interrupts at the completion of conversion for each 16th sample/convert sequence

	_BUFM=0;		//**Buffer Mode Select bit**//
					// 0-Buffer configured as one 16-word buffer ADCBUF(15...0)
					// 0-Buffer configured as one 8-word buffer ADCBUF(15...8), ADCBUF(7...0)

	_ALTS=0;		//**Alternate Input Sample Mode Select bit**//
					// 0-Always use MUX A input multiplexer settings
					/* 1-Uses MUX A input multiplexer settings for first sample, then alternate between MUX B and 
					MUX A input multiplexer settings for all subsequent samples*/



//****************************//
//ADCON3: A/D Control Register 3
//****************************//
	_SAMC=1;		//**Auto Sample Time bits**//
					// (0-31) Tad

	_ADRC=0;		//**A/D Conversion Clock Source bit**//
					// 0-Clock derived from system clock
					// 1-A/D internal RC clock

	_ADCS=14;		//**A/D Conversion Clock Select bits**//
					// (1-64)*Tcy/2
					
					// Usou-se Tad=1000ns, logo obtem-se ADCS=14


//******************************//
//ADCHS: A/D Input Select Register
//******************************//
	_CH0NB=0;		//**Channel 0 Negative Input Select

	_CH0SB=0;

	_CH0NA=0;

	_CH0SA=0;



//*************************************//
//ADPCFG: A/D Port Configuration Register
//*************************************//

	// Este registo e configurado na funçao configure_adc_channel //



//*************************************//
//ADCSSL: A/D Input Scan Select Register
//*************************************//

	// Este registo nao precisa ser configurado, pois o CSCNA é 0 //	

}




void configure_adc_channel(int channel){
  TRISB |= (1 <<channel); 		// ADC_CHANNEL defined as input 
  ADPCFG &= ~(1 <<channel);		// ADC_CHANNEL defined as analog
  
}




int read_adc(int channel){
 int x;
 _CH0SA = channel;
 _SAMP=1;
 while(_SAMP);
 while(!_DONE);
 x=ADCBUF0;
 return x;
}
