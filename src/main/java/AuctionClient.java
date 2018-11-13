import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class AuctionClient {
    private InetAddress host;
    private final int PORT = 4110;
    private String serverResponse;
    private String clientRequest;

    public AuctionClient(InetAddress host) {
        this.host = host;
        this.serverResponse = " ";
        this.clientRequest = " ";
    }

    public void connectToAuction() {
        Socket link = null;

        // Try to connect to the auction
        try {
            link = new Socket(this.host, this.PORT);

            // Get the input to get messages from the server
            Scanner serverInput = new Scanner(link.getInputStream());

            // Setup an output stream to send data to the server
            PrintWriter clientOutput = new PrintWriter(link.getOutputStream(), true);

            // Setup a scanner for the users keyboard input
            Scanner userInput = new Scanner(System.in);

            // Loop infinitely until the user types in: leave auction
            do {

                do {
                    // Get the response
                    this.serverResponse = serverInput.nextLine();

                    // Print it if it doesn't equal to the end of the block
                    if(!this.serverResponse.equals("endblock")) {
                        System.out.println(this.serverResponse);
                    }

                } while(!this.serverResponse.equals("endblock"));

                // Get whatever the user is typing in
                this.clientRequest = userInput.nextLine();

                System.out.println(this.clientRequest);

                // Output it to the server
                clientOutput.println(this.clientRequest);


            } while(!this.clientRequest.equals("leave"));


        } catch (IOException e) {
            // If we cannot connect to the auction
            e.printStackTrace();
        } finally {
            // Finally try and close the connection
            try {
                System.out.println("Disconnecting from auction....");
                link.close();
            } catch(IOException exception) {
                System.out.println("Unable to disconnect from auction");
                System.exit(1);
            }
        }
    }
}
