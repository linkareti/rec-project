#!/bin/bash
clear
echo Starting multicast

INITIAL_HEAP_MEM=@multicast.initial.heap@
MAX_HEAP_MEM=@multicast.max.heap@

MULTICAST_BASE_DIR=./multicast

export GENERIC_ORB_SYSPROPS="-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$MULTICAST_BASE_DIR/etc/openorb.xml "
export MULTICAST_ORB_SYSPROPS="-Dopenorb.profile=ReCMultiCastController -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController"
export MEM_SYSPROPS="-Xms$INITIAL_HEAP_MEM -Xmx$MAX_HEAP_MEM"
export LOG_SYSPROPS="-Djava.util.logging.config.file=$MULTICAST_BASE_DIR/etc/loggers.config.properties"
export TOOLKIT_SYSPROPS="-Djava.awt.headless=true"
export SECURITYMANAGER_SYSPROPS="-DReC.MultiCast.SecurityManager=com.linkare.rec.impl.multicast.security.CompositeSecurityManager -DReC.MultiCast.CompositeSecurityManager.list=com.linkare.rec.impl.multicast.security.AllocationManagerSecurityManager"
export ALLOCATIONMANAGER_SYSPROPS="-DReC.MultiCast.LabID=@rec.am.lab.id@ -DReC.MultiCast.AllocationManagerHost=localhost -DReC.MultiCast.AllocationManagerPort=3700"

export RECCLASSPATH=$MULTICAST_BASE_DIR/lib/xml-apis.jar:$MULTICAST_BASE_DIR/lib/tools-1.4.0.jar:$MULTICAST_BASE_DIR/lib/openorb_orb-1.4.0.jar:$MULTICAST_BASE_DIR/lib/openorb_pss-1.4.0.jar:$MULTICAST_BASE_DIR/lib/openorb_ots-1.4.0.jar:$MULTICAST_BASE_DIR/lib/logkit.jar:$MULTICAST_BASE_DIR/lib/xercesImpl.jar:$MULTICAST_BASE_DIR/lib/avalon-framework.jar
export MULTICAST_CLASSPATH=$MULTICAST_BASE_DIR/ReCMulticastController.jar:$MULTICAST_BASE_DIR/ELabMulticastController.jar:$MULTICAST_BASE_DIR/ReCCommon.jar

#export DEBUG="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60002,suspend=n"

export BOOTCLASSPATH=-Xbootclasspath/p:$MULTICAST_BASE_DIR/lib/openorb_orb_omg-1.4.0.jar 

echo --------------------------------------------------------------------------------
echo BootClassPath    : $BOOTCLASSPATH
echo --------------------------------------------------------------------------------
echo ClassPath        : $RECCLASSPATH;$MULTICAST_CLASSPATH
echo --------------------------------------------------------------------------------
echo System Properties: $GENERIC_ORB_SYSPROPS $MULTICAST_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS 

java $BOOTCLASSPATH -classpath $RECCLASSPATH:$MULTICAST_CLASSPATH $GENERIC_ORB_SYSPROPS $SECURITYMANAGER_SYSPROPS $ALLOCATIONMANAGER_SYSPROPS $MULTICAST_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS $TOOLKIT_SYSPROPS $DEBUG com.linkare.rec.impl.multicast.startup.MultiCastControllerMain &
