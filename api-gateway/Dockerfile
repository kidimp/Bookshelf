FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
EXPOSE 8060:8060
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
CMD java -jar /app/api-gateway.jar