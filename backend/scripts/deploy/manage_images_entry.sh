#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUNDLE_ROOT="$(dirname "$SCRIPT_DIR")"

# .env 로드 (번들 루트 → /opt/backend-app 순서)
for f in "$BUNDLE_ROOT/.env" "/opt/backend-app/.env"; do
  [ -f "$f" ] && { set -a; . "$f"; set +a; break; }
done

exec "$SCRIPT_DIR/manage_images.sh" download "${S3_BUCKET}" "${IMAGE_TAG}"
