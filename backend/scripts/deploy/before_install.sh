#!/bin/bash

# deploy/before_install.sh
set -e

echo "=== Before Install: Setting up environment ==="

# Create application directory (appspec.yml에서 /opt/backend-app으로 설정했으므로 통일)
sudo mkdir -p /opt/backend-app
sudo chown -R ubuntu:ubuntu /opt/backend-app

# Update package list
sudo apt-get update -y

# Install Docker if not present
if ! command -v docker &> /dev/null; then
    echo "Installing Docker..."
    sudo apt-get install -y ca-certificates curl gnupg lsb-release

    # Add Docker's official GPG key
    sudo mkdir -m 0755 -p /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

    # Set up repository
    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
      $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

    # Install Docker Engine
    sudo apt-get update -y
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

    # Start and enable Docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -a -G docker ubuntu
fi

# Install Docker Compose standalone if not present
if ! command -v docker-compose &> /dev/null; then
    echo "Installing Docker Compose..."
    sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
fi

# Install unzip if not present (AWS CLI 설치에 필요)
if ! command -v unzip &> /dev/null; then
    echo "Installing unzip..."
    sudo apt-get install -y unzip
fi

# Install AWS CLI if not present
if ! command -v aws &> /dev/null; then
    echo "Installing AWS CLI..."
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install
    rm -rf aws awscliv2.zip
fi

# Start Docker service
sudo systemctl start docker

# .env 파일이 존재하는지 확인 후 환경변수 로드
if [ -f /opt/backend-app/.env ]; then
    echo "Loading environment variables..."
    source /opt/backend-app/.env

    # 환경변수가 제대로 설정되었는지 확인
    if [ -z "$IMAGE_TAG" ] || [ -z "$S3_BUCKET" ]; then
        echo "Error: Required environment variables not set"
        exit 1
    fi

    echo "Downloading Docker images from S3..."

    # Create directory for docker images
    mkdir -p /opt/backend-app/docker-images

    # Download Docker images from S3
    aws s3 cp s3://${S3_BUCKET}/docker-images/${IMAGE_TAG}/api-server.tar.gz /opt/backend-app/docker-images/
    aws s3 cp s3://${S3_BUCKET}/docker-images/${IMAGE_TAG}/batch-server.tar.gz /opt/backend-app/docker-images/
    aws s3 cp s3://${S3_BUCKET}/docker-images/${IMAGE_TAG}/test-server.tar.gz /opt/backend-app/docker-images/

    # Load Docker images
    echo "Loading Docker images..."
    docker load < /opt/backend-app/docker-images/api-server.tar.gz
    docker load < /opt/backend-app/docker-images/batch-server.tar.gz
    docker load < /opt/backend-app/docker-images/test-server.tar.gz

    # Clean up downloaded files
    rm -rf /opt/backend-app/docker-images
else
    echo "Warning: .env file not found at /opt/backend-app/.env"
    echo "Skipping Docker image download..."
fi

echo "=== Before Install completed ==="