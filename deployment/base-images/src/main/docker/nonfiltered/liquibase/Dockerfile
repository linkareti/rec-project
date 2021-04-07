FROM docker-thirdparty.linkare.com/openjdk:8u242-jdk-slim-buster

ENV TZ=Europe/Lisbon \
    TARGET_DB="" \
    DB_HOST="" \
    DB_PORT="" \
    DB_USER="" \
    DB_PASS_FILE="" \
    DB_CONTEXT=dev \
    DUMP_SQL="" \
    FORCE_UNLOCK="" \
    RESET_CKSUMS="" \
    LOGGING_LEVEL=info \
    CLASSPATH=/changelog \
    CHANGELOG_FILE=db.changelog-master.xml \
    UPDATE_TO_TAG=""

#
# Setting up the timezone
#
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ >/etc/timezone

# Create liquibase user
RUN groupadd --gid 10000 liquibase \
    && useradd --shell /bin/false \
        --uid 10000 \
        --gid 10000 \
        --expiredate 1 \
        liquibase

# Copy command scripts
COPY --chown=10000:10000 resources/*.sh /scripts/

# Add dockerize binary
COPY resources/dockerize /usr/local/bin/

# Get liquibase from base image
COPY --chown=10000:10000 resources/liquibase/ /opt/liquibase/

# Create directories needed and give right permissions
RUN mkdir -p /changelog/diff \
    && ln -s /opt/liquibase/liquibase /usr/local/bin/ \
    && chmod +x /opt/liquibase/liquibase \
    && chmod -R +x /scripts

# Get JDBC driver
COPY --chown=10000:10000 resources/mysql-connector-java.jar /opt/liquibase/lib/mysql-connector-java.jar

WORKDIR /opt/liquibase

USER liquibase

ENTRYPOINT [ "/scripts/startup.sh" ]