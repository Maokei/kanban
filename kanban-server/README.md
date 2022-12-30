# Kanban server

## Building
`mvn clean install`

### Building docker image:
```
docker build -t myimage:1.0 .

docker run --rm -it $(docker build -q .)

docker-compose up -d
```