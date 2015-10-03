import java.io.IOException;

/**
 * Created by Надюша on 02.10.2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String productDirectory=args[0];
        String coloursFile=args[1];
        ProductsReader productsReader = new ProductsReader(productDirectory);
        productsReader.readFiles();
        productsReader.makeUpResult();
    }
}
