# Redis Dockerfile 主备服务器模拟

`master`和`backup`容器

## 启动

```bash
docker-compose up
```

## 关闭

```bash
docker-compose stop
```

## 端口

| 名称 | 容器端口 |  宿主端口 |
| ---| ---| --- |
| master | 6379 | 6379|
| backup| 6379| 6380

## 容器内域名

- `master` - master容器
- `backup` - backup容器

## attach容器

attach容器(默认ifcp用户)

```bash
docker-compose exec master bash # 进入master容器
docker-compose exec backup bash # 进入backup容器
```

root用户attach容器

```bash
docker-compose exec -u root backup bash # 进入master容器
docker-compose exec -u root master bash # 进入backup容器
```

## 测试同步数据

```bash
# 进入任一容器
ifcp_redis_attach_master.sh # 连接master容器的redis
# 存储值
set a 1
set b 3
# 获取值
get a
get b
# 退出切换至backup容器的redis
exit
ifcp_redis_attach_backup.sh
# 获取a和b的值，查看是否相同
get a
get b
```

## 测试宕机情况

```bash
# 进入master容器，关闭redis进程
# 可以直接杀死进程，也可以
ifcp_redis_attach_master.sh
shutdown
# 查看docker-compose启动终端，需要等一会儿，待sentinel选举完后
# 查看backup容器的redis状态
ifcp_redis_attach_backup.sh
info replication
# role属性表示角色
```

## 容器内命令

| 命令 | 说明 |
|---|---|
|ifcp_redis_attach_backup.sh | 连接backup容器的redis|
|ifcp_redis_attach_master.sh | 连接master容器的redis
|ifcp_redis_start.sh| 启动redis和sentinel