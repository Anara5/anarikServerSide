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
        try {
            // connection established, server waiting for a message from client
            System.out.println("Waiting for client message...");

            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            // send a message to client
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Hi! Please enter your name");

            // Read a message sent by client application
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);

            // Send a response information to the client application
            oos.writeObject("Hi, " + message + "! Are you child? (yes/no)");

            // read a reply from client and send a message
            String message2 = (String) ois.readObject();
            System.out.println("Message Received: " + message2);

            if (message2.equals("yes")) {
                oos.writeObject("Welcome to the kids area, " + message + "! Let's play!");
            } else {
                oos.writeObject("Welcome to the adult zone, " + message + "! Have a good rest, or a good working day!");
            }

            ois.close();
            oos.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}