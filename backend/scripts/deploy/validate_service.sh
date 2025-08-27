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

# (기존의 check_process, check_health 함수는 그대로 둠)
# ...

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
# api-server만 health check가 필요하다고 가정
if [ "$svc" == "api-server" ]; then
    check_health "API Server" "$port"
fi

echo "=== Deployment validation completed successfully! ==="