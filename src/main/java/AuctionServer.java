import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class AuctionServer {

    private ServerSocket serverSocket;
    private final int PORT = 4110;
    private final String welcomeMessage = "Welcome to the Auction";
    private String clientRequest = "";
    private ArrayList<Product> products;

    public AuctionServer() {

        // Lets start with some test mock products
        products = new ArrayList<Product>();

        Product product1 = new Product("Mac Book Pro", 1599);
        Product product2 = new Product("Galaxy S9 Plus", 999);

        products.add(product1);
        products.add(product2);

        System.out.println("Auction Server Starting...");

        // Try open the port
        try {
            serverSocket = new ServerSocket(this.PORT);
        } catch (IOException e) {
            System.out.println("Unable to attach to port.");
            e.printStackTrace();
        }

        this.startServer();
    }

    private void startServer() {
        Socket link = null;

        // Infinitely check for connections
        do {
            // Try accept the connection
            try {
                Socket client = serverSocket.accept();

                System.out.println("New client connection established...");

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
