package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ServerMain {
    public static void main(String[] args) throws SocketException {
        int port = 1234;
        int bufferSize = 200;
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            while (true){
                DatagramPacket datagramPacket = new DatagramPacket(new byte[bufferSize],bufferSize);
                try {
                    datagramSocket.receive(datagramPacket);
                    new Thread(()-> handleRequest(datagramSocket, datagramPacket)).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void handleRequest(DatagramSocket datagramSocket, DatagramPacket datagramPacket){
        String request = new String(datagramPacket.getData());
        System.out.println(request+ " from "+ datagramPacket.getSocketAddress());
        String response = "response to:"+ request;
        DatagramPacket dResponse = new DatagramPacket(response.getBytes(), response.getBytes().length, datagramPacket.getSocketAddress());
        System.out.println(response);
        try {
            datagramSocket.send(dResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
