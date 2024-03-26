FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
LABEL name="image-service"
EXPOSE 8082
COPY build/libs/image-service-0.0.1-SNAPSHOT.jar image-service.jar
CMD java -jar /app/image-service.jar