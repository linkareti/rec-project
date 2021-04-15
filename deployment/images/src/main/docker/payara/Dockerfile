FROM ${public.repo}/payara/server-full:${payara.docker.version}

USER root

# FIXME - Binary needed to check other docker container up (upgrade-db)
RUN apt-get update && apt-get install -y \
  iputils-ping \
  && rm -rf /var/lib/apt/lists/*

USER payara

ENV REC_DB_HOST="" \
    REC_DB_PORT="" \
    GFTIMER_DB_HOST="" \
    GFTIMER_DB_PORT="" \
    JVM_OPTIONS="" \
    CONTAINERS_MULTICAST="" \
    DEVELOPMENT_MODE="" \
    DEBUG="" \
    CONTAINERS_UPGRADE_DB=""

COPY --chown=payara:payara resources/start.sh ${SCRIPT_DIR}/init_0_start.sh
COPY --chown=payara:payara resources/payara-resources.xml ${PAYARA_DIR}/glassfish/domains/production/config/payara-resources.xml
COPY --chown=payara:payara resources/mysql-connector-java.jar ${PAYARA_DIR}/glassfish/domains/production/lib/mysql-connector-java.jar
COPY resources/dockerize /usr/local/bin/

EXPOSE 9009 4848 8080

VOLUME [ "/autodeploy" ]

COPY --chown=payara:payara resources/rec.web.war ${HOME_DIR}/deployments/rec.web.war