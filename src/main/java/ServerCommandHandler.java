import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
* ServerCommandHandler
* This takes in a command that is read from the server admin and then does something with it.
*/
public class ServerCommandHandler {

    // A string to hold the contents of what is being displayed back to the user
    private String display = "";
    private Scanner userInput;

    public ServerCommandHandler() {
        this.userInput = new Scanner(System.in);
    }

    public void executeCommand(String command) {
        if(command.equals("list")) {
            // List all connected clients
            System.out.println("Connected clients:");
        } else if(command.equals("add")) {

            String productName;
            String productPrice;

            // Show a menu to the user
            System.out.println("[================[Add  Product]================]");
            System.out.println("Product Name:");

            // Get the product name
            productName = userInput.nextLine();

            System.out.println("\nProduct Price:");

            productPrice = userInput.nextLine();

            // Add the product
            Product product = new Product(productName, Float.parseFloat(productPrice));

            AuctionServer.products.add(product);

            System.out.println("Added Product: "+productName+" for €"+productPrice);


            System.out.println("Type main to get back to the main menu");


        } else {
            String numberOfClients;
            if(AuctionServer.numberOfConnections > 1 || AuctionServer.numberOfConnections == 0){
                numberOfClients = AuctionServer.numberOfConnections+" clients currently connected\n";
            } else {
                numberOfClients = AuctionServer.numberOfConnections+" client currently connected\n";
            }
            System.out.println("[================[Server  Menu]================]\n"+
                    numberOfClients+
                    "Please pick an option:\n"+
                    "add      : Add a product to the auction\n"+
                    "list     : List connected clients\n"+
                    "leave    : Close the server and the auction\n");
        }
    }
}
