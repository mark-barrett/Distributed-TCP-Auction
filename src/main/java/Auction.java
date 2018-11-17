import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
* Auction
* This is the main Auction class which extends the Thread subclass. It is used to cycle through items and execute them
* in time.
*/
public class Auction extends Thread {

    private ArrayList<Product> products;

    public static Product currentProductForSale;

    private Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Auction(ArrayList<Product> products) {
        this.setProducts(products);
        this.setTimer(new Timer());
    }

    public void run() {
        // Start the auction for 1 minute. If there are no bids start it again.
        this.getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                // Get a product off the list
                currentProductForSale = products.get(0);

                System.out.println("[==============[Starting Auction]==============]");
                System.out.println("Starting Auction for 1 Minute and Opening Bids");
                System.out.println("Product: "+currentProductForSale.getName());
                System.out.println("Price: "+currentProductForSale.getPrice());
                System.out.println("Auction has started");

            }
        }, 0, 60000);
    }

    public void acceptBid(float amount) {
        System.out.println("Bid of blah made");
        // This would be something like. this.getTimer().cancel. Then start a new one.
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
