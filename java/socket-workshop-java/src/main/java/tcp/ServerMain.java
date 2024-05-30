package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            int clientId = 0 ;
            System.out.println("server started");
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
                System.out.println("client "+ id+ " sent: " + s);
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

    public static void handleData(Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());


        int buffer = 1024;
        byte[] content = new byte[buffer];
        inputStream.readFully(content);

        File file = new File("");
        byte[] bytes = Files.readAllBytes(file.toPath());
        outputStream.write(bytes);
        outputStream.flush();

        new Scanner(System.in).nextLine();
    }
}
