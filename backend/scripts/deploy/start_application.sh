#!/bin/bash
# CodeDeploy Hook: ApplicationStart

set -e

echo "=== ApplicationStart: Starting application ==="

DEPLOY_DIR="/home/ubuntu/deploy"
APP_DIR="/home/ubuntu/app"
LOG_DIR="$APP_DIR/logs"
mkdir -p "$LOG_DIR"

# .env 파일 로드
if [ -f "$DEPLOY_DIR/.env" ]; then
    source "$DEPLOY_DIR/.env"
else
    echo "Error: .env file not found!"
    exit 1
fi

# 서비스 포트 정의
declare -A services
services=(
    ["api-server"]="8080"
    ["batch-server"]="8081"
)

# DEPLOY_SERVICE 변수가 없으면 에러
if [ -z "$DEPLOY_SERVICE" ] || [ -z "${services[$DEPLOY_SERVICE]}" ]; then
    echo "Error: DEPLOY_SERVICE is invalid or not set."
    exit 1
fi

# 배포 대상 서비스만 처리
svc=$DEPLOY_SERVICE
port=${services[$svc]}
JAR_NAME="$svc.jar"
S3_PATH="s3://${S3_DEPLOY_BUCKET}/artifacts/${ARTIFACT_VERSION}/${JAR_NAME}"
LOCAL_PATH="$APP_DIR/${JAR_NAME}"
LOG_FILE="$LOG_DIR/${svc}.log"

echo "--- Processing service: $svc ---"

# 1. S3에서 JAR 파일 다운로드
echo "Downloading $JAR_NAME from $S3_PATH..."
aws s3 cp "$S3_PATH" "$LOCAL_PATH"

# 2. 애플리케이션 시작
echo "Starting $svc on port $port..."
nohup java -jar \
    -Dspring.profiles.active=prod \
    -Dserver.port=${port} \
    -Duser.timezone=Asia/Seoul \
    "$LOCAL_PATH" > "$LOG_FILE" 2>&1 &

echo "$svc started successfully. Log file: $LOG_FILE"

echo "=== ApplicationStart completed successfully ==="