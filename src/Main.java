import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

// SERVER SIDE

public class Main {
    public static void main(String[] args) {
        final int port = 8080;

        // connection
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

                // connection established, server waiting for a message from client
                System.out.println("Waiting for client message...");

                // send a message to client
                oos.writeObject("Hi! Please enter your name");

                // Read a message sent by client application
                String message = (String) ois.readObject();
                System.out.println("Message Received: " + message);

                // Send a response information to the client application
                oos.writeObject("Hi, " + message + "! Are you child? (yes/no)");

                do {
                    // read a reply from client and send a message
                    String message2 = (String) ois.readObject();
                    System.out.println("Message Received: " + message2);

                    if (message2.equals("yes")) {
                        oos.writeObject("Welcome to the kids area, " + message + "! Let's play!");
                        break;
                    } else if (message2.equals("no")) {
                        oos.writeObject("Welcome to the adult zone, " + message + "! Have a good rest, or a good working day!");
                        break;
                    }
                        oos.writeObject("Please enter (yes/no)");
                } while (true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}