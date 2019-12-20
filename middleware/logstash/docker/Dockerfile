FROM centos:7.2.1511

# 添加用户
RUN useradd ifcp

# 添加源文件
ADD source/logstash-7.3.0.tar.gz /home/ifcp/
ADD source/mysql-connector-java.tar /home/ifcp/logstash-7.3.0/lib

# 添加配置文件
ADD config /home/ifcp/logstash-7.3.0/config
ADD bin/* /home/ifcp/bin/

# 添加依赖
ADD dependencies/dependencies.tar /usr/local/

RUN cd /usr/local/dependencies/ \
  && rpm -Uvh *.rpm --nodeps --force \
  && rm -rf /usr/local/dependencies \
  && chown -R ifcp /home/ifcp/logstash-7.3.0 \
  && chmod -R +x /home/ifcp/bin \
  && cp -rf /home/ifcp/bin/* /usr/local/bin

#ENV ELASTICSEARCH_HOST
ENV LOGSTASH_CONFIG /home/ifcp/logstash-7.3.0/config
ENV LOGSTASH_LAST_RUN /home/ifcp/logstash-7.3.0/data/last-run
ENV JDBC_CONNECT_STRING jdbc:mysql://139.199.76.194:33306/wm_dev?useUnicode=true&allowMultiQueries=true
ENV JDBC_USER user1
ENV JDBC_PASSWORD esnP@ssw0rd.17
ENV LOGSTASH_OUTPUT_MODE stdout

USER ifcp

CMD ["bin/bash"]