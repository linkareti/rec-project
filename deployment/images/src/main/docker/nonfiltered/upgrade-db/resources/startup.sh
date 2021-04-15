#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
NC='\033[0m' # No Color

update_script="/scripts/liquibase_update.sh"
cmd="dockerize"

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

file_env 'DB_PASS'

if [ "${DB_HOST}" != "" ]; then 
	cmd="$cmd -wait tcp://${DB_HOST}:${DB_PORT} -timeout 240s"
fi 

if [ -f "${update_script}" ]; then
    cmd="$cmd ${update_script}"
else
   printf "${RED}No upgrade script: %s${NC}\n" "${update_script}"
   exit 1
fi

$cmd