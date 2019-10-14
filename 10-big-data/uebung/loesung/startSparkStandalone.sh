#!/usr/bin/env bash

echo "Important: Modify this script and point the slave to your spark master url"

echo "Starting Apache Spark Master"
../spark-2.3.0-bin-hadoop2.7/sbin/start-master.sh

echo "Waiting 5 seconds to let the Master come up"
sleep 5

echo "Starting 2 Apache Spark Slaves:"
#TODO: Set the url to your local master. Note: localhost does not work.
../spark-2.3.0-bin-hadoop2.7/sbin/start-slave.sh "spark://Florians-MacBook-Pro.local:7077"
