PATH=$PATH:/usr/lib/jvm/java-1.6.0-sun-1.6.0.15/bin
#:/usr/lib/jvm/java-1.6.0-sun-1.6.0.15/jre/lib
JMFHOME=/opt/JMF-2.1.1e
CLASSPATH=$JMFHOME/lib/jmf.jar
LD_LIBRARY_PATH=$JMFHOME/lib

OUTPUT_BASE_DIR=/multicast
export SYSTEM_PROPS="-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$OUTPUT_BASE_DIR/orb/OpenORBMultiCast.xml"

RECCLASSPATH=$OUTPUT_BASE_DIR/openorb/xml-apis_sig.jar:$OUTPUT_BASE_DIR/openorb/tools-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_orb-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_pss-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_orb_omg-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/openorb_ots-1.4.0_sig.jar:$OUTPUT_BASE_DIR/openorb/logkit_sig.jar:$OUTPUT_BASE_DIR/openorb/xercesImpl_sig.jar:$OUTPUT_BASE_DIR/openorb/avalon-framework_sig.jar:$OUTPUT_BASE_DIR/recbase/ReCData_sig.jar:$OUTPUT_BASE_DIR/recbase/ReCMulticastController_sig.jar:.:$CLASSPATH
export PATH OUTPUT_BASE_DIR SYSTEM_PROPS RECCLASSPATH
