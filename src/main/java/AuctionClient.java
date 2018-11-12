import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class AuctionClient {
    private InetAddress host;
    private final int PORT = 1234;
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
                // While there are new lines coming in from the server
                while(serverInput.hasNextLine()) {
                    System.out.println(serverInput.nextLine());
                }
                // Get whatever the user is typing in
                this.clientRequest = userInput.nextLine();

                // Output it to the server
                clientOutput.print(this.clientRequest);

            } while(!userInput.equals("leave auction"));



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
