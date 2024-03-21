# bookshelf-microservice-docker
Java 17 and Spring Boot version 3.2.3 are used.

There are 5 modules: config-server, service-registry, api-gateway, book-service, image-service.

Each module has its own Dockerfile and application.yaml which contain ip, port and other settings.

1. In each microservice, delete the .gradle and build folders.

2. In the root of each microservice, create a Dockerfile file and fill it in.

3. Fill in docker-compose.yaml. This file is located in the root directory for all microservices, e.g.:
<i>C:\Users\Chous\IdeaProjects\bookshelf</i>

4. Run the build of each microservice. Run the build command from the microservice directory, e.g.: 
<i>C:\Users\Chous\IdeaProjects\bookshelf\book-service</i>
   <p><b>./gradlew build</b><p>
   For this command to work you need to set System-About-Advanced system variables-Advanced-EnvironmentVariables
   -JAVA-HOME to the same jdk as in the project.
   <p>To build without tests <b>./gradlew build -x test</b>

5. After we have completed the build of each microservice in Terminal, we return to the common directory, e.g.: 
<i>C:\Users\Chous\IdeaProjects\bookshelf\book-service</i> and execute:
   <p><b>docker-compose up --build</b><p>

<p>
<b>Ports in use:</b><br>

consul 8500 <br>
config-server 8088 <br>
service-registry 9001 <br>
api-gateway 8060 <br>
book-service 8081 <br>
image-service 8082 <br>
