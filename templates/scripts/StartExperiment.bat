cls

echo Starting @experiment.name@ Driver

set INITIAL_HEAP_MEM=@hardwareserver.initial.heap@
set MAX_HEAP_MEM=@hardwareserver.max.heap@

set DRIVER_BASE_DIR=./hardwareserver

set GENERIC_ORB_SYSPROPS=-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=$DRIVER_BASE_DIR/etc/openorb.xml 
set DRIVER_ORB_SYSPROPS=-Dopenorb.profile=ReCHardware -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController
set MEM_SYSPROPS=-Xms%INITIAL_HEAP_MEM% -Xmx%MAX_HEAP_MEM%
set LOG_SYSPROPS=-Djava.util.logging.config.file=%DRIVER_BASE_DIR%/etc/loggers.config.properties
set DRIVER_HARWARE_INFO_SYSPROPS=-DeLab.@experiment.name@.HardwareInfo=@experiment.name@HardwareInfo.xml
set TOOLKIT_SYSPROPS=-Dawt.toolkit=sun.awt.motif.MToolkit

set RECCLASSPATH=%DRIVER_BASE_DIR%/lib/xml-apis.jar;%DRIVER_BASE_DIR%/lib/tools-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_orb-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_pss-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_orb_omg-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_ots-1.4.0.jar;%DRIVER_BASE_DIR%/lib/logkit.jar;%DRIVER_BASE_DIR%/lib/xercesImpl.jar;%DRIVER_BASE_DIR%/lib/avalon-framework.jar
set DRIVER_CLASSPATH=%DRIVER_BASE_DIR%/@experiment.name@Driver.jar;%DRIVER_BASE_DIR%/ELabHardwareServer.jar;%DRIVER_BASE_DIR%/ReCHardwareServer.jar;%DRIVER_BASE_DIR%/ReCCommon.jar

#export ARGS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60001,suspend=n" 

echo ClassPath        : %RECCLASSPATH%;%DRIVER_CLASSPATH%
echo 
echo System Properties: %GENERIC_ORB_SYSPROPS% %DRIVER_ORB_SYSPROPS% %LOG_SYSPROPS% %MEM_SYSPROPS% %DRIVER_HARWARE_INFO_SYSPROPS% %TOOLKIT_SYSPROPS%

java -classpath %RECCLASSPATH%;%DRIVER_CLASSPATH% %GENERIC_ORB_SYSPROPS% %DRIVER_ORB_SYSPROPS% %LOG_SYSPROPS% %MEM_SYSPROPS% %DRIVER_HARWARE_INFO_SYSPROPS% %TOOLKIT_SYSPROPS% pt.utl.ist.elab.driver.@experiment.name@.ServerMain &
