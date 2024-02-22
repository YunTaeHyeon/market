FROM openjdk:17-alpine
WORKDIR /usr/app/
ARG JAR_FILE=/build/libs/buildfilename.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]