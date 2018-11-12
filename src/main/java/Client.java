import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    private InetAddress host;
    private final int PORT = 1234;

    public Client(InetAddress host) {
        this.host = host;
    }

    public void connectToAuction() {
        Socket link = null;

        // Try to connect to the auction
        try {
            link = new Socket(this.host, this.PORT);


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
