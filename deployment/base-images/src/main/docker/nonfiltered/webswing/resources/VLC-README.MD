# Built @ 20200210 by jcortes
# Package org.videolan.vlc - vlc-slim_x86_64_linux
RUN mkdir -p /tmp/vlc-libs/lib \
              /tmp/vlc-libs/usr \
              /tmp/vlc-libs/lib64 \
     && apt-get update \
     && apt-get install --no-install-recommends -y vlc

# To find binaries needed use 'ldd /usr/bin/vlc'
RUN cp /usr/lib/x86_64-linux-gnu/libvlc.so.5 \
    /usr/lib/x86_64-linux-gnu/libvlccore.so.9 \
    /usr/lib/x86_64-linux-gnu/liblz4.so.1 \
    /usr/lib/x86_64-linux-gnu/libbluray.so.2 \
    /usr/lib/x86_64-linux-gnu/libdvdnav.so.4 \
    /usr/lib/x86_64-linux-gnu/libarchive.so.13 \
    /usr/lib/x86_64-linux-gnu/libtag.so.1 \
    /usr/lib/x86_64-linux-gnu/libxml2.so.2 \
    /usr/lib/x86_64-linux-gnu/libpulse.so.0 \
    /usr/lib/x86_64-linux-gnu/libasound.so.2 \
    /usr/lib/x86_64-linux-gnu/libsndio.so.7.0 \
    /usr/lib/x86_64-linux-gnu/libxcb-keysyms.so.1 \
    /usr/lib/x86_64-linux-gnu/libQt5Svg.so.5 \
    /usr/lib/x86_64-linux-gnu/libQt5Widgets.so.5 \
    /usr/lib/x86_64-linux-gnu/libQt5X11Extras.so.5 \
    /usr/lib/x86_64-linux-gnu/libQt5Gui.so.5 \
    /usr/lib/x86_64-linux-gnu/libQt5Core.so.5 \
    /usr/lib/x86_64-linux-gnu/libwayland-client.so.0 \
    /usr/lib/x86_64-linux-gnu/libliveMedia.so.64 \
    /usr/lib/x86_64-linux-gnu/libgroupsock.so.8 \
    /usr/lib/x86_64-linux-gnu/libBasicUsageEnvironment.so.1 \
    /usr/lib/x86_64-linux-gnu/libUsageEnvironment.so.3 \
    /usr/lib/x86_64-linux-gnu/libharfbuzz.so.0 \
    /usr/lib/x86_64-linux-gnu/libicui18n.so.63 \
    /usr/lib/x86_64-linux-gnu/libicuuc.so.63 \
    /usr/lib/x86_64-linux-gnu/libpcre2-16.so.0 \
    /usr/lib/x86_64-linux-gnu/libdouble-conversion.so.1 \
    /usr/lib/x86_64-linux-gnu/libglib-2.0.so.0 \
    /usr/lib/x86_64-linux-gnu/pulseaudio/libpulsecommon-12.2.so \
    /usr/lib/x86_64-linux-gnu/libSDL-1.2.so.0  \
    /usr/lib/x86_64-linux-gnu/libSDL_image-1.2.so.0  \
    /usr/lib/x86_64-linux-gnu/libaom.so.0  \
    /usr/lib/x86_64-linux-gnu/libavcodec.so.58  \
    /usr/lib/x86_64-linux-gnu/libavutil.so.56  \
    /usr/lib/x86_64-linux-gnu/libcairo.so.2  \
    /usr/lib/x86_64-linux-gnu/libdvdread.so.4  \
    /usr/lib/x86_64-linux-gnu/libgobject-2.0.so.0  \
    /usr/lib/x86_64-linux-gnu/libgraphite2.so.3  \
    /usr/lib/x86_64-linux-gnu/libicudata.so.63  \
    /usr/lib/x86_64-linux-gnu/libjpeg.so.62  \
    /usr/lib/x86_64-linux-gnu/libmpeg2.so.0  \
    /usr/lib/x86_64-linux-gnu/librsvg-2.so.2  \
    /usr/lib/x86_64-linux-gnu/libspeex.so.1  \
    /usr/lib/x86_64-linux-gnu/libtheoradec.so.1  \
    /usr/lib/x86_64-linux-gnu/libtheoraenc.so.1  \
    /usr/lib/x86_64-linux-gnu/libvorbis.so.0  \
    /usr/lib/x86_64-linux-gnu/libvorbisenc.so.2  \
    /usr/lib/x86_64-linux-gnu/libasyncns.so.0 \
    /usr/lib/x86_64-linux-gnu/libcaca.so.0 \
    /usr/lib/x86_64-linux-gnu/libcodec2.so.0.8.1 \
    /usr/lib/x86_64-linux-gnu/libcroco-0.6.so.3 \
    /usr/lib/x86_64-linux-gnu/libcrystalhd.so.3 \
    /usr/lib/x86_64-linux-gnu/libgdk_pixbuf-2.0.so.0 \
    /usr/lib/x86_64-linux-gnu/libgio-2.0.so.0 \
    /usr/lib/x86_64-linux-gnu/libgsm.so.1 \
    /usr/lib/x86_64-linux-gnu/libmp3lame.so.0 \
    /usr/lib/x86_64-linux-gnu/libogg.so.0 \
    /usr/lib/x86_64-linux-gnu/libopenjp2.so.7 \
    /usr/lib/x86_64-linux-gnu/libopus.so.0 \
    /usr/lib/x86_64-linux-gnu/libpango-1.0.so.0 \
    /usr/lib/x86_64-linux-gnu/libpangocairo-1.0.so.0 \
    /usr/lib/x86_64-linux-gnu/libpangoft2-1.0.so.0 \
    /usr/lib/x86_64-linux-gnu/libpulse-simple.so.0 \
    /usr/lib/x86_64-linux-gnu/libshine.so.3 \
    /usr/lib/x86_64-linux-gnu/libsnappy.so.1 \
    /usr/lib/x86_64-linux-gnu/libsndfile.so.1 \
    /usr/lib/x86_64-linux-gnu/libswresample.so.3 \
    /usr/lib/x86_64-linux-gnu/libtiff.so.5 \
    /usr/lib/x86_64-linux-gnu/libtwolame.so.0 \
    /usr/lib/x86_64-linux-gnu/libva-drm.so.2 \
    /usr/lib/x86_64-linux-gnu/libva-x11.so.2 \
    /usr/lib/x86_64-linux-gnu/libva.so.2 \
    /usr/lib/x86_64-linux-gnu/libvdpau.so.1 \
    /usr/lib/x86_64-linux-gnu/libvpx.so.5 \
    /usr/lib/x86_64-linux-gnu/libwavpack.so.1 \
    /usr/lib/x86_64-linux-gnu/libwebp.so.6 \
    /usr/lib/x86_64-linux-gnu/libwebpmux.so.3 \
    /usr/lib/x86_64-linux-gnu/libx264.so.155 \
    /usr/lib/x86_64-linux-gnu/libx265.so.165 \
    /usr/lib/x86_64-linux-gnu/libxcb-render.so.0 \
    /usr/lib/x86_64-linux-gnu/libxcb-shm.so.0 \
    /usr/lib/x86_64-linux-gnu/libxvidcore.so.4 \
    /usr/lib/x86_64-linux-gnu/libzvbi.so.0 \
    /usr/lib/x86_64-linux-gnu/libFLAC.so.8 \
    /usr/lib/x86_64-linux-gnu/libfribidi.so.0 \
    /usr/lib/x86_64-linux-gnu/libgmodule-2.0.so.0 \
    /usr/lib/x86_64-linux-gnu/libjbig.so.0 \
    /usr/lib/x86_64-linux-gnu/libnuma.so.1 \
    /usr/lib/x86_64-linux-gnu/libsoxr.so.0 \
    /usr/lib/x86_64-linux-gnu/libthai.so.0 \
    /usr/lib/x86_64-linux-gnu/libdatrie.so.1 \
    /usr/lib/x86_64-linux-gnu/libgomp.so.1 \
    /usr/lib/x86_64-linux-gnu/libswscale.so.5 \
    /usr/lib/x86_64-linux-gnu/libplacebo.so.7 \
    /usr/lib/x86_64-linux-gnu/libGLESv2.so.2 \
    /usr/lib/x86_64-linux-gnu/libxcb-xv.so.0 \
    /usr/lib/x86_64-linux-gnu/liblcms2.so.2 \
    /usr/lib/x86_64-linux-gnu/libvulkan.so.1 \
    /usr/lib/x86_64-linux-gnu/liblua5.2.so.0 /tmp/vlc-libs/usr/\
    && cp /lib/x86_64-linux-gnu/libpthread.so.0 \ 
    /lib/x86_64-linux-gnu/libdl.so.2 \
    /lib/x86_64-linux-gnu/libc.so.6 \
    /lib/x86_64-linux-gnu/libm.so.6 \
    /lib/x86_64-linux-gnu/libidn.so.11 \
    /lib/x86_64-linux-gnu/libdbus-1.so.3 \
    /lib/x86_64-linux-gnu/libsystemd.so.0 \
    /lib/x86_64-linux-gnu/librt.so.1 \
    /lib/x86_64-linux-gnu/liblzma.so.5 \
    /lib/x86_64-linux-gnu/libgcrypt.so.20 \
    /lib/x86_64-linux-gnu/libcap.so.2 \
    /lib/x86_64-linux-gnu/libwrap.so.0 \
    /lib/x86_64-linux-gnu/libslang.so.2 \
    /lib/x86_64-linux-gnu/libgpg-error.so.0 /tmp/vlc-libs/lib/ \
    && cp /lib64/ld-linux-x86-64.so.2 /tmp/vlc-libs/lib64/
    
COPY --from=vlc-sources /usr/bin/vlc /usr/bin/
COPY --from=vlc-sources /usr/lib/x86_64-linux-gnu/vlc/ /usr/lib/x86_64-linux-gnu/vlc/
COPY --from=vlc-sources /usr/share/vlc/ /usr/share/vlc/

COPY --from=vlc-sources /tmp/vlc-libs/lib/ /lib/x86_64-linux-gnu/
COPY --from=vlc-sources /tmp/vlc-libs/usr/ /usr/lib/x86_64-linux-gnu/
COPY --from=vlc-sources /tmp/vlc-libs/lib64/ /lib64/

rm -f /tmp/vlc-slim/usr/lib/x86_64-linux-gnu/vlc/plugins/plugins.dat