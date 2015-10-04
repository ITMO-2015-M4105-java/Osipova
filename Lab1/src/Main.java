import java.io.File;
import java.io.IOException;

/**
 * Created by Надюша on 02.10.2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String productDirectory=args[0];
        String coloursFile=args[1];
        ColoursReader coloursReader = new ColoursReader(coloursFile);
        coloursReader.readColours();

        ProductsReader productsReader = new ProductsReader(productDirectory,coloursReader.getColourMap());
        coloursReader.getColourMap();
        productsReader.readFiles();
        if (productsReader.getProductMap().isEmpty()){
            System.out.println("SORRY, THERE IS NO CORRECT PRODUCT DATA TO PROCESS");
            return;
        }
        productsReader.makeUpResult();
    }
}
