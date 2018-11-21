import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private float price;
    private boolean sold;
    public String purchasedByThread;

    public Product(String name, float price) {
        this.setName(name);
        this.setPrice(price);
        this.purchasedByThread = "nobody";
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Product Name: "+this.getName()+"\n"+
                "Product Price: €"+this.getPrice()+"\n\n";
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }
}
