#!/bin/bash   

clear
echo Starting multicast

source ./SetProperties.sh

export SYSTEM_PROPS="-Dopenorb.profile=ReCMultiCastController -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController -Djava.util.logging.config.file=$OUTPUT_BASE_DIR/recbase/loggers.config.properties $SYSTEM_PROPS -Xms128m -Xmx256m"

export RECCLASSPATH=$OUTPUT_BASE_DIR/recbase/ReCMultiCastController_sig.jar:$RECCLASSPATH

export ARGS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=40000,suspend=n"

#export PREPEND=-Xbootclasspath/p:$OUTPUT_BASE_DIR/openorb/openorb_orb_omg-1.4.0_sig.jar

echo $ARGS -classpath $RECCLASSPATH $SYSTEM_PROPS com.linkare.rec.impl.multicast.startup.MultiCastControllerMain 

java $ARGS $PREPEND -classpath $RECCLASSPATH $SYSTEM_PROPS com.linkare.rec.impl.multicast.startup.MultiCastControllerMain &

echo $!> /var/lock/rec_multicast
