#include "elab.h"		

char state[20];
char parameters[N_PARAMETERS_MAX][20];
int stop = 0;
int reset = 0;

//Function to put in the while true in main	
void rec_generic_driver(void ){		

	int i, j, parameter_n;

	//Every second this send the ids message (Timer1 -> 1sec)
	if(_T1IF){
		_T1IF = 0;
		printf("IDS\t"ID_DO_HARDWARE"\t%s\r", state);
	}	

	//When occur a reset do it
	if(_SWR || _WDTO){					
		_SWR = 0;
		_WDTO = 0;
		reset = 0;
		sprintf(state,"RESETING");
	}

//If the computer send a command this code see what command was sended
	if(command_received()){			
		
	//ids - identification command
		if(strncmp(Serial.rbuf,"ids",3) == 0){
			printf("IDS\t"ID_DO_HARDWARE"\t%s\r", state);
		}

	/*cfg - configure command and save the N command (as a string) in 
	the parameters[N][]*/
		if(strncmp(Serial.rbuf,"cfg",3) == 0){
			j = 0;
			parameter_n = 0;
			i = 4;
			while(Serial.rbuf[i] != '\0' && parameter_n < N_PARAMETERS_MAX){		
				parameters[parameter_n][j] = Serial.rbuf[i];
				i++;
				j++;
				//When finish the reception of a parameter
				if(Serial.rbuf[i] == '\t' || Serial.rbuf[i] == '\0'){
					parameters[parameter_n][j] = '\0';				
					j = 0;
					parameter_n++;
				}
			}
			
			/*Caso o numero de parametros seja inferior
			ao numero de parametros maximo*/
			while(parameter_n < N_PARAMETERS_MAX){			
				parameters[parameter_n][0] = 'X';			
				parameter_n++;
			}
			parameter_n = 0;
			printf("CFG");
			
			//Send the parameters received and saved in parameters[][]
			while(parameter_n < N_PARAMETERS_MAX){
				printf("\t");
				printf("%s", parameters[parameter_n]);
				parameter_n++; 
			}
			printf("\r");
			sprintf(state,"CONFIGURING");
		}

	//cur - current configuration command
		if(strncmp(Serial.rbuf,"cur",3) == 0){
			parameter_n = 0;
			printf("CUR");
			//Send the parametes received
			while(parameter_n < N_PARAMETERS_MAX && parameters[parameter_n][0] != '\0'){		
				printf("\t");
				printf("%s", parameters[parameter_n]);
				parameter_n++; 
			}
			printf("\r");
		}

	//str - strat command
		if(strncmp(Serial.rbuf,"str",3) == 0){
			sprintf(state,"STARTING");
			printf("STR\r");
		}
	
	//rst command
		if(reset == 1){
			reset = 0;
			printf("RST\r");
			asm("RESET");
		}
	
	//stp command
		if(stop == 1){
			stop = 0;
			printf("STP\r");
			sprintf(state,"STOPPING");
		}	

	}//Fim do if(command_received())

	//rst command
	if(reset == 1){
		reset = 0;
		printf("RST\r");
		asm("RESET");
	}

	//stp command
	if(stop == 1){
		stop = 0;
		printf("STP\r");
		sprintf(state,"STOPPING");
	}	

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



