#!/bin/bash
#Copying tamplate project to new project folder
if [[ ( ! $1 ) || ( ! $2 ) || ( ! $3 ) || ( ! $4 )]]; then
	echo "Usage:"
	echo ""
	echo "Call the script using the SDP name of the Project in first place,the Alias in second place, in third ID and the location of the pendulum to finish , to be analysed, as arguments."
	echo ""
	echo "Thank You!"
	echo "Bye....."
	echo ""
	exit 0;
fi

echo "--------------------------------------------------------"
#$1 SDP NAME /$2 Alias  /$3 ID /$4 location
class="$(tr '[:lower:]' '[:upper:]' <<< ${2:0:1})${2:1}"

cp ./worldpendulum_2 ./$2 -r
find $2 -name 'worldpendulum' -exec rename 's/worldpendulum/'$2'/g' {} + > /dev/null 2>&1
#find $1 -iname '*world*pendulum*' -exec rename 's/world*pendulum*/'$2'/g' {} + > /dev/null 2>&1
#find $1 -iname '*world_pendulum*' -exec rename 's/world_pendulum*/'$2'/g' {} + > /dev/null 2>&1
#find $1 -iname '*worldpendulum*' -exec rename 's/*worldpendulum*/'$2'/g' {} + > /dev/null 2>&1


find $2 -type f -exec sed -i 's/worldpendulum_ccvalg.sdp/'$1'/g' {} + > /dev/null 2>&1
find $2 -type f -exec sed -i 's/worldpendulum/'$2'/g' {} + > /dev/null 2>&1
find $2 -type f -exec sed -i 's/ELAB_WORLD_PENDULUM_CCVALG/'$3'/g' {} + > /dev/null 2>&1
sed -i 's/Faro/'$4'/g' ./$2/src/java/client/pt/utl/ist/elab/client/$2/resources/messages_en.properties
sed -i 's/Faro/'$4'/g' ./$2/src/java/client/pt/utl/ist/elab/client/$2/resources/messages.properties
sed -i 's/Faro/'$4'/g' ./$2/src/java/server/pt/utl/ist/elab/driver/$2/HardwareInfo.xml