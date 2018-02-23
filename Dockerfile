ARG BASE_IMAGE
ARG JAR_FILE

FROM $BASE_IMAGE

ENV JVM_ARGS = ""

ADD ${JAR_FILE} app.jar
ADD entrypoint.sh /bin/entrypoint.sh

VOLUME /tmp

ENTRYPOINT /bin/entrypoint.sh