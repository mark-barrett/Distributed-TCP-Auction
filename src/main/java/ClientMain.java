import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain {

    public static void main(String[] args) {

        // Start the auction client
        try {

            AuctionClient client = new AuctionClient(InetAddress.getByName("localhost"), "1234", "markbarrett");

            client.connectToAuction();
        }
        catch(UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        /*
        try {

            if(args.length != 3) {
                System.out.println("Port, host and name must be specified.");
            } else {
                AuctionClient client = new AuctionClient(InetAddress.getByName(args[0]), args[1], args[2]);

                client.connectToAuction();
            }
        }
        catch(UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        */
    }
}
