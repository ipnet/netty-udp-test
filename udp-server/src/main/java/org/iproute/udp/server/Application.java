package org.iproute.udp.server;


import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.udp.UdpServer;

import java.time.Duration;

/**
 * UdpServer
 *
 * @author zhuzhenjie
 * @since 2022/8/26
 */
@Slf4j
public class Application {
    static final int PORT = Integer.parseInt(System.getProperty("server.port", "12345"));

    public static void main(String[] args) {
        Connection server =
                UdpServer.create()
                        .host("127.0.0.1")
                        .port(PORT)
                        .handle((in, out) ->
                                out.sendObject(
                                        in.receiveObject()
                                                .map(o -> {
                                                            if (o instanceof DatagramPacket) {
                                                                log.info("receive");
                                                                DatagramPacket p = (DatagramPacket) o;
                                                                return new DatagramPacket(p.content().retain(), p.sender());
                                                            } else {
                                                                return Mono.error(new Exception("Unexpected type of the message: " + o));
                                                            }
                                                        }
                                                )
                                )
                        )
                        .bindNow(Duration.ofSeconds(30));

        server.onDispose()
                .block();

    }
}
