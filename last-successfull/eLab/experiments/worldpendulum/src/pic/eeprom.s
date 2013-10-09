;
; file: eeprom_functions.s
;
.INCLUDE "p30F4011.inc"


.global _eeprom_erase_block
.global _eeprom_erase_word
.global _eeprom_erase_all

.global _eeprom_write_block
.global _eeprom_write_word

.global _eeprom_read_block
.global _eeprom_read_word

.global _eeprom_wait


;codes for NVMCON:
	;Erase 1 data word 0x4044
	;Erase 16 data words 0x4045
	;Erase Entire EEPROM 0x4046
	;Program 1 data word 0x4004
	;Program 16 data words 0x4005

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ERASE ENTIRE EEPROM
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_erase_all:
;Setup NVMCON to erase all of data EEPROM
	MOV #0x4046,W2
	MOV W2,NVMCON
;Disable interrupts while the KEY sequence is written
	DISI #6
;Write the KEY sequence
	MOV #0x55, W2
	MOV W2, NVMKEY
	MOV #0xAA, W2
	MOV W2, NVMKEY
;Start the erase cycle
	BSET NVMCON,#15
	NOP
	NOP
	GOTO _eeprom_wait
	RETURN


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ERASE EEPROM BLOCK
; (one block is 16 words or 32 bytes)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_erase_block:
;W0=page
;W1=offset
;Set up a pointer to the EEPROM location to be erased.
	MOV W0,NVMADRU
	MOV W1,NVMADR
;Setup NVMCON to erase one block of data EEPROM
	MOV #0x4045,W0
	MOV W0,NVMCON
;Disable interrupts while the KEY sequence is written
	DISI #6
;Write the KEY sequence
	MOV #0x55, W0
	MOV W0, NVMKEY
	MOV #0xAA, W0
	MOV W0, NVMKEY
;Start the erase cycle
	BSET NVMCON,#15
	NOP
	NOP
	GOTO _eeprom_wait
	RETURN


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; ERASE EEPROM WORD
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_erase_word:
;W0=page
;W1=offset
;Set up a pointer to the EEPROM location to be erased.
	MOV W0,NVMADRU
	MOV W1,NVMADR
;Setup NVMCON to erase one word of data EEPROM
	MOV #0x4044,W0
	MOV W0,NVMCON
;Disable interrupts while the KEY sequence is written
	DISI #6
;Write the KEY sequence
	MOV #0x55, W0
	MOV W0, NVMKEY
	MOV #0xAA, W0
	MOV W0, NVMKEY
;Start the erase cycle
	BSET NVMCON,#15
	NOP
	NOP
	GOTO _eeprom_wait
	RETURN


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; WRITE EEPROM BLOCK
; (one block is 16 words or 32 bytes)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_write_block:
;W0=page
;W1=offset
;W2=pointer to data
;Setup a pointer to data EEPROM
	MOV W0,TBLPAG
;Write data value to holding latch
	DO #15,table_write_end
	TBLWTL [W2],[W1]
	INC2 W1,W1
table_write_end:
	INC2 W2,W2
;NVMADR captures write address from the TBLWTL instruction.
;Setup NVMCON for programming one block to data EEPROM
	MOV #0x4005,W0
	MOV W0,NVMCON
;Disable interrupts while the KEY sequence is written
	DISI #6
;Write the key sequence
	MOV #0x55,W0
	MOV W0,NVMKEY
	MOV #0xAA,W0
	MOV W0,NVMKEY
;Start the write cycle
	BSET NVMCON,#15
	NOP
	NOP
	GOTO _eeprom_wait
	RETURN



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; WRITE EEPROM WORD
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_write_word:
;W0=page
;W1=offset
;W2=data
;Setup a pointer to data EEPROM
	MOV W0,TBLPAG
;Write data value to holding latch
	TBLWTL W2,[W1]
;NVMADR captures write address from the TBLWTL instruction.
;Setup NVMCON for programming one word to data EEPROM
	MOV #0x4004,W0
	MOV W0,NVMCON
;Disable interrupts while the KEY sequence is written
	DISI #6
;Write the key sequence
	MOV #0x55,W0
	MOV W0,NVMKEY
	MOV #0xAA,W0
	MOV W0,NVMKEY
;Start the write cycle
	BSET NVMCON,#15
	NOP
	NOP
	GOTO _eeprom_wait
	RETURN




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; READ EEPROM BLOCK
; (one block is 16 words or 32 bytes)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_read_block:
;W0=page
;W1=offset
;W2=buffer
;Setup pointer to EEPROM memory
	MOV W0,TBLPAG
;Read the EEPROM data
	DO #15,table_read_end
	TBLRDL [W1],[W2]
	INC2 W1,W1
table_read_end:
	INC2 W2,W2
	RETURN



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; READ EEPROM WORD
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_read_word:
;W0=page
;W1=offset
;W2=buffer
;Setup pointer to EEPROM memory
	MOV W0,TBLPAG
;Read the EEPROM data
	TBLRDL [W1],[W2]
	RETURN



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; WAIT FOR EEPROM WRITE
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_eeprom_wait:
	BTSC NVMCON,#15
	GOTO _eeprom_wait
	RETURN



.end


