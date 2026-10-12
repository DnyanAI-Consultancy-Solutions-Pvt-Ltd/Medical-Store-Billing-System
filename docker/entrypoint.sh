#!/usr/bin/env bash
set -Eeuo pipefail

: "${POSTGRES_USER:=medicaluser}"
: "${POSTGRES_DB:=medicalbilling}"
: "${POSTGRES_PASSWORD:?Set POSTGRES_PASSWORD in Hugging Face Space Secrets}"
: "${BACKUP_DIR:=/data/backups}"
: "${APP_PORT:=7860}"

export PGPASSWORD="$POSTGRES_PASSWORD"
export SPRING_DATASOURCE_URL="${SPRING_DATASOURCE_URL:-jdbc:postgresql://127.0.0.1:5432/${POSTGRES_DB}}"
export SPRING_DATASOURCE_USERNAME="${SPRING_DATASOURCE_USERNAME:-$POSTGRES_USER}"
export SPRING_DATASOURCE_PASSWORD="${SPRING_DATASOURCE_PASSWORD:-$POSTGRES_PASSWORD}"
export SPRING_DATASOURCE_DRIVER_CLASS_NAME="${SPRING_DATASOURCE_DRIVER_CLASS_NAME:-org.postgresql.Driver}"
export SERVER_PORT="${SERVER_PORT:-$APP_PORT}"

echo "[startup] Starting PostgreSQL service..."
service postgresql start

echo "[startup] Creating/updating database role and database..."
runuser -u postgres -- psql -v ON_ERROR_STOP=1 \
  --set=db_user="$POSTGRES_USER" \
  --set=db_password="$POSTGRES_PASSWORD" \
  --set=db_name="$POSTGRES_DB" <<'SQL'
SELECT format('CREATE ROLE %I LOGIN PASSWORD %L', :'db_user', :'db_password')
WHERE NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = :'db_user') \gexec
SELECT format('ALTER ROLE %I WITH LOGIN PASSWORD %L', :'db_user', :'db_password') \gexec
SELECT format('CREATE DATABASE %I OWNER %I', :'db_name', :'db_user')
WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = :'db_name') \gexec
SQL

echo "[startup] Waiting for PostgreSQL..."
for i in $(seq 1 30); do
  if pg_isready -h 127.0.0.1 -p 5432 >/dev/null 2>&1; then
    break
  fi
  sleep 2
done
pg_isready -h 127.0.0.1 -p 5432

# IMPORTANT: /data must be the read-write Hugging Face Storage Bucket mount.
mkdir -p "$BACKUP_DIR"
if [ ! -w "$BACKUP_DIR" ]; then
  echo "[ERROR] Backup directory $BACKUP_DIR is not writable. Check the Space's bucket mount and read-write setting."
  exit 1
fi

# If the container's ephemeral PostgreSQL database is empty, restore the newest bucket backup.
TABLE_COUNT="$(psql -h 127.0.0.1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" -Atc \
  "SELECT count(*) FROM pg_tables WHERE schemaname NOT IN ('pg_catalog','information_schema');")"
LATEST_BACKUP="$(find "$BACKUP_DIR" -maxdepth 1 -type f -name 'medical-billing-*.dump' -printf '%T@ %p\n' 2>/dev/null | sort -nr | head -n 1 | cut -d' ' -f2- || true)"

if [ "${TABLE_COUNT:-0}" = "0" ] && [ -n "${LATEST_BACKUP:-}" ] && [ -f "$LATEST_BACKUP" ]; then
  echo "[startup] Empty database detected. Restoring newest backup: $(basename "$LATEST_BACKUP")"
  pg_restore --no-owner --no-acl --role="$POSTGRES_USER" \
    -h 127.0.0.1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" "$LATEST_BACKUP"
else
  echo "[startup] No restore needed (tables=$TABLE_COUNT; backup=${LATEST_BACKUP:-none})."
fi

echo "[startup] Creating an initial backup if possible..."
/usr/local/bin/backup-db.sh || echo "[WARNING] Initial backup failed; check bucket mount and logs."

# Periodic backup every 15 minutes. The backup script keeps a rolling set.
(
  while true; do
    sleep 900
    /usr/local/bin/backup-db.sh || echo "[WARNING] Scheduled database backup failed."
  done
) &

echo "[startup] Starting Spring Boot application on port $SERVER_PORT..."
exec java ${JAVA_OPTS:-} -jar /app/app.jar
