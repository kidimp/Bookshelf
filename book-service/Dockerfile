FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
EXPOSE 8081
COPY build/libs/book-service-0.0.1-SNAPSHOT.jar book-service.jar
CMD java -jar /app/book-service.jar