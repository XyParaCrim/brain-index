FROM alpine:3.10

COPY bin/xpc_* /usr/bin/
COPY source/* /usr/local/src/

RUN set -x \

    && chmod +x /usr/bin/xpc_* \

    && echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories \
    && echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories \
    && apk update \
    && apk add --no-cache --virtual .build-deps gcc libc-dev make perl-dev openssl-dev pcre-dev zlib-dev \

    && cd /usr/local/src \

    && tar -xf nginx-1.18.0.tar.gz \
    && tar -xf libfastcommon-1.0.43.tar.gz \
    && tar -xf fastdfs-6.06.tar.gz \
    && tar -xf fastdfs-nginx-module-1.22.tar.gz \

    && mv nginx-1.18.0 nginx \
    && mv libfastcommon-1.0.43 libfastcommon \
    && mv fastdfs-6.06 fastdfs \
    && mv fastdfs-nginx-module-1.22 fastdfs-nginx-module \

    && cd /usr/local/src/libfastcommon \
    && ./make.sh \
    && ./make.sh install \
    && cd /usr/local/src/fastdfs/ \
    && ./make.sh \
    && ./make.sh install \
    && cd /usr/local/src/nginx/ \
    && ./configure --add-module=/usr/local/src/fastdfs-nginx-module/src/ --conf-path=/etc/fdfs/nginx/nginx.conf \
    && make && make install \

    && mkdir -p /var/fdfs \
    && mkdir -p /var/fdfs/tracker \
    && mkdir -p /var/fdfs/storage \

    && apk del .build-deps \
    && apk add --no-cache pcre-dev bash \
    && rm -rf /usr/local/src/*

CMD ["tail", "-f", "/dev/null"]