import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
* Client Listener
* This is a class that extends the thread subclass. It allows for the server to listen for incoming client connections
* whilst it does other things like adding products etc.
*/
public class ClientListener extends Thread {

    private ServerSocket serverSocket;
    private ArrayList<Product> products;

    public ClientListener(ServerSocket serverSocket, ArrayList<Product> products) {
        // Get the server socket from the Auction Server
        this.serverSocket = serverSocket;
        // Get the products
        this.products = products;

    }

    // Overwrite the thread run method.
    public void run() {

        System.out.println("Auction Server Started, Listening for Clients...");

        // Infinitely check for connections
        do {
            // Try accept the connection
            try {
                Socket client = serverSocket.accept();

                AuctionServer.numberOfConnections += 1;

                // Create a client handler instance thread
                ClientHandler handler = new ClientHandler(client, products);

                handler.start();

            } catch (IOException exception) {
                System.out.println(exception);
                exception.printStackTrace();
            }
        } while(true);

    }
}
