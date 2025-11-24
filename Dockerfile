FROM amazoncorretto:21-alpine

WORKDIR /app

COPY build/libs/*SNAPSHOT.jar app.jar
COPY entrypoint.sh /app/entrypoint.sh

RUN chmod +x /app/entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/app/entrypoint.sh"]
