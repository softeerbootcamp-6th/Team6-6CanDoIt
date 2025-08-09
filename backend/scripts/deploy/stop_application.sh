#!/bin/bash

# scripts/deploy/stop_application.sh
set -e

echo "=== Stopping application services ==="

cd /opt/backend-app

# Function to stop container gracefully
stop_container() {
    local container_name=$1
    if [ $(docker ps -q -f name=^${container_name}$) ]; then
        echo "Stopping container: $container_name"
        docker stop $container_name || true
        docker rm $container_name || true
    else
        echo "Container $container_name is not running"
    fi
}

# Stop all application containers
stop_container "api-server"
stop_container "batch-server"
stop_container "test-server"

# Stop infrastructure containers (optional - keep running for zero-downtime)
# stop_container "mysql"
# stop_container "redis"

# Remove old images to free up space (keep last 2 versions)
echo "Cleaning up old Docker images..."
docker image prune -f || true

# Remove unused networks
docker network prune -f || true

echo "=== Application stopped ==="
