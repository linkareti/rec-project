#!/bin/bash
clear
echo Starting @experiment.name@ Driver

# @ Initializing driver starter script @

INITIAL_HEAP_MEM=@hardwareserver.initial.heap@
MAX_HEAP_MEM=@hardwareserver.max.heap@

BASE_USER=elab
BASE_USER_HOMEDIR=~elab
EXPERIMENT_NAME=@experiment.name@
DEPLOY_DIR=`pwd`
DRIVER_BASE_DIR="$DEPLOY_DIR/hardwareserver"

export GENERIC_ORB_SYSPROPS="-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$DRIVER_BASE_DIR/etc/openorb.xml -Djava.net.preferIPv4Stack=true"
export DRIVER_ORB_SYSPROPS="-Dopenorb.profile=ReCHardware -Drec.multicastcontroller.initref=MultiCastController -Drec.percent.freememory.threshold.serialization=10 -Drec.multicastdataproducer.getsamples.idletime=60 -Drec.driver.show.gui=false"
export MEM_SYSPROPS="-Xms$INITIAL_HEAP_MEM -Xmx$MAX_HEAP_MEM"
export LOG_SYSPROPS="-Djava.util.logging.config.file=$DRIVER_BASE_DIR/etc/loggers.config.properties" 
export PROCESSINGMANAGER_SYSPROPS="-Drec.processingmanager.threadPool.coresize=@rec.driver.processingmanager.threadpool.coresize@ -Drec.processingmanager.threadPool.maxsize=@rec.driver.processingmanager.threadpool.maxsize@ -Drec.processingmanager.thread.idletime=@rec.processingmanager.thread.idletime@"
export EXPERIMENT_DRIVER_CLASS="-Dexperiment.driver.class=@experiment.driver.class@"

# TODO - Aleitao
# não esquecer que cada experiência poderá querer definir command line arguments 
# adicionais... pode ser o driver_hardware_info ou outras quaisquer...


export TOOLKIT_SYSPROPS="-Dawt.toolkit=sun.awt.motif.MToolkit -Djava.awt.headless=true"

export RECCLASSPATH=$DRIVER_BASE_DIR/lib/xml-apis.jar:$DRIVER_BASE_DIR/lib/tools-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_orb-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_pss-1.4.0.jar:$DRIVER_BASE_DIR/lib/openorb_ots-1.4.0.jar:$DRIVER_BASE_DIR/lib/logkit.jar:$DRIVER_BASE_DIR/lib/xercesImpl.jar:$DRIVER_BASE_DIR/lib/avalon-framework.jar:$DRIVER_BASE_DIR/lib/OSP.jar:$DRIVER_BASE_DIR/lib/RXTXcomm.jar:$DRIVER_BASE_DIR/lib/commons-i18n-0.0.1-SNAPSHOT.jar:$DRIVER_BASE_DIR/lib/commons-net-0.0.1-SNAPSHOT.jar
export DRIVER_CLASSPATH=$DRIVER_BASE_DIR/@experiment.name@Driver.jar:$DRIVER_BASE_DIR/ELabHardwareServer.jar:$DRIVER_BASE_DIR/ReCHardwareServer.jar:$DRIVER_BASE_DIR/ReCCommon.jar
export DRIVER_EXPERIMENT_CLASSPATH=

#export DEBUG="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60001,suspend=n" 

export BOOTCLASSPATH=-Xbootclasspath/p:$DRIVER_BASE_DIR/lib/openorb_orb_omg-1.4.0.jar

echo --------------------------------------------------------------------------------
echo BootClassPath    : $BOOTCLASSPATH
echo --------------------------------------------------------------------------------
echo ClassPath        : $RECCLASSPATH;$DRIVER_CLASSPATH;$DRIVER_EXPERIMENT_CLASSPATH
echo --------------------------------------------------------------------------------
echo System Properties: $GENERIC_ORB_SYSPROPS $DRIVER_ORB_SYSPROPS $LOG_SYSPROPS $MEM_SYSPROPS $TOOLKIT_SYSPROPS 
echo --------------------------------------------------------------------------------


java $BOOTCLASSPATH -Djava.library.path=/home/elab/rxtx -classpath $RECCLASSPATH:$DRIVER_CLASSPATH:$DRIVER_EXPERIMENT_CLASSPATH $GENERIC_ORB_SYSPROPS $DRIVER_ORB_SYSPROPS $LOG_SYSPROPS $PROCESSINGMANAGER_SYSPROPS $MEM_SYSPROPS $DRIVER_HARWARE_INFO_SYSPROPS $TOOLKIT_SYSPROPS $EXPERIMENT_DRIVER_CLASS $DEBUG @driver.main.class@ &

PID=$!
echo $PID > $DEPLOY_DIR/@experiment.name@.pid
