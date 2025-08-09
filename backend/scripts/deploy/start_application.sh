#!/bin/bash

# scripts/deploy/start_application.sh
set -e

echo "=== Starting application services ==="

echo "Current directory: $(pwd)"
echo "Files in current directory: $(ls -la)"
echo "Files in /opt/backend-app: $(ls -la /opt/backend-app)"

# Load environment variables
if [ -f /opt/backend-app/.env ]; then
    source /opt/backend-app/.env
else
    echo "Warning: .env file not found at /opt/backend-app/.env"
    exit 1
fi

cd /opt/backend-app

# Load Docker images if they exist in current directory
if [ -d "docker-images" ]; then
    echo "Loading Docker images from local files..."
    if [ -f "docker-images/api-server.tar.gz" ]; then
        docker load < docker-images/api-server.tar.gz
    fi
    if [ -f "docker-images/batch-server.tar.gz" ]; then
        docker load < docker-images/batch-server.tar.gz
    fi
    if [ -f "docker-images/test-server.tar.gz" ]; then
        docker load < docker-images/test-server.tar.gz
    fi
else
    echo "Docker images already loaded from before_install step"
fi

# Create network if it doesn't exist
docker network create backend-network || true

# Start infrastructure services first (if not already running)
echo "Starting infrastructure services..."

# MySQL
if [ ! $(docker ps -q -f name=^mysql$) ]; then
    docker run -d \
        --name mysql \
        --network backend-network \
        -p 3306:3306 \
        -e MYSQL_ROOT_PASSWORD=root_pass \
        -e MYSQL_DATABASE=backend_db \
        -e MYSQL_USER=backend_user \
        -e MYSQL_PASSWORD=backend_pass \
        -v mysql-data:/var/lib/mysql \
        --restart unless-stopped \
        mysql:8.0

    # Wait for MySQL to be ready
    echo "Waiting for MySQL to be ready..."
    sleep 30
fi

# Start application services
echo "Starting application services..."

# API Server
docker run -d \
    --name api-server \
    --network backend-network \
    -p 8080:8080 \
    -e SPRING_PROFILES_ACTIVE=docker \
    -e SERVER_PORT=8080 \
    -e DB_HOST=mysql \
    -e DB_PORT=3306 \
    -e DB_NAME=backend_db \
    -e DB_USER=backend_user \
    -e DB_PASSWORD=backend_pass \
    --restart unless-stopped \
    $API_SERVER_IMAGE

# Batch Server
docker run -d \
    --name batch-server \
    --network backend-network \
    -p 8081:8080 \
    -e SPRING_PROFILES_ACTIVE=docker \
    -e SERVER_PORT=8080 \
    -e DB_HOST=mysql \
    -e DB_PORT=3306 \
    -e DB_NAME=backend_db \
    -e DB_USER=backend_user \
    -e DB_PASSWORD=backend_pass \
    -e BATCH_JOB_ENABLED=true \
    --restart unless-stopped \
    $BATCH_SERVER_IMAGE

# Test Server
docker run -d \
    --name test-server \
    --network backend-network \
    -p 8082:8080 \
    -e SPRING_PROFILES_ACTIVE=docker \
    -e SERVER_PORT=8080 \
    -e DB_HOST=mysql \
    -e DB_PORT=3306 \
    -e DB_NAME=backend_db \
    -e DB_USER=backend_user \
    -e DB_PASSWORD=backend_pass \
    --restart unless-stopped \
    $TEST_SERVER_IMAGE

echo "=== Application services started ==="

# Show running containers
echo "Running containers:"
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
