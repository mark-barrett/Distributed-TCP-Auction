import java.io.PrintWriter;
import java.util.ArrayList;

public class CommandHandler {

    // A string to hold the contents of what is being displayed back to the user
    private String display = "";

    public CommandHandler() {
    }

    public void executeCommand(String command, PrintWriter output, ArrayList<Product> products) {
        // Check to see what type is is:
        if(command.equals("bid")) {
            // Return the bid screen
            System.out.println("Heres how you bid");
        } else if(command.equals("list")) {

            Product product;

            this.display = "[================[Product List]================]\n";

            // Return a list of the products
            for(int i=0; i<products.size(); i++) {
                product = products.get(i);

                this.display += product.toString();
            }

            this.display += "Return to the main menu by typing: main";

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
