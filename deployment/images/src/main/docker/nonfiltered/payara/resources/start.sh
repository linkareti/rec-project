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

if [ "${CONTAINERS_MULTICAST}" != "" ]; then
	for container in ${CONTAINERS_MULTICAST}; do
    cmd="$cmd -wait tcp://${container} -timeout 240s"
	done
fi

if [ "${CONTAINERS_UPGRADE_DB}" != "" ]; then
	for container in ${CONTAINERS_UPGRADE_DB}; do
		while ping -c1 ${container} &>/dev/null; do
	   		printf "${BLUE}Sleeping 1s while waiting for container '${container}' to end!${NC}\n";
	   		sleep 1;
		done
	done
	printf "${GREEN}Containers ${CONTAINERS_UPGRADE_DB} finished...${NC}\n";
fi

if [ -z $PREBOOT_COMMANDS ]; then echo "Variable PREBOOT_COMMANDS is not set."; exit 1; fi
if [ -z $POSTBOOT_COMMANDS ]; then echo "Variable POSTBOOT_COMMANDS is not set."; exit 1; fi

# Create pre and post boot command files if they don't exist
touch $POSTBOOT_COMMANDS
touch $PREBOOT_COMMANDS

ASADMIN_BASE_CMD="${PAYARA_DIR}/bin/asadmin --interactive=false --user ${ADMIN_USER} --passwordfile=${PAYARA_DIR}/../passwordFile"

# Create password aliases
echo 'create-password-alias --passwordfile /run/secrets/as_recdb.secret rec_db' >> $POSTBOOT_COMMANDS
echo 'create-password-alias --passwordfile /run/secrets/as_mail.secret elab_mail' >> $POSTBOOT_COMMANDS
# Create needed resources
echo "add-resources ${PAYARA_DIR}/glassfish/domains/production/config/payara-resources.xml" >> $POSTBOOT_COMMANDS

# Update existent resources
echo 'set resources.jdbc-resource.jdbc/__TimerPool.pool-name=rec' >> $POSTBOOT_COMMANDS
echo 'set resources.mail-resource.mail/mailSession.password=${ALIAS=elab_mail}' >> $POSTBOOT_COMMANDS

if [ "${DEVELOPMENT_MODE}" != "" ]; then
    printf "${BLUE}Running in development mode:${NC}\n"
    printf "${BLUE}Adding auto-deploy to POSTBOOT_COMMANDS${NC}\n"
    echo 'set configs.config.server-config.admin-service.das-config.autodeploy-enabled=true'  >> $POSTBOOT_COMMANDS
    printf "${BLUE}Creating symlink to rec.web.war file${NC}\n"
    ln -sfn /autodeploy/rec.web.war /opt/payara/appserver/glassfish/domains/production/autodeploy/rec.web.war
    if [ $? -ne 0 ]; then
        printf "${RED}An error occured while trying to create symlink${NC}\n"
        exit 1
    else
        printf "${GREEN}Symlink created successfully${NC}\n"
    fi
    printf "${BLUE}Removing default rec.web.war deploy file${NC}\n"
    rm ${HOME_DIR}/deployments/rec.web.war
    if [ $? -ne 0 ]; then
        printf "${RED}An error occured while trying to delete file${NC}\n"
        exit 1
    else
        printf "${GREEN}File deleted successfully${NC}\n"
    fi
fi

# Remove expired certificates
keytool -storepass changeit -list -v -keystore ${PAYARA_DIR}/glassfish/domains/production/config/cacerts.jks | grep 'Alias\|Valid' | awk 'BEGIN { vflen=length("Alias name: "); ulen=length("until: ") } /^Alias name:/ { a=substr($0,vflen+1); } /^Valid from:/ { f=substr($0,vflen+1,index($0, "until: ")-vflen-2) ; u=substr($0,index($0, "until:")+ulen);print "'name';"a",'from';"f",'until';"u"";}' | while read output_line ; do
  cert_alias=$(echo ${output_line} | cut -d ',' -f1 | cut -d ';' -f2)
  expiry=$(echo ${output_line} | cut -d ',' -f3 | cut -d ';' -f2)
  current_date=$(date)
  expiry_epoch=$(date "+%s" -d "${expiry}")
  current_date_epoch=$(date "+%s" -d "${current_date}")
  if [ ${current_date_epoch} -gt ${expiry_epoch} ] ; then
    printf "${BLUE}Deleting expired certificate alias : ${cert_alias} (${expiry})${NC}\n"
    keytool -storepass changeit -delete -noprompt -keystore ${PAYARA_DIR}/glassfish/domains/production/config/cacerts.jks -alias "${cert_alias}"
    if [ $? -ne 0 ]; then
      printf "${RED}An error occured with certificate alias ${cert_alias} deletion${NC}\n"
      RETVAL=1
    else
      printf "${GREEN}Alias ${cert_alias} deleted successfully${NC}\n"
    fi
  fi
done

if [ ${RETVAL} -ne 0 ]; then
  printf "${RED}An error occured with expired certificate alias removal${NC}\n"
  exit 1
fi

${cmd}