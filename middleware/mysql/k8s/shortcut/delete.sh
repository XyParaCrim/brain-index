#!/usr/bin/env bash
kubectl delete -f ../controller/mysql-server.yaml
kubectl delete -f ../volume/ff.yaml
rm -rf /Users/yanjiaxun/Kuternetes/persistent-volume/*