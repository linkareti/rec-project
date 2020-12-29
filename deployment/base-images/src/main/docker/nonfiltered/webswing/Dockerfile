FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip /tmp/

RUN mkdir -p /tmp/rollpaper \
             /tmp/jarfiles \
    && unzip /tmp/client.zip -d /tmp/ \
    && unzip /tmp/jarfiles.zip -d /tmp/

FROM docker-thirdparty.linkare.com/webswing/webswing-se:2.7.3_jre11

COPY resources/webswing.config /opt/webswing/
COPY resources/jetty.properties /opt/webswing/
COPY resources/default.xml /opt/webswing/rollpaper/etc/config/
COPY --from=base /tmp/client/ /opt/webswing/rollpaper/
COPY --from=base /tmp/jarfiles/ /opt/webswing/jarfiles/

#FIXME - Workaround for network_mode: "host"
#RUN sed -i 's/localhost/elab-multicast/g' /opt/webswing/rollpaper/etc/ReCFaceConfig.xml

EXPOSE 8080

#-Djava.util.logging.config.file=/opt/webswing/rollpaper/loggers.config.properties