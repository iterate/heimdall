#!/usr/bin/env bash
set -euo pipefail

./build.sh
docker run -p 3000:3000 "heimdall:dev"
