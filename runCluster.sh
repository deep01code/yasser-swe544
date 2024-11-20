#!/bin/bash

# Command to run the first instance
nohup java -Dserver.port=8081 \
  -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door1counter \
  -DDOOR_CLUSTER_NUMBER=door1 \
  -DINSTANCE_NUMBER=1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door2 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > door1-instance1.log 2>&1 &

# Command to run the second instance
nohup java -Dserver.port=8082 \
  -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door2counter \
  -DDOOR_CLUSTER_NUMBER=door2 \
  -DINSTANCE_NUMBER=1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door3 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > door2-instance1.log 2>&1 &

# Command to run the third instance
nohup java -Dserver.port=8083 \
  -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door3counter \
  -DDOOR_CLUSTER_NUMBER=door3 \
  -DINSTANCE_NUMBER=1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door4 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > door3-instance1.log 2>&1 &

# Command to run the fourth instance
nohup java -Dserver.port=8084 \
  -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door4counter \
  -DDOOR_CLUSTER_NUMBER=door4 \
  -DINSTANCE_NUMBER=1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door1 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > door4-instance1.log 2>&1 &

echo "All Java processes are running in detached mode. Logs are being written to respective files."
