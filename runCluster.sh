#!/bin/bash

######################################################################################################################
# Command to run the first cluster
nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door1counter \
  -DDOOR_CLUSTER_NUMBER=door1 \
  -DINSTANCE_NUMBER=1 \
  -DIDENTIFIER=door1-instance1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door2 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door1counter \
  -DDOOR_CLUSTER_NUMBER=door1 \
  -DINSTANCE_NUMBER=2 \
  -DIDENTIFIER=door1-instance2 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door2 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door1counter \
  -DDOOR_CLUSTER_NUMBER=door1 \
  -DINSTANCE_NUMBER=3 \
  -DIDENTIFIER=door1-instance3 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door2 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

######################################################################################################################
# Command to run the second cluster
nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door2counter \
  -DDOOR_CLUSTER_NUMBER=door2 \
  -DINSTANCE_NUMBER=1 \
  -DIDENTIFIER=door2-instance1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door3 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door2counter \
  -DDOOR_CLUSTER_NUMBER=door2 \
  -DINSTANCE_NUMBER=2 \
  -DIDENTIFIER=door2-instance2 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door3 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door2counter \
  -DDOOR_CLUSTER_NUMBER=door2 \
  -DINSTANCE_NUMBER=3 \
  -DIDENTIFIER=door2-instance3 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door3 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

######################################################################################################################
# Command to run the third cluster
nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door3counter \
  -DDOOR_CLUSTER_NUMBER=door3 \
  -DINSTANCE_NUMBER=1 \
  -DIDENTIFIER=door3-instance1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door4 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door3counter \
  -DDOOR_CLUSTER_NUMBER=door3 \
  -DINSTANCE_NUMBER=2 \
  -DIDENTIFIER=door3-instance2 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door4 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door3counter \
  -DDOOR_CLUSTER_NUMBER=door3 \
  -DINSTANCE_NUMBER=3 \
  -DIDENTIFIER=door3-instance3 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door4 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

######################################################################################################################
# Command to run the fourth cluster
nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door4counter \
  -DDOOR_CLUSTER_NUMBER=door4 \
  -DINSTANCE_NUMBER=1 \
  -DIDENTIFIER=door4-instance1 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door1 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door4counter \
  -DDOOR_CLUSTER_NUMBER=door4 \
  -DINSTANCE_NUMBER=2 \
  -DIDENTIFIER=door4-instance2 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door1 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

nohup java -DADDRESS=localhost \
  -DBOOTSTRAP_SERVERS=localhost:9092 \
  -DDOOR_CLUSTER_CARS_COUNTER_PATH=door4counter \
  -DDOOR_CLUSTER_NUMBER=door4 \
  -DINSTANCE_NUMBER=3 \
  -DIDENTIFIER=door4-instance3 \
  -DNEXT_DOOR_CLUSTER_NUMBER=door1 \
  -jar build/libs/swe544-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

######################################################################################################################
echo "All Java processes are running in detached mode with no output."
