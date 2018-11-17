import java.io.PrintWriter;
import java.util.ArrayList;

public class AuctionCommandHandler {

    // A string to hold the contents of what is being displayed back to the user
    private String display = "";

    public AuctionCommandHandler() {
    }

    public void executeCommand(String command, PrintWriter output, ArrayList<Product> products) {

        System.out.println("Here");
        // Check to see what type is is:
        if(command.equals("bid")) {
            // Return the product currently for sale
            this.display = "[===========[Current Item  For Sale]===========]\n" +
                            "Product: "+Auction.currentProductForSale.getName()+"\n"+
                            "Price: "+Auction.currentProductForSale.getPrice()+"\n"+
                            " \n"+
                            "To Enter a bid type: bid [amount]\n"+
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

        } else {
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
