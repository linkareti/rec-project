FROM ${repo}/${simplifiedGroupId}/base-images/openjdk11:${project.version}

COPY --chown=10000:10000 elab-${project.version}-runner.jar /opt/elab/elab-backend.jar
COPY --chown=10000:10000 lib /opt/elab/lib/