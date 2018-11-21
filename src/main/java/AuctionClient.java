import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class AuctionClient {
    private InetAddress host;
    private final int PORT;
    private String serverResponse;
    private String clientRequest;
    private String name;

    public AuctionClient(InetAddress host, String port, String name) {
        this.host = host;
        this.PORT = Integer.parseInt(port);
        this.serverResponse = " ";
        this.clientRequest = " ";
        this.name = name;
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

            // Message Thread
            MessageListener messageListener = new MessageListener(serverInput);

            messageListener.start();

            // Setup a scanner for the users keyboard input
            Scanner userInput = new Scanner(System.in);

            // Output the username to the client handler
            clientOutput.println("USERNAME:"+this.name);

            // Loop infinitely until the user types in: leave auction
            do {

                // Get whatever the user is typing in
                this.clientRequest = userInput.nextLine();

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
