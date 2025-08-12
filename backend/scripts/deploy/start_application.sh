#!/bin/bash
set -e

echo "=== Starting application services using Docker Compose ==="

# .env 파일이 있는 배포 루트 디렉터리로 이동
cd /opt/backend-app

# .env 파일 로드 (docker-compose가 자동으로 읽지만, 명시적으로 로드할 수도 있음)
if [ -f .env ]; then
    source .env
    echo "Loaded .env file."
else
    echo "Error: .env file not found!"
    exit 1
fi

# docker-compose.yml 파일을 이용해 모든 서비스를 백그라운드에서 실행
# --detach(-d): 백그라운드 실행
# --force-recreate: 기존 컨테이너가 있어도 강제로 다시 만듦
echo "Starting services defined in docker-compose.yml..."
docker-compose -f docker-compose.yml up --detach --force-recreate

echo "=== Application services started ==="

# 실행 중인 컨테이너 목록 표시
echo "Running containers:"
docker-compose ps