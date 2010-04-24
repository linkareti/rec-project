cls
echo Starting multicast

set INITIAL_HEAP_MEM=@multicast.initial.heap@
set MAX_HEAP_MEM=@multicast.max.heap@

set MULTICAST_BASE_DIR=.\multicast

set GENERIC_ORB_SYSPROPS=-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=%MULTICAST_BASE_DIR%/etc/openorb.xml
set MULTICAST_ORB_SYSPROPS=-Dopenorb.profile=ReCMultiCastController -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController
set MEM_SYSPROPS=-Xms%INITIAL_HEAP_MEM% -Xmx%MAX_HEAP_MEM%
set LOG_SYSPROPS=-Djava.util.logging.config.file=%MULTICAST_BASE_DIR%/etc/loggers.config.properties 


set RECCLASSPATH=%MULTICAST_BASE_DIR%/lib/xml-apis.jar;%MULTICAST_BASE_DIR%/lib/tools-1.4.0.jar;%MULTICAST_BASE_DIR%/lib/openorb_orb-1.4.0.jar;%MULTICAST_BASE_DIR%/lib/openorb_pss-1.4.0.jar;%MULTICAST_BASE_DIR%/lib/openorb_orb_omg-1.4.0.jar;%MULTICAST_BASE_DIR%/lib/openorb_ots-1.4.0.jar;%MULTICAST_BASE_DIR%/lib/logkit.jar;%MULTICAST_BASE_DIR%/lib/xercesImpl.jar;%MULTICAST_BASE_DIR%/lib/avalon-framework.jar
set MULTICAST_CLASSPATH=%MULTICAST_BASE_DIR%/ReCMulticastController.jar;%MULTICAST_BASE_DIR%/ELabMulticastController.jar;%MULTICAST_BASE_DIR%/ReCCommon.jar;

echo ClassPath        : %RECCLASSPATH%;%MULTICAST_CLASSPATH%
echo 
echo System Properties: %GENERIC_ORB_SYSPROPS% %MULTICAST_ORB_SYSPROPS% %LOG_SYSPROPS% %MEM_SYSPROPS% 

java -classpath %RECCLASSPATH%;%MULTICAST_CLASSPATH% %GENERIC_ORB_SYSPROPS% %MULTICAST_ORB_SYSPROPS% %LOG_SYSPROPS% %MEM_SYSPROPS% com.linkare.rec.impl.multicast.startup.MultiCastControllerMain
