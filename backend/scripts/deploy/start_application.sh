#!/bin/bash
# CodeDeploy Hook: ApplicationStart
# This script downloads the JAR files from S3 and starts the Spring Boot applications.

set -e

echo "=== ApplicationStart: Starting application ==="

DEPLOY_DIR="/home/ubuntu/deploy"
APP_DIR="/home/ubuntu/app"
LOG_DIR="$APP_DIR/logs"

# Create log directory if it doesn't exist
mkdir -p "$LOG_DIR"

# Load environment variables from .env file created by GitHub Actions
if [ -f "$DEPLOY_DIR/.env" ]; then
    echo "Loading environment variables from .env file."
    source "$DEPLOY_DIR/.env"
else
    echo "Error: .env file not found in $DEPLOY_DIR!"
    exit 1
fi

# Define services and their properties
declare -A services
services=(
    ["api-server"]="8080"
    ["batch-server"]="8081"
)

# Download and start each service
for svc in "${!services[@]}"; do
    port=${services[$svc]}
    JAR_NAME="$svc.jar"
    S3_PATH="s3://${S3_BUCKET}/artifacts/${ARTIFACT_VERSION}/${JAR_NAME}"
    LOCAL_PATH="$APP_DIR/${JAR_NAME}"
    LOG_FILE="$LOG_DIR/${svc}.log"

    echo "--- Processing service: $svc ---"

    # 1. Download the JAR file from S3
    echo "Downloading $JAR_NAME from $S3_PATH..."
    aws s3 cp "$S3_PATH" "$LOCAL_PATH"

    # 2. Start the application using java -jar
    echo "Starting $svc on port $port..."
    
    # The environment variables sourced from .env will be available to the Java process.
    nohup java -jar \
        -Dspring.profiles.active=prod \
        -Dserver.port=${port} \
        "$LOCAL_PATH" > "$LOG_FILE" 2>&1 &

    echo "$svc started successfully. Log file: $LOG_FILE"
    sleep 5 # Give a moment for the process to initialize
done

echo "=== ApplicationStart completed successfully ==="
