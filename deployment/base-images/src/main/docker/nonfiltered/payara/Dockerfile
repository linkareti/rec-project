FROM docker-thirdparty.linkare.com/payara/server-full:5.2020.3-jdk11
#FROM docker-thirdparty.linkare.com/payara/micro:5.2020.7-jdk11

ENV REC_DB_HOST="" \
    REC_DB_PORT="" \
    GFTIMER_DB_HOST="" \
    GFTIMER_DB_PORT="" \
    JVM_OPTIONS="" \
    CONTAINERS_MULTICAST="" \
    DEVELOPMENT_MODE="" \
    DEBUG=""

COPY --chown=payara:payara resources/start.sh ${SCRIPT_DIR}/init_0_start.sh
COPY --chown=payara:payara resources/payara-resources.xml ${PAYARA_DIR}/glassfish/domains/production/config/payara-resources.xml
COPY --chown=payara:payara resources/mysql-connector-java.jar ${PAYARA_DIR}/glassfish/domains/production/lib/mysql-connector-java.jar
COPY resources/dockerize /usr/local/bin/

EXPOSE 9009 4848 8080

VOLUME [ "/autodeploy" ]