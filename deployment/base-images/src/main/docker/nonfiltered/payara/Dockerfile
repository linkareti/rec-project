FROM docker-thirdparty.linkare.com/payara/server-full:5.2020.3-jdk11

ENV REC_DB_HOST="" \
    REC_DB_PORT="" \
    GFTIMER_DB_HOST="" \
    GFTIMER_DB_PORT="" \
    JVM_OPTIONS=""

# Temporary
USER root
RUN apt update && apt install -y vim unzip net-tools procps telnet
USER payara

COPY resources/start.sh ${SCRIPT_DIR}/init.d/start.sh
COPY resources/payara-resources.xml ${PAYARA_DIR}/glassfish/domains/production/config/payara-resources.xml
COPY resources/mysql-connector-java.jar /opt/payara/appserver/glassfish/domains/production/lib/mysql-connector-java.jar