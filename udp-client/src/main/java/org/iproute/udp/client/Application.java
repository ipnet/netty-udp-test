package org.iproute.udp.client;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;

/**
 * Application
 *
 * @author zhuzhenjie
 * @since 2022/8/26
 */
public class Application {
    static final String HOST = System.getProperty("server.host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("server.port", "12345"));

    @SneakyThrows
    public static void main(String[] args) {
        // Connection connection =
        //         UdpClient.create()
        //                 .host(HOST)
        //                 .port(PORT)
        //                 .handle((in, out) ->
        //                         in.receive()
        //                                 .then()
        //                 )
        //                 .connectNow(Duration.ofSeconds(30));

        DatagramChannel udp = DatagramChannel.open();
        udp.configureBlocking(true);
        udp.connect(new InetSocketAddress(HOST, PORT));


        byte[] data = new byte[1024];
        new Random().nextBytes(data);
        for (int i = 0; i < 4; i++) {
            udp.write(ByteBuffer.wrap(data));
        }

        udp.close();

        // connection.onDispose()
        //         .block();
    }

}
