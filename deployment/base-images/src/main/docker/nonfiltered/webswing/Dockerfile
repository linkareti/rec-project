FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip resources/*.tar.gz /tmp/

RUN mkdir -p /tmp/webswing \
    && unzip /tmp/webswing.zip -d /tmp/ \
    && mv /tmp/webswing-examples*/* /tmp/webswing/

# FROM docker-thirdparty.linkare.com/debian:buster-slim as vlc-sources

# RUN mkdir -p /tmp/vlc-libs/lib \
#              /tmp/vlc-libs/usr \
#              /tmp/vlc-libs/lib64 \
#     && apt-get update \
#     && apt-get install --no-install-recommends -y vlc

# # To find binaries needed use 'ldd /usr/bin/vlc'
# RUN cp /usr/lib/x86_64-linux-gnu/libvlc.so.5 \
#     /usr/lib/x86_64-linux-gnu/libvlccore.so.9 \
#     /usr/lib/x86_64-linux-gnu/liblz4.so.1 /tmp/vlc-libs/usr/ \
#     && cp /lib/x86_64-linux-gnu/libpthread.so.0 \ 
#     /lib/x86_64-linux-gnu/libdl.so.2 \
#     /lib/x86_64-linux-gnu/libc.so.6 \
#     /lib/x86_64-linux-gnu/libm.so.6 \
#     /lib/x86_64-linux-gnu/libidn.so.11 \
#     /lib/x86_64-linux-gnu/libdbus-1.so.3 \
#     /lib/x86_64-linux-gnu/libsystemd.so.0 \
#     /lib/x86_64-linux-gnu/librt.so.1 \
#     /lib/x86_64-linux-gnu/liblzma.so.5 \
#     /lib/x86_64-linux-gnu/libgcrypt.so.20 \
#     /lib/x86_64-linux-gnu/libgpg-error.so.0 /tmp/vlc-libs/lib/ \
#     && cp /lib64/ld-linux-x86-64.so.2 /tmp/vlc-libs/lib64/

#FROM docker-thirdparty.linkare.com/openjdk:11.0.9.1-jdk-buster
FROM docker-thirdparty.linkare.com/openjdk:11.0.9.1-jdk-slim-buster

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
    /tmp/webswing/jetty.properties /opt/webswing/
COPY --from=base /tmp/webswing/api/ /opt/webswing/api/
COPY --from=base /tmp/webswing/fonts/ /opt/webswing/fonts/
COPY --from=base /tmp/webswing/lang/ /opt/webswing/lang/
COPY --from=base /tmp/webswing/ssl/ /opt/webswing/ssl/
COPY --from=base /tmp/webswing/apps/webapps/admin/ /opt/webswing/apps/webapps/admin/
COPY resources/webswing.sh /opt/webswing/
COPY resources/default.xml /opt/webswing/openorb/etc/config/
COPY resources/start.sh /opt/webswing/
COPY resources/dockerize /usr/local/bin/

#COPY --from=vlc-sources /usr/bin/vlc /usr/bin/
#COPY --from=vlc-sources /usr/lib/x86_64-linux-gnu/vlc/ /usr/lib/x86_64-linux-gnu/vlc/
#COPY --from=vlc-sources /usr/share/vlc/ /usr/share/vlc/

#COPY --from=vlc-sources /tmp/vlc-libs/lib /lib/x86_64-linux-gnu/
#COPY --from=vlc-sources /tmp/vlc-libs/usr /usr/lib/x86_64-linux-gnu/
#COPY --from=vlc-sources /tmp/vlc-libs/lib64 /lib64/

RUN apt-get update \
    && apt-get install --no-install-recommends -y vlc xauth xvfb libxrender1 libxtst6 libxext6 fontconfig vim procps net-tools dbus-x11 \
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





