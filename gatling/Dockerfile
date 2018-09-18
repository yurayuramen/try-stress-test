FROM openjdk:8u171-jdk-alpine
RUN apk update && apk add unzip curl bash vim
RUN curl -o /root/gatling.zip https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/2.3.1/gatling-charts-highcharts-bundle-2.3.1-bundle.zip
RUN cd /root && unzip /root/gatling.zip
COPY ./src/test/scala/mygatling /root/scala-code

