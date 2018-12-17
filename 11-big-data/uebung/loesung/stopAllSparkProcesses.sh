#!/usr/bin/env bash

echo "Stopping all Apache Spark Processes"
../spark-2.3.0-bin-hadoop2.7/sbin/stop-slave.sh
../spark-2.3.0-bin-hadoop2.7/sbin/stop-master.sh