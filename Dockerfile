FROM clojure:openjdk-11-tools-deps

# Fetch deps when building container, not at runtime
RUN clojure -e :deps-fetched

CMD clojure -A:main
