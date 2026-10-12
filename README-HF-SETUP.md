# Medical Billing System: Spring Boot + PostgreSQL in one Hugging Face Docker Space

## Important persistence warning
This image runs PostgreSQL inside the same container as Spring Boot. PostgreSQL's live data directory remains on the Space's ephemeral container filesystem. The Hugging Face Storage Bucket is used for database dump backups under `/data/backups`; it is NOT used as PostgreSQL's live `PGDATA`.

This reduces risk by restoring the newest available backup after a fresh container starts, but it is not a zero-data-loss or production-grade HA setup. A failure can lose transactions since the last successful backup (the scheduled interval is 15 minutes). Keep an independent backup and test restores. Do not delete Neon until migration and restore testing are complete.

## Files
- `Dockerfile`: builds the existing Maven project and installs Java + PostgreSQL.
- `docker/entrypoint.sh`: starts PostgreSQL, creates the DB/user, restores newest backup into an empty DB, starts Spring Boot.
- `docker/backup-db.sh`: creates compressed/custom-format PostgreSQL backups in the bucket mount.

## Hugging Face Space setup
1. In the existing Space settings, attach your Storage Bucket as a **read-write** volume mounted at `/data` (or change `BACKUP_DIR` to a subdirectory of the mount path you actually use).
2. Keep the Space SDK set to Docker and `app_port: 7860` in the README YAML.
3. Add `POSTGRES_PASSWORD` under Space Settings -> Variables and secrets as a **Secret**. Use a strong unique password. Do not put it in Git or the Dockerfile.
4. Optional variables:
   - `POSTGRES_USER` (default `medicaluser`)
   - `POSTGRES_DB` (default `medicalbilling`)
   - `BACKUP_DIR` (default `/data/backups`)
   - `SPRING_DATASOURCE_URL` (default `jdbc:postgresql://127.0.0.1:5432/${POSTGRES_DB}`)
   - `SPRING_DATASOURCE_USERNAME` (default `POSTGRES_USER`)
   - `SPRING_DATASOURCE_PASSWORD` (default `POSTGRES_PASSWORD`)
   - `SERVER_PORT` (default `7860`)
5. Push the files to the existing Space. Do not remove Neon or its credentials until you have verified the new app and restored data.

## Spring Boot configuration
Make sure `src/main/resources/application.properties` does not hard-code the Neon connection. Use:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://127.0.0.1:5432/medicalbilling}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:medicaluser}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
server.port=${SERVER_PORT:7860}
```

The project must include the PostgreSQL JDBC driver dependency.

## First deployment checklist
- Confirm the bucket is attached at `/data` and read-write.
- Confirm logs show PostgreSQL ready and Spring Boot started.
- Create a test medicine and a test bill.
- Check that a `.dump` backup appears under the bucket's `backups` directory.
- Test a restore into a separate test database before relying on this setup.
- Keep a copy of any old Neon data until you have confirmed all expected records are present.

## Production recommendation
For a real multi-user billing system, use managed PostgreSQL with supported persistent storage and backups; use the bucket for independent backup copies. This same-container setup is a workaround and has a backup recovery-point gap.
