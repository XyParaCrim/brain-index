#!/usr/bin/env bash
if [[ "$2" ]]; then
  /home/ifcp/logstash-7.3.0/bin/logstash -f /home/ifcp/logstash-7.3.0/config/${1}/jdbc-${2}.conf
else
  /home/ifcp/logstash-7.3.0/bin/logstash -f /home/ifcp/logstash-7.3.0/config/${1}/jdbc-${LOGSTASH_OUTPUT_MODE}.conf
fi