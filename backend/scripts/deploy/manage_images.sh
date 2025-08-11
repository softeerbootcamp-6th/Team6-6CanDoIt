#!/bin/bash

# scripts/deploy/manage_images.sh
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
IMAGE_TAG=${IMAGE_TAG:-latest}

# Function to download and load images from S3
download_and_load_images() {
    echo "=== Downloading Docker images from S3 ==="

    local s3_bucket=$1
    local image_tag=$2

    if [ -z "$s3_bucket" ] || [ -z "$image_tag" ]; then
        echo "Error: S3 bucket and image tag are required"
        return 1
    fi

    # Create temporary directory for downloads
    local temp_dir=$(mktemp -d)
    echo "Using temporary directory: $temp_dir"
    cd "$temp_dir"

    # Define services to download
    local services=("api-server" "batch-server")

    # Download all images first
    for service in "${services[@]}"; do
        echo "Downloading ${service} image for tag: ${image_tag}"
        aws s3 cp "s3://${s3_bucket}/docker-images/${image_tag}/${service}.tar.gz" . || {
            echo "❌ Failed to download ${service} image"
            # Clean up and exit if any download fails
            cd /
            rm -rf "$temp_dir"
            return 1
        }
    done

    # Load images after all downloads are complete
    echo "Loading Docker images..."
    for service in "${services[@]}"; do
        if [ -f "${service}.tar.gz" ]; then
            echo "Loading ${service}..."
            # Decompress the .gz file and pipe it to docker load
            gunzip -c "${service}.tar.gz" | docker load
            echo "✅ Loaded ${service}"
        else
            echo "⚠️ Warning: ${service}.tar.gz not found, skipping load."
        fi
    done

    # Clean up the temporary directory
    echo "Cleaning up temporary files..."
    cd /
    rm -rf "$temp_dir"

    echo "=== Images loaded successfully ==="
}

# Function to save and upload images to S3
save_and_upload_images() {
    echo "=== Saving and uploading Docker images to S3 ==="

    local s3_bucket=$1
    local image_tag=$2

    if [ -z "$s3_bucket" ] || [ -z "$image_tag" ]; then
        echo "Error: S3 bucket and image tag are required"
        return 1
    fi

    # Create temporary directory
    local temp_dir=$(mktemp -d)
    cd "$temp_dir"

    # Save images
    echo "Saving Docker images..."
    docker save api-server:${image_tag} | gzip > api-server.tar.gz
    docker save batch-server:${image_tag} | gzip > batch-server.tar.gz

    # Upload to S3
    echo "Uploading images to S3..."
    aws s3 cp api-server.tar.gz s3://${S3_BUCKET}/docker-images/${image_tag}/
    aws s3 cp batch-server.tar.gz s3://${S3_BUCKET}/docker-images/${image_tag}/

    # Clean up
    cd /
    rm -rf "$temp_dir"

    echo "=== Images uploaded successfully ==="
}

# Function to clean up old images from S3
cleanup_old_images() {
    echo "=== Cleaning up old images from S3 ==="

    local s3_bucket=$1
    local keep_versions=${2:-5}

    if [ -z "$s3_bucket" ]; then
        echo "Error: S3 bucket is required"
        return 1
    fi

    # List all image versions and keep only the latest N versions
    aws s3api list-objects-v2 \
        --bucket "$s3_bucket" \
        --prefix "docker-images/" \
        --query "Contents[?LastModified<=\`$(date -d "${keep_versions} days ago" -Iseconds)\`].Key" \
        --output text | while read -r key; do
        if [ -n "$key" ] && [ "$key" != "None" ]; then
            echo "Deleting old image: $key"
            aws s3 rm "s3://${S3_BUCKET}/${key}"
        fi
    done

    echo "=== Cleanup completed ==="
}

# Function to list available images in S3
list_s3_images() {
    echo "=== Available images in S3 ==="

    local s3_bucket=$1

    if [ -z "$s3_bucket" ]; then
        echo "Error: S3 bucket is required"
        return 1
    fi

    aws s3 ls s3://${S3_BUCKET}/docker-images/ --recursive --human-readable
}

# Main execution
case "$1" in
    download)
        download_and_load_images "$2" "$3"
        ;;
    upload)
        save_and_upload_images "$2" "$3"
        ;;
    cleanup)
        cleanup_old_images "$2" "$3"
        ;;
    list)
        list_s3_images "$2"
        ;;
    *)
        echo "Usage: $0 {download|upload|cleanup|list} [s3_bucket] [image_tag]"
        echo ""
        echo "Commands:"
        echo "  download <bucket> <tag>  - Download and load images from S3"
        echo "  upload <bucket> <tag>    - Save and upload images to S3"
        echo "  cleanup <bucket> [days]  - Clean up old images (default: keep 5 days)"
        echo "  list <bucket>            - List available images in S3"
        exit 1
        ;;
esac
