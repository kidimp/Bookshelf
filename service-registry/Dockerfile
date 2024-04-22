FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
EXPOSE 9001
COPY build/libs/service-registry-0.0.1-SNAPSHOT.jar service-registry.jar
CMD java -jar /app/service-registry.jar