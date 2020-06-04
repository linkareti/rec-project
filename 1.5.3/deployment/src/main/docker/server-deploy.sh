#!/bin/bash

release_tag=$1

script_filename="server-deploy.sh"
remote_filename="client-deploy.sh"
script_dir="/docker/nascimento/scripts"
script_path="${script_dir}/${script_filename}"
remote_path="${script_dir}/${remote_filename}"

if [ -z "${release_tag}" ] ; then
	printf "Usage:\n"
	printf "%s rel-<release-tag>\n" "${script_path}"
	exit 1
fi

bundle_dir="/binary-releases"
bundle_filename="nascimento-images-export.tar.gz"
bundle_path="${bundle_dir}/${release_tag}/${bundle_filename}"
temp_dir="/tmp/latest-nascimento"
temp_extracted_dir="${temp_dir}/nascimento-images"
compose_filename="docker-compose-runner.yml"
env_compose_filename="docker-compose-runner-qlt.yml"
stack_compose_filename="docker-stack.yml"
compose_path="${temp_extracted_dir}/${compose_filename}"
env_compose_path="${temp_extracted_dir}/${env_compose_filename}"
stack_compose_path="${temp_extracted_dir}/${stack_compose_filename}"
stack_name="nascimento"

declare -a remote_server
#remote_server+="qlt-rno-admin@nascimento.justica.gov.pt"

# Undeploy stack
docker stack ls | grep ${stack_name}
if [ $? -eq 0 ] ; then
	printf "Removing unused images\n"
	docker image prune -a -f
	if [ $? -ne 0 ] ; then
		printf "An error occurred when trying to remove unused images\n"
		exit 1
	else
		printf "Old images removed successfully\n"
	fi
	printf "Undeploying stack : %s\n" "${stack_name}"
	docker stack rm ${stack_name}
        if [ $? -ne 0 ] ; then
                printf "An error occurred when trying to remove stack\n"
                exit 1
        else
                printf "Stack removed successfully\n"
        fi
fi

# Load images in remote servers
for server in ${remote_server[@]} ; do
	printf "Copying images to remote server : %s\n" "${server}"
	scp -r "${bundle_dir}/${release_tag}" "${server}:${bundle_dir}"
        if [ $? -ne 0 ] ; then
                printf "An error occured while trying to copy image bundle to remote server : %s\n" "${server}"
                exit 1
        else
                printf "Image bundle copied with success to remote server : %s\n" "${server}"
        fi
	printf "Loading images on remote server : %s\n" "${server}"
        ssh ${server} "${remote_path} ${release_tag}"
        if [ $? -ne 0 ] ; then
                printf "An error occured on remote server : %s\n" "${server}"
                exit 1
        else
                printf "Images loaded with success in remote server : %s\n" "${server}"
        fi
done

# Create temporary directory
if [ ! -d "${temp_dir}" ] ; then
	printf "Creating temporary dir : %s\n" "${temp_dir}"
	mkdir -p "${temp_dir}"
	if [ $? -ne 0 ] ; then
		printf "An error occurred when trying to create temporary dir : %s\n" "${temp_dir}"
		exit 1
	else
		printf "Temporary directory created successfully\n"
	fi
fi

if [ -f "${bundle_path}" ] ; then
	printf "Extracting image bundle\n"
	tar -C "${temp_dir}" -zxvf "${bundle_path}"
	if [ $? -ne 0 ] ; then
		printf "An error occurred when extracting tar file\n"
		exit 1
	else
		printf "File extracted successfully\n"
	fi
	printf "Loading image bundle\n"
	for f in `find ${temp_dir} -name *.tar` ; do
		docker load -i $f
		if [ $? -ne 0 ] ; then
			printf "An error occurred when loading image %s\n" "$f"
			exit 1
		else
			printf "Image loaded successfully\n"
		fi
	done
else
	printf "Release path does not exist : %s\n" "${bundle_path}"
	exit 1
fi

# Create swarm file overriding environment variables
printf "Creating deploy file : %s\n" "${stack_compose_path}"
docker-compose -f ${compose_path} -f ${env_compose_path} config > ${stack_compose_path}
if [ $? -ne 0 ] ; then
        printf "An error occurred when trying to create deploy file : %s\n" ${stack_compose_path}
        exit 1
else
        printf "File created successfully\n"
fi

# Deploy in swarm cluster
printf "Deploying stack : %s\n" "${stack_compose_path}"
docker stack deploy -c ${stack_compose_path} ${stack_name}
if [ $? -ne 0 ] ; then
        printf "An error occurred when deploying stack\n"
        exit 1
else
        printf "Stack deployed successfully\n"
fi

# Remove all release dirs except last 2
ls -dt ${bundle_dir}/rel-*/ | tail -n +3 | xargs rm -rf
if [ $? -ne 0 ] ; then
        printf "An error occurred when trying to delete old releases : %s\n"
        exit 1
else
        printf "Old releases removed successfully\n"
fi

# Remove temporary directory
if [[ -d "${temp_dir}" && ${temp_dir} != "/" ]] ; then
        printf "Removing temporary dir : %s\n" "${temp_dir}"
	rm -r ${temp_dir}
	if [ $? -ne 0 ] ; then
		printf "An error occurred when trying to remove temporary dir : %s\n" "${temp_dir}"
		exit 1
	else
		printf "Temporary directory removed successfully\n"
	fi
fi

