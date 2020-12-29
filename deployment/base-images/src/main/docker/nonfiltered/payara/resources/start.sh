#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

cmd="dockerize "

if [ "${REC_DB_HOST}" != "" -a "${REC_DB_PORT}" != "" ]; then
    cmd="$cmd -wait tcp://${REC_DB_HOST}:${REC_DB_PORT} -timeout 240s"
fi

if [ "${GFTIMER_DB_HOST}" != "" -a "${GFTIMER_DB_PORT}" != "" ]; then
    cmd="$cmd -wait tcp://${GFTIMER_DB_HOST}:${GFTIMER_DB_PORT} -timeout 240s"
fi

if [ "${MULTICAST_HOST}" != "" -a "${MULTICAST_PORT}" != "" ]; then
    cmd="$cmd -wait tcp://${MULTICAST_HOST}:${MULTICAST_PORT} -timeout 240s"
fi

if [ -z $PREBOOT_COMMANDS ]; then echo "Variable PREBOOT_COMMANDS is not set."; exit 1; fi
if [ -z $POSTBOOT_COMMANDS ]; then echo "Variable POSTBOOT_COMMANDS is not set."; exit 1; fi

# Create pre and post boot command files if they don't exist
touch $POSTBOOT_COMMANDS
touch $PREBOOT_COMMANDS

ASADMIN_BASE_CMD="${PAYARA_DIR}/bin/asadmin --interactive=false --user ${ADMIN_USER} --passwordfile=${PAYARA_DIR}/../passwordFile"

# Create password aliases
echo 'create-password-alias --passwordfile /run/secrets/as_recdb.secret rec_db' >> $POSTBOOT_COMMANDS
echo 'create-password-alias --passwordfile /run/secrets/as_gftimerdb.secret gftimer_db' >> $POSTBOOT_COMMANDS
echo 'create-password-alias --passwordfile /run/secrets/as_mail.secret elab_mail' >> $POSTBOOT_COMMANDS
# Create needed resources
echo "add-resources ${PAYARA_DIR}/glassfish/domains/production/config/payara-resources.xml" >> $POSTBOOT_COMMANDS

# Update existent resources
# FIXME - Workaround for network_mode: "host"
#echo 'set resources.jdbc-resource.jdbc/__TimerPool.pool-name=timer' >> $POSTBOOT_COMMANDS
echo 'set resources.jdbc-resource.jdbc/__TimerPool.pool-name=rec' >> $POSTBOOT_COMMANDS
echo 'set resources.mail-resource.mail/mailSession.password=${ALIAS=elab_mail}' >> $POSTBOOT_COMMANDS

${cmd}