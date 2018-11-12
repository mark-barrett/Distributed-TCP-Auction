import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class AuctionServer {

    private ServerSocket serverSocket;
    private final int PORT = 1234;
    private final String welcomeMessage = "Welcome to the Auction" +
            " the auction.";

    public AuctionServer() {
        System.out.println("Opening port for incoming client connections....");

        // Try open the port
        try {
            serverSocket = new ServerSocket(this.PORT);
        } catch (IOException e) {
            System.out.println("Unable to attach to port.");
            e.printStackTrace();
        }

        // Execute the handle clients method infinitely until stopped
        do {
            this.handleClient();
        } while (true);
    }

    private void handleClient() {
        Socket link = null;

        // Try accept the connection
        try {
            link = serverSocket.accept();

            // Once we have established a connection return a welcome message
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            // Send the welcome message
            output.println(this.welcomeMessage);

            // Output the current item
            String auctionMenu = "[================[Auction Menu]================]\n" +
                    "Please pick an option:\n"+
                    "bid   : Show the current item and bid on it\n"+
                    "list  : List items currently up for auction\n"+
                    "leave : Leave the current auction\n";

            output.println(auctionMenu);

            // While the message sent is not equal to: ***CLOSE-CONNECTION***
        } catch(IOException exception) {
            System.out.println(exception);
            exception.printStackTrace();
        } finally {
            try {
                System.out.println("Closing auction....");
                link.close();
            } catch(IOException exception) {
                System.out.println("Unable to close auction....");
            }
        }
    }


}
