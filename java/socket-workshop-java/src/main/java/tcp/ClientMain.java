package tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException {


        // video 1: initialization

        try (Socket socket = new Socket("localhost", 1234)) {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            Scanner terminal = new Scanner(System.in);
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
