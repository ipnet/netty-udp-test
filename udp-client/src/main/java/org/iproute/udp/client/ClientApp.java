package org.iproute.udp.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpClient;

import java.time.Duration;
import java.util.UUID;

/**
 * Application
 *
 * @author zhuzhenjie
 * @since 2022/8/26
 */
@Slf4j
public class ClientApp {
    static final String HOST = System.getProperty("server.host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("server.port", "12345"));

    @SneakyThrows
    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString();
        log.info("Connect to udp server . {}:{}", HOST, PORT);
        log.info("And send an uuid to server . uuid = {}", uuid);
        Connection connection =
                UdpClient.create()
                        .host(HOST)
                        .port(PORT)
                        .handle((in, out) -> out
                                .sendString(Mono.just(uuid))
                        )
                        .connectNow(Duration.ofSeconds(30));

        connection.onDispose()
                .block();
    }
}

