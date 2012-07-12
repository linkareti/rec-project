#!/bin/bash
clear
echo Starting multicast

INITIAL_HEAP_MEM=@multicast.initial.heap@
MAX_HEAP_MEM=@multicast.max.heap@

MULTICAST_BASE_DIR=./multicast

export GENERIC_ORB_SYSPROPS="-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$MULTICAST_BASE_DIR/etc/openorb.xml "
export MULTICAST_ORB_SYSPROPS="-Dopenorb.profile=ReCMultiCastController -Drec.multicastcontroller.bindname=MultiCastController -Drec.multicastcontroller.initref=MultiCastController -Drec.percent.freememory.threshold.serialization=10 -Drec.multicastdataproducer.getsamples.idletime=60 -Drec.multicastcontroller.maxclients.per.hardware=20 -Drec.multicastcontroller.max.hardwares=40 -Drec.multicastcontroller.showgui=false"
export MEM_SYSPROPS="-Xms$INITIAL_HEAP_MEM -Xmx$MAX_HEAP_MEM"
export LOG_SYSPROPS="-Djava.util.logging.config.file=$MULTICAST_BASE_DIR/etc/loggers.config.properties"
export TOOLKIT_SYSPROPS="-Djava.awt.headless=true"
#export SECURITYMANAGER_SYSPROPS="-Drec.multicast.securitymanager=@rec.am.securitymanager@ -Drec.multicast.compositesecuritymanager.list=@rec.am.compositesecuritymanager.list@"
#export SECURITYMANAGER_TIMES_SYSPROPS="-Drec.multicast.securitymanager.interval.lap.time.minutes=@rec.am.securitymanager.interval.lap.time.minutes@ -Drec.multicast.securitymanager.near.lap.time.minutes=@rec.am.securitymanager.near.lap.time.minutes@ -Drec.multicast.securitymanager.refresh.lap.time.minutes=@rec.am.securitymanager.refresh.lap.time.minutes@"
#export ALLOCATIONMANAGER_SYSPROPS="-Drec.multicast.labid=@rec.am.lab.id@ -Drec.multicast.allocation.manager.host=@rec.am.host@ -Drec.multicast.allocation.manager.port=@rec.am.port@"
export PROCESSINGMANAGER_SYSPROPS="-Drec.processingmanager.threadPool.coresize=@rec.multicast.processingmanager.threadpool.coresize@ -Drec.processingmanager.threadPool.maxsize=@rec.multicast.processingmanager.threadpool.maxsize@ -Drec.processingmanager.thread.idletime=@rec.processingmanager.thread.idletime@"
#export JMX_SYSPROPS="-Djava.rmi.server.hostname=@java.rmi.server.hostname@ -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=@com.sun.management.jmxremote.port@ -Dcom.sun.management.jmxremote.access.file=$MULTICAST_BASE_DIR/etc/jmxremote.access -Dcom.sun.management.jmxremote.password.file=$MULTICAST_BASE_DIR/etc/jmxremote.password"
#export REPOSITORY_SYSPROPS="-Drec.multicast.repository=@rec.multicast.repository@"
export SYSPROP_BADWORD_REFRESH_TIME_LAP_MINUTES=-Drec.multicast.badwordmanager.refresh.lap.time.minutes=@rec.multicast.badwordmanager.refresh.lap.time.minutes@

export RECCLASSPATH=$MULTICAST_BASE_DIR/lib/xml-apis.jar:$MULTICAST_BASE_DIR/lib/tools-1.4.0.jar:$MULTICAST_BASE_DIR/lib/openorb_orb-1.4.0.jar:$MULTICAST_BASE_DIR/lib/openorb_pss-1.4.0.jar:$MULTICAST_BASE_DIR/lib/openorb_ots-1.4.0.jar:$MULTICAST_BASE_DIR/lib/logkit.jar:$MULTICAST_BASE_DIR/lib/xercesImpl.jar:$MULTICAST_BASE_DIR/lib/avalon-framework.jar
export MULTICAST_CLASSPATH=$MULTICAST_BASE_DIR/ReCMulticastController.jar:$MULTICAST_BASE_DIR/ELabMulticastController.jar:$MULTICAST_BASE_DIR/ReCCommon.jar:$MULTICAST_BASE_DIR/lib/rec.am-api-0.0.1-SNAPSHOT.jar:$MULTICAST_BASE_DIR/lib/commons-0.0.1.jar:$MULTICAST_BASE_DIR/lib/glassfish/ejb-container.jar:$MULTICAST_BASE_DIR/lib/glassfish/appclient.security.jar

#export DEBUG="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60002,suspend=n"

export BOOTCLASSPATH=-Xbootclasspath/p:$MULTICAST_BASE_DIR/lib/openorb_orb_omg-1.4.0.jar 

echo --------------------------------------------------------------------------------
echo BootClassPath    : $BOOTCLASSPATH
echo --------------------------------------------------------------------------------
echo ClassPath        : $RECCLASSPATH;$MULTICAST_CLASSPATH
echo --------------------------------------------------------------------------------
echo System Properties: $GENERIC_ORB_SYSPROPS $MULTICAST_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS 

java $BOOTCLASSPATH -classpath $RECCLASSPATH:$MULTICAST_CLASSPATH $GENERIC_ORB_SYSPROPS $SECURITYMANAGER_SYSPROPS $SECURITYMANAGER_TIMES_SYSPROPS $ALLOCATIONMANAGER_SYSPROPS $PROCESSINGMANAGER_SYSPROPS $JMX_SYSPROPS $REPOSITORY_SYSPROPS $MULTICAST_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS $TOOLKIT_SYSPROPS $SYSPROP_BADWORD_REFRESH_TIME_LAP_MINUTES $DEBUG com.linkare.rec.impl.multicast.startup.MultiCastControllerMain &

PID=$!
echo $PID > multicast_@lab.name@.pid
