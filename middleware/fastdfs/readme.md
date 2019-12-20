# fastdfs部署

## 使用的系统软件

| 名称 | 描述 |  版本 |
| ---| ---| --- |
|centos| 操作系统 | 7.2.1511
|libfatscommon|FastDFS分离出的一些公用函数包|1.0.38
|nginx|给fastdfs提供http服务|1.12.2
|fastdfs-nginx-module|FastDFS和nginx的关联模块|1.20
|FastDFS|文件服务器|5.11

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
| 解压安装包位置 | /home/ifcp
| fastdfs数据存储位置| /home/ifcp/fastdfs
| fastdfs配置目录| /home/ifcp/fastdfs/conf
| nginx安装位置| /home/ifcp/nginx
| storage数据位置| /home/ifcp/fastdfs/storage
| tracker数据位置| /home/ifcp/fastdfs/tracker

## 安装并部署

### 安装libfatscommon

```bash
# 解压至/home/ifcp
tar –zxvf libfastcommon-1.0.38.tar.gz -C /home/ifcp
# 编译安装
cd /home/ifcp/libfastcommon-1.0.38
./make.sh && ./make.sh install
```

### 安装FastDFS

```bash
# 解压至/home/ifcp
tar -zxvf fastdfs-5.11.tar.gz -C /home/ifcp
# 编译安装
cd /home/ifcp/fastdfs-5.11
./make.sh && ./make.sh install
```
### 部署FastDFS

#### 配置文件管理

在`/etc/fdfs`目录下有fastdfs配置文件的模板(xxx.conf.sample)，为了方便，将它们复制到`/home/ifcp/fastdfs/conf`

```bash
cp -r /etc/fdfs/ /home/ifcp/fastdfs/conf
```

#### 配置tracker

```bash
# 复制一份tracker配置文件，作为运行配置文件
cd /home/ifcp/fastdfs/conf
cp trakcer.conf.sample tracker.conf
# 修改配置文件
vim tracker.conf
```

需要修改的内容如下

```yaml
# 数据和日志文件存储根目录
base_path=/home/ifcp/fastdfs/tracker
# tracker的http端口，默认为8080，因为nginx使用8080，storage需要与nginx一致
http.server_port=8888
```

#### 配置storage

```bash
# 复制一份storage配置文件，作为运行配置文件
cd /home/ifcp/fastdfs/conf
cp storage.conf.sampe storage.conf
# 修改配置文件
vim storage.conf
```
需要修改的内容如下

```yaml
# 数据和日志文件存储根目录
base_path=/home/ifcp/fastdfs/storage 
# 第一个存储目录
store_path0=/home/ifcp/fastdfs/storage  
# tracker服务器IP和端口,有多少台填多少台
tracker_server=172.16.4.97:22122  # 服务器1
tracker_server=172.16.4.98:22122  # 服务器2
# http访问文件的端口(默认8888,看情况修改,和nginx中保持一致)
http.server_port=8080
```

#### tracker启动与关闭

- 启动命令 `/usr/bin/fdfs_trackerd /home/ifcp/fastdfs/conf/tracker.conf start`
- 关闭命令 `/usr/bin/fdfs_trackerd /home/ifcp/fastdfs/conf/tracker.conf stop`或者`killall fdfs_trackerd`

#### storage启动与关闭

- 启动命令 `/usr/bin/fdfs_storaged /home/ifcp/fastdfs/conf/storage.conf start`
- 关闭命令 `/usr/bin/fdfs_storaged /home/ifcp/fastdfs/conf/storage.conf stop`或者`killall fdfs_storaged`

### 安装nginx

nginx主要目的是为了能让fastdfs提供http服务

#### 解压

```bash
# 解压nginx模块至/home/ifcp
tar zxvf fastdfs-nginx-module-1.20.tar.gz -C /home/ifcp
# 解压nginx至/home/ifcp
tar zxvf nginx-1.12.2.tar.gz -C /home/ifcp
```

#### nginx模块配置

```bash
# 修改nginx模块配置文件
vim /home/ifcp/fastdfs-nginx-module-1.20/src/config
```
未修改的样子

```bash
if test -n "${ngx_module_link}"; then
    ngx_module_type=HTTP
    ngx_module_name=$ngx_addon_name
    ngx_module_incs="/usr/local/include"
    ngx_module_libs="-lfastcommon -lfdfsclient"
    ngx_module_srcs="$ngx_addon_dir/ngx_http_fastdfs_module.c"
    ngx_module_deps=
    CFLAGS="$CFLAGS -D_FILE_OFFSET_BITS=64 -DFDFS_OUTPUT_CHUNK_SIZE='256*1024' -DFDFS_MOD_CONF_FILENAME='\"/etc/fdfs/mod_fastdfs.conf\"'"
    . auto/module
else
    HTTP_MODULES="$HTTP_MODULES ngx_http_fastdfs_module"
    NGX_ADDON_SRCS="$NGX_ADDON_SRCS $ngx_addon_dir/ngx_http_fastdfs_module.c"
    CORE_INCS="$CORE_INCS /usr/local/include"
    CORE_LIBS="$CORE_LIBS -lfastcommon -lfdfsclient"
    CFLAGS="$CFLAGS -D_FILE_OFFSET_BITS=64 -DFDFS_OUTPUT_CHUNK_SIZE='256*1024' -DFDFS_MOD_CONF_FILENAME='\"/etc/fdfs/mod_fastdfs.conf\"'"
fi
```
1.修改代码文件位置

   - 将`ngx_module_incs="/usr/include/fastdfs /usr/include/fastcommon/"`改为`ngx_module_incs="/usr/include/fastdfs /usr/include/fastcommon/"`
   - 将`CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"`改为`CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"`
   - 即`/usr/local/include`改为`/usr/include/fastdfs /usr/include/fastcommon/`

2.修改`mod_fastdfs.conf`位置

默认情况下，程序会去`/etc/fdfs/`找`mod_fastdfs.conf`，若找不到会抛出异常（很明显文件内容里的`-DFDFS_MOD_CONF_FILENAME='\"/etc/fdfs/mod_fastdfs.conf\"`）。
因为约定配置文件位置`/home/ifcp/fastdfs/conf`，所以把`-DFDFS_MOD_CONF_FILENAME='\"/etc/fdfs/mod_fastdfs.conf\"`改为`-DFDFS_MOD_CONF_FILENAME='\"/home/ifcp/fastdfs/conf/mod_fastdfs.conf\"`


```bash
cp /home/ifcp/fastdfs/conf/http.conf /etc/fdfs/ #供nginx访问使用
cp /home/ifcp/fastdfs/conf/mime.types /etc/fdfs/ #供nginx访问使用
cp /home/ifcp/fastdfs-nginx-module-1.20/src/mod_fastdfs.conf /home/ifcp/fastdfs/conf
# mod_fastdfs.conf
vim /home/ifcp/fastdfs/conf/mod_fastdfs.conf
# 需要修改的内容如下
tracker_server=172.16.4.97:22122  # 服务器1
tracker_server=172.16.4.98:22122  # 服务器2
# set to false when uri like /M00/00/00/xxx
# set to true when uri like ${group_name}/M00/00/00/xxx, such as group1/M00/xxx
url_have_group_name=true
store_path0=/home/ifcp/fastdfs/storage
```

#### 安装nginx

```bash
cd /home/ifcp/nginx-1.12.2/
# 编译安装
./configure --prefix=/home/ifcp/nginx/ --add-module=/home/ifcp/fastdfs-nginx-module-1.20/src
make
make install
```

```
# 修改nginx配置文件
vim /home/ifcp/nginx/conf/nginx.conf
# 添加如下配置(用户，端口，绑定地址，静态资源路径)
user ifcp
server {
  listen 8080;
  server_name 本机IP;
  
  location /group1/M00/ {
    ngx_fastdfs_module;
  }
}
```
#### 启动

```bash
/home/ifcp/nginx/sbin/nginx
```

### 其他

#### 测试

1.测试nginx`wget http://172.16.4.97:8080`

2.测试上传文件

```bash
# 配置client
cd /home/ifcp/fastdfs/conf
cp client.sample.conf client.conf
vim client.conf
# 修改内容如下
base_path=/home/ifcp/fastdfs/tracker //tracker服务器文件路径
tracker_server=172.16.4.97:22122 //tracker服务器IP地址和端口号
# 上传文件(第二个参数是任意文件）
/usr/bin/fdfs_upload_file client.conf <上传文件>
```

3.测试下载文件`wget http://172.16.4.97:8080/<上传文件成功后返回的路径>`

4.上传和下载可切换不同服务器地址测试，保证成功

#### 查看运行情况

```bash
/usr/bin/fdfs_monitor /home/ifcp/fastdfs/storage.conf
```

#### 注意

1.FastDFS server程序自带start、stop和restart指令，命令行示例如下：
  - `/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf  [start | stop | restart]`
  - `/usr/bin/fdfs_storaged /etc/fdfs/storage.conf  [start | stop | restart]`
  
  可以使用kill或者killall正常杀掉 fdfs_trackerd 和 fdfs_storaged 进程，但千万不要加上-9参数强杀，否则可能会导致binlog数据丢失等问题。