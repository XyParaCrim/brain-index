# elasticsearch Dockerfile 尽可能主备服务器模拟

## 容器

- `master` - elasticsearch
- `backup` - elasticsearch
- `vote-only` - elasticsearch
- `kibana` - kibana(web服务，elasticsearch可视化。不是必须的)

## 启动

```bash
docker-compose up
# 如不需要kibana, 则
docker-compose up master backup vote-only
```

## 关闭

```bash
docker-compose stop
```
## 端口

| 名称 | 容器端口 |  宿主端口 |
| ---| ---| --- |
| master | 9200 | 9200
| backup | 9200 | 9201
| vote-only | 9200 | 9202
| kibana | 5601 | 5601

## 容器内域名

- `master` - master容器
- `backup` - backup容器
- `vote-only` - vote-only容器

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
## 简单手动测试建立索引

```bash
# 进入任一容器后
curl -X PUT 'master:9200/weather'

# 查看索引,是否同步索引
curl master:9200/_cat/indices
curl backup:9200/_cat/indices
curl vote-only:9200/_cat/indices
```

## 简单测试宕机情况

```bash
# 进入任一容器后
curl master:9200/_cat/nodes # 查看所有节点
curl master:9200/_cat/master # 查看master节点是谁

# 或者在宿主机
curl localhost:9200/_cat/nodes # 查看所有节点
curl localhost:9200/_cat/master # 查看master节点是谁

# 假设是backup容器，则在宿主机关闭backup容器
docker-compose stop backup

# 再次查看集群状态
curl localhost:9200/_cat/nodes # 查看所有节点
curl localhost:9200/_cat/master # 查看master节点是谁
```

## kibana

kibana可以快速建立增删查改，监控集群状态。

```bash
# 启动kibana容器
docker-compose up kibana
```

浏览器打开[localhost:5601](http://localhost:5601)


