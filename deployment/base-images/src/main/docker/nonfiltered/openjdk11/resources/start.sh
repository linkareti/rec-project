#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
NC='\033[0m' # No Color

for file in /startup.d/*.sh; do
    if [ -f "${file}" ]; then
        if [ ! -x "${file}" ]; then
            printf "${BLUE}Adding execution permissions to ${file}${NC}\n"
            chmod +x ${file}
        fi
        printf "${BLUE}Running ${file}${NC}\n"
        bash -c ${file}
    fi
done