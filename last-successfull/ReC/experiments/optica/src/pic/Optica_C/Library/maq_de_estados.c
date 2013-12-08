#include "elab.h"

void maq_de_estados(void)
{
////UNKNOWN state
//	if(strcmp(state,"UNKNOWN") == 0)
//	{
//		while((strcmp(state,"CONFIGURING") != 0) &&
//			  (strcmp(state,"STOPPING") != 0) &&
//			  (strcmp(state,"RESETING") != 0))
//		{
//			sprintf(state,"UNKNOWN");
//			rec_generic_driver();
//		}
//	}

//STOPPING transition
	if(strcmp(state,"STOPPING") == 0)
	{
		stopping();
		sprintf(state,"STOPED");
	}

//STOPED state
	if(strcmp(state,"STOPED") == 0)
	{
		printf("STPOK\r");
		while((strcmp(state,"CONFIGURING") != 0) &&
			  (strcmp(state,"STARTING") != 0) &&
			  (strcmp(state,"RESETING") != 0))
		{
			sprintf(state,"STOPED");
			rec_generic_driver();
			ClrWdt();
		}
	}
	
//CONFIGURING transition
	if(strcmp(state,"CONFIGURING") == 0)
	{
		if(protocolo == 1) protocolo_1_configuring();
		if(protocolo == 2) protocolo_2_configuring();
		if(protocolo == 3) protocolo_3_configuring();
		if(protocolo == 4) protocolo_4_configuring();

		sprintf(state,"CONFIGURED");
	}

//CONFIGURED state
	if(strcmp(state,"CONFIGURED") == 0)
	{
		printf("CFGOK\r");
		while((strcmp(state,"STARTING") != 0) &&
			  (strcmp(state,"RESETING") != 0) &&
			  (strcmp(state,"STOPPING") != 0) &&
			  (strcmp(state,"RESETING") != 0) &&
			  (strcmp(state,"CONFIGURING") != 0))
		{
			sprintf(state,"CONFIGURED");
			rec_generic_driver();
			ClrWdt();
		}
	}


//STARTING transition
	if(strcmp(state,"STARTING") == 0)
	{
		if(protocolo == 1) protocolo_1_starting();
		if(protocolo == 2) protocolo_2_starting();
		if(protocolo == 3) protocolo_3_starting();
		if(protocolo == 4) protocolo_4_starting();

		sprintf(state,"STARTED");
	}


//STARTED state
	if(strcmp(state,"STARTED") == 0)
	{
		printf("STROK\r");
		if(protocolo == 1) protocolo_1_started();
		if(protocolo == 2) protocolo_2_started();
		if(protocolo == 3) protocolo_3_started();
		if(protocolo == 4) protocolo_4_started();
		if(protocolo == 5) protocolo_5_started();

		while((strcmp(state,"STOPPING") != 0) &&
			  (strcmp(state,"RESETING") != 0))
		{
			sprintf(state,"STARTED");
			rec_generic_driver();
			ClrWdt();
		}
	}


//RESETING transition
	if(strcmp(state,"RESETING") == 0)
	{
		sprintf(state,"RESETED");
	}


//RESETED state
	if(strcmp(state,"RESETED") == 0)
	{
		printf("RSTOK\r");
		while((strcmp(state,"CONFIGURING") != 0) &&
			  (strcmp(state,"STOPPING") != 0))
		{
			sprintf(state,"RESETED");
			rec_generic_driver();
			ClrWdt();
		}
	}
}
