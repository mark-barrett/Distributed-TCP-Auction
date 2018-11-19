import java.io.PrintWriter;
import java.util.ArrayList;

public class AuctionCommandHandler {

    // A string to hold the contents of what is being displayed back to the user
    private String display = "";

    public AuctionCommandHandler() {
    }

    public void executeCommand(String command, PrintWriter output, ArrayList<Product> products) {

        // Check to see what type is is:
        if(command.equals("bid")) {

            System.out.println(System.currentTimeMillis());
            System.out.println(Auction.auctionStartTime);

            long timeRemaining = 60 - ((System.currentTimeMillis() - Auction.auctionStartTime) / 1000);
            // Return the product currently for sale
            this.display = "[===========[Current Item  For Sale]===========]\n" +
                            "Product: "+Auction.currentProductForSale.getName()+"\n"+
                            "Price: "+Auction.currentProductForSale.getPrice()+"\n"+
                            "Current Bid: "+Auction.currentBid+"\n"+
                            "Time Remaining: "+Long.toString(timeRemaining)+" seconds\n"+
                            " \n"+
                            "To Enter a bid type: place bid: [amount]\n"+
                            "Or return to the main menu: main\n"+
                            "endblock";

        } else if(command.equals("list")) {

            Product product;

            this.display = "[================[Product List]================]\n";

            // Return a list of the products
            for(int i=0; i<products.size(); i++) {
                product = products.get(i);

                this.display += product.toString();
            }

            this.display += "Return to the main menu by typing: main\n"+
                    "endblock";

        } else if(command.contains("place bid")) {
            // The user wants to place a bid
            float bid = Float.parseFloat((command.substring(command.lastIndexOf(": ")+1)));

            // Check if entered bid is greater than the price of product if the current bid is 0 or greater then the currentBid if its not
            if(Auction.currentBid == 0.0) {
                if(bid > Auction.currentProductForSale.getPrice()) {
                    this.display = "Bid of "+bid+" added. To return to the main menu: main\n"+
                    "endblock";

                    // Accept the bid
                    Auction.acceptBid(Auction.currentProductForSale, bid);

                } else {
                    this.display = "That bid is lower then the product price.\n"+
                            "Place a higher bid or return to the main menu.\n"+
                            "endblock";
                }
            } else {
                if(bid > Auction.currentBid) {
                    this.display = "Bid of "+bid+" added. To return to the main menu: main\n"+
                    "endblock";

                    // Accept the bid
                    Auction.acceptBid(Auction.currentProductForSale, bid);

                } else {
                    this.display = "That bid is lower then the product price.\n"+
                            "Place a higher bid or return to the main menu.\n"+
                            "endblock";
                }
            }
        }
        else {
            // Return the menu
            this.display = "[================[Auction Menu]================]\n" +
                    "bid   : Show the current item and bid on it\n"+
                    "list  : List items currently up for auction\n"+
                    "leave : Leave the current auction\n"+
                    "Please choose an option:\n"+
                    "endblock";
        }

        // Out put the display
        output.println(this.display);
    }
}
