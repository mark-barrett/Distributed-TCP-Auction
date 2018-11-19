import com.sun.corba.se.spi.activation.Server;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionServer {

    private ServerSocket serverSocket;
    private final int PORT = 4110;
    private final String welcomeMessage = "Welcome to the Auction";
    private String enteredInput;
    public static ArrayList<Product> products;
    private Scanner userInput;
    private ServerCommandHandler serverCommandHandler;

    public static Auction auction;
    public static int numberOfConnections = 0;

    public AuctionServer() {

        // Lets start with some test mock products
        products = new ArrayList<Product>();

        // Get the products from the local file.
        try {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("products.dat"));

            // Try assign them to the ArrayList
            while(inStream.readObject() != null) {
                products.add((Product) inStream.readObject());
            }


        } catch(Exception exception) {
            System.out.println(exception);
            exception.printStackTrace();
        }

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

        // Start by creating the actual auction
        auction = new Auction(products);

        // Start it
        auction.start();
        
        // Now that is done we can display the menu to the server admin.
        do {
            serverCommandHandler.executeCommand(this.enteredInput);

            this.enteredInput = userInput.nextLine();

        } while(!this.enteredInput.equals("leave"));

        // This is when a user wants to close the auction but save the state of the products into a file.
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("products.dat"));

            System.out.println("Saving products to local file");
            for(int i=0; i<AuctionServer.products.size(); i++) {
                outStream.writeObject(AuctionServer.products.get(i));
            }

            System.out.println("Products saved successfully");

            outStream.close();

        } catch(Exception exception) {
            System.out.println(exception);
            exception.printStackTrace();
        }

        try
        {
            System.out.println("Closing Server....");
            serverSocket.close();
            System.exit(1);
        }
        catch(IOException ioEx)
        {
            System.out.println("Unable to disconnect!");
            System.exit(1);
        }

    }
}
