#!/bin/bash

echo "Waiting for Zookeeper to be ready..."
while ! nc -z zookeeper 2181; do
    sleep 2
done
echo "Zookeeper is ready. Starting Kafka..."

# Start Kafka
exec /etc/confluent/docker/run
