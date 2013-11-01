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

        _TRISF6 = 0;  //make RF6 outup -> High Voltage control pin
        _LATF6 = 0;   //make sure high voltage is off
 /*       printf("\n\ropen valve\n"); //open Argon valve since its the only one with gas
        _TRISE4 = 0;  //make sure that RB8 is an output
        _LATE4 = 1;     //open on-off valve
       
        //puff
        OC3RS = 500;     //inject gas for 0.5s
        delay_ms(500);
        OC3RS = 0;
*/

	float pressure=-1, setpoint=0, setpoint2=0, error=0, kp=0, ki=0, kd=0, value=0;
	float previous_error = 0, derivative =0, integral1 =0;
	setpoint = ((float) param_5)/1000.;
	setpoint2 = ((float) param_6)/1000.;

//	kp= 0.35;
        //kp = 0.35;
//	ki= 0.05;
        //ki= 0.08;
//	kd= 0.01;
        //kd= 0.02;

//	error =0, integral1 = 0, derivative =0, value =0;
	
	T2CONbits.TON= 1;	//Enable Timer2
	PR2 = 589;		//Timer2 Period = PR2*34ns, used in PID

//	printf("\n\rSetPoint = %e   kp= %f \n",setpoint, kp);

	//while(RXbuffer[str_pos-1]!= 0x0D){

        pressure = acquire_gauge_01_pressure();
        _TRISD0 = 0;    //make sure the relay control bit is an output
        _LATD0 = 1;     //start pumping

        count = 0;

        printf("DAT\r");
        //TODO: Put both counts in the while to 1000 and 2000 respectivly

        while((pressure > setpoint2) && (count < 1000))
        {
            pressure = acquire_gauge_01_pressure();
            printf("\r502\t110\t%f\n",pressure);
            delay_ms(100);
            ClrWdt();
            count++;
        }

        _LATD0 = 0;     //stop pumping and valve

        delay_ms(1500);
        _LATF6 = 1;     //turn high voltage on
        //puff
        delay_ms(1500);

        //printf("\n\r puffing\n");
        _TRISE4 = 0;  //make sure that RB8 is an output
        _LATE4 = 1;     //open on-off valve
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

        _LATE4 = 0;     //close on-off valve
//	error = setpoint - pressure;
        OC3RS= 0; 			//Closes Valve
	T2CONbits.TON= 0;	//Disable Timer2

/*	while(estab < 20 && count < 10000){
		//LATFbits.LATF6=0;
		pressure =acquire_gauge_01_pressure();

		error = setpoint - pressure;
		integral1 +=error * 0.148;
		derivative=(error-previous_error)/0.148;
		value = 25*(kp * error  + ki * integral1 + kd * derivative)/(2.4e-2);
		//value = kp * error;
                /*PWM table:
                 * PWM = 0 -> P = 17 uBar Vsol = 0,54V
                 * PWM = 100 -> P = 80 uBar Vsol = 1,33V
                 * PWM = 200 -> P = 88 uBar Vsol = 2,24V
                 * PWM = 300 -> P = 99 uBar Vsol = 2,93V
                 * PWM = 400 -> P = 150 uBar Vsol = 3,46V
                 * PWM = 500 -> P = 1500 uBar Vsol = 3,88V
                
                //value = 500;

                //glow from 17 uBar ->
                previous_error = error;
		if( value>470){value = 470;}
		else if( value<1){ value = 1;}
               // value = 470;
		OC3RS = (int) value;
		printf("\r pressao:%f PWM:%d value:%f setpoint:%f er:%f count:%d\n",pressure,OC3RS, value, setpoint, error,count);
		//LATFbits.LATF6=1;
		if(fabs(error/setpoint)<0.02){estab++;}
		ClrWdt();
                count++;
                //delay_ms(100);
	}
*/
//	OC3RS=50;
	
	//*Main Routine*//

	PR3 = 589;			//Timer3 Period = PR3*34ns, used in WaveGeneration

//	printf("\r pressao:%e \n",acquire_gauge_01_pressure ());//Debug Only

	//printf("DAT\r");

	timeout_count = 0;
	points_count = 0;	//Clears acquired points counter
	OC4RS = 0;  		//sets duty-cycle to 0 and	
	T3CONbits.TON = 1;  //Enable Timer3
	ADCON1bits.ADON = 1;//Enable ADC
	IFS0bits.T3IF = 0;  //Clears Flag
	IFS0bits.ADIF = 0;  //Clears Flag

        myflag=0;

  //while(RXbuffer[str_pos-1]!= 0x0D)
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

	printf("END\r");
	
	T3CONbits.TON= 0;	//Disable Timer3
	OC3RS= 0; 			//Closes Valve
        delay_ms(2000);
        _LATD0 = 0;               //close pump
		_LATF6 = 0;			   	  //HV off
        delay_ms(10);		//Waits 10ms
	T2CONbits.TON= 0;	//Disable Timer2
	ADCON1bits.ADON = 0;//Disable ADC
	IFS0bits.T3IF = 0;  //Clears Flag
	T2CONbits.TON= 0;	//Disable Timer2

//	printf("\r pressao:%e \n",acquire_gauge_01_pressure ());//Debug Only
	
}

void stopping(void)
{
	T2CONbits.TON= 1;	//Enable Timer2	
	OC3RS= 0; 		//Closes Valve
        _LATE4 = 0;               //close ON/OFF valve argon
        _LATD0 = 0;               //turn pump off 
        _LATF6 = 0;				  //HV off
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
		//OC4RS = 2*wave_count < n_samp ? OC4RS + duty_inc : OC4RS - duty_inc;
	}
 }

/* This is T4 interrupt handler */
//void __attribute__((__interrupt__,auto_psv)) _T4Interrupt(void)
//{    
//	IFS1bits.T4IF = 0;    //resets and reenables the T3 interrupt flag
//}

/* This is ADC interrupt handler */
void __attribute__((__interrupt__,auto_psv)) _ADCInterrupt(void)
{    
	IFS0bits.ADIF = 0; // clear interrupt
/*
    c[0]=(char) (128+(ADCBUF1>>3));
    c[1]=(char) (128+(ADCBUF1&7)+((ADCBUF0>>3)&112));
    c[2]=(char) (128+(127&ADCBUF0));

	putchar('\r');
	putchar(c[0]);
	putchar(' ');
	putchar(c[1]);
	putchar(' ');
	putchar(c[2]);
	putchar('\n');
*/
	data[0] = ADCBUF1;
	data[1] = ADCBUF0;
	myflag = 1;

	++points_count;
}
