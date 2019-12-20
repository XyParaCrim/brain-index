FROM centos:7.2.1511

# 添加用户
RUN useradd ifcp

# 添加依赖
ADD dependencies/dependencies.tar /usr/local/

# 添加源文件
ADD source/redis-4.0.14.tar /home/ifcp/

# 添加快捷命令
ADD bin/* /home/ifcp/bin/

RUN cd /usr/local/dependencies/ \
      && rpm -Uvh *.rpm --nodeps --force \
      && rm -rf /usr/local/dependencies \
      && cd /home/ifcp/redis-4.0.14 \
      && make \
      && mkdir /home/ifcp/redis \
      && mkdir /home/ifcp/redis/conf \
      && mkdir /home/ifcp/redis/logs \
      && mkdir /home/ifcp/redis/work \
      && mkdir /home/ifcp/redis/tmp \
      && chmod -R +x /home/ifcp/bin \
      && cp -rf /home/ifcp/bin/* /usr/local/bin \
      && chown -R ifcp /home/ifcp/redis-4.0.14 \
      && chown -R ifcp /home/ifcp/redis

CMD ["ifcp_redis_start.sh"]
