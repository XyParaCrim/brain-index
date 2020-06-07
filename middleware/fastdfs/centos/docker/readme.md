# FastDFS Dockerfile 主备服务器模拟

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
| nginx | 8080 | 8081(master)、8082(backup)

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

## 测试上传

```bash
# 进入任一容器后
ifcp_fdfs_upload_to_backup.sh # 上传文件到backup容器
ifcp_fdfs_upload_to_master.sh # 上传文件到master容器
```

测试两个容器都可以上传

## 测试下载

- **容器内** 

```bash
wget master:8080/${上传返回路径}
wget backup:8080/${上传返回路径}
```

**容器没有安装wget，需要`yum install wget`**

- **宿主机** 

浏览器输入`localhost:8081/${上传返回路径}`和`localhost:8082/${上传返回路径}`

## 容器内命令

| 命令 | 说明 |
|---|---|
|ifcp_fdfs_start.sh | 启动fastdfs|
|ifcp_fdfs_stop.sh | 关闭fastdfs
|ifcp_fdfs_upload_to_backup.sh| 上传文件到backup容器
|ifcp_fdfs_upload_to_master.sh| 上传文件到master容器
|ifcp_nginx_start.sh| 启动nginx|
|ifcp_nginx_stop.sh| 关闭nginx|
|ifcp_start.sh|启动fastdfs和nginx|
|ifcp_stop.sh|关闭fastdfs和nginx|
|ifcp_fdfs_monitoring.sh|fastdfs的monitor命令|

