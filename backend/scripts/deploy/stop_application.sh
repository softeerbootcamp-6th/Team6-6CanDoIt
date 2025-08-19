#!/bin/bash
# CodeDeploy Hook: ApplicationStop
# This script stops the running Spring Boot applications.

set -e

echo "=== ApplicationStop: Stopping application ==="

# List of application JARs to stop
JARS=("api-server.jar" "batch-server.jar")

for jar in "${JARS[@]}"; do
    # Find the process ID (PID) of the running Java application
    PID=$(pgrep -f "java -jar.*${jar}")

    if [ -n "$PID" ]; then
        echo "Found running process for $jar with PID: $PID. Stopping it..."
        # Send SIGTERM (15) for a graceful shutdown
        kill -15 "$PID"
        # Wait for the process to terminate
        wait "$PID" 2>/dev/null
        echo "$jar stopped successfully."
    else
        echo "No running process found for $jar. Nothing to stop."
    fi
done

echo "=== ApplicationStop completed successfully ==="
