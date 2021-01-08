FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip resources/*.tar.gz /tmp/

RUN mkdir -p /tmp/javafx/lib \
             /tmp/client_final/lib \
    && unzip /tmp/client.zip -d /tmp/ \
    && unzip /tmp/javafx-sdk.zip -d /tmp/ \
    && mv /tmp/javafx-sdk*/lib/ /tmp/javafx/lib/ \
    && mv /tmp/client/lib/*_sig.jar /tmp/client_final/lib/ \
    && mv /tmp/client/*_sig.jar /tmp/client_final/ \
    && mv /tmp/client/images/ /tmp/client_final/

FROM ${repo}/${simplifiedGroupId}/base-images/webswing-${webswing.version}-${openjdk.version}:${project.version}

COPY --chown=webswing:webswing resources/webswing.config /opt/webswing/
COPY --chown=webswing:webswing --from=base /tmp/client_final/ /opt/webswing/apps/webapps/elab/
COPY --chown=webswing:webswing resources/common/*.jar /opt/webswing/apps/webapps/elab/common/lib/
COPY --chown=webswing:webswing --from=base /tmp/javafx/lib/ /opt/webswing/apps/webapps/elab/javafx/