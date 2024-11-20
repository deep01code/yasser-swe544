#!/bin/sh

# Extract the slot number from the hostname
INSTANCE_NUMBER="INSTANCE_${HOSTNAME##*-}"

# Export INSTANCE_NUMBER to be available in the application
export INSTANCE_NUMBER

# Start the application
exec java -jar /path/to/your/app.jar
