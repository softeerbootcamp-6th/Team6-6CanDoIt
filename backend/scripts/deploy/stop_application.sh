#!/bin/bash
set -e

echo "=== Stopping application services using Docker Compose ==="

# .env 파일이 있는 배포 루트 디렉터리로 이동
cd /opt/backend-app

# docker-compose.yml 파일을 이용해 모든 서비스를 중지하고 컨테이너, 네트워크 제거
# --volumes: docker-compose.yml에 정의된 볼륨까지 제거 (현재는 없으므로 안전)
echo "Stopping and removing services..."
docker-compose -f docker-compose.yml down --volumes

echo "=== Application stopped ==="