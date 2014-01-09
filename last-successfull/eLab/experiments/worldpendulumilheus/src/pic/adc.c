#include <p30F4011.h>		//defines dspic register locations and structures definitions
#include "world_pendulum.h"

void __attribute__((__interrupt__, __no_auto_psv__)) _ADCInterrupt (void);

//temperature in Celsius
static volatile double temperatureCelsius;

//Initializes all the necessary stuff for acquiring with the adc
//Uses AN2 for temperature measurement from LM35
void adc_init () {
	ADCON1bits.ADSIDL = 1;		//Stop in Idle Mode bit
								//1 = Discontinue module operation when device enters Idle mode
								//0 = Continue module operation in Idle mode
								
    ADCON1bits.FORM = 0b00;		//FORM<1:0>: Data Output Format bits
                                //11 = Signed fractional (DOUT = sddd dddd dddd 0000)
                                //10 = Fractional (DOUT = dddd dddd dddd 0000)
                                //01 = Signed integer (DOUT = ssss sddd dddd dddd)
                                //00 = Integer (DOUT = 0000 dddd dddd dddd)

    ADCON1bits.SSRC = 0b111;	//SSRC<2:0>: Conversion Trigger Source Select bits
                                //111 = Internal counter ends sampling and starts conversion (auto convert)
                                //110 = Reserved
                                //101 = Reserved
                                //100 = Reserved
                                //011 = Motor Control PWM interval ends sampling and starts conversion
                                //010 = General purpose Timer3 compare ends sampling and starts conversion
                                //001 = Active transition on INT0 pin ends sampling and starts conversion
                                //000 = Clearing SAMP bit ends sampling and starts conversion
                                
	ADCON1bits.SIMSAM = 1;		//Simultaneous Sample Select bit (only applicable when CHPS = 01 or 1x)
								//1 = Samples CH0, CH1, CH2, CH3 simultaneously (when CHPS = 1x)
								//or
								//Samples CH0 and CH1 simultaneously (when CHPS = 01)
								//0 = Samples multiple channels individually in sequence
								
    ADCON1bits.ASAM = 1;	//A/D Sample Auto-Start bit
    						//1 = Sampling begins immediately after last conversion completes. SAMP bit is auto set.
							//0 = Sampling begins when SAMP bit set

    ADCON1bits.SAMP = 1;	//A/D Sample Enable bit
							//1 = At least one A/D sample/hold amplifier is sampling
							//0 = A/D sample/hold amplifiers are holding
							//When ASAM = 0, writing '1' to this bit will start sampling.
							//When SSRC = 000, writing '0' to this bit will end sampling and start conversion.

    ADCON2bits.VCFG = 0b100;	//Voltage Reference Configuration bits
                                //A/D VREFH						A/D VREFL
                                //000 AVDD						AVSS
                                //001 External VREF+ pin		AVSS
                                //010 AVDD						External VREF- pin
                                //011 External VREF+ pin		External VREF- pin
                                //1xx AVDD						AVSS

    ADCON2bits.CSCNA = 0;   //Scan Input Selections for CH0+ S/H Input for MUX A Input Multiplexer Setting bit
                            //1 = Scan inputs
                            //0 = Do not scan inputs
                            
    ADCON2bits.CHPS = 0b00;	//Selects Channels Utilized bits
							//1x = Converts CH0, CH1, CH2 and CH3
							//01 = Converts CH0 and CH1
							//00 = Converts CH0
							//When SIMSAM bit (ADCON1<3>) = 0 multiple channels sampled sequentially
							//When SIMSAM bit (ADCON1<3>) = 1 multiple channels sampled as in CHPS<1:0>

    ADCON2bits.SMPI = 0b1111;	//SMPI<3:0>: Sample/Convert Sequences Per Interrupt Selection bits
								//1111 = Interrupts at the completion of conversion for each 16th sample/convert sequence
								//1110 = Interrupts at the completion of conversion for each 15th sample/convert sequence
								//.....
								//0001 = Interrupts at the completion of conversion for each 2nd sample/convert sequence
								//0000 = Interrupts at the completion of conversion for each sample/convert sequence

    ADCON2bits.BUFM = 0;    //BUFM: Buffer Mode Select bit
                            //1 = Buffer configured as two 8-word buffers ADCBUF(15...8), ADCBUF(7...0)
                            //0 = Buffer configured as one 16-word buffer ADCBUF(15...0)

    ADCON2bits.ALTS = 0;    //ALTS: Alternate Input Sample Mode Select bit
                            //1 = Uses MUX A input multiplexer settings for first sample, then alternate between MUX B and MUX A input
                            //multiplexer settings for all subsequent samples
                            //0 = Always use MUX A input multiplexer settings

    ADCON3bits.SAMC = 0b11111;	//SAMC<4:0>: Auto Sample Time bits
                                //11111 = 31 TAD
                                //·····
                                //00001 = 1 TAD
                                //00000 = 0 TAD (only allowed if performing sequential conversions using more than one S/H amplifier)

    ADCON3bits.ADRC = 0;	//ADRC: A/D Conversion Clock Source bit
    						//1 = A/D internal RC clock
							//0 = Clock derived from system clock
							//When the ADRC bit is set, the ADCS<5:0> bits have no effect on the A/D operation.

    ADCON3bits.ADCS = 0b111111;     //ADCS<5:0>: A/D Conversion Clock Select bits
                                    //111111 = TCY/2 (ADCS<5:0> + 1) = 32 TCY
                                    //······
                                    //000001 = TCY/2 (ADCS<5:0> + 1) = TCY
                                    //000000 = TCY/2 (ADCS<5:0> + 1) = TCY/2

	ADCHSbits.CH123NB = 0b00;	//Channel 1, 2, 3 Negative Input Select for MUX B Multiplexer Setting bits
	ADCHSbits.CH123NA = 0b00;	//Channel 1, 2, 3 Negative Input Select for MUX A Multiplexer Setting bits
								//11 = CH1 negative input is AN9, CH2 negative input is AN10, CH3 negative input is AN11
								//10 = CH1 negative input is AN6, CH2 negative input is AN7, CH3 negative input is AN8
								//0x = CH1, CH2, CH3 negative input is VREF-

	ADCHSbits.CH123SB = 0b00;	//Channel 1, 2, 3 Positive Input Select for MUX B Multiplexer Setting bit
	ADCHSbits.CH123SA = 0b00;	//Channel 1, 2, 3 Positive Input Select for MUX A Multiplexer Setting bit
								//1 = CH1 positive input is AN3, CH2 positive input is AN4, CH3 positive input is AN5
								//0 = CH1 positive input is AN0, CH2 positive input is AN1, CH3 positive input is AN2

	ADCHSbits.CH0NB = 0;	//Channel 0 Negative Input Select for MUX B Multiplexer Setting bit
    ADCHSbits.CH0NA = 0;	//Channel 0 Negative Input Select for MUX A Multiplexer Setting bit
							//1 = Channel 0 negative input is AN1
							//0 = Channel 0 negative input is VREF-
							
	ADCHSbits.CH0SB = 0b0000;	//Channel 0 Positive Input Select for MUX B Multiplexer Setting bits
	ADCHSbits.CH0SA = 0b0100;	//Channel 0 Positive Input Select for MUX A Multiplexer Setting bit
								//1111 = Channel 0 positive input is AN15
								//1110 = Channel 0 positive input is AN14
								//1101 = Channel 0 positive input is AN13
								//·····
								//0001 = Channel 0 positive input is AN1
								//0000 = Channel 0 positive input is AN0

    ADPCFG &= 0b1111111111101111;    //PCFG<15:0>: Analog Input Pin Configuration Control bits
                                    //1 = Analog input pin in Digital mode, port read input enabled, A/D input multiplexer input connected to AVSS
                                    //0 = Analog input pin in Analog mode, port read input disabled, A/D samples pin voltage

    ADCSSL = 0b0000000000000000;	//CSSL<15:0>: A/D Input Pin Scan Selection bits
									//1 = Select ANx for input scan
									//0 = Skip ANx for input scan

	TRISB |= 0b0000000000010000;	//Set analog pins to inputs

    ADCON1bits.ADON = 1;	//enable ad converter
    IFS0bits.ADIF = 0;		//clear adc interrupt flag
    IEC0bits.ADIE = 1;		//enable adc end of sampling/conversion interrupts
    
    //ADCON1bits.DONE		//DONE is '0' if adc is converting
    						//DONE is '1' if adc ended the conversion
    						
	//ADCON1bits.BUFS 		//Buffer Fill Status bit
							//Only valid when BUFM = 1 (ADRES split into 2 x 8-word buffers).
							//1 = A/D is currently filling buffer 0x8-0xF, user should access data in 0x0-0x7
							//0 = A/D is currently filling buffer 0x0-0x7, user should access data in 0x8-0xF
}


//ISR for adc. This is called when the adc finishes a conversion
void  __attribute__((__interrupt__, __no_auto_psv__)) _ADCInterrupt (void) {
	static unsigned int val[32];
	static unsigned int i;
	static unsigned int j;
	static unsigned int meanVal;
	/*adc calibration
		60mV - 0
		64mV - 1
		106mV - 9
		152mV - 18
		258mV - 40
		418mV - 74
		734mV - 139
		1038mV - 201
		1411mV - 280
		1770mV - 354
		1954mV - 392
		2177mV - 437
		2399mV - 483
		2701mV - 546
		3052mV - 619
		3363mV - 683
		4010mV - 817
		4438mV - 905
		4748mV - 969
		4982mV - 1018
		5010mV - 1022
		5015mV - 1023
	*/
    IFS0bits.ADIF = 0;
    val[i++] = ADCBUF0;
    if(i < 32) return;
    i = 0;
    meanVal=0;
    for(j=0; j<32; j++) meanVal += val[j];
    meanVal /= 32;
	temperatureCelsius = ((double)meanVal * 4.955 / 1023.0 + 0.060) * 100.0;
}

double get_temperature() {
	double retVal;

    IEC0bits.ADIE = 0;
	asm("nop");
	retVal = temperatureCelsius;
    IEC0bits.ADIE = 1;
	return retVal;
}
