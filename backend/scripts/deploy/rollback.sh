#!/bin/bash

# scripts/deploy/rollback.sh
set -e

echo "=== Starting rollback process ==="

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROLLBACK_TAG=${1:-"previous"}

if [ -z "$ROLLBACK_TAG" ]; then
    echo "Usage: $0 <rollback_tag>"
    echo "Example: $0 abc123def"
    exit 1
fi

# Load current environment
if [ -f /opt/backend/.env ]; then
    source /opt/backend/.env
fi

echo "Rolling back to image tag: $ROLLBACK_TAG"

# Function to stop current containers
stop_current_containers() {
    echo "Stopping current containers..."

    docker stop api-server || true
    docker stop batch-server || true

    docker rm api-server || true
    docker rm batch-server || true
}

# Function to download and load rollback images
load_rollback_images() {
    echo "Loading rollback images..."

    # Download images using the manage_images script
    ${SCRIPT_DIR}/manage_images.sh download "$S3_BUCKET" "$ROLLBACK_TAG"
}

# Function to start containers with rollback images
start_rollback_containers() {
    echo "Starting containers with rollback images..."

    # Create network if it doesn't exist
    docker network create backend-network || true

    # Start application services with rollback images
    docker run -d \
        --name api-server \
        --network backend-network \
        -p 8080:8080 \
        -e SPRING_PROFILES_ACTIVE=prod \
        -e SERVER_PORT=8080 \
        -e DB_HOST=mysql \
        -e DB_PORT=3306 \
        -e DB_NAME=backend_db \
        -e DB_USER=backend_user \
        -e DB_PASSWORD=backend_pass \
        --restart unless-stopped \
        api-server:${ROLLBACK_TAG}

    docker run -d \
        --name batch-server \
        --network backend-network \
        -p 8081:8080 \
        -e SPRING_PROFILES_ACTIVE=prod \
        -e SERVER_PORT=8080 \
        -e DB_HOST=mysql \
        -e DB_PORT=3306 \
        -e DB_NAME=backend_db \
        -e DB_USER=backend_user \
        -e DB_PASSWORD=backend_pass \
        -e BATCH_JOB_ENABLED=true \
        --restart unless-stopped \
        batch-server:${ROLLBACK_TAG}
}

# Function to validate rollback
validate_rollback() {
    echo "Validating rollback..."

    # Wait for services to start
    sleep 30

    # Check if containers are running
    if ! docker ps | grep -q "api-server"; then
        echo "❌ API Server rollback failed"
        return 1
    fi

    if ! docker ps | grep -q "batch-server"; then
        echo "❌ Batch Server rollback failed"
        return 1
    fi

    # Health checks
    local max_attempts=10
    local attempt=1

    while [ $attempt -le $max_attempts ]; do
        if curl -f http://localhost:8080/actuator/health &>/dev/null && \
           curl -f http://localhost:8081/actuator/health &>/dev/null ; then
            echo "✅ Rollback validation successful"
            return 0
        fi

        echo "⏳ Waiting for services to be healthy (attempt $attempt/$max_attempts)"
        sleep 10
        attempt=$((attempt + 1))
    done

    echo "❌ Rollback validation failed"
    return 1
}

# Update environment file with rollback tag
update_env_file() {
    echo "Updating environment file..."

    cat > /opt/backend/.env << EOF
IMAGE_TAG=$ROLLBACK_TAG
S3_BUCKET=$S3_BUCKET
API_SERVER_IMAGE=api-server:$ROLLBACK_TAG
BATCH_SERVER_IMAGE=batch-server:$ROLLBACK_TAG
EOF
}

# Execute rollback
main() {
    echo "Starting rollback to tag: $ROLLBACK_TAG"

    stop_current_containers
    load_rollback_images
    start_rollback_containers

    if validate_rollback; then
        update_env_file
        echo "✅ Rollback completed successfully"
        echo "Services are now running with image tag: $ROLLBACK_TAG"
    else
        echo "❌ Rollback validation failed"
        echo "Please check the logs and try manual recovery"
        exit 1
    fi
}

# Run main function
main
