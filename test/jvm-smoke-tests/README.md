# JVM Smoke Tests

This is a small project intended to run some simple smoke tests of Mordant, to facilitate testing Mordant across
multiple JREs.

The intention is that the Mordant jars are built using the canonical JDK (22, at time of writing) and then this
project's tests can be run on all target JREs to make sure that basic functionality works all all targets.

It could also be used to test against multiple Kotlin versions.

To run:

## Build and publish Mordant to maven local

In the root directory:
```shell
./gradlew publishToMavenLocal \
  -PVERSION_NAME="$(if git diff HEAD --quiet; then git rev-parse HEAD; else echo "local"; fi)" \
  --no-configuration-cache
```

## Run the tests

```shell
cd test/jvm-smoke-tests && \
./gradlew test \
  -PVERSION_NAME="$(if git diff HEAD --quiet; then git rev-parse HEAD; else echo "local"; fi)"
```
