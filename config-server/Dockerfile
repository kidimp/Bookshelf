FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
EXPOSE 8088:8088
COPY build/libs/config-server-0.0.1-SNAPSHOT.jar config-server.jar
CMD java -jar /app/config-server.jar