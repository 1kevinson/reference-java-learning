#!/bin/bash
set -e

docker exec -it postgres-batch mkdir -p /tmp-scripts

docker cp ../database/schema-drop-postgres.sql postgres-batch:/tmp-scripts/schema-drop-postgres.sql
docker cp ../database/schema-create-postgres.sql postgres-batch:/tmp-scripts/schema-create-postgres.sql
docker cp ../database/00-init-batch-tables.sql postgres-batch:/tmp-scripts/00-init-batch-tables.sql

docker exec postgres-batch psql -f /tmp-scripts/schema-drop-postgres.sql -U random -d billing
docker exec postgres-batch psql -f /tmp-scripts/schema-create-postgres.sql -U random -d billing
docker exec postgres-batch psql -f /tmp-scripts/00-init-batch-tables.sql -U random -d billing