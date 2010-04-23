clear
echo Starting Driver
cd /home/elab/ReC7.0/driver/eLab/Aleatorio
source ../../../startup/SetProperties

rm ReC7_AleatorioDriver_*

export SYSTEM_PROPS="-Dopenorb.profile=ReCHardware -DReC.MultiCastController.BindName=MultiCastController -DReC.MultiCastController.InitRef=MultiCastController -Djava.util.logging.config.file=loggers.config.properties $SYSTEM_PROPS -DeLab.Aleatorio.HardwareInfo=AleatorioHardwareInfo.xml -Dawt.toolkit=sun.awt.motif.MToolkit"

export RECCLASSPATH=$OUTPUT_BASE_DIR/recbase/ReCHardwareDriver_sig.jar:ElabAleatorioDriver_sig.jar:$RECCLASSPATH

#export ARGS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,address=60001,suspend=n" 

java $ARGS -noverify -classpath $RECCLASSPATH $SYSTEM_PROPS pt.utl.ist.elab.driver.Aleatorio.AleatorioServerMain &
echo $!> /var/lock/rec_aleatorio
