import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain {

    public static void main(String[] args) {

        // Start the auction client
        try {
            AuctionClient client = new AuctionClient(InetAddress.getLocalHost());

            client.connectToAuction();
        }
        catch(UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
    }
}
