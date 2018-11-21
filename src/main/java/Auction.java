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
                // Get a product off the list that isn't sold
                productloop:
                for(int i=0; i<products.size(); i++) {
                    if(!products.get(i).isSold()) {
                        currentProductForSale = products.get(i);
                        break productloop;
                    }
                }

                // Set the start time
                auctionStartTime = System.currentTimeMillis();

                System.out.println("[==============[Starting Auction]==============]");
                System.out.println("Starting Auction for 1 Minute and Opening Bids");
                System.out.println("Product: "+currentProductForSale.getName());
                System.out.println("Price: "+currentProductForSale.getPrice());
                System.out.println("Current Bid: "+currentBid);
                System.out.println("Auction has started");

                try {
                    Auction.sleep(60000);
                } catch(Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }

                // This timer task cannot exist if the user has bid on the item so if it has got to here with being
                // cancelled then we know it hasn't been sold.
                if(Auction.currentProductForSale.purchasedByThread == 0) {
                    System.out.println("[==============[Closing  Auction]==============]");
                    System.out.println("Closing Auction. No bids made on this product.");
                }
            }
        };

        Auction.getTimer().schedule(timerTask, 0, 60000);

    }

    public static void restartAuction() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // Set the start time
                auctionStartTime = System.currentTimeMillis();

                System.out.println("[==============[Starting Auction]==============]");
                System.out.println("Starting Auction for 1 Minute and Opening Bids");
                System.out.println("Product: "+currentProductForSale.getName());
                System.out.println("Price: "+currentProductForSale.getPrice());
                System.out.println("Current Bid: "+currentBid);
                System.out.println("Auction has started");

                try {
                    Auction.sleep(60000);
                } catch(Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }

                System.out.println("Auction has finished!");

                // Check if the Auction.currentProductForSale.purchasedBy is not 0. if its not 0 then we
                // know that someone has bid on it
                // Send this to all client threads
                if(Auction.currentProductForSale.purchasedByThread != 0) {

                    // Set the value of the currentProductForSale to sold in the array list.
                    for(int i=0; i<products.size(); i++) {
                        if(products.get(i) == Auction.currentProductForSale) {
                            products.get(i).setSold(true);
                        }
                    }

                    // Set the next product in the arraylist
                    productloop:
                    for(int i=0; i<products.size(); i++) {
                        System.out.println("Finding a new product to sell");
                        System.out.println("Size of array list:"+products.size());
                        if(!products.get(i).isSold()) {
                            currentProductForSale = products.get(i);
                            System.out.println("Found one");
                            System.out.println(currentProductForSale.toString());
                            break productloop;
                        }
                    }

                    // Get all of the connected clients and then notify them of the bids
                    for(ClientHandler handler: ClientListener.getClients()) {
                        // The notify method
                        handler.productSold(Auction.currentProductForSale, Auction.currentBid);
                    }
                }
            }
        };

        Auction.getTimer().cancel();
        timer = new Timer();
        timer.schedule(timerTask, 0, 60000);
    }

    public static void acceptBid(Product product, float amount, Long clientID) {
        // This would be something like. this.getTimer().cancel. Then start a new one.
        Auction.currentBid = amount;

        // Set the current product for sale as being bid on (bought by) the thread by clientID
        Auction.currentProductForSale.purchasedByThread = clientID;

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
