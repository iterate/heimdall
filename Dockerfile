FROM clojure:openjdk-11-lein

WORKDIR /app

# Cache dependencies
ADD project.clj ./
RUN lein deps

# Load application code
COPY src/ ./src/
COPY resources/ ./resources/
RUN lein uberjar

CMD ["java", "-jar", "/app/target/heimdall-0.1.0-SNAPSHOT-standalone.jar"]
