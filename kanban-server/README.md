# Kanban server

## Building
mvn clean install

### Building docker image:
docker build -t myimage:1.0 .

docker image prune --filter label=stage=builder
