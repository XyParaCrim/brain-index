# redis部署

## 使用的系统软件

| 名称 | 描述 |  版本 |
| ---| ---| --- |
|centos| 操作系统 | 7.2.1511
|redis|数据库|4.0.14

## 编译环境
- gcc 
- gcc-c++ 
- make 
- automake 
- autoconf 
- libtool 
- pcre 
- pcre-devel 
- zlib 
- zlib-devel 
- openssl-devel

## 磁盘目录

| 说明 | 路径 |
| ---| ---|
| 解压安装包位置 | `/home/ifcp`
| 数据目录| `/home/ifcp/redis`
| 运行配置目录| `/home/ifcp/redis/conf`
| 日志|`/home/ifcp/redis/logs`
| redis工作目录|`/home/ifcp/redis/work`
| sentinel工作目录| `/home/ifcp/redis/tmp`

## 安装并部署

### 解压安装
1. 解压至/home/ifcp`tar -xvf  redis-4.0.14.tar -C /home/ifcp/`
2. 编译安装`cd /home/ifcp/ redis-4.0.14 && make`

### 创建约定目录

```bash
mkdir /home/ifcp/redis
mkdir /home/ifcp/redis/conf
mkdir /home/ifcp/redis/logs
mkdir /home/ifcp/redis/work
mkdir /home/ifcp/redis/tmp
```

### 配置

#### master redis

```
# 复制模块配置到约定目录下
cp /home/ifcp/redis-4.0.14/redis.conf /home/ifcp/redis/conf
# 修改配置
vi /home/ifcp/redis/conf/redis.conf
# 修改内容如下
bind xx.xx.xx.xx # 本机ip，例如 bind 192.168.1.100
daemonize yes # 后台运行
logfile "/home/ifcp/redis/logs/redis.log" # 日志位置
stop-writes-on-bgsave-error no
dir /home/ifcp/redis/work # 工作目录
```

#### salve redis

```
# 复制模块配置到约定目录下
cp /home/ifcp/redis-4.0.14/redis.conf /home/ifcp/redis/conf
# 修改配置
vi /home/ifcp/redis/conf/redis.conf
# 修改内容如下
bind xx.xx.xx.xx # 本机ip，例如 bind 192.168.1.100
daemonize yes # 后台运行
logfile "/home/ifcp/redis/logs/redis.log" # 日志位置
stop-writes-on-bgsave-error no
dir /home/ifcp/redis/work # 工作目录
slaveof xx.xx.xx.xx 6379 # master redis服务器IP
```

#### sentinel

```bash
# 复制模块配置到约定目录下
cp /home/ifcp/redis-4.0.14/sentinel.conf /home/ifcp/redis/conf
# 修改配置
vi /home/ifcp/redis/conf/sentinel.conf
# 修改内容如下
protected-mode yes # 因为redis默认开启，这里也一样
bind xx.xx.xx.xx # 本机ip，例如 bind 192.168.1.100
dir /home/ifcp/redis/tmp # 工作目录
sentinel monitor mymaster xx.xx.xx.xx 6379 1 # xx.xx.xx.xx为master redis服务器IP
```

**两份配置除了`bind`选项，其他一致**

## 启动

```bash
/home/ifcp/redis-4.0.14/src/redis-server /home/ifcp/redis/conf/redis.conf
/home/ifcp/redis-4.0.14/src/redis-sentinel /home/ifcp/redis/conf/sentinel.conf
```