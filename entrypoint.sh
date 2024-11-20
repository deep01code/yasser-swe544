#!/bin/sh

# Wait for Zookeeper
while ! nc -z kafka 2181; do
  echo "Waiting for Zookeeper..."
  sleep 2
done

# Wait for Kafka
while ! nc -z kafka 9092; do
  echo "Waiting for Kafka..."
  sleep 2
done

# Extract the slot number from the hostname
INSTANCE_NUMBER="INSTANCE_${HOSTNAME##*-}"

# Export INSTANCE_NUMBER to be available in the application
export INSTANCE_NUMBER

# Start the application
exec java -jar /path/to/your/app.jar
