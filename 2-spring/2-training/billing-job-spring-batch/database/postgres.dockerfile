FROM postgres:15.1-alpine

LABEL author="Kevin Kouomeu"
LABEL description="Postgres DB for batch training"
LABEL version="1.0"

COPY *.sql /docker-entrypoint-initdb.d/