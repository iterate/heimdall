#!/usr/bin/env bash
set -euo pipefail

./build.sh
docker run -p 5000:5000 -e PORT=5000 "heimdall:dev"
