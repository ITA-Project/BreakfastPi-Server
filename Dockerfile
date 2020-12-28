FROM openjdk:8-alpine
VOLUME /tmp
VOLUME /log
COPY ./target/BreakfastPiServer.jar app.jar
CMD ["java", "-jar", "app.jar"]