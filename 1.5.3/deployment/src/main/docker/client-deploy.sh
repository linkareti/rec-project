#!/bin/bash

release_tag=$1

script_filename="client-deploy.sh"
script_dir="/docker/nascimento/scripts"
script_path="${script_dir}/${script_filename}"

if [ -z "${release_tag}" ] ; then
	printf "Usage:\n"
	printf "%s rel-<release-tag>\n" "${script_path}"
	exit 1
fi

bundle_dir="/binary-releases"
bundle_filename="nascimento-images-export.tar.gz"
bundle_path="${bundle_dir}/${release_tag}/${bundle_filename}"
temp_dir="/tmp/latest-nascimento"

# Remove all unused images
docker image prune -a -f
if [ $? -ne 0 ] ; then
	printf "An error occurred when trying to remove unused images\n"
	exit 1
else
	printf "Old images removed successfully\n"
fi

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

# Extract and load docker images
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

# Delete temporary directory
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

# Remove all release dirs except last 2
ls -dt ${bundle_dir}/rel-*/ | tail -n +3 | xargs rm -rf
if [ $? -ne 0 ] ; then
	printf "An error occurred when trying to delete old releases : %s\n"
	exit 1
else
	printf "Old releases removed successfully\n"
fi

