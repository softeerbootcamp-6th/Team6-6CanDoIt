#!/bin/bash
# CodeDeploy Hook: ValidateService
# This script validates that the services are running and healthy.

set -e

echo "=== ValidateService: Validating deployed services ==="

# Function to check if a Java process is running
check_process() {
    local jar_name=$1
    echo "Checking if process for $jar_name is running..."
    if pgrep -f "java -jar.*${jar_name}" > /dev/null; then
        echo "✅ Process for $jar_name is running."
        return 0
    else
        echo "❌ Process for $jar_name is NOT running."
        return 1
    fi
}

# Function to check service health via HTTP endpoint
check_health() {
    local service_name=$1
    local port=$2
    local max_attempts=5
    local attempt=1

    echo "Checking health of $service_name on port $port..."

    while [ $attempt -le $max_attempts ]; do
        if curl -s -f "http://localhost:${port}/actuator/health" > /dev/null; then
            echo "✅ $service_name is healthy (attempt $attempt/$max_attempts)."
            return 0
        fi

        echo "⏳ $service_name not ready yet (attempt $attempt/$max_attempts), waiting 10 seconds..."
        sleep 10
        ((attempt++))
    done

    echo "❌ $service_name failed health check after $max_attempts attempts."
    return 1
}

# --- Validation Steps ---

# 1. Check that the processes are running
check_process "api-server.jar"
check_process "batch-server.jar"

# 2. Check the health endpoints
check_health "API Server" 8080
check_health "Batch Server" 8081

echo "=== Deployment validation completed successfully! ==="