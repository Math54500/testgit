import java.io.*;
import java.net.*;

public class TcpClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java TcpClient <server_address> <port_number>");
            System.exit(1);
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connecté au serveur sur " + serverAddress + ":" + port);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String message;
            while (true) {
                message = in.readLine();
                System.out.println("[serveur] Reçu : " + message);

                if (message.equals("stop")) {
                    break;
                }

                System.out.print("[client] Entrez un message au clavier... ");
                message = userInput.readLine();
                out.write(message + "\n");
                out.flush();

                if (message.equals("stop")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
