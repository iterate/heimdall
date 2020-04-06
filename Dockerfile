FROM clojure:openjdk-11-tools-deps

# Fetch deps before copying source for better caching
COPY deps.edn .
RUN clojure -e :deps-fetched

# Then copy source
COPY src src
COPY resources resources

CMD ["clojure", "-A:main"]
