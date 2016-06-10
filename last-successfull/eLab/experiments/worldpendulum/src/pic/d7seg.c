#include "d7seg.h"
#include <p30F4011.h>		//defines dspic registers



void init_d7seg()
{
    D7SEG_DIO_TRIS = 0; //Definição dos pinos como Output
    D7SEG_SCK_TRIS = 0;
    D7SEG_RCK_TRIS = 0;
}

void print_d7seg(unsigned long pint, unsigned long pdec) {

    unsigned int send, //palavra de 2 bytes enviada em série para os registos
            j, //variável auxiliar (máscara)
            digit; //algarismo a apresentar num display. {0,1,2,...,9}
    static unsigned int position = 0b10000000; //codificação da posição do display a alterar
    static unsigned long int pinttemp = 0b10000000;

    static unsigned int digitcode[] = {
        0b11000000, //0
        0b11111001, //1
        0b10100100, //2
        0b10110000, //3
        0b10011001, //4
        0b10010010, //5
        0b10000010, //6
        0b11111000, //7
        0b10000000, //8
        0b10011000 //9
    }; //Cada elemento deste vector codifica quais os leds a acender tal que fique
    //representado o algarismo pretendido (índice do vector)
    //Exemplo 0b11111001 ilumina (0) apenas os leds 'b' e 'c', correspondentes ao algarismo 1.
    if (position == 0)
    {
        position = 0b10000000;
        pinttemp = pint;
    }

  //  for (position = 0b10000000; position; )
        //Este ciclo itera sobre todas as posições dos display
        //Exemplo position = 0b01010001 codificava o 2º, 4º e 8º displays (em simultâneo)

        digit = pinttemp % 10; //Cálculo do próximo dígito a ser representado
        pinttemp /= 10; //Preparação para a iteração seguinte
        send = (position << 8) + digitcode[digit];
        //Composição da palavra a ser enviada para os registos.
        //Primeiro byte = position, segundo = digito codificado

        D7SEG_RCK = 0; //Início do envio de dados
        for (j = 0b1000000000000000; j; j >>= 1)
        //Este ciclo envia em série a palavra de 2 bytes "send" para os registos
        {
            D7SEG_SCK = 0; //Clock
            D7SEG_DIO = ((send & j) ? 1 : 0); //Bit a enviar
            D7SEG_SCK = 1; //Clock
        }
        D7SEG_RCK = 1; //Fim do envio de dados, copia dos dados do
        //registo de entrada para o registo de armazenamento

    position >>= 1;
}
