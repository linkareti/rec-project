#!/bin/bash   

clear
echo Starting multicast

OUTPUT_BASE_DIR=/multicast
export SYSTEM_PROPS="-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$OUTPUT_BASE_DIR/orb/OpenORBMultiCast.xml"

RECCLASSPATH=$OUTPUT_BASE_DIR/openorb/xml-apis_sig.jar:$OUTPUT_BASE_DIR/openorb/tools-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_orb-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_pss-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_orb_omg-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_ots-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/logkit_sig.jar:$OUTPUT_BASE_DIR/openorb/xercesImpl_sig.jar:$OUTPUT_BASE_DIR/openorb/avalon-framework_sig.jar:$OUTPUT_BASE_DIR/recbase/ReCData_sig.jar:$OUTPUT_BASE_DIR/recbase/ReCMulticastController_sig.jar:.:$CLASSPATH
export PATH OUTPUT_BASE_DIR SYSTEM_PROPS RECCLASSPATH

export SYSTEM_PROPS="-Dopenorb.profile=ReCMultiCastController -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController -Djava.util.logging.config.file=$OUTPUT_BASE_DIR/recbase/loggers.config.properties $SYSTEM_PROPS -Xms128m -Xmx256m"

export RECCLASSPATH=$OUTPUT_BASE_DIR/recbase/ReCMultiCastController_sig.jar:$RECCLASSPATH

export ARGS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=40000,suspend=n"

#export PREPEND=-Xbootclasspath/p:$OUTPUT_BASE_DIR/openorb/openorb_orb_omg-1.4.0_sig.jar

echo $ARGS -classpath $RECCLASSPATH $SYSTEM_PROPS com.linkare.rec.impl.multicast.startup.MultiCastControllerMain 

java $ARGS $PREPEND -classpath $RECCLASSPATH $SYSTEM_PROPS com.linkare.rec.impl.multicast.startup.MultiCastControllerMain &

echo $!> /var/lock/rec_multicast
