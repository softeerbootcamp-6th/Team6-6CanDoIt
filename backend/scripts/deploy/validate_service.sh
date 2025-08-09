#!/bin/bash

# scripts/deploy/validate_service.sh
set -e

echo "=== Validating deployed services ==="

# Function to check service health
check_service() {
    local service_name=$1
    local port=$2
    local max_attempts=30
    local attempt=1

    echo "Checking $service_name on port $port..."

    while [ $attempt -le $max_attempts ]; do
        if curl -f http://localhost:$port/actuator/health &>/dev/null; then
            echo "✅ $service_name is healthy (attempt $attempt/$max_attempts)"
            return 0
        fi

        echo "⏳ $service_name not ready yet (attempt $attempt/$max_attempts)"
        sleep 10
        attempt=$((attempt + 1))
    done

    echo "❌ $service_name failed health check after $max_attempts attempts"
    return 1
}

# Function to check container status
check_container() {
    local container_name=$1

    if [ $(docker ps -q -f name=^${container_name}$ -f status=running) ]; then
        echo "✅ Container $container_name is running"
        return 0
    else
        echo "❌ Container $container_name is not running"
        docker logs $container_name --tail 50 || true
        return 1
    fi
}

# Check container status first
echo "Checking container status..."
#check_container "api-server"
#check_container "batch-server"
check_container "test-server"

# Wait for applications to fully start
echo "Waiting for applications to start up..."
sleep 30

# Check service health endpoints
echo "Checking service health endpoints..."
#check_service "API Server" 8080
#check_service "Batch Server" 8081
check_service "Test Server" 8082

# Final summary
echo ""
echo "=== Deployment Validation Summary ==="
echo "✅ All services are running and healthy"
echo ""
echo "Service URLs:"
#echo "- API Server: http://localhost:8080"
#echo "- Batch Server: http://localhost:8081"
echo "- Test Server: http://localhost:8082"
echo ""
echo "=== Deployment validation completed successfully ==="
