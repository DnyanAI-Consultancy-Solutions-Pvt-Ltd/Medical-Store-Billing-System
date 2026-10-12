# Multi-stage build: build the Spring Boot application, then run Java + PostgreSQL.
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY . .
RUN mvn -B -DskipTests package

FROM debian:bookworm-slim
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       openjdk-17-jre-headless postgresql postgresql-contrib cron ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /build/target/*.jar /app/app.jar
COPY docker/entrypoint.sh /usr/local/bin/entrypoint.sh
COPY docker/backup-db.sh /usr/local/bin/backup-db.sh
RUN chmod +x /usr/local/bin/entrypoint.sh /usr/local/bin/backup-db.sh

# Hugging Face serves the web application on 7860.
# PostgreSQL stays internal to this same container on localhost:5432.
EXPOSE 7860
ENV APP_PORT=7860 \
    POSTGRES_DB=medicalbilling \
    POSTGRES_USER=medicaluser \
    BACKUP_DIR=/data/backups

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
