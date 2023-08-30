FROM openjdk:17-jdk-slim-buster
LABEL authors="imuhyeon"

#CMD ["./gradlew", "clean", "package"]

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} ./app.jar

ENV TZ=Asia/Seoul
#ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "./app.jar"]