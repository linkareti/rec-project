#!/bin/bash
clear

echo Starting Driver

INITIAL_HEAP_MEM=@driver.initial.heap@
MAX_HEAP_MEM=@driver.max.heap@

DRIVER_BASE_DIR=./driver

export GENERIC_ORB_SYSPROPS="-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$DRIVER_BASE_DIR/etc/openorb.xml "
export DRIVER_ORB_SYSPROPS="-Dopenorb.profile=ReCHardware -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController"
export MEM_SYSPROPS="-Xms$INITIAL_HEAP_MEM -Xmx$MAX_HEAP_MEM"
export LOG_SYSPROPS="-Djava.util.logging.config.file=$DRIVER_BASE_DIR/etc/loggers.config.properties" 
export DRIVER_HARWARE_INFO_SYSPROPS="-DeLab.@experiment.name@.HardwareInfo=HardwareInfo.xml"
export TOOLKIT_SYSPROPS="-Dawt.toolkit=sun.awt.motif.MToolkit"

export RECCLASSPATH=$DRIVER_BASE_DIR/lib/xml-apis.jar:$DRIVER_BASE_DIR/lib/tools-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_orb-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_pss-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_orb_omg-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_ots-1.4.0.jar:$DRIVER_BASE_DIR/lib/logkit.jar:$DRIVER_BASE_DIR/lib/xercesImpl.jar:$DRIVER_BASE_DIR/lib/avalon-framework.jar
export DRIVER_CLASSPATH=$DRIVER_BASE_DIR/ReCHardwareDriver.jar:$DRIVER_BASE_DIR/ReCCommon.jar

#export ARGS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60001,suspend=n" 

echo ClassPath        : $RECCLASSPATH:$DRIVER_CLASSPATH
echo 
echo System Properties: $GENERIC_ORB_SYSPROPS $DRIVER_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS $DRIVER_HARWARE_INFO_SYSPROPS $TOOLKIT_SYSPROPS

java -classpath $RECCLASSPATH:$DRIVER_CLASSPATH $GENERIC_ORB_SYSPROPS $DRIVER_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS $DRIVER_HARWARE_INFO_SYSPROPS $TOOLKIT_SYSPROPS pt.utl.ist.elab.driver.@experiment.name@.ServerMain &
