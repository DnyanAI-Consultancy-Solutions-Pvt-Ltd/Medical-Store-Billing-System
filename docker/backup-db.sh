#!/usr/bin/env bash
set -Eeuo pipefail

: "${POSTGRES_USER:=medicaluser}"
: "${POSTGRES_DB:=medicalbilling}"
: "${POSTGRES_PASSWORD:?POSTGRES_PASSWORD must be set}"
: "${BACKUP_DIR:=/data/backups}"

export PGPASSWORD="$POSTGRES_PASSWORD"
mkdir -p "$BACKUP_DIR"

stamp="$(date -u +%Y%m%dT%H%M%SZ)"
tmp="$BACKUP_DIR/.medical-billing-${stamp}.dump.tmp"
final="$BACKUP_DIR/medical-billing-${stamp}.dump"

pg_dump -h 127.0.0.1 -p 5432 -U "$POSTGRES_USER" -d "$POSTGRES_DB" \
  --format=custom --no-owner --no-acl --file="$tmp"
mv "$tmp" "$final"

# Keep the newest 96 timestamped backups (approximately 24 hours at 15-minute intervals).
mapfile -t old_backups < <(
  find "$BACKUP_DIR" -maxdepth 1 -type f -name 'medical-billing-*.dump' -printf '%T@ %p\n' \
    | sort -nr | tail -n +97 | cut -d' ' -f2-
)
if [ "${#old_backups[@]}" -gt 0 ]; then
  printf '%s\n' "${old_backups[@]}" | xargs -r rm -f --
fi

echo "[backup] Saved $final"
