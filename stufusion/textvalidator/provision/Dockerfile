#
# Stufusion notification Server
#
FROM java:8
MAINTAINER Pulkit Mehra<pmehra@outlook.in>

LABEL Description="This image is used to start the stufusion core	 service" 
LABEL Vendor="stufusion" 
LABEL Version="1.0"

VOLUME /tmp
RUN mkdir /app


ADD ./dist/text.analytics.jar /app/text.analytics.jar
ADD ./dist/wait-for-it.sh /app/wait-for-it.sh

RUN bash -c 'touch /app/text.analytics.jar'

WORKDIR /app

ENV JAVA_OPTS=""
ENV APPNAME=stufusion-textanalytics-server
ENV APPVERSION=""

ENTRYPOINT [ "bash", "-c", "  java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar text.analytics.jar" ]

HEALTHCHECK --interval=15s --timeout=10s CMD curl --fail http://localhost:${EXPOSE_PORT}/${CONTEXT}/api/health || exit 1