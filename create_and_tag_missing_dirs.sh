#!/bin/bash
pushd experiments
for dir in *; do
	for subdir in {src,lib}; do
		for subsubdir in {common,client,server}; do
			if [ -d ${dir} ]; then
				if [ ! -d ${dir}/${subdir}/java/${subsubdir} ]; then
					printf "Creating dir ${dir}/${subdir}/java/${subsubdir}\n"
					mkdir -p ${dir}/${subdir}/java/${subsubdir}
				fi
				if [ ! -f ${dir}/${subdir}/java/${subsubdir}/readme.txt ]; then
					printf "Creating file ${dir}/${subdir}/java/${subsubdir}/readme.txt\n"
					touch ${dir}/${subdir}/java/${subsubdir}/readme.txt
					git add ${dir}/${subdir}/java/${subsubdir}/readme.txt
				fi
			fi
		done
	done
done
popd

for dir in {elab,rec}; do
	for subdir in {compile,java}; do
		for subsubdir in {common,client,driver,multicast}; do
			if [ -d ${dir} ]; then
				if [ ! -d ${dir}/lib/${subdir}/${subsubdir} ]; then
					printf "Creating dir ${dir}/lib/${subdir}/${subsubdir}\n"
					mkdir -p ${dir}/lib/${subdir}/${subsubdir}
				fi
				if [ ! -f ${dir}/lib/${subdir}/${subsubdir}/readme.txt ]; then
					printf "Creating file ${dir}/lib/${subdir}/${subsubdir}/readme.txt\n"
					touch ${dir}/lib/${subdir}/${subsubdir}/readme.txt
					git add ${dir}/lib/${subdir}/${subsubdir}/readme.txt
				fi
			fi
		done
	done
done
