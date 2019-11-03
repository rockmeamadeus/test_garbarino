FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://garbarino-mongo:27017/test","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
