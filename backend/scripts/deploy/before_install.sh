#!/bin/bash

# scripts/deploy/before_install.sh
set -e

echo "=== Before Install: Setting up environment ==="

# Create application directory
sudo mkdir -p /opt/backend
sudo chown -R ec2-user:ec2-user /opt/backend

# Install Docker if not present
if ! command -v docker &> /dev/null; then
    echo "Installing Docker..."
    sudo yum update -y
    sudo yum install -y docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -a -G docker ec2-user
fi

# Install Docker Compose if not present
if ! command -v docker-compose &> /dev/null; then
    echo "Installing Docker Compose..."
    sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
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

# Configure AWS CLI and download Docker images
echo "Downloading Docker images from S3..."
source /opt/backend/.env

# Create directory for docker images
mkdir -p /opt/backend/docker-images

# Download Docker images from S3
aws s3 cp s3://${S3_BUCKET}/docker-images/${IMAGE_TAG}/api-server.tar.gz /opt/backend/docker-images/
aws s3 cp s3://${S3_BUCKET}/docker-images/${IMAGE_TAG}/batch-server.tar.gz /opt/backend/docker-images/
aws s3 cp s3://${S3_BUCKET}/docker-images/${IMAGE_TAG}/test-server.tar.gz /opt/backend/docker-images/

# Load Docker images
echo "Loading Docker images..."
docker load < /opt/backend/docker-images/api-server.tar.gz
docker load < /opt/backend/docker-images/batch-server.tar.gz
docker load < /opt/backend/docker-images/test-server.tar.gz

# Clean up downloaded files
rm -rf /opt/backend/docker-images

echo "=== Before Install completed ==="
