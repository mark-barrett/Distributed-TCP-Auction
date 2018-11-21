import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Scanner input;
    private PrintWriter output;
    private AuctionCommandHandler commandHandler;
    private String welcomeMessage;
    private String clientRequest;
    private ArrayList<Product> products;
    private String name;

    public ClientHandler(Socket socket, ArrayList<Product> products) {

        // Set the products
        this.products = products;

        try {
            // Instantiate input and output streams
            this.input = new Scanner(socket.getInputStream());
            this.output = new PrintWriter(socket.getOutputStream(), true);

            // Instantiate a command handler instance
            this.commandHandler = new AuctionCommandHandler();

            // Set the welcome message and client request
            this.welcomeMessage = "Welcome to the Auction";
            this.clientRequest = "";

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getClientName() {
        return this.name;
    }

    public void run() {

        // The first thing to ever come through should be the username of the user
        String tempUsername = input.nextLine();

        // If the message contains the USERNAME flag then extract it by removing the USERNAME:
        if(tempUsername.contains("USERNAME")) {
            this.name = tempUsername.replace("USERNAME:", "");
        }

        // First send a welcome message
        output.println(this.welcomeMessage);

        // While what is coming from the client is not leave
        do {
            this.commandHandler.executeCommand(this.clientRequest, this.output, products, this);

            this.clientRequest = input.nextLine();

        } while(!this.clientRequest.equals("leave"));

        AuctionServer.numberOfConnections -= 1;

        // Remove this client from the list of clients:
        ClientListener.getClients().remove(this);
    }

    public void notifyBid(Product product, float amount) {
        output.println("\n[================[New Bid Made]================]");
        output.println("A new bid of "+Float.toString(amount)+" on product "+product.getName()+" has been made.");
        output.println("Auction timer has been reset to 1 minute.\n");
    }

    public void productSold(Product product, float currentBid) {
        output.println("[==============[Closing  Auction]==============]");
        output.println("Closing Auction. Product has been sold!");
        output.println("Product Sold: "+product.getName());
        output.println("Sold To: "+product.purchasedByThread);
        output.println("Sold For: "+currentBid);
    }
}
