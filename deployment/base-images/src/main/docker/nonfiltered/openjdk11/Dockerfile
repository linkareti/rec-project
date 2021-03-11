FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip resources/*.tar.gz /tmp/

RUN mkdir -p /tmp/jdk11 \
    && tar xvfz /tmp/openjdk11.tar.gz -C /tmp/ \
    && mv /tmp/jdk*/* /tmp/jdk11

FROM docker-thirdparty.linkare.com/python:3.9.1-slim-buster
ENV APPNAME="" \
    APPCMD="" \
    APPUSER="" \
    WORKDIR="" \
    JAVA_HOME="/opt/java/openjdk11"

ENV PATH=$PATH:${JAVA_HOME}/bin

COPY --from=base /tmp/jdk11/ /opt/java/openjdk11/

COPY resources/start.sh /docker-entrypoint.sh
COPY resources/dockerize /usr/local/bin/
COPY resources/app.conf.j2 /templates/

RUN apt-get update \
    && apt-get install --no-install-recommends -y unzip procps vim net-tools telnet \
    && pip3 --no-cache-dir install supervisor \
    && rm -rf /var/lib/apt/lists/*

RUN chmod +x /docker-entrypoint.sh

RUN useradd -m elab

COPY resources/supervisord.conf /etc/supervisor/supervisord.conf

VOLUME [ "/templates" ]
VOLUME [ "/etc/supervisor/conf.d" ]

ENTRYPOINT [ "/docker-entrypoint.sh"]
