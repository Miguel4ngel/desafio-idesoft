FROM adoptopenjdk/openjdk11:latest

ARG JAR_FILE=./build/libs/desafio-0.0.1-SNAPSHOT.jar

WORKDIR c://Dockers

COPY ${JAR_FILE} desafio-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","desafio-0.0.1-SNAPSHOT.jar"]