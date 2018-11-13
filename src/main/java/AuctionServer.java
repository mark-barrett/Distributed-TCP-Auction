import com.sun.corba.se.spi.activation.Server;
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
    private String enteredInput;
    private ArrayList<Product> products;
    private Scanner userInput;
    private ServerCommandHandler serverCommandHandler;

    public static int numberOfConnections = 0;

    public AuctionServer() {

        // Lets start with some test mock products
        products = new ArrayList<Product>();

        Product product1 = new Product("Mac Book Pro", 1599);
        Product product2 = new Product("Galaxy S9 Plus", 999);

        products.add(product1);
        products.add(product2);

        // Create a scanner for userInput for the menu
        this.userInput = new Scanner(System.in);

        // String to hold the choice on the server
        this.enteredInput = "";

        // Instantiate a server command handler
        serverCommandHandler = new ServerCommandHandler();

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
        // Lets execute the Client Listener thread so it can start listening for client connections
        ClientListener clientListener = new ClientListener(this.serverSocket, this.products);

        clientListener.start();

        // Now that is done we can display the menu to the server admin.
        do {
            serverCommandHandler.executeCommand(this.enteredInput);

            this.enteredInput = userInput.nextLine();

        } while(!this.enteredInput.equals("leave") || !this.enteredInput.equals("leave -s"));

    }
}
