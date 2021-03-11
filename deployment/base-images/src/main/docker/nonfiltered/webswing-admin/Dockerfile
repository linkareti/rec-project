# Only usable in webswing 20.2.X (Our application does not work with this version)

FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip /tmp/

RUN mkdir -p /tmp/admin \
    && unzip /tmp/webswing.zip -d /tmp/ \
    && mv /tmp/webswing-examples*/admin/* /tmp/admin/ \
    && mv /tmp/webswing-examples*/ssl /tmp/admin/

FROM docker-thirdparty.linkare.com/openjdk:11.0.9.1-jre-buster

ENV WEBSWING_HOST="" \
    WEBSWING_PORT="" \
    WEBSWING_WEBSOCKET_PORT="" \
    WEBSWING_SHARED_LOG_DIR="" \
    WEBSWING_PUBLIC_URL="" \
    WEBSWING_SECRET=tTDyp4uKbbN6qhjyJ5iBuM8aZWs8aXtn5vwLfOHi3cPkujpDJPoYlo3ChCmhZKjvQ1mZeKefaObuSHOQaB9zBmrmfoyA4ctP20k25sS8emvlSOXv8vFWVFJrxrke0kC6

COPY --from=base /tmp/admin/ /opt/webswing/
COPY resources/webswing-admin.properties.j2 /templates/
COPY resources/jetty.properties /opt/webswing/
COPY resources/start.sh /opt/webswing/
COPY resources/dockerize /usr/local/bin/
COPY resources/admin.sh /opt/webswing/

RUN useradd --create-home webswing \
    && chown -R webswing:webswing /opt/webswing \
    && chmod +x /opt/webswing/admin.sh \
                /opt/webswing/start.sh
    
USER webswing

EXPOSE 8080 8443

WORKDIR /opt/webswing

CMD [ "/opt/webswing/start.sh" ]