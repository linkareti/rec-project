#!/bin/bash

url="jdbc:mysql://${DB_HOST}:${DB_PORT}/${TARGET_DB}?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false"
driver="com.mysql.cj.jdbc.Driver"

if [ "${LOGGING_LEVEL}" == "debug" ]; then
    printf "${BLUE}Applying changes to the database $TARGET_DB, @ $url with credentials ($DB_USER,$DB_PASS) with context $DB_CONTEXT ${NC}\n"
    printf "${BLUE}Changelog file is : $CHANGELOG_FILE ${NC}\n"
fi

if [ "${RESET_CKSUMS}" != "" ]; then
    printf "${BLUE}Resetting checksums${NC}\n"
    liquibase --classpath=${CLASSPATH} --driver=$driver --changeLogFile=$CHANGELOG_FILE --url=$url --username=$DB_USER --password=$DB_PASS --logLevel=$LOGGING_LEVEL --contexts=$DB_CONTEXT clearCheckSums
fi

if [ "${DUMP_SQL}" != "" ]; then
    printf "${BLUE}Dumping SQL file with instructions to run${NC}\n"
    liquibase --classpath=${CLASSPATH} --driver=$driver --changeLogFile=$CHANGELOG_FILE --url=$url --username=$DB_USER --password=$DB_PASS --logLevel=$LOGGING_LEVEL updateSQL
elif [ "${UPDATE_TO_TAG}" != "" ]; then
    printf "${BLUE}Running liquibase update instructions to tag ${UPDATE_TO_TAG} ${NC}\n"
    liquibase --classpath=${CLASSPATH} --driver=$driver --changeLogFile=$CHANGELOG_FILE --url=$url --username=$DB_USER --password=$DB_PASS --logLevel=$LOGGING_LEVEL --contexts=$DB_CONTEXT updateToTag ${UPDATE_TO_TAG}
else

    if [ "${FORCE_UNLOCK}" != "" ]; then
        printf "${BLUE}Force liquibase database unlock${NC}\n"
        liquibase --classpath=${CLASSPATH} --driver=$driver --changeLogFile=$CHANGELOG_FILE --url=$url --username=$DB_USER --password=$DB_PASS --logLevel=$LOGGING_LEVEL releaseLocks
    fi

    printf "${BLUE}Running liquibase update instructions${NC}\n"
    liquibase --classpath=${CLASSPATH} --driver=$driver --changeLogFile=$CHANGELOG_FILE --url=$url --username=$DB_USER --password=$DB_PASS --logLevel=$LOGGING_LEVEL --contexts=$DB_CONTEXT update
fi