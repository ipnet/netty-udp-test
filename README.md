# netty udp test

## build

```shell
gradlew clean build
```

## udp-server

default `server.port` is `12345`

```shell
java -jar [-Dserver.port=<port>] udp-server-1.0-SNAPSHOT.jar
```

## udp-client

default `server.host` is `127.0.0.1`
default `server.port` is `12345`

```shell
java -jar -D[-Dserver.port=<host>] -D[-Dserver.port=<port>] udp-client-1.0-SNAPSHOT.jar
```