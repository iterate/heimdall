# Set env vars and try to start

export PORT=8080
export DB_USERNAME="heimdall"
export DB_PORT="5432"
export DB_HOST="localhost"
export DB_PASSWORD="f7886e6385e2579c9c60bfcc8c064b8a4ff6fc5b"
export DB_DATABASE="heimdall"

clj -A:main
