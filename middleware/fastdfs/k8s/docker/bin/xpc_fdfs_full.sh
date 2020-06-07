#!/usr/bin/env bash
fdfs_trackerd /etc/fdfs/tracker.conf start
fdfs_storaged /etc/fdfs/storage.conf start
/usr/local/nginx/sbin/nginx
tail -f /dev/null