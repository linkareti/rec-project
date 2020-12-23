#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

cmd="dockerize "
PWD="$( cd "$( dirname "$0" )" && pwd )"
RETVAL=0
hardware_server_filepath="${PWD}/vtiro.zip"
hardware_server_scriptpath="/opt/StartvtiroDriver.sh"
pid_path_name="/opt/process.pid"
multicast_url="elab-multicast:9001"
cmd="${cmd} -wait tcp://${multicast_url} -timeout 240s"
cmd="${cmd} ${hardware_server_scriptpath}"

if [ -f "${hardware_server_filepath}" ]; then
    printf "${BLUE}Unzipping ${hardware_server_filepath}${NC}\n"
    unzip ${hardware_server_filepath} -d /opt/
else
    printf "${RED}File ${hardware_server_filepath} not found${NC}\n"
    exit 1
fi

# FIXME - Workaround until it is fixed in project!
printf "${YELLOW}FIXME - Fixing hardwareServer BOOTCLASSPATH, Fix it in original code${NC}\n"
sed -i 's/export BOOTCLASSPATH=-Xbootclasspath.*/export BOOTCLASSPATH="--illegal-access=permit "/g' ${hardware_server_scriptpath}

printf "${YELLOW}FIXME - Fixing hardwareServer DRIVER_CLASSPATH, Fix it in original code${NC}\n"
sed -i '/^export DRIVER_CLASSPATH=/ s/$/:$DRIVER_BASE_DIR\/lib\/openorb_orb_omg-1.4.0.jar/' ${hardware_server_scriptpath}

printf "${YELLOW}FIXME - Fixing hardwareServer openorb.xml hostname, Fix it in original code${NC}\n"
sed -i "s/localhost:9001/${multicast_url}/g" /opt/hardwareserver/etc/openorb.xml

pushd /opt

${cmd}

#PID=$(cat $pid_path_name)
#while [ -e /proc/$PID ]
#do
#    echo "Process: $PID is still running" > /dev/null
#    sleep 5
#done

#echo "Process $PID has finished"

popd

sleep infinity

exit ${RETVAL}