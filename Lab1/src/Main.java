import java.io.File;
import java.io.IOException;

/**
 * Created by AnSick on 02.10.2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
       if (args.length!=2){
            System.out.println("INCORRECT NUMBER OF ARGUMENTS");
            System.exit(0);
        }
        
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
