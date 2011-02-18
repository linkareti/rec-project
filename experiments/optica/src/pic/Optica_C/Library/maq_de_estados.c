#include "elab.h"

//int protocolo = 0, param_7;
//double param_1, param_2, param_3, param_4, param_5, param_6;

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
		}
	}
	
//CONFIGURING transition
	if(strcmp(state,"CONFIGURING") == 0)
	{
//		sscanf(parameters[0],"%d", &protocolo);		
//		sscanf(parameters[1],"%lf", &param_1);
//		sscanf(parameters[2],"%lf", &param_2);
//		sscanf(parameters[3],"%lf", &param_3);
//		sscanf(parameters[4],"%lf", &param_4);
//		sscanf(parameters[5],"%lf", &param_5);
//		sscanf(parameters[6],"%lf", &param_6);
//		sscanf(parameters[7],"%d", &param_7);

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
		}
	}
}
