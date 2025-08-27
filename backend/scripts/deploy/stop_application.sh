#!/bin/bash
# CodeDeploy Hook: ApplicationStop

set -e

echo "=== ApplicationStop: Stopping application ==="

DEPLOY_DIR="/home/ubuntu/deploy"
TIMEOUT=30

# .env 파일에서 배포할 서비스 이름 로드
if [ -f "$DEPLOY_DIR/.env" ]; then
    source "$DEPLOY_DIR/.env"
fi

# DEPLOY_SERVICE 변수가 없으면 스크립트 종료
if [ -z "$DEPLOY_SERVICE" ]; then
    echo "Warning: DEPLOY_SERVICE is not set. Assuming this is the first deployment and skipping stop."
    exit 0
fi

JAR_NAME="${DEPLOY_SERVICE}.jar"
PIDS=$(pgrep -f "java -jar.*${JAR_NAME}")

if [ -z "$PIDS" ]; then
    echo "No running process found for $JAR_NAME. Nothing to stop."
else
    echo "Stopping process for $JAR_NAME (PIDs: $PIDS)..."
    kill -15 $PIDS
    # (기존의 타임아웃 로직 추가...)
fi

echo "=== ApplicationStop completed successfully ==="
