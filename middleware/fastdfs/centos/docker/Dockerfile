FROM centos:7.2.1511
# 添加用户
RUN useradd ifcp \
  && mkdir /home/ifcp/fastdfs \
  && mkdir /home/ifcp/fastdfs/tracker \
  && mkdir /home/ifcp/fastdfs/storage

# 添加配置文件
ADD conf/* /home/ifcp/fastdfs/conf/
ADD bin/* /home/ifcp/bin/

# 添加依赖
ADD dependencies/dependencies.tar /usr/local/

# 添加源文件
ADD source/fastdfs-5.11.tar /home/ifcp/
ADD source/fastdfs-nginx-module-1.20.tar /home/ifcp/
ADD source/libfastcommon-1.0.41.tar.gz /home/ifcp/
ADD source/nginx-1.12.2.tar.gz /home/ifcp/

RUN cd /usr/local/dependencies/ \
  && rpm -Uvh *.rpm --nodeps --force \
  && rm -rf /usr/local/dependencies \
  && cd /home/ifcp/libfastcommon-1.0.41 \
  && ./make.sh && ./make.sh install  \
  && cd /home/ifcp/fastdfs-5.11 \
  && ./make.sh && ./make.sh install \
  && chown -R ifcp /home/ifcp/fastdfs \
  && cp -f /home/ifcp/fastdfs/conf/http.conf /etc/fdfs/ \
  && cp -f /home/ifcp/fastdfs/conf/mime.types /etc/fdfs/ \
  && cp -f /home/ifcp/fastdfs/conf/config /home/ifcp/fastdfs-nginx-module-1.20/src/ \
  && cd /home/ifcp/nginx-1.12.2 \
  && ./configure --prefix=/home/ifcp/nginx/ --add-module=/home/ifcp/fastdfs-nginx-module-1.20/src/ \
  &&  make && make install \
  && cp -f /home/ifcp/fastdfs/conf/nginx.conf /home/ifcp/nginx/conf/ \
  && chown -R ifcp /home/ifcp/nginx \
  && chmod -R +x /home/ifcp/bin \
  && cp -rf /home/ifcp/bin/* /usr/local/bin

ENTRYPOINT ["ifcp_start.sh"]

