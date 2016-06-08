#include "elab.h"		

char state[20];
char parameters[N_PARAMETERS_MAX][20];
int stop = 0;
int time_out = 0;

int protocolo = 0, param_1;

//Function to put in the while true in main	
void rec_generic_driver(void ){		

	//Every second this send the ids message (Timer1 -> 1sec)
	if(_T1IF){
		_T1IF = 0;
		printf("IDS\t"ID_DO_HARDWARE"\t%s\n\r", state);
		if(strcmp(state,"STOPED") != 0) time_out++;
		if(time_out >= TIME_OUT){
			sprintf(state,"STOPPING");
			time_out = 0;
		}
	}

	//When occur a reset do it
	if(_SWR || _WDTO){					
		_SWR = 0;
		_WDTO = 0;
		sprintf(state,"RESETING");
	}

//If the computer send a command this code see what command was sended
	if(command_received() || (stop == 1)){			
		
	//ids - identification command
		if(strncmp(Serial.rbuf,"ids",3) == 0){
			printf("IDS\t"ID_DO_HARDWARE"\t%s\n\r", state);
		}

	/*cfg - configure command and save the N command (as a string) in 
	the parameters[N][]*/
		if(strncmp(Serial.rbuf,"cfg",3) == 0){
			sscanf(Serial.rbuf,"%*s\t%d\t%d",&protocolo,&param_1);
			printf("CFG\t%d\t%d\n\r",protocolo,param_1);
			sprintf(state,"CONFIGURING");
		}

	//cur - current configuration command
		if(strncmp(Serial.rbuf,"cur",3) == 0){
			printf("CUR\t%d\t%d\n\r",protocolo,param_1);
		}

	//str - strat command
		if(strncmp(Serial.rbuf,"str",3) == 0){
			sprintf(state,"STARTING");
			printf("STR\n\r");
		}
	
	//stp command
		if(stop == 1){
			stop = 0;
			printf("STP\n\r");
			sprintf(state,"STOPPING");
		}	

	}//Fim do if(command_received())

	/*
	Send_data_to_computer(){
		printf("DAT\r");
		printf("%x\t%x\t%x\t[clock relativo]\r",canal_1, canal_..., canal_N, clock_relativo); //clock relativo optional
		printf("END\r");
	}
	*/

	/*
	Send_binary_data_to_computer(){
		printf("BIN\t%d\r", numero_de_bytes);
		printf("%b", dados_em_binario);
	}	
	*/

	/*
	Send_error_code(){
		printf("ERR\t%d\r", codigo_do_erro);
	}
	*/
}



