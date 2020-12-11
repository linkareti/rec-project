FROM docker-thirdparty.linkare.com/openjdk:11.0.8-jdk-buster

# TODO - To be used with multicast server

COPY resources/start.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

VOLUME [ "/startup.d"]

ENTRYPOINT [ "/docker-entrypoint.sh"]