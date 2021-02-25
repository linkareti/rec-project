#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

cmd="dockerize "
PWD="$( cd "$( dirname "$0" )" && pwd )"
RETVAL=0
hardware_server_filepath="${PWD}/hardwareserver.zip"
#multicast_url="elab-multicast:9001"
#cmd="${cmd} -wait tcp://${multicast_url} -timeout 240s"
#cmd="${cmd} ${hardware_server_scriptpath}"

if [ -f "${hardware_server_filepath}" ]; then
    printf "${BLUE}Unzipping ${hardware_server_filepath}${NC}\n"
    unzip ${hardware_server_filepath} -d /opt/
else
    printf "${RED}File ${hardware_server_filepath} not found${NC}\n"
    exit 1
fi

#${cmd}

pushd /opt

for zipfile in *.zip ; do
    dirname=`echo ${zipfile} | cut -d '_' -f 1`
    if [ ! -d /opt/${dirname} ]; then
        mkdir -p /opt/${dirname}
    fi
    unzip ${zipfile} -d /opt/${dirname}/


    pushd /opt/${dirname}/

    for script in Start*.sh ; do

        APPNAME="${dirname}"
        APPCMD="/opt/${dirname}/${script}"
        APPUSER="root"
        WORKDIR="/opt/${dirname}/"

        dockerize -template /templates/app.conf.j2:/etc/supervisor/conf.d/${dirname}.conf

        # FIXME - Workaround until it is fixed in project!
        printf "${YELLOW}FIXME - Fixing hardwareServer BOOTCLASSPATH at ${script}, Fix it in original code${NC}\n"
        sed -i 's/export BOOTCLASSPATH=-Xbootclasspath.*/export BOOTCLASSPATH="--illegal-access=permit "/g' ${script}

        printf "${YELLOW}FIXME - Fixing hardwareServer DRIVER_CLASSPATH at ${script}, Fix it in original code${NC}\n"
        sed -i '/^export DRIVER_CLASSPATH=/ s/$/:$DRIVER_BASE_DIR\/lib\/openorb_orb_omg-1.4.0.jar/' ${script}

        #printf "${BLUE}Starting ${script}${NC}\n"
        #./${script}
    done
    popd
done
popd

#printf "${BLUE}Sleeping until hardware server processes finishes${NC}\n"
#sleep infinity

exit ${RETVAL}