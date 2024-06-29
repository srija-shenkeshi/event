FROM bitnami/java:17

EXPOSE 8080

WORKDIR /app

COPY /build/libs/event-0.0.1-SNAPSHOT.jar /app/event-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "./event-0.0.1-SNAPSHOT.jar"]