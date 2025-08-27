#!/bin/bash
# CodeDeploy Hook: ValidateService

set -e

echo "=== ValidateService: Validating deployed service ==="

DEPLOY_DIR="/home/ubuntu/deploy"

# .env 파일 로드
if [ -f "$DEPLOY_DIR/.env" ]; then
    source "$DEPLOY_DIR/.env"
fi

if [ -z "$DEPLOY_SERVICE" ]; then
    echo "Error: DEPLOY_SERVICE not set."
    exit 1
fi

# Java 프로세스 확인 함수
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

# Health check 함수
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

# 서비스 포트 정의
declare -A services
services=(
    ["api-server"]="8080"
    ["batch-server"]="8081"
)

# 배포 대상 서비스만 검증
svc=$DEPLOY_SERVICE
port=${services[$svc]}
JAR_NAME="$svc.jar"

echo "--- Validating service: $svc ---"
check_process "$JAR_NAME"

if [ "$svc" == "api-server" ]; then
    check_health "API Server" "$port"
fi

echo "=== Deployment validation completed successfully! ==="