#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[1;34m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color
#Create experiment based on template

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
experiment_name=$1
experiment_id=$2
experiment_location=$3
experiment_video=$4
experiment_serial_device=${5:-/dev/ttyS0}

if [[ ( ! $experiment_name ) || ( ! $experiment_id ) || ( ! $experiment_location ) ]]; then
	echo "Usage:"
	echo ""
	echo "./$(basename "$0") <experiment name> <experiment ID> <Location> <Video Location>"
	echo ""
	echo "experiment name - Name of the experiment (e.g. worldpendulum)"
	echo "experiment ID - ID of the experiment (e.g. ELAB_WORLD_PENDULUM_CCVALG)"
	echo "Location - Pendulum location (e.g. Faro)"
	echo "(optional) Video Location - URL to the video (e.g. rtsp://elabmc.ist.utl.pt:80/wp_ccvalg.sdp)"
	echo "(optional) Serial Device - Pendulum serial device location at the server (e.g. /dev/ttyS0)"
	echo ""
	echo "Thank You!"
	echo "Bye....."
	echo ""
	exit 0;
fi

echo "--------------------------------------------------------"

pushd ${DIR}
# Create structure based on template project
printf "${BLUE}Creating ${experiment_name} based on wptemplate\n${NC}"
cp -r ./wptemplate ${experiment_name}
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while creating ${experiment_name}\n${NC}"
	exit 1
else
	printf "${GREEN}Directory created sucessfully\n${NC}"
fi

# Replace needed experiment pattern
printf "${BLUE}Replacing patterns at template files\n${NC}"
find ${experiment_name} -name 'worldpendulum' -exec rename 's/worldpendulum/'${experiment_name}'/g' {} + > /dev/null 2>&1

# Replace directory names
for dir in $(find $experiment_name -type d -name worldpendulum); do
	printf "${BLUE}Renaming $dir into $(dirname $dir)/${experiment_name}\n${NC}"
	mv $dir $(dirname $dir)/${experiment_name}
	if [ $? -ne 0 ]; then
		printf "${RED}An error occured while renaming directories\n${NC}"
		exit 1
	else
		printf "${GREEN}Directory renamed sucessfully\n${NC}"
	fi
done

if [ -z ${experiment_video} ]; then
	printf "${YELLOW}######### WARNING #########\n${NC}"
	printf "${YELLOW}Video URL is not defined\n${NC}"
	printf "${YELLOW}url.video property is not going to be set\n${NC}"
	printf "${YELLOW}Keep this information for future reference\n${NC}"
	printf "${YELLOW}######### WARNING #########\n${NC}"
	find ${experiment_name} -type f -exec sed -i 's#--experiment_video_url--##g' {} + > /dev/null 2>&1
	if [ $? -ne 0 ]; then
		printf "${RED}An error occured while replacing patterns\n${NC}"
		exit 1
	else
		printf "${GREEN}Patterns replaced successfully\n${NC}"
	fi
else
	printf "${BLUE}Replacing video URL to $experiment_video\n${NC}"
	find ${experiment_name} -type f -exec sed -i 's#--experiment_video_url--#'$experiment_video'#g' {} + > /dev/null 2>&1
	if [ $? -ne 0 ]; then
		printf "${RED}An error occured while replacing patterns\n${NC}"
		exit 1
	else
		printf "${GREEN}Patterns replaced successfully\n${NC}"
	fi
fi

printf "${BLUE}Setting serial device location to ${experiment_serial_device}\n${NC}"
find ${experiment_name} -type f -exec sed -i 's#--experiment_serial_device--#'$experiment_serial_device'#g' {} + > /dev/null 2>&1
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while replacing templated location\n${NC}"
	exit 1
else
	printf "${GREEN}Template serial device location replaced successfully\n${NC}"
fi

printf "${BLUE}Replacing experiment name in files to ${experiment_name}\n${NC}"
find ${experiment_name} -type f -exec sed -i 's/worldpendulum/'$experiment_name'/g' {} + > /dev/null 2>&1
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while replacing patterns\n${NC}"
	exit 1
else
	printf "${GREEN}Patterns replaced successfully\n${NC}"
fi

printf "${BLUE}Replacing experiment ID in files to $experiment_id\n${NC}"
find ${experiment_name} -type f -exec sed -i 's#--experiment_id--#'$experiment_id'#g' {} + > /dev/null 2>&1
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while replacing patterns\n${NC}"
	exit 1
else
	printf "${GREEN}Patterns replaced successfully\n${NC}"
fi

printf "${BLUE}Replacing experiment location in files to ${experiment_location}\n${NC}"
find ${experiment_name} -type f -exec sed -i 's#--experiment_location--#'$experiment_location'#g' {} + > /dev/null 2>&1
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while replacing patterns\n${NC}"
	exit 1
else
	printf "${GREEN}Patterns replaced successfully\n${NC}"
fi

pushd ${experiment_name}/src/java/

printf "${BLUE}Renaming worldpendulum_background.jpg\n${NC}"
mv client/pt/utl/ist/elab/client/${experiment_name}/resources/worldpendulum_background.jpg client/pt/utl/ist/elab/client/${experiment_name}/resources/${experiment_name}_background.jpg
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while renaming file\n${NC}"
	exit 1
else
	printf "${GREEN}File renamed sucessfully\n${NC}"
fi

printf "${BLUE}Renaming worldpendulum_iconified.jpg\n${NC}"
mv client/pt/utl/ist/elab/client/${experiment_name}/resources/worldpendulum_iconified.jpg client/pt/utl/ist/elab/client/${experiment_name}/resources/${experiment_name}_iconified.jpg
if [ $? -ne 0 ]; then
	printf "${RED}An error occured while renaming file\n${NC}"
	exit 1
else
	printf "${GREEN}File renamed sucessfully\n${NC}"
fi

popd