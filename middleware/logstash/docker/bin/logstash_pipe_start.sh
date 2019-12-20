#!/usr/bin/env bash
if [[ "$2" ]]; then
  LOGSTASH_OUTPUT_MODE = $2
  export LOGSTASH_OUTPUT_MODE
fi

/home/ifcp/logstash-7.3.0/bin/logstash