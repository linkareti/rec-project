#FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

#COPY resources/*.zip /tmp/

#RUN mkdir -p /tmp/hardwareserver \
#             /tmp/multicast \
#    && unzip /tmp/vtiroHardwareServer_11-12-2020.zip -d /tmp/hardwareserver/ \
#    && unzip /tmp/multicast_basic_11-12-2020.zip -d /tmp/multicast/

FROM docker-thirdparty.linkare.com/payara/server-full:5.2020.3-jdk11

ENV REC_DB_HOST="" \
    REC_DB_PORT="" \
    GFTIMER_DB_HOST="" \
    GFTIMER_DB_PORT="" \
    JVM_OPTIONS="" \
    MULTICAST_HOST="" \
    MULTICAST_PORT=""

# Temporary
#USER root
#RUN yum install -y vim unzip net-tools procps telnet
#USER payara

RUN mkdir -p /opt/payara/multicast/basic/ \
             /opt/payara/hardwareserver/

COPY --chown=payara:payara resources/start.sh ${SCRIPT_DIR}/init_0_start.sh
COPY --chown=payara:payara resources/payara-resources.xml ${PAYARA_DIR}/glassfish/domains/production/config/payara-resources.xml
COPY --chown=payara:payara resources/mysql-connector-java.jar ${PAYARA_DIR}/glassfish/domains/production/lib/mysql-connector-java.jar
COPY --chown=payara:payara resources/rec-web.war ${HOME_DIR}/deployments/rec.web.war
#COPY --chown=payara:payara --from=base /tmp/hardwareserver/ /opt/payara/hardwareserver/
#COPY --chown=payara:payara --from=base /tmp/multicast/ /opt/payara/multicast/basic/
COPY resources/dockerize /usr/local/bin/