cls
@ECHO OFF
echo Starting @experiment.name@ Driver

REM @ Initializing driver starter script @

set INITIAL_HEAP_MEM=@hardwareserver.initial.heap@
set MAX_HEAP_MEM=@hardwareserver.max.heap@

set DRIVER_BASE_DIR=%CD%\hardwareserver

set GENERIC_ORB_SYSPROPS=-Dorg.omg.CORBA.ORBClass=org.openorb.orb.core.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.openorb.orb.core.ORBSingleton -Dopenorb.config=%DRIVER_BASE_DIR%/etc/openorb.xml 
set DRIVER_ORB_SYSPROPS=-Dopenorb.profile=ReCHardware -Drec.multicastcontroller.initref=MultiCastController -Drec.percent.freememory.threshold.serialization=10 -Drec.multicastdataproducer.getsamples.idletime=60 -Drec.driver.show.gui=false
set MEM_SYSPROPS=-Xms%INITIAL_HEAP_MEM% -Xmx%MAX_HEAP_MEM%
set LOG_SYSPROPS=-Djava.util.logging.config.file=%DRIVER_BASE_DIR%/etc/loggers.config.properties
set PROCESSINGMANAGER_SYSPROPS=-Drec.processingmanager.threadPool.coresize=@rec.driver.processingmanager.threadpool.coresize@ -Drec.processingmanager.threadPool.maxsize=@rec.driver.processingmanager.threadpool.maxsize@ -Drec.processingmanager.thread.idletime=@rec.processingmanager.thread.idletime@

REM TODO - Aleitao
REM não esquecer que cada experiência poderá querer definir command line arguments 
REM adicionais... pode ser o driver_hardware_info ou outras quaisquer...


set TOOLKIT_SYSPROPS=-Dawt.toolkit=sun.awt.motif.MToolkit -Djava.awt.headless=true

set RECCLASSPATH=%DRIVER_BASE_DIR%/lib/xml-apis.jar;%DRIVER_BASE_DIR%/lib/tools-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_orb-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_pss-1.4.0.jar;%DRIVER_BASE_DIR%/lib/openorb_ots-1.4.0.jar;%DRIVER_BASE_DIR%/lib/logkit.jar;%DRIVER_BASE_DIR%/lib/xercesImpl.jar;%DRIVER_BASE_DIR%/lib/avalon-framework.jar;%DRIVER_BASE_DIR%/lib/OSP.jar;%DRIVER_BASE_DIR%/lib/RXTXcomm.jar;%DRIVER_BASE_DIR%/lib/commons-i18n-0.0.1-SNAPSHOT.jar;%DRIVER_BASE_DIR%/lib/commons-net-0.0.1-SNAPSHOT.jar
set DRIVER_CLASSPATH=%DRIVER_BASE_DIR%/@experiment.name@Driver.jar;%DRIVER_BASE_DIR%/ELabHardwareServer.jar;%DRIVER_BASE_DIR%/ReCHardwareServer.jar;%DRIVER_BASE_DIR%/ReCCommon.jar
set DRIVER_EXPERIMENT_CLASSPATH=

REM set DEBUG=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60001,suspend=n 

set BOOTCLASSPATH=-Xbootclasspath/p:%DRIVER_BASE_DIR%/lib/openorb_orb_omg-1.4.0.jar

echo --------------------------------------------------------------------------------
echo BootClassPath    : %BOOTCLASSPATH%
echo --------------------------------------------------------------------------------
echo ClassPath        : %RECCLASSPATH%;%DRIVER_CLASSPATH%;%DRIVER_EXPERIMENT_CLASSPATH%
echo --------------------------------------------------------------------------------
echo System Properties: %GENERIC_ORB_SYSPROPS% %DRIVER_ORB_SYSPROPS% %LOG_SYSPROPS% %MEM_SYSPROPS% %TOOLKIT_SYSPROPS% 
echo --------------------------------------------------------------------------------


java %BOOTCLASSPATH% -classpath %RECCLASSPATH%;%DRIVER_CLASSPATH%;%DRIVER_EXPERIMENT_CLASSPATH% %GENERIC_ORB_SYSPROPS% %DRIVER_ORB_SYSPROPS% %LOG_SYSPROPS% %MEM_SYSPROPS% %DRIVER_HARWARE_INFO_SYSPROPS% %TOOLKIT_SYSPROPS% %DEBUG% @driver.main.class@ &
