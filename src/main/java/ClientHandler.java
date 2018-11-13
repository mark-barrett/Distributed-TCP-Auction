import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Scanner input;
    private PrintWriter output;
    private CommandHandler commandHandler;
    private String welcomeMessage;
    private String clientRequest;
    private ArrayList<Product> products;

    public ClientHandler(Socket socket, ArrayList<Product> products) {

        // Set the products
        this.products = products;

        try {
            // Instantiate input and output streams
            this.input = new Scanner(socket.getInputStream());
            this.output = new PrintWriter(socket.getOutputStream(), true);

            // Instantiate a command handler instance
            this.commandHandler = new CommandHandler();

            // Set the welcome message and client request
            this.welcomeMessage = "Welcome to the Auction";
            this.clientRequest = "";

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public void run() {

        // First send a welcome message
        output.println(this.welcomeMessage);

        // While what is coming from the client is not leave
        do {
            this.commandHandler.executeCommand(this.clientRequest, this.output, products);

            this.clientRequest = input.nextLine();

        } while(!this.clientRequest.equals("leave"));
    }
}
