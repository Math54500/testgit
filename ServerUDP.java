import java.net.*;

public class ServerUDP {
    private static final int PORT = 65535;
    private static final String HOST = "192.168.36.36";

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            // Thread pour envoyer les messages
            Thread senderThread = new Thread(() -> {
                try {
                    InetAddress address = InetAddress.getByName(HOST);
                    DatagramSocket senderSocket = new DatagramSocket();
                    while (true) {
                        // Lire le message de l'utilisateur
                        String message = System.console().readLine();
                        byte[] buffer = message.getBytes();

                        // Envoyer le message au destinataire
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
                        senderSocket.send(packet);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            senderThread.start();

            // Thread pour recevoir les messages
            Thread receiverThread = new Thread(() -> {
                try {
                    DatagramSocket receiverSocket = new DatagramSocket(PORT);
                    while (true) {
                        byte[] buffer = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        receiverSocket.receive(packet);

                        // Afficher le message reçu
                        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("Message reçu: " + receivedMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
