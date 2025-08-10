#!/bin/bash

# scripts/deploy/cleanup_s3.sh
set -e

# .env 파일 로드
if [ -f /opt/backend-app/.env ]; then
    set -a
    source /opt/backend-app/.env
    set +a
fi

echo "=== S3 Docker Images Cleanup ==="

S3_BUCKET=${1:-$S3_BUCKET}
RETENTION_DAYS=${2:-7}

if [ -z "$S3_BUCKET" ]; then
    echo "Error: S3 bucket name is required"
    echo "Usage: $0 <s3_bucket> [retention_days]"
    exit 1
fi

echo "Bucket: $S3_BUCKET"
echo "Retention: $RETENTION_DAYS days"

# Calculate cutoff date
CUTOFF_DATE=$(date -d "$RETENTION_DAYS days ago" +%Y-%m-%d)
echo "Deleting images older than: $CUTOFF_DATE"

# List and delete old image directories
echo "Finding old image directories..."

aws s3api list-objects-v2 \
    --bucket "$S3_BUCKET" \
    --prefix "docker-images/" \
    --delimiter "/" \
    --query "CommonPrefixes[].Prefix" \
    --output text | while read -r prefix; do

    if [ -n "$prefix" ] && [ "$prefix" != "None" ]; then
        # Extract image tag from prefix (docker-images/TAG/)
        IMAGE_TAG=$(basename "$prefix" | sed 's/\///g')

        # Get the creation date of the first object in this directory
        FIRST_OBJECT=$(aws s3api list-objects-v2 \
            --bucket "$S3_BUCKET" \
            --prefix "$prefix" \
            --max-items 1 \
            --query "Contents[0].LastModified" \
            --output text)

        if [ -n "$FIRST_OBJECT" ] && [ "$FIRST_OBJECT" != "None" ]; then
            OBJECT_DATE=$(date -d "$FIRST_OBJECT" +%Y-%m-%d)

            if [[ "$OBJECT_DATE" < "$CUTOFF_DATE" ]]; then
                echo "Deleting old images for tag: $IMAGE_TAG (created: $OBJECT_DATE)"

                # Delete all objects with this prefix
                aws s3 rm "s3://$S3_BUCKET/$prefix" --recursive

                echo "✅ Deleted images for tag: $IMAGE_TAG"
            else
                echo "Keeping images for tag: $IMAGE_TAG (created: $OBJECT_DATE)"
            fi
        fi
    fi
done

# Clean up deployment archives older than retention period
echo ""
echo "Cleaning up old deployment archives..."

aws s3api list-objects-v2 \
    --bucket "$S3_BUCKET" \
    --prefix "deployments/" \
    --query "Contents[?LastModified<=\`$(date -d '$RETENTION_DAYS days ago' -Iseconds)\`].Key" \
    --output text | while read -r key; do

    if [ -n "$key" ] && [ "$key" != "None" ]; then
        echo "Deleting old deployment: $key"
        aws s3 rm "s3://$S3_BUCKET/$key"
    fi
done

# Show remaining storage usage
echo ""
echo "=== Remaining S3 Storage Usage ==="
aws s3 ls "s3://$S3_BUCKET/docker-images/" --recursive --human-readable --summarize

echo ""
echo "=== Cleanup completed ==="
