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
public class ServerApp {
    static final int PORT = Integer.parseInt(System.getProperty("server.port", "12345"));

    public static void main(String[] args) {
        // Connection server =
        //         UdpServer.create()
        //                 .host("127.0.0.1").port(PORT)
        //                 .handle((in, out) ->
        //                         out.sendObject(
        //                                 in.receiveObject()
        //                                         .map(o -> {
        //                                             if (o instanceof DatagramPacket) {
        //                                                 DatagramPacket p = (DatagramPacket) o;
        //                                                 log.info("udp server receive msg");
        //                                                 return new DatagramPacket(p.content().retain(), p.sender());
        //                                             } else {
        //                                                 return Mono.error(new Exception("Unexpected type of the message: " + o));
        //                                             }
        //                                         })))
        //                 .bindNow(Duration.ofSeconds(30));


        Connection server =
                UdpServer.create()
                        .host("127.0.0.1").port(PORT)
                        .handle((in, out) ->
                                in.receiveObject()
                                        .map(o -> {
                                            if (o instanceof DatagramPacket) {
                                                DatagramPacket p = (DatagramPacket) o;
                                                int i = p.content().readableBytes();
                                                byte[] bs = new byte[i];
                                                p.content().readBytes(bs);
                                                log.info("udp server receive msg | {}", new String(bs));
                                                return Mono.empty();
                                            } else {
                                                return Mono.error(new Exception("Unexpected type of the message: " + o));
                                            }
                                        }).then()
                        )
                        .bindNow(Duration.ofSeconds(30));

        log.info("Udp started on port(s): {}", PORT);
        server.onDispose()
                .block();

    }
}
