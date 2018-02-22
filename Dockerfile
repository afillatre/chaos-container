FROM openjdk:8-jdk-alpine
ENV JVM_ARGS = ""
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ADD entrypoint.sh /bin/entrypoint.sh
ENTRYPOINT /bin/entrypoint.sh