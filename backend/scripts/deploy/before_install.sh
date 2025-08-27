#!/bin/bash
# CodeDeploy Hook: BeforeInstall

set -e

DEPLOY_DIR="/home/ubuntu/deploy"

if [ -d "$DEPLOY_DIR" ]; then
    echo "Cleaning up old deployment files in $DEPLOY_DIR..."
    sudo rm -rf "$DEPLOY_DIR"
fi

# 새 배포를 위해 디렉토리를 재생성
echo "Creating deployment directory $DEPLOY_DIR..."
sudo mkdir -p "$DEPLOY_DIR"
sudo chown ubuntu:ubuntu "$DEPLOY_DIR"

echo "=== BeforeInstall: Setting up environment ==="

# Java 17 (Temurin) 설치 확인 및 설치
if ! command -v java &>/dev/null || ! java -version 2>&1 | grep -q "17."; then
    echo "Installing Java 17..."
    sudo apt-get update -y
    sudo apt-get install -y wget apt-transport-https
    wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo gpg --dearmor -o /usr/share/keyrings/adoptium.gpg
    echo "deb [signed-by=/usr/share/keyrings/adoptium.gpg] https://packages.adoptium.net/artifactory/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/adoptium.list
    sudo apt-get update -y
    sudo apt-get install -y temurin-17-jdk
fi

# AWS CLI 설치 확인 및 설치
if ! command -v aws &> /dev/null; then
    echo "Installing AWS CLI..."
    sudo apt-get install -y unzip
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install
    rm -rf awscliv2.zip aws
fi

# 애플리케이션 디렉토리 생성
sudo mkdir -p /home/ubuntu/app
sudo chown -R ubuntu:ubuntu /home/ubuntu/app

echo "=== BeforeInstall completed successfully ==="