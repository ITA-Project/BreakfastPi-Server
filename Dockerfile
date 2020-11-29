FROM openjdk:8-alpine
COPY ./target/BreakfastPiServer.jar app.jar
CMD ["java", "-jar", "app.jar"]