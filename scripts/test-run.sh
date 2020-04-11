# Set env vars and try to start

export PORT=8080
export DB_USERNAME="apps-heimdall-test-655e87"
export DB_PORT="5432"
export DB_HOST="localhost"
export DB_PASSWORD="F21ikTH0tE6G2Iw"
export DB_DATABASE="apps-heimdall-test-655e87"

clj -A:main
