#!/bin/bash

echo "Shutting down cluster..."

# Find and kill Java processes matching the JAR file
pids=$(ps aux | grep "build/libs/swe544-0.0.1-SNAPSHOT.jar" | grep -v grep | awk '{print $2}')

if [ -z "$pids" ]; then
  echo "No Java processes found for the cluster."
else
  echo "Killing the following processes: $pids"
  kill -9 $pids
  echo "Cluster processes have been terminated."
fi
