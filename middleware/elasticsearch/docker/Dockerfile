FROM centos:7.2.1511

# 添加用户
RUN useradd ifcp

# 添加源文件
ADD source/elasticsearch-7.3.0-linux-x86_64.tar.gz /home/ifcp/
ADD source/elasticsearch-analysis-ik-7.3.0.tar /home/ifcp/elasticsearch-7.3.0/plugins/

RUN mv /home/ifcp/elasticsearch-7.3.0/plugins/elasticsearch-analysis-ik-7.3.0 /home/ifcp/elasticsearch-7.3.0/plugins/ik \
  && chown -R ifcp /home/ifcp/elasticsearch-7.3.0/

CMD ["/home/ifcp/elasticsearch-7.3.0/bin/elasticsearch"]