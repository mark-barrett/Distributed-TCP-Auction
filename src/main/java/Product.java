import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private String name;
    private float price;

    public Product(String name, float price) {
        this.setName(name);
        this.setPrice(price);
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
}
