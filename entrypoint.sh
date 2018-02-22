#!/bin/sh

java -Djava.security.egd=file:/dev/./urandom ${JAVA_ARGS} -jar /app.jar