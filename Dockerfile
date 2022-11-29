FROM openjdk:11-jdk-oracle
WORKDIR message-server
ADD target/message-server.jar app.jar
ENTRYPOINT java -jar app.jar