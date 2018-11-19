import com.sun.deploy.util.SessionState;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
* Auction
* This is the main Auction class which extends the Thread subclass. It is used to cycle through items and execute them
* in time.
*/
public class Auction extends Thread {

    private static ArrayList<Product> products;

    public static Product currentProductForSale;

    public static float currentBid;

    private static Timer timer;

    public static Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public static long auctionStartTime;

    final long NANOSEC_PER_SEC = 1000l*1000*1000;

    public Auction(ArrayList<Product> products) {
        this.setProducts(products);
        this.setTimer(new Timer());
        Auction.currentBid = 0;
    }

    public void run() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // Get a product off the list
                currentProductForSale = products.get(0);

                // Set the start time
                auctionStartTime = System.currentTimeMillis();

                System.out.println("[==============[Starting Auction]==============]");
                System.out.println("Starting Auction for 1 Minute and Opening Bids");
                System.out.println("Product: "+currentProductForSale.getName());
                System.out.println("Price: "+currentProductForSale.getPrice());
                System.out.println("Current Bid: "+currentBid);
                System.out.println("Auction has started");

                while ((System.nanoTime()-System.nanoTime())< 1*60*NANOSEC_PER_SEC){

                }

                System.out.println("Auction done!");
            }
        };

        Auction.getTimer().schedule(timerTask, 0, 60000);

    }

    public static void restartAuction() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // Get a product off the list
                currentProductForSale = products.get(0);

                // Set the start time
                auctionStartTime = System.currentTimeMillis();

                System.out.println("[==============[Starting Auction]==============]");
                System.out.println("Starting Auction for 1 Minute and Opening Bids");
                System.out.println("Product: "+currentProductForSale.getName());
                System.out.println("Price: "+currentProductForSale.getPrice());
                System.out.println("Current Bid: "+currentBid);
                System.out.println("Auction has started");
            }
        };

        Auction.getTimer().cancel();
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    public static void acceptBid(Product product, float amount) {
        // This would be something like. this.getTimer().cancel. Then start a new one.
        Auction.currentBid = amount;

        // Get all of the connected clients and then notify them of the bids
        for(ClientHandler handler: ClientListener.getClients()) {
            // The notify method
            handler.notifyBid(product, amount);
        }

        Auction.restartAuction();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        Auction.products = products;
    }
}
