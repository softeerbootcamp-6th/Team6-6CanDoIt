#!/usr/bin/env bash
set -euo pipefail

echo "=== Stopping application services ==="

# If Docker is not installed or daemon isn't running, skip gracefully
if ! command -v docker >/dev/null 2>&1; then
  echo "Docker not installed; nothing to stop."
  exit 0
fi
if ! docker info >/dev/null 2>&1; then
  echo "Docker daemon not running; skipping stop."
  exit 0
fi

# Containers to remove (remove if present; ignore if absent)
containers=(api-server batch-server test-server mysql redis)
for n in "${containers[@]}"; do
  if docker ps -a --format '{{.Names}}' | grep -qx "$n"; then
    echo "Removing container: $n"
    docker rm -f "$n" || true
  else
    echo "Container $n not present"
  fi
done

# Remove dedicated network if it exists
if docker network ls --format '{{.Name}}' | grep -qx 'backend-network'; then
  docker network rm backend-network || true
fi

# Remove volumes if present (ignore errors)
docker volume rm mysql-data >/dev/null 2>&1 || true

# Prune dangling images and networks (safe: only unused resources)
docker image prune -f || true
docker network prune -f || true

echo "=== Application stopped ==="
