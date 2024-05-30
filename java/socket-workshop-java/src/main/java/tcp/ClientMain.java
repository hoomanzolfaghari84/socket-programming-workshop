package tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        startClient();
    }

    public static void startClient() throws IOException {
        try (Socket socket = new Socket("localhost", 1234)) {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            Scanner terminal = new Scanner(System.in);
            System.out.println("connection established");
            while (true){
                String s = terminal.nextLine();
                printWriter.println(s);
                printWriter.flush();
                String res = scanner.nextLine();
                System.out.println("server response: " + res);
            }
        }
    }
}
