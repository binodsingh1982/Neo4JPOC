#
# Stufusion Auth Server
#
FROM java:8
MAINTAINER Pulkit Mehra<pmehra@outlook.in>

LABEL Description="This image is used to start the stufusion auth service" 
LABEL Vendor="stufusion" 
LABEL Version="1.0"

VOLUME /tmp
RUN mkdir /app


ADD ./dist/stufusion-oauth-server.jar /app/stufusion-oauth-server.jar
ADD ./dist/wait-for-it.sh /app/wait-for-it.sh

RUN bash -c 'touch /app/stufusion-oauth-server.jar'

WORKDIR /app

ENV JAVA_OPTS=""
ENV APPNAME=stufusion-oauth-server
ENV APPVERSION=""

ENTRYPOINT [ "bash", "-c", "./wait-for-it.sh -t 30 -h $DB_HOST -p $DB_PORT -s -- java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar stufusion-oauth-server.jar" ]

HEALTHCHECK --interval=15s --timeout=10s CMD curl --fail http://localhost:${EXPOSE_PORT}/${CONTEXT}/api/health || exit 1