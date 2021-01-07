FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip resources/*.tar.gz /tmp/

RUN mkdir -p /tmp/rollpaper \
             /tmp/jarfiles \
             /tmp/javafx/lib \
             /tmp/webswing \
    && unzip /tmp/client.zip -d /tmp/ \
    && unzip /tmp/jarfiles.zip -d /tmp/ \
    && unzip /tmp/javafx-sdk.zip -d /tmp/ \
    && unzip /tmp/webswing.zip -d /tmp/ \
    && mv /tmp/javafx-sdk*/lib/ /tmp/javafx/lib/ \
    && mv /tmp/webswing-examples*/* /tmp/webswing/

FROM docker-thirdparty.linkare.com/openjdk:11.0.9.1-jdk-buster

ENV WEBSWING_JAVA_HOME=/usr/local/openjdk-11 \
    WEBSWING_JAVA_OPTS="-Xmx128M" \
    WEBSWING_HOME=/opt/webswing \
    DISPLAY=:99
    
ENV WEBSWING_OPTS="-h 0.0.0.0 -j $WEBSWING_HOME/jetty.properties -c $WEBSWING_HOME/webswing.config"


# Copy webswing application
COPY --from=base \
    /tmp/webswing/webswing-server.war \
    /tmp/webswing/webswing.sh \
    /tmp/webswing/webswing.config \
    /tmp/webswing/jetty.properties \
    /tmp/webswing/api/ \
    /tmp/webswing/fonts/ \
    /tmp/webswing/lang \
    /tmp/webswing/ssl/ /opt/webswing/
COPY --from=base /tmp/webswing/apps/webapps/admin/ /opt/webswing/apps/webapps/admin/
COPY resources/webswing.config /opt/webswing/
COPY resources/webswing.sh /opt/webswing/
COPY resources/default.xml /opt/webswing/rollpaper/etc/config/
COPY --from=base /tmp/client/ /opt/webswing/rollpaper/
COPY --from=base /tmp/jarfiles/ /opt/webswing/jarfiles/
COPY --from=base /tmp/javafx/lib/ /opt/webswing/javafx/
COPY resources/start.sh /opt/webswing/
COPY resources/dockerize /usr/local/bin/

RUN apt-get update \
    && apt-get install --no-install-recommends -y xauth xvfb libxrender1 libxtst6 libxext6 fontconfig vlc vim procps net-tools dbus-x11 \
    && rm -rf /var/lib/apt/lists/*

RUN useradd --create-home webswing \
    && mkdir -p /opt/webswing/.cache/xdgr \
                /opt/webswing/logs \
    && chown -R webswing:webswing /opt/webswing \
    && chmod +x /opt/webswing/webswing.sh \
                /opt/webswing/start.sh
    
WORKDIR /opt/webswing

USER webswing

EXPOSE 8080 8443 8000

#-Djava.util.logging.config.file=/opt/webswing/rollpaper/etc/loggers.config.properties

CMD [ "/opt/webswing/start.sh" ]





