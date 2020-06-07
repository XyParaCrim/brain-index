#!/usr/bin/env bash
kubectl delete statefulset fastdfs-storage
kubectl delete statefulset fastdfs-tracker
kubectl delete svc fastdfs-storage-service
kubectl delete svc fastdfs-tracker-service