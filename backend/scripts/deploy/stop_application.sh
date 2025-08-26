#!/bin/bash
# CodeDeploy Hook: ApplicationStop
# This script stops the running Spring Boot applications.

set -e

echo "=== ApplicationStop: Stopping application ==="

JARS=("api-server.jar" "batch-server.jar")
TIMEOUT=30

for jar in "${JARS[@]}"; do
    PIDS=$(pgrep -f "java -jar.*${jar}")

    if [ -z "$PIDS" ]; then
        echo "No running process found for $jar. Nothing to stop."
        continue
    fi

    for PID in $PIDS; do
        echo "Found running process for $jar with PID: $PID. Sending SIGTERM..."
        kill -15 "$PID"

        counter=0
        while ps -p "$PID" > /dev/null; do
            if [ $counter -ge $TIMEOUT ]; then
                echo "Process $PID for $jar did not stop after $TIMEOUT seconds. Forcing shutdown with SIGKILL..."
                kill -9 "$PID"
                break
            fi
            echo "Waiting for $jar (PID: $PID) to stop... ($counter/$TIMEOUT)"
            sleep 1
            ((counter++))
        done

        if ! ps -p "$PID" > /dev/null; then
            echo "$jar (PID: $PID) stopped successfully."
        else
            echo "Failed to stop $jar (PID: $PID)."
        fi
    done
done

echo "=== ApplicationStop completed successfully ==="