package udp;

import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws SocketException {
        int clientPort = 4321;
        String serverIp = "localhost";
        int serverPort = 1234;
        int bufferSize = 200;

        Scanner terminal = new Scanner(System.in);
        SocketAddress serverAddress = new InetSocketAddress(serverIp , serverPort);
        try (DatagramSocket datagramSocket = new DatagramSocket(clientPort)) {
            while(true){
                String request = terminal.nextLine();
                if(Objects.equals(request, "quit")) break;

                DatagramPacket datagramPacket = new DatagramPacket(
                        request.getBytes(), request.getBytes().length, serverAddress);

                try {
                    datagramSocket.send(datagramPacket);
                    DatagramPacket response = new DatagramPacket(new byte[bufferSize], bufferSize);
                    datagramSocket.receive(response);
                    String s = new String(response.getData());
                    System.out.println(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
