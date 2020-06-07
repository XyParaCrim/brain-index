#!/usr/bin/env bash
fdfs_storaged /etc/fdfs/storage.conf start
/usr/local/nginx/sbin/nginx
tail -f /dev/null