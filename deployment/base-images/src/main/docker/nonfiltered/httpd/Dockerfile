FROM docker-thirdparty.linkare.com/busybox:1.31.1 as base

## Docker multi-stage image, more info at:
# https://docs.docker.com/develop/develop-images/multistage-build/
# Image with the purpose of lowering layers and size of the main domibus image 
ENV DOCKERIZE_VERSION=0.6.1

# Download Dockerize Binary for use with Docker
ADD "https://nexus.linkare.com/nexus/repository/thirdparty/com/github/dockerize-alpine-linux-amd64/${DOCKERIZE_VERSION}/dockerize-alpine-linux-amd64-${DOCKERIZE_VERSION}.tar.gz" /tmp/
RUN tar xvfz /tmp/dockerize-alpine-linux-amd64-${DOCKERIZE_VERSION}.tar.gz -C /tmp/

FROM docker-thirdparty.linkare.com/alpine:3.11.6 as ssl-container

ARG CERT_HOSTNAME="elab-dev.vps"
ARG CERT_DOMAINNAME="tecnico.ulisboa.pt"

RUN apk update \
  && apk add --no-cache openssl \
  && rm -rf /var/cache/apk/* \
  && openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /tmp/server.key -out /tmp/server.crt  -subj "/C=PT/ST=Lisboa/L=Lisboa/O=Dev/CN=${CERT_HOSTNAME}.${CERT_DOMAINNAME}"

FROM docker-thirdparty.linkare.com/httpd:2.4.41

#
# Add dockerize
#
COPY --from=base /tmp/dockerize /usr/local/bin/

# Timezone
ENV TZ "Europe/Lisbon"

# Load needed apache modules
RUN sed -i "s/#LoadModule rewrite_module/LoadModule rewrite_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule proxy_module/LoadModule proxy_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule lbmethod_bybusyness/LoadModule lbmethod_bybusyness/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule slotmem_shm/LoadModule slotmem_shm/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule proxy_balancer/LoadModule proxy_balancer/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule info_module/LoadModule info_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule proxy_http_module/LoadModule proxy_http_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule remoteip_module/LoadModule remoteip_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule cache_module/LoadModule cache_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule cache_disk_module/LoadModule cache_disk_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule logio_module/LoadModule logio_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule ssl_module/LoadModule ssl_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#Include conf\/extra\/httpd-ssl.conf/Include conf\/extra\/httpd-ssl.conf/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule socache_shmcb_module/LoadModule socache_shmcb_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule expires_module/LoadModule expires_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule proxy_wstunnel_module/LoadModule proxy_wstunnel_module/g" /usr/local/apache2/conf/httpd.conf \
    && sed -i "s/#LoadModule proxy_ajp_module/LoadModule proxy_ajp_module/g" /usr/local/apache2/conf/httpd.conf \
    && mkdir -p /usr/local/apache2/conf/vhosts.d \
    && ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && echo "Include conf/extra/monitoring.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "Include conf/extra/security.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "Include conf/extra/performance.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "Include conf/extra/cache.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "Include conf/extra/vhost.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "ServerName 127.0.0.1:80" >> /usr/local/apache2/conf/httpd.conf \
    && echo "SSLCertificateFile \"/usr/local/apache2/conf/server.crt\"" >> /usr/local/apache2/conf/httpd.conf \
    && echo "SSLCertificateKeyFile \"/usr/local/apache2/conf/server.key\"" >> /usr/local/apache2/conf/httpd.conf \
    && echo "SSLCACertificateFile \"/usr/local/apache2/conf/serverca.crt\"" >> /usr/local/apache2/conf/httpd.conf \
    && echo "IncludeOptional conf/vhosts.d/*.conf" >> /usr/local/apache2/conf/httpd.conf \
    && echo "root soft nofile 15000" >> /etc/security/limits.conf \
    && echo "root hard nofile 15000" >> /etc/security/limits.conf

#
# Copy default certificates
#
COPY --from=ssl-container /tmp/server.key /etc/ssl/private/
COPY --from=ssl-container /tmp/server.crt /etc/ssl/certs/

# Copy configuration files
COPY resources/*.conf /usr/local/apache2/conf/extra/

# Copy error pages
#COPY resources/error_pages /usr/local/apache2/error/

# Copy maintenance page
#COPY resources/under-maintenance.html /usr/local/apache2/htdocs/

# Copy startup script
COPY resources/startup.sh /scripts/startup.sh

RUN chmod +x /scripts/startup.sh \
    && cp /etc/ssl/certs/server.crt /etc/ssl/certs/serverca.crt \
    && ln -s /etc/ssl/certs/server.crt /usr/local/apache2/conf/server.crt \
    && ln -s /etc/ssl/private/server.key /usr/local/apache2/conf/server.key \
    && ln -s /etc/ssl/certs/serverca.crt /usr/local/apache2/conf/serverca.crt

EXPOSE 80 443

VOLUME [ "/etc/ssl/certs", "/etc/ssl/private", "/usr/local/apache2/conf/vhosts.d" ]

CMD [ "/scripts/startup.sh" ]