# Kanban server

## Building
`mvn clean install`

### Building docker image:
```
docker build -t kanban-server:latest .

docker run --rm -it $(docker build -q .)

docker-compose up -d
```