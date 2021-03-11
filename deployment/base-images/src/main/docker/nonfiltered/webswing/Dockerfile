FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip resources/*.tar.gz /tmp/

RUN mkdir -p /tmp/webswing \
    && unzip /tmp/webswing.zip -d /tmp/ \
    && mv /tmp/webswing-*/* /tmp/webswing/

# FROM docker-thirdparty.linkare.com/debian:buster-slim as vlc-sources

# WORKDIR /

# # Custom package - org.videolan.vlc - vlc-slim_x86_64_linux
# # Check VLC-README.MD
# COPY resources/vlc-slim.tar.gz /tmp/

# # Removing plugins.dat to solve stale cache issue
# # main libvlc error: stale plugins cache: modified /usr/lib/x86_64-linux-gnu/vlc/plugins/*
# RUN mkdir -p /tmp/vlc-slim \
#     && tar xvfz /tmp/vlc-slim.tar.gz -C /tmp/vlc-slim/ \
#     && rm -f /tmp/vlc-slim/usr/lib/x86_64-linux-gnu/vlc/plugins/plugins.dat

FROM docker-thirdparty.linkare.com/openjdk:11.0.9.1-jdk-slim-buster

ENV WEBSWING_JAVA_HOME=/usr/local/openjdk-11 \
    WEBSWING_JAVA_OPTS="-Xmx128M" \
    WEBSWING_HOME=/opt/webswing \
    DISPLAY=:99 \
    WEBSWING_SECRET=tTDyp4uKbbN6qhjyJ5iBuM8aZWs8aXtn5vwLfOHi3cPkujpDJPoYlo3ChCmhZKjvQ1mZeKefaObuSHOQaB9zBmrmfoyA4ctP20k25sS8emvlSOXv8vFWVFJrxrke0kC6

    
ENV WEBSWING_OPTS="-h 0.0.0.0 -j $WEBSWING_HOME/jetty.properties -c $WEBSWING_HOME/webswing.config"

# Copy webswing application
COPY --from=base \
    /tmp/webswing/webswing-server.war \
    /tmp/webswing/webswing.sh \
    /tmp/webswing/webswing.config \
    /tmp/webswing/jetty.properties /opt/webswing/
COPY --from=base /tmp/webswing/api/ /opt/webswing/api/
COPY --from=base /tmp/webswing/fonts/ /opt/webswing/fonts/
COPY --from=base /tmp/webswing/lang/ /opt/webswing/lang/
#COPY --from=base /tmp/webswing/ssl/ /opt/webswing/ssl/
#COPY --from=base /tmp/webswing/apps/webapps/admin/ /opt/webswing/apps/webapps/admin/
COPY resources/webswing.sh /opt/webswing/
COPY resources/default.xml /opt/webswing/openorb/etc/config/
COPY resources/start.sh /opt/webswing/
COPY resources/dockerize /usr/local/bin/
COPY resources/webswing.properties.j2 /templates/

# Unpack (custom made) VLC slim and its dependencies
# COPY --from=vlc-sources /tmp/vlc-slim/lib/x86_64-linux-gnu/ /lib/x86_64-linux-gnu/
# COPY --from=vlc-sources /tmp/vlc-slim/usr/bin/vlc /usr/bin/vlc
# COPY --from=vlc-sources /tmp/vlc-slim/usr/lib/x86_64-linux-gnu /usr/lib/x86_64-linux-gnu/
# COPY --from=vlc-sources /tmp/vlc-slim/usr/share/vlc/ /usr/share/vlc/

RUN useradd --create-home webswing \
    && mkdir -p /opt/webswing/.cache/xdgr \
                /opt/webswing/logs \
    && chown -R webswing:webswing /opt/webswing \
    && chmod +x /opt/webswing/webswing.sh \
                /opt/webswing/start.sh
    
WORKDIR /opt/webswing

EXPOSE 8080 8443 8000

#-Djava.util.logging.config.file=/opt/webswing/rollpaper/etc/loggers.config.properties

CMD [ "/opt/webswing/start.sh" ]

RUN apt-get update \
    && apt-get install --no-install-suggests --no-install-recommends -y vlc xauth xvfb libxrender1 libxtst6 libxext6 fontconfig vim procps net-tools dbus-x11 \
    && rm -rf /var/lib/apt/lists/*

USER webswing