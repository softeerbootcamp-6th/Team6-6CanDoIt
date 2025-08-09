#!/bin/bash
set -e

echo "=== Stopping application services using Docker Compose ==="

# 배포된 애플리케이션 디렉터리로 이동
cd /opt/backend-app

# docker-compose.yml 파일이 존재할 경우에만 down 명령 실행
if [ -f docker-compose.yml ]; then
    docker-compose -f docker-compose.yml down --volumes
else
    echo "docker-compose.yml not found. Nothing to stop with compose."
fi

echo "=== Application stopped ==="