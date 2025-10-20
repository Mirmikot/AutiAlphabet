#!/bin/bash
# Script to download gradle-wrapper.jar if missing

WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading gradle-wrapper.jar for Gradle 8.7..."
    mkdir -p gradle/wrapper
    curl -L -o "$WRAPPER_JAR" https://github.com/gradle/gradle/raw/v8.7.0/gradle/wrapper/gradle-wrapper.jar
    echo "Downloaded gradle-wrapper.jar successfully"
else
    echo "gradle-wrapper.jar already exists"
fi

