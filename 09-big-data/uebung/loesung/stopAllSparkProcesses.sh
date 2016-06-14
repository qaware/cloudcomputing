#!/usr/bin/env bash

echo "Stopping all Apache Spark Processes"
././../spark-1.6.1-bin-hadoop2.6/sbin/stop-slave.sh
././../spark-1.6.1-bin-hadoop2.6/sbin/stop-master.sh