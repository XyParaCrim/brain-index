# logstash docker模拟 与 实际部署

## 使用的系统软件

| 名称 | 描述 |  版本 |
| ---| ---| --- |
|centos| 操作系统 | 7.2.1511
|logstash|好软件|7.3.0


## 运行环境
- JDK11

## 磁盘目录

| 说明 | 路径 |
| ---| ---|
| 解压安装包位置 | `/home/ifcp`
| 数据目录| `/home/ifcp/logstash-7.3.0/data`
| 运行配置目录| `/home/ifcp/logstash-7.3.0/config`
| 日志|`/home/ifcp/logstash-7.3.0/logs`

## 安装并部署
1. 解压至/home/ifcp`tar -xzvf logstash-7.3.0.tar.gz -C /home/ifcp/`
2. 添加JDBC至logstash目录下`tar -xvf mysql-connector-java.tar -C /home/ifcp/logstash-7.3.0/lib`

### 配置

```bash
# 用docker文件下的config替换至
cp -rf config /home/ifcp/logstash-7.3.0/config
```

其他运行配置是通过环境变量传入的

| 环境变量 | 描述 |  默认值 |
| ---| ---| --- |
|ELASTICSEARCH_HOST|elasticsearch地址|
| LOGSTASH_CONFIG | logstash配置目录|
|LOGSTASH_LAST_RUN| logstash上一次运行的时间戳文件目录|
|JDBC_CONNECT_STRING|连接数据库url|
|JDBC_USER| 数据库用户|
|JDBC_PASSWORD|数据库密码|
|LOGSTASH_OUTPUT_MODE|输出模式(设为stdout，则标准输出，不传输到elasticsearch)| elasticsearch

### 启动

```bash
/home/ifcp/logstash-7.3.0/bin/logstash
```

### docker

`LOGSTASH_OUTPUT_MODE`默认为`stdout`。其他默认值查看`Dockerfile`

```bash
# build 镜像
docker build -t logstash:mock .
# 启动容器
docker run -i -t logstash:mock
```
#### 容器内命令

| 命令 | 说明 |
|---|---|
|logstash_pipe_start.sh | 启动全部任务|
|logstash_single_start.sh | 启动一个任务|

```bash
logstash_pipe_start.sh elasticsearch
logstash_pipe_start.sh stdout
```

```bash
# question为已经配置好的任务，其他任务查看`config`
logstash_single_start.sh question stdout
logstash_single_start.sh document elasticsearch
```