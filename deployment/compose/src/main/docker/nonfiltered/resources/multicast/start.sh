#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

cmd="dockerize "
PWD="$( cd "$( dirname "$0" )" && pwd )"
RETVAL=0
multicast_server_filepath="${PWD}/multicast.zip"
#multicast_server_scriptpath="/opt/StartMultiCastController.sh"
#pid_path_name="/opt/process.pid"
#multicast_host="elab-multicast"
#multicast_port="9001"
#cmd="${cmd} ${multicast_server_scriptpath}"

if [ -f "${multicast_server_filepath}" ]; then
    printf "${BLUE}Unzipping ${multicast_server_filepath}${NC}\n"
    unzip ${multicast_server_filepath} -d /opt/
else
    printf "${RED}File ${multicast_server_filepath} not found${NC}\n"
    exit 1
fi

pushd /opt

for dir in {basic,intermediate,advanced}; do
    if [ -d ${dir} ]; then
        APPNAME="${dir}"
        APPCMD="/opt/${dir}/StartMultiCastController.sh"
        APPUSER="root"

        dockerize -template /templates/app.conf.j2:/etc/supervisor/conf.d/${dir}.conf

        pushd ${dir}

        # FIXME - Workaround until it is fixed in project!
        printf "${YELLOW}FIXME - Fixing multicast MULTICAST_BASE_DIR, Fix it in original code${NC}\n"
        sed -i "s/^export MULTICAST_BASE_DIR=.*/export MULTICAST_BASE_DIR=\"\/opt\/${dir}\/multicast\"/g" StartMultiCastController.sh

        printf "${YELLOW}FIXME - Fixing multicast BOOTCLASSPATH, Fix it in original code${NC}\n"
        sed -i 's/export BOOTCLASSPATH=-Xbootclasspath.*/export BOOTCLASSPATH="--illegal-access=permit "/g' StartMultiCastController.sh

        printf "${YELLOW}FIXME - Fixing multicast MULTICAST_CLASSPATH, Fix it in original code${NC}\n"
        sed -i '/^export MULTICAST_CLASSPATH=/ s/$/:$MULTICAST_BASE_DIR\/lib\/openorb_orb_omg-1.4.0.jar/' StartMultiCastController.sh

        #./StartMultiCastController.sh

        popd
    fi
done

popd

#sleep infinity

exit ${RETVAL}