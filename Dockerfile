# https://habr.com/ru/post/457476/
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/moneykeeper.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8090
