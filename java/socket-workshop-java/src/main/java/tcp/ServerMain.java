package tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            int clientId = 0 ;
            while (true) {
                Socket socket = serverSocket.accept();
                clientId++;
                Thread thread = new Thread(new ClientRunnable(clientId, socket));
                thread.start();
            }
        }
    }
    public static class ClientRunnable implements Runnable{
        Socket socket;
        int id;
        Scanner scanner ;
        PrintWriter printWriter;
        Scanner terminal;
        public ClientRunnable(int id,Socket socket) {
            this.id = id;
            this.socket = socket;
        }

        private void setupStreams() throws IOException {
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());
            terminal = new Scanner(System.in);
        }
        private void listen(){
            while (true) {
                String s = scanner.nextLine();
                System.out.println("client sent: " + s);
                String response = "server response to:" + s;
                printWriter.println(response);
                printWriter.flush();
            }
        }

        @Override
        public void run() {
            try {
                setupStreams();
                listen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
