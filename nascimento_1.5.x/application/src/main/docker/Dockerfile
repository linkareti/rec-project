FROM ${repo}/${simplifiedGroupId}/wildfly:${docker.base.image.version}

USER jboss

COPY resources/nascimento_irn.p12 /home/nascimento/certs/nascimento_irn.p12
COPY resources/saml_fa_truststore.jks /home/nascimento/certs/saml_fa_truststore.jks
COPY resources/eai.mj.pt.pem /resources/
COPY commands.cli /opt/jboss/wildfly
RUN /opt/jboss/wildfly/execute.sh \
    && rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history

ADD ${project.artifactId} /opt/jboss/wildfly/standalone/deployments/${project.artifactId}.war
RUN touch /opt/jboss/wildfly/standalone/deployments/${project.artifactId}.war.dodeploy
