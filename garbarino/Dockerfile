FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD MAVEN_TOOL_CHAIN app.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://garbarino-mongo:27017/test","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]