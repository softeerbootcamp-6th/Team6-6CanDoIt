#!/bin/bash
# CodeDeploy Hook: BeforeInstall
# This script prepares the EC2 instance for deployment.

set -e

echo "=== BeforeInstall: Setting up environment ==="

# Update package list
sudo apt-get update -y

# Install Java 17 (Temurin)
if ! command -v java &>/dev/null || ! java -version 2>&1 | grep -q "17."; then
    echo "Installing Java 17..."
    sudo apt-get install -y wget apt-transport-https
    wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo gpg --dearmor -o /usr/share/keyrings/adoptium.gpg
    echo "deb [signed-by=/usr/share/keyrings/adoptium.gpg] https://packages.adoptium.net/artifactory/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/adoptium.list
    sudo apt-get update -y
    sudo apt-get install -y temurin-17-jdk
fi

# Install AWS CLI if not present
if ! command -v aws &> /dev/null; then
    echo "Installing AWS CLI..."
    sudo apt-get install -y unzip
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install
    rm -rf awscliv2.zip aws
fi

# Create application directory
# This directory will store the running JARs and logs.
sudo mkdir -p /home/ubuntu/app
sudo chown -R ubuntu:ubuntu /home/ubuntu/app

echo "=== BeforeInstall completed successfully ==="
