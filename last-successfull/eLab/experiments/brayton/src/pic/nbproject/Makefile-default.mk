#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Include project Makefile
ifeq "${IGNORE_LOCAL}" "TRUE"
# do not include local makefile. User is passing all local related variables already
else
include Makefile
# Include makefile containing local settings
ifeq "$(wildcard nbproject/Makefile-local-default.mk)" "nbproject/Makefile-local-default.mk"
include nbproject/Makefile-local-default.mk
endif
endif

# Environment
MKDIR=gnumkdir -p
RM=rm -f 
MV=mv 
CP=cp 

# Macros
CND_CONF=default
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
IMAGE_TYPE=debug
OUTPUT_SUFFIX=elf
DEBUGGABLE_SUFFIX=elf
FINAL_IMAGE=dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}
else
IMAGE_TYPE=production
OUTPUT_SUFFIX=hex
DEBUGGABLE_SUFFIX=elf
FINAL_IMAGE=dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}
endif

# Object Directory
OBJECTDIR=build/${CND_CONF}/${IMAGE_TYPE}

# Distribution Directory
DISTDIR=dist/${CND_CONF}/${IMAGE_TYPE}

# Object Files Quoted if spaced
OBJECTFILES_QUOTED_IF_SPACED=${OBJECTDIR}/brayton_exp.o ${OBJECTDIR}/Library/adc.o ${OBJECTDIR}/Library/delays.o ${OBJECTDIR}/Library/inputcapture.o ${OBJECTDIR}/Library/maq_de_estados.o ${OBJECTDIR}/Library/protocolos.o ${OBJECTDIR}/Library/rec_generic_driver.o ${OBJECTDIR}/Library/relay.o ${OBJECTDIR}/Library/sendUInt_fast.o ${OBJECTDIR}/Library/servo.o ${OBJECTDIR}/Library/timer1.o ${OBJECTDIR}/Library/uart.o ${OBJECTDIR}/Library/outputcompare.o ${OBJECTDIR}/Library/timer3.o
POSSIBLE_DEPFILES=${OBJECTDIR}/brayton_exp.o.d ${OBJECTDIR}/Library/adc.o.d ${OBJECTDIR}/Library/delays.o.d ${OBJECTDIR}/Library/inputcapture.o.d ${OBJECTDIR}/Library/maq_de_estados.o.d ${OBJECTDIR}/Library/protocolos.o.d ${OBJECTDIR}/Library/rec_generic_driver.o.d ${OBJECTDIR}/Library/relay.o.d ${OBJECTDIR}/Library/sendUInt_fast.o.d ${OBJECTDIR}/Library/servo.o.d ${OBJECTDIR}/Library/timer1.o.d ${OBJECTDIR}/Library/uart.o.d ${OBJECTDIR}/Library/outputcompare.o.d ${OBJECTDIR}/Library/timer3.o.d

# Object Files
OBJECTFILES=${OBJECTDIR}/brayton_exp.o ${OBJECTDIR}/Library/adc.o ${OBJECTDIR}/Library/delays.o ${OBJECTDIR}/Library/inputcapture.o ${OBJECTDIR}/Library/maq_de_estados.o ${OBJECTDIR}/Library/protocolos.o ${OBJECTDIR}/Library/rec_generic_driver.o ${OBJECTDIR}/Library/relay.o ${OBJECTDIR}/Library/sendUInt_fast.o ${OBJECTDIR}/Library/servo.o ${OBJECTDIR}/Library/timer1.o ${OBJECTDIR}/Library/uart.o ${OBJECTDIR}/Library/outputcompare.o ${OBJECTDIR}/Library/timer3.o


CFLAGS=
ASFLAGS=
LDLIBSOPTIONS=

############# Tool locations ##########################################
# If you copy a project from one host to another, the path where the  #
# compiler is installed may be different.                             #
# If you open this project with MPLAB X in the new host, this         #
# makefile will be regenerated and the paths will be corrected.       #
#######################################################################
# fixDeps replaces a bunch of sed/cat/printf statements that slow down the build
FIXDEPS=fixDeps

.build-conf:  ${BUILD_SUBPROJECTS}
	${MAKE} ${MAKE_OPTIONS} -f nbproject/Makefile-default.mk dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}

MP_PROCESSOR_OPTION=30F4011
MP_LINKER_FILE_OPTION=,--script=p30F4011.gld
# ------------------------------------------------------------------------------------
# Rules for buildStep: compile
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
${OBJECTDIR}/brayton_exp.o: brayton_exp.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR} 
	@${RM} ${OBJECTDIR}/brayton_exp.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  brayton_exp.c  -o ${OBJECTDIR}/brayton_exp.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/brayton_exp.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/brayton_exp.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/adc.o: Library/adc.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/adc.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/adc.c  -o ${OBJECTDIR}/Library/adc.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/adc.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/adc.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/delays.o: Library/delays.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/delays.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/delays.c  -o ${OBJECTDIR}/Library/delays.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/delays.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/delays.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/inputcapture.o: Library/inputcapture.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/inputcapture.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/inputcapture.c  -o ${OBJECTDIR}/Library/inputcapture.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/inputcapture.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/inputcapture.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/maq_de_estados.o: Library/maq_de_estados.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/maq_de_estados.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/maq_de_estados.c  -o ${OBJECTDIR}/Library/maq_de_estados.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/maq_de_estados.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/maq_de_estados.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/protocolos.o: Library/protocolos.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/protocolos.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/protocolos.c  -o ${OBJECTDIR}/Library/protocolos.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/protocolos.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/protocolos.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/rec_generic_driver.o: Library/rec_generic_driver.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/rec_generic_driver.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/rec_generic_driver.c  -o ${OBJECTDIR}/Library/rec_generic_driver.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/rec_generic_driver.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/rec_generic_driver.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/relay.o: Library/relay.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/relay.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/relay.c  -o ${OBJECTDIR}/Library/relay.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/relay.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/relay.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/sendUInt_fast.o: Library/sendUInt_fast.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/sendUInt_fast.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/sendUInt_fast.c  -o ${OBJECTDIR}/Library/sendUInt_fast.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/sendUInt_fast.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/sendUInt_fast.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/servo.o: Library/servo.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/servo.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/servo.c  -o ${OBJECTDIR}/Library/servo.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/servo.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/servo.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/timer1.o: Library/timer1.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/timer1.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/timer1.c  -o ${OBJECTDIR}/Library/timer1.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/timer1.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/timer1.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/uart.o: Library/uart.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/uart.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/uart.c  -o ${OBJECTDIR}/Library/uart.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/uart.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/uart.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/outputcompare.o: Library/outputcompare.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/outputcompare.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/outputcompare.c  -o ${OBJECTDIR}/Library/outputcompare.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/outputcompare.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/outputcompare.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/timer3.o: Library/timer3.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/timer3.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/timer3.c  -o ${OBJECTDIR}/Library/timer3.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/timer3.o.d"      -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/timer3.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
else
${OBJECTDIR}/brayton_exp.o: brayton_exp.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR} 
	@${RM} ${OBJECTDIR}/brayton_exp.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  brayton_exp.c  -o ${OBJECTDIR}/brayton_exp.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/brayton_exp.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/brayton_exp.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/adc.o: Library/adc.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/adc.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/adc.c  -o ${OBJECTDIR}/Library/adc.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/adc.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/adc.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/delays.o: Library/delays.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/delays.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/delays.c  -o ${OBJECTDIR}/Library/delays.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/delays.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/delays.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/inputcapture.o: Library/inputcapture.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/inputcapture.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/inputcapture.c  -o ${OBJECTDIR}/Library/inputcapture.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/inputcapture.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/inputcapture.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/maq_de_estados.o: Library/maq_de_estados.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/maq_de_estados.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/maq_de_estados.c  -o ${OBJECTDIR}/Library/maq_de_estados.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/maq_de_estados.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/maq_de_estados.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/protocolos.o: Library/protocolos.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/protocolos.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/protocolos.c  -o ${OBJECTDIR}/Library/protocolos.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/protocolos.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/protocolos.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/rec_generic_driver.o: Library/rec_generic_driver.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/rec_generic_driver.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/rec_generic_driver.c  -o ${OBJECTDIR}/Library/rec_generic_driver.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/rec_generic_driver.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/rec_generic_driver.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/relay.o: Library/relay.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/relay.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/relay.c  -o ${OBJECTDIR}/Library/relay.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/relay.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/relay.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/sendUInt_fast.o: Library/sendUInt_fast.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/sendUInt_fast.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/sendUInt_fast.c  -o ${OBJECTDIR}/Library/sendUInt_fast.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/sendUInt_fast.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/sendUInt_fast.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/servo.o: Library/servo.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/servo.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/servo.c  -o ${OBJECTDIR}/Library/servo.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/servo.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/servo.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/timer1.o: Library/timer1.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/timer1.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/timer1.c  -o ${OBJECTDIR}/Library/timer1.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/timer1.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/timer1.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/uart.o: Library/uart.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/uart.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/uart.c  -o ${OBJECTDIR}/Library/uart.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/uart.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/uart.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/outputcompare.o: Library/outputcompare.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/outputcompare.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/outputcompare.c  -o ${OBJECTDIR}/Library/outputcompare.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/outputcompare.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/outputcompare.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
${OBJECTDIR}/Library/timer3.o: Library/timer3.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/Library 
	@${RM} ${OBJECTDIR}/Library/timer3.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  Library/timer3.c  -o ${OBJECTDIR}/Library/timer3.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/Library/timer3.o.d"      -g -omf=elf -O0 -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/Library/timer3.o.d" $(SILENT)  -rsi ${MP_CC_DIR}../ 
	
endif

# ------------------------------------------------------------------------------------
# Rules for buildStep: assemble
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
else
endif

# ------------------------------------------------------------------------------------
# Rules for buildStep: assemblePreproc
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
else
endif

# ------------------------------------------------------------------------------------
# Rules for buildStep: link
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}: ${OBJECTFILES}  nbproject/Makefile-${CND_CONF}.mk    
	@${MKDIR} dist/${CND_CONF}/${IMAGE_TYPE} 
	${MP_CC} $(MP_EXTRA_LD_PRE)  -o dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}  ${OBJECTFILES_QUOTED_IF_SPACED}      -mcpu=$(MP_PROCESSOR_OPTION)        -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -Wl,--defsym=__MPLAB_BUILD=1,--defsym=__ICD2RAM=1,--defsym=__MPLAB_DEBUG=1,--defsym=__DEBUG=1,--defsym=__MPLAB_DEBUGGER_PK3=1,$(MP_LINKER_FILE_OPTION),--stack=16,--check-sections,--data-init,--pack-data,--handles,--isr,--no-gc-sections,--fill-upper=0,--stackguard=16,--no-force-link,--smart-io,--report-mem$(MP_EXTRA_LD_POST) 
	
else
dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}: ${OBJECTFILES}  nbproject/Makefile-${CND_CONF}.mk   
	@${MKDIR} dist/${CND_CONF}/${IMAGE_TYPE} 
	${MP_CC} $(MP_EXTRA_LD_PRE)  -o dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${DEBUGGABLE_SUFFIX}  ${OBJECTFILES_QUOTED_IF_SPACED}      -mcpu=$(MP_PROCESSOR_OPTION)        -omf=elf -Wl,--defsym=__MPLAB_BUILD=1,$(MP_LINKER_FILE_OPTION),--stack=16,--check-sections,--data-init,--pack-data,--handles,--isr,--no-gc-sections,--fill-upper=0,--stackguard=16,--no-force-link,--smart-io,--report-mem$(MP_EXTRA_LD_POST) 
	${MP_CC_DIR}\\xc16-bin2hex dist/${CND_CONF}/${IMAGE_TYPE}/Brayton.X.${IMAGE_TYPE}.${DEBUGGABLE_SUFFIX} -a  -omf=elf 
	
endif


# Subprojects
.build-subprojects:


# Subprojects
.clean-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r build/default
	${RM} -r dist/default

# Enable dependency checking
.dep.inc: .depcheck-impl

DEPFILES=$(shell mplabwildcard ${POSSIBLE_DEPFILES})
ifneq (${DEPFILES},)
include ${DEPFILES}
endif
