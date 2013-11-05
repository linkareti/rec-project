#include "elab.h"


int timeout_count = 0;
int timeout_nwait = 0;
int wave_count = 0;
int points_count = 0;
int duty_inc = 0;
int t_sinal = 0;
int max_duty= 500;
int n_samp = 0;
int n_period = 0;
int c[6];//Used in the publication of data-points
int data[2];//Buffer for ADC values
int myflag = 0;
int gas_selector = 0;

void configuring(void)
{
 //Nothing to do here
}

void starting(void)
{

    //*Wave Generation*//
	//param_1 -> Max Dutty -> Amplitude
	//param_2 -> Sweep signal period = input2*PR3*34ns
	//*Data Aquisition*//
	//param_3 -> No of points per period
	//param_4 -> No of periods
	//*Preasure*//
	//param_5 -> Pressure Set Point
	//param_6 -> Pumpdown Pressure
	//*Gas Selection*//
	//param_7 -> Gas Selection (not implemented yet)

	if(param_1 > DUTTY_MAX) param_1 = DUTTY_MAX;
	if(param_1 < DUTTY_MIN) param_1 = DUTTY_MIN;

	max_duty = param_1; //Should be <500
	t_sinal = param_2;  //Tsinal real = input2*PR3*34ns
	n_samp = param_3;   //No of points per period
	n_period = param_4; //No of periods 
	timeout_nwait = t_sinal/n_samp; // Must be >0
	duty_inc = (2*max_duty)/n_samp;	// Must be >0

	if(timeout_nwait == 0) {timeout_nwait = 1;}
	if(duty_inc == 0) {duty_inc = 1;}

	gas_selector = param_7;

        //added the relay switching to turn the vac pum on and high voltage supply
       
        
        
}

void started(void)
{
	//*PID for the Preasure Routine*//
	
	int estab = 0;
    int count = 0;


    _TRISD1 = 0;  //make RD1 outup -> High Voltage control pin
    _LATD1 = 0;   //make sure high voltage is off

	float pressure=-1, setpoint=0, setpoint2=0, error=0, kp=0, ki=0, kd=0, value=0;
	float previous_error = 0, derivative =0, integral1 =0;
	setpoint = ((float) param_5)/1000.;
	setpoint2 = ((float) param_6)/1000.;
	
	T2CONbits.TON= 1;	//Enable Timer2
	PR2 = 589;			//Timer2 Period = PR2*34ns, used in PID

    pressure = acquire_gauge_01_pressure();
    _TRISE5 = 0;    //make sure the relay control bit is an output
    _LATE5 = 1;     //start pumping
    count = 0;

    printf("DAT\r");  //Start sending data to hardware server

    while((pressure > setpoint2) && (count < 1000))
    {
        pressure = acquire_gauge_01_pressure();
        printf("\r502\t110\t%f\n",pressure);
        delay_ms(100);
        ClrWdt();
        count++;
    }

    _LATE5 = 0;     //stop pumping and valve

    delay_ms(1500);
    _LATD1 = 1;     //turn high voltage on
    delay_ms(1500);

    //Gas Selection
    _TRISE4 = 0;  //make sure that E4 is an output
	_TRISF6 = 0;  //make sure that F6 is an output
	_TRISD0 = 0;  //make sure that D0 is an output
	
	switch(gas_selector) 
	{
		case 1:
		    _LATE4 = 1;     //open on-off valve (gas 1)
		    break;
		case 2 :
		    _LATF6 = 1;     //open on-off valve (gas 2)
		    break;
		case 3 :
		    _LATD0 = 1;     //open on-off valve (gas 3)
		    break;
	}

    OC3RS = 393;//PR2*2/3;     //inject gas

    count = 0;
    while((pressure < setpoint) && (count < 2000))
    {
        pressure = acquire_gauge_01_pressure();
        delay_ms(50);
        count++;
        printf("\r502\t110\t%f\n",pressure);
        //printf("\n\r pressao:%f count: %d\n",pressure,count);
        ClrWdt();
    }


    _LATE4 = 0;     //close on-off valve (gas 1)
    _LATF6 = 0;     //close on-off valve (gas 2)
    _LATD0 = 0;     //close on-off valve (gas 3)

    OC3RS= 0; 			//Closes Flow-Valve
	T2CONbits.TON= 0;	//Disable Timer2

	
	//*Main Routine*//

	PR3 = 589;			//Timer3 Period = PR3*34ns, used in WaveGeneration
	timeout_count = 0;
	points_count = 0;	//Clears acquired points counter
	OC4RS = 0;  		//sets duty-cycle to 0 and	
	T3CONbits.TON = 1;  //Enable Timer3
	ADCON1bits.ADON = 1;//Enable ADC
	IFS0bits.T3IF = 0;  //Clears Flag
	IFS0bits.ADIF = 0;  //Clears Flag

    myflag=0;

	while(points_count < n_samp * n_period){
		if(myflag == 1){
            myflag=0;
            if (data[0]>1000){data[0] = 999;}
            if (data[1]>1000){data[1] = 999;}
			c[0] = data[0]/100;
			c[1] = (data[0]-100*c[0])/10;
			c[2] = data[0]%10;

			c[3] = data[1]/100;
			c[4] = (data[1]-100*c[3])/10;
			c[5] = data[1]%10;
                        
                       
            for(count=0; count<=5; count++ ){
                if(c[count]>9){c[count]=9;}
            }

			putchar('\r');
			putchar('0'+ (char)c[0]);
			putchar('0'+ (char)c[1]);
			putchar('0'+ (char)c[2]);
			putchar('\t');
			putchar('0'+ (char)c[3]);
			putchar('0'+ (char)c[4]);
			putchar('0'+ (char)c[5]);
			putchar('\t');
			putchar('0');
			putchar('\n');
		}
	}

    count = 0;
    while(count < 10)
    {
        pressure = acquire_gauge_01_pressure();
        delay_ms(50);
        count++;
        printf("\r502\t110\t%f\n",pressure);
        ClrWdt();
    }

	printf("END\r"); //Stop sending data to hardware server
	
	T3CONbits.TON= 0;	//Disable Timer3
	OC3RS= 0; 			//Closes Valve
    delay_ms(2000);
    _LATE5 = 0;               //close pump
	_LATD1 = 0;			   	  //HV off
    delay_ms(10);		//Waits 10ms
	T2CONbits.TON= 0;	//Disable Timer2
	ADCON1bits.ADON = 0;//Disable ADC
	IFS0bits.T3IF = 0;  //Clears Flag
	T2CONbits.TON= 0;	//Disable Timer2
	
}

void stopping(void)
{
	T2CONbits.TON= 1;	//Enable Timer2	
	OC3RS= 0; 		//Closes Flow-Valve
    _LATE4 = 0;     //close on-off valve (gas 1)
    _LATF6 = 0;     //close on-off valve (gas 2)
    _LATD0 = 0;     //close on-off valve (gas 3)

    _LATE5 = 0;     //close pump
	_LATD1 = 0;		//HV off
}


/* This is T3 interrupt handler */
void __attribute__((__interrupt__,auto_psv)) _T3Interrupt(void)
{    

	IFS0bits.T3IF = 0;    //resets and reenables the T3 interrupt flag

	timeout_count++;

	if(timeout_count>=timeout_nwait){
		timeout_count=0; //Resets timeout_count
		ADCON1bits.SAMP = 1; //Activates sampling
		
		OC4RS += duty_inc;
		if((OC4RS > max_duty) || (OC4RS < 10)){ duty_inc = -duty_inc;}
		ClrWdt();
	}
 }


/* This is ADC interrupt handler */
void __attribute__((__interrupt__,auto_psv)) _ADCInterrupt(void)
{    
	IFS0bits.ADIF = 0; // clear interrupt

	data[0] = ADCBUF1;
	data[1] = ADCBUF0;
	myflag = 1;

	++points_count;
}
