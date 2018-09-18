FROM openjdk:8u171-jdk-alpine3.8
RUN apk update && apk add bash unzip
COPY sbt-1.2.1.zip /sbt/sbt-1.2.1.zip
RUN unzip /sbt/sbt-1.2.1.zip
COPY target/universal/stage /app
