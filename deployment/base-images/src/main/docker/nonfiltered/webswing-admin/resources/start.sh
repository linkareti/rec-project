#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

# usage: file_env VAR [DEFAULT]
#    ie: file_env 'XYZ_DB_PASSWORD' 'example'
# (will allow for "$XYZ_DB_PASSWORD_FILE" to fill in the value of
#  "$XYZ_DB_PASSWORD" from a file, especially for Docker's secrets feature)
file_env() {
    local var="$1"
    local fileVar="${var}_FILE"
    local def="${2:-}"
    if [[ ${!var:-} && ${!fileVar:-} ]]; then
        echo >&2 "error: both $var and $fileVar are set (but are exclusive)"
        exit 1
    fi
    local val="$def"
    if [[ ${!var:-} ]]; then
        val="${!var}"
    elif [[ ${!fileVar:-} ]]; then
        val="$(< "${!fileVar}")"
    fi

    if [[ -n $val ]]; then
        export "$var"="$val"
    fi

    unset "$fileVar"
}

file_env 'WEBSWING_SECRET'

cmd="dockerize "

if [ "${WEBSWING_HOST}" != "" -a "${WEBSWING_PORT}" != "" ]; then
    cmd="$cmd -wait tcp://${WEBSWING_HOST}:${WEBSWING_PORT} -timeout 240s"
fi

if [ "${WEBSWING_HOST}" != "" -a "${WEBSWING_WEBSOCKET_PORT}" != "" ]; then
    cmd="$cmd -wait tcp://${WEBSWING_HOST}:${WEBSWING_WEBSOCKET_PORT} -timeout 240s"
fi

if [ -f "/templates/webswing-admin.properties.j2" ]; then
    printf "${BLUE}Templating /templates/webswing-admin.properties.j2 into /opt/webswing/webswing-admin.properties${NC}\n"
    cmd="${cmd} -template /templates/webswing-admin.properties.j2:/opt/webswing/webswing-admin.properties"
fi

cmd="${cmd} /opt/webswing/admin.sh"

${cmd}