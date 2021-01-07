FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

COPY resources/*.zip resources/*.tar.gz /tmp/

RUN mkdir -p /tmp/javafx/lib \
    && unzip /tmp/client.zip -d /tmp/ \
    && unzip /tmp/javafx-sdk.zip -d /tmp/ \
    && mv /tmp/javafx-sdk*/lib/ /tmp/javafx/lib/

FROM ${repo}/${simplifiedGroupId}/base-images/webswing-${webswing.version}-${openjdk.version}:${project.version}

COPY --chown=webswing:webswing resources/webswing.config /opt/webswing/
COPY --chown=webswing:webswing --from=base /tmp/client/ /opt/webswing/client/
COPY --chown=webswing:webswing resources/common/*.jar /opt/webswing/common/lib/
COPY --chown=webswing:webswing --from=base /tmp/javafx/lib/ /opt/webswing/javafx/