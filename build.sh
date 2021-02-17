#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

ENVIRONMENT=$1
DEBUG=${2:-"false"}

CUR_DIR=$(cd -P -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd -P)

# The file that has the necessary environment properties configuration
TARGET_BUILD_PROPERTIES=build_${ENVIRONMENT}.properties

if [ -z ${ENVIRONMENT} ]; then
    printf "${RED}ENVIRONMENT variable must be set, exiting...${NC}\n"
    exit 1
fi

pushd ${CUR_DIR}

if [ ! -f ${TARGET_BUILD_PROPERTIES} ]; then
    printf "${RED}${TARGET_BUILD_PROPERTIES} must exist, exiting...${NC}\n"
    exit 1
fi

# Create build.properties if not exist
if [ ! -f build.properties ]; then
	if [ -f "build.properties.sample" ]; then
		cp build.properties.sample build.properties
	else
		printf "${RED}build.properties.sample file not found, exiting...${NC}\n"
		exit 1
	fi
fi

# clean build.properties (copied from build.properties.sample)
BUILD_PROPERTIES=build.properties

# If the property exists on build.properties and hasn't the desired configuration we'll replace it with the
# production value, if it doesn't exist we'll add it
while read line; do
	# Obtain the property name on build.properties (the name and the equals, but not the value, ex: "lab.list=")
	PROPERTY_NAME=$(echo "$line" | grep -o "^.*=")
	# See if we have some value for this property and save its exit code
	grep "^$PROPERTY_NAME" $BUILD_PROPERTIES
	PROPERTY_NAME_GREP_EXIT_CODE=$?
	# See if the desired property value already has the desired production configuration
	PROPERTY_VALUE=$(grep "^$line$" $BUILD_PROPERTIES)
	if [ ${DEBUG} != "false" ]; then
		printf "${BLUE}PROPERTY_NAME=${PROPERTY_NAME}\n${NC}"
		printf "${BLUE}PROPERTY_NAME_GREP_EXIT_CODE=${PROPERTY_NAME_GREP_EXIT_CODE}\n${NC}"
		printf "${BLUE}PROPERTY_VALUE=${PROPERTY_VALUE}\n${NC}"
		printf "${BLUE}line=${line}\n${NC}"
	fi
	if [[ $PROPERTY_NAME_GREP_EXIT_CODE -eq 0 && -z $PROPERTY_VALUE ]] ; then
		sed -i "s;^$PROPERTY_NAME.*;$line;" $BUILD_PROPERTIES
	elif [[ $PROPERTY_NAME_GREP_EXIT_CODE -ne 0 ]] ; then
		echo "$line" >> $BUILD_PROPERTIES
	fi
done < $TARGET_BUILD_PROPERTIES

# Run build process
ant -f buildall.xml dist.all
RETVAL=$?
if [ $RETVAL -ne 0 ]; then
	printf "${RED}An error occured at build time${NC}\n"
	exit $RETVAL
fi

# Package rec.web application
wget http://e-lab.ist.utl.pt/moodle/wspp/wsdl_pp.php -P rec.web/src/main/resources

popd