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
deployment_dir="/home/elab/rec-deployment"

if [ -f "${multicast_server_filepath}" ]; then
    printf "${BLUE}Unzipping ${multicast_server_filepath}${NC}\n"
    if [ ! -d "${deployment_dir}" ]; then
        mkdir -p ${deployment_dir}
        # FIXME - Add validation
    fi
    unzip ${multicast_server_filepath} -d ${deployment_dir}
    # FIXME - Add validation
    rm -f ${multicast_server_filepath}
    # FIXME - Add validation
else
    printf "${RED}File ${multicast_server_filepath} not found${NC}\n"
    exit 1
fi

pushd ${deployment_dir}

for zip in *.zip; do
    lab_type=$(echo $zip | cut -d '_' -f 2)
    if [ ! -d ${lab_type} ]; then
        mkdir ${lab_type}
        # FIXME - Add validation
    fi

    unzip ${zip} -d ${deployment_dir}/${lab_type}
    # FIXME - Add validation
    rm -f ${zip}
    # FIXME - Add validation

    APPNAME="${lab_type}"
    APPCMD="${deployment_dir}/${lab_type}/StartMultiCastController.sh"
    APPUSER="elab"
    WORKDIR="${deployment_dir}/${lab_type}"

    cmd="${cmd} -template /templates/app.conf.j2:/etc/supervisor/conf.d/${lab_type}.conf"

done

popd

chown -R elab ${deployment_dir}
# FIXME - Add validation

${cmd}

exit ${RETVAL}