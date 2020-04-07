FROM clojure:openjdk-11-tools-deps

# Fetch deps before copying source for better caching
COPY deps.edn .
RUN clojure -e :deps-fetched

# Fetch test deps
RUN clojure -A:test:runner

# Then copy source
COPY src src
COPY resources resources
COPY test test

# Run tests
RUN clojure -A:test:runner

# We should actually test too
# I've pushed things that won't compile a few times now.


CMD ["clojure", "-A:main"]
