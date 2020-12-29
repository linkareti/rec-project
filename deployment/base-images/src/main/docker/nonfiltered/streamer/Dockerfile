FROM debian:stretch-slim as builder

# Install dependencies
RUN apt-get update && \
	apt-get install -y \
		wget build-essential ca-certificates \
		openssl libssl-dev yasm \
		libpcre3-dev librtmp-dev libtheora-dev \
		libvorbis-dev libvpx-dev libfreetype6-dev \
		libmp3lame-dev libx264-dev libx265-dev && \
    rm -rf /var/lib/apt/lists/*

# Download and decompress Nginx
# Original download info https://nginx.org/download/1.18.0.tar.gz
RUN mkdir -p /tmp/build/nginx
COPY resources/nginx.tar.gz /tmp/build/nginx/.
RUN	cd /tmp/build/nginx && \
	tar -zxf /tmp/build/nginx/nginx.tar.gz

# Download and decompress RTMP module
# Original download info https://github.com/arut/nginx-rtmp-module/archive/1.2.1.tar.gz
RUN mkdir -p /tmp/build/nginx-rtmp-module    
COPY resources/nginx-rtmp-module.tar.gz /tmp/build/nginx-rtmp-module/.
RUN	cd /tmp/build/nginx-rtmp-module && \
	tar -zxf nginx-rtmp-module.tar.gz && \
    cd nginx-rtmp-module-1.2.1

# Build nginx with nginx-rtmp module
RUN cd /tmp/build/nginx/nginx-1.18.0 && \
    ./configure \
        --sbin-path=/usr/local/sbin/nginx \
        --conf-path=/etc/nginx/nginx.conf \
        --error-log-path=/var/log/nginx/error.log \
        --http-log-path=/var/log/nginx/access.log \		
        --pid-path=/var/run/nginx/nginx.pid \
        --lock-path=/var/lock/nginx.lock \
        --http-client-body-temp-path=/tmp/nginx-client-body \
        --with-http_ssl_module \
        --with-threads \
        --add-module=/tmp/build/nginx-rtmp-module/nginx-rtmp-module-1.2.1 && \
    make -j $(getconf _NPROCESSORS_ONLN) && \
    make install
    
# Install ffmpeg source
# Original download  http://ffmpeg.org/releases/ffmpeg-4.2.1.tar.gz
#RUN mkdir -p /tmp/build/ffmpeg
#COPY resources/ffmpeg.tar.gz /tmp/build/ffmpeg/.
#RUN cd /tmp/build/ffmpeg && \
#  tar -zxf ffmpeg.tar.gz && \
#  rm ffmpeg.tar.gz

# Build ffmpeg
#RUN cd /tmp/build/ffmpeg/ffmpeg-4.2.1 && \
#  ./configure \
#	  --enable-version3 \
#	  --enable-gpl \
#	  --enable-small \
#	  --enable-libx264 \
#	  --enable-libx265 \
#	  --enable-libvpx \
#	  --enable-libtheora \
#	  --enable-libvorbis \
#	  --enable-librtmp \
#	  --enable-postproc \
#	  --enable-swresample \ 
#	  --enable-libfreetype \
#	  --enable-libmp3lame \
#	  --disable-debug \
#	  --disable-doc \
#	  --disable-ffplay \
#	  --extra-libs="-lpthread -lm" && \
#	make -j $(getconf _NPROCESSORS_ONLN) && \
#	make install

# Copy stats.xsl file to nginx html directory and cleaning build files
RUN cp /tmp/build/nginx-rtmp-module/nginx-rtmp-module-1.2.1/stat.xsl /usr/local/nginx/html/stat.xsl && \
	rm -rf /tmp/build

##### Building the final image #####
FROM debian:stretch-slim

# Install dependencies
RUN apt-get update && \
	apt-get install -y \
		ca-certificates openssl libpcre3-dev \
		librtmp1 libtheora0 libvorbis-dev libmp3lame0 \
		libvpx4 libx264-dev libx265-dev && \
    rm -rf /var/lib/apt/lists/*

# Copy files from build stage to final stage	
COPY --from=builder /usr/local /usr/local
COPY --from=builder /etc/nginx /etc/nginx
COPY --from=builder /var/log/nginx /var/log/nginx
COPY --from=builder /var/lock /var/lock
#COPY --from=builder /var/run/nginx /var/run/nginx

# Forward logs to Docker
RUN ln -sf /dev/stdout /var/log/nginx/access.log && \
    ln -sf /dev/stderr /var/log/nginx/error.log

# Set up config file
COPY resources/nginx.conf /etc/nginx/nginx.conf

# Copy  html players to container
COPY resources/players /usr/local/nginx/html/players

EXPOSE 1935
EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]