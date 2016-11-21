#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Include project Makefile
include Makefile
# Include makefile containing local settings
ifeq "$(wildcard nbproject/Makefile-local-default.mk)" "nbproject/Makefile-local-default.mk"
include nbproject/Makefile-local-default.mk
endif

# Environment
MKDIR=mkdir -p
RM=rm -f 
MV=mv 
CP=cp 

# Macros
CND_CONF=default
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
IMAGE_TYPE=debug
OUTPUT_SUFFIX=elf
DEBUGGABLE_SUFFIX=elf
FINAL_IMAGE=dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}
else
IMAGE_TYPE=production
OUTPUT_SUFFIX=hex
DEBUGGABLE_SUFFIX=elf
FINAL_IMAGE=dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}
endif

# Object Directory
OBJECTDIR=build/${CND_CONF}/${IMAGE_TYPE}

# Distribution Directory
DISTDIR=dist/${CND_CONF}/${IMAGE_TYPE}

# Object Files Quoted if spaced
OBJECTFILES_QUOTED_IF_SPACED=${OBJECTDIR}/_ext/1670778117/uart.o ${OBJECTDIR}/_ext/1670778117/adc.o ${OBJECTDIR}/_ext/1670778117/delays.o ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o ${OBJECTDIR}/_ext/1670778117/protocolos.o ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o ${OBJECTDIR}/_ext/1670778117/timer.o ${OBJECTDIR}/_ext/1670778117/output_compare.o ${OBJECTDIR}/_ext/43898991/langmuir.o ${OBJECTDIR}/_ext/1670778117/hardware_uart.o ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o
POSSIBLE_DEPFILES=${OBJECTDIR}/_ext/1670778117/uart.o.d ${OBJECTDIR}/_ext/1670778117/adc.o.d ${OBJECTDIR}/_ext/1670778117/delays.o.d ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d ${OBJECTDIR}/_ext/1670778117/protocolos.o.d ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d ${OBJECTDIR}/_ext/1670778117/timer.o.d ${OBJECTDIR}/_ext/1670778117/output_compare.o.d ${OBJECTDIR}/_ext/43898991/langmuir.o.d ${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d

# Object Files
OBJECTFILES=${OBJECTDIR}/_ext/1670778117/uart.o ${OBJECTDIR}/_ext/1670778117/adc.o ${OBJECTDIR}/_ext/1670778117/delays.o ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o ${OBJECTDIR}/_ext/1670778117/protocolos.o ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o ${OBJECTDIR}/_ext/1670778117/timer.o ${OBJECTDIR}/_ext/1670778117/output_compare.o ${OBJECTDIR}/_ext/43898991/langmuir.o ${OBJECTDIR}/_ext/1670778117/hardware_uart.o ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o


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
	${MAKE}  -f nbproject/Makefile-default.mk dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}

MP_PROCESSOR_OPTION=30F4011
MP_LINKER_FILE_OPTION=,--script=p30F4011.gld
# ------------------------------------------------------------------------------------
# Rules for buildStep: compile
ifeq ($(TYPE_IMAGE), DEBUG_RUN)
${OBJECTDIR}/_ext/1670778117/uart.o: ../../Library/uart.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/uart.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/uart.c  -o ${OBJECTDIR}/_ext/1670778117/uart.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/uart.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/uart.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/adc.o: ../../Library/adc.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/adc.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/adc.c  -o ${OBJECTDIR}/_ext/1670778117/adc.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/adc.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/adc.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/delays.o: ../../Library/delays.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/delays.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/delays.c  -o ${OBJECTDIR}/_ext/1670778117/delays.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/delays.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/delays.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/maq_de_estados.o: ../../Library/maq_de_estados.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/maq_de_estados.c  -o ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/protocolos.o: ../../Library/protocolos.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/protocolos.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/protocolos.c  -o ${OBJECTDIR}/_ext/1670778117/protocolos.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/protocolos.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/protocolos.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o: ../../Library/rec_generic_driver.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/rec_generic_driver.c  -o ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/timer.o: ../../Library/timer.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/timer.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/timer.c  -o ${OBJECTDIR}/_ext/1670778117/timer.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/timer.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/timer.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/output_compare.o: ../../Library/output_compare.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/output_compare.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/output_compare.c  -o ${OBJECTDIR}/_ext/1670778117/output_compare.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/output_compare.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/output_compare.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/43898991/langmuir.o: ../../langmuir.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/43898991 
	@${RM} ${OBJECTDIR}/_ext/43898991/langmuir.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../langmuir.c  -o ${OBJECTDIR}/_ext/43898991/langmuir.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/43898991/langmuir.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/43898991/langmuir.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/hardware_uart.o: ../../Library/hardware_uart.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/hardware_uart.c  -o ${OBJECTDIR}/_ext/1670778117/hardware_uart.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o: ../../Library/pfeiffer_crc.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/pfeiffer_crc.c  -o ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o: ../../Library/pfeiffer_gauges.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/pfeiffer_gauges.c  -o ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d"        -g -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d" $(SILENT) 
	
else
${OBJECTDIR}/_ext/1670778117/uart.o: ../../Library/uart.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/uart.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/uart.c  -o ${OBJECTDIR}/_ext/1670778117/uart.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/uart.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/uart.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/adc.o: ../../Library/adc.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/adc.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/adc.c  -o ${OBJECTDIR}/_ext/1670778117/adc.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/adc.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/adc.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/delays.o: ../../Library/delays.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/delays.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/delays.c  -o ${OBJECTDIR}/_ext/1670778117/delays.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/delays.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/delays.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/maq_de_estados.o: ../../Library/maq_de_estados.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/maq_de_estados.c  -o ${OBJECTDIR}/_ext/1670778117/maq_de_estados.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/maq_de_estados.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/protocolos.o: ../../Library/protocolos.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/protocolos.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/protocolos.c  -o ${OBJECTDIR}/_ext/1670778117/protocolos.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/protocolos.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/protocolos.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o: ../../Library/rec_generic_driver.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/rec_generic_driver.c  -o ${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/rec_generic_driver.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/timer.o: ../../Library/timer.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/timer.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/timer.c  -o ${OBJECTDIR}/_ext/1670778117/timer.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/timer.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/timer.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/output_compare.o: ../../Library/output_compare.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/output_compare.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/output_compare.c  -o ${OBJECTDIR}/_ext/1670778117/output_compare.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/output_compare.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/output_compare.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/43898991/langmuir.o: ../../langmuir.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/43898991 
	@${RM} ${OBJECTDIR}/_ext/43898991/langmuir.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../langmuir.c  -o ${OBJECTDIR}/_ext/43898991/langmuir.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/43898991/langmuir.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/43898991/langmuir.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/hardware_uart.o: ../../Library/hardware_uart.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/hardware_uart.c  -o ${OBJECTDIR}/_ext/1670778117/hardware_uart.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/hardware_uart.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o: ../../Library/pfeiffer_crc.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/pfeiffer_crc.c  -o ${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/pfeiffer_crc.o.d" $(SILENT) 
	
${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o: ../../Library/pfeiffer_gauges.c  nbproject/Makefile-${CND_CONF}.mk
	@${MKDIR} ${OBJECTDIR}/_ext/1670778117 
	@${RM} ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d 
	${MP_CC} $(MP_EXTRA_CC_PRE)  ../../Library/pfeiffer_gauges.c  -o ${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o  -c -mcpu=$(MP_PROCESSOR_OPTION)  -MMD -MF "${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d"        -g -omf=elf -O0 -I"../../Library" -msmart-io=1 -Wall -msfr-warn=off
	@${FIXDEPS} "${OBJECTDIR}/_ext/1670778117/pfeiffer_gauges.o.d" $(SILENT) 
	
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
dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}: ${OBJECTFILES}  nbproject/Makefile-${CND_CONF}.mk   
	@${MKDIR} dist/${CND_CONF}/${IMAGE_TYPE} 
	${MP_CC} $(MP_EXTRA_LD_PRE)  -o dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}  ${OBJECTFILES_QUOTED_IF_SPACED}      -mcpu=$(MP_PROCESSOR_OPTION)        -D__DEBUG -D__MPLAB_DEBUGGER_PK3=1  -omf=elf -Wl,--defsym=__MPLAB_BUILD=1,--defsym=__MPLAB_DEBUG=1,--defsym=__ICD2RAM=1,--defsym=__DEBUG=1,--defsym=__MPLAB_DEBUGGER_PK3=1,$(MP_LINKER_FILE_OPTION),--heap=256,--stack=16,--check-sections,--data-init,--pack-data,--handles,--isr,--no-gc-sections,--fill-upper=0,--stackguard=16,--library-path="../..",--no-force-link,--smart-io,-Map="${DISTDIR}/langmuir.X.${IMAGE_TYPE}.map",--report-mem$(MP_EXTRA_LD_POST) 
	
else
dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${OUTPUT_SUFFIX}: ${OBJECTFILES}  nbproject/Makefile-${CND_CONF}.mk   
	@${MKDIR} dist/${CND_CONF}/${IMAGE_TYPE} 
	${MP_CC} $(MP_EXTRA_LD_PRE)  -o dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${DEBUGGABLE_SUFFIX}  ${OBJECTFILES_QUOTED_IF_SPACED}      -mcpu=$(MP_PROCESSOR_OPTION)        -omf=elf -Wl,--defsym=__MPLAB_BUILD=1,$(MP_LINKER_FILE_OPTION),--heap=256,--stack=16,--check-sections,--data-init,--pack-data,--handles,--isr,--no-gc-sections,--fill-upper=0,--stackguard=16,--library-path="../..",--no-force-link,--smart-io,-Map="${DISTDIR}/langmuir.X.${IMAGE_TYPE}.map",--report-mem$(MP_EXTRA_LD_POST) 
	${MP_CC_DIR}/xc16-bin2hex dist/${CND_CONF}/${IMAGE_TYPE}/langmuir.X.${IMAGE_TYPE}.${DEBUGGABLE_SUFFIX} -a  -omf=elf 
	
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

DEPFILES=$(shell "${PATH_TO_IDE_BIN}"mplabwildcard ${POSSIBLE_DEPFILES})
ifneq (${DEPFILES},)
include ${DEPFILES}
endif
